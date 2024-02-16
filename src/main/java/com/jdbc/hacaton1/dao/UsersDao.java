package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.PrivateUserModel;
import com.jdbc.hacaton1.models.UsersModel;
import java.util.List;


public interface UsersDao {

    List<UsersModel> getAllUsers();

    Integer createUser(UsersModel userToCreate);

    PrivateUserModel getUserById(Integer id);

    String updateUserById(Integer id, UsersModel user);

    String updateUsersRateById(Integer id, Integer rate);

    Integer getIdByLoginAndPassword(UsersModel user);

    String deleteUserById(Integer id);
}
