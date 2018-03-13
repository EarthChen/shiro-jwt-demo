package com.earthchen.spring.boot.shirojwtdemo.service;

import com.earthchen.spring.boot.shirojwtdemo.dao.UserInfoDao;
import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        return userInfoDao.findOne(userId);
    }
}
