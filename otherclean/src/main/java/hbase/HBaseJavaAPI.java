package hbase;

import com.google.common.collect.Lists;
import com.holyrobot.common.ConfigConstants;
import com.holyrobot.common.ConfigPropManage;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.coprocessor.AggregateImplementation;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * hbase操作api
 * 
 * @author Administrator
 *
 */
public class HBaseJavaAPI {
	// 日志
	public static Logger logger = Logger.getLogger(HBaseJavaAPI.class);

	// 声明静态配置
	public static Configuration conf = null;
	static {
		try {
			conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", "cdh04,cdh05,cdh06");
			conf.set("hbase.zookeeper.property.clientPort", "2181");
			System.setProperty("HADOOP_USER_NAME", "hdfs");
			logger.info("hbase初始化配置结束");
		} catch (Exception ex) {
			logger.info("==========hbase初始化配置失败:" + ex.getMessage() + "=======================");
		}

		// conf.set("hbase.zookeeper.quorum", "s1:2181");
	}


	public static void run() {
		try {
			logger.info("=============hbase初始化运行开始====================");
			HBaseAdmin admin = new HBaseAdmin(conf);
			logger.info("=============hbase初始化运行结束====================");
		} catch (Exception e) {
			logger.info("hbase初始化运行失败:" + e.getMessage());
		}
	}

