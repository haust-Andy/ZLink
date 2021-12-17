package com.zzy.interceptor;


import com.alibaba.druid.util.StringUtils;
import com.zzy.pojo.entity.User;
import com.zzy.service.AccountService;
import com.zzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = accountService.verifyToken(request.getHeader("token"));

        if (user == null) {
            response.sendRedirect("/ZLink/tokenError");
            return false;
        } else {
            request.getSession().setAttribute("user", user);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
