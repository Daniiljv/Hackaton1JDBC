package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsDao {

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

    public Integer createComment(CommentsModel comment){
        int commentId = 0;

        String SQL = "insert into comments (product_id, user_id, comment) " +
                                    "values(?,?,?) returning id";

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1,comment.getProductId());
            statement.setInt(2,comment.getUserId());
            statement.setString(3,comment.getComment());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                commentId = resultSet.getInt(1);
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return commentId;
    }

    public List<GeneralComments> getCommentsByProductId(Integer id){

        List<GeneralComments> comments = new ArrayList<>();

        String SQL = "select u.login, c.comment from comments c " +
        "join users u on u.id = c.user_id " +
        "join products p on p.id = c.product_id " +
        "where p.id = ? " +
        "order by c.id desc";

        try(Connection connection = connection();
           PreparedStatement statement = connection.prepareStatement(SQL)){

           statement.setInt(1, id);

           ResultSet resultSet = statement.executeQuery();
           while (resultSet.next()){
               GeneralComments comment = new GeneralComments();

               comment.setUserName(resultSet.getString(1));
               comment.setComment(resultSet.getString(2));

               comments.add(comment);
           }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return comments;
    }

    public List<GeneralComments> getCommentsByUserId(Integer id){

        List<GeneralComments> comments = new ArrayList<>();

        String SQL = "select u.login, c.comment from comments c " +
                "join users u on u.id = c.user_id " +
                "join products p on p.id = c.product_id " +
                "where u.id = ? ";

        try(Connection connection = connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                GeneralComments comment = new GeneralComments();

                comment.setUserName(resultSet.getString(1));
                comment.setComment(resultSet.getString(2));

                comments.add(comment);
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return comments;
    }
}
