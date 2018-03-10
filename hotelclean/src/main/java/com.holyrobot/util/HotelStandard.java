package com.holyrobot.util;


import com.holyrobot.common.Hotelinfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 标准化酒店数据
 */
public class HotelStandard {

    private static final Logger logger = LoggerFactory.getLogger(HotelStandard.class);

    public static Hotelinfo standardHotel(Object obj) throws Exception {
        Hotelinfo hotel = (Hotelinfo) obj;

        //酒店名称 del ,test ,测试
        for (String filter : FilterName.HOTEL_NAME_FILTER) {
            if (StringUtils.isBlank(hotel.getName()) || hotel.getName().contains(filter)) {
                return null;
            }
        }

        //定制和名称特殊符号
        hotel.setName(StandardUtil.replaceE2C(hotel.getName()));
        hotel.setAddress(StandardUtil.replaceE2C(hotel.getAddress()));


        if (StringUtils.isNotBlank(hotel.getLatitude()) && StringUtils.isNotBlank(hotel.getLongitude())) {
            try {
                //经纬度
                hotel.setLatitude(StandardUtil.save2dec(Double.valueOf(StandardUtil.delSpechar(hotel.getLatitude()))).toString());
                hotel.setLongitude(StandardUtil.save2dec(Double.valueOf(StandardUtil.delSpechar(hotel.getLongitude()))).toString());
            } catch (Exception e) {
                hotel.setLatitude("-999");
                hotel.setLongitude("-999");
                logger.error("经纬度标准化失败！，Latitude" + hotel.getLatitude() + " Longitude" + hotel.getLongitude(), e);
            }
        }
        //星级
        hotel.setStar(StandardUtil.preStar(hotel.getStar()));

        //评分
        hotel.setGrade(StandardUtil.preGrade(hotel.getGrade()));

        //评分人数
        hotel.setGradenum(StandardUtil.preGradeNum(hotel.getGradenum()));

        //数据源
        hotel.setDatasource(StandardUtil.captureName(hotel.getDatasource()));
        return hotel;
    }

    public static void main(String[] args) throws Exception {
        Hotelinfo hotelinfo = new Hotelinfo();
        hotelinfo.setId("b10a44f6-d12f-492e-9b5e-6bd7fe8c 0bc7");
        hotelinfo.setUrl(null);
        hotelinfo.setName("三亚凯丰大酒店");
        hotelinfo.setAddress("凤凰路159号，近火车站/凤凰机场/三亚湾红树林。 （ 三亚湾）");
        hotelinfo.setLongitude("109.49142168512");
        hotelinfo.setLatitude("18.293092351768");
        hotelinfo.setStar("高档型（携程用户评定为3.5钻）");
        hotelinfo.setPrice("338");
        hotelinfo.setDatasource("ctrip");
        hotelinfo.setGrade("4.1");
        hotelinfo.setGradenum("479");
        hotelinfo.setBeennum("");
        hotelinfo.setCreatedate(new Date());
        hotelinfo.setIntroduction("2010年开业  2015年装修  388间房    联系方式　　 　　三亚凯丰大酒店毗邻三亚海航学院，交通便利。 　　三亚凯丰大酒店是一家集客房、餐饮、康乐、会议、商务、购物于一体的豪华酒店。我们致力于满足客户度假和商务需求，突出环保、休闲、时尚、健康以 及舒适的风格。酒店拥有各类客房，房间视野宽阔，碧海蓝天一览无余，内设有宽带上网、卫星电视、国际国内直拨电话、独立淋浴室、四楼花园式游泳池等设施。 　　三亚凯丰大酒店拥有中餐、西餐、咖啡厅、茶艺等各类餐饮场 所，无论您在任何时候来到酒店，都能得到周到的服务，和舒适的享受。酒店还可容纳600余位宾客的多功能会议厅，是举办各种会议的理想场所。'");

        Hotelinfo hotelinfo1 = standardHotel(hotelinfo);
        System.out.println(hotelinfo1.toString());
    }
}
