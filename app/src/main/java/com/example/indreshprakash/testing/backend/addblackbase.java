package com.example.indreshprakash.testing.backend;

public class addblackbase {

    String addid;
    Double latitude;
    Double longitude;

    public addblackbase(){

    }

    public addblackbase(String addid, Double latitude, Double longitude) {
        this.addid = addid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddid() {
        return addid;
    }

    public void setAddid(String addid) {
        this.addid = addid;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

