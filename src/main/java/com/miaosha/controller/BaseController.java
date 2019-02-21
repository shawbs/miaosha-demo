package com.miaosha.controller;

import com.miaosha.error.BusinessException;
import com.miaosha.error.CommonError;
import com.miaosha.error.EmBusinessError;
import com.miaosha.respone.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex){
        CommonReturnType commonReturnType = new CommonReturnType();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            commonReturnType.setErrorCode(businessException.getErrorCode());
            commonReturnType.setErrorMsg(businessException.getErrorMsg());
        }else if(ex instanceof MissingServletRequestParameterException){
            commonReturnType.setErrorCode(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrorCode());
            commonReturnType.setErrorMsg(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrorMsg());
        }else{
            commonReturnType.setErrorCode(EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            commonReturnType.setErrorMsg(EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        commonReturnType.setData(null);
        return commonReturnType;
    }
}
