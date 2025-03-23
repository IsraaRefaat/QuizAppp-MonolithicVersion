package com.esraa.quizapp.service;


import com.esraa.quizapp.dao.QuestionDao;
import com.esraa.quizapp.dao.QuizDao;
import com.esraa.quizapp.model.Question;
import com.esraa.quizapp.model.QuestionWrapper;
import com.esraa.quizapp.model.Quiz;
import com.esraa.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> create(String category, int numQ, String title) {

        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {

        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionFromDB) {

            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());

            questionForUser.add(qw);
        }

        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }


    public ResponseEntity<String> calculateResult(int id, List<Response> response) {

        int score = 0;
        int i=0;
        Quiz quiz = quizDao.findById(id).get();

        List<Question> questions = quiz.getQuestions();


            for (Response r : response) {

                if(r.getUserResponse().equals(questions.get(i).getRightAnswer()))
                    score++;

                i++;


            }


        return new ResponseEntity<>("Score: " + score, HttpStatus.OK);



    }
}
