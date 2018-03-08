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
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cuill on 2018/2/26.
 * 清洗程序消费者
 */
public class HotelConumerStartor {
    private static final Logger logger = LoggerFactory.getLogger(HotelConumerStartor.class);

    final static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 150, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());


    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"cdh01:9092,cdh:9092,cdh04:9092", "topic_hotel", "test11", "latest"};
            logger.debug("param init success");
        }
        String bootstrap = args[0];
        String topic = args[1];
        String group = args[2];
        String offset = args[3];

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        props.put("group.id", group);
        //自动commit
        props.put("enable.auto.commit", "true");
        //定时commit的周期
        props.put("auto.commit.interval.ms", "1000");
        //consumer活性超时时间
        props.put("session.timeout.ms", "30000");
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
                    logger.debug("消费者接受消息===== Type == " + rd.getType() + rd.getData().getClass() + " 启动处理");
                    try {
                        executor.execute(new ProcessObj(rd));
                        logger.debug("启动线程处理数据 Class = " + rd.getData().getClass());
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
            logger.debug("线程==name" + Thread.currentThread().getName() + "执行清洗操作");
            if (receiverData.getType() == 1) {
                logger.debug("TYPE = 1，进入数据清洗");
                Hotelinfo hotelinfo = (Hotelinfo) receiverData.getData();
                try {
                    logger.debug("标准化前数据 hotelInfo = " + hotelinfo.toString());
                    Hotelinfo standedHotel = HotelStandard.standardHotel(hotelinfo);
                    if (null != standedHotel) {
                        receiverData.setData(standedHotel);
                        logger.debug("酒店数据标准化成功，标准化后数据 hotelInfo = " + receiverData.getData().toString());
                    } else {
                        logger.debug("酒店数据异常返回 null hotelinfo = " + hotelinfo.toString());
                        return;
                    }
                } catch (Exception e) {
                    logger.error(receiverData.getData().getClass() + " 酒店数据标准化失败 酒店hotelinfo =" + hotelinfo.toString(), e);
                }
            }
            //保存hbase
            if (null != receiverData && null != receiverData.getData()) {
                HotelObjectDao.saveToHbase(receiverData);
                logger.debug("保存hhbase成功 Data = " + receiverData.getData().toString());
            }
        }
    }

}



