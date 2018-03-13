package com.earthchen.spring.boot.shirojwtdemo.dao;

import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
}
