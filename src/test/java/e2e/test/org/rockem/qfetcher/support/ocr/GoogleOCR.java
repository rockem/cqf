package e2e.test.org.rockem.qfetcher.support.ocr;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import e2e.test.org.rockem.qfetcher.support.WireMockRecorder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


public class GoogleOCR {

    private final WireMockServer wireMock = new WireMockServer(options().port(8090));

    public void start() {
        wireMock.start();
    }

    public void receivedImage(String imageName) throws IOException, URISyntaxException {
        wireMock.verify(postRequestedFor(urlPathMatching("/v1/images:annotate"))
                .withRequestBody(matchingJsonPath("$.requests.[0].image[?(@.content == \"" + FileUtil.getAsBase64(imageName) + "\")]")));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new WireMockRecorder("https://vision.googleapis.com")
                .record(
                        createOCRRequestFor("invalid_image.png"),
                        createOCRRequestFor("chegg.png"));
    }

    private static HttpUriRequest createOCRRequestFor(final String imageName) throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(
                "http://localhost:8080/v1/images:annotate?key=" + System.getenv("OCR_API_KEY"));
        httpPost.setEntity(new StringEntity(
                new Gson().toJson(new GoogleOCRRequestCreator("__files/" + imageName).create()), "UTF-8"));
        return httpPost;
    }
}
