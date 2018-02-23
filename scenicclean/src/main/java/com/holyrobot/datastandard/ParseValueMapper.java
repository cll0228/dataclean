package com.holyrobot.datastandard;

import com.holyrobot.common.Object2Array;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Sceinfo;
import org.apache.kafka.streams.kstream.ValueMapper;

/**
 * kStream算子解析景点对象
 */
public class ParseValueMapper implements ValueMapper<byte[],Sceinfo>{
    @Override
    public Sceinfo apply(byte[] bytes) {
        ReceiverData rd = (ReceiverData) Object2Array.byteArrayToObject(bytes);  //解析
        Sceinfo scenicData = ReciverDataParser.parse(rd);
        return scenicData;
    }
}
