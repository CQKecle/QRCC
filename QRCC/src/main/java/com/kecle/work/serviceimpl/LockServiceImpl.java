package com.kecle.work.serviceimpl;

import com.kecle.work.dao.UserAndLockMapper;
import com.kecle.work.pojo.UserAndLock;
import com.kecle.work.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockServiceImpl implements LockService {

    @Autowired
    private UserAndLockMapper userAndLockMapper;

    @Override
    public List<UserAndLock> ergodicLocks(String user_id) {
        return userAndLockMapper.selectByUserId(user_id);
    }
}
