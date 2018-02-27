package com.holyrobot.common;

import java.util.Date;

public class HotelRoomPriceData extends RobotObject {
    public String id;

    public String hotelId;

    public String roomId;

    public String productName;//产品名称

    public String price;

    public String date;

    public String availableNum;//剩余房间数

    public String isbooking;//是否可以预定

    public String appliyBy;//供应商

    public String isHasBreakfast;//是否有早餐

    public String isWindow;//是否有窗

    public String isCancled;//是否可以取消

    public String isWifi;//是否有wifi

    public String paymethod;//付款方式

    public String isDomesticGuest;//是否内宾

    public Date createDate;

    public String creator;

    public String creatorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(String availableNum) {
        this.availableNum = availableNum;
    }

    public String getIsbooking() {
        return isbooking;
    }

    public void setIsbooking(String isbooking) {
        this.isbooking = isbooking;
    }

    public String getAppliyBy() {
        return appliyBy;
    }

    public void setAppliyBy(String appliyBy) {
        this.appliyBy = appliyBy;
    }

    public String getIsHasBreakfast() {
        return isHasBreakfast;
    }

    public void setIsHasBreakfast(String isHasBreakfast) {
        this.isHasBreakfast = isHasBreakfast;
    }

    public String getIsWindow() {
        return isWindow;
    }

    public void setIsWindow(String isWindow) {
        this.isWindow = isWindow;
    }

    public String getIsCancled() {
        return isCancled;
    }

    public void setIsCancled(String isCancled) {
        this.isCancled = isCancled;
    }

    public String getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(String isWifi) {
        this.isWifi = isWifi;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getIsDomesticGuest() {
        return isDomesticGuest;
    }

    public void setIsDomesticGuest(String isDomesticGuest) {
        this.isDomesticGuest = isDomesticGuest;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
