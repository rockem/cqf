package e2e.test.org.rockem.qfetcher.support.ocr;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static e2e.test.org.rockem.qfetcher.support.ocr.FileUtil.getAsBase64;
import static java.util.Arrays.asList;

public class GoogleOCRRequestCreator {

    private final String imageName;

    public GoogleOCRRequestCreator(String imageName) {
        this.imageName = imageName;
    }

    public Map<String, Object> create() throws URISyntaxException, IOException {
        return ImmutableMap.of("requests", asList(
                ImmutableMap.of(
                        "image", ImmutableMap.of("content", getAsBase64(this.imageName)),
                        "features", asList(ImmutableMap.of("type", "TEXT_DETECTION")))));
    }
}
