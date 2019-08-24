package org.rockem.qfetcher.extractor.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Component
public class CsvQuestionExtractor implements QuestionExtractor {
    @Override
    public List<String> extractFrom(byte[] data) {
        try {
            return createCsvReader(data).readAll().stream()
                    .map(l -> l[1].trim()).collect(toList());
        } catch (IOException e) {
            return asList();
        }
    }

    private CSVReader createCsvReader(byte[] data) {
        return new CSVReaderBuilder(new StringReader(new String(data)))
                .withSkipLines(1)
                .build();
    }

    @Override
    public String getType() {
        return "csv";
    }
}
