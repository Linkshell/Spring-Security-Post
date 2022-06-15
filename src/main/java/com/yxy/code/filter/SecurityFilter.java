package com.yxy.code.filter;

import com.yxy.code.bean.UserDto;
import com.yxy.code.common.UserBean;
import com.yxy.code.service.LoginMapperService;
import com.yxy.code.utils.jjwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LoginMapperService loginMapperService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims = JwtUtils.ParseJwt(token);
        String id = claims.getId();
        UserBean user = loginMapperService.getUser(id);

        UserDto admin = new UserDto(user.getUserId(), user.getPassword());
        System.out.println(String.valueOf(user.getRole()));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin,null, AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(user.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
