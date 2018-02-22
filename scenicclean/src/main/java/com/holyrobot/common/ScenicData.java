package com.holyrobot.common;

import java.util.Date;
import java.util.List;

/**
 * 景点数据
 */
public class ScenicData extends RobotObject {
    public String id;

    public String urlid;

    public String name;

    public String address;

    public String longitude;

    public String latitude;

    public String starlevel;

    public String type;

    public String advicetime;

    public String opentime;

    public String servicecommitment;

    public String referprice;

    public String grade;

    public String gradenum;

    public String beennum;

    public String wanttonum;

    public String datasource;

    public Date createdate;

    public String creator;

    public String creatorid;

    public String introduction;

    public String otherinformation;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUrlid() {
        return urlid;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid == null ? null : urlid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getStarlevel() {
        return starlevel;
    }

    public void setStarlevel(String starlevel) {
        this.starlevel = starlevel == null ? null : starlevel.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAdvicetime() {
        return advicetime;
    }

    public void setAdvicetime(String advicetime) {
        this.advicetime = advicetime == null ? null : advicetime.trim();
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime == null ? null : opentime.trim();
    }

    public String getServicecommitment() {
        return servicecommitment;
    }

    public void setServicecommitment(String servicecommitment) {
        this.servicecommitment = servicecommitment == null ? null : servicecommitment.trim();
    }

    public String getReferprice() {
        return referprice;
    }

    public void setReferprice(String referprice) {
        this.referprice = referprice == null ? null : referprice.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getGradenum() {
        return gradenum;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOtherinformation() {
        return otherinformation;
    }

    public void setOtherinformation(String otherinformation) {
        this.otherinformation = otherinformation;
    }


    public void setGradenum(String gradenum) {
        this.gradenum = gradenum == null ? null : gradenum.trim();
    }

    public String getBeennum() {
        return beennum;
    }

    public void setBeennum(String beennum) {
        this.beennum = beennum == null ? null : beennum.trim();
    }

    public String getWanttonum() {
        return wanttonum;
    }

    public void setWanttonum(String wanttonum) {
        this.wanttonum = wanttonum == null ? null : wanttonum.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
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

    private List<ScenicTicketData> ticketDataList;

    public List<ScenicTicketData> getPriceDataList() {
        return ticketDataList;
    }

    public void ScenicTicketData(List<ScenicTicketData> ticketDataList) {
        this.ticketDataList = ticketDataList;
    }
}
