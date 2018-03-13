package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import com.earthchen.spring.boot.shirojwtdemo.config.MessageConfig;
import com.earthchen.spring.boot.shirojwtdemo.util.HttpResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: EarthChen
 * @date: 2018/02/26
 */
public abstract class StatelessFilter extends AccessControlFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        //未认证
        if (null == subject || !subject.isAuthenticated()) {
            HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                    , MessageConfig.REST_CODE_AUTH_UNAUTHORIZED
                    , MessageConfig.REST_MESSAGE_AUTH_UNAUTHORIZED);
            //未授权
        } else {
            HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                    , MessageConfig.REST_CODE_AUTH_FORBIDDEN
                    , MessageConfig.REST_MESSAGE_AUTH_FORBIDDEN);
        }
        return false;
    }

    /**
     * 判断是否是jwt提交
     *
     * @param request
     * @return
     */
    protected boolean isJwtSubmission(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("Authorization");
        return (request instanceof HttpServletRequest) && !StringUtils.isBlank(jwt);
    }

    /**
     * 构造jwttoken
     *
     * @param request
     * @param response
     * @return
     */
    protected AuthenticationToken createJwtToken(ServletRequest request, ServletResponse response) {
        String host = request.getRemoteHost();
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("Authorization");
        return new JwtToken(jwt, host);
    }
}
