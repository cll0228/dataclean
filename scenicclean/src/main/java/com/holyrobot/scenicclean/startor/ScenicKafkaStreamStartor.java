package com.holyrobot.scenicclean.startor;

import com.holyrobot.common.*;
import com.holyrobot.datastandard.ParseValueMapper;
import com.holyrobot.datastandard.SaveHbaseMapper;
import com.holyrobot.datastandard.ScenicForeachAction;
import com.holyrobot.datastandard.StandardMapper;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.codehaus.jackson.map.deser.std.StringDeserializer;
import scala.collection.immutable.Stream;

import java.util.Properties;

/**
 * 景点kafka流处理启动器
 */
public class ScenicKafkaStreamStartor {
    public static void main(String[] args){
        Properties config = new Properties();
        ConfigManager cm = new ConfigManager();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG,"scienprocess");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,cm.getConfig(ConfigItem.KAFKA_SERVER));
        config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());
        KStreamBuilder builder = new KStreamBuilder();
        KStream<String,byte[]> scienStream = builder.stream(cm.getConfig(ConfigItem.SCENIC_INPUT_TOPIC));
        //流转成ScenicData
        KStream<String,ScenicData> scenicDataStream = scienStream.mapValues(new ParseValueMapper());
        //标准化景点数据
        KStream<String,ScenicData> standardStream = scenicDataStream.mapValues(new StandardMapper());
        //保存到hbase
        standardStream.mapValues(new SaveHbaseMapper());
        KafkaStreams streams = new KafkaStreams(builder,config);
        streams.start();
    }
}
