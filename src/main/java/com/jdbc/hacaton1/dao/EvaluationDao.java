package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.EvaluationModel;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service

public class EvaluationDao {
    private Connection connection() {
        Connection connection = null;

        try {
            String URL = "jdbc:postgresql://localhost:5432/Hackaton#1";
            String USER = "postgres";
            String PASSWORD = "postgres";

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return connection;
    }

    public Integer createEvaluation(EvaluationModel evaluation){

        int id = 0;

        String SQL = "insert into evaluate_product (product_id, user_id, evaluate) " +
                     "values(?,?,?) returning id ";

        try(Connection connection = connection();
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
}
