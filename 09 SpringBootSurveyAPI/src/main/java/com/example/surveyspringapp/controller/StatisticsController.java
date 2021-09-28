package com.example.surveyspringapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.example.surveyspringapp.entity.Answer;
import com.example.surveyspringapp.entity.Survey;
import com.example.surveyspringapp.entity.User;
import com.example.surveyspringapp.repository.AnswerRepository;
import com.example.surveyspringapp.repository.SurveyRepository;
import com.example.surveyspringapp.repository.UserRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @GetMapping("/stats/user/{id}")
    public Map<String, Object> showUserStats(@PathVariable("id") Long id) {

        List<Survey> surveys = surveyRepository.findAllByUserUserId(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mySurveys", surveys.size());

        surveys = surveyRepository.findAll();

        JSONArray averageArray = new JSONArray();

        for (int i = 0; i < surveys.size(); i++) {

            Survey survey = surveys.get(i);

            List<Answer> userAnswers = answerRepository.findAllByUserUserIdAndSurveySurveyId(id, survey.getSurveyId());

            if (userAnswers.size() == 0) {
                averageArray.put(i, 0);
                continue;
            }

            double rating = 0;
            for (Answer answer : userAnswers) {
                rating += answer.getRating();
            }

            rating = rating / userAnswers.size();

            averageArray.put(i, rating);

        }

        jsonObject.put("average", averageArray);

        JSONArray answerNumberArray = new JSONArray();

        for (int i = 0; i < surveys.size(); i++) {
            Survey survey = surveys.get(i);
            List<Answer> userAnswers = answerRepository.findAllByUserUserIdAndSurveySurveyId(id, survey.getSurveyId());
            answerNumberArray.put(i, userAnswers.size());
        }

        jsonObject.put("answerNumber", answerNumberArray);

        System.out.println(jsonObject);

        return jsonObject.toMap();
    }

    @GetMapping("/stats")
    public Map<String, Object> allStats() {

        JSONObject jsonObject = new JSONObject();

        SessionFactory factory = com.example.surveyspringapp.hibernate.HibernateUtils.getSessionFactory();

        Session session = factory.getCurrentSession();

        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> surveyQuery = builder.createTupleQuery();
        Root<User> userRoot = surveyQuery.from(User.class);

        Join<Object, Object> usersBySurveys = userRoot.join("surveys", JoinType.LEFT);

        surveyQuery.select(builder.tuple(userRoot, builder.count(usersBySurveys)));
        surveyQuery.groupBy(userRoot.get("userId"));
        surveyQuery.orderBy(builder.desc(builder.count(usersBySurveys)));

        List<Tuple> surveyResult = session.createQuery(surveyQuery).getResultList();
        List<JSONObject> _usersBySurveys = new ArrayList<>();

        for (Tuple t : surveyResult) {
            User user = (User) t.get(0);
            Long count = (Long) t.get(1);
            System.out.println("User " + user.getUsername() + " has " + count + " surveys");

            JSONObject j = new JSONObject();
            j.put("username", user.getUsername());
            j.put("count", count);

            _usersBySurveys.add(j);
        }

        jsonObject.put("surveyRank", _usersBySurveys);

        CriteriaQuery<Tuple> answerQuery = builder.createTupleQuery();
        Root<User> userRoot_1 = answerQuery.from(User.class);

        Join<Object, Object> usersByAnswers = userRoot_1.join("answers", JoinType.LEFT);

        answerQuery.select(builder.tuple(userRoot_1, builder.count(usersByAnswers)));
        answerQuery.groupBy(userRoot_1.get("userId"));
        answerQuery.orderBy(builder.desc(builder.count(usersByAnswers)));

        List<Tuple> answersResult = session.createQuery(answerQuery).getResultList();
        List<JSONObject> _usersByAnswers = new ArrayList<>();

        for (Tuple tuple : answersResult) {
            User user = (User) tuple.get(0);
            Long count = (Long) tuple.get(1);

            System.out.println("User " + user.getUsername() + " has " + count + " answers");

            JSONObject j = new JSONObject();
            j.put("username", user.getUsername());
            j.put("count", count);

            _usersByAnswers.add(j);
        }

        jsonObject.put("answersRank", _usersByAnswers);

        List<User> users = userRepository.findAll();
        List<Survey> surveys = surveyRepository.findAll();
        List<Answer> answers = answerRepository.findAll();

        jsonObject.put("avegareSurveys", surveys.size() / users.size());
        jsonObject.put("avegareAnswers", answers.size() / surveys.size());

        jsonObject.put("allSurveys", surveys.size());

        return jsonObject.toMap();
    }
}
