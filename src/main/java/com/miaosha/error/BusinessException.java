package com.miaosha.error;

/**
 * 包装器业务错误实现
 */
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    public BusinessException (CommonError commonError, String errorMsg){
        super();
        this.commonError = commonError;
        this.commonError.setMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setMsg(String errorMsg) {
        this.commonError.setMsg(errorMsg);
        return this;
    }

}
