package com.example.quiz.services;


import com.example.quiz.models.Question;
import com.example.quiz.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> addQuestion(List<Question> questions){
        return questionRepo.saveAll(questions);
    }

    public Question getQuestionById(long id){
        return questionRepo.findById(id).get();
    }
}
