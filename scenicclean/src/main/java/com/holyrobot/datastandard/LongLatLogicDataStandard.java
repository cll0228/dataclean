package com.holyrobot.datastandard;

/**
 * 经纬度逻辑处理
 */
public class LongLatLogicDataStandard implements ScenicDataFieldStandard {
    @Override
    public String standardData(String originalData) {
        String[] longLat = originalData.split(",");
        if (longLat.length <= 1) return "";
        String longitude = "";
        String lat = "";
//        处理经纬度放反（经纬小于90且纬度大于90）
//        3.处理经纬度错误（经纬度为空，或经纬度都大于90或小于90）
        if(longLat[0].length() >1 && longLat[1].length() > 1){
            Double latD = Double.parseDouble(longLat[1]);
            Double longD = Double.parseDouble(longLat[0]);
            if(longD < 90 && latD > 90){  //取反
                longitude = longLat[1];
                lat = longLat[0];
            }else if(longD > 90 && latD >90){
                longitude = "";
                lat = "";
            }
            else{
                longitude = longLat[0];
                lat = longLat[1];
            }
        }
        else if(longLat[0].length() ==0 && longLat[1].length()==0){
            longitude = "";
            lat = "";
        }
        return longitude + "," + lat;
    }
}
