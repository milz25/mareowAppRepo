package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerFeedBack {
    @SerializedName("efficiency")
    @Expose
    private String efficiency;
    @SerializedName("inactive")
    @Expose
    private String inactive;
    @SerializedName("queryResponse")
    @Expose
    private String queryResponse;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;
    @SerializedName("workAttitude")
    @Expose
    private String workAttitude;
    @SerializedName("workCommitment")
    @Expose
    private String workCommitment;
    @SerializedName("partyId")
    @Expose
    private String partyId;
    @SerializedName("workOrderId")
    @Expose
    private String workOrderId;

    public String getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public String getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(String queryResponse) {
        this.queryResponse = queryResponse;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getWorkAttitude() {
        return workAttitude;
    }

    public void setWorkAttitude(String workAttitude) {
        this.workAttitude = workAttitude;
    }

    public String getWorkCommitment() {
        return workCommitment;
    }

    public void setWorkCommitment(String workCommitment) {
        this.workCommitment = workCommitment;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }
}
