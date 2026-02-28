package com.ayush.quizApp.service;

import com.ayush.quizApp.dao.QuestionDao;
import com.ayush.quizApp.dao.QuizDao;
import com.ayush.quizApp.model.QuestionWrapper;
import com.ayush.quizApp.model.Quiz;
import com.ayush.quizApp.model.Response;
import com.ayush.quizApp.model.questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<questions> Questions = questionDao.findRandomQuestionByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(Questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {

        Optional<Quiz> quiz = quizDao.findById(id);

        if (quiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<questions> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (questions q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionText(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );
            questionForUser.add(qw);
        }

        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).orElseThrow(() ->
                new RuntimeException("Quiz not found"));

        List<questions> ques = quiz.getQuestions();

        int right = 0;

        for (int i = 0; i < responses.size() && i < ques.size(); i++) {

            Integer userAnswer = responses.get(i).getResponse();
            Integer correctAnswer = ques.get(i).getCorrectOption();

            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
