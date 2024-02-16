package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.enams.ResultCode;
import com.jdbc.hacaton1.enams.ResultCodeAPI;
import com.jdbc.hacaton1.models.CommentsModel;
import com.jdbc.hacaton1.models.GeneralComments;
import com.jdbc.hacaton1.models.responseMessageAPI.ResponseMessageAPI;
import com.jdbc.hacaton1.services.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/")

public class CommentsController {

    private final CommentsService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment created successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to add comment to database",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road creates comment")
    @PostMapping("create")
    public ResponseMessageAPI<Integer> createComment(@RequestBody CommentsModel comment){
        try {
            return new ResponseMessageAPI<>(
                    service.createComment(comment),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (RuntimeException rte) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.FAIL,
                    rte.getClass().getSimpleName(),
                    rte.getMessage(),
                    ResultCode.FAIL.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All comments of product returned successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any comments for this product",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road returns all comments for special product by product id")
    @GetMapping("getByProductId/{id}")
    public ResponseMessageAPI<List<GeneralComments>> getCommentsByProductId(@PathVariable Integer id){
        try {
            return new ResponseMessageAPI<>(
                    service.getCommentsByProductId(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All users comments returned successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any comments left by user",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road returns all comments of special user by users id")
    @GetMapping("getByUserId/{id}")
    public ResponseMessageAPI<List<GeneralComments>> getCommentsByUserId(@PathVariable Integer id){
        try {
            return new ResponseMessageAPI<>(
                    service.getCommentsByUserId(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments likes and dislikes updated successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any comments with this id",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road updates count of likes and dislikes")
    @PutMapping("updateLikesDislikesById")
    public ResponseMessageAPI<String> updateCommentsLikesDislikesById(@RequestParam Integer id, @RequestBody CommentsModel comment){
        try {
            return new ResponseMessageAPI<>(
                    service.updateCommentsLikesDislikesById(id, comment),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments text updated successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any comments with this id",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road updates text of the special comment")
    @PutMapping("updateCommentById")
    public ResponseMessageAPI<String> updateCommentById(@RequestParam Integer id, @RequestParam String comment){
        try {
            return new ResponseMessageAPI<>(
                    service.updateCommentById(id, comment),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment deleted successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any comments with this id",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road deletes comment by id")
    @DeleteMapping("delete")
    public ResponseMessageAPI<String> deleteCommentById(@RequestParam Integer id){
        try {
            return new ResponseMessageAPI<>(
                    service.deleteCommentById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }
}
