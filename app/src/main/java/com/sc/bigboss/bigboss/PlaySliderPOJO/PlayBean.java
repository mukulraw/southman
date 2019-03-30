package com.sc.bigboss.bigboss.PlaySliderPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayBean {


    @SerializedName("product_info")
    @Expose
    private List<ProductInfo> productInfo = null;
    @SerializedName("thumb")
    @Expose
    private List<List<String>> thumb = null;

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

    public List<List<String>> getThumb() {
        return thumb;
    }

    public void setThumb(List<List<String>> thumb) {
        this.thumb = thumb;
    }

}
