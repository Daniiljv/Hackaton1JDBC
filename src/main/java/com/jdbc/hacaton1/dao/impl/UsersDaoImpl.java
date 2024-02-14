package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.UsersDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UsersDaoImpl implements UsersDao {

    private final DatabaseConfiguration database;

    @Override
    public List<UsersModel> getAllUsers() {
        String SQL = "select * from users u " + "where u.delete_time is null ";
        List<UsersModel> users = new ArrayList<>();

        try (Connection connection = database.connection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL)) {

            while (resultSet.next()) {
                UsersModel user = new UsersModel();

                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRate(resultSet.getInt(4));

                users.add(user);
            }
            if (users.isEmpty()) {
                return null;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return users;
    }

    @Override
    public Integer createUser(String login, String password) {
        int userId = -1;

        String insertSQL = "insert into users (login, password) values (?, ?) returning id";

        try (Connection connection = database.connection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return userId;
    }

    @Override
    public PrivateUserModel getUserById(Integer id) {

        PrivateUserModel user = null;

        String SQL = "select id, login, rate from users " + "where id = ? and delete_time is null ";

        try (Connection connection = database.connection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new PrivateUserModel();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setRate(resultSet.getInt(3));
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return user;
    }

    @Override
    public String updateUserById(Integer id, UsersModel user) {

        String selectSQL = "select * from users where id = ?";
        String updateSQL = "update users " + "set login = ?, password = ? " + "where id = ? and delete_time is null ";

        try (Connection connection = database.connection(); PreparedStatement selectStatement = connection.prepareStatement(selectSQL); PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);
            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                updateStatement.setString(1, user.getLogin());
                updateStatement.setString(2, user.getPassword());
                updateStatement.setInt(3, id);

                updateStatement.executeUpdate();
            } else return null;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "User with id " + id + " is updated";
    }

    @Override
    public String updateUsersRateById(Integer id, Integer rate) {

        String selectSQL = "select * from users where id = ?";
        String updateSQL = "update users " + "set rate = ? " + "where id = ? and delete_time is null ";

        try (Connection connection = database.connection(); PreparedStatement selectStatement = connection.prepareStatement(selectSQL); PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);
            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                updateStatement.setInt(1, rate);
                updateStatement.setInt(2, id);

                updateStatement.executeUpdate();
            } else return null;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "Users rate is " + rate;
    }

    @Override
    public Integer getIdByLoginAndPassword(UsersModel user) {

        int id = 0;

        String SQL = "select id, delete_time from users " + "where login = ? and password = ? and delete_time is null ";

        try (Connection connection = database.connection(); PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getDate("delete_time") == null) {
                id = resultSet.getInt(1);
            } else return null;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return id;
    }

    @Override
    public String deleteUserById(Integer id) {

        String selectSQL = "select * from users where id = ?";
        String deleteSQL = "update comments " + "set delete_time = ? " + "where comments.user_id = ?; " +

                "update evaluate_product " + "set delete_time = ? " + "where evaluate_product.user_id = ?; " +

                "update comments " + "set delete_time = ? " + "where comments.product_id in (select id from products p " + "where p.users_id = ?); " +

                "update evaluate_product " + "set delete_time = ? " + "where evaluate_product.product_id in (select id from products p " + "where p.users_id = ?); " +

                "update products " + "set delete_time = ? " + "where users_id = ?; " +

                "update users " + "set delete_time = ? " + "where id = ?; ";


        try (Connection connection = database.connection(); PreparedStatement selectStatement = connection.prepareStatement(selectSQL); PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            selectStatement.setInt(1, id);

            ResultSet selectResult = selectStatement.executeQuery();

            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                deleteStatement.setDate(1, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(2, id);
                deleteStatement.setDate(3, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(4, id);
                deleteStatement.setDate(5, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(6, id);
                deleteStatement.setDate(7, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(8, id);
                deleteStatement.setDate(9, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(10, id);
                deleteStatement.setDate(11, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(12, id);

                deleteStatement.executeUpdate();
            } else return null;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "User with ID " + id + " was deleted!";
    }
}




