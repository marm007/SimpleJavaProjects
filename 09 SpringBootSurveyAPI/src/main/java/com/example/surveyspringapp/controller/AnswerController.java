package com.example.surveyspringapp.controller;

import javax.validation.Valid;

import com.example.surveyspringapp.controller.helpers.AnswerData;
import com.example.surveyspringapp.entity.Answer;
import com.example.surveyspringapp.entity.Survey;
import com.example.surveyspringapp.exception.ResourceNotFoundException;
import com.example.surveyspringapp.repository.AnswerRepository;
import com.example.surveyspringapp.repository.SurveyRepository;
import com.example.surveyspringapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/answer")
    public Answer create(@Valid @RequestHeader("user-id") Long userId, @Valid @RequestBody AnswerData answerData) {

        return userRepository.findById(userId).map(user -> {
            Survey survey = surveyRepository.findById(answerData.getSurveyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Survey", "id", answerData.getSurveyId()));

            Answer answer = new Answer();
            answer.setRating(answerData.getRating());
            answer.setSurvey(survey);
            answer.setUser(user);

            System.out.println(answer.toString());

            return answerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @GetMapping("/answer/{id}")
    public Answer get(@Valid @PathVariable("id") Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Answer", "id", id));
    }

    @PutMapping("/answer/{id}")
    public Answer update(@Valid @RequestHeader("user-id") Long userId, @PathVariable("id") Long id,
            @RequestParam(name = "rating") int rating) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", id));

        answer.setRating(rating);

        Answer updatedAnswer = answerRepository.save(answer);
        return updatedAnswer;
    }

}
