package hbase;

import java.util.List;

/**
 * hbase一行记录
 * @author Administrator
 *
 */
public class HBaseRow {
	//行键值
	private String rowKey;
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	//一行的数据
	private List<HbaseColumn> datas;
	public List<HbaseColumn> getDatas() {
		return datas;
	}
	public void setDatas(List<HbaseColumn> datas) {
		this.datas = datas;
	}
}
