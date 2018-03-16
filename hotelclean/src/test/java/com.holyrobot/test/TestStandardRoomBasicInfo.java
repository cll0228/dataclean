package com.holyrobot.test;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Roombasicinfo;
import com.holyrobot.start.HotelConumerStartor;
import com.holyrobot.util.StandardUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by cuill on 2018/3/16.
 * 测试房型
 */
public class TestStandardRoomBasicInfo {

    private ConsumerRecord<String, byte[]> record;

    @Before
    public void testKafkaConsumerBefore() {
        record = Mockito.mock(ConsumerRecord.class);
        ReceiverData receiverData = new ReceiverData();

        receiverData.setFlag(1);
        receiverData.setType(1);
        receiverData.setType(3);
        receiverData.setData(roombasicinfo());
        when(record.value()).thenReturn(StandardUtil.objectToByteArray(receiverData));
    }

    @Test
    public void testKafkaConsumer() {
        HotelConumerStartor.execBytes(record.value());
    }


    private static Roombasicinfo roombasicinfo() {
        Roombasicinfo roombasicinfo = new Roombasicinfo();
        roombasicinfo.setId(UUID.randomUUID().toString());
        roombasicinfo.setHotelid(UUID.randomUUID().toString());
        roombasicinfo.setRoomtype("睿选豪华套房");
        roombasicinfo.setBedtype("大床");
        roombasicinfo.setIsaddbed("可加床");
        roombasicinfo.setBedsize("大床1.8米");
        roombasicinfo.setFloor("3-10层");
        roombasicinfo.setPeoplecount("2");
        roombasicinfo.setCreatedate(new Date());
        roombasicinfo.setCreator("cuill");
        roombasicinfo.setCreatorid("12312312321");
        roombasicinfo.setRemark("测试用例数据");
        roombasicinfo.setAdminarea("中国,海南");
        return roombasicinfo;
    }
}
