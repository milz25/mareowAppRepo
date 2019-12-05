package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackForOwner {
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
    private float queryResponse;
    @SerializedName("totalRating")
    @Expose
    private float totalRating;
    @SerializedName("workAttitude")
    @Expose
    private float workAttitude;
    @SerializedName("workCommitment")
    @Expose
    private float workCommitment;
    @SerializedName("working_efficiency")
    @Expose
    private int workingEfficiency;
    @SerializedName("suggessions")
    @Expose
    private List<Suggession> suggessions = null;
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

    public float getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(float queryResponse) {
        this.queryResponse = queryResponse;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
    }

    public float getWorkAttitude() {
        return workAttitude;
    }

    public void setWorkAttitude(float workAttitude) {
        this.workAttitude = workAttitude;
    }

    public float getWorkCommitment() {
        return workCommitment;
    }

    public void setWorkCommitment(float workCommitment) {
        this.workCommitment = workCommitment;
    }

    public int getWorkingEfficiency() {
        return workingEfficiency;
    }

    public void setWorkingEfficiency(int workingEfficiency) {
        this.workingEfficiency = workingEfficiency;
    }

    public List<Suggession> getSuggessions() {
        return suggessions;
    }

    public void setSuggessions(List<Suggession> suggessions) {
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
