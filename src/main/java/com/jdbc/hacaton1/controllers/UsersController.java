package com.jdbc.hacaton1.controllers;


import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import com.jdbc.hacaton1.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService service;

    @GetMapping("getAllUsers")
    private List<UsersModel> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("getUserById:{id}")
    public PrivateUserModel getUserById(@PathVariable Integer id){
        return service.getUserById(id);
    }

    @PostMapping("createUser")
    public Integer createUser(@RequestBody UsersModel userToCreate) {
        return service.createUser(userToCreate.getLogin(), userToCreate.getPassword());
    }


}
