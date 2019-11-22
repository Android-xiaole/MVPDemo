package com.lee.mvpdemo.data.model;

import com.lee.base.mvp.BaseModel;

/**
 * Author ：le
 * Date ：2019-11-18 16:32
 * Description ：
 */
public class TestModel extends BaseModel {

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
