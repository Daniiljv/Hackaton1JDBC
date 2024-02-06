package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.EvaluationDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.EvaluationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor

public class EvaluationDaoImpl implements EvaluationDao {

    private final DatabaseConfiguration database;

    public Integer createEvaluation(EvaluationModel evaluation){

        int id = 0;

        String SQL = "insert into evaluate_product (product_id, user_id, evaluate) " +
                     "values(?,?,?) returning id ";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1,evaluation.getProductId());
            statement.setInt(2,evaluation.getUserId());
            statement.setInt(3,evaluation.getEvaluate());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return id;
    }

    public Double getAvgEvaluation(Integer id){
        double avgEvaluation = 0;

        String SQL = "SELECT AVG(evaluate) FROM evaluate_product WHERE product_id = ?";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    avgEvaluation = resultSet.getInt(1);
                }
            }
        catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return avgEvaluation;
    }

    public String deleteEvaluationById(Integer id){

        String SQL = "delete from evaluate_product where id = ?";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, id);

            statement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return "Evaluation with ID " + id + " was deleted!";
    }
}
