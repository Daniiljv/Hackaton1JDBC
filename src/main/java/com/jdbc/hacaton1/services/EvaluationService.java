package com.jdbc.hacaton1.services;

import com.jdbc.hacaton1.dao.EvaluationDao;
import com.jdbc.hacaton1.models.EvaluationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class EvaluationService {
    private final EvaluationDao dao;

    public Integer createEvaluation(EvaluationModel evaluation){
        return dao.createEvaluation(evaluation);
    }

    public Integer getAvgEvaluation(Integer id){
        return dao.getAvgEvaluation(id);
    }
}
