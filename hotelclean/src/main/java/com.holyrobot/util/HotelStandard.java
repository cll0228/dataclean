package com.holyrobot.util;


import com.holyrobot.common.Hotelinfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标准化酒店数据
 */
public class HotelStandard {

    private static final Logger logger = LoggerFactory.getLogger(HotelStandard.class);

    public static Hotelinfo standardHotel(Object obj) throws Exception {
        Hotelinfo hotel = (Hotelinfo) obj;

        //酒店名称 del ,test ,测试
        String[] split = FilterName.HOTEL_NAME_FILTER.split(",");
        for (String s : split) {
            if (hotel.getName().contains(s)) {
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
                logger.error("经纬度标准化失败！，Latitude" + hotel.getLatitude() + " Longitude" + hotel.getLongitude(), e);
            }
        }
        //星级
        hotel.setStar(StandardUtil.preStar(hotel.getStar()));

        //评分
        hotel.setGrade(StandardUtil.preGrade(hotel.getGrade()));

        //评分人数
        hotel.setGradenum(null == hotel.getGradenum() ? "0" : hotel.getGradenum());

        //数据源
        hotel.setDatasource(StandardUtil.captureName(hotel.getDatasource()));
        return hotel;
    }
}
