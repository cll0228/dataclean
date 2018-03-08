package com.holyrobot.scenicclean.startor;

import com.holyrobot.common.*;
import com.holyrobot.dao.RobotObjectDao;
import com.holyrobot.datastandard.ScePriceDataStandard;
import com.holyrobot.datastandard.ScenicDataStandard;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 *
 */
public class ScenicConsumerStartor {
    static Logger logger = LoggerFactory.getLogger(ScenicConsumerStartor.class);

    private static volatile boolean isStop = false;

    public static void main(String[] args) {
        if (args.length < 4) {
            args = new String[]{"cdh01:9092,chd02:9092,cdh04:9092", "topic_scenic", "test2", "latest"};
            logger.debug("param init success");
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
        props.put("auto.offset.reset",offset);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (!isStop) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, byte[]> record : records) {
                try {

                    byte[] bytes = record.value();
                    ReceiverData rd = (ReceiverData) Object2Array.byteArrayToObject(bytes);
                    if (rd.getType() == 4) {
                        logger.info("==================处理景点价格开始===================");
                        Scepriceinfo scepriceinfo = (Scepriceinfo) rd.getData();
                        logger.info("处理前的数据：" + JsonCommon.prepareData(scepriceinfo));
                        ScePriceDataStandard dataStandard = new ScePriceDataStandard();
                        Scepriceinfo spi = dataStandard.standardData(scepriceinfo);
                        logger.info("处理后的数据：" + JsonCommon.prepareData(spi));
                        RobotObjectDao.insertHbase(spi);
                        logger.info("==================处理景点价格结束===================");
                    } else {
                        logger.info("==================处理景点详情开始===================");
                        Sceinfo si = (Sceinfo) rd.getData();
                        logger.info("处理前的数据：" + JsonCommon.prepareData(si));
                        ScenicDataStandard dataStandard = new ScenicDataStandard();
                        dataStandard.standardData(si);
                        logger.info("处理后的数据：" + JsonCommon.prepareData(si));
                        RobotObjectDao.insertHbase(si);
                        logger.info("==================处理景点详情结束===================");
                    }
                    ;
                } catch (Exception e) {
                    logger.error(e.getMessage() + ":" + e.getStackTrace());
                }
            }
        }
    }


}
