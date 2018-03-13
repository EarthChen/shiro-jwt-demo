package com.earthchen.spring.boot.shirojwtdemo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Data
@Entity
public class UserInfo {

    @Id
    private Long userId;

    private String username;

    private String password;

}
