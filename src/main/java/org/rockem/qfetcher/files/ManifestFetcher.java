package org.rockem.qfetcher.files;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class ManifestFetcher {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String manifestUrl;

    public ManifestFetcher(String manifestUrl) {
        this.manifestUrl = manifestUrl;
    }

    public List<String> fetch() {
        try {
            ResponseEntity<String> manifest = restTemplate.getForEntity(manifestUrl, String.class);
            return asList(manifest.getBody().split("\n"));
        } catch(RestClientException e) {
            throw new FailedToFetchManifestException(format("Failed to fetch: %s", manifestUrl), e);
        }
    }

    public static class FailedToFetchManifestException extends RuntimeException {
        public FailedToFetchManifestException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
