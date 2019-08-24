package test.org.rockem.qfetcher;

import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.question.FetchRequest;
import org.rockem.qfetcher.files.ManifestFetcher;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManifestFetcherTest {

    @Test
    void failOnFailureToFetch() {
        assertThrows(
                ManifestFetcher.FailedToFetchManifestException.class,
                () -> new ManifestFetcher(new FetchRequest("http://unknown", asList())).fetch());
    }
}