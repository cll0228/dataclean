package com.holyrobot.common;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json
 */
public class JsonCommon {
    static Logger logger = LoggerFactory.getLogger(JsonCommon.class);
    public static String prepareData(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
