package com.entidades.buenSabor.presentation.rest;

import com.entidades.buenSabor.business.service.EstadisticasDashboardService;
import com.entidades.buenSabor.domain.dto.Estadisticas.FechasLimites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/estadisticasDashboard")
@CrossOrigin("*")
public class EstadisticasDashboardController {

    @Autowired
    EstadisticasDashboardService estadisticas;

    @GetMapping("/rankingProductos")
    public ResponseEntity<?> rankingProductos (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return ResponseEntity.ok(estadisticas.productosMasVendidos(fechaDesde, fechaHasta));
    }

   @GetMapping("/recaudacionesDiarias")
    public ResponseEntity<?> recaudacionesDiarias (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return ResponseEntity.ok(estadisticas.ingresosDiarioYMensual(fechaDesde, fechaHasta));
    }

    /* @GetMapping("/recaudacionesMensuales")
    public ResponseEntity<?> recaudacionesMensuales (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return ResponseEntity.ok(estadisticas.ingresosMensuales(fechaDesde, fechaHasta));
    } */

    @GetMapping("/resultadoEconomico")
    public ResponseEntity<?> costosGanancias (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return ResponseEntity.ok(estadisticas.findCostosGananciasByFecha(fechaDesde, fechaHasta));
    }

    @GetMapping("/pedidosCliente")
    public ResponseEntity<?> pedidosCliente (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return ResponseEntity.ok(estadisticas.findCantidadPedidosPorCliente(fechaDesde, fechaHasta));
    }

    @GetMapping("/limite-fechas")
    public FechasLimites getFechasLimites() {
        return estadisticas.getFechasLimites();
    }

    @GetMapping("/excel/ranking-productos")
    public ResponseEntity<?> excelRanking (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException {
        byte[] excelContent = estadisticas.generarExcelRanking(fechaDesde, fechaHasta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=ranking-productos.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }

    @GetMapping("/excel/ingresos")
    public ResponseEntity<?> excelIngresos (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException {
        byte[] excelContent = estadisticas.generarExcelIngresos(fechaDesde, fechaHasta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=ingresos-diarios-mensuales.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }

    @GetMapping("/excel/pedidos-clientes")
    public ResponseEntity<?> excelClientesPedidos (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException {
        byte[] excelContent = estadisticas.generarExcelClientes(fechaDesde, fechaHasta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=pedidos-por-cliente.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }

    @GetMapping("/excel/resultado-economico")
    public ResponseEntity<?> excelresultadoEconomico (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException {
        byte[] excelContent = estadisticas.generarExcelResultadoEconomico(fechaDesde, fechaHasta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=resultado-economico.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }

    @GetMapping("/excel/pedidos")
    public ResponseEntity<?> excelPedidos (
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException {
        byte[] excelContent = estadisticas.generarExcelPedidos(fechaDesde, fechaHasta);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=pedidos.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }

}
