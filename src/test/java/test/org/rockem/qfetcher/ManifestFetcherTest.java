package test.org.rockem.qfetcher;

import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.files.ManifestFetcher;

import static org.junit.jupiter.api.Assertions.*;

class ManifestFetcherTest {

    @Test
    void failOnFailureToFetch() {
        assertThrows(
                ManifestFetcher.FailedToFetchManifestException.class,
                () -> new ManifestFetcher("http://unknown").fetch());
    }
}