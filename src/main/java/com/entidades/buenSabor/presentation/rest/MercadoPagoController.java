package com.entidades.buenSabor.presentation.rest;


import com.entidades.buenSabor.domain.entities.MpPreference;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apiMp")
@CrossOrigin("*")
public class MercadoPagoController {

    @PostMapping
    public MpPreference getList(@RequestBody Pedido pedido) {

        /*
        *  List<PreferenceItemRequest> items = new ArrayList<>();
        for (PedidoDetalleDto detalle : pedido.getPedidosDetalle()) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(detalle.getTitle())
                    .quantity(detalle.getQuantity())
                    .unitPrice(new BigDecimal(detalle.getUnitPrice()))
                    .build();
            items.add(itemRequest);
        }
        *
        * */



        try {
            MercadoPagoConfig.setAccessToken("");

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(pedido.getId().toString())
                    .title("compra producto")
                    .description("Pedido realizado desde el carrito de compras")
                    .pictureUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwIO85PD8j6F_gTdPtZC20xoE6MOVD0dcR_Q&s")
                    .quantity(1)
                    .currencyId("ARG")
                    .unitPrice(new BigDecimal(pedido.getTotal()))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173")
                    .pending("http://localhost:5173")
                    .failure("http://localhost:5173")
                    .build();


            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();


            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);


            MpPreference mpPreference = new MpPreference();
            mpPreference.setStatusCode(preference.getResponse().getStatusCode());
            mpPreference.setId(preference.getId());
            return mpPreference;


        } catch (MPException e) {
            throw new RuntimeException(e);

        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }
}
