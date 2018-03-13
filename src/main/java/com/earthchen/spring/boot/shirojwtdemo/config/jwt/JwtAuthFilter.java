package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import com.earthchen.spring.boot.shirojwtdemo.config.MessageConfig;
import com.earthchen.spring.boot.shirojwtdemo.util.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * jwt认证过滤器
 *
 * @author: EarthChen
 * @date: 2018/02/26
 */
@Slf4j
public class JwtAuthFilter extends StatelessFilter {

    /**
     * 访问允许
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (null != getSubject(request, response)
                && getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return false;
    }

    /**
     * 拒绝
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isJwtSubmission(request)) {
            AuthenticationToken token = createJwtToken(request, response);
            try {
                Subject subject = getSubject(request, response);
                subject.login(token);
                return true;
            } catch (AuthenticationException e) {
                log.error(request.getRemoteHost() + " JWT认证  " + e.getMessage());
                HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                        , MessageConfig.REST_CODE_AUTH_UNAUTHORIZED, e.getMessage());
            }
        }
        HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                , MessageConfig.REST_CODE_AUTH_UNAUTHORIZED
                , MessageConfig.REST_MESSAGE_AUTH_UNAUTHORIZED);
        return false;
    }

}
