package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.mapper.PedidoMapper;
import com.entidades.buenSabor.business.service.*;
import com.entidades.buenSabor.business.service.Base.BaseServiceImp;
import com.entidades.buenSabor.domain.entities.*;
import com.entidades.buenSabor.domain.enums.Estado;
import com.entidades.buenSabor.domain.enums.Rol;
import com.entidades.buenSabor.domain.enums.TipoEnvio;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class PedidoServiceImp extends BaseServiceImp<Pedido,Long> implements PedidoService {


    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    PedidoMapper pedidoMapper;
    @Autowired
    ArticuloInsumoService articuloInsumoService;
    @Autowired
    ArticuloService articuloService;
    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    ArticuloManufacturadoService articuloManufacturadoService;

    @Override
    public Pedido create(Pedido pedido) throws RuntimeException{
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstado(Estado.PENDIENTE);
        calcularTotal(pedido);
        validarStock(pedido.getDetallePedidos());
        aplicarDescuento(pedido);
        calcularTiempoEstimado(pedido);
        calcularTotalCosto(pedido);
        return super.create(pedido);
    }

    @Override
    public void validarStock(Set<DetallePedido> detalles) throws RuntimeException {
        for (DetallePedido detalle : detalles) {
            Articulo articulo = detalle.getArticulo();
            if (articulo instanceof ArticuloInsumo) {
                ArticuloInsumo insumo = (ArticuloInsumo) articulo;
                if (!insumo.tieneStockSuficiente(detalle.getCantidad())) {
                    throw new RuntimeException("Stock insuficiente para el artículo: " + insumo.getDenominacion());
                }
                // Decrementar el stock
                insumo.setStockActual(insumo.getStockActual() - detalle.getCantidad());
                articuloService.update(insumo, insumo.getId());
            } else {
                ArticuloManufacturado articuloManufacturado = articuloManufacturadoService.getById(articulo.getId());
                for (ArticuloManufacturadoDetalle amd : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                    if (!amd.getArticuloInsumo().tieneStockSuficiente(detalle.getCantidad())) {
                        throw new RuntimeException("Stock insuficiente para el artículo: " + amd.getArticuloInsumo().getDenominacion());
                    }
                    // Decrementar el stock
                    amd.getArticuloInsumo().setStockActual(amd.getArticuloInsumo().getStockActual() - detalle.getCantidad());
                    articuloService.update(amd.getArticuloInsumo(), amd.getArticuloInsumo().getId());
                }
            }
        }
    }


    @Override
    public void aplicarDescuento(Pedido pedido) {
        if (pedido.getTipoEnvio() == TipoEnvio.TAKE_AWAY) {
            pedido.setTotal(pedido.getTotal() * 0.9); // Aplicar 10% de descuento
        }
    }

    @Override
    public void calcularTiempoEstimado(Pedido pedido) {
        int tiempoArticulos = pedido.getDetallePedidos().stream()
                .mapToInt(detalle -> {
                    if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                        ArticuloManufacturado articuloManufacturado = (ArticuloManufacturado) detalle.getArticulo();
                        return articuloManufacturado.getTiempoEstimadoMinutos();
                    } else {
                        return 0;
                    }
                })
                .sum();
        int tiempoCocina = obtenerPedidosEnCocina().stream()
                .flatMap(p -> p.getDetallePedidos().stream())
                .mapToInt(detalle -> {
                    if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                        ArticuloManufacturado articuloManufacturado = (ArticuloManufacturado) detalle.getArticulo();
                        return articuloManufacturado.getTiempoEstimadoMinutos();
                    } else {
                        return 0;
                    }
                })
                .sum();

        int cantidadCocineros = contarCocineros();
        int tiempoCocinaPromedio = cantidadCocineros > 0 ? tiempoCocina / cantidadCocineros : 0;

        int tiempoDelivery = pedido.getTipoEnvio() == TipoEnvio.DELIVERY ? 10 : 0;
        pedido.setHoraEstimadaFinalizacion(LocalTime.now().plusMinutes(tiempoArticulos + tiempoCocinaPromedio + tiempoDelivery));
    }

    @Override
    public List<Pedido> obtenerPedidosEnCocina() {

        return pedidoRepository.findByEstado(Estado.PREPARACION);
    }

    @Override
    public int contarCocineros() {
        return empleadoService.contarPorRol(Rol.COCINERO);
    }

    @Override
    public Pedido cambiaEstado(Estado estado, Long id) {
        Pedido pedido = getById(id);
        pedido.setEstado(estado);
        return create(pedido);
    }

    private void calcularTotal(Pedido pedido) {
        double total = 0.0;
        for (DetallePedido detalle : pedido.getDetallePedidos()) {
            total += detalle.getCantidad() * detalle.getArticulo().getPrecioVenta();
        }
        pedido.setTotal(total);
    }
    private void calcularTotalCosto(Pedido pedido) {
        double totalCosto = 0.0;
        for (DetallePedido detalle : pedido.getDetallePedidos()) {
            Articulo articulo = detalle.getArticulo();
            if (articulo instanceof ArticuloInsumo) {
                ArticuloInsumo insumo = (ArticuloInsumo) articulo;
                totalCosto += detalle.getCantidad() * insumo.getPrecioCompra();
            } else if (articulo instanceof ArticuloManufacturado) {
                ArticuloManufacturado manufacturado = (ArticuloManufacturado) articulo;
                for (ArticuloManufacturadoDetalle detalleManufacturado : manufacturado.getArticuloManufacturadoDetalles()) {
                    ArticuloInsumo insumo = detalleManufacturado.getArticuloInsumo();
                    totalCosto += detalleManufacturado.getCantidad() * insumo.getPrecioCompra();
                }
            }
        }
        pedido.setTotalCosto(totalCosto);

    }
}

