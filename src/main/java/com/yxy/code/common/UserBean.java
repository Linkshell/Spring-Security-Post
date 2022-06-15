package com.yxy.code.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("y_user")
public class UserBean {
    public String userId;
    public String niceName;
    public String password;
    public int sex;
    public int age;
    public int vip;
    public String rank;
    public int role;
    public int status;
}
