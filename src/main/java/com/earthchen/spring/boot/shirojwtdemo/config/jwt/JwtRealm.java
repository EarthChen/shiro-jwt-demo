package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import com.earthchen.spring.boot.shirojwtdemo.domain.UserInfo;
import com.earthchen.spring.boot.shirojwtdemo.service.UserInfoService;
import com.earthchen.spring.boot.shirojwtdemo.service.UserInfoServiceImpl;
import com.earthchen.spring.boot.shirojwtdemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public Class<?> getAuthenticationTokenClass() {
        //此Realm只支持JwtToken
        return JwtToken.class;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String jwt = (String) jwtToken.getPrincipal();

        JwtPlayload jwtPlayload = new JwtPlayload();
        try {
            jwtPlayload = jwtUtil.getJwtPlayloadFromToken(jwt);
        } catch (AuthenticationException e) {
            log.info("jwt令牌={}解析错误", jwt);
            throw new AuthenticationException(e.getMessage());
        }
//        UserInfo userInfo = userInfoService.getUserInfoByUserId(jwtPlayload.getUserId());
//        if (userInfo == null) {
//            log.info("jwt令牌解析出的用户不存在");
//            throw new AuthenticationException("JWT 令牌错误: 没有这个用户");
//        }


        // 如果要使token只能使用一次，此处可以过滤并缓存jwtPlayload.getId()
        // 可以做签发方验证
        // 可以做接收方验证
        return new SimpleAuthenticationInfo(jwtPlayload, Boolean.TRUE, getName());
    }

    /**
     * 授权,JWT已包含访问主张只需要解析其中的主张定义就行了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 获取jwt负载信息
        JwtPlayload jwtPlayload = (JwtPlayload) principals.getPrimaryPrincipal();
        // 构造授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addRole(user.getRole());
//        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
//        simpleAuthorizationInfo.addStringPermissions(permission);

        Long userId = jwtPlayload.getUserId();
        // 查数据库或者缓存设置权限
        simpleAuthorizationInfo.addRole("user");
        simpleAuthorizationInfo.addStringPermission("add");
        return simpleAuthorizationInfo;

    }
}



