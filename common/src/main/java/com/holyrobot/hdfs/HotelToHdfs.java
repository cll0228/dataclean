package com.holyrobot.hdfs;

import com.holyrobot.common.Hotelinfo;
import com.holyrobot.hbase.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by cuill on 2018/3/27.
 */
public class HotelToHdfs {
    // 日志
    private static Logger logger = LoggerFactory.getLogger(HotelToHdfs.class);

    /**
     * 声明静态配置
     */
    private static Configuration conf = null;

    static {
        try {
            conf = HBaseConfiguration.create();
            ConfigManager cm = new ConfigManager();
            conf.set("hbase.zookeeper.quorum", cm.getConfig("hbase.zookeeper.quorum"));
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            logger.info("hbase初始化配置结束");
        } catch (Exception e) {
            logger.error("====hbase初始化配置失败:" + e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        HTable table = new HTable(conf, Bytes.toBytes("HolyRobot:HotelBasicInfo_clean"));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (Result r : scanner) {
            count++;
            if (count == 300) {
                break;
            }
            Hotelinfo h = new Hotelinfo();
            for (Cell cell : r.rawCells()) {
                String key = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                if (key.equals("id")) {
                    h.setId("" != value ? value : "null");
                }
                if (key.equals("address")) {
                    h.setAddress("" != value ? value : "null");
                }
                if (key.equals("adminarea")) {
                    h.setAdminarea("" != value ? value : "null");
                }
                if (key.equals("beennum")) {
                    h.setBeennum("" != value ? value : "null");
                }
                if (key.equals("createdate")) {
                    h.setCreatedate(fm.parse("" != value ? value : "null"));
                }
                try {
                    if (key.equals("datasource")) {
                        h.setDatasource("" != value ? value : "null");
                    }
                } catch (Exception e) {
                    h.setDatasource("null");
                }

                if (key.equals("grade")) {
                    h.setGrade("" != value ? value : "null");
                }
                if (key.equals("gradenum")) {
                    h.setGradenum("" != value ? value : "null");
                }
                if (key.equals("introduction")) {
                    h.setIntroduction("" != value ? value : "null");
                }
                if (key.equals("longitude")) {
                    h.setLongitude("" != value ? value : "null");
                }
                if (key.equals("latitude")) {
                    h.setLatitude("" != value ? value : "null");
                }
                if (key.equals("name")) {
                    h.setName("" != value ? value : "null");
                }
                if (key.equals("price")) {
                    h.setPrice("" != value ? value : "null");
                }
                if (key.equals("remark")) {
                    h.setRemark("" != value ? value : "null");
                }
                if (key.equals("star")) {
                    h.setStar("" != value ? value : "null");
                }
                if (key.equals("urlid")) {
                    h.setUrlid("" != value ? value : "null");
                }
                if (key.equals("whantto")) {
                    h.setWhantto("" != value ? value : "null");
                }
            }
            sb.append(h.toHdfsString());
        }
        //
        File file = new File("c:/a.txt");
//        FileUtils.writeStringToFile(file, sb.toString());

        new HdfsOperate().uploadFile("c:/a.txt", "hdfs://cm-master01:8020/user/hotel/");

    }

    private static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

}
