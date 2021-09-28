package com.example.surveyspringapp.repository;

import java.util.List;

import com.example.surveyspringapp.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        @Query(value = "select u.username, u.surveys.size from User u join  u.surveys su group by u Order By su.size desc", countQuery = "select count(u) from User u")
        public List<String> findAllOrderBySurveysCountAsc();

        @Query(value = "select u.username, u.answers.size from User u join u.answers su group by u Order By su.size desc", countQuery = "select count(u) from User u")
        public List<String> findAllOrderByAnswersCountAsc();
}
