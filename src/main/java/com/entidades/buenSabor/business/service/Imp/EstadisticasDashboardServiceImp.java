package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.service.EstadisticasDashboardService;
import com.entidades.buenSabor.domain.dto.Estadisticas.*;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.*;

import com.entidades.buenSabor.repositories.DetallePedidoRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class EstadisticasDashboardServiceImp implements EstadisticasDashboardService {

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta) {
        return detallePedidoRepository.productosMasVendidos(fechaDesde, fechaHasta);
    }

    @Override
    public List<IngresosDiariosMensualesDto> ingresosDiarioYMensual(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosDiarioYMensual(fechaDesde, fechaHasta);
    }

    /* @Override
    public List<IngresosMensualesDto> ingresosMensuales(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosMensuales(fechaDesde, fechaHasta);
    }

    @Override
    public MontoGananciaDto findCostosGananciasByFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pedidoRepository.findCostosGananciasByFecha(fechaDesde, fechaHasta);
    } */

    @Override
    public List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.findCantidadPedidosPorCliente(fechaDesde,fechaHasta);
    }

    public FechasLimites getFechasLimites(){
        return pedidoRepository.getFechasLimites();
    }

    /* @Override
    public byte[] generarReporteExcel(Date fechaDesde, Date fechaHasta) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ranking de comidas");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Denominacion", "Cantidad Vendida"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        List<RankingProductos> ranking = bestProducts(fechaDesde, fechaHasta);

        int rowNum = 1;
        for (RankingProductos r : ranking){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getDenominacion());
            row.createCell(1).setCellValue(r.getCountVentas());
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        Sheet sheet2 = workbook.createSheet("Ingresos Diarios");

        // Crear encabezado
        Row headerRowIngresosDiarios = sheet2.createRow(0);
        String[] headersIngresosDiarios = {"Fecha", "Ingresos"};
        for (int i = 0; i < headersIngresosDiarios.length; i++) {
            Cell cell = headerRowIngresosDiarios.createCell(i);
            cell.setCellValue(headersIngresosDiarios[i]);
        }

        List<IngresosDiarios> ingresosDiarios = ingresosDiarios(fechaDesde, fechaHasta);

        rowNum = 1;
        for (IngresosDiarios r : ingresosDiarios){
            Row row = sheet2.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getFecha());
            row.createCell(1).setCellValue(r.getIngresos());
        }

        // Autosize columns
        for (int i = 0; i < headersIngresosDiarios.length; i++) {
            sheet2.autoSizeColumn(i);
        }

        Sheet sheet3 = workbook.createSheet("Ingresos Mensuales");

        // Crear encabezado
        Row headerRowIngresosMensuales = sheet3.createRow(0);
        String[] headersIngresosMensuales = {"Mes", "Ingresos"};
        for (int i = 0; i < headersIngresosMensuales.length; i++) {
            Cell cell = headerRowIngresosMensuales.createCell(i);
            cell.setCellValue(headersIngresosMensuales[i]);
        }

        List<IngresosMensuales> ingresosMensuales = ingresosMensuales(fechaDesde, fechaHasta);

        rowNum = 1;
        for (IngresosMensuales r : ingresosMensuales){
            Row row = sheet3.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getMes());
            row.createCell(1).setCellValue(r.getIngresos());
        }

        // Autosize columns
        for (int i = 0; i < headersIngresosMensuales.length; i++) {
            sheet3.autoSizeColumn(i);
        }

        Sheet sheet4 = workbook.createSheet("Pedidos por Cliente");

        // Crear encabezado
        Row headerRowPedidosClientes= sheet4.createRow(0);
        String[] headersPedidoClientes = {"Email cliente", "Cantidad de pedidos"};
        for (int i = 0; i < headersPedidoClientes.length; i++) {
            Cell cell = headerRowPedidosClientes.createCell(i);
            cell.setCellValue(headersPedidoClientes[i]);
        }

        LocalDate dateInicio = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFin = fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<PedidosCliente> pedidosClientes = findCantidadPedidosPorCliente(dateInicio, dateFin);

        rowNum = 1;
        for (PedidosCliente r : pedidosClientes){
            Row row = sheet4.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getEmail());
            row.createCell(1).setCellValue(r.getCantidadPedidos());
        }

        // Autosize columns
        for (int i = 0; i < headersPedidoClientes.length; i++) {
            sheet4.autoSizeColumn(i);
        }

        Sheet sheet5 = workbook.createSheet("Monto de Ganancia");

        // Crear encabezado
        Row headerRowGanancia= sheet5.createRow(0);
        String[] headersGanancia = {"Costo", "Ganancia", "Resultado"};
        for (int i = 0; i < headersGanancia.length; i++) {
            Cell cell = headerRowGanancia.createCell(i);
            cell.setCellValue(headersGanancia[i]);
        }

        CostoGanancia costoGanancias = findCostosGananciasByFecha(dateInicio, dateFin);

        rowNum = 1;
        Row row = sheet5.createRow(rowNum++);
        row.createCell(0).setCellValue(costoGanancias.getCostos());
        row.createCell(1).setCellValue(costoGanancias.getGanancias());
        row.createCell(2).setCellValue(costoGanancias.getResultado());


        // Autosize columns
        for (int i = 0; i < headersPedidoClientes.length; i++) {
            sheet5.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    } */

    //EXCEL RANKING
    @Override
    public byte[] generarExcelRanking(Date fechaDesde, Date fechaHasta) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ranking de Productos");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Nombre", "Cantidad Vendida"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        List<RankingProductosDto> ranking = productosMasVendidos(fechaDesde, fechaHasta);

        int rowNum = 1;
        for (RankingProductosDto r : ranking) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getDenominacion());
            row.createCell(1).setCellValue(r.getCantidad());
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    // EXCEL INGRESOS
    @Override
    public byte[] generarExcelIngresos(Date fechaDesde, Date fechaHasta) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ingresos Diarios y Mensuales");

        // Crear encabezado
        Row headerRowIngresosDiarios = sheet.createRow(0);
        String[] headersIngresosDiarios = {"Fecha", "Ingresos"};
        for (int i = 0; i < headersIngresosDiarios.length; i++) {
            Cell cell = headerRowIngresosDiarios.createCell(i);
            cell.setCellValue(headersIngresosDiarios[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        List<IngresosDiariosMensualesDto> ingresosDiariosMensuales = ingresosDiarioYMensual(fechaDesde, fechaHasta);

        int rowNum = 1;
        for (IngresosDiariosMensualesDto r : ingresosDiariosMensuales) {
            Row row = sheet.createRow(rowNum++);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(r.getDia());
            dateCell.setCellStyle(dateCellStyle);

            row.createCell(1).setCellValue("$ " + r.getIngresos());
        }

        // Autosize columns
        for (int i = 0; i < headersIngresosDiarios.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    // EXCEL CLIENTES
    @Override
    public byte[] generarExcelClientes(Date fechaDesde, Date fechaHasta) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pedidos por Clientes");

        // Crear encabezado
        Row headerRowPedidosClientes= sheet.createRow(0);
        String[] headersPedidoClientes = {"Nombre y Apellido", "Cantidad de pedidos"};
        for (int i = 0; i < headersPedidoClientes.length; i++) {
            Cell cell = headerRowPedidosClientes.createCell(i);
            cell.setCellValue(headersPedidoClientes[i]);
        }

        List<CantidadPedidosClienteDto> pedidosClientes = findCantidadPedidosPorCliente(fechaDesde, fechaHasta);

        int rowNum = 1;
        for (CantidadPedidosClienteDto r : pedidosClientes){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getNombre() + " " + r.getApellido());
            row.createCell(1).setCellValue(r.getCantidad_pedidos());
        }

        // Autosize columns
        for (int i = 0; i < headersPedidoClientes.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }
}
