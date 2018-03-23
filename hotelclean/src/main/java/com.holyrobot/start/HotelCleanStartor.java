package com.holyrobot.start;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.dao.HotelObjectDao;
import com.holyrobot.util.DataStandardFunction;
import com.holyrobot.util.StandardUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by cuill on 2018/2/11.
 */
public class HotelCleanStartor {

    private static final Logger logger = LoggerFactory.getLogger(HotelCleanStartor.class);

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("hotel_clean");
        if (args.length == 0) {
            conf.setMaster("local[1]");
        }

        JavaStreamingContext jsc = new JavaStreamingContext(conf, new Duration(3000));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "node5:9092,node4:9092,node3:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", ByteArrayDeserializer.class);
        kafkaParams.put("group.id", "test");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        Collection<String> topics = Arrays.asList("topic_hotel");
        JavaInputDStream<ConsumerRecord<String, byte[]>> stream =
                KafkaUtils.createDirectStream(
                        jsc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, byte[]>Subscribe(topics, kafkaParams)
                );

        JavaDStream<ReceiverData> map = stream.map((ConsumerRecord<String, byte[]> stringConsumerRecord) -> {
            //String key = stringConsumerRecord.key();
            byte[] value = stringConsumerRecord.value();
            //转对象
            return (ReceiverData) StandardUtil.byteArrayToObject(value);

        });

        JavaDStream<ReceiverData> dataJavaDStream = map.map(new DataStandardFunction());
        //保存数据到hbase
        dataJavaDStream.foreachRDD((JavaRDD<ReceiverData> robotObjectJavaRDD) -> {
            List<ReceiverData> list = robotObjectJavaRDD.collect();
            if (null != list && list.size() != 0) {
                for (ReceiverData obj : list) {
                    HotelObjectDao.saveToHbase(obj);
                }
            }
        });
        dataJavaDStream.print();

        logger.info("start success");
        jsc.start();
        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            logger.error("receive data failed", e);
        }
    }
}
