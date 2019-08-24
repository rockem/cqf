package org.rockem.qfetcher.files;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestFileFetcher implements FileFetcher {

    private final RestTemplate restTemplate = new RestTemplate();

    public byte[] fetch(String url) {
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
            return response.getBody();
        } catch (RestClientException e) {
            return new byte[] {};
        }
    }
}
