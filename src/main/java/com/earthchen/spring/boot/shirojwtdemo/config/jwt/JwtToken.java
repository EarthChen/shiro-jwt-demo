package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */

public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 8120601965307749615L;

    // json web token
    private String jwt;

    // 客户端IP
    private String host;

    public JwtToken(String jwt, String host) {
        this.jwt = jwt;
        this.host = host;
    }

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.jwt;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }
}
