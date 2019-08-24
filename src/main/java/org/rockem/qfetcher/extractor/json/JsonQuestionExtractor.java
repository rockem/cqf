package org.rockem.qfetcher.extractor.json;

import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonQuestionExtractor implements QuestionExtractor {
    @Override
    public List<String> extractFrom(byte[] data) {
        return null;
    }

    @Override
    public String getType() {
        return "json";
    }
}
