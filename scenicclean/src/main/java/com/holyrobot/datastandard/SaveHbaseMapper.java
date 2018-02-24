package com.holyrobot.datastandard;

import com.holyrobot.common.Sceinfo;
import com.holyrobot.dao.RobotObjectDao;
import org.apache.kafka.streams.kstream.ValueMapper;

/**
 * 保存到hbase的kStream mapper
 */
public class SaveHbaseMapper implements ValueMapper<Sceinfo,Object>{
    @Override
    public Object apply(Sceinfo scenicData) {
        try {
//            RobotObjectDao.insertHbase(scenicData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
