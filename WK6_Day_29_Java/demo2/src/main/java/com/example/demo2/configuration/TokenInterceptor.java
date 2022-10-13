package com.example.demo2.configuration;

import com.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    //before goes to controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token  = request.getHeader("token");
        String user_id = request.getHeader("user_id");
        //add specific url
        String current_url = request.getRequestURL().toString();
        System.out.println("Current URL: " + current_url);
        if(current_url.contains("login") || current_url.contains("logout")){
            return true; //if login or logout, then go straight without checking token in header
        }

        Integer int_user_id=Integer.parseInt(user_id);
        if (token.equals("")){
            throw new Exception("Please send the token");
        }
        if(user_id.equals("")){
            throw new Exception("Please send the user_id");
        }
        if(userService.validateToken(token, int_user_id)){
            return true;
        }else {
            return false;
        }
    }

    //before sending response/after completion of controller
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //after sending the response
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
