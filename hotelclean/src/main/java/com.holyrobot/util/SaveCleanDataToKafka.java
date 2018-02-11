package com.holyrobot.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by cuill on 2018/2/11.
 */
public class SaveCleanDataToKafka {

    static {
        //初始化kafka的配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node5:9092");//broker 集群地址
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "MsgProducer");//自定义客户端id
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);//key 序列号方式
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.ByteArraySerializer.class);//value 序列化方式
        producer = new KafkaProducer<>(properties);
    }

    private static   KafkaProducer<String, byte[]> producer;

    public void send(String topic,Object obj){
        producer.send(new ProducerRecord<>(topic,StandardUtil.objectToByteArray(obj)));
    }




}
