package com.example.surveyspringapp.controller.helpers;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AnswerData {

    @NotNull
    private Long surveyId;

    @NotNull
    @Max(5)
    @Min(0)
    private int rating;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
