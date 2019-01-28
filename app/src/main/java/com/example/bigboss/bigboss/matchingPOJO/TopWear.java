package com.example.bigboss.bigboss.matchingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopWear {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("buyer_name")
    @Expose
    private Object buyerName;
    @SerializedName("phone_number")
    @Expose
    private Object phoneNumber;
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
    @SerializedName("negotiable")
    @Expose
    private String negotiable;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("whatsapp_order_now")
    @Expose
    private Object whatsappOrderNow;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("product_detail")
    @Expose
    private Object productDetail;
    @SerializedName("created")
    @Expose
    private String created;

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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Object getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(Object buyerName) {
        this.buyerName = buyerName;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
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

    public Object getWhatsappOrderNow() {
        return whatsappOrderNow;
    }

    public void setWhatsappOrderNow(Object whatsappOrderNow) {
        this.whatsappOrderNow = whatsappOrderNow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(Object productDetail) {
        this.productDetail = productDetail;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
