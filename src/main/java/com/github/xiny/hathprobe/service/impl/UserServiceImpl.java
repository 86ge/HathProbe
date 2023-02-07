package com.github.xiny.hathprobe.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiny.hathprobe.domain.User;
import com.github.xiny.hathprobe.service.UserService;
import com.github.xiny.hathprobe.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86459
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-02-06 22:31:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    public User login(String userName, String password) throws IllegalArgumentException {
        System.out.println(userName);
        User user = getById(userName);
        if (user==null||!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("账号或密码错误");
        }
        StpUtil.login(userName);
        return user;
    }

}




