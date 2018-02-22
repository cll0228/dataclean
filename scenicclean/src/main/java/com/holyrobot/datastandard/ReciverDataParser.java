package com.holyrobot.datastandard;

import com.holyrobot.common.*;

public class ReciverDataParser {
    static long count = 0;
    public static ScenicData parse(ReceiverData rd){
        //解析景点价格和详情两个对象
        byte[] priceByte = rd.getBody().get("price");
        byte[] detailByte = rd.getBody().get("detail");
        ScenicPriceData priceData = (ScenicPriceData) Object2Array.byteArrayToObject(priceByte);
        ScenicTicketData ticketData = (ScenicTicketData)Object2Array.byteArrayToObject(detailByte);
        ScenicData scenicData = new ScenicData();
        System.out.println("price=" + priceData + ";tick" + ticketData + ":" + count++);
        return scenicData;
    }
}
