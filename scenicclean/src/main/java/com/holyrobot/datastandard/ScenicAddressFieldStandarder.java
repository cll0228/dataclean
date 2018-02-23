package com.holyrobot.datastandard;

/**
 * 景点地址字段数据标准化
 */
public class ScenicAddressFieldStandarder implements ScenicDataFieldStandard {
    @Override
    public String standardData(String originalData) {
        originalData = originalData == null ? "" :originalData;
        String[] words = {"地　　址：", "地址：", "位于", "?", "。"};
        String temp = originalData;
        for (String str : words){
            temp = temp.replace(str,"");
        }
        return temp;
    }
}
