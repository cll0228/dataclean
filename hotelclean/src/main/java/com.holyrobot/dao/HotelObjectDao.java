package com.holyrobot.dao;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.hbase.HBaseApi;
import com.holyrobot.hbase.HbaseColumn;
import com.holyrobot.common.HotelDetail;
import com.holyrobot.common.HotelRoomData;
import com.holyrobot.common.HotelRoomPriceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuill on 2018/2/23.
 */
public class HotelObjectDao {

    private static final Logger logger = LoggerFactory.getLogger(HotelObjectDao.class);

    public static void saveToHbase(ReceiverData obj) {
        if (null == obj || null == obj.getType() || null == obj.getData()) {
            return;
        }

        String tableName = null;
        if (obj.getType() == 1) {
            HotelDetail hotelDetail = (HotelDetail) obj.getData();
            tableName = "HolyRobot:HotelBasicInfo_clean";
            String rowKey = hotelDetail.getName() + "_" + hotelDetail.getAddress();
            hotelObjToHbaseSchema(obj, rowKey, tableName, HotelDetail.class);
        }

        if (obj.getType() == 2) {
            HotelRoomPriceData priceData = (HotelRoomPriceData) obj.getData();
            tableName = "HolyRobot:RoomPrice_clean";
            String rowKey = priceData.getHotelId() + "_" + priceData.getRoomId();
            hotelObjToHbaseSchema(obj, rowKey, tableName, HotelRoomPriceData.class);
        }

        if (obj.getType() == 3) {
            HotelRoomData hotelRoomData = (HotelRoomData) obj.getData();
            tableName = "HolyRobot:RoomBasicInfo_clean";
            String rowKey = hotelRoomData.getRoomType();
            hotelObjToHbaseSchema(obj, rowKey, tableName, HotelRoomData.class);
        }

    }

    private static void hotelObjToHbaseSchema(ReceiverData receiverData, String rowKey, String tableName, Class cls) {
        List<HbaseColumn> cols = new ArrayList<HbaseColumn>();
        Field[] fields = cls.getFields();
        for (Field field :
                fields) {
            try {
                if (field.getName().equals("rowKey") || field.getName().equals("creator") || field.getName().equals("creatorId")) {
                    continue;
                }
                HbaseColumn col = new HbaseColumn();
                col.setColName(field.getName());
                col.setFamilyName("info");
                col.setColValue(field.get(receiverData.getData()) == null ? "" : field.get(receiverData.getData()).toString());
                cols.add(col);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            HBaseApi.insertRow(tableName, rowKey, cols);
        } catch (IOException e) {
            logger.error(rowKey + tableName + " 保存hbase失败");
            e.printStackTrace();
        }
    }


}