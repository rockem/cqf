package org.rockem.qfetcher.extractor.ocr;

import com.google.gson.Gson;
import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GoogleOCRQuestionExtractor implements QuestionExtractor {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url;
    private final String key;

    public GoogleOCRQuestionExtractor(
            @Value("${google.ocr.url}") String url,
            @Value("${google.ocr.key}") String key) {
        this.url = url;
        this.key = key;
    }

    public List<String> extractFrom(byte[] data) {
        String encodedImage = new String(Base64.getEncoder().encode(data));
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(
                    url + "?key=" + key,
                    new Gson().toJson(new GoogleOCRRequest(encodedImage)),
                    String.class);
            final Optional<String> question = new OCRResultQuestionExtractor(result.getBody()).extract();
            return question.map(Arrays::asList).orElseGet(Arrays::asList);
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String getType() {
        return "png";
    }
}
