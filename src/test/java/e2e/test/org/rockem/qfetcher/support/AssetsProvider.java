package e2e.test.org.rockem.qfetcher.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import e2e.test.org.rockem.qfetcher.support.ocr.FileUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class AssetsProvider {
    public static final String ASSETS_PATH = "/assets";
    public static final String ASSETS_URL = "http://localhost:8091" + ASSETS_PATH;

    private final static WireMockServer wireMock = new WireMockServer(
            options().port(8091).extensions(new AssetTransformer()));


    public static void start() {
        wireMock.start();
        wireMock.givenThat(get(urlPathMatching(ASSETS_PATH + "/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/octet-stream")
                        .withTransformers("asset-transformer")));
    }

    private static class AssetTransformer extends ResponseDefinitionTransformer {
        @Override
        public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
            String path = request.getUrl();
            String manifest = path.substring(path.lastIndexOf("/") + 1);
            try {
                return new ResponseDefinitionBuilder()
                        .withHeader("Content-Type", "application/octet-stream")
                        .withStatus(200)
                        .withBody(contentOf(manifest))
                        .build();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        private byte[] contentOf(String name) throws IOException, URISyntaxException {
            return FileUtil.readFile("__files/" + name);
        }

        @Override
        public String getName() {
            return "asset-transformer";
        }

    }
}
