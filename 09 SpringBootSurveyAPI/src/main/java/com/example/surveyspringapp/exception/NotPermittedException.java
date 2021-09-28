package com.example.surveyspringapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotPermittedException extends RuntimeException {

    private Object userId;

    private String resurceName;

    private Object resurceId;

    public NotPermittedException(Object userId, String resurceName, Object resurceId) {
        super(String.format("User with id %s not allowed to modify resource %s with id %s", userId, resurceName,
                resurceId));

        this.userId = userId;
        this.resurceName = resurceName;
        this.resurceId = resurceId;
    }

    public Object getUserId() {
        return userId;
    }

    public String getResurceName() {
        return resurceName;
    }

    public Object getResurceId() {
        return resurceId;
    }
}
