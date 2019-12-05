package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MachineReviewDTO implements Serializable {
    @SerializedName("condition")
    @Expose
    private int condition;
    @SerializedName("efficiency")
    @Expose
    private int efficiency;
    @SerializedName("fuelEfficiency")
    @Expose
    private int fuelEfficiency;
    @SerializedName("maintained")
    @Expose
    private int maintained;
    @SerializedName("queryResponse")
    @Expose
    private int queryResponse;
    @SerializedName("totalRating")
    @Expose
    private float totalRating;
    @SerializedName("workAttitude")
    @Expose
    private int workAttitude;
    @SerializedName("workCommitment")
    @Expose
    private int workCommitment;
    @SerializedName("working_efficiency")
    @Expose
    private int workingEfficiency;
    @SerializedName("suggessions")
    @Expose
    private List<Object> suggessions = null;
    @SerializedName("partyOwnerId")
    @Expose
    private int partyOwnerId;
    @SerializedName("reviews")
    @Expose
    private int reviews;

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(int fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public int getMaintained() {
        return maintained;
    }

    public void setMaintained(int maintained) {
        this.maintained = maintained;
    }

    public int getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(int queryResponse) {
        this.queryResponse = queryResponse;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
    }

    public int getWorkAttitude() {
        return workAttitude;
    }

    public void setWorkAttitude(int workAttitude) {
        this.workAttitude = workAttitude;
    }

    public int getWorkCommitment() {
        return workCommitment;
    }

    public void setWorkCommitment(int workCommitment) {
        this.workCommitment = workCommitment;
    }

    public int getWorkingEfficiency() {
        return workingEfficiency;
    }

    public void setWorkingEfficiency(int workingEfficiency) {
        this.workingEfficiency = workingEfficiency;
    }

    public List<Object> getSuggessions() {
        return suggessions;
    }

    public void setSuggessions(List<Object> suggessions) {
        this.suggessions = suggessions;
    }

    public int getPartyOwnerId() {
        return partyOwnerId;
    }

    public void setPartyOwnerId(int partyOwnerId) {
        this.partyOwnerId = partyOwnerId;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
