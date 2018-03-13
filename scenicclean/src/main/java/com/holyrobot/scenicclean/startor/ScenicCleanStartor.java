package com.holyrobot.scenicclean.startor;

import com.holyrobot.hbase.ConfigManager;
import com.holyrobot.common.Object2Array;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.RobotObject;
import com.holyrobot.dao.RobotObjectDao;
import com.holyrobot.datastandard.DataStandardFunction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ScenicCleanStartor {
    static Logger logger = LoggerFactory.getLogger(ScenicCleanStartor.class);

    public static void main(String[] args) throws Exception {
        ConfigManager cm = new ConfigManager();
        SparkConf conf = new SparkConf().setAppName("realtime").setMaster("local");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(conf, new Duration(3000));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "node3:9092,node4:9092,node5:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", ByteArrayDeserializer.class);
        kafkaParams.put("group.id", "test1");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        Collection<String> topics = Arrays.asList("realtime");
        JavaInputDStream<ConsumerRecord<String, byte[]>> stream =
                KafkaUtils.createDirectStream(
                        javaStreamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, byte[]>Subscribe(topics, kafkaParams)
                );

        //byte[]转string
        JavaDStream<ReceiverData> map = stream.map(new Function<ConsumerRecord<String, byte[]>, ReceiverData>() {
            @Override
            public ReceiverData call(ConsumerRecord<String, byte[]> stringConsumerRecord) throws Exception {
                String key = stringConsumerRecord.key();
                byte[] value = stringConsumerRecord.value();
                String strVal = new String(value);
                System.out.println("=================s" + strVal);
                ReceiverData rd = (ReceiverData) Object2Array.byteArrayToObject(value);  //解析
                return rd;
            }
        });
        map.print();
        //标准化算子
        JavaDStream<RobotObject> standarded = map.map(new DataStandardFunction());
        //保存到hbase
        standarded.foreachRDD(new VoidFunction2<JavaRDD<RobotObject>, Time>() {
            @Override
            public void call(JavaRDD<RobotObject> robotObjectJavaRDD, Time time) throws Exception {
                //保存到hbase Api
                List<RobotObject> list = robotObjectJavaRDD.collect();
                if (null != list && list.size() != 0) {
                    System.out.println(list.toString());
                }

                for (RobotObject obj : list) {
                    RobotObjectDao.insertHbase(obj);
                }
            }
        });
        logger.info("启动实时处理成功。");
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
}
