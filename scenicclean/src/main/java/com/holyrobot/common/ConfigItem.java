package com.holyrobot.common;

/**
 * 配置项
 */
public interface ConfigItem {
    String KAFKA_SERVER = "kafka.server";
    String CONFIG_PATH = "mapping.path";
    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    String APP_ENV = "app.env";
    //topic配置
    String SCENIC_INPUT_TOPIC = "scenic.input";
    String SCENIC_OUTPUT_TOPIC = "scenic.output";
    //es 配置
    String ES_IP = "es.ip";
    String ES_PORT = "es.port";
    String ES_CLUSTER = "es.clustername";
}
