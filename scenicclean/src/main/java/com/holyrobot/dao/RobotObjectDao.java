package com.holyrobot.dao;


import com.holyrobot.common.Sceinfo;
import com.holyrobot.common.Scepriceinfo;
import com.holyrobot.hbase.HBaseApi;
import com.holyrobot.hbase.HbaseColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class RobotObjectDao {

    private static final Logger logger = LoggerFactory.getLogger(RobotObjectDao.class);

    private static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void insertHbase(Serializable object) throws Exception {
        if (object instanceof Sceinfo) {
            Sceinfo scenicData = (Sceinfo) object;
            String rowKey = scenicData.getAdminarea() + "_" + scenicData.getId();
            insertHbase(object, rowKey, "HolyRobot:SceInfo_clean");
        } else if (object instanceof Scepriceinfo) {
            Scepriceinfo scenicData = (Scepriceinfo) object;
            String rowKey = scenicData.getAdminarea() + "_" + scenicData.getId();
            insertHbase(object, rowKey, "HolyRobot:ScePriceInfo_clean");
        }
    }

    /**
     * 插入数据库
     *
     * @param object
     * @param rowKey
     * @param tableName
     * @throws IOException
     */
    private static void insertHbase(Object object, String rowKey, String tableName) throws IOException {
        List<HbaseColumn> cols = new ArrayList<HbaseColumn>();
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getName().equals("rowKey") || field.getName().equals("creator") || field.getName().equals("creatorid") || field.getName().equals("serialVersionUID")) {
                    continue;
                }
                HbaseColumn col = new HbaseColumn();
                col.setColName(field.getName());
                col.setFamilyName("info");
                if ("createdate".equals(field.getName())) {
                    col.setColValue(fm.format(new Date(field.get(object).toString())));
                } else {
                    col.setColValue(field.get(object) == null ? "" : field.get(object).toString());
                }
                cols.add(col);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            HBaseApi.insertRow(tableName, rowKey, cols);
            logger.info("保存hbase成功，class=" + object.getClass());
        } catch (IOException e) {
            logger.error(rowKey + tableName + " 保存hbase失败", e);
        }
    }

}
