package e2e.test.org.rockem.qfetcher;

import e2e.test.org.rockem.qfetcher.support.AppDriver;
import e2e.test.org.rockem.qfetcher.support.AssetsProvider;
import e2e.test.org.rockem.qfetcher.support.ManifestProvider;
import e2e.test.org.rockem.qfetcher.support.ocr.GoogleOCR;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static e2e.test.org.rockem.qfetcher.support.ManifestProvider.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {

    final AppDriver app = new AppDriver();
    final GoogleOCR ocr = new GoogleOCR();

    @BeforeAll
    void startMocks() {
        ManifestProvider.start();
        AssetsProvider.start();
        ocr.start();
    }

    @Test
    void retrieveNoQuestionsForInvalidQuestionsFile() throws Exception {
        app.fetchFor(INVALID_IMAGE_FILE);
        ocr.receivedImage(firstFileFor(INVALID_IMAGE_FILE));
        app.retrievedNoQuestions();
    }

    @Test
    void retrieveQuestionFromImageFile() throws Exception {
        app.fetchFor(VALID_IMAGE_FILE);
        app.retrievedQuestion("Chegg\nR\n", "png");
    }

    @Test
    void filterGivenExtensions() throws Exception {
        app.fetchFor(VALID_IMAGE_FILE, "csv");
        app.retrievedNoQuestions();
    }
}
