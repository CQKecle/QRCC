package com.kecle.work.service;

import com.kecle.work.pojo.User;

import java.util.List;

public interface UserService {


    User login(String ID, String password);
    User checkDeviceId(String ID);
    int writeDeviceID(String ID,String deviceID);
    User checkUsername(String username);
    int insertNewUser(String name,String psw);
    List<User> searchBelongArea(String belongArea);
}
