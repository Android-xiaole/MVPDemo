package com.lee.base.mvp;

/**
 * Author ：le
 * Date ：2019-10-21 17:16
 * Description ：基础模型类，可以通过error方法过滤数据
 */
public class BaseModel implements IModel{

    //正常后台的json模型都包含这两个字段
    private int code;//返回码
    private String message;//返回信息

    @Override
    public BaseError error() {
        if (code == HttpCode.OK.getCode()){
            return null;
        }
        return new BaseError(code,message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
