package e2e.test.org.rockem.qfetcher.support;


import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.rockem.qfetcher.Application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppDriver {

    public static final String APP_DOMAIN = "http://localhost:8080";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private JsonObject retrievedQuestions;


    public AppDriver() {
        Application.main();
    }

    public void fetchFor(String manifest, String... exts) throws IOException {
        CloseableHttpResponse response = httpClient.execute(createFetchRequestFor(manifest, exts));
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        retrievedQuestions = new Gson().fromJson(EntityUtils.toString(response.getEntity()), JsonObject.class);
    }

    private HttpPost createFetchRequestFor(String manifest, String[] exts) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(APP_DOMAIN + "/api/v1/fetch");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(createRequestBodyFor(manifest, exts)));
        return httpPost;
    }

    private String createRequestBodyFor(String manifest, String[] exts) {
        return new Gson().toJson(
                ImmutableMap.of(
                        "manifest", ManifestProvider.URL + "/" + manifest,
                        "filter", exts));
    }

    public void retrievedNoQuestions() {
        assertThat(retrievedQuestions.get("questions").getAsJsonArray()).isEmpty();
    }

    public void retrievedQuestion(String question, String extension) {
        assertThat(firstRetrievedQuestion().get("value").getAsString()).isEqualTo(question);
        assertThat(firstRetrievedQuestion().get("source").getAsString()).isEqualTo(extension);
    }

    private JsonObject firstRetrievedQuestion() {
        return retrievedQuestions.get("questions").getAsJsonArray().get(0)
                .getAsJsonObject();
    }
}
