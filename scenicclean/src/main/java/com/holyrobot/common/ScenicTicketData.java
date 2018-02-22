package com.holyrobot.common;

import java.io.Serializable;
import java.util.List;

/**
 * 景区门票
 */
public class ScenicTicketData extends RobotObject{
    public String type;
    private List<ScenicPriceData> priceData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ScenicPriceData> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<ScenicPriceData> priceData) {
        this.priceData = priceData;
    }
}
