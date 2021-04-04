package com.lee.mvpdemo.data.bean;

/**
 * Author ：le
 * Date ：2019-11-18 16:32
 * Description ：
 */
public class TestBean {

    private long id;
    private String title;
    private String content;

    public TestBean() {
    }

    public TestBean(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

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
