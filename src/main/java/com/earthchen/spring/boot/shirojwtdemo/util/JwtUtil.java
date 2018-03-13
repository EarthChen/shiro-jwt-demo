package com.earthchen.spring.boot.shirojwtdemo.util;

import com.earthchen.spring.boot.shirojwtdemo.config.jwt.JwtPlayload;
import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import io.jsonwebtoken.*;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Component
public class JwtUtil {

    /**
     * 创建时间
     */
    private static final String CLAIM_KEY_CREATE_DATE = "created_date";

    /**
     * 令牌id
     */
    private static final String CLAIM_KEY_ID = "id";

    /**
     * 用户名
     */
    private static final String CLAIM_KEY_USERNAME = "username";

    /**
     * 用户id
     */
    private static final String CLAIM_KEY_USERID = "userId";


    @Value("${shiro.jwt.secret}")
    private String secret;

    @Value("${shiro.jwt.expiration}")
    private Long expiration;

    /**
     * 生成token
     *
     * @param userInfo
     * @return
     */
    public String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, UUID.randomUUID());
        claims.put(CLAIM_KEY_USERID, userInfo.getUserId());
        claims.put(CLAIM_KEY_USERNAME, userInfo.getUsername());
        claims.put(CLAIM_KEY_CREATE_DATE, new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 生成过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中解析出jwt负载
     *
     * @param jwt
     * @return
     */
    public JwtPlayload getJwtPlayloadFromToken(String jwt) {
        if (jwt.isEmpty()) {
            throw new AuthenticationException("没有jwt令牌");
        }
        JwtPlayload jwtPlayload = new JwtPlayload();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(jwt)
                    .getBody();
            jwtPlayload = new JwtPlayload();
            jwtPlayload.setId(claims.get(CLAIM_KEY_ID).toString());
            jwtPlayload.setUserId(Long.valueOf(String.valueOf(claims.get(CLAIM_KEY_USERID))));
            jwtPlayload.setUsername(claims.get(CLAIM_KEY_USERNAME).toString());// 用户名
            jwtPlayload.setCreateDate(new Date(Long.parseLong(claims.get(CLAIM_KEY_CREATE_DATE).toString())));
//            jwtPlayload.setIssuer(claims.getIssuer());// 签发者
//            jwtPlayload.setIssuedAt(claims.getIssuedAt());// 签发时间
//            jwtPlayload.setAudience(claims.getAudience());// 接收方
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("JWT 令牌过期:" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException("JWT 令牌无效:" + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("JWT 令牌格式错误:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("JWT 令牌参数异常:" + e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationException("JWT 令牌错误:" + e.getMessage());
        }
        return jwtPlayload;

    }
}
