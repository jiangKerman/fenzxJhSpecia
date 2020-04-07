package com.fenzx.Config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("teacher" + session.getAttribute("teacher")
                + "admin" + session.getAttribute("admin")
                + "student" + session.getAttribute("student"));
        if (session.getAttribute("teacher") == null
                && session.getAttribute("admin") == null
                && session.getAttribute("student") == null) {

            //设置缓存区编码为UTF-8编码格式
            response.setCharacterEncoding("UTF-8");

//在响应中主动告诉浏览器使用UTF-8编码格式来接收数据
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.getWriter().print(
                    "<html><body><script type='text/javascript'>"
                            + "alert('会话超时，请重新登陆！');"
                            + "location.href=\"login\";"
                            + "</script></body></html>");

//            response.sendRedirect("login");
            return false;
        }
        return true;
    }
}
