package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "User Login and Register Interface", tags = {"Register and Login Controller"})
public class RegistLoginController extends BasicController{

    @Autowired
    private UserService userService;

    @ApiOperation(value = "User Register Interface", notes = "User register Interface")
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
        users.setPassword("");

        UsersVO usersVO = setUsersRedisSessionToken(users);

        //VO is view object, used to interact with the view layer
        return IMoocJSONResult.ok(usersVO);
    }

    @ApiOperation(value = "User Log in", notes = "User Login Interface")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users users) throws Exception {

        String username = users.getUsername();
        String password = users.getPassword();

        //设置线程睡眠 -> 改善用户体验
        Thread.sleep(3000);

        //1.用户名和密码不能为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return IMoocJSONResult.ok("User Name and Password Can not be empty...");
        }

        //2.判断用户是否存在
        Users usersResult = userService.queryUserForLogin(username,MD5Utils.getMD5Str(users.getPassword()));

        //3.返回
        if(usersResult != null) {
            usersResult.setPassword("");
            UsersVO usersVO = setUsersRedisSessionToken(usersResult);
            return IMoocJSONResult.ok(usersVO);
        } else {
            return IMoocJSONResult.errorMsg("Wrong User name or Password");
        }
    }

    @ApiOperation(value = "User log out", notes = "User logout interface")
    @ApiImplicitParam(name = "userId", value = "User id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) throws Exception {
        //Delete the user session in redis means log out
        try{
            redis.del(USER_REDIS_SESSION+":"+userId);
        } catch (Exception e) {
            return IMoocJSONResult.errorMsg("User already log out");
        }

        return IMoocJSONResult.ok(userId+" has log out");
    }

    private UsersVO setUsersRedisSessionToken(Users usersModel) {

        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION+":"+ usersModel.getId(), uniqueToken,1000*60*30);

        UsersVO usersVO = new UsersVO();

        BeanUtils.copyProperties(usersModel,usersVO);

        usersVO.setUserToken(uniqueToken);

        //VO is view object, used to interact with the view layer
        return usersVO;

    }
}
