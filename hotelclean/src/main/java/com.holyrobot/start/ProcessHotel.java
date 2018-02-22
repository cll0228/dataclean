package com.holyrobot.start;

import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.RobotObject;
import com.holyrobot.util.DataStandardFunction;
import com.holyrobot.util.HotelStandard;
import com.holyrobot.util.StandardUtil;
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

/**
 * Created by cuill on 2018/2/11.
 */
public class ProcessHotel {

    private static final Logger logger = LoggerFactory.getLogger(ProcessHotel.class);

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("processhotel");
        if (args.length == 0) {
            conf.setMaster("local[1]");
        }

        JavaStreamingContext jsc = new JavaStreamingContext(conf, new Duration(3000));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "node5:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", ByteArrayDeserializer.class);
        kafkaParams.put("group.id", "test");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        Collection<String> topics = Arrays.asList("myhotel");
        JavaInputDStream<ConsumerRecord<String, byte[]>> stream =
                KafkaUtils.createDirectStream(
                        jsc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, byte[]>Subscribe(topics, kafkaParams)
                );

        JavaDStream<ReceiverData> map = stream.map(new Function<ConsumerRecord<String, byte[]>, ReceiverData>() {
            @Override
            public ReceiverData call(ConsumerRecord<String, byte[]> stringConsumerRecord) throws Exception {
//                String key = stringConsumerRecord.key();
                byte[] value = stringConsumerRecord.value();
                //转对象
                ReceiverData rd = (ReceiverData) StandardUtil.byteArrayToObject(value);
                return rd;
            }
        });

        JavaDStream<ReceiverData> dataJavaDStream = map.map(new DataStandardFunction());

        dataJavaDStream.print();
        logger.info("启动实时处理成功。");
        jsc.start();
        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            logger.error("receive data failed",e);
        }


    }
}
