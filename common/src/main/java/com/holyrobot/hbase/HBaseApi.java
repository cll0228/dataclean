package com.holyrobot.hbase;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * hbaseapi
 */
public class HBaseApi {
    // 日志
    public static Logger logger = LoggerFactory.getLogger(HBaseApi.class);

    // 声明静态配置
    public static Configuration conf = null;

    static {
        try {
            conf = HBaseConfiguration.create();
            ConfigManager cm = new ConfigManager();
            conf.set("hbase.zookeeper.quorum", cm.getConfig("hbase.zookeeper.quorum"));
//            conf.set("hbase.zookeeper.quorum","cdh04,cdh05,cdh06");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            logger.info("hbase初始化配置结束");
        } catch (Exception e) {
            logger.error("====hbase初始化配置失败:" + e);
        }

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

    public static void scan(String tableName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        Integer count = 0;
        for (Result r : scanner) {
            count++;
            for (Cell cell : r.rawCells()) {
                System.out.println(Bytes.toString(r.getRow()) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "===" + Bytes.toString(CellUtil.cloneValue(cell)) +
                        "   Time : " + cell.getTimestamp());
            }
            System.out.println("========================================");
        }
        System.out.println(count);
    }

    /**
     * 根据 rowkey删除一条记录
     *
     * @param tablename
     * @param rowkey
     */
    public static void deleteRow(String tablename, String rowkey) {
        try {
            HTable table = new HTable(conf, tablename);
            List list = new ArrayList();
            Delete d1 = new Delete(rowkey.getBytes());
            list.add(d1);

            table.delete(list);
            System.out.println("删除行成功!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void queryByValue(String tableName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        Integer count = 0;
        for (Result r : scanner) {
            for (Cell cell : r.rawCells()) {
                if ("Ctrip".equals(Bytes.toString(CellUtil.cloneValue(cell)))) {
                    String rowKey = Bytes.toString(r.getRow());
                    deleteRow(tableName, rowKey);
                    break;
                }
            }
        }
    }

    private static void countHotel(String tableName) throws IOException, ParseException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        Integer count = 0;
        for (Result r : scanner) {
            for (Cell cell : r.rawCells()) {
                if ("createdate".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
                    String datetime = Bytes.toString(CellUtil.cloneValue(cell));
                    if ("null".equals(datetime) || "".equals(datetime)) {
                        continue;
                    }
                    Long time = null;
                    try {
                        Date parse = fm.parse(datetime);
                        time = parse.getTime();
                    } catch (Exception e) {
                        try {
                            time = Long.valueOf(JSON.parseObject(datetime).get("time").toString());
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    }
                    if (time > fm.parse("2018-03-20 00:00:00").getTime()) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) throws IOException, ParseException {
//        scan("HolyRobot:HotelBasicInfo_clean");
//        countHotel("HolyRobot:Routeinfo_clean");
        HBaseAdmin admin = new HBaseAdmin(conf);
        TableName[] tableNames = admin.listTableNames();
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();


    }

    static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
