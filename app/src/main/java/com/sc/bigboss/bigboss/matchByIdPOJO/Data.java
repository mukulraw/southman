package com.sc.bigboss.bigboss.matchByIdPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("wear")
    @Expose
    private String wear;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("sub_title")
    @Expose
    private String subTitle;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("negotiable")
    @Expose
    private String negotiable;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("whatsapp_order_now")
    @Expose
    private String whatsappOrderNow;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("thumb")
    @Expose
    private List<String> thumb = null;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWear() {
        return wear;
    }

    public void setWear(String wear) {
        this.wear = wear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getNegotiable() {
        return negotiable;
    }

    public void setNegotiable(String negotiable) {
        this.negotiable = negotiable;
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

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }
}
