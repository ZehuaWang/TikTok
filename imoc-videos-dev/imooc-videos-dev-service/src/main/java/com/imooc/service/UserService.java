package com.imooc.service;

import com.imooc.pojo.Users;

public interface UserService {
    /**
     * @Description Decide if the user name exits
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * @Description Save User -> User register
     * @param users
     */
    public void saveUser(Users users);
}
