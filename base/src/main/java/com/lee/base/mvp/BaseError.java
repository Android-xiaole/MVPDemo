package com.lee.base.mvp;

import java.util.Objects;

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

    /**
     * 重写equals方便快捷判断错误类型，如果code和message都相同就认为是同一个错误
     *
     * @param o 被比对对象
     * @return 结果是否相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseError baseError = (BaseError) o;
        return code == baseError.code && getMessage().equals(baseError.getMessage());
    }

    /**
     * 重写equals的时候必须重新hashCode方法
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, getMessage());
    }
}
