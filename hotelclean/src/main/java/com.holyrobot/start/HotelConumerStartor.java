package com.holyrobot.start;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.dao.HotelObjectDao;
import com.holyrobot.pojo.HotelDetail;
import com.holyrobot.util.HotelStandard;
import com.holyrobot.util.StandardUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by cuill on 2018/2/26.
 */
public class HotelConumerStartor {
    private static final Logger logger = LoggerFactory.getLogger(HotelConumerStartor.class);


    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"node5:9092,node4:9092,node3:9092", "topic_hotel", "test2", "latest"};
            System.out.println("param init success");
        }
        String bootstrap = args[0];
        String topic = args[1];
        String group = args[2];
        String offset = args[3];

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");  //自动commit
        props.put("auto.commit.interval.ms", "1000"); //定时commit的周期
        props.put("session.timeout.ms", "30000"); //consumer活性超时时间
        props.put("auto.offset.reset", offset);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("key.deserializer.encoding", "UTF8");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, byte[]> record : records) {
                try {
                    byte[] bytes = record.value();
                    ReceiverData rd = (ReceiverData) StandardUtil.byteArrayToObject(bytes);
                    System.out.println("消费者接受消息=====" + rd.getData().getClass() + " 启动处理");
                    if (null == rd || null == rd.getType() || null == rd.getData()) {
                        continue;
                    }
                    try {
                        new ProcessObj(rd).start();
                        System.out.println("start a thread to process data sucess Class = " + rd.getData().getClass());
                    } catch (Exception e) {
                        logger.error("start a thread to process data failed Class = " + rd.getData().getClass(), e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("consumer error", e);
                }
            }
        }
    }

    public static class ProcessObj extends Thread {
        private static final Logger logger = LoggerFactory.getLogger(ProcessObj.class);

        private ReceiverData receiverData;


        public ReceiverData getReceiverData() {
            return receiverData;
        }

        public void setReceiverData(ReceiverData receiverData) {
            this.receiverData = receiverData;
        }

        public ProcessObj(ReceiverData receiverData) {
            this.receiverData = receiverData;
        }

        @Override
        public void run() {
            if (receiverData.getType() == 1) {
                HotelDetail hotelDetail = (HotelDetail) receiverData.getData();
                try {
                    receiverData.setData(HotelStandard.standardHotel(hotelDetail));
                    System.out.println(receiverData.getData().getClass() + " standard success ID = " + hotelDetail.getName());
                } catch (Exception e) {
                    logger.error(receiverData.getData().getClass() + " standard fail ID = " + hotelDetail.getName(), e);
                }
            }
            //保存hbase
            HotelObjectDao.saveToHbase(receiverData);
            System.out.println("save to hbase success Data = " + receiverData.getClass());
        }
    }

}



