package com.holyrobot.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

/**
 * 配置管理器
 */
public class ConfigManager {
    static Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    /**
     * 获得值
     *
     * @param key 键
     * @return 值
     */
    public String getConfig(String key) {
            try {
                Properties props = new Properties();
                URL url = ConfigManager.class.getClassLoader().getResource("application.properties");
                FileInputStream fis = new FileInputStream(url.getPath());
                props.load(fis);
                Set<Object> ks = props.keySet();
                for(Object kk: ks){
                    logger.info("键值：" + kk.toString());
                }
                return props.getProperty(key);
            }catch (Exception ex){
                logger.error(ex.getMessage());
            }
            return "";

    }
}
