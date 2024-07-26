package com.example.quiz.controller;


import com.example.quiz.models.User;
import com.example.quiz.response.BaseResponse;
import com.example.quiz.response.MessageResponse;
import com.example.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public MessageResponse registerUser(@RequestBody User user){
        if(userService.registerUser(user)!= null)
            return new MessageResponse(HttpStatus.OK.value(),"Registration SuccessFull");
        return new MessageResponse(HttpStatus.BAD_REQUEST.value(),"Something Went Wrong Try Again");
    }

    @GetMapping("/login")
    public BaseResponse<User> loginUser(@RequestParam String  email,@RequestParam String password){
        User user = userService.LoginUser(email,password);
        if(user!=null){
            return new BaseResponse<User>(HttpStatus.OK.value(), "Logged In SuccessFully",user);
        }
        return new BaseResponse<User>(HttpStatus.BAD_REQUEST.value(), "InValid Email and Password",null);
    }

    @PostMapping("/start")
    public BaseResponse<User> checkAnswer(@RequestParam long userId, @RequestParam long questionId, @RequestParam int answer){
        User user=userService.checkAnswer(userId,questionId,answer);
        BaseResponse<User> baseResponse=new BaseResponse<>();
        baseResponse.setBody(user);
        if(questionId==10){
            baseResponse.setMessage("Game Come to End ! score: "+user.getCurrentScore());
            baseResponse.setStatusCode(HttpStatus.OK.value());
        }
        else{
            baseResponse.setMessage("Update score: "+user.getCurrentScore());
            baseResponse.setStatusCode(HttpStatus.OK.value());
        }
        return baseResponse;
    }


}
