package com.example.bigboss.bigboss.VideoGenralPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("small_desc")
    @Expose
    private String smallDesc;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("whats_order_now")
    @Expose
    private String whatsOrderNow;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        this.smallDesc = smallDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWhatsOrderNow() {
        return whatsOrderNow;
    }

    public void setWhatsOrderNow(String whatsOrderNow) {
        this.whatsOrderNow = whatsOrderNow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
