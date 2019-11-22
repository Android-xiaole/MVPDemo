package com.lee.base.mvp;

/**
 * Author ：le
 * Date ：2019-10-21 16:43
 * Description ：处理接口返回错误的基类，也可根据具体业务需求进行扩展
 */
public class BaseError extends Exception {

    private int code = HttpCode.UNKNOW_ERROR.getCode();

    public BaseError(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseError(Throwable exception) {
        super(exception.getMessage());
    }

    public int getCode() {
        return code;
    }

    /**
     * 类似于这种错误，开发者可以根据自己的需求，扩展不同类型的error
     */
    public static BaseError netWorkError() {
        return new BaseError(HttpCode.NETWORK_ERROR.getCode(), "网络不通畅,请检查后再试");
    }

    public static BaseError jsonParseError() {
        return new BaseError(HttpCode.JSONPARSE_ERROR.getCode(), "数据解析异常");
    }

    public static BaseError unKnowError() {
        return new BaseError(HttpCode.UNKNOW_ERROR.getCode(), "未知异常");
    }

    public static BaseError nullDataError() {
        return new BaseError(HttpCode.UNKNOW_ERROR.getCode(), "数据空指针异常");
    }

}
