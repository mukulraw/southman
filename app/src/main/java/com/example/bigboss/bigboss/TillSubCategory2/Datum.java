package com.example.bigboss.bigboss.TillSubCategory2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subcat_id")
    @Expose
    private String subcatId;
    @SerializedName("subcat_name")
    @Expose
    private String subcatName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(String subcatId) {
        this.subcatId = subcatId;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
