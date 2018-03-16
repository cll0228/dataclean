package com.holyrobot.test;

import com.holyrobot.common.Hotelinfo;
import com.holyrobot.util.HotelStandard;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cuill on 2018/3/14.
 */
public class TestStandard {
    @Test
    public void test() throws Exception {

        HotelStandard standard = new HotelStandard();

        //生成酒店对象
        System.out.println(standard.standardHotel(HotelInfo1()));
        System.out.println(standard.standardHotel(HotelInfo2()));
        System.out.println(standard.standardHotel(HotelInfo3()));
        System.out.println(standard.standardHotel(HotelInfo4()));
        System.out.println(standard.standardHotel(HotelInfo5()));
        System.out.println(standard.standardHotel(HotelInfo6()));
        System.out.println(standard.standardHotel(HotelInfo7()));
        System.out.println(standard.standardHotel(HotelInfo8()));
        System.out.println(standard.standardHotel(HotelInfo9()));
        System.out.println(standard.standardHotel(HotelInfo10()));
        System.out.println(standard.standardHotel(HotelInfo11()));
        System.out.println(standard.standardHotel(HotelInfo12()));
        System.out.println(standard.standardHotel(HotelInfo13()));
        System.out.println(standard.standardHotel(HotelInfo14()));
        System.out.println(standard.standardHotel(HotelInfo15()));
        System.out.println(standard.standardHotel(HotelInfo16()));

    }

    /**
     * 生成酒店对象，名称包含 测试
     *
     * @return
     */
    private static Hotelinfo HotelInfo1() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三亚建设宾馆测试");
        info.setAddress("天涯区三亚天涯区三亚市市辖区建设街68号");
        info.setLongitude("109.507153");
        info.setLatitude("18.243549");
        info.setStar("途牛用户评定为经济型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 生成酒店对象，名称包含 test
     *
     * @return
     */
    private static Hotelinfo HotelInfo2() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三亚建设宾馆test");
        info.setAddress("天涯区三亚天涯区三亚市市辖区建设街68号");
        info.setLongitude("109.507153");
        info.setLatitude("18.243549");
        info.setStar("途牛用户评定为经济型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 生成酒店对象，名称包含 del
     *
     * @return
     */
    private static Hotelinfo HotelInfo3() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三亚建设宾馆del");
        info.setAddress("天涯区三亚天涯区三亚市市辖区建设街68号");
        info.setLongitude("109.507153");
        info.setLatitude("18.243549");
        info.setStar("途牛用户评定为经济型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 生成酒店对象，名称包含 名称包含特殊字符
     * 地址包含特殊字符
     * 经纬度 特殊符号 保留6位小数
     * 星级为null
     *
     * @return
     */
    private static Hotelinfo HotelInfo4() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 几星级
     *
     * @return
     */
    private static Hotelinfo HotelInfo5() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("二星级/经济型");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 经济型
     *
     * @return
     */
    private static Hotelinfo HotelInfo6() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("经济型（携程用户评定为1.5钻）");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 途牛用户评定为品质型酒店 ，替换高档
     *
     * @return
     */
    private static Hotelinfo HotelInfo7() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为品质型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 途牛用户评定为简约型酒店 ，替换经济
     *
     * @return
     */
    private static Hotelinfo HotelInfo8() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.2");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分 null
     *
     * @return
     */
    private static Hotelinfo HotelInfo9() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade(null);
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分 百分制
     *
     * @return
     */
    private static Hotelinfo HotelInfo10() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("85%");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分 7.6
     *
     * @return
     */
    private static Hotelinfo HotelInfo11() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("7.6");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分 4.3
     *
     * @return
     */
    private static Hotelinfo HotelInfo12() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("4.3");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分 -4.3
     *
     * @return
     */
    private static Hotelinfo HotelInfo13() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("-4.3");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }


    /**
     * 评分人数 null
     *
     * @return
     */
    private static Hotelinfo HotelInfo14() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("-4.3");
        info.setGradenum("");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 评分人数 124.5
     *
     * @return
     */
    private static Hotelinfo HotelInfo15() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("-4.3");
        info.setGradenum("121.5");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    /**
     * 来源
     *
     * @return
     */
    private static Hotelinfo HotelInfo16() {
        Hotelinfo info = new Hotelinfo();
        info.setId(UUID.randomUUID().toString());
        info.setUrlid(UUID.randomUUID().toString());
        info.setName("三[亚-建]设)宾馆(");
        info.setAddress("天涯区三亚天涯区[三亚](市市)-辖区建设街-68号");
        info.setLongitude("121【.453]15-}4[31");
        info.setLatitude("18【.】243[【54]-}93112");
        info.setStar("途牛用户评定为简约型酒店");
        info.setPrice("98");
        info.setDatasource("tuniu");
        info.setGrade("-4.3");
        info.setGradenum("124");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark(null);
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

    static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
