package org.rockem.qfetcher.extractor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvQuestionExtractor implements QuestionExtractor {
    @Override
    public List<String> extractFrom(byte[] data) {
        return null;
    }

    @Override
    public String getType() {
        return "csv";
    }
}
