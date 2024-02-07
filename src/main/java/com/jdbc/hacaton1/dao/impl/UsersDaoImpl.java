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
        String SQL = "select * from users";
        List<UsersModel> users = new ArrayList<>();

        try (Connection connection = database.connection();
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

    @Override
    public Integer createUser(String login, String password) {
        int userId = 0;

        String insertSQL = "insert into users (login, password) values (?, ?) returning id";

        try (Connection connection = database.connection();
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

    @Override
    public PrivateUserModel getUserById(Integer id) {

        PrivateUserModel user = new PrivateUserModel();

        String SQL = "select id, login, rate from users " +
                     "where id = ?";

        try(Connection connection = database.connection();
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

    @Override
    public String updateUserById(Integer id, UsersModel user) {

        String SQL = "update users " +
                     "set login = ?, password = ? " +
                     "where id = ?";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, id);

            statement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return "User with id " + id + " is updated";
    }

    @Override
    public String updateUsersRateById(Integer id, Integer rate) {

        String SQL = "update users " +
                     "set rate = ? " +
                     "where id = ?";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, rate);
            statement.setInt(2, id);

            statement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
            return "Users rate is " + rate;
    }

    @Override
    public Integer getIdByLoginAndPassword(UsersModel user) {

        int id = 0;

        String SQL = "select id from users " +
                     "where login = ? and password = ? ";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
          return id;
    }

    public String deleteUserById(Integer id){

        String SQL = "delete from users where id = ? ";


        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, id);

            statement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return "User with ID " + id + " was deleted!";
    }
}
