package com.example.surveyspringapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.surveyspringapp.entity.Survey;
import com.example.surveyspringapp.entity.User;
import com.example.surveyspringapp.exception.NotPermittedException;
import com.example.surveyspringapp.exception.ResourceNotFoundException;
import com.example.surveyspringapp.repository.AnswerRepository;
import com.example.surveyspringapp.repository.SurveyRepository;
import com.example.surveyspringapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/survey")
    public Survey create(@Valid @RequestHeader(name = "user-id") Long userId, @Valid @RequestBody Survey survey) {

        return userRepository.findById(userId).map(user -> {
            survey.setUser(user);
            return surveyRepository.save(survey);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId", "user_id", userId));
    }

    @GetMapping("/survey")
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    @GetMapping("/survey/user/{id}")
    public List<Survey> getUserSurveys(@PathVariable("id") Long id) {
        return surveyRepository.findAllByUserUserId(id);
    }

    @DeleteMapping("/survey/{id}")
    public ResponseEntity<?> deleteSurvey(@Valid @RequestHeader(name = "user-id") Long userId,
            @PathVariable("id") Long id) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey", "id", id));

        if (!survey.getUser().equals(user))
            throw new NotPermittedException(userId, "survey", id);

        surveyRepository.delete(survey);

        return ResponseEntity.ok().build();
    }

}
