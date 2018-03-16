package com.holyrobot.test;

import com.holyrobot.common.Hotelinfo;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.start.HotelConumerStartor;
import com.holyrobot.util.HotelStandard;
import com.holyrobot.util.StandardUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by cuill on 2018/3/14.
 */
public class TestStandard {

    private ConsumerRecord<String, byte[]> record;

    @Test
    public void test() throws Exception {

        HotelStandard standard = new HotelStandard();
        //生成酒店对象
        Hotelinfo hotelinfo = standard.standardHotel(HotelInfo1());
        //断言预期值为null
        Assert.assertEquals(null, hotelinfo);
        Hotelinfo hotelinfo1 = standard.standardHotel(HotelInfo2());
        Assert.assertEquals(null, hotelinfo1);
        Hotelinfo hotelinfo2 = standard.standardHotel(HotelInfo3());
        Assert.assertEquals(null, hotelinfo2);

        Hotelinfo returnInfo1 = standard.standardHotel(HotelInfo4());
        Hotelinfo info = new Hotelinfo();
        info.setId(returnInfo1.getId());
        info.setUrlid(returnInfo1.getUrlid());
        info.setAdminarea("中国,海南");
        info.setName("三【亚-建】设）宾馆（");
        info.setAddress("天涯区三亚天涯区【三亚】（市市）-辖区建设街-68号");
        info.setLongitude("121.453154");
        info.setLatitude("18.243549");
        info.setStar("其它");
        info.setPrice("98");
        info.setDatasource("Tuniu");
        info.setGrade("84");
        info.setGradenum("10");
        info.setBeennum(null);
        info.setWhantto(null);
        info.setCreatedate(new Date());
        info.setCreator("tyl");
        info.setCreatorid("tyl13564205515");
        info.setRemark("测试用例数据");
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        Assert.assertEquals(info.toString(), returnInfo1.toString());

        Hotelinfo returnInfo2 = standard.standardHotel(HotelInfo5());
        info.setStar("二星级");
        info.setId(returnInfo2.getId());
        info.setUrlid(returnInfo2.getUrlid());
        Assert.assertEquals(info.toString(), returnInfo2.toString());

        Hotelinfo returnInfo3 = standard.standardHotel(HotelInfo6());
        info.setStar("经济型");
        info.setId(returnInfo3.getId());
        info.setUrlid(returnInfo3.getUrlid());
        Assert.assertEquals(info.toString(), returnInfo3.toString());

        Hotelinfo returnInfo4 = standard.standardHotel(HotelInfo7());
        info.setStar("高档型");
        info.setId(returnInfo4.getId());
        info.setUrlid(returnInfo4.getUrlid());
        Assert.assertEquals(info.toString(), returnInfo4.toString());

        Hotelinfo returnInfo5 = standard.standardHotel(HotelInfo8());
        info.setStar("经济型");
        info.setId(returnInfo5.getId());
        info.setUrlid(returnInfo5.getUrlid());
        Assert.assertEquals(info.toString(), returnInfo5.toString());


        Hotelinfo returnInfo6 = standard.standardHotel(HotelInfo9());
        info.setId(returnInfo6.getId());
        info.setUrlid(returnInfo6.getUrlid());
        info.setGrade("0");
        Assert.assertEquals(info.toString(), returnInfo6.toString());

        Hotelinfo returnInfo7 = standard.standardHotel(HotelInfo10());
        info.setId(returnInfo7.getId());
        info.setUrlid(returnInfo7.getUrlid());
        info.setGrade("85");
        Assert.assertEquals(info.toString(), returnInfo7.toString());

        Hotelinfo returnInfo8 = standard.standardHotel(HotelInfo11());
        info.setId(returnInfo8.getId());
        info.setUrlid(returnInfo8.getUrlid());
        info.setGrade("76");
        Assert.assertEquals(info.toString(), returnInfo8.toString());


        Hotelinfo returnInfo9 = standard.standardHotel(HotelInfo12());
        info.setId(returnInfo9.getId());
        info.setUrlid(returnInfo9.getUrlid());
        info.setGrade("86");
        Assert.assertEquals(info.toString(), returnInfo9.toString());


        Hotelinfo returnInfo10 = standard.standardHotel(HotelInfo13());
        info.setId(returnInfo10.getId());
        info.setUrlid(returnInfo10.getUrlid());
        info.setGrade("0");
        Assert.assertEquals(info.toString(), returnInfo10.toString());


        Hotelinfo returnInfo11 = standard.standardHotel(HotelInfo14());
        info.setId(returnInfo11.getId());
        info.setUrlid(returnInfo11.getUrlid());
        info.setGradenum("0");
        Assert.assertEquals(info.toString(), returnInfo11.toString());

        Hotelinfo returnInfo12 = standard.standardHotel(HotelInfo15());
        info.setId(returnInfo12.getId());
        info.setUrlid(returnInfo12.getUrlid());
        info.setGradenum("0");
        Assert.assertEquals(info.toString(), returnInfo12.toString());

        Hotelinfo returnInfo13 = standard.standardHotel(HotelInfo16());
        info.setId(returnInfo13.getId());
        info.setUrlid(returnInfo13.getUrlid());
        info.setGradenum("124");
        info.setDatasource("Tuniu");
        Assert.assertEquals(info.toString(), returnInfo13.toString());
    }

    @Before
    public void testKafkaConsumerBefore() {
        record = Mockito.mock(ConsumerRecord.class);
        ReceiverData receiverData = new ReceiverData();

        receiverData.setFlag(1);
        receiverData.setType(1);
        receiverData.setData(HotelInfo8());
        when(record.value()).thenReturn(StandardUtil.objectToByteArray(receiverData));
    }

    @Test
    public void testKafkaConsumer() {
        HotelConumerStartor.execBytes(record.value());
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
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
        info.setAdminarea("中国,海南");
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
        info.setRemark("测试用例数据");
        info.setIntroduction("上海佳和上居酒店位于上海市浦东新区妙境路（川环南路路口），地处川沙中心地段，地理位置优越，交通便利，步行约5分钟可至绿地购物广场及家乐福。");
        return info;
    }

}
