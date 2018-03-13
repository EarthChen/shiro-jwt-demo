package com.earthchen.spring.boot.shirojwtdemo.config.jwt;

import com.earthchen.spring.boot.shirojwtdemo.config.MessageConfig;
import com.earthchen.spring.boot.shirojwtdemo.util.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * jwt权限过滤器
 *
 * @author: EarthChen
 * @date: 2018/02/26
 */
@Slf4j
public class JwtPermsFilter extends StatelessFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if ((null == subject || !subject.isAuthenticated()) && isJwtSubmission(request)) {
            AuthenticationToken token = createJwtToken(request, response);
            try {
                subject = getSubject(request, response);
                subject.login(token);
                return this.checkPerms(subject, mappedValue);
            } catch (AuthenticationException e) {
                log.error(request.getRemoteHost() + " jwt鉴权  " + e.getMessage());
                HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                        , MessageConfig.REST_CODE_AUTH_UNAUTHORIZED, e.getMessage());
            }
        }
        return false;
    }

    /**
     * 检查权限
     *
     * @param subject
     * @param mappedValue
     * @return
     */
    protected boolean checkPerms(Subject subject, Object mappedValue) {
        String[] perms = (String[]) mappedValue;
        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
        }
        return isPermitted;
    }
}