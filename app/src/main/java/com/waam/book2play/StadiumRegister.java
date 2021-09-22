package com.waam.book2play;

public class StadiumRegister {
    private String sEmail;
    private String sPhone;
    private String sName;
    private String sLocation;
    private String sOT;
    private String sCT;
    private String sPrice;
    private String sImageURL;

    public StadiumRegister() {
    }

    public StadiumRegister(String sEmail, String sPhone, String sName, String sLocation, String sOT, String sCT, String sPrice, String sImageURL) {
        this.sEmail = sEmail;
        this.sPhone = sPhone;
        this.sName = sName;
        this.sLocation = sLocation;
        this.sOT = sOT;
        this.sCT = sCT;
        this.sPrice = sPrice;
        this.sImageURL = sImageURL;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsLocation() {
        return sLocation;
    }

    public void setsLocation(String sLocation) {
        this.sLocation = sLocation;
    }

    public String getsOT() {
        return sOT;
    }

    public void setsOT(String sOT) {
        this.sOT = sOT;
    }

    public String getsCT() {
        return sCT;
    }

    public void setsCT(String sCT) {
        this.sCT = sCT;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getsImageURL() {
        return sImageURL;
    }

    public void setsImageURL(String sImageURL) {
        this.sImageURL = sImageURL;
    }
}
