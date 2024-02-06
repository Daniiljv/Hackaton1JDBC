package com.jdbc.hacaton1.services;

import com.jdbc.hacaton1.dao.CommentsDao;
import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentsService {

    private final CommentsDao dao;
    public Integer createComment(CommentsModel comment){
        return dao.createComment(comment);
    }

    public List<GeneralComments> getCommentsByProductId(Integer id){
        return dao.getCommentsByProductId(id);
    }

    public List<GeneralComments> getCommentsByUserId(Integer id){
        return dao.getCommentsByUserId(id);
    }

    public void updateComment(Integer id, CommentsModel comment){
        dao.updateComment(id, comment);
    }

    public String deleteCommentById(Integer id){
        return dao.deleteCommentById(id);
    }
}
