package com.yxy.code.controller;

import com.yxy.code.common.UserBean;
import com.yxy.code.service.TestMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestMapperService testMapperService;
    @GetMapping("/test01")
    @PreAuthorize("hasAuthority('1')")
    public String test01(){
        return "管理员";
    }

    @GetMapping("/test02")
    public UserBean test02(){
        return testMapperService.getTest();
    }

    @PostMapping("/user/login")
    public UserBean test03(){
        return testMapperService.getTest();
    }
}
