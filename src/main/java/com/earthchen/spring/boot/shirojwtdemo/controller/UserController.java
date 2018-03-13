package com.earthchen.spring.boot.shirojwtdemo.controller;

import com.earthchen.spring.boot.shirojwtdemo.dao.UserInfoDao;
import com.earthchen.spring.boot.shirojwtdemo.service.UserInfoService;
import com.earthchen.spring.boot.shirojwtdemo.util.ResultVOUtil;
import com.earthchen.spring.boot.shirojwtdemo.vo.ResultVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.UserDataHandler;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoDao userInfoDao;

    @GetMapping
    public ResultVO userList(){
        return ResultVOUtil.success(userInfoDao.findAll());

    }
}
