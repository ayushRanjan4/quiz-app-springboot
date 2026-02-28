package com.ayush.quizApp.Controller;

import com.ayush.quizApp.model.questions;
import com.ayush.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestion")
    public ResponseEntity<List<questions>> getAllQuestion(){

        return questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<questions>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody questions ques){

        return questionService.addQuestion(ques);
    }
    @PostMapping("addMultiQuestion")
    public ResponseEntity<String> addMultQuestion(@RequestBody List<questions> ques ){
        return questionService.addMultiQuestion(ques);
    }

}