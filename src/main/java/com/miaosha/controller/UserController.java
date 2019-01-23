package com.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.base.Utils;
import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.error.BusinessException;
import com.miaosha.error.CommonError;
import com.miaosha.error.EmBusinessError;
import com.miaosha.respone.CommonReturnType;
import com.miaosha.service.UserService;
import com.miaosha.service.impl.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/getOtp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone){
        //生成otp code
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        randomInt += 100000;
        String otpCode = String.valueOf(randomInt);

        //关联手机号和对应的otp code
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        System.out.println("telphone:" + telphone + ", otpCode:" + otpCode);

        Map map = new HashMap();
        map.put("code", otpCode);
        map.put("telphone", telphone);
        return CommonReturnType.create(map);
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException{
        UserVO userVO = new UserVO();
        UserModel userModel = userService.getUserById(id);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(userModel,userVO);
        return CommonReturnType.create(userVO);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpcode")String otpcode
                                     ) throws BusinessException {
        String inSessionCode = (String)httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(inSessionCode,otpcode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"验证码错误");
        }
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword("123456");
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/setUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType setUserInfo (
            @RequestParam(name = "id")Integer id,
            @RequestParam(name = "name")String name,
            @RequestParam(name = "age")Integer age,
            @RequestParam(name = "gender")Integer gender,
            @RequestParam(name = "password")String password
    ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender((byte)gender.intValue());
        userModel.setEncrptPassword(Utils.EncodeByMd5(password));
        userService.setUserinfo(userModel);
        return CommonReturnType.create(null);
    }

}
