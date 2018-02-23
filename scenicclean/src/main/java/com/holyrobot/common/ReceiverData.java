package com.holyrobot.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 接收的数据对象
 */
public class ReceiverData implements Serializable{
    private static final long serialVersionUID = 771208872458900148L;
    /**
     * data对象类型
     * 1.酒店详情；2.酒店价格；3.酒店房型；
     * 4.景点价格；5.景点详情；
     * 6.行程详情；7.行程价格；
     * 8.机票详情；9.机票价格。
     */
    private Integer type;
    /**
     * 对应酒店、景点、行程、机票数据
     */
    private Object data;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

