package com.sistemasdistr.basico.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiTestService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.flask.base-url:http://api-flask:5000}")
    private String flaskBaseUrl;

    public ApiTestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String probarLlamadaSimple() {
        try {
            return llamarApiExterna();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API externa devolvió un error 404: recurso no encontrado.";
            }
            return "La API externa devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API externa. Detalle: " + e.getMessage();
        }
    }

    private String llamarApiExterna() {
        String url = flaskBaseUrl + "/saludo";
        return restTemplate.getForObject(url, String.class);
    }

    public String probarErrorArchivo() {
        try {
            String url = flaskBaseUrl + "/archivo-error";
            return restTemplate.getForObject(url, String.class);
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al abrir un archivo.";
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarErrorBaseDatos() {
        try {
            String url = flaskBaseUrl + "/db-error";
            return restTemplate.getForObject(url, String.class);
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error de acceso a la base de datos.";
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarPokemon() {
        try {
            String url = flaskBaseUrl + "/pokemon/pikachu";
            String respuesta = restTemplate.getForObject(url, String.class);

            JsonNode json = objectMapper.readTree(respuesta);

            String nombre = json.path("nombre").asText("desconocido");
            String id = json.path("id").asText("desconocido");
            String altura = json.path("altura").asText("desconocido");
            String peso = json.path("peso").asText("desconocido");

            return "Pokémon consultado correctamente -> Nombre: " + nombre
                    + ", ID: " + id
                    + ", Altura: " + altura
                    + ", Peso: " + peso + ".";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API Flask informó de que el Pokémon no existe.";
            }
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al consultar Pokémon.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        } catch (Exception e) {
            return "No se pudo interpretar correctamente la respuesta de la API Flask.";
        }
    }

    public String probarPokemonError() {
        try {
            String url = flaskBaseUrl + "/pokemon-error";
            String respuesta = restTemplate.getForObject(url, String.class);
            return "Respuesta inesperada: " + respuesta;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API Flask informó de que el Pokémon solicitado no existe.";
            }
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al consultar Pokémon.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarBaseDatosOk() {
        try {
            String url = flaskBaseUrl + "/db-ok";
            String respuesta = restTemplate.getForObject(url, String.class);
            return "Consulta de base de datos realizada correctamente: " + respuesta;
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al acceder a la base de datos.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarBaseDatosErrorReal() {
        try {
            String url = flaskBaseUrl + "/db-error-real";
            String respuesta = restTemplate.getForObject(url, String.class);
            return "Respuesta inesperada: " + respuesta;
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error real al consultar la base de datos.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }
}