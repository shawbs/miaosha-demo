package com.miaosha.service;

import com.miaosha.dao.UserDOMapper;
import com.miaosha.error.BusinessException;
import com.miaosha.service.impl.model.UserModel;

public interface UserService {
    //通过用户ID获取用户
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    void setUserinfo(UserModel userModel) throws BusinessException;
    UserModel getUserByTelphone(String telphone) throws BusinessException;
}
