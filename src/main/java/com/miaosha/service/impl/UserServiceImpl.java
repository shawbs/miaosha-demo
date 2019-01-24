package com.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.dao.UserDOMapper;
import com.miaosha.dao.UserPasswordDOMapper;
import com.miaosha.dataobject.UserDO;
import com.miaosha.dataobject.UserPasswordDO;
import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.service.UserService;
import com.miaosha.service.impl.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;


    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserModel userModel = convertFormDataObject(userDO);
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //先userModel->dataobject 然后插入到数据库
        UserDO userDO = convertFormModel(userModel);

        //insertSelective和insert的区别是：前者在数据为null时不插入而使用数据库默认值；后者是强行插入
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertFormPassword(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    @Transactional
    public void setUserinfo(UserModel userModel) throws BusinessException {
        if(userModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        if(StringUtils.isEmpty(String.valueOf(userModel.getId())))
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);

        UserDO userDO = convertFormModel(userModel);
        UserPasswordDO userPasswordDO = convertFormPassword(userModel);
        //insertSelective和insert的区别是：前者在数据为null时不插入而使用数据库默认值；后者是强行插入
        userDOMapper.updateByPrimaryKeySelective(userDO);
        userPasswordDOMapper.updateByPrimaryKeySelective(userPasswordDO);
    }

    public UserDO convertFormModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    public UserPasswordDO convertFormPassword(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    public UserModel convertFormDataObject(UserDO userDO){
        if(userDO == null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

    @Override
    @Transactional
    public UserModel getUserByTelphone(String telphone) throws BusinessException {
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserModel userModel = convertFormDataObject(userDO);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userModel.getId());
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());

        return userModel;
    }
}
