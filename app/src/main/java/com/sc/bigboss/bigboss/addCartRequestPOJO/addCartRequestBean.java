package com.sc.bigboss.bigboss.addCartRequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sc.bigboss.bigboss.vouchersPOJO.Benefit;

import java.util.List;

public class addCartRequestBean {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("benefits")
    @Expose
    private List<Benefit> benefits = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }
}
