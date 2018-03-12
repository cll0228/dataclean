package com.holyrobot.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cuill on 2018/3/12.
 */
public class TestKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestKafkaConsumer.class);

    final static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 150, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());


    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"cdh01:9092,cdh:9092,cdh04:9092", "TestTopic", "test333", "latest"};
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
        props.put("enable.auto.commit", "false");
        //定时commit的周期
//        props.put("auto.commit.interval.ms", "100");
        //consumer活性超时时间"
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", offset);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("key.deserializer.encoding", "UTF8");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        int count = 0;
        System.out.println("kafka consumer start success。");
        while (true) {
            count++;
            ConsumerRecords<String, byte[]> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, byte[]> record : records) {
//                consumer.commitAsync();/**/
                byte[] bytes = record.value();
                long offset1 = record.offset();
                pre(bytes);//处理业务逻辑
                System.out.println("第" + count + "条消息" + new String(bytes) + ",偏移量 ：" + offset1);
            }
        }
    }

    private static void pre(byte[] bytes) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random r = new Random();
//        if (r.nextInt(10) < 5) {
//            System.out.println(1 / 0);
//        }
    }

}
