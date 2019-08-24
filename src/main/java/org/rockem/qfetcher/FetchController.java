package org.rockem.qfetcher;

import org.rockem.qfetcher.files.ManifestFetcher;
import org.rockem.qfetcher.files.RestFileFetcher;
import org.rockem.qfetcher.extractor.QuestionExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fetch")
public class FetchController {

    private final QuestionsFetcher questionsFetcher;

    public FetchController(List<QuestionExtractor> extractors) {
        this.questionsFetcher = new QuestionsFetcher(new RestFileFetcher(), extractors);
    }

    @PostMapping
    public ResponseEntity<Questions> fetch(@RequestBody FetchRequest fetchRequest) {
        List<String> docs = new ManifestFetcher(fetchRequest.getManifest()).fetch();
        return ResponseEntity.ok(new Questions(questionsFetcher.fetchFrom(docs)));
    }

}
