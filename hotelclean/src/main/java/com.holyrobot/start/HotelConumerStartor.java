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
 * 清洗程序消费者.
 */
public class HotelConumerStartor {

    /**
     * 日志类.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HotelConumerStartor.class);

    /**
     * 线程池.
     */
    private static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(20, 30, 5, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>());

    /**
     * 消费者程序入口.
     *
     * @param args 启动程序传入的参数集合，zk集群，kafkatopoic，消费者组，每次启动消费位置.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"cm-node01:9092,cm-node02:9092,cm-node03:9092",
                    "topic_hotel", "test3", "latest"};
            LOGGER.debug("param init success");
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
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("key.deserializer.encoding", "UTF8");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, byte[]> records =
                    consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, byte[]> record : records) {
                try {
                    execBytes(record.value());
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("consumer error", e);
                }
            }
        }
    }

    /**
     * 接受kafka字节数组处理业务逻辑.
     *
     * @param bytes 字节数组.
     */
    public static void execBytes(final byte[] bytes) {
        ReceiverData rd = (ReceiverData) StandardUtil.byteArrayToObject(bytes);
        if (null == rd || null == rd.getType() || null == rd.getData()) {
            return;
        }
        LOGGER.debug("消费者接受消息,type=="
                + rd.getType() + rd.getData().getClass() + " 启动处理");
        try {
            EXECUTOR.execute(new ProcessObj(rd));
            LOGGER.debug("启动线程处理数据 Class = " + rd.getData().getClass());
        } catch (Exception e) {
            LOGGER.error("启动线程失败处理数据 Class = " + rd.getData().getClass(), e);
        }
    }


    /**
     * 处理酒店对象的内部类.
     */
    public static class ProcessObj extends Thread {
        /**
         * 日志类.
         */
        private static final Logger LOGGER =
                LoggerFactory.getLogger(ProcessObj.class);

        /**
         * 接受日志对象.
         */
        private ReceiverData receiverData;

        /**
         * 空参数构造.
         */
        public ProcessObj() {
            super();
        }

        /**
         * 有参数构造.
         *
         * @param receiverData 接受kafka参数封装的酒店对象实体.
         */
        public ProcessObj(final ReceiverData receiverData) {
            this.receiverData = receiverData;
        }

        /**
         * 线程run方法.
         */
        @Override
        public void run() {
            LOGGER.debug("线程==name"
                    + Thread.currentThread().getName() + "执行清洗操作");
            if (receiverData.getType() == 1) {
                LOGGER.debug("type=1，进入数据清洗");
                Hotelinfo hotelinfo = (Hotelinfo) receiverData.getData();
                try {
                    LOGGER.debug("标准化前数据 hotelInfo = "
                            + hotelinfo.toString());
                    Hotelinfo standedHotel =
                            HotelStandard.standardHotel(hotelinfo);
                    if (null != standedHotel) {
                        receiverData.setData(standedHotel);
                        LOGGER.debug("酒店数据标准化成功，标准化后数据hotelInfo="
                                + receiverData.getData().toString());
                    } else {
                        LOGGER.debug("酒店数据异常返回 null hotelInfo = "
                                + hotelinfo.toString());
                        receiverData.setData(null);
                        return;
                    }
                } catch (Exception e) {
                    LOGGER.error(receiverData.getData().getClass()
                            + "酒店数据标准化失败 酒店hotelinfo="
                            + hotelinfo.toString(), e);
                }
            }
            //保存hbase
            if (null != receiverData && null != receiverData.getData()) {
                HotelObjectDao.saveToHbase(receiverData);
                LOGGER.debug("保存hbase成功 Data="
                        + receiverData.getData().toString());
            }
        }
    }

}



