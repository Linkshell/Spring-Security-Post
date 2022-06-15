package com.yxy.code.mapper;

import com.yxy.code.common.UserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    UserBean getUser(String uid);
}
