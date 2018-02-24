package com.holyrobot.datastandard;

import com.holyrobot.common.Sceinfo;
import com.holyrobot.common.Scepriceinfo;

/**
 * 景点价格标准化
 */
public class ScePriceDataStandard {
    public Scepriceinfo standardData(Scepriceinfo robotObject){
        Scepriceinfo result = (Scepriceinfo) robotObject;
        priceStandard(result);
        return result;
    }
    //景点价格标准化
    private void priceStandard(Scepriceinfo result){
        ScientPriceDataStanadard stanadard = new ScientPriceDataStanadard();
        result.setMarketingprice(stanadard.standardData(result.getMarketingprice()));
        result.setSaleprice(stanadard.standardData(result.getSaleprice()));
    }
}
