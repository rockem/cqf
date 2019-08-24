package e2e.test.org.rockem.qfetcher.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static e2e.test.org.rockem.qfetcher.support.AssetsProvider.ASSETS_URL;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;


public class ManifestProvider {
    public static final String INVALID_IMAGE_FILE = "invalid_image";
    public static final String VALID_IMAGE_FILE = "valid_image";
    public static final String MANIFEST_PATH = "/manifest";
    public static final String URL = "http://localhost:8089" + MANIFEST_PATH;
    public static final Map<String, List<String>> ASSETS = ImmutableMap.of(
      INVALID_IMAGE_FILE, asList("invalid_image.png"),
      VALID_IMAGE_FILE, asList("chegg.png")
    );

    private final static WireMockServer wireMock = new WireMockServer(
            options().port(8089).extensions(new ManifestBodyTransformer()));

    public static void start() {
        wireMock.start();
        wireMock.givenThat(get(urlPathMatching(MANIFEST_PATH + "/.*"))
                .willReturn(aResponse().withTransformers("manifest-body")));
    }

    public static String firstFileFor(String manifest) {
        return "__files/" + ASSETS.get(manifest).get(0);
    }

    private static class ManifestBodyTransformer extends ResponseDefinitionTransformer {
        @Override
        public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
            String path = request.getUrl();
            String manifest = path.substring(path.lastIndexOf("/") + 1);
            return new ResponseDefinitionBuilder()
                    .withHeader("Content-Type", "application/octet-stream")
                    .withStatus(200)
                    .withBody(contentOf(manifest))
                    .build();
        }

        private String contentOf(String manifest) {
            return ASSETS.get(manifest).stream().map(a -> ASSETS_URL + "/" + a).collect(joining("\n"));
        }

        @Override
        public String getName() {
            return "manifest-body";
        }

        @Override
        public boolean applyGlobally() {
            return false;
        }
    }
}
