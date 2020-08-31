package com.cnorz.controller;

import com.cnorz.pojo.Users;
import com.cnorz.pojo.vo.UsersVO;
import com.cnorz.service.UserService;
import com.cnorz.utils.JSONResult;
import com.cnorz.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("u")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registOrLogin")
    public JSONResult registOrLogin(@RequestBody Users user) throws Exception{
        // 校验用户名、密码不为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return  JSONResult.errorMsg("用户名及密码不能为空！");
        }

        // 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        Users userResult = null;

        if(usernameIsExist) {
            // 登录
            userResult = userService.queryUserForLogin(user.getUsername(),
                    MD5Utils.getMD5Str(user.getPassword()));
            if(userResult == null) {
                return JSONResult.errorMsg("用户名或密码错误");
            }
        } else {
            // 注册
            user.setNickname(user.getUsername());
            user.setFaceImage("");
            user.setFaceImageBig("");
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            userResult = userService.saveUser(user);
        }
        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userResult, userVO);

        return JSONResult.ok(userVO);
    }
}
