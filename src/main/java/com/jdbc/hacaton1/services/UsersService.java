package com.jdbc.hacaton1.services;

import com.jdbc.hacaton1.dao.UsersDao;
import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersDao dao;

    public List<UsersModel> getAllUsers() {
        return dao.getAllUsers();
    }

    public Integer createUser(String login, String password){
        return dao.createUser(login, password);
    }

    public PrivateUserModel getUserById(Integer id){
      return dao.getUserById(id);
    }

}
