package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkOrderExtend implements Serializable {

    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("workorderNo")
    @Expose
    private String workorderNo;
    @SerializedName("woStartDate")
    @Expose
    private String woStartDate;
    @SerializedName("woStartDateStr")
    @Expose
    private String woStartDateStr;
    @SerializedName("woEndDate")
    @Expose
    private String woEndDate;
    @SerializedName("woEndDateStr")
    @Expose
    private String woEndDateStr;
    @SerializedName("woExtendedEndDate")
    @Expose
    private String woExtendedEndDate;
    @SerializedName("woExtendedEndDateStr")
    @Expose
    private String woExtendedEndDateStr;
    @SerializedName("tncflg")
    @Expose
    private boolean tncflg;
    @SerializedName("ownComment")
    @Expose
    private String ownComment;
    @SerializedName("renExtendReason")
    @Expose
    private String renExtendReason;
    @SerializedName("extendStatus")
    @Expose
    private String extendStatus;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkorderNo() {
        return workorderNo;
    }

    public void setWorkorderNo(String workorderNo) {
        this.workorderNo = workorderNo;
    }

    public String getWoStartDateStr() {
        return woStartDateStr;
    }

    public void setWoStartDateStr(String woStartDateStr) {
        this.woStartDateStr = woStartDateStr;
    }

    public String getWoEndDate() {
        return woEndDate;
    }

    public void setWoEndDate(String woEndDate) {
        this.woEndDate = woEndDate;
    }

    public String getWoEndDateStr() {
        return woEndDateStr;
    }

    public void setWoEndDateStr(String woEndDateStr) {
        this.woEndDateStr = woEndDateStr;
    }

    public String getWoExtendedEndDate() {
        return woExtendedEndDate;
    }

    public void setWoExtendedEndDate(String woExtendedEndDate) {
        this.woExtendedEndDate = woExtendedEndDate;
    }

    public String getWoExtendedEndDateStr() {
        return woExtendedEndDateStr;
    }

    public void setWoExtendedEndDateStr(String woExtendedEndDateStr) {
        this.woExtendedEndDateStr = woExtendedEndDateStr;
    }

    public boolean isTncflg() {
        return tncflg;
    }

    public void setTncflg(boolean tncflg) {
        this.tncflg = tncflg;
    }

    public String getOwnComment() {
        return ownComment;
    }

    public void setOwnComment(String ownComment) {
        this.ownComment = ownComment;
    }

    public String getRenExtendReason() {
        return renExtendReason;
    }

    public void setRenExtendReason(String renExtendReason) {
        this.renExtendReason = renExtendReason;
    }

    public String getExtendStatus() {
        return extendStatus;
    }

    public void setExtendStatus(String extendStatus) {
        this.extendStatus = extendStatus;
    }
    public String getWoStartDate() {
        return woStartDate;
    }

    public void setWoStartDate(String woStartDate) {
        this.woStartDate = woStartDate;
    }

}

