package org.rockem.qfetcher.files;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FilenameUtils.getExtension;

public class DocsFilter {
    private final List<String> filter;

    public DocsFilter(List<String> filter) {
        this.filter = filter;
    }

    public List<String> filter(List<String> docs) {
        if(filter == null || filter.isEmpty()) {
            return docs;
        }
        return docs.stream()
                .filter(d -> filter.contains(getExtension(d))).collect(toList());
    }
}
