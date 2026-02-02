package com.booktool.backend.integration.openlibrary;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class OpenLibraryClient {

    private static final String BASE_URL = "https://openlibrary.org/isbn/";

    private final RestTemplate restTemplate;

    public OpenLibraryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenLibraryEditionDTO fetchByIsbn(String isbn) {
        // https://openlibrary.org/isbn/9780618343997.json
        String url = BASE_URL + isbn + ".json";

        try {
            return restTemplate.getForObject(url, OpenLibraryEditionDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}