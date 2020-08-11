package com.cnorz.controller;

import com.cnorz.pojo.Users;
import com.cnorz.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("u")
public class UserController {

    @PostMapping("/registOrLogin")
    public JSONResult registOrLogin(@RequestBody Users user) {
        // 校验用户名、密码不为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return  JSONResult.errorMsg("用户名及密码不能为空！");
        }

        return JSONResult.ok();
    }
}
