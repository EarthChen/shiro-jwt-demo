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
import java.util.stream.Stream;

/**
 * jwt角色过滤器
 *
 * @author: EarthChen
 * @date: 2018/02/26
 */
@Slf4j
public class JwtRolesFilter extends StatelessFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if ((null == subject || !subject.isAuthenticated()) && isJwtSubmission(request)) {
            AuthenticationToken token = createJwtToken(request, response);
            try {
                subject = getSubject(request, response);
                subject.login(token);
                return this.checkRoles(subject, mappedValue);
            } catch (AuthenticationException e) {
                log.error(request.getRemoteHost() + " JWT鉴权  " + e.getMessage());
                HttpResponseUtil.restFailed(WebUtils.toHttp(response)
                        , MessageConfig.REST_CODE_AUTH_UNAUTHORIZED, e.getMessage());
            }
        }
        return false;
    }

    /**
     * 检查角色
     *
     * @param subject
     * @param mappedValue
     * @return
     */
    protected boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        return Stream.of(rolesArray)
                .anyMatch(role -> subject.hasRole(role));
    }
}
