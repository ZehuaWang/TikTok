package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistLoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users users) throws Exception { //Users为Json对象

        //Business logic of User Register
        if(StringUtils.isEmpty(users.getUsername()) || StringUtils.isEmpty(users.getPassword())) {
            return IMoocJSONResult.errorMsg("User name and password can not be empty");
        }

        //User name and password can not be empty
        boolean usernameIsExist = userService.queryUsernameIsExist(users.getUsername());

        //Decide the user if exit
        if(!usernameIsExist) {
            users.setNickname(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFansCounts(0);
            users.setReceiveLikeCounts(0);
            users.setFollowCounts(0);
            userService.saveUser(users);
        } else {
            return IMoocJSONResult.errorMsg("User name already exist");
        }

        return IMoocJSONResult.ok();
    }

}
