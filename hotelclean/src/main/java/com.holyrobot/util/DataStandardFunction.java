package com.holyrobot.util;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.pojo.HotelDetail;
import com.holyrobot.pojo.HotelRoomData;
import org.apache.spark.api.java.function.Function;

/**
 * Created by cuill on 2018/2/11.
 */
public class  DataStandardFunction implements Function<ReceiverData,ReceiverData> {

    private  HotelStandard hotelStandard;
    private   SaveCleanDataToKafka toKafka;

    @Override
    public ReceiverData call(ReceiverData receiverData) throws Exception {
        if(null == hotelStandard ||null == toKafka){
            hotelStandard = new HotelStandard();
            toKafka= new SaveCleanDataToKafka();
        }
        if (receiverData.getType() == 1) {
            HotelDetail hotel = hotelStandard.standardHotel(receiverData.getData());
            receiverData.setData(hotel);
            if(null != hotel){
               //保存到其它主题
                toKafka.send("fail_clean_hotel_topic",receiverData);
            }
        }
        toKafka.send("clean_hotel_topic",receiverData);
        return receiverData;
    }
}