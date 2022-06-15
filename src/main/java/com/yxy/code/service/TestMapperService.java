package com.yxy.code.service;

import com.yxy.code.common.UserBean;
import com.yxy.code.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestMapperService {
    @Autowired
    TestMapper testMapper;
    public UserBean getTest(){
        return testMapper.getTest();
    }
}
