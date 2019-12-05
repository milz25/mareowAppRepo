package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFeedback {

    @SerializedName("feedbackId")
    @Expose
    private int feedbackId;
    @SerializedName("feedbackTo")
    @Expose
    private int feedbackTo;
    @SerializedName("feedbackBy")
    @Expose
    private int feedbackBy;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("condition")
    @Expose
    private int condition;
    @SerializedName("fuelEfficiency")
    @Expose
    private int fuelEfficiency;
    @SerializedName("working_efficiency")
    @Expose
    private int workingEfficiency;
    @SerializedName("maintained")
    @Expose
    private int maintained;
    @SerializedName("machineRemark")
    @Expose
    private String machineRemark;
    @SerializedName("totalRatingMachine")
    @Expose
    private float totalRatingMachine;
    @SerializedName("workCommitment")
    @Expose
    private int workCommitment;
    @SerializedName("workAttitude")
    @Expose
    private int workAttitude;
    @SerializedName("efficiency")
    @Expose
    private int efficiency;
    @SerializedName("queryResponse")
    @Expose
    private int queryResponse;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;
    @SerializedName("totalRatingOwner")
    @Expose
    private float totalRatingOwner;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("lastUpdatedLogin")
    @Expose
    private String lastUpdatedLogin;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getFeedbackTo() {
        return feedbackTo;
    }

    public void setFeedbackTo(int feedbackTo) {
        this.feedbackTo = feedbackTo;
    }

    public int getFeedbackBy() {
        return feedbackBy;
    }

    public void setFeedbackBy(int feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(int fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public int getWorkingEfficiency() {
        return workingEfficiency;
    }

    public void setWorkingEfficiency(int workingEfficiency) {
        this.workingEfficiency = workingEfficiency;
    }

    public int getMaintained() {
        return maintained;
    }

    public void setMaintained(int maintained) {
        this.maintained = maintained;
    }

    public String getMachineRemark() {
        return machineRemark;
    }

    public void setMachineRemark(String machineRemark) {
        this.machineRemark = machineRemark;
    }

    public float getTotalRatingMachine() {
        return totalRatingMachine;
    }

    public void setTotalRatingMachine(float totalRatingMachine) {
        this.totalRatingMachine = totalRatingMachine;
    }

    public int getWorkCommitment() {
        return workCommitment;
    }

    public void setWorkCommitment(int workCommitment) {
        this.workCommitment = workCommitment;
    }

    public int getWorkAttitude() {
        return workAttitude;
    }

    public void setWorkAttitude(int workAttitude) {
        this.workAttitude = workAttitude;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(int queryResponse) {
        this.queryResponse = queryResponse;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public float getTotalRatingOwner() {
        return totalRatingOwner;
    }

    public void setTotalRatingOwner(float totalRatingOwner) {
        this.totalRatingOwner = totalRatingOwner;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLastUpdatedLogin() {
        return lastUpdatedLogin;
    }

    public void setLastUpdatedLogin(String lastUpdatedLogin) {
        this.lastUpdatedLogin = lastUpdatedLogin;
    }

}
