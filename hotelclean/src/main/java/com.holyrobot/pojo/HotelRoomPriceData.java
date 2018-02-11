package com.holyrobot.pojo;

import com.holyrobot.common.RobotObject;

import java.util.Date;

public class HotelRoomPriceData extends RobotObject {
    private String id;

    private String hotelid;

    private String roomid;

    private String productname;//产品名称

    private String price;

    private String date;

    private String availablenum;//剩余房间数

    private String isbooking;//是否可以预定

    private String appliyby;//供应商

    private String ishasbreakfast;//是否有早餐

    private String iswindow;//是否有窗

    private String iscancled;//是否可以取消

    private String iswifi;//是否有wifi

    private String paymethod;//付款方式

    private String isdomesticguest;//是否内宾

    private Date createdate;

    private String creator;

    private String creatorid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid == null ? null : hotelid.trim();
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid == null ? null : roomid.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getAvailablenum() {
        return availablenum;
    }

    public void setAvailablenum(String availablenum) {
        this.availablenum = availablenum == null ? null : availablenum.trim();
    }

    public String getIsbooking() {
        return isbooking;
    }

    public void setIsbooking(String isbooking) {
        this.isbooking = isbooking == null ? null : isbooking.trim();
    }

    public String getAppliyby() {
        return appliyby;
    }

    public void setAppliyby(String appliyby) {
        this.appliyby = appliyby == null ? null : appliyby.trim();
    }

    public String getIshasbreakfast() {
        return ishasbreakfast;
    }

    public void setIshasbreakfast(String ishasbreakfast) {
        this.ishasbreakfast = ishasbreakfast == null ? null : ishasbreakfast.trim();
    }

    public String getIswindow() {
        return iswindow;
    }

    public void setIswindow(String iswindow) {
        this.iswindow = iswindow == null ? null : iswindow.trim();
    }

    public String getIscancled() {
        return iscancled;
    }

    public void setIscancled(String iscancled) {
        this.iscancled = iscancled == null ? null : iscancled.trim();
    }

    public String getIswifi() {
        return iswifi;
    }

    public void setIswifi(String iswifi) {
        this.iswifi = iswifi == null ? null : iswifi.trim();
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod == null ? null : paymethod.trim();
    }

    public String getIsdomesticguest() {
        return isdomesticguest;
    }

    public void setIsdomesticguest(String isdomesticguest) {
        this.isdomesticguest = isdomesticguest == null ? null : isdomesticguest.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid == null ? null : creatorid.trim();
    }
}
