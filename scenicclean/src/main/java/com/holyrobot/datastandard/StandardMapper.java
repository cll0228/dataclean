package com.holyrobot.datastandard;

import org.apache.kafka.streams.kstream.ValueMapper;

public class StandardMapper implements ValueMapper<ScenicData,ScenicData> {
    @Override
    public ScenicData apply(ScenicData scenicData) {
        ScenicDataStandard dataStandard = new ScenicDataStandard();
        ScenicData standarded = dataStandard.standardData(scenicData);
        return standarded;
    }
}
