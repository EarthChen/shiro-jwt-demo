package com.earthchen.spring.boot.shirojwtdemo.controller;

import com.earthchen.spring.boot.shirojwtdemo.util.ResultVOUtil;
import com.earthchen.spring.boot.shirojwtdemo.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: EarthChen
 * @date: 2018/02/25
 */
@RestController
public class shiroController {

    @GetMapping("/401")
    public ResultVO un401() {
        return ResultVOUtil.error(401, "未登录，请登录后在尝试");
    }
}
