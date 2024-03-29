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

    public Integer createUser(UsersModel userToCreate){
        return dao.createUser(userToCreate);
    }

    public PrivateUserModel getUserById(Integer id){
      return dao.getUserById(id);
    }

    public String updateUserById(Integer id, UsersModel user){
        return dao.updateUserById(id, user);
    }
    public String updateUsersRateById(Integer id, Integer rate){
        return dao.updateUsersRateById(id, rate);
    }
    public Integer getIdByLoginAndPassword(String login, String password){
        return dao.getIdByLoginAndPassword(login, password);
    }
    public String deleteUserById(Integer id){
        return dao.deleteUserById(id);
    }
}
