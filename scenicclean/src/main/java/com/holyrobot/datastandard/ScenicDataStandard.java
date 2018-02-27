package com.holyrobot.datastandard;

import com.holyrobot.common.RobotObject;
import com.holyrobot.common.Sceinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 景点数据标准化
 */
public class ScenicDataStandard {
    static Logger logger = LoggerFactory.getLogger(ScenicDataStandard.class);
    public Sceinfo standardData(Sceinfo robotObject){
        logger.info("================景点标准化开始================");
        Sceinfo result = (Sceinfo) robotObject;
        longLatStandard(result);
        nameStandard(result);
        addressStandard(result);
        logger.info("================景点标准化结束================");
        return result;
    }

    //经纬度标准化
    private void longLatStandard(Sceinfo result){
        LongitudeLatDataStandard lld = new LongitudeLatDataStandard();
        //经纬度标准化
        result.setLongitude(lld.standardData(result.getLongitude()));
        result.setLatitude(lld.standardData(result.getLatitude()));
        LongLatLogicDataStandard llds = new LongLatLogicDataStandard();
        String ll = result.getLongitude() + "," + result.getLatitude();
        String longLat = llds.standardData(ll);
        if(longLat.length() > 1){
            result.setLongitude(longLat.split(",")[0]);
            result.setLongitude(longLat.split(",")[1]);
        }

    }
    //名称标注化
    private void nameStandard(Sceinfo result){
        SceientNameFieldStandarder standarder = new SceientNameFieldStandarder();
        result.setName(standarder.standardData(result.getName()));
    }
    //景点地址
    private void addressStandard(Sceinfo result){
        ScenicAddressFieldStandarder standarder = new ScenicAddressFieldStandarder();
        result.setAddress(standarder.standardData(result.getAddress()));
    }
}
