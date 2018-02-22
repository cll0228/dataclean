package com.holyrobot.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
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
            logger.info("=================hbase初始化配置开始======================");
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "node2,node3,node4,node6");
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
        for (Result r : scanner) {
            for (Cell cell : r.rawCells()) {
                System.out.println(Bytes.toString(r.getRow()) +"  " + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "===" + Bytes.toString(CellUtil.cloneValue(cell)) +
                        "   Time : " + cell.getTimestamp());
            }
            System.out.println("================================================================================================");
        }

    }

    public static void main(String[] args) throws IOException {
        scan("HolyRobot:HotelBasicInfo");
    }
}
