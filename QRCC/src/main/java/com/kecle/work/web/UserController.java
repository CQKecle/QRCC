package com.kecle.work.web;

import com.alibaba.fastjson.JSON;
import com.kecle.work.pojo.LockAndManager;
import com.kecle.work.pojo.User;
import com.kecle.work.pojo.UserAndLock;
import com.kecle.work.service.LockService;
import com.kecle.work.service.LocksAndManagerService;
import com.kecle.work.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserService userService1;
    @Resource
    private UserService userService2;
    @Resource
    private LockService lockService;
    @Resource
    private LocksAndManagerService locksAndManagerService;

    private static Logger logger = Logger.getLogger(UserController.class);



    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMsg = new HashMap<>();
        String ID = request.getParameter("ID");
        String password = request.getParameter("password");
        String deviceID = request.getParameter("DEVICE_ID");
        User user;
        List<UserAndLock> userAndLock = null;
        List<User> users = null;
        List<LockAndManager> lockAndManagers = new ArrayList<>();
        try{
            user = userService.login(ID,password);
            userService.writeDeviceID(ID,deviceID);
        }catch (Exception e){
            returnMsg.put("return_code", "2");
            returnMsg.put("msg", "UNKNOWN_ERROR");
            logger.error("数据库查询失败！", e);
            return returnMsg;
        }

        if (user != null){
            int power;
            int is_manager = user.getIsmanager();
            String user_id = user.getId();
            String belongArea = user.getBelongarea();


            if (is_manager == 1){
                power = 1;
            }else{
                power = 0;
            }

//            通过user_id和belongArea遍历出用户所映射的锁以及所属单位下的所有成员信息
            try {
                userAndLock = lockService.ergodicLocks(user_id);
                users = userService1.searchBelongArea(belongArea);
                for(UserAndLock lock : userAndLock){
                    logger.info(lock.getLockid()+lock.getUserid());
                }

            }catch (Exception e){
                e.printStackTrace();
                logger.info("锁查询失败！");
            }

//            通过lock_id遍历出所有的锁信息
            for (UserAndLock locks:userAndLock){
                LockAndManager lockAndManager = locksAndManagerService.searchByLockId(locks.getLockid());
                lockAndManagers.add(lockAndManager);
                logger.info(lockAndManager.getLockname()+lockAndManager.getBymanager()+lockAndManager.getDescribe());
            }

            returnMsg.put("return_code", "1");
            returnMsg.put("msg", "USER_LOGIN_SUCCESS");
            returnMsg.put("ID",user_id);
            returnMsg.put("password",user.getPassword());
            returnMsg.put("username",user.getUsername());
            returnMsg.put("belong_area",belongArea);
            returnMsg.put("born_area",user.getBornarea());
            returnMsg.put("sex",user.getSex());
            returnMsg.put("image_id",user.getImageid());
            returnMsg.put("autograph",user.getAutograph());
            returnMsg.put("power",power);
            returnMsg.put("time",user.getTime());
            returnMsg.put("people",users);
            returnMsg.put("locks",lockAndManagers);
        }
        else{
            returnMsg.put("return_code","0");
            returnMsg.put("msg","数据库不存在这个用户");
        }
        return returnMsg;
    }
    @ResponseBody
    @RequestMapping(value = "checkState",method =RequestMethod.POST)
    public String checkState(HttpServletRequest request,HttpServletResponse response){
        String ID = request.getParameter("ID");
        logger.info("user_id                   -->" + ID );
        User user = null;
        String device_id;
        try {
            user = userService2.checkDeviceId(ID);

        }catch (Exception e){
            e.printStackTrace();
        }
        if(user != null){
            device_id = user.getDeviceid();
        }else
            device_id = "操作失败";

        return device_id;
    }
    @ResponseBody
    @RequestMapping(value = "enroll",method =RequestMethod.POST)
    public Map<String, Object> enroll(HttpServletRequest request,HttpServletResponse response){
        int back = 0;
        Map<String, Object> returnMsg = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user;
        try{
            user = userService.checkUsername(username);
            if (user == null) {
                back = userService.insertNewUser(username, password);
            }
            else{
                returnMsg.put("message","用户名已经存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (back == 1) {
            returnMsg.put("return_code",back);
            returnMsg.put("msg","Insert success");
        }
        else {
            returnMsg.put("return_code",back);
        }
        return returnMsg;
    }
}
