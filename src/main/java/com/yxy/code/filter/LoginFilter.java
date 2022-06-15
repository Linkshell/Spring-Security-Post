package com.yxy.code.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String method = request.getMethod();

        // 仅使用POST方法提交
        if(!"POST".equalsIgnoreCase(method)){
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            sb.append(inputStr);
        }
        JSONObject jsonObject = JSON.parseObject(sb.toString());
        // 从请求中获取 用户名及命名
        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();
        // 生成 username+password 形式的 token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 交给内部 AuthenticationManager 去认证，并返回 Authentication
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
