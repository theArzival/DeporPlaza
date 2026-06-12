package com.Sistema.DeporPlaza.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DniRestController {

    @PostMapping("/buscarDni")
    // Response para que no devuelva un HTML
    public Map<String, String> buscarDni(@RequestParam String dni) {
        // nuestro token
        String API_TOKEN = "096d36124236181956d4dfc000abb2aebfa99f0e74304a25507c1a0b5418ec88";
        // IY1VdrJBvTQhfjcmr7guAMbuclJ45NNSvN5qXVi2X3xkaO0t8NOdNmf877If
        // url del api
        String API_URL = "https://apiperu.dev/api/dni";
        // pondre el resultado en un clave valor
        Map<String, String> resultado = new HashMap<>();

        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("dni", dni);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class);
            // data contiene datos del dni
            /*
             * Esto son sus keys
             * "data": {
             * "numero": "7**7**53",
             * "nombre_completo": "PE** A**LAR, CA***INA",
             * "nombres": "CA***INA",
             * "apellido_paterno": "PE**",
             * "apellido_materno": "A**LAR",
             * "codigo_verificacion": "8"
             * }
             */
            Map<String, String> data = (Map<String, String>) response.getBody().get("data");
            resultado.put("dni", data.get("numero"));
            resultado.put("nombres", data.get("nombres"));
            resultado.put("apellidos",
                    data.get("apellido_paterno") + " " + data.get("apellido_materno"));

        } catch (Exception e) {

            resultado.put("error", "No encontrado el DNI");

        }

        return resultado;
    }
}
