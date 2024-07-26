package com.example.quiz.services;

import com.example.quiz.models.Question;
import com.example.quiz.models.User;
import com.example.quiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QuestionService questionService;

    public User registerUser(User user){
        return userRepo.save(user);
    }

    public User LoginUser(String email,String password){
        User user = userRepo.findByEmail(email);
        if(user != null){
            if(user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User getUserById(long id){
        return userRepo.findById(id).get();
    }

    public User checkAnswer(long userId,long questionId,int answer){
        User user=getUserById(userId);
        Question question=questionService.getQuestionById(questionId);
        if(question.getAnswer() == answer){
            user.setCurrentScore(user.getCurrentScore()+1);
            userRepo.save(user);
        }
        if(questionId==10){
            if(user.getCurrentScore()>user.getScore()){
                user.setScore(user.getCurrentScore());
            }
            user.setCurrentScore(0);
            userRepo.save(user);
        }

        return user;
    }
}

