package com.holyrobot.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * hbaseapi
 */
public class HBaseApi {
    // 日志
    public static Logger logger = Logger.getLogger(HBaseApi.class);

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

    public static void insertRow(String tableName, String rowKey, List<HbaseColumn> datas) throws IOException {
        // 获取表
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        table.setAutoFlush(false);
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

    public static void delete(String tableName, String rowKey, String family, String qualifier, String timeStampe) {
        try {
            //判断是表是否存在
            HBaseAdmin admin = new HBaseAdmin(conf);
            if (!admin.tableExists(Bytes.toBytes(tableName))) {
                System.err.println("the table " + tableName + " is not exist");
                System.exit(1);
            }
            //创建表连接
            HTable table = new HTable(conf, tableName.valueOf(tableName));
            //准备删除数据
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            if (family != null && qualifier != null) {
                delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            } else if (family != null && qualifier == null) {
                delete.deleteFamily(Bytes.toBytes(family));
            }
            //检查时间戳
            if (timeStampe != null) {
                delete.setTimestamp(Long.parseLong(timeStampe));
            }
            //进行数据删除
            table.delete(delete);
            table.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void scan(String tableName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        Integer count = 0;
        for (Result r : scanner) {
            count++;
            for (Cell cell : r.rawCells()) {
                System.out.println(Bytes.toString(r.getRow()) + "  " + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "===" + Bytes.toString(CellUtil.cloneValue(cell)) +
                        "   Time : " + cell.getTimestamp());
            }
        }
        System.out.println(count);

//                if ("roomid".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
//                    if (Bytes.toString(CellUtil.cloneValue(cell)) == null) {
//                        System.out.println(Bytes.toString(r.getRow()));
//                    }
//                }

//                if ("grade".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
//                    if (Bytes.toString(CellUtil.cloneValue(cell)) == null ||"".equals(Bytes.toString(CellUtil.cloneValue(cell)) == null)) {
//                        System.out.println(Bytes.toString(r.getRow()));
//                    }
//                }
    }

    public static void main(String[] args) throws IOException {
        scan("HolyRobot:OrderInfo");
//        filter("HolyRobot:HotelBasicInfo_clean");
//        delete("HolyRobot:HotelBasicInfo_clean", "null_null_0224a992-233c-11e8-9d49-680715098273", "info", null, null);


    }

    public static void insertTestData() throws IOException {
        List<HbaseColumn> list = new ArrayList<>();
        HbaseColumn column = new HbaseColumn();
        column.setFamilyName("info");
        column.setColName("name");
        column.setColValue("三亚亚龙湾寰岛海底世界酒店");
        list.add(column);

        HbaseColumn column1 = new HbaseColumn();
        column1.setFamilyName("info");
        column1.setColName("address");
        column1.setColValue("海南省三亚市亚龙湾国家旅游度假区龙海路2号(位于海底世界景区旁)");
        list.add(column1);

        String rowkey = "null_null_0224a992-233c-11e8-9d49-680715098273";

        insertRow("HolyRobot:HotelBasicInfo_clean", rowkey, list);
    }


    public static void filter(String tableName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan s = new Scan();
        SingleColumnValueFilter f = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("id"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("0224a992-233c-11e8-9d49-680715098273"));
        s.setFilter(f);
        ResultScanner scanner = table.getScanner(s);
        for (Result r : scanner) {
            for (Cell cell : r.rawCells()) {
                System.out.println(Bytes.toString(r.getRow()) + "  " + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "===" + Bytes.toString(CellUtil.cloneValue(cell)) +
                        "   Time : " + cell.getTimestamp());
            }
        }
    }


}
