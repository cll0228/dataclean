package com.holyrobot.pojo;

import com.holyrobot.common.RobotObject;

import java.io.Serializable;
import java.util.Date;

public class HotelDetail extends RobotObject implements Serializable {
    public String id;

    public String url;

    public String name;

    public String address;

    public String longitude;

    public String latitude;

    public String star;

    public String price;

    public String dataSource;

    public String grade;

    public String gradeNum;

    public String beenNum;

    public String whantTo;

    public Date createDate;

    public String creator;

    public String creatorId;

    public String remark;

    public String introduction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(String gradeNum) {
        this.gradeNum = gradeNum;
    }

    public String getBeenNum() {
        return beenNum;
    }

    public void setBeenNum(String beenNum) {
        this.beenNum = beenNum;
    }

    public String getWhantTo() {
        return whantTo;
    }

    public void setWhantTo(String whantTo) {
        this.whantTo = whantTo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "HotelDetail{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", star='" + star + '\'' +
                ", price='" + price + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", grade='" + grade + '\'' +
                ", gradeNum='" + gradeNum + '\'' +
                ", beenNum='" + beenNum + '\'' +
                ", whantTo='" + whantTo + '\'' +
                ", createDate=" + createDate +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", remark='" + remark + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
