package com.cuichuang.common.base.view;

import java.io.Serializable;

/**
 * File descripition:   mode基类
 *
 * @author CC
 * @date 2018/6/19
 */
public class BaseModel<T> implements Serializable {
    private String msg;
    private String code;
    private T data;

    public BaseModel(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }
}
