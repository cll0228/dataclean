package com.holyrobot.datastandard;

/**
 * 景点名称字段标准化
 */
public class SceientNameFieldStandarder implements ScenicDataFieldStandard {
    @Override
    public String standardData(String originalData) {
        originalData = originalData ==null ? "":originalData;
        String[] words = {"专车", "自驾车", "游艇租赁", "租车", "俱乐部", "体验", "餐厅", "咖啡", "远程教育", "演艺中心",
                "飞机中心", "户外", "酒店", "游艇会", "运动中心", "游船", "摄影", "夜游三亚湾", "垂钓", "课程",
                "高尔夫吧", "号场", "送机", "西排"};
        String temp = originalData;
        for(String w : words){
            temp = temp.replace(w,"");
        }
        return temp;
    }
}
