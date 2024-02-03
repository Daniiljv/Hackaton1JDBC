package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.EvaluationModel;
import com.jdbc.hacaton1.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class EvaluationController {

    private final EvaluationService service;

    @PostMapping("createEvaluation")
    public Integer createEvaluation(@RequestBody EvaluationModel evaluation){
        return service.createEvaluation(evaluation);
    }

    @GetMapping("getAvgEvaluationByProductId:{id}")
        public Double getAvgEvaluation(@PathVariable Integer id){
        return service.getAvgEvaluation(id);
    }
}
