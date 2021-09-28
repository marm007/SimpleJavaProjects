package com.example.surveyspringapp.repository;

import java.util.List;

import com.example.surveyspringapp.entity.Survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    public List<Survey> findAllByUserUserId(Long id);
}
