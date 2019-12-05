package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderCancel {

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
    @SerializedName("woMaxEndDateStr")
    @Expose
    private String woMaxEndDateStr;
    @SerializedName("woCancelDate")
    @Expose
    private String woCancelDate;
    @SerializedName("woCancelDateStr")
    @Expose
    private String woCancelDateStr;
    @SerializedName("tncflg")
    @Expose
    private boolean tncflg;
    @SerializedName("ownCancelComment")
    @Expose
    private String ownCancelComment;
    @SerializedName("renCancelReason")
    @Expose
    private String renCancelReason;
    @SerializedName("cancelStatus")
    @Expose
    private String cancelStatus;
    @SerializedName("loginRoleLogic")
    @Expose
    private String loginRoleLogic;

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

    public String getWoStartDate() {
        return woStartDate;
    }

    public void setWoStartDate(String woStartDate) {
        this.woStartDate = woStartDate;
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

    public String getWoMaxEndDateStr() {
        return woMaxEndDateStr;
    }

    public void setWoMaxEndDateStr(String woMaxEndDateStr) {
        this.woMaxEndDateStr = woMaxEndDateStr;
    }

    public String getWoCancelDate() {
        return woCancelDate;
    }

    public void setWoCancelDate(String woCancelDate) {
        this.woCancelDate = woCancelDate;
    }

    public String getWoCancelDateStr() {
        return woCancelDateStr;
    }

    public void setWoCancelDateStr(String woCancelDateStr) {
        this.woCancelDateStr = woCancelDateStr;
    }

    public boolean isTncflg() {
        return tncflg;
    }

    public void setTncflg(boolean tncflg) {
        this.tncflg = tncflg;
    }

    public String getOwnCancelComment() {
        return ownCancelComment;
    }

    public void setOwnCancelComment(String ownCancelComment) {
        this.ownCancelComment = ownCancelComment;
    }

    public String getRenCancelReason() {
        return renCancelReason;
    }

    public void setRenCancelReason(String renCancelReason) {
        this.renCancelReason = renCancelReason;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getLoginRoleLogic() {
        return loginRoleLogic;
    }

    public void setLoginRoleLogic(String loginRoleLogic) {
        this.loginRoleLogic = loginRoleLogic;
    }

}


