package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.EvaluationModel;
import com.jdbc.hacaton1.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class EvaluationController {

    private final EvaluationService service;

    @PostMapping("createEvaluation")
    public Integer createEvaluation(@RequestBody EvaluationModel evaluation){
        return service.createEvaluation(evaluation);
    }
}
