package test.org.rockem.qfetcher;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.files.FileFetcher;
import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.rockem.qfetcher.Questions.Question;
import org.rockem.qfetcher.QuestionsFetcher;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionsFetcherTest {

    private final FileFetcher fileFetcher = mock(FileFetcher.class);
    private final QuestionsFetcher questionsFetcher =
            new QuestionsFetcher(fileFetcher, asList(new SimpleQuestionExtractor()));

    @Test
    void fetchQuestionsFromMultipleDocuments() {
        Map<String, String> files = ImmutableMap.of(
                "file://kuku.txt", "kuku de loco",
                "file://popo.txt", "popo de kuku"
        );
        final List<Question> questions = fetchFrom(files);
        verifyQuestions(questions, files);
    }

    private List<Question> fetchFrom(Map<String, String> urlToContent) {
        urlToContent.forEach((u, c) ->
                when(fileFetcher.fetch(u)).thenReturn(c.getBytes())
        );
        return questionsFetcher.fetchFrom(newArrayList(urlToContent.keySet()));
    }

    private void verifyQuestions(List<Question> questions, Map<String, String> files) {
        assertThat(questions).hasSize(files.keySet().size());
        files.values().forEach(q ->
            assertThat(questions).contains(new Question(q, "txt"))
        );
    }

    @Test
    void ignoreUnsupportedExtensions() {
        Map<String, String> files = ImmutableMap.of(
                "file://unsupported.psd", "par par par"
        );
        assertThat(fetchFrom(files)).isEmpty();
    }

    private static class SimpleQuestionExtractor implements QuestionExtractor {

        @Override
        public List<String> extractFrom(byte[] data) {
            return asList(new String(data).split("\n"));
        }

        @Override
        public String getType() {
            return "txt";
        }
    }
}