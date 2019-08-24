package org.rockem.qfetcher.extractor.ocr;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GoogleOCRRequest {

    private final List<OCRRequest> requests;

    public GoogleOCRRequest(String imageInBase64) {
        requests = asList(new OCRRequest(imageInBase64));
    }

    public static class OCRRequest {
        private Map<String, String> image;
        private List<Map<String, String>> features = asList(ImmutableMap.of("type", "TEXT_DETECTION"));

        public OCRRequest(String imageInBase64) {
            image = ImmutableMap.of("content", imageInBase64);
        }
    }
}
