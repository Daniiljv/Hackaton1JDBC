package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.EvaluationModel;

public interface EvaluationDao {


    Integer createEvaluation(EvaluationModel evaluation);

    Double getAvgEvaluation(Integer id);

    String deleteEvaluationById(Integer id);
}
