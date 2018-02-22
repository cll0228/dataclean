package com.holyrobot.datastandard;

import com.holyrobot.common.RobotObject;
import com.holyrobot.common.ScenicData;

/**
 * 景点数据标准化
 */
public class ScenicDataStandard {
    public ScenicData standardData(RobotObject robotObject){
        ScenicData result = (ScenicData) robotObject;
        longLatStandard(result);
        nameStandard(result);
        priceStandard(result);
        addressStandard(result);
        return result;
    }

    //经纬度标准化
    private void longLatStandard(ScenicData result){
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
    private void nameStandard(ScenicData result){
        SceientNameFieldStandarder standarder = new SceientNameFieldStandarder();
        result.setName(standarder.standardData(result.getName()));
    }
    //景点价格标准化
    private void priceStandard(ScenicData result){
        ScientPriceDataStanadard stanadard = new ScientPriceDataStanadard();
//        for(ScenicPriceData price : result.getPriceDataList()){
//            price.setSaleprice(stanadard.standardData(price.getSaleprice()));
//            price.setpublicingprice(stanadard.standardData(price.getpublicingprice()));
//        }
    }
    //景点地址
    private void addressStandard(ScenicData result){
        ScenicAddressFieldStandarder standarder = new ScenicAddressFieldStandarder();
        result.setAddress(standarder.standardData(result.getAddress()));
    }
}
