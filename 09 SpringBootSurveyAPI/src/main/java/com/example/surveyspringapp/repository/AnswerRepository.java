package com.example.surveyspringapp.repository;

import java.util.List;

import com.example.surveyspringapp.entity.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    public List<Answer> findAllByUserUserId(Long id);

    public List<Answer> findAllByUserUserIdAndSurveySurveyId(Long userId, Long surveyId);

}
