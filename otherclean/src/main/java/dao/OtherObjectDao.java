package dao;

import com.holyrobot.common.*;
import hbase.HBaseJavaAPI;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by cuill on 2018/2/23.
 */
public class OtherObjectDao {

    private static final Logger logger = LoggerFactory.getLogger(OtherObjectDao.class);

    private static String NAMESPACE = "HolyRobot";

    public static void saveToHbase(ReceiverData obj) {
        if (null == obj || null == obj.getType() || null == obj.getData()) {
            return;
        }

        String tableName = null;
        if (obj.getType() == 8) {
            Flightinfo flightinfo = (Flightinfo) obj.getData();
            String rowKey = flightinfo.getId();
            Map map = new HashMap<>();

            map.put("rowKey", rowKey);
            map.put("entity", flightinfo);
            JSONObject jsonObject = new JSONObject().fromObject(map);
            addData("Flightinfo",jsonObject);


        }
        if (obj.getType() == 9) {
            Flightpriceinfo flightpriceinfo = (Flightpriceinfo) obj.getData();
            String rowKey = flightpriceinfo.getId();
            Map map = new HashMap<>();

            map.put("rowKey", rowKey);
            map.put("entity", flightpriceinfo);
            JSONObject jsonObject = new JSONObject().fromObject(map);
            addData("Flightpriceinfo",jsonObject);


        }
        if (obj.getType() == 10) {
            Pictureinfo pictureinfo = (Pictureinfo) obj.getData();
                String rowKey = pictureinfo.getId();
                Map map = new HashMap<>();

                map.put("rowKey", rowKey);
                map.put("entity", pictureinfo);
                JSONObject jsonObject = new JSONObject().fromObject(map);
                addData("Pictureinfo",jsonObject);


        }

        if (obj.getType() == 11) {
            Commentinfo commoninfo = (Commentinfo) obj.getData();
                String rowKey = commoninfo.getId();
                Map map = new HashMap<>();

                map.put("rowKey", rowKey);
                map.put("entity", commoninfo);
                JSONObject jsonObject = new JSONObject().fromObject(map);
                addData("Commentinfo",jsonObject);


        }
        if (obj.getType() == 12) {
            Addressinfo addressinfo = (Addressinfo) obj.getData();
            String rowKey = addressinfo.getId();
            Map map = new HashMap<>();

            map.put("rowKey", rowKey);
            map.put("entity", addressinfo);
            JSONObject jsonObject = new JSONObject().fromObject(map);
            addData("Addressinfo",jsonObject);


        }

    }

    /**
     * 为表添加数据（适合知道有多少列族的固定表）
     * @param "tableName":"表名"
     * @param obj
     * JSON格式说明：{"rowKey":"行键",“entity”：“实体对象”}
     * @return
     */
    public static boolean addData(String tableName, JSONObject obj) {
        boolean flag = false;
        try {
            String rowKey = obj.getString("rowKey");
            JSONObject entity = new JSONObject().fromObject(obj.get("entity").toString());
            //获取实体JSON字段映射
            Map<String,String> map = getEntity(tableName,entity);
            tableName = NAMESPACE + ":" + tableName+"_clean";
            Set<String> keySet = map.keySet();
            Iterator<String> iterator = keySet.iterator();
            String[] column = new String[keySet.size()];
            String[] value = new String[keySet.size()];
            int i = 0;
            while(iterator.hasNext()){
                String key = iterator.next();
                String val = map.get(key);
                column[i] = key;
                value[i] = val;
                i++;
            }
            HBaseJavaAPI.addData(rowKey,tableName,column,value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实体类JSON对象与表中列映射
     * @param tableName     表名
     * @param obj           实体类JSON对象
     * @return
     */
    public static Map<String,String> getEntity(String tableName,JSONObject obj){
        Map<String,String> map = new HashMap<>();
        try {
            Class cla = Class.forName("com.holyrobot.common."+tableName);
//            Class cla = Class.forName("com.holyrobot.common.RoomBasicInfoEntity");
            //获取类中所有属性
            Field[] fields = cla.getDeclaredFields();
            for (Field field:fields) {
                if(obj.containsKey(field.getName()) && StringUtils.isNotBlank(obj.getString(field.getName()))){
                    map.put(field.getName(),obj.getString(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}