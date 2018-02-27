package com.holyrobot.common;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * ES文档管理操作API
 */
public class ESDocumentManager {
    static String CLUSTER_NAME = "elasticsearch_csi";
    static String HOST_IP = "node5";
    static int PORT = 9300;
    private volatile static TransportClient client;
    static IndicesAdminClient indexAdmin;
    static {
        try {
            TransportClient cc = getClient();
            indexAdmin = cc.admin().indices();
        }catch (Exception ex){

        }
    }
    public static TransportClient getClient() throws Exception{
        if(client == null){
            synchronized (TransportClient.class){
                Settings settings = Settings.builder()
                        .put("cluster.name",CLUSTER_NAME)
                        .build();
                client = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName(HOST_IP),PORT));
            }
        }
        return client;
    }

    public static void insertDoc(String indexName,String type,String id,Object object) throws Exception{
        Map<String,Object> doc2 = new HashMap<String,Object>();
        Class cls = object.getClass();
        Field[] fs = cls.getDeclaredFields();
        for(Field f : fs){
            String name = f.getName();
            f.setAccessible(true);
            String value = f.get(object) == null?"":f.get(object).toString();
            doc2.put(name,value);
        }
        IndexResponse response = client.prepareIndex(indexName,type,id)
                .setSource(doc2)
                .get();
    }
}
