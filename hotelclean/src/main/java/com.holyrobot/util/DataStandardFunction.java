package com.holyrobot.util;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.pojo.HotelDetail;
import org.apache.spark.api.java.function.Function;

/**
 * Created by cuill on 2018/2/11.
 */
public class DataStandardFunction implements Function<ReceiverData, ReceiverData> {

    private SaveCleanDataToKafka toKafka;

    @Override
    public ReceiverData call(ReceiverData receiverData) throws Exception {
        if (null == toKafka) {
            toKafka = new SaveCleanDataToKafka();
        }

        if (null != receiverData && null != receiverData.getType() && receiverData.getType() == 1) {
            HotelDetail hotel = HotelStandard.standardHotel(receiverData.getData());
            if (null == hotel) {
                //保存到失败主题
                toKafka.send("fail_clean_hotel_topic", receiverData);
            }
            receiverData.setData(hotel);
        }
        return receiverData;
    }


}