package hbase;

public class HbaseIncrCol {
    private String familyName;      //列簇名
    private String colName;         //列名
    private Long colValue;          //列值

    public HbaseIncrCol() {
    }

    public HbaseIncrCol(String familyName, String colName, Long colValue) {
        this.familyName = familyName;
        this.colName = colName;
        this.colValue = colValue;
    }

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

    public Long getColValue() {
        return colValue;
    }

    public void setColValue(Long colValue) {
        this.colValue = colValue;
    }
}
