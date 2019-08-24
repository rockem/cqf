package org.rockem.qfetcher.extractor.json;

import com.google.gson.Gson;
import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Component
public class JsonQuestionExtractor implements QuestionExtractor {
    @Override
    public List<String> extractFrom(byte[] data) {
        JsonQuestions questions = new Gson().fromJson(new String(data), JsonQuestions.class);
        if(questions == null || questions.getQuestions() == null) {
            return asList();
        }
        return questions.getQuestions().stream().map(JsonQuestions.Question::getText).collect(toList());
    }

    @Override
    public String getType() {
        return "json";
    }
}
