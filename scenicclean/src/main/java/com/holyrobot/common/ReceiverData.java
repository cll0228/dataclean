package com.holyrobot.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 接收的数据对象
 */
public class ReceiverData implements Serializable{
    private static final long serialVersionUID =771208872458900148L;
    /**
     * 数据类型:酒店、景点、行程
     */
    private String type;
    /**
     * 消息体：包括主页面byte，详情页byte
     */
    private Map<String,byte[]> body;
    /**
     * 数据标示
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 网站Url地址
     */
    private String dataUrl;

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Map<String, byte[]> getBody() {
        return body;
    }

    public void setBody(Map<String, byte[]> body) {
        this.body = body;
    }
}

