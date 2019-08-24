package test.org.rockem.qfetcher.ocr;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.extractor.ocr.OCRResultQuestionExtractor;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OCRResultQuestionExtractorTest {

    public static final Map<String, Object> RESULT_WITH_ERROR = ImmutableMap.of(
            "responses", Arrays.asList(
                    ImmutableMap.of("error", ImmutableMap.of("code", 4))));
    public static final Map<String, Object> HELLO_QUESTION = ImmutableMap.of(
            "responses", Arrays.asList(
                    ImmutableMap.of("textAnnotations", Arrays.asList(ImmutableMap.of("description", "hello")))));

    @Test
    void emptyIfRetrievedAnErrorFromGoogle() {
        assertThat(new OCRResultQuestionExtractor(new Gson().toJson(RESULT_WITH_ERROR)).extract()).isNotPresent();
    }

    @Test
    void retrieveQuestionIfAvailable() {
        assertThat(new OCRResultQuestionExtractor(new Gson().toJson(HELLO_QUESTION)).extract()).get().isEqualTo("hello");
    }
}