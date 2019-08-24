package org.rockem.qfetcher.files;

public interface FileFetcher {

    byte[] fetch(String url);
}
