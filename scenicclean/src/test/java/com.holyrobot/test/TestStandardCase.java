package com.holyrobot.test;

import com.alibaba.fastjson.JSON;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Sceinfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuill on 2018/3/2.
 * 测试用例，创建酒店对象 发送到kafka
 * test
 */
public class TestStandardCase {

    private static String url = "http://192.168.0.230:8081/send";

    private static final Logger logger = LoggerFactory.getLogger(TestStandardCase.class);

    @Test
    public void test() {
        ReceiverData data = new ReceiverData();
        data.setType(5);
        data.setFlag(1);
        //生成酒店对象
        data.setData(sceinfo());
        TestCase.httpPostWithJson(JSON.toJSONString(data), url);
    }

    private Sceinfo sceinfo() {
        Sceinfo s = new Sceinfo();
        s.setId("00001c90-fde5-4fa9-ad41-8e9b8595de48");
        s.setUrlid("http://you.ctrip.com/sight/sanya61/65650.html");
        s.setName("海棠湾");
        s.setAddress("三亚市海棠区");
        s.setIntroduction("海棠湾少了城市的喧嚣与繁闹，多了份原生态的美丽与安宁。这里特别适合就想静静地呆在酒店度个假的人群，站在这里的沙滩远眺，还能看到蜈支洲岛。 海棠湾开发得比较晚，且离三亚市区较远，所以与亚龙湾、大东海相比，这里相对去的人比较少。 沙滩上的国际连锁的高端酒店品牌不计其数，包括万达希尔顿逸林、万丽、喜来登等，且酒店设施都比较新。 从海棠湾可以去到著名的潜水胜地“蜈支洲岛”，这里还有着珠江南田温泉等景区");
        s.setLatitude("18.321999776036");
        s.setLongitude("109.736834058262");
        s.setAdvicetime("建议0.5-1天");
        s.setOpentime("全天开放");
        s.setOtherinformation("免费开放");
        s.setGrade("92.0");
        s.setGradenum("570");
        s.setBeennum("1501");
        s.setWanttonum("6");
        s.setDatasource("ctrip");
        s.setCreatedate(new Date());
        s.setAdminarea("中国,海南");
        return s;
    }

    static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
