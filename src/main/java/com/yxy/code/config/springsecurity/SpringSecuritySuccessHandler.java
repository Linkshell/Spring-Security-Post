package com.yxy.code.config.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxy.code.utils.jjwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringSecuritySuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        System.out.println("成");
        Map<Object, Object> map = new HashMap<>();
        map.put("success",false);
        map.put("message","验签失败");

        String jsonMap = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = JwtUtils.CreateJwt(authentication.getName());
        redisTemplate.opsForValue().set(authentication.getName(),token);
        Map<Object, Object> map = new HashMap<>();
        map.put("code",200);
        map.put("token",token);
        map.put("message","登录成功");

        String jsonMap = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
    }
}
