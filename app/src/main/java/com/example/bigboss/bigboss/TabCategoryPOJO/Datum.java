package com.example.bigboss.bigboss.TabCategoryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {



    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("videocat_name")
    @Expose
    private String videocatName;
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

    public String getVideocatName() {
        return videocatName;
    }

    public void setVideocatName(String videocatName) {
        this.videocatName = videocatName;
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
