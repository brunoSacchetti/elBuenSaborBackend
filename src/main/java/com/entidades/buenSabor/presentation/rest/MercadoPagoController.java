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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pagoMercadoPago")
public class MercadoPagoController {

    @Value("${mercado.pago.access.token}")
    private String accessToken;
    @PostMapping
    public MpPreference createPreference(@RequestBody Pedido pedido) {
        try {

            //Asignamos el token defindo en properties
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(pedido.getId().toString())
                    .title("Compra realizada en el Buen Sabor")
                    .description("Pedido realizado - Buen Sabor - 2024")
                    .quantity(1)
                    .currencyId("ARS")
                    .unitPrice(new BigDecimal(pedido.getTotal()))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/pedidos")
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
