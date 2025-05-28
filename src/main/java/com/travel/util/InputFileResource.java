package com.travel.util;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public class InputFileResource extends InputStreamResource {
    private final String filename;

    public InputFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        return -1;
    }
}
