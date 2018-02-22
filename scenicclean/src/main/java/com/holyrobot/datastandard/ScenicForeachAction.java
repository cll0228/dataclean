package com.holyrobot.datastandard;

import com.holyrobot.common.*;
import org.apache.kafka.streams.kstream.ForeachAction;

public class ScenicForeachAction implements ForeachAction<String,byte[]> {
    static long count = 0;
    @Override
    public void apply(String s, byte[] bytes) {
        ReceiverData rd = (ReceiverData) Object2Array.byteArrayToObject(bytes);  //解析
        ScenicData scenicData = ReciverDataParser.parse(rd);
        ScenicDataStandard standard = new ScenicDataStandard();
        standard.standardData(scenicData);
    }
}
