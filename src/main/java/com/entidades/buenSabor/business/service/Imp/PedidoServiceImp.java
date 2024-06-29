package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.mapper.PedidoMapper;
import com.entidades.buenSabor.business.service.*;
import com.entidades.buenSabor.business.service.Base.BaseServiceImp;
import com.entidades.buenSabor.domain.entities.*;
import com.entidades.buenSabor.domain.enums.Estado;
import com.entidades.buenSabor.domain.enums.Rol;
import com.entidades.buenSabor.domain.enums.TipoEnvio;
import com.entidades.buenSabor.repositories.FacturaRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

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

    @Autowired
    FacturaService facturaService;

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    SendEmailService sendEmailService;

    @Override
    public Pedido create(Pedido pedido) throws RuntimeException{
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstado(Estado.PENDIENTE);
        //calcularTotal(pedido);
        disminucionStock(pedido.getDetallePedidos());
        //aplicarDescuento(pedido);
        calcularTiempoEstimado(pedido);
        calcularTotalCosto(pedido);
        return super.create(pedido);
    }
    private static final Logger logger = Logger.getLogger(PedidoServiceImp.class.getName());


    private static final Map<Estado, Set<Estado>> validTransitions = new EnumMap<>(Estado.class);

    static {
        validTransitions.put(Estado.PENDIENTE, EnumSet.of(Estado.PREPARACION, Estado.RECHAZADO));
        validTransitions.put(Estado.PREPARACION, EnumSet.of(Estado.FACTURADO, Estado.RECHAZADO));
        validTransitions.put(Estado.FACTURADO, EnumSet.of(Estado.DELIVERY, Estado.RECHAZADO));
        validTransitions.put(Estado.DELIVERY, EnumSet.of(Estado.ENTREGADO));
        validTransitions.put(Estado.ENTREGADO, EnumSet.noneOf(Estado.class));
        validTransitions.put(Estado.RECHAZADO, EnumSet.noneOf(Estado.class));
    }
    @Override
    public void disminucionStock(Set<DetallePedido> detalles) throws RuntimeException {
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
                    amd.getArticuloInsumo().setStockActual(amd.getArticuloInsumo().getStockActual() - detalle.getCantidad() * amd.getCantidad());
                    articuloService.update(amd.getArticuloInsumo(), amd.getArticuloInsumo().getId());

                    // Registro del decremento de stock
                    logger.info("Stock del artículo insumo '" + amd.getArticuloInsumo().getDenominacion() + "' en el artículo manufacturado '"
                            + articuloManufacturado.getDenominacion() + "' disminuido en " + detalle.getCantidad() * amd.getCantidad());
                }
            }
        }
    }

    //Metodo para volver al stock si es que rechazaron el pedido
    public void volverStockAnterior(Set<DetallePedido> detalles) {
        for (DetallePedido detalle : detalles) {
            Articulo articulo = detalle.getArticulo();
            if (articulo instanceof ArticuloInsumo) {
                ArticuloInsumo insumo = (ArticuloInsumo) articulo;
                insumo.setStockActual(insumo.getStockActual() + detalle.getCantidad());
                articuloInsumoService.update(insumo, insumo.getId());
            } else {
                ArticuloManufacturado articuloManufacturado = articuloManufacturadoService.getById(articulo.getId());
                for (ArticuloManufacturadoDetalle amd : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                    ArticuloInsumo insumo = amd.getArticuloInsumo();
                    insumo.setStockActual(insumo.getStockActual() + detalle.getCantidad() * amd.getCantidad());
                    articuloInsumoService.update(insumo, insumo.getId());

                    // Registro del decremento de stock
                    logger.info("Stock del artículo insumo '" + amd.getArticuloInsumo().getDenominacion() + "' en el artículo manufacturado '"
                            + articuloManufacturado.getDenominacion() + "' aumentado en " + detalle.getCantidad() * amd.getCantidad());
                }
            }
        }
    }


    @Override
    public boolean aplicarDescuento(Pedido pedido) {
        if (pedido.getTipoEnvio() == TipoEnvio.TAKE_AWAY) {
            pedido.setTotal(pedido.getTotal() * 0.9);
            return true;
        }
        return false;
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

    /* @Override
    public Pedido cambiaEstado(Estado estado, Long id) {
        Pedido pedido = getById(id);
        pedido.setEstado(estado);
        return create(pedido);
    } */

    @Override
    public Pedido cambiaEstado(Estado estado, Long id) {
        Pedido pedido = getById(id);
        pedido.setEstado(estado);

        if (estado == Estado.PREPARACION) {
            Factura factura = new Factura();
            factura.setFechaFacturacion(LocalDate.now());
            if (aplicarDescuento(pedido)){
                factura.setMontoDescuento(10);
            }else {
                factura.setMontoDescuento(0);
            }
            factura.setFormaPago(pedido.getFormaPago());
            factura.setTotalVenta(pedido.getTotal());
            pedido.setFactura(factura);

            facturaRepository.save(factura);
        }

        if (estado == Estado.FACTURADO) {

            System.out.println("ESTOY EN FACTURADO");

            try {
                byte[] facturaPdf = facturaService.generarFacturaPDF(pedido);
                String emailCliente = pedido.getCliente().getEmail();
                sendEmailService.sendMail(facturaPdf, emailCliente, null, "Factura de Pedido Nro " + pedido.getId(), "Revise su factura por favor.", "factura-buenSabor_" + pedido.getId() + ".pdf");
            } catch (java.io.IOException e) {
                throw new RuntimeException("Error al generar o enviar la factura: " + e.getMessage(), e);
            }
        }

        if (estado == Estado.RECHAZADO) {
            volverStockAnterior(pedido.getDetallePedidos());
        }

        return pedidoRepository.save(pedido);
    }
   /* @Override
    public Pedido cambiaEstado(Estado estado, Long id) {
        Pedido pedido = getById(id);
        pedido.setEstado(estado);

        System.out.println("Estado del pedido en cambiaEstado: " + pedido.getEstado());

        switch (estado) {
            case PREPARACION:
                Factura factura = new Factura();
                factura.setFechaFacturacion(LocalDate.now());
                if (aplicarDescuento(pedido)){
                    factura.setMontoDescuento(10);
                } else {
                    factura.setMontoDescuento(0);
                }
                factura.setFormaPago(pedido.getFormaPago());
                factura.setTotalVenta(pedido.getTotal());
                pedido.setFactura(factura);

                facturaRepository.save(factura);
                break;

            case FACTURADO:
                System.out.println("ESTOY EN FACTURADO");

                try {
                    byte[] facturaPdf = facturaService.generarFacturaPDF(pedido);
                    String emailCliente = pedido.getCliente().getEmail();
                    sendEmailService.sendMail(facturaPdf, emailCliente, null, "Factura de Pedido Nro " + pedido.getId(), "Revise su factura por favor.", "factura-buenSabor_" + pedido.getId() + ".pdf");
                } catch (java.io.IOException e) {
                    throw new RuntimeException("Error al generar o enviar la factura: " + e.getMessage(), e);
                }
                break;

            case RECHAZADO:
                volverStockAnterior(pedido.getDetallePedidos());
                break;

            case DELIVERY:
                // Lógica para el estado DELIVERY
                // Aquí podrías agregar alguna lógica específica si es necesario, como notificar al cliente que el pedido está en camino.
                System.out.println("El pedido está en proceso de entrega.");
                break;

            case ENTREGADO:
                // Lógica para el estado ENTREGADO
                // Podrías actualizar la fecha de entrega o cualquier otra información relevante.
                pedido.setFechaPedido(LocalDate.now());
                System.out.println("El pedido ha sido entregado.");
                break;

            default:
                break;
        }

        System.out.println("PEDIDO ACTUALIZADO: " + pedido);

        return pedidoRepository.save(pedido);
    } */

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

