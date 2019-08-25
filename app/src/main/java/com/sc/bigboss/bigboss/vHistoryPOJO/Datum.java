package com.sc.bigboss.bigboss.vHistoryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
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
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMode() {
        return mode;
    }

    public String getStatus() {
        return status;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
