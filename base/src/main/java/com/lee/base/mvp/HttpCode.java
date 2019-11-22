package com.lee.base.mvp;

/**
 * Author ：le
 * Date ：2019-10-21 17:17
 * Description ：网络请求返回code的枚举类，根据后台给出的code来处理，
 * 也可以自行添加自定义的错误，这里只是举个例子
 */
public enum  HttpCode {

    OK(200),//返回正常
    CLIENT_ERROR(400),//客户端错误
    SERVER_ERROR(500),//服务器错误
    NODATA_ERROR(600),//没有数据的错误（这类错误可以自定义）
    NULLDATA_ERROR(995),//接口返回model空指针错误
    JSONPARSE_ERROR(996),//json解析错误
    NETWORK_ERROR(997),//网络错误
    OTHER_ERROR(998),//其他类型的错误，也是未明确分类的错误
    UNKNOW_ERROR(999);//未知错误

    private int code;

    HttpCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
