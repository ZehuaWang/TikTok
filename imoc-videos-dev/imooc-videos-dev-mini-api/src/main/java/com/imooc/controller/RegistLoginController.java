package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.utils.IMoocJSONResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistLoginController {

    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users users) { //Users为Json对象

        //Business logic of User Register
        if(StringUtils.isEmpty(users.getUsername()) || StringUtils.isEmpty(users.getPassword())) {
            return IMoocJSONResult.errorMsg("User name and password can not be empty");
        }

        //User name and password can not be empty



        //Decide the user if exit

        return IMoocJSONResult.ok();
    }

}
