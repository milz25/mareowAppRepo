package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferWorkOrder implements Serializable {

    @SerializedName("workOrderLineId")
    @Expose
    private int workOrderLineId;
    @SerializedName("commencementDate")
    @Expose
    private String commencementDate;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("expectedDateDelivery")
    @Expose
    private String expectedDateDelivery;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("lastUpdatedLogin")
    @Expose
    private Object lastUpdatedLogin;
    @SerializedName("machineBookDate")
    @Expose
    private String machineBookDate;
    @SerializedName("machineLocationCurrent")
    @Expose
    private String machineLocationCurrent;
    @SerializedName("remarksQcelNaciel")
    @Expose
    private String remarksQcelNaciel;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("workLocationSite")
    @Expose
    private String workLocationSite;
    @SerializedName("workOrderAmt")
    @Expose
    private double workOrderAmt;
    @SerializedName("operatorId")
    @Expose
    private int operatorId;
    @SerializedName("supervisorId")
    @Expose
    private int supervisorId;
    @SerializedName("operatorName")
    @Expose
    private String operatorName;
    @SerializedName("supervisorName")
    @Expose
    private String supervisorName;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("workorderDTO")
    @Expose
    private OfferWorkOrderDTO workorderDTO;

    public int getWorkOrderLineId() {
        return workOrderLineId;
    }

    public void setWorkOrderLineId(int workOrderLineId) {
        this.workOrderLineId = workOrderLineId;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpectedDateDelivery() {
        return expectedDateDelivery;
    }

    public void setExpectedDateDelivery(String expectedDateDelivery) {
        this.expectedDateDelivery = expectedDateDelivery;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Object getLastUpdatedLogin() {
        return lastUpdatedLogin;
    }

    public void setLastUpdatedLogin(Object lastUpdatedLogin) {
        this.lastUpdatedLogin = lastUpdatedLogin;
    }

    public String getMachineBookDate() {
        return machineBookDate;
    }

    public void setMachineBookDate(String machineBookDate) {
        this.machineBookDate = machineBookDate;
    }

    public String getMachineLocationCurrent() {
        return machineLocationCurrent;
    }

    public void setMachineLocationCurrent(String machineLocationCurrent) {
        this.machineLocationCurrent = machineLocationCurrent;
    }

    public String getRemarksQcelNaciel() {
        return remarksQcelNaciel;
    }

    public void setRemarksQcelNaciel(String remarksQcelNaciel) {
        this.remarksQcelNaciel = remarksQcelNaciel;
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

    public String getWorkLocationSite() {
        return workLocationSite;
    }

    public void setWorkLocationSite(String workLocationSite) {
        this.workLocationSite = workLocationSite;
    }

    public double getWorkOrderAmt() {
        return workOrderAmt;
    }

    public void setWorkOrderAmt(int workOrderAmt) {
        this.workOrderAmt = workOrderAmt;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public OfferWorkOrderDTO getWorkorderDTO() {
        return workorderDTO;
    }

    public void setWorkorderDTO(OfferWorkOrderDTO workorderDTO) {
        this.workorderDTO = workorderDTO;
    }

}
