package com.holyrobot.common;

import java.util.Date;

/**
 * 景点数据
 */
public class ScenicPriceData extends RobotObject {
    public String id;

    public String scenicid;

    public String urlid;

    public String pricetype;

    public String tickettype;

    public String priceitem;

    public String publicingprice;

    public String salecondition;

    public String saleprice;

    public String discountinfo;

    public Date createdate;

    public String creator;

    public String creatorid;

    public String remark;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getScenicid() {
        return scenicid;
    }

    public void setScenicid(String scenicid) {
        this.scenicid = scenicid == null ? null : scenicid.trim();
    }

    public String getUrlid() {
        return urlid;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid == null ? null : urlid.trim();
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype == null ? null : pricetype.trim();
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype == null ? null : tickettype.trim();
    }

    public String getPriceitem() {
        return priceitem;
    }

    public void setPriceitem(String priceitem) {
        this.priceitem = priceitem == null ? null : priceitem.trim();
    }

    public String getpublicingprice() {
        return publicingprice;
    }

    public void setpublicingprice(String publicingprice) {
        this.publicingprice = publicingprice == null ? null : publicingprice.trim();
    }

    public String getSalecondition() {
        return salecondition;
    }

    public void setSalecondition(String salecondition) {
        this.salecondition = salecondition == null ? null : salecondition.trim();
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice == null ? null : saleprice.trim();
    }

    public String getDiscountinfo() {
        return discountinfo;
    }

    public void setDiscountinfo(String discountinfo) {
        this.discountinfo = discountinfo == null ? null : discountinfo.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
