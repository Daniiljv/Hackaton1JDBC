package com.jdbc.hacaton1.controllers;


import com.jdbc.hacaton1.enams.ResultCode;
import com.jdbc.hacaton1.enams.ResultCodeAPI;
import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.responseMessageAPI.ResponseMessageAPI;
import com.jdbc.hacaton1.models.UsersModel;
import com.jdbc.hacaton1.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "UsersController", description = "There are all roads for working with users")
@RestController
@RequiredArgsConstructor
@RequestMapping("users/")
public class UsersController {

    private final UsersService service;
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of users returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There aren't any users",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road returns list of all users")
    @GetMapping("getAll")
    private ResponseMessageAPI<List<UsersModel>> getAllUsers() {
        try {
            return new ResponseMessageAPI<>(
                    service.getAllUsers(),
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
                    description = "User returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There isn't any users with this id",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road returns user with special id")
    @GetMapping("getById/{id}")
    public ResponseMessageAPI<PrivateUserModel> getUserById(@PathVariable Integer id){
        try {
            return new ResponseMessageAPI<>(
                    service.getUserById(id),
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
                    description = "Users id returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There isn't any users with this login and password",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road returns users id with special log and password")
    @GetMapping("getIdByLoginAndPassword")
    public ResponseMessageAPI<Integer> getIdByLoginAndPassword(@RequestBody UsersModel user) {
        try{
            return new ResponseMessageAPI<>(
                    service.getIdByLoginAndPassword(user),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode());
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
                    description = "User created successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to add user to database",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road creates user by unique login and password")
    @PostMapping("create")
    public ResponseMessageAPI<Integer> createUser(@RequestBody UsersModel userToCreate) {
        try{
            return new ResponseMessageAPI<>(
                    service.createUser(userToCreate),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode());
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
                    description = "User created successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with special id is not found",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road updates user by special id")
    @PutMapping("updateById")
    public ResponseMessageAPI<String> updateUserById(@RequestParam Integer id, @RequestBody UsersModel user){
        try{
            return new ResponseMessageAPI<>(
                    service.updateUserById(id, user),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode());
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
                    description = "Users rate updated successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with special id is not found",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road updates users rate by special id")
    @PutMapping("updateRateById")
    public ResponseMessageAPI<String> updateUsersRateById(@RequestParam Integer id, @RequestParam Integer rate){
        try{
            return new ResponseMessageAPI<>(
                    service.updateUsersRateById(id, rate),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode());
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
                    description = "User deleted successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with special id is not found",
                    content = {@Content(mediaType = "application/json")})
    })
    @Operation(summary = "This road deletes user by special id")
    @DeleteMapping("delete")
    public ResponseMessageAPI<String> deleteUserById(@RequestParam Integer id){
        try{
            return new ResponseMessageAPI<>(
                    service.deleteUserById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode());
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
