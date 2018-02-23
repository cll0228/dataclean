package com.holyrobot.datastandard;

import com.holyrobot.common.*;

public class ReciverDataParser {
    static long count = 0;
    public static Sceinfo parse(ReceiverData rd){
        //解析景点价格和详情两个对象
        byte[] priceByte = rd.getBody().get("price");
        byte[] detailByte = rd.getBody().get("detail");
        Sceinfo priceData = (Sceinfo) Object2Array.byteArrayToObject(priceByte);
        return priceData;
    }
}
