package com.kecle.work.serviceimpl;

import com.kecle.work.dao.LockAndManagerMapper;
import com.kecle.work.pojo.LockAndManager;
import com.kecle.work.service.LocksAndManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

@Service
public class LocksAndManagerServiceImpl implements LocksAndManagerService {

    @Autowired
    LockAndManagerMapper lockAndManagerMapper;

    private static Logger logger = Logger.getLogger(LocksAndManagerServiceImpl.class);

    @Override
    public LockAndManager searchByLockId(String lock_id) {
        logger.info("-----------------LocksAndManagerServiceImpl"+lock_id);
        return lockAndManagerMapper.selectLockByLockId(lock_id);
    }
}
