package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.EvaluationModel;
import com.jdbc.hacaton1.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("evaluations/")

public class EvaluationController {

    private final EvaluationService service;

    @PostMapping("create")
    public Integer createEvaluation(@RequestBody EvaluationModel evaluation){
        return service.createEvaluation(evaluation);
    }

    @GetMapping("getAvgEvaluationByProductId/{id}")
        public Double getAvgEvaluation(@PathVariable Integer id){
        return service.getAvgEvaluation(id);
    }

    @DeleteMapping("deleteById")
    public String deleteEvaluationById(@RequestParam Integer id){
        return service.deleteEvaluationById(id);
    }
}
