package com.lee.base.mvp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author ：le
 * Date ：2019-10-24 10:26
 * Description ：okhttp构建json请求体的类
 */
public class RequestBodyBuilder {
    private final JsonObject jsonObject;

    public RequestBodyBuilder() {
        jsonObject = new JsonObject();
    }

    public RequestBodyBuilder addProperty(String property, Number value) {
        jsonObject.addProperty(property, value);
        return this;
    }

    public RequestBodyBuilder addProperty(String property, JsonElement jsonObject) {
        this.jsonObject.add(property, jsonObject);
        return this;
    }

    public <T> RequestBodyBuilder addProperty(String property, List<T> listValue) {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (T t : listValue) {
            if (t instanceof Number) {
                jsonArray.add((Number) t);
            } else if (t instanceof JsonElement) {
                jsonArray.add((JsonElement) t);
            } else if (t instanceof String) {
                jsonArray.add((String) t);
            } else if (t instanceof Boolean) {
                jsonArray.add((Boolean) t);
            } else if (t instanceof Character) {
                jsonArray.add((Character) t);
            } else {
                JsonElement jsonElement = gson.toJsonTree(t);
                jsonArray.add(jsonElement);
            }
        }
        this.jsonObject.add(property, jsonArray);
        return this;
    }

    public RequestBodyBuilder addProperty(String property, String value) {
        jsonObject.addProperty(property, value);
        return this;
    }

    public RequestBodyBuilder addProperty(String property, Boolean value) {
        jsonObject.addProperty(property, value);
        return this;
    }

    public RequestBodyBuilder addProperty(String property, Character value) {
        jsonObject.addProperty(property, value);
        return this;
    }

    public RequestBodyBuilder removeProperty(String property) {
        this.jsonObject.remove(property);
        return this;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

    public RequestBody build() {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }
}
