package com.esraa.quizapp.controller;

import com.esraa.quizapp.model.QuestionWrapper;
import com.esraa.quizapp.model.Response;
import com.esraa.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category , @RequestParam int numQ, @RequestParam String title) {
        return quizService.create(category,numQ,title);
    }


    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable int id , @RequestBody List<Response> response) {
        return quizService.calculateResult(id,response);
    }

}
