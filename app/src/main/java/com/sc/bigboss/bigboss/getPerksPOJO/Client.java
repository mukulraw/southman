package com.sc.bigboss.bigboss.getPerksPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("minimun_bill")
    @Expose
    private String minimunBill;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("asset")
    @Expose
    private String asset;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getMinimunBill() {
        return minimunBill;
    }

    public void setMinimunBill(String minimunBill) {
        this.minimunBill = minimunBill;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}
