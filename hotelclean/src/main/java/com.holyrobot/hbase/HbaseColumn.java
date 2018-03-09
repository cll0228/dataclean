package com.holyrobot.hbase;

/**
 * hbase列实体 test
 *
 * @author Administrator
 */
public class HbaseColumn {
    public HbaseColumn() {
        //构造函数
    }

    public HbaseColumn(String familyName, String colName, String value) {
        this.familyName = familyName;
        this.colName = colName;
        this.colValue = value;
    }


    private String familyName;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    private String colName;
    private String colValue;
}
