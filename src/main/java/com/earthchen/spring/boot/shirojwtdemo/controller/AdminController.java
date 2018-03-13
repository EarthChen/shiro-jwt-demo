package com.earthchen.spring.boot.shirojwtdemo.controller;

import com.earthchen.spring.boot.shirojwtdemo.util.ResultVOUtil;
import com.earthchen.spring.boot.shirojwtdemo.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: EarthChen
 * @date: 2018/02/26
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ResultVO list(){
        return ResultVOUtil.success("123456");
    }
}
