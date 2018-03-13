package com.earthchen.spring.boot.shirojwtdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: EarthChen
 * @date: 2018/02/26
 */
@Slf4j
public class HttpResponseUtil {

    /**
     * REST失败响应
     */
    public static void restFailed(HttpServletResponse response, String code, String message) {
        respondJson(response, HttpServletResponse.SC_BAD_REQUEST, code, message);
    }


    /**
     * JSON响应
     */
    private static void respondJson(HttpServletResponse response
            , int respondStatus, String code, String message) {
//        response.reset();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        response.setStatus(respondStatus);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String json = new ObjectMapper().writeValueAsString(map);
            out.write(json);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
