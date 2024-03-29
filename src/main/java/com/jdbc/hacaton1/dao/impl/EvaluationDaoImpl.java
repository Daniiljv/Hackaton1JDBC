package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.EvaluationDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.EvaluationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
@Slf4j

public class EvaluationDaoImpl implements EvaluationDao {

    private final DatabaseConfiguration database;

    @Override
    public Integer createEvaluation(EvaluationModel evaluation) throws RuntimeException {
        log.info("STARTED : EvaluationDaoImpl - createEvaluation() {}", evaluation);
        int id = -1;

        String selectUserSQL = "select * from users where id = ? ";
        String selectProductSQL = "select * from products where id = ? ";
        String insertSQL = "insert into evaluate_product (product_id, user_id, evaluate) " +
                "values(?,?,?) returning id ";

        try (Connection connection = database.connection();
             PreparedStatement userStatement = connection.prepareStatement(selectUserSQL);
             PreparedStatement productStatement = connection.prepareStatement(selectProductSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {

            userStatement.setInt(1, evaluation.getUserId());
            productStatement.setInt(1, evaluation.getProductId());

            ResultSet userResult = userStatement.executeQuery();
            ResultSet productResult = productStatement.executeQuery();

            if (userResult.next() && productResult.next() &&
                    userResult.getDate("delete_time") == null &&
                    productResult.getDate("delete_time") == null) {

                insertStatement.setInt(1, evaluation.getProductId());
                insertStatement.setInt(2, evaluation.getUserId());
                insertStatement.setInt(3, evaluation.getEvaluate());

                ResultSet resultSet = insertStatement.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to add evaluation to database", sqlException);
        }
        log.info("FINISHED : EvaluationDaoImpl - createEvaluation(). Evaluation id = {}", id);
        return id;
    }

    @Override
    public Double getAvgEvaluation(Integer productId) throws NullPointerException {
        log.info("STARTED : EvaluationDaoImpl - getAvgEvaluation() {}", productId);
        Double avgEvaluation = null;

        String SQL = "select avg(evaluate) from evaluate_product where product_id = ? and delete_time is null";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                avgEvaluation = resultSet.getDouble(1);
                if (resultSet.wasNull()) {
                    throw new NullPointerException();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("STARTED : EvaluationDaoImpl - getAvgEvaluation() {}", avgEvaluation);
        return avgEvaluation;
    }

    @Override
    public String deleteEvaluationById(Integer id) throws NullPointerException{
        log.info("STARTED : EvaluationDaoImpl - deleteEvaluationById() {}", id);
        String selectSQL = "select * from evaluate_product where id = ?";
        String deleteSQL = "update evaluate_product " +
                "set delete_time = ? " +
                "where id = ?";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            selectStatement.setInt(1, id);
            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                deleteStatement.setDate(1, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(2, id);

                deleteStatement.executeUpdate();
            } else throw new NullPointerException();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : EvaluationDaoImpl - deleteEvaluationById() {}", id);
        return "Evaluation with ID " + id + " was deleted!";
    }
}
