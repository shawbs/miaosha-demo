package com.miaosha.error;

public interface CommonError {
    int getErrorCode();
    String getErrorMsg();
    CommonError setMsg(String errorMsg);
}
