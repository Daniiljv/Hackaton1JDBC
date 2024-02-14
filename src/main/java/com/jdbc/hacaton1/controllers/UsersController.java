package com.jdbc.hacaton1.controllers;


import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import com.jdbc.hacaton1.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/")
public class UsersController {

    private final UsersService service;

    @GetMapping("getAll")
    private List<UsersModel> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("getById/{id}")
    public PrivateUserModel getUserById(@PathVariable Integer id){
        return service.getUserById(id);
    }

    @GetMapping("getIdByLoginAndPassword")
    public Integer getIdByLoginAndPassword(@RequestBody UsersModel user) {
        return service.getIdByLoginAndPassword(user);
    }
    @PostMapping("create")
    public Integer createUser(@RequestBody UsersModel userToCreate) {
        return service.createUser(userToCreate.getLogin(), userToCreate.getPassword());
    }

    @PutMapping("updateById")
    public String updateUserById(@RequestParam Integer id, @RequestBody UsersModel user){
        return service.updateUserById(id, user);
    }

    @PutMapping("updateRateById")
    public String updateUsersRateById(@RequestParam Integer id, @RequestParam Integer rate){
        return service.updateUsersRateById(id, rate);
    }
    @DeleteMapping("delete")
    public String deleteUserById(@RequestParam Integer id){
        return service.deleteUserById(id);
    }
}
