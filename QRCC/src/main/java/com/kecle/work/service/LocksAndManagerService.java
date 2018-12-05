package com.kecle.work.service;

import com.kecle.work.pojo.LockAndManager;

public interface LocksAndManagerService {
    LockAndManager searchByLockId(String lock_id);

}
