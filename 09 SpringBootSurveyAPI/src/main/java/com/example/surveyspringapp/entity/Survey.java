package com.example.surveyspringapp.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Survey {

    @Id
    @Column(name = "survey_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @Basic
    @Column(name = "survey_title")
    private String surveyTitle;

    @Basic
    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Survey{" + "surveyId=" + surveyId + ", surveyTitle='" + surveyTitle + '\'' + ", question='" + question
                + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Survey survey = (Survey) o;
        return Objects.equals(surveyId, survey.surveyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyId);
    }
}
