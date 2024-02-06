package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;

import java.util.List;


public interface CommentsDao {


    Integer createComment(CommentsModel comment);

    List<GeneralComments> getCommentsByProductId(Integer id);

    List<GeneralComments> getCommentsByUserId(Integer id);

    void updateComment(Integer id, CommentsModel comment);

    String deleteCommentById(Integer id);
}
