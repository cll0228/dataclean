package com.holyrobot.datastandard;

import com.holyrobot.dao.RobotObjectDao;
import org.apache.kafka.streams.kstream.ValueMapper;

/**
 * 保存到hbase的kStream mapper
 */
public class SaveHbaseMapper implements ValueMapper<ScenicData,Object>{
    @Override
    public Object apply(ScenicData scenicData) {
        try {
            RobotObjectDao.insertHbase(scenicData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
