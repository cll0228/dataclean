package com.holyrobot.start;

import com.holyrobot.common.Hotelinfo;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.dao.HotelObjectDao;
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
            logger.info("param init success");
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
                    if (null == rd || null == rd.getType() || null == rd.getData()) {
                        continue;
                    }
                    logger.info("消费者接受消息===== Type == " + rd.getType() + rd.getData().getClass() + " 启动处理");
                    try {
                        new ProcessObj(rd).start();
                        logger.info("启动线程处理数据 Class = " + rd.getData().getClass());
                    } catch (Exception e) {
                        logger.error("启动线程失败处理数据 Class = " + rd.getData().getClass(), e);
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
                logger.info("TYPE = 1，进入数据清洗");
                Hotelinfo hotelinfo = (Hotelinfo) receiverData.getData();
                try {
                    logger.info("清洗前数据 hotelDetail = " + hotelinfo.toString());
                    receiverData.setData(HotelStandard.standardHotel(hotelinfo));
                    logger.info(receiverData.getData().getClass() + " 酒店数据标准化成功 酒店名称 = " + hotelinfo.getName());
                    logger.info("清洗后数据 hotelDetail = " + receiverData.getData().toString());
                } catch (Exception e) {
                    logger.error(receiverData.getData().getClass() + " 酒店数据标准化失败 酒店名称= " + hotelinfo.getName(), e);
                }
            }
            //保存hbase
            HotelObjectDao.saveToHbase(receiverData);
            logger.info("保存hhbase成功 Data = " + receiverData.getData().getClass());
        }
    }

}



