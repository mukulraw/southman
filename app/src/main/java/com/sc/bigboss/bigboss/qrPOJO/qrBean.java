package com.sc.bigboss.bigboss.qrPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class qrBean {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("voucher")
    @Expose
    private Voucher voucher;
    @SerializedName("redeem")
    @Expose
    private Redeem redeem;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Redeem getRedeem() {
        return redeem;
    }

    public void setRedeem(Redeem redeem) {
        this.redeem = redeem;
    }
}
