package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import com.jdbc.hacaton1.services.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class CommentsController {

    private final CommentsService service;

    @PostMapping("createComment")
    public Integer createComment(@RequestBody CommentsModel comment){
        return service.createComment(comment);
    }

    @GetMapping("getCommentsByProductId:{id}")
    public List<GeneralComments> getCommentsByProductId(@PathVariable Integer id){
        return service.getCommentsByProductId(id);
    }

    @GetMapping("getCommentsByUserId:{id}")
    public List<GeneralComments> getCommentsByUserId(@PathVariable Integer id){
        return service.getCommentsByUserId(id);
    }
}
