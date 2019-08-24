package test.org.rockem.qfetcher.extractor.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.extractor.json.JsonQuestionExtractor;
import org.rockem.qfetcher.extractor.json.JsonQuestions;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class JsonQuestionExtractorTest {

    @Test
    void retrieveNoQuestionIfProtocolMismatch() {
        assertThat(extractFrom("{\"stam\":123}")).isEmpty();
    }

    private List<String> extractFrom(String json) {
        return new JsonQuestionExtractor().extractFrom(json.getBytes());
    }

    @Test
    void retrieveQuestionFromJson() {
        JsonQuestions questions = new JsonQuestions(asList(
                JsonQuestions.Question.builder().text("question 111?").build(),
                JsonQuestions.Question.builder().text("question 222?").build()
        ));

        List<String> result = extractFrom(new Gson().toJson(questions));
        assertThat(result).containsOnly("question 111?", "question 222?");
    }

    private List<String> textsOf(JsonQuestions questions) {
        return questions.getQuestions().stream().map(JsonQuestions.Question::getText).collect(toList());
    }
}