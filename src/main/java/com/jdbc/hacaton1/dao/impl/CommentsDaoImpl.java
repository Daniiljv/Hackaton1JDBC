package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.CommentsDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class CommentsDaoImpl implements CommentsDao {

    private final DatabaseConfiguration database;

    @Override
    public Integer createComment(CommentsModel comment) throws RuntimeException{
        int commentId = -1;

        String selectUserSQL = "select * from users where id = ? ";
        String selectProductSQL = "select * from products where id = ? ";
        String insertSQL = "insert into comments (product_id, user_id, comment) " +
                "values(?,?,?) returning id";

        try (Connection connection = database.connection();
             PreparedStatement userStatement = connection.prepareStatement(selectUserSQL);
             PreparedStatement productStatement = connection.prepareStatement(selectProductSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {

            userStatement.setInt(1, comment.getUserId());
            productStatement.setInt(1, comment.getProductId());

            ResultSet userResult = userStatement.executeQuery();
            ResultSet productResult = productStatement.executeQuery();

            if (userResult.next() && productResult.next() &&
                    userResult.getDate("delete_time") == null &&
                    productResult.getDate("delete_time") == null) {


                insertStatement.setInt(1, comment.getProductId());
                insertStatement.setInt(2, comment.getUserId());
                insertStatement.setString(3, comment.getComment());

                ResultSet resultSet = insertStatement.executeQuery();

                if (resultSet.next()) {
                    commentId = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Failed to add comment to database");
        }
        return commentId;
    }

    @Override
    public List<GeneralComments> getCommentsByProductId(Integer id) throws NullPointerException {

        List<GeneralComments> comments = new ArrayList<>();

        String SQL = "select u.login, c.comment, c.likes_count, c.dislikes_count from comments c " +
                "join users u on u.id = c.user_id " +
                "join products p on p.id = c.product_id " +
                "where p.id = ? and c.delete_time is null and u.delete_time is null and p.delete_time is null " +
                "order by c.id desc";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GeneralComments comment = new GeneralComments();

                comment.setUserName(resultSet.getString(1));
                comment.setComment(resultSet.getString(2));
                comment.setLikesCount(resultSet.getInt(3));
                comment.setDislikesCount(resultSet.getInt(4));

                comments.add(comment);
            }
            if (comments.isEmpty()) {
                throw new NullPointerException();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return comments;
    }

    @Override
    public List<GeneralComments> getCommentsByUserId(Integer id) throws NullPointerException{

        List<GeneralComments> comments = new ArrayList<>();

        String SQL = "select u.login, c.comment, c.likes_count, c.dislikes_count from comments c " +
                "join users u on u.id = c.user_id " +
                "join products p on p.id = c.product_id " +
                "where u.id = ? and c.delete_time is null and u.delete_time is null and u.delete_time is null ";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GeneralComments comment = new GeneralComments();

                comment.setUserName(resultSet.getString(1));
                comment.setComment(resultSet.getString(2));
                comment.setLikesCount(resultSet.getInt(3));
                comment.setDislikesCount(resultSet.getInt(4));

                comments.add(comment);
            }
            if (comments.isEmpty()) {
                throw new NullPointerException();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return comments;
    }

    @Override
    public String updateCommentsLikesDislikesById(Integer id, CommentsModel comment) throws NullPointerException {

        String selectSQL = "select * from comments where id = ?";
        String updateSQL = "update comments " +
                "set likes_count = ?, dislikes_count = ? " +
                "where id = ? and delete_time is null ";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);

            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                updateStatement.setInt(1, comment.getLikesCount());
                updateStatement.setInt(2, comment.getDislikesCount());
                updateStatement.setInt(3, id);

                updateStatement.executeUpdate();
            } else throw new NullPointerException();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "Comment with id " + id + " is updated";
    }

    @Override
    public String updateCommentById(Integer id, String comment) throws NullPointerException{

        String selectSQL = "select * from comments where id = ?";
        String updateSQL = "update comments " +
                "set comment = ? " +
                "where id = ? and delete_time is null ";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);

            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next() && selectResult.getDate("delete_time") == null) {
                updateStatement.setString(1, comment);
                updateStatement.setInt(2, id);

                updateStatement.executeUpdate();
            } else throw new NullPointerException();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "Comment with id " + id + " is updated";
    }

    @Override
    public String deleteCommentById(Integer id) throws NullPointerException {

        String selectSQL = "select * from comments where id = ?";
        String deleteSQL = "update comments " +
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
        return "Comment with ID " + id + " was deleted!";
    }
}
