package com.holyrobot.datastandard;

/**
 * 景点数据字段标准化
 */
public interface ScenicDataFieldStandard {
    /**
     * 标准化数据
     * @param originalData
     * @return
     */
    String standardData(String originalData);
}
