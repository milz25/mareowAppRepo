package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptWorkOrder {
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("operatorId")
    @Expose
    private int operatorId;
    @SerializedName("supervisorId")
    @Expose
    private int supervisorId;
    @SerializedName("planId")
    @Expose
    private int planId;
    @SerializedName("operatorFlg")
    @Expose
    private boolean operatorFlg;
    @SerializedName("agreementFlg")
    @Expose
    private boolean agreementFlg;
    @SerializedName("ownerSign")
    @Expose
    private String ownerSign;
    @SerializedName("renSign")
    @Expose
    private String renSign;
    @SerializedName("status")
    @Expose
    private String status;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public boolean getOperatorFlg() {
        return operatorFlg;
    }

    public void setOperatorFlg(boolean operatorFlg) {
        this.operatorFlg = operatorFlg;
    }

    public boolean getAgreementFlg() {
        return agreementFlg;
    }

    public void setAgreementFlg(boolean agreementFlg) {
        this.agreementFlg = agreementFlg;
    }

    public String getOwnerSign() {
        return ownerSign;
    }

    public void setOwnerSign(String ownerSign) {
        this.ownerSign = ownerSign;
    }

    public String getRenSign() {
        return renSign;
    }

    public void setRenSign(String renSign) {
        this.renSign = renSign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
