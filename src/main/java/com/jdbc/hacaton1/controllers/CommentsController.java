package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import com.jdbc.hacaton1.services.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/")

public class CommentsController {

    private final CommentsService service;

    @PostMapping("create")
    public Integer createComment(@RequestBody CommentsModel comment){
        return service.createComment(comment);
    }

    @GetMapping("getByProductId/{id}")
    public List<GeneralComments> getCommentsByProductId(@PathVariable Integer id){
        return service.getCommentsByProductId(id);
    }

    @GetMapping("getByUserId/{id}")
    public List<GeneralComments> getCommentsByUserId(@PathVariable Integer id){
        return service.getCommentsByUserId(id);
    }

    @PutMapping("updateLikesDislikesById")
    public String updateCommentsLikesDislikesById(@RequestParam Integer id, @RequestBody CommentsModel comment){
        return service.updateCommentsLikesDislikesById(id, comment);
    }

    @PutMapping("updateCommentById")
    public String updateCommentById(@RequestParam Integer id, @RequestParam String comment){
        return service.updateCommentById(id, comment);
    }


    @DeleteMapping("delete")
    public String deleteCommentById(@RequestParam Integer id){
        return service.deleteCommentById(id);
    }
}
