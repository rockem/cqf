package test.org.rockem.qfetcher.extractor.csv;

import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.extractor.csv.CsvQuestionExtractor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvQuestionExtractorTest {

    public static final String CVS = "" +
            "id, text, field\n" +
            "3, what day is today?, general\n" +
            "19, what is your name?, personal\n";

    @Test
    void ignoreCorruptedFile() {
        assertThat(questionsListFrom("sdf$sdf52")).isEmpty();
    }

    private List<String> questionsListFrom(String csv) {
        return new CsvQuestionExtractor().extractFrom(csv.getBytes());
    }

    @Test
    void retrieveQuestionsFromCsv() {
        assertThat(questionsListFrom(CVS)).containsOnly("what day is today?", "what is your name?");
    }
}