package com.holyrobot.datastandard;

import com.holyrobot.common.Sceinfo;
import org.apache.kafka.streams.kstream.ValueMapper;

public class StandardMapper implements ValueMapper<Sceinfo,Sceinfo> {
    @Override
    public Sceinfo apply(Sceinfo scenicData) {
        ScenicDataStandard dataStandard = new ScenicDataStandard();
        Sceinfo standarded = dataStandard.standardData(scenicData);
        return standarded;
    }
}
