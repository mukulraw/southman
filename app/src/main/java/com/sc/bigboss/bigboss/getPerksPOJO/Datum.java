package com.sc.bigboss.bigboss.getPerksPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("participations")
    @Expose
    private String participations;
    @SerializedName("winnings")
    @Expose
    private String winnings;
    @SerializedName("perks")
    @Expose
    private String perks;
    @SerializedName("cash_rewards")
    @Expose
    private String cashRewards;
    @SerializedName("created")
    @Expose
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getParticipations() {
        return participations;
    }

    public void setParticipations(String participations) {
        this.participations = participations;
    }

    public String getWinnings() {
        return winnings;
    }

    public void setWinnings(String winnings) {
        this.winnings = winnings;
    }

    public String getPerks() {
        return perks;
    }

    public void setPerks(String perks) {
        this.perks = perks;
    }

    public String getCashRewards() {
        return cashRewards;
    }

    public void setCashRewards(String cashRewards) {
        this.cashRewards = cashRewards;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
