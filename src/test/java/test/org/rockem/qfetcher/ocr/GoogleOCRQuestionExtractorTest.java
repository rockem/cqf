package test.org.rockem.qfetcher.ocr;

import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.extractor.ocr.GoogleOCRQuestionExtractor;

import static org.assertj.core.api.Assertions.assertThat;

class GoogleOCRQuestionExtractorTest {

    @Test
    void retrieveNoQuestionOnFailureToConnect() {
        assertThat(new GoogleOCRQuestionExtractor("http://no-available", "123").extractFrom(new byte[] {})).isEmpty();
    }


}