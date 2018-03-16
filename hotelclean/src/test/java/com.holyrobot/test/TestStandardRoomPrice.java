package com.holyrobot.test;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Roomprice;
import com.holyrobot.start.HotelConumerStartor;
import com.holyrobot.util.StandardUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Created by cuill on 2018/3/16.
 * 测试房型
 */
public class TestStandardRoomPrice {

    private ConsumerRecord<String, byte[]> record;

    @Before
    public void testKafkaConsumerBefore() {
        record = Mockito.mock(ConsumerRecord.class);
        ReceiverData receiverData = new ReceiverData();

        receiverData.setFlag(1);
        receiverData.setType(1);
        receiverData.setType(2);
        receiverData.setData(roomprice());
        when(record.value()).thenReturn(StandardUtil.objectToByteArray(receiverData));
    }

    @Test
    public void testKafkaConsumer() {
        HotelConumerStartor.execBytes(record.value());
    }


    private static Roomprice roomprice() {
        Roomprice roomprice = new Roomprice();
        roomprice.setId("27f62c6c-f1cb-11e7-99d7-00ff5f1d87131");
        roomprice.setHotelid("0a4ffb41-f1c3-11e7-99d7-00ff5f1d87131");
        roomprice.setRoomid("0fbf8310-f1c3-11e7-99d7-00ff5f1d87131");
        roomprice.setProductname("[单人床] 含早 取消扣款(VIP通道及4人往返门船票)(含四早)(op)趣订网");
        roomprice.setPrice("1346");
        roomprice.setAvailablenum("0");
        roomprice.setIsbooking("");
        roomprice.setAppliyby("趣订网");
        roomprice.setIswindow("有窗");
        roomprice.setIswifi("有");
        roomprice.setPaymethod("在线付");
        roomprice.setDate("2018-01-05");
        roomprice.setCreatedate(new Date());
        roomprice.setCreator("cuill");
        roomprice.setCreatorid("12312312321");
        roomprice.setRemark("测试用例数据");
        roomprice.setAdminarea("中国,海南");
        return roomprice;
    }
}
