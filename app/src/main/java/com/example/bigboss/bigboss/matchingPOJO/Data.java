package com.example.bigboss.bigboss.matchingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("top_wear_id")
    @Expose
    private String topWearId;
    @SerializedName("bottom_wear_id")
    @Expose
    private String bottomWearId;
    @SerializedName("top_wear")
    @Expose
    private List<List<TopWear>> topWear = null;
    @SerializedName("bottom_wear")
    @Expose
    private List<List<BottomWear>> bottomWear = null;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopWearId() {
        return topWearId;
    }

    public void setTopWearId(String topWearId) {
        this.topWearId = topWearId;
    }

    public String getBottomWearId() {
        return bottomWearId;
    }

    public void setBottomWearId(String bottomWearId) {
        this.bottomWearId = bottomWearId;
    }

    public List<List<TopWear>> getTopWear() {
        return topWear;
    }

    public void setTopWear(List<List<TopWear>> topWear) {
        this.topWear = topWear;
    }

    public List<List<BottomWear>> getBottomWear() {
        return bottomWear;
    }

    public void setBottomWear(List<List<BottomWear>> bottomWear) {
        this.bottomWear = bottomWear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
