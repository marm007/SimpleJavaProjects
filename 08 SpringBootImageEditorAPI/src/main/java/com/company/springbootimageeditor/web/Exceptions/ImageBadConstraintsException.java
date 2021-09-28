package com.company.springbootimageeditor.web.Exceptions;

public class ImageBadConstraintsException extends RuntimeException {

    public ImageBadConstraintsException(String one, String reason, String two) {
        super("Selected " + one + " is " + reason + " than " + two + "!");
    }
}
