package com.company.springbootimageeditor.web.Exceptions;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String id) {
        super("Image id not found " + id);
    }
}
