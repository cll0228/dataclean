package com.holyrobot.common;

import java.io.Serializable;

/**
 * 解析对象的父类
 */
public class RobotObject implements Serializable{
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
