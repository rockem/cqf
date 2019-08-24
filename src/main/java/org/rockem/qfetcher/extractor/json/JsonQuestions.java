package org.rockem.qfetcher.extractor.json;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class JsonQuestions {

    private List<Question> questions;

    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Question {
        private Integer id;
        private String text;
        private String field;
    }
}
