package com.sc.bigboss.bigboss.getPerksPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("txn")
    @Expose
    private String txn;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("red")
    @Expose
    private String red;
    @SerializedName("blue")
    @Expose
    private String blue;
    @SerializedName("mode")
    @Expose
    private String mode;
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

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBlue() {
        return blue;
    }

    public String getRed() {
        return red;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
