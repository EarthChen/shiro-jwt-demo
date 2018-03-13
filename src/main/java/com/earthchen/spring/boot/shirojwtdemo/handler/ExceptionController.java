package com.earthchen.spring.boot.shirojwtdemo.handler;

import com.earthchen.spring.boot.shirojwtdemo.util.ResultVOUtil;
import com.earthchen.spring.boot.shirojwtdemo.vo.ResultVO;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: EarthChen
 * @date: 2018/02/26
 */
@RestControllerAdvice
public class ExceptionController {


//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(ShiroException.class)
//    public ResultVO handle401(ShiroException e) {
//        return ResultVOUtil.error(401, e.getMessage());
//    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResultVO handle401(ShiroException e) {
        return ResultVOUtil.error(401, e.getMessage());
    }
}
