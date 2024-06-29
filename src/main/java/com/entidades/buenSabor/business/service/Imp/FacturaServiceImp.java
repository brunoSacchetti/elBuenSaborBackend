package com.entidades.buenSabor.business.service.Imp;


import com.entidades.buenSabor.business.service.Base.BaseServiceImp;
import com.entidades.buenSabor.business.service.FacturaService;
import com.entidades.buenSabor.domain.entities.DetallePedido;
import com.entidades.buenSabor.domain.entities.Factura;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.domain.enums.TipoEnvio;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Service
public class FacturaServiceImp extends BaseServiceImp<Factura,Long> implements FacturaService {
    /* @Override
    public byte[] generarFacturaPDF(Pedido pedido) throws IOException {

        ClassPathResource pdfTemplateResource = new ClassPathResource("template/factura.pdf");

        InputStream inputStream = pdfTemplateResource.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(inputStream);
        PdfWriter writer = new PdfWriter(baos);


        // Crear documento PDF
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        // Obtener la primera página del PDF
        PdfPage page = pdfDoc.getFirstPage();

        // Obtener el lienzo de la página para dibujar
        PdfCanvas canvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER);

        //x=derecha,izq y=arriba abajo
        canvas.beginText()
                .setFontAndSize(font, 15)
                .moveText(120, 633)
                .showText(" " + pedido.getCliente().getNombre()+ " " +pedido.getCliente().getApellido() )
                .endText();
        //domicilo
        canvas.beginText()
                .setFontAndSize(font, 15)
                .moveText(120, 610)
                .showText(String.valueOf(pedido.getDomicilio().getCalle()+" " + pedido.getDomicilio().getNumero()))
                .endText();
        //telefono
        canvas.beginText()
                .setFontAndSize(font, 15)
                .moveText(400, 611)
                .showText(pedido.getCliente().getTelefono())
                .endText();
        //fecha
        canvas.beginText()
                .setFontAndSize(font, 15)
                .moveText(115, 760)
                .showText(String.valueOf(pedido.getFechaPedido()))
                .endText();
        //detalles
        int y = 540; // Posición inicial en Y para los detalles usar siempre el y
        Set<DetallePedido> detalles = pedido.getDetallePedidos();
        for (DetallePedido detalle : detalles) {
            //cantidad
            canvas.beginText()
                    .setFontAndSize(font, 15)
                    .moveText(90, y)
                    .showText(String.valueOf(detalle.getCantidad()))
                    .endText();
            //descripcion
            canvas.beginText()
                    .setFontAndSize(font, 15)
                    .moveText(123, y)
                    .showText( detalle.getArticulo().getDenominacion())
                    .endText();
            //precio
            canvas.beginText()
                    .setFontAndSize(font, 15)
                    .moveText(460, y)
                    .showText(String.valueOf(detalle.getArticulo().getPrecioVenta()))
                    .endText();
            y -= 25; // Decrementar la posición en Y para el siguiente detalle
        }
        //total
        canvas.beginText()
                .setFontAndSize(font, 15)
                .moveText(460, 120)
                .showText(String.valueOf(pedido.getTotal()))
                .endText();

        // Cerrar el documento
        pdfDoc.close();

        return baos.toByteArray();
    } */

    @Override
    public byte[] generarFacturaPDF(Pedido pedido) throws IOException {
        ClassPathResource pdfTemplateResource = new ClassPathResource("plantillaFactura/factura-buenSabor.pdf");

        InputStream inputStream = pdfTemplateResource.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(inputStream);
        PdfWriter writer = new PdfWriter(baos);

        // Crear documento PDF
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        // Obtener la primera página del PDF
        PdfPage page = pdfDoc.getFirstPage();

        // Obtener el lienzo de la página para dibujar
        PdfCanvas canvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER_OBLIQUE);

        // Fecha - Arriba a la izquierda, por encima de la línea
        canvas.beginText()
                .setFontAndSize(font, 11)
                .moveText(35, 713) // 750 Ajusta las coordenadas según sea necesario
                .showText("Fecha: " + pedido.getFechaPedido())
                .endText();

        // Nombre y Apellido - Debajo de la línea, arriba a la izquierda
        canvas.beginText()
                .setFontAndSize(font, 9)
                .moveText(35, 670) // Ajusta las coordenadas según sea necesario
                .showText("Cliente: " + pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido())
                .endText();

        // Teléfono - Debajo de la línea, arriba a la izquierda
        canvas.beginText()
                .setFontAndSize(font, 9)
                .moveText(35, 655) // Ajusta las coordenadas según sea necesario
                .showText("Teléfono: " + pedido.getCliente().getTelefono())
                .endText();

        // Email - Debajo de la línea, arriba a la izquierda
        canvas.beginText()
                .setFontAndSize(font, 9)
                .moveText(35, 640) // Ajusta las coordenadas según sea necesario
                .showText("Email: " + pedido.getCliente().getEmail())
                .endText();

        // Mostrar descuento del 10% si el tipo de envío es TAKE_AWAY
        if (pedido.getTipoEnvio() == TipoEnvio.TAKE_AWAY) {
            canvas.beginText()
                    .setFontAndSize(font, 9)
                    .moveText(430, 180) // Ajusta las coordenadas según sea necesario
                    .showText("Descuento aplicado del 10%")
                    .endText();
        }

        // Detalles de los artículos
        int y = 500; // Posición inicial en Y para los detalles, ajustar según la plantilla
        Set<DetallePedido> detalles = pedido.getDetallePedidos();
        for (DetallePedido detalle : detalles) {
            // Cantidad
            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(180, y) // Ajusta las coordenadas según sea necesario
                    .showText(String.valueOf(detalle.getCantidad()))
                    .endText();
            // Descripción
            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(250, y) // Ajusta las coordenadas según sea necesario
                    .showText(detalle.getArticulo().getDenominacion())
                    .endText();
            // Precio Unitario
            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(455, y) // Ajusta las coordenadas según sea necesario
                    .showText(String.valueOf(detalle.getArticulo().getPrecioVenta()))
                    .endText();
            // Subtotal
            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(530, y) // Ajusta las coordenadas según sea necesario
                    .showText(String.valueOf(detalle.getCantidad() * detalle.getArticulo().getPrecioVenta()))
                    .endText();
            y -= 25; // Decrementar la posición en Y para el siguiente detalle
        }

        // Total del pedido
        canvas.beginText()
                .setFontAndSize(font, 13)
                .moveText(518, 210) // Ajusta las coordenadas según sea necesario
                .showText(String.valueOf(pedido.getTotal()))
                .endText();

        // Cerrar el documento
        pdfDoc.close();

        return baos.toByteArray();
    }

}