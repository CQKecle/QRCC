package com.kecle.work.service;

import com.kecle.work.pojo.UserAndLock;

import java.util.List;

public interface LockService {
    List<UserAndLock> ergodicLocks(String user_id);
}