	public static String getMaxRowkey(String tableName, String prefix, String startKey, String stopKey) throws Exception {
		HTable brokerHouse = new HTable(conf, tableName);
		//获取这个最大值
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes(prefix + startKey));
		scan.setStopRow(Bytes.toBytes(prefix + stopKey));
		scan.setReversed(true);
		ResultScanner rs = brokerHouse.getScanner(scan);
		String maxRow = "";
		Result rr = rs.next();
		if(rr != null){
			String rowVal = new String(rr.getRow(),"utf-8");
			String mr = rowVal.replace(prefix,"");
			if(mr.length() > 0){
				Long max = Long.parseLong(mr);
				maxRow = prefix + (max + 1);
			}
		}
		if(maxRow.equals("")){
			maxRow = prefix + ( Long.parseLong(startKey) + 1 );
		}
//		brokerHouse.close();
//		rs.close();
		return maxRow;
	}
	/**
	 * HBase API添加协处理器
	 */
	public static void addCoprocessor(Configuration conf, String tableName) {
		try {
			byte[] tableNameBytes = Bytes.toBytes(tableName);
			HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);
			HTableDescriptor htd = hbaseAdmin.getTableDescriptor(tableNameBytes);
			if (!htd.hasCoprocessor(AggregateImplementation.class.getName())) {
				hbaseAdmin.disableTable(tableNameBytes);
				htd.addCoprocessor(AggregateImplementation.class.getName());
				hbaseAdmin.modifyTable(tableNameBytes, htd);
				hbaseAdmin.enableTable(tableNameBytes);
			}

			hbaseAdmin.close();

		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String[] family ={"info"};
		creatTable("HolyRobot:Addressinfo_clean",family);
	}

	/*
	 * 创建表
	 * 
	 * @tableName 表名
	 * 
	 * @family 列族列表
	 */
	public static void creatTable(String tableName, String[] family) throws Exception {

		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
		for (int i = 0; i < family.length; i++) {
			desc.addFamily(new HColumnDescriptor(family[i]));
		}
		if (admin.tableExists(tableName)) {
			System.out.println("table Exists!");
			logger.info(tableName + " Exists！");
		} else {
			admin.createTable(desc);
			System.out.println("create table Success!");
			logger.info("create " + tableName + " Success!");
		}

		// 关闭
		try {
			if (admin != null) {
				admin.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 为表添加数据（适合知道有多少列族的固定表）
	 * 
	 * @rowKey rowKey
	 * 
	 * @tableName 表名
	 * 
	 * @column1 第一个列族列表
	 * 
	 * @value1 第一个列的值的列表
	 * 
	 * @column2 第二个列族列表
	 * 
	 * @value2 第二个列的值的列表
	 */
	public static void addData(String rowKey, String tableName, String[] column, String[] value) throws IOException {

		// 设置rowkey
		Put put = new Put(Bytes.toBytes(rowKey));
		// HTale负责跟记录相关的操作如增删改查等
		// 获取表
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		// 获取所有的列族
		HColumnDescriptor[] columnFamilies = table.getTableDescriptor().getColumnFamilies();

		for (int i = 0; i < columnFamilies.length; i++) {
			// 获取列族名
			String familyName = columnFamilies[i].getNameAsString();
			// article列族put数据
			if (familyName.equals("info")) {
				for (int j = 0; j < column.length; j++) {
					put.add(Bytes.toBytes(familyName), Bytes.toBytes(column[j]), Bytes.toBytes(value[j]));
				}
			}
			// author列族put数据
			// if (familyName.equals("other")) {
//			for (int j = 0; j < column2.length; j++) {
//				put.add(Bytes.toBytes(familyName), Bytes.toBytes(column2[j]), Bytes.toBytes(value2[j]));
//			}
			// }
		}
		table.put(put);
		System.out.println("add data Success!");
		logger.info("add data Success!");

		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 添加数据
	 * @param tableName
	 * @param rowKey
	 * @throws IOException
	 * @param datas
	 */
	public static void insertRow(String tableName, String rowKey, List<HbaseColumn> datas) throws IOException {
		// 获取表
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Put putRow1 = new Put(Bytes.toBytes(rowKey));
		for (HbaseColumn col : datas) {
			putRow1.add(col.getFamilyName().getBytes("utf-8"), col.getColName().getBytes("utf-8"),
			    col.getColValue().getBytes("utf-8"));
		}
		table.put(putRow1);
		table.flushCommits();
		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加列
	 * @param tableName
	 * @param rowKey
	 * @param datas
	 * @return
	 * @throws IOException
	 */
	public static Result incrRow(String tableName, String rowKey, List<HbaseIncrCol> datas) throws IOException {
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Increment increment = new Increment(Bytes.toBytes(rowKey));
		for (HbaseIncrCol col : datas) {
			increment.addColumn(Bytes.toBytes(col.getFamilyName()), Bytes.toBytes(col.getColName()), col.getColValue());
		}
		Result result = table.increment(increment);
		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 根据rowkey查询
	 * @param tableName
	 * @param rowKey
	 * @return
	 * @throws IOException
	 */
	public static HBaseRow getResult(String tableName, String rowKey) throws IOException {
		Get get = new Get(Bytes.toBytes(rowKey));
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Result result = table.get(get);
		HBaseRow hBaseRow = new HBaseRow();
		if(!result.isEmpty()){
			hBaseRow.setRowKey(Bytes.toString(result.getRow()));
			List<HbaseColumn> dds = new ArrayList<>();
			for (Cell cell : result.listCells()) {
				dds.add(new HbaseColumn(Bytes.toString(CellUtil.cloneFamily(cell)),Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell))));
			}
			hBaseRow.setDatas(dds);
			return hBaseRow;
		}
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	/*
	 * 遍历查询hbase表
	 * 
	 * @tableName 表名
	 */
	public static List<Result> getResultScann(String tableName, int pageSize, int pageIndex) throws IOException {
		Scan scan = new Scan();
		ResultScanner rs = null;
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		scan.setReversed(true);
		List<Result> rl = new ArrayList<Result>();
		try {
			int sum = 0;
			rs = table.getScanner(scan);
			Result result = null;
			while ((result = rs.next()) != null) {
				if (sum >= pageSize * (pageIndex - 1) && sum < pageSize * pageIndex) {
					rl.add(result);
				}
				if (sum >= pageSize * pageIndex) {
					break;
				}
				sum++;
			}
		} finally {
			rs.close();
			table.close();
		}
		return rl;
	}

	/*
	 * 遍历查询hbase表(从 start_rowkey 到 stop_rowkey )
	 * 
	 * @tableName 表名
	 */
	public static List<HBaseRow> getResultScann(String tableName, String start_rowkey, String stop_rowkey)
	    throws IOException {
		Scan scan = new Scan();
		scan.setMaxVersions(4);
		scan.setStartRow(Bytes.toBytes(start_rowkey));
		scan.setStopRow(Bytes.toBytes(stop_rowkey));
		List<HBaseRow> datas = new ArrayList<HBaseRow>();
		ResultScanner rs = null;
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		
		try {
			rs = table.getScanner(scan);
			
			for (Result r : rs) {
				HBaseRow row = new HBaseRow();
				row.setRowKey(new String(r.getRow(), "utf-8"));
				List<HbaseColumn> dds = new ArrayList<HbaseColumn>();
				for (Cell cell : r.rawCells()) {
					
					System.out.println("version:" + cell.getMvccVersion());
					row.setRowKey(new String(CellUtil.cloneRow(cell)));
					dds.add(new HbaseColumn(new String(CellUtil.cloneFamily(cell)),
					    new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell))));

				}
				row.setDatas(dds);
				datas.add(row);
			}
			logger.info("遍历查询   " + tableName + " Success!");

		} finally {
			if(rs != null){
				rs.close();
			}
			if (table != null) {
				table.close();
			}
		}
		return datas;
	}

	/**
	 * 根据开始和结束行  查询第一行的rowkey
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @param isRevert
	 * @return
	 */
	public static String getFirstRow(String tableName,String startRow,String stopRow,boolean isRevert){
		String result = "";
		try {
			Scan scan = new Scan();
			scan.setMaxVersions(4);
			scan.setStartRow(Bytes.toBytes(startRow));
			scan.setStopRow(Bytes.toBytes(stopRow));
			scan.setReversed(isRevert);
			List<HBaseRow> datas = new ArrayList<HBaseRow>();
			ResultScanner rs = null;
			HTable table = new HTable(conf, Bytes.toBytes(tableName));
			rs = table.getScanner(scan);
			Result rl = rs.next();
			if(rl.rawCells().length > 0){
				result = new String(rl.getRow(),"utf-8");
			}
		}catch (Exception ex){

		}

        return result;
	}

	/**
	 * 多列模糊查询
	 * 
	 * @param tableName
	 * @param familyName
	 * @param cols
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getFuzzyResultsByCols(String tableName, String familyName, Map<Object,Object> cols) throws IOException {
		List<SingleColumnValueFilter> columnValueFilters = Lists.newArrayList();
		for (Object key : cols.keySet()) {
			String columnName = String.valueOf(key);
			String colValue = String.valueOf(cols.get(key));
			SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName),
			    Bytes.toBytes(columnName), CompareOp.valueOf("EQUAL"), new SubstringComparator(colValue));
			scvf.setFilterIfMissing(true);
			scvf.setLatestVersionOnly(true); // OK
			columnValueFilters.add(scvf);
		}
		FilterList filterList = new FilterList(columnValueFilters.toArray(new SingleColumnValueFilter[0]));
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		scan1.setFilter(filterList);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		//只查询出前100条记录
		int index = 0;
		for (Result res : scanner1) {
//			index++;
//			if(index == 20000){
//				break;
//			}
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 多列精准查询
	 * 
	 * @param tableName
	 * @param familyName
	 * @param cols
	 * @return
	 * @throws IOException
	 */
	/*public static List<Result> getAccurateResultsByCols(String tableName, String familyName, Map<Object,Object> cols) throws IOException {
		List<SingleColumnValueFilter> columnValueFilters = Lists.newArrayList();
		for (Object key : cols.keySet()) {
			String columnName = String.valueOf(key);
			String colValue = String.valueOf(cols.get(key));
			if (StringUtils.isNotBlank(colValue)) {
				SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
						CompareFilter.CompareOp.EQUAL, Bytes.toBytes(colValue));
				//要过滤的列必须存在，如果不存在，那么这些列不存在的数据也会返回。如果不想让这些数据返回，则设置setFilterIfMissing为true,true 跳过改行;false 通过该行
				scvf.setFilterIfMissing(true);
				scvf.setLatestVersionOnly(true); // OK
				columnValueFilters.add(scvf);
			}
		}
		FilterList filterList = new FilterList(columnValueFilters.toArray(new SingleColumnValueFilter[0]));
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		scan1.setFilter(filterList);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}*/

	/**
	 * 指定多列条件"and"或"or"，是否模糊查询
	 *
	 * @param tableName
	 * @param familyName
	 * @param cols
	 * @param condition 条件“and”/"or"
	 * @param isLike true:模糊查询
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultsByColsCon(String tableName, String familyName, Map<Object,Object> cols,String condition,String isLike) throws IOException {
		List<Filter> filters = new ArrayList<Filter>();
		for (Object key : cols.keySet()) {
			String columnName = String.valueOf(key);
			String colValue = String.valueOf(cols.get(key));
			if (StringUtils.isNotBlank(colValue)) {
				SingleColumnValueFilter scvf = null;
				if("true".equals(isLike)){
					scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
							CompareOp.EQUAL, new SubstringComparator(colValue));
				}else{
					scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
							CompareOp.EQUAL, Bytes.toBytes(colValue));
				}
				//要过滤的列必须存在，如果不存在，那么这些列不存在的数据也会返回。如果不想让这些数据返回，则设置setFilterIfMissing为true,true 跳过改行;false 通过该行
				scvf.setFilterIfMissing(true);
				scvf.setLatestVersionOnly(true); // OK
				filters.add(scvf);
			}
		}
		FilterList filterList = null;
		if("or".equals(condition)){
			filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE,filters);
		}else{
			filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
		}
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		scan1.setFilter(filterList);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 单列范围查找（如：日期列在"2018-02-01"-"2018-02-10"之间的数据）
	 *
	 * @param tableName		表名
	 * @param familyName	列簇名
	 * @param column		列名
	 * @param start			开始值
	 * @param end			结束值
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultsByColsScope(String tableName, String familyName, String column, String start, String end) throws IOException {
		List<Filter> filters = new ArrayList<>();
		if (StringUtils.isNotBlank(start)) {
			SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(column),
					CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(start)));
			//要过滤的列必须存在，如果不存在，那么这些列不存在的数据也会返回。如果不想让这些数据返回，则设置setFilterIfMissing为true,true 跳过改行;false 通过该行
			scvf.setFilterIfMissing(true);
			scvf.setLatestVersionOnly(true); // OK
			filters.add(scvf);
		}
		if (StringUtils.isNotBlank(end)) {
			SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(column),
					CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(end)));
			//要过滤的列必须存在，如果不存在，那么这些列不存在的数据也会返回。如果不想让这些数据返回，则设置setFilterIfMissing为true,true 跳过改行;false 通过该行
			scvf.setFilterIfMissing(true);
			scvf.setLatestVersionOnly(true); // OK
			filters.add(scvf);
		}
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		scan1.setFilter(filterList);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 根据行健模糊查询
	 * 
	 * @param tableName
	 * @param rowValue
	 * @return
	 * @throws IOException
	 */
	public static List<HBaseRow> getDataByRowKey(String tableName, String rowValue) throws IOException {
		Scan scan = new Scan();
//		scan.setMaxVersions(5);
		FilterList filterList = new FilterList();
		Filter filter = new RowFilter(CompareOp.EQUAL, new SubstringComparator(rowValue));
		filterList.addFilter(filter);
		scan.setFilter(filterList);
		List<HBaseRow> datas = new ArrayList<HBaseRow>();
		ResultScanner rs = null;
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		try {
			rs = table.getScanner(scan);

			for (Result r : rs) {
				HBaseRow row = new HBaseRow();
				List<HbaseColumn> dds = new ArrayList<HbaseColumn>();
				row.setRowKey(new String(r.getRow(), "utf-8"));
				for (Cell cell : r.rawCells()) {
					dds.add(new HbaseColumn(new String(CellUtil.cloneFamily(cell)),
					    new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell))));
//					System.out.println(new String(CellUtil.cloneValue(cell)));
				}
				row.setDatas(dds);
				datas.add(row);
			}

			logger.info("遍历查询   " + tableName + " Success!");

		} finally {
			rs.close();
			if (table != null) {
				table.close();
			}
		}
		return datas;
	}

	/*
	 * 查询表中的某一列
	 * 
	 * @tableName 表名
	 * 
	 * @rowKey rowKey
	 */
	public static String getResultByColumn(String tableName, String rowKey, String familyName, String columnName)
	    throws IOException {
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
		Result result = table.get(get);
		StringBuilder sb = new StringBuilder();
		for (Cell cell : result.rawCells()) {
//			System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
//			System.out.println("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
			// System.out.println("value:"+new
			// String(CellUtil.cloneValue(cell))+" ");
			sb.append(new String(CellUtil.cloneValue(cell)));
//			System.out.println("Timetamp:" + cell.getTimestamp() + " ");
//			System.out.println("-------------------------------------------");// 关闭

		}
		logger.info("查询Success!");
		try {
			return sb.toString();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (table != null) {
				table.close();
			}
		}
		return sb.toString();
	}

	/**
	 * 查询符合某列的行
	 * 
	 * @param tableName
	 * @param familyName
	 * @param colName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultByCol(String tableName, String familyName, String colName, String value)
	    throws IOException {
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(colName),
		    CompareOp.valueOf("EQUAL"), Bytes.toBytes(value));
		scvf.setFilterIfMissing(true);
		scvf.setLatestVersionOnly(true); // OK
		scan1.setFilter(scvf);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {

			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 查询符合某列的行(模糊查询)
	 * 
	 * @param tableName
	 * @param familyName
	 * @param colName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultByColSubstring(String tableName, String familyName, String colName,
	    String value) throws IOException {
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		// Filter scvf = new ValueFilter(CompareFilter.CompareOp.EQUAL, new
		// SubstringComparator(value));
		SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(familyName), Bytes.toBytes(colName),
		    CompareOp.valueOf("EQUAL"), new SubstringComparator(value));
		scvf.setFilterIfMissing(true);
		scvf.setLatestVersionOnly(true); // OK
		scan1.setFilter(scvf);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {

			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 查询符合某列的行(前缀匹配)
	 * 
	 * @param tableName
	 * @param familyName
	 * @param colName
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultByColPrefix(String tableName, String familyName, String colName, String value)
	    throws IOException {
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		Filter scvf = new PrefixFilter(Bytes.toBytes(value)); // OK
		
		scan1.setFilter(scvf);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/**
	 * 查询符合多个条件的行
	 * 
	 * @param tableName
	 * @param
	 * @return
	 * @throws IOException
	 */
	public static List<Result> getResultByColFilter(String tableName, List<HbaseColumn> params) throws IOException {
		HTable table = new HTable(conf, tableName);
		table.setAutoFlushTo(false);
		Scan scan1 = new Scan();
		List<Filter> filters = new ArrayList<Filter>();
		for (HbaseColumn col : params) {
			SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes(col.getFamilyName()),
			    Bytes.toBytes(col.getColName()), CompareOp.valueOf("EQUAL"),
			    Bytes.toBytes(col.getColValue()));
			scvf.setFilterIfMissing(true);
			scvf.setLatestVersionOnly(true);
			filters.add(scvf);
		}
		// OK 综合使用多个过滤器， AND 和 OR 两种关系
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
		scan1.setFilter(filterList);
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Result> result = new ArrayList<Result>();
		for (Result res : scanner1) {
			result.add(res);
		}
		scanner1.close();
		table.close();
		return result;
	}

	/*
	 * 更新表中的某一列
	 * 
	 * @tableName 表名
	 * 
	 * @rowKey rowKey
	 * 
	 * @familyName 列族名
	 * 
	 * @columnName 列名
	 * 
	 * @value 更新后的值
	 */
	public static void updateTable(String tableName, String rowKey, String familyName, String columnName, String value)
	    throws IOException {
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
		table.put(put);
		System.out.println("update table Success!");
		logger.info("update " + tableName + " Success!");

		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/*
	 * 查询某列数据的多个版本
	 * 
	 * @tableName 表名
	 * 
	 * @rowKey rowKey
	 * 
	 * @familyName 列族名
	 * 
	 * @columnName 列名
	 */
	public static void getResultByVersion(String tableName, String rowKey, String familyName, String columnName)
	    throws IOException {
		// System.setProperty("HADOOP_USER_NAME", "hdfs");
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Get get = new Get(Bytes.toBytes(rowKey));
		get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
		get.setMaxVersions(5);
		Result result = table.get(get);
		for (Cell cell : result.rawCells()) {
			System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
			System.out.println("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
			System.out.println("value:" + new String(CellUtil.cloneValue(cell)) + " ");
			System.out.println("Timetamp:" + cell.getTimestamp() + " ");
			System.out.println("-------------------------------------------");
		}
		/*
		 * List<?> results = table.get(get).list(); Iterator<?> it =
		 * results.iterator(); while (it.hasNext()) {
		 * System.out.println(it.next().toString()); }
		 */
		logger.info("查询 Success!");

		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单行
	 * 
	 * @param tableName
	 *          表名
	 * @param rowKey
	 *          行键
	 * @param family
	 *          组名
	 * @param qualifier
	 *          版本号
	 * @param timeStampe
	 *          时间戳
	 * @param
	 */
	public static void deleteOneRow(String tableName, String rowKey, String family, String qualifier, String timeStampe) {
		try {
			// 判断是表是否存在
			HBaseAdmin admin = new HBaseAdmin(conf);
			if (!admin.tableExists(Bytes.toBytes(tableName))) {
				System.err.println("表" + tableName + "不存在");
				System.exit(1);
			}
			// 创建表连接
			HTable table = new HTable(conf, TableName.valueOf(tableName));
			// 准备删除数据
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			if (family != null && qualifier != null) {
				delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
			} else if (family != null && qualifier == null) {
				delete.deleteFamily(Bytes.toBytes(family));
			}
			// 检查时间戳
			if (timeStampe != null) {
				delete.setTimestamp(Long.parseLong(timeStampe));
			}
			// 进行数据删除
			table.delete(delete);
			table.close();
			admin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 删除指定的列
	 * 
	 * @tableName 表名
	 * 
	 * @rowKey rowKey
	 * 
	 * @familyName 列族名
	 * 
	 * @columnName 列名
	 */
	public static void deleteColumn(String tableName, String rowKey, String falilyName, String columnName)
	    throws IOException {
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
		deleteColumn.deleteColumns(Bytes.toBytes(falilyName), Bytes.toBytes(columnName));
		table.delete(deleteColumn);
		System.out.println(falilyName + ":" + columnName + "is deleted!");
		logger.info("delete " + columnName + " Success!");
		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 根据rowKey删除指定的行
	 * 
	 * @tableName 表名
	 * 
	 * @rowKey rowKey
	 */
	public static void deleteAllColumn(String tableName, String rowKey) throws IOException {
		// System.setProperty("HADOOP_USER_NAME", "hdfs");
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
		table.delete(deleteAll);
		System.out.println("all columns are deleted!");
		logger.info("delete " + rowKey + " Success!");

		// 关闭
		try {
			if (table != null) {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 删除表
	 * 
	 * @tableName 表名
	 */
	public static void deleteTable(String tableName) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		admin.disableTable(tableName);
		admin.deleteTable(tableName);
		System.out.println(tableName + "is deleted!");
		logger.info("delete " + tableName + "success!");

		// 关闭
		try {
			if (admin != null) {
				admin.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 关闭conf连接
	public static void close() {
		if (conf != null) {
			try {
				conf.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 统计表数量
	 * 
	 */
	public static long exeCount(String tableName, String family) {
		try {
			// 使用hbase提供的聚合coprocessor
			AggregationClient aggregationClient = new AggregationClient(conf);
			Scan scan = new Scan();
			// 指定扫描列族，唯一值
			scan.addFamily(Bytes.toBytes(family));
			long start = System.currentTimeMillis();
			HBaseJavaAPI.addCoprocessor(conf, tableName);
			long rowCount = aggregationClient.rowCount(TableName.valueOf(tableName), new LongColumnInterpreter(), scan);
			return rowCount;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return 100;

	}

	/**
	 * 根据某列的值查询数量
	 * 
	 * @param tableName
	 * @param familyName
	 * @return
	 * @throws Throwable
	 */
	public static long rowCount(String tableName, String familyName) throws Throwable {
		AggregationClient aggregationClient = new AggregationClient(conf);
		Scan scan = new Scan(); // 指定扫描列族，唯一值
		scan.addFamily(Bytes.toBytes(familyName));
		HTable table = new HTable(conf, Bytes.toBytes(tableName));
		HBaseJavaAPI.addCoprocessor(conf, tableName);
		LongColumnInterpreter columnInterpreter = new LongColumnInterpreter();
		long rowCount = aggregationClient.rowCount(table, columnInterpreter, scan);
		System.out.println("row count is " + rowCount);
		return rowCount;
	}

	public static String nextRentalHouseNo(String houseRowKey) {
		String str = "";
		synchronized ("") {
			try {
				String prefix = "R";
				String start = "999999999";
				String stop = "100000000";
				String maxRow  = HBaseJavaAPI.getMaxRowkey("Hswh:HouseMax",prefix,start,stop);
				List<HbaseColumn> datas = new ArrayList<>();
				datas.add(HBaseJavaAPI.ghc("houseRowKey",houseRowKey));
				HBaseJavaAPI.insertRow("Hswh:HouseMax",maxRow,datas);
				return maxRow;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "R" + str;
		}
	}

	public static String nextSaleHouseNo(String houseRowKey) {
		String str = "";
		synchronized ("") {
			try {
				String prefix = "S";
				String start = "999999999";
				String stop = "100000000";
				String maxRow  = HBaseJavaAPI.getMaxRowkey("Hswh:HouseMax",prefix,start,stop);
				List<HbaseColumn> datas = new ArrayList<>();
				datas.add(HBaseJavaAPI.ghc("houseRowKey",""));
				HBaseJavaAPI.insertRow("Hswh:HouseMax",maxRow,datas);
				return maxRow;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "S" + str;
		}
	}

	static void insertCity() {

	}

	public static HbaseColumn ghc(String col, String value) {
		HbaseColumn id = new HbaseColumn();
		id.setFamilyName("info");
		id.setColName(col);
		id.setColValue(value);
		return id;
	}

	// 空值日期更新
	static void updatePublishDate() throws Exception {
		String[] ds = { "2017-2-10", "2017-2-15", "2017-2-18", "2017-3-1", "2017-2-15", "2017-3-6", "2017-3-7",
		    "2017-3-8", "2017-3-9" };
		Random ran1 = new Random(9);
		HTable table = new HTable(conf, Bytes.toBytes("innjia_house"));

		// 创建一个空的Scan实例
		Scan scan1 = new Scan();
		// 在行上获取遍历器
		ResultScanner scanner1 = table.getScanner(scan1);
		List<Put> list = new ArrayList<Put>();
		// 打印行的值
		for (Result res : scanner1) {

			Put put = new Put(res.getRow());
			int row = ran1.nextInt(9);
			for (Cell cell : res.rawCells()) {
				byte[] colName = CellUtil.cloneQualifier(cell);
				put.add(Bytes.toBytes("info"), colName, CellUtil.cloneValue(cell));
				if (colName.equals("publish_date") && CellUtil.cloneValue(cell) != null) {
					put.add(Bytes.toBytes("info"), Bytes.toBytes("publish_date"), Bytes.toBytes(ds[row]));
					table.put(put);
				}
			}

			// list.add(put);
			// System.out.println(new String(res.getRow(),"utf8"));
		}
		// table.put(list);
		System.in.read();
		table.close();
	}
}
