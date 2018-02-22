package com.holyrobot.datastandard;

/**
 * 景点价格标准化
 */
public class ScientPriceDataStanadard implements ScenicDataFieldStandard {
    @Override
    public String standardData(String originalData) {
        return originalData.replace("￥","");
    }
}
