package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachineFeedBack {
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("fuelEfficiency")
    @Expose
    private String fuelEfficiency;
    @SerializedName("maintained")
    @Expose
    private String maintained;
    @SerializedName("working_efficiency")
    @Expose
    private String workingEfficiency;
    @SerializedName("partyId")
    @Expose
    private String partyId;
    @SerializedName("workOrderId")
    @Expose
    private String workOrderId;
    @SerializedName("machineRemark")
    @Expose
    private String machineRemark;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(String fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public String getMaintained() {
        return maintained;
    }

    public void setMaintained(String maintained) {
        this.maintained = maintained;
    }

    public String getWorkingEfficiency() {
        return workingEfficiency;
    }

    public void setWorkingEfficiency(String workingEfficiency) {
        this.workingEfficiency = workingEfficiency;
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

    public String getMachineRemark() {
        return machineRemark;
    }

    public void setMachineRemark(String machineRemark) {
        this.machineRemark = machineRemark;
    }

}
