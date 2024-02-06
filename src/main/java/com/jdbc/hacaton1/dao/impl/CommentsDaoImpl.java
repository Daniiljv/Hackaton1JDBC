package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.CommentsDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class CommentsDaoImpl implements CommentsDao {

    private final DatabaseConfiguration database;

    public Integer createComment(CommentsModel comment){
        int commentId = 0;

        String SQL = "insert into comments (product_id, user_id, comment) " +
                                    "values(?,?,?) returning id";

        try (Connection connection = database.connection();
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

        String SQL = "select u.login, c.comment, c.likes_count, c.dislikes_count from comments c " +
        "join users u on u.id = c.user_id " +
        "join products p on p.id = c.product_id " +
        "where p.id = ? " +
        "order by c.id desc";

        try(Connection connection = database.connection();
           PreparedStatement statement = connection.prepareStatement(SQL)){

           statement.setInt(1, id);

           ResultSet resultSet = statement.executeQuery();
           while (resultSet.next()){
               GeneralComments comment = new GeneralComments();

               comment.setUserName(resultSet.getString(1));
               comment.setComment(resultSet.getString(2));
               comment.setLikesCount(resultSet.getInt(3));
               comment.setDislikesCount(resultSet.getInt(4));

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

        try(Connection connection = database.connection();
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

    public void updateComment(Integer id, CommentsModel comment) {

        String SQL = "update comments " +
                     "set likes_count = ?, dislikes_count = ? " +
                     "where id = ?";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, comment.getLikesCount());
            statement.setInt(2, comment.getDislikesCount());
            statement.setInt(3, id);

            statement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public String deleteCommentById(Integer id){

        String SQL = "delete from comments where id = ?";

        try(Connection connection = database.connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, id);

            statement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return "Comment with ID " + id + " was deleted!";
    }
}
