package com.holyrobot.datastandard;

/**
 * 景点经纬度数据标准化
 */
public class LongitudeLatDataStandard implements ScenicDataFieldStandard {
    /**
     * orginalData 用,隔开
     * @param originalData
     * @return
     */
    @Override
    public String standardData(String originalData) {
        originalData = originalData == null ? "":originalData;
//        清洗特殊字符 '[', ']', ',', ' '
        char[] speciChar = {'[', ']', ',', ' '};
        String temp = originalData;
        for (char ch : speciChar){
            temp = temp.replace(String.valueOf(ch),"");
        }
        return temp.trim();
    }
}
