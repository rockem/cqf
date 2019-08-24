package org.rockem.qfetcher;

import org.rockem.qfetcher.files.FileFetcher;
import org.rockem.qfetcher.extractor.QuestionExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

public class QuestionsFetcher {

    private final FileFetcher fileFetcher;
    private final Map<String, QuestionExtractor> extractorMap;

    public QuestionsFetcher(FileFetcher fileFetcher, List<QuestionExtractor> questionExtractors) {
        this.fileFetcher = fileFetcher;
        extractorMap = questionExtractors.stream().collect(toMap(QuestionExtractor::getType, e -> e));
    }

    public List<Questions.Question> fetchFrom(List<String> docs) {
        List<Questions.Question> questions = new ArrayList<>();
        docs.forEach(doc -> {
            byte[] file = fileFetcher.fetch(doc);
            final QuestionExtractor questionExtractor = questionExtractorFor(doc);
            questionExtractor.extractFrom(file).forEach(q ->
                    questions.add(new Questions.Question(q, questionExtractor.getType())));
        });
        return questions;
    }

    private QuestionExtractor questionExtractorFor(String doc) {
        return extractorMap.getOrDefault(extensionFor(doc), new DummyQuestionExtractor());
    }

    private String extensionFor(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    private class DummyQuestionExtractor implements QuestionExtractor {
        @Override
        public List<String> extractFrom(byte[] data) {
            return asList();
        }

        @Override
        public String getType() {
            return null;
        }
    }
}
