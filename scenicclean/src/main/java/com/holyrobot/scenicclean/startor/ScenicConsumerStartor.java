package com.holyrobot.scenicclean.startor;

import com.holyrobot.common.*;
import com.holyrobot.dao.RobotObjectDao;
import com.holyrobot.datastandard.ScePriceDataStandard;
import com.holyrobot.datastandard.ScenicDataStandard;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
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
    public static void main(String[] args){
        if(args.length <4) {
            logger.error("请输入4个参数");
            System.exit(-1);
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
        props.put("key.deserializer.encoding", "UTF8");
        props.put("value.deserializer.encoding", "UTF8");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (!isStop) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, byte[]> record : records){
                try{
                    byte[] bytes = record.value();
                    ReceiverData rd = (ReceiverData) Object2Array.byteArrayToObject(bytes);
                    if(rd.getType() == 4){
                        Sceinfo si = (Sceinfo)rd.getData();
                        ScenicDataStandard dataStandard = new ScenicDataStandard();
                        Sceinfo sci = dataStandard.standardData(si);
                        RobotObjectDao.insertHbase(sci);
                    }else{
                        Scepriceinfo scepriceinfo = (Scepriceinfo)rd.getData();
                        ScePriceDataStandard dataStandard = new ScePriceDataStandard();
                        Scepriceinfo spi = dataStandard.standardData(scepriceinfo);
                        RobotObjectDao.insertHbase(spi);
                    }
                    ;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}
