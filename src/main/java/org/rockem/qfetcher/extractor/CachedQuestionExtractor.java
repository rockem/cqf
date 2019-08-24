package org.rockem.qfetcher.extractor;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CachedQuestionExtractor implements QuestionExtractor {

    private final QuestionExtractor delegate;
    private final Cache<ByteBuffer, List<String>> cache;

    public CachedQuestionExtractor(QuestionExtractor questionExtractor) {
        this.delegate = questionExtractor;
        cache = createCache();
    }

    private Cache<ByteBuffer, List<String>> createCache() {
        return new Cache2kBuilder<ByteBuffer, List<String>>() {}
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .resilienceDuration(30, TimeUnit.SECONDS)
                .loader(this::extractQuestions)
                .build();
    }

    private List<String> extractQuestions(ByteBuffer byteBuffer) {
        return delegate.extractFrom(byteBuffer.array());
    }

    @Override
    public List<String> extractFrom(byte[] data) {
        return cache.get(ByteBuffer.wrap(data));
    }

    @Override
    public String getType() {
        return delegate.getType();
    }
}
