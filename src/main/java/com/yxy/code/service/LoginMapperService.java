package com.yxy.code.service;

import com.yxy.code.common.UserBean;
import com.yxy.code.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginMapperService {
    @Autowired
    LoginMapper loginMapper;
    public UserBean getUser(String uid){
        return loginMapper.getUser(uid);
    }
}
