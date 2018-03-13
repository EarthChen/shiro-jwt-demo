package com.earthchen.spring.boot.shirojwtdemo.service;

import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
public interface UserInfoService {

    UserInfo getUserInfoByUserId(Long userId);


}
