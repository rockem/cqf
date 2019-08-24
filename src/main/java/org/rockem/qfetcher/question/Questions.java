package org.rockem.qfetcher.question;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Questions {

    private List<Question> questions = new ArrayList<>();

    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Question {
        private String value;
        private String source;

        public Question(String value, String source) {
            this.value = value;
            this.source = source;
        }
    }
}
