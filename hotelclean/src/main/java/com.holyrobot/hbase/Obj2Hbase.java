package com.holyrobot.hbase;

import com.holyrobot.pojo.HotelDetail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obj2Hbase {
    public static void objToHbaseSchema(HotelDetail hotel) throws IOException {
        List<HbaseColumn> cols = new ArrayList<HbaseColumn>();
        Class cls = HotelDetail.class;
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
                col.setColValue(field.get(hotel) == null ? "" : field.get(hotel).toString());
                cols.add(col);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String rowKey = hotel.getName() + "_" + hotel.getAddress() + "_" + new Random().nextInt(1000000);
        HBaseApi.insertRow("HolyRobot:HotelBasicInfo", rowKey, cols);
    }
}
