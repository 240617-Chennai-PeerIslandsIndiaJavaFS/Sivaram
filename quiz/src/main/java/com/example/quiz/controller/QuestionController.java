package com.example.quiz.controller;

import com.example.quiz.models.Question;
import com.example.quiz.response.BaseResponse;
import com.example.quiz.response.MessageResponse;
import com.example.quiz.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/{id}")
    public BaseResponse<Question> getQuestionById(@PathVariable long id){
//        return new ResponseEntity<>(questionService.getQuestionById(id),HttpStatus.OK);
        return new BaseResponse<Question>(HttpStatus.OK.value(),"Choose one Option pass that option Value at Url ( <questions Number>/<option> )", questionService.getQuestionById(id));
    }

    @PostMapping
    public MessageResponse addQuestion(@RequestBody List<Question> question){
        if(questionService.addQuestion(question) != null)
            return new MessageResponse(HttpStatus.OK.value(), "Questions Are Successfully");
        return new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Questions Are Successfully");
    }
}
