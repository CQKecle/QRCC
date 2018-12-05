package com.kecle.work.serviceimpl;

import com.kecle.work.dao.UserMapper;
import com.kecle.work.pojo.User;
import com.kecle.work.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User login(String ID, String password) {
        logger.info("user_id-->" + ID + "  password-->" + password);
        return userMapper.selectByNameAndPsw(ID, password);
    }

    @Override
    public User checkDeviceId(String ID) {
        logger.info("implement   user_id                   -->" + ID );
        return userMapper.checkDeviceId(ID);
    }

    @Override
    public int writeDeviceID(String ID, String deviceID) {
        return userMapper.writeDeviceID(ID,deviceID);
    }

    @Override
    public int insertNewUser(String name,String psw){
        return userMapper.insertNewUser(name,psw);
    }

    @Override
    public List<User> searchBelongArea(String belongArea) {
        return userMapper.searchBelongArea(belongArea);
    }

    @Override
    public User checkUsername(String username){return userMapper.checkUsername(username);}
}
