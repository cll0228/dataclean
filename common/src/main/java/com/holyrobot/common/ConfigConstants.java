package com.holyrobot.common;


public interface ConfigConstants {
    /**
     * es相关常量
     */
    String ES_CLUSTER_NAME = "es.clusterName";
    String ES_PING_TIMEOUT = "es.pingTimeout";
    String ES_NODES_SAMPLER_INTERVAL = "es.nodesSamplerInterval";
    String ES_IP = "es.ip";
    String ES_PORT = "es.port";

    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorun";
    String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT = "hbase.port";

    //hbase 表名
    String FLIGHT_INPUT_INFO = "HolyRobot:FlightInputInfo";

}
