package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;

import java.util.List;


public interface CommentsDao {


    Integer createComment(CommentsModel comment);

    List<GeneralComments> getCommentsByProductId(Integer id);

    List<GeneralComments> getCommentsByUserId(Integer id);

    String updateCommentsLikesDislikesById(Integer id, CommentsModel comment);

    String updateCommentByID(Integer id, String comment);

    String deleteCommentById(Integer id);
}
