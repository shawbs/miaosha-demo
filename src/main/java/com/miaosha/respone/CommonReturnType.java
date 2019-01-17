package com.miaosha.respone;

public class CommonReturnType {
    private Integer errorCode; // 0成功，其它失败
    private String errorMsg;
    private Object data; //返回前端需要的数据体

    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,0,"ok");
    }

    public static CommonReturnType create(Object result, Integer errorCode, String errorMsg){
        CommonReturnType type = new CommonReturnType();
        type.setErrorCode(errorCode);
        type.setErrorMsg(errorMsg);
        type.setData(result);
        return type;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
