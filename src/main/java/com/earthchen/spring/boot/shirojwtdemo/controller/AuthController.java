package com.earthchen.spring.boot.shirojwtdemo.controller;

import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import com.earthchen.spring.boot.shirojwtdemo.service.UserInfoService;
import com.earthchen.spring.boot.shirojwtdemo.util.JwtUtil;
import com.earthchen.spring.boot.shirojwtdemo.util.ResultVOUtil;
import com.earthchen.spring.boot.shirojwtdemo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public ResultVO login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        // 去数据库校验账号密码
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        return ResultVOUtil.success(jwtUtil.generateToken(userInfo));
    }
}
