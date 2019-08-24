package org.rockem.qfetcher.extractor;

import java.util.List;

public interface QuestionExtractor {

    List<String> extractFrom(byte[] data);

    String getType();
}
