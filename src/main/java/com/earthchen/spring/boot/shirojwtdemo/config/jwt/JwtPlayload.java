package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import lombok.Data;

import java.util.Date;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Data
public class JwtPlayload {

    private String id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createDate;

}
