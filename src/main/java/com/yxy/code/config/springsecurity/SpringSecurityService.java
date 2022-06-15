package com.yxy.code.config.springsecurity;

import com.yxy.code.common.UserBean;
import com.yxy.code.service.LoginMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SpringSecurityService implements UserDetailsService {
    @Autowired
    LoginMapperService loginMapperService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean user = loginMapperService.getUser(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(user.getUserId(),user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(user.getRole())));
    }
}
