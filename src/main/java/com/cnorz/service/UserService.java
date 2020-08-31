package com.cnorz.service;

import com.cnorz.pojo.Users;

public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param pwd
     * @return
     */
    public Users queryUserForLogin(String username, String pwd);

    /**
     * 用户注册
     * @param user
     * @return
     */
    public Users saveUser(Users user);
}
