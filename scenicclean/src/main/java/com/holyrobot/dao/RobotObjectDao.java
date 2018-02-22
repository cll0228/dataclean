package com.holyrobot.dao;


import com.holyrobot.common.HBaseApi;
import com.holyrobot.common.HbaseColumn;

import com.holyrobot.common.RobotObject;
import com.holyrobot.common.ScenicData;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class RobotObjectDao {
    public static void insertHbase(RobotObject object) throws Exception{
        if(object instanceof ScenicData){
            ScenicData scenicData = (ScenicData)object;
            Random random = new Random();
            String rowKey = scenicData.address + scenicData.getName() + random.nextInt(15566666);
            insertHbase(object,rowKey,"HolyRobot:SceInfo");
//            ESIndeBuilder.buildScenicIndex(scenicData);
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
        Field[] fields = cls.getFields();
        for (Field field :
                fields) {
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
