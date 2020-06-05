package com.fenzx.ldy.antiRobot;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityInteceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        String userAgent=request.getHeader("User-Agent");
//        System.out.println("执行了拦截器");
//        System.out.println(userAgent);
        userAgent=userAgent.split("/")[0];
        if (BlackList.verify(userAgent)) {
            return true;
        } else {
            response.sendError(412);
            return false;
        }
    }
}
