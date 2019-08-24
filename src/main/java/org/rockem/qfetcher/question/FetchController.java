package org.rockem.qfetcher.question;

import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.rockem.qfetcher.files.ManifestFetcher;
import org.rockem.qfetcher.files.RestFileFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/v1")
public class FetchController {

    private final ExecutorService executor = Executors.newFixedThreadPool(100);
    private final QuestionsFetcher questionsFetcher;
    private final Integer timeout;

    public FetchController(List<QuestionExtractor> extractors, @Value("${manifest.timeout:30}") Integer timeout) {
        this.questionsFetcher = new QuestionsFetcher(new RestFileFetcher(), extractors);
        this.timeout = timeout;
    }

    @PostMapping("/fetch")
    public ResponseEntity<Questions> fetch(@RequestBody FetchRequest fetchRequest) {
        Future<Questions> job = executor.submit(() -> {
            List<String> docs = new ManifestFetcher(fetchRequest).fetch();
            return new Questions(questionsFetcher.fetchFrom(docs));
        });
        return getQuestions(job);

    }

    private ResponseEntity<Questions> getQuestions(Future<Questions> job) {
        try {
            Questions questions = job.get(timeout, TimeUnit.SECONDS);
            if(questions == null) {
                throw new FetchManifestQuestionsTimeoutException("Timeout on fetching manifest");
            }
            return ResponseEntity.ok(questions);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new FetchManifestQuestionsTimeoutException("Failed to fetch manifest", e);
        }
    }

    @ResponseStatus(value= HttpStatus.REQUEST_TIMEOUT, reason = "Timed out while fetching manifest questions")
    public static class FetchManifestQuestionsTimeoutException extends RuntimeException {
        public FetchManifestQuestionsTimeoutException(String message) {
            super(message);
        }

        public FetchManifestQuestionsTimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
