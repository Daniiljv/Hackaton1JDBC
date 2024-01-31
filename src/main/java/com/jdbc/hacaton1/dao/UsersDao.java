package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersDao {
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

    public List<UsersModel> getAllUsers() {
        String SQL = "select * from users";
        List<UsersModel> users = new ArrayList<>();

        try (Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {

            while (resultSet.next()) {
                UsersModel user = new UsersModel();

                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRate(resultSet.getInt(4));

                users.add(user);
            }


        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return users;
    }

    public Integer createUser(String login, String password) {
        int userId = 0;

        String insertSQL = "insert into users (login, password) values (?, ?) returning id";

        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)
        ) {
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

    public PrivateUserModel getUserById(Integer id) {

        PrivateUserModel user = new PrivateUserModel();

        String SQL = "select id, login, rate from users " +
                     "where id = ?";

        try(Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    user.setId(resultSet.getInt(1));
                    user.setLogin(resultSet.getString(2));
                    user.setRate(resultSet.getInt(3));
                }
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return user;
    }
}
