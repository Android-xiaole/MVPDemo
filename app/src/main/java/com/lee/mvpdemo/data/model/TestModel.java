package com.lee.mvpdemo.data.model;

import com.lee.base.mvp.BaseError;
import com.lee.base.mvp.BaseModel;
import com.lee.base.mvp.HttpCode;

/**
 * Author ：lee
 * Date ：2021/4/4 15:14
 * Description ：
 */
class TestModel extends BaseModel {

    private DateBean data;

    @Override
    public BaseError error() {
        if (code == HttpCode.OK.getCode()) {
            // 这里判断虽然code=200但是如果内容为null依然认为这是错误的请求
            if (data.content != null) {
                return null;
            } else {
                return BaseError.nullDataError();
            }
        }
        return new BaseError(code, message);
    }

    public DateBean getData() {
        return data;
    }

    public void setData(DateBean data) {
        this.data = data;
    }

    public static class DateBean {
        private long id;
        private String title;
        private String content;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
