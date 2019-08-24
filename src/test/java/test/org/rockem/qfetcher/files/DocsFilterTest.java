package test.org.rockem.qfetcher.files;

import org.junit.jupiter.api.Test;
import org.rockem.qfetcher.files.DocsFilter;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class DocsFilterTest {

    public static final List<String> FILES = asList("http://file1.csv", "http://file2.jpg");

    @Test
    void filterNonIfNoFilterValues() {
        assertThat(new DocsFilter(asList()).filter(FILES)).containsAll(FILES);
    }

    @Test
    void filterNonWithNullFilter() {
        assertThat(new DocsFilter(null).filter(FILES)).containsAll(FILES);
    }

    @Test
    void retrieveOnlyGivenExtensions() {
        assertThat(new DocsFilter(asList("csv")).filter(FILES)).containsOnly(FILES.get(0));
    }
}