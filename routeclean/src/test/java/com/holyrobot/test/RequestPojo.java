package com.holyrobot.test;
/**
 * @Author: 周陈
 * @Description: 爬虫入口API传输对象
 */
public class RequestPojo {
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
     * 1:表示数据完整
     * 0:表示数据不完整，还需要二次爬取
     */
    private Integer flag;
    /**
     * 当前版本号
     */
    private String version;
    /**
     * 对应酒店、景点、行程、机票数据
     */
    private Object data;

    @Override
    public String toString() {
        return "RequestPojo{" +
                "type=" + type +
                ", flag=" + flag +
                ", version='" + version + '\'' +
                ", data=" + data +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

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
