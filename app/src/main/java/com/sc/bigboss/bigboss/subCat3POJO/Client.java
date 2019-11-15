package com.sc.bigboss.bigboss.subCat3POJO;

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
}
