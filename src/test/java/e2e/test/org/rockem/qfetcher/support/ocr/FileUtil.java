package e2e.test.org.rockem.qfetcher.support.ocr;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Base64;

public class FileUtil {

    public static String getAsBase64(String fileName) throws URISyntaxException, IOException {
        return new String(Base64.getEncoder().encode(readFile(fileName)));
    }

    public static byte[] readFile(String fileName) throws IOException, URISyntaxException {
        return FileUtils.readFileToByteArray(Paths.get(FileUtil.class.getClassLoader().getResource(fileName).toURI()).toFile());
    }
}
