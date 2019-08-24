package org.rockem.qfetcher.extractor.ocr;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Optional;

public class OCRResultQuestionExtractor {
    private final JsonObject json;

    public OCRResultQuestionExtractor(String body) {
        json = new Gson().fromJson(body, JsonObject.class);
    }

    public Optional<String> extract() {
        JsonObject firstResponse = json.get("responses").getAsJsonArray().get(0).getAsJsonObject();
        if(firstResponse.get("error") != null) {
            return Optional.empty();
        } else {
            return Optional.of(getFirstDescriptionFrom(firstResponse));
        }
    }

    private String getFirstDescriptionFrom(JsonObject firstResponse) {
        return firstResponse.get("textAnnotations").getAsJsonArray().get(0)
                .getAsJsonObject().get("description").getAsString();
    }
}
