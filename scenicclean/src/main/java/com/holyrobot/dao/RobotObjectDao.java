package com.holyrobot.dao;


import com.holyrobot.common.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class RobotObjectDao {
    public static void insertHbase(Serializable object) throws Exception{
        if(object instanceof Sceinfo){
            Sceinfo scenicData = (Sceinfo)object;
            String rowKey = scenicData.getId() ;
            insertHbase(object,rowKey,"HolyRobot:SceInfo");
            ESDocumentManager.insertDoc("scenic","detail",scenicData.getId(),scenicData);
        }else if(object instanceof Scepriceinfo){
            Scepriceinfo scenicData = (Scepriceinfo)object;
            String rowKey = scenicData.getId();
            insertHbase(object,rowKey,"HolyRobot:ScePriceInfo");
            ESDocumentManager.insertDoc("scenicprice","price",scenicData.getId(),scenicData);
        }
    }

    /**
     * 插入数据库
     * @param object
     * @param rowKey
     * @param tableName
     * @throws IOException
     */
    private static void insertHbase(Object object,String rowKey,String tableName) throws IOException {
        List<HbaseColumn> cols = new ArrayList<HbaseColumn>();
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getName().equals("rowKey")) {
                    continue;
                }
                HbaseColumn col = new HbaseColumn();
                if (field.getName().equals("urlid")) {
                    //todo如何转化为url，看后期数据
                    col.setColName("Url");
                } else {
                    col.setColName(field.getName());
                }
                col.setFamilyName("info");
                col.setColValue(field.get(object) == null ? "" : field.get(object).toString());
                cols.add(col);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HBaseApi.insertRow(tableName, rowKey, cols);
    }

}
