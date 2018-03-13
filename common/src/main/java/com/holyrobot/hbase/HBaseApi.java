package com.holyrobot.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
            conf.set("hbase.zookeeper.quorum", cm.getConfig(ConfigItem.HBASE_ZOOKEEPER_QUORUM));
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
                System.out.println(Bytes.toString(r.getRow()) + "  " + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        "===" + Bytes.toString(CellUtil.cloneValue(cell)) +
                        "   Time : " + cell.getTimestamp());
            }
            System.out.println("========================================");
        }
        System.out.println(count);
    }

    public static void main(String[] args) throws IOException {
        scan("HolyRobot:HotelBasicInfo_clean");


    }
}
