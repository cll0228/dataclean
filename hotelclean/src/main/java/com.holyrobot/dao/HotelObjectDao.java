package com.holyrobot.dao;

import com.holyrobot.common.Hotelinfo;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Roombasicinfo;
import com.holyrobot.common.Roomprice;
import com.holyrobot.hbase.HBaseApi;
import com.holyrobot.hbase.HbaseColumn;
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
            Hotelinfo hotelDetail = (Hotelinfo) obj.getData();
            tableName = "HolyRobot:HotelBasicInfo_clean";
            String rowKey = hotelDetail.getName() + "_" + hotelDetail.getAddress();
            hotelObjToHbaseSchema(obj, rowKey, tableName, Hotelinfo.class);
        }

        if (obj.getType() == 2) {
            Roomprice priceData = (Roomprice) obj.getData();
            tableName = "HolyRobot:RoomPrice_clean";
            String rowKey = priceData.getHotelid() + "_" + priceData.getRoomid();
            hotelObjToHbaseSchema(obj, rowKey, tableName, Roomprice.class);
        }

        if (obj.getType() == 3) {
            Roombasicinfo hotelRoomData = (Roombasicinfo) obj.getData();
            tableName = "HolyRobot:RoomBasicInfo_clean";
            String rowKey = hotelRoomData.getRoomtype();
            hotelObjToHbaseSchema(obj, rowKey, tableName, Roombasicinfo.class);
        }

    }

    private static void hotelObjToHbaseSchema(ReceiverData receiverData, String rowKey, String tableName, Class cls) {
        List<HbaseColumn> cols = new ArrayList<HbaseColumn>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
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