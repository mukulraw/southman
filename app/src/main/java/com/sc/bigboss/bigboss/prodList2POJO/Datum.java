package com.sc.bigboss.bigboss.prodList2POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sub_title")
    @Expose
    private String subTitle;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("whatsapp_order_now")
    @Expose
    private String whatsappOrderNow;
    @SerializedName("sku")
    @Expose
    private String sku;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getWhatsappOrderNow() {
        return whatsappOrderNow;
    }

    public void setWhatsappOrderNow(String whatsappOrderNow) {
        this.whatsappOrderNow = whatsappOrderNow;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
