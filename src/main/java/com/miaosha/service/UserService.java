package com.miaosha.service;

import com.miaosha.dao.UserDOMapper;
import com.miaosha.service.impl.model.UserModel;

public interface UserService {
    //通过用户ID获取用户
    UserModel getUserById(Integer id);
}
