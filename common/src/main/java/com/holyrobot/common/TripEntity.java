package com.holyrobot.common;

public class TripEntity {
    private Integer id;

    private String rouid;

    private String departure;

    private String destination;

    private String price;

    private String time;

    private String spot;

    private String hotel;

    private String datasource;

    private String urlid;
    
    

    @Override
	public String toString() {
		return "TripEntity [id=" + id  + ", hotel=" + hotel +  ", rouid=" + rouid + ", departure=" + departure + ", destination=" + destination
				+ ", price=" + price + ", time=" + time + ", spot=" + spot + ", datasource="
				+ datasource + ", urlid=" + urlid + "]";
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouid() {
        return rouid;
    }

    public void setRouid(String rouid) {
        this.rouid = rouid == null ? null : rouid.trim();
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure == null ? null : departure.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot == null ? null : spot.trim();
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel == null ? null : hotel.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    public String getUrlid() {
        return urlid;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid;
    }
}