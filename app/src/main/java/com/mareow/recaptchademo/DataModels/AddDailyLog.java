package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDailyLog {
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("machineId")
    @Expose
    private int machineId;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("logDate")
    @Expose
    private String logDate;
    @SerializedName("logDateStr")
    @Expose
    private String logDateStr;
    @SerializedName("logStartTime")
    @Expose
    private String logStartTime;
    @SerializedName("logStartTimeStr")
    @Expose
    private String logStartTimeStr;
    @SerializedName("logEndTime")
    @Expose
    private Object logEndTime;
    @SerializedName("logEndTimeStr")
    @Expose
    private Object logEndTimeStr;
    @SerializedName("logHours")
    @Expose
    private float logHours;
    @SerializedName("logHoursStr")
    @Expose
    private Object logHoursStr;
    @SerializedName("remarkType")
    @Expose
    private String remarkType;
    @SerializedName("remarkCode")
    @Expose
    private String remarkCode;
    @SerializedName("remarkDesc")
    @Expose
    private String remarkDesc;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("editDate")
    @Expose
    private String editDate;
    @SerializedName("editLogFLG")
    @Expose
    private boolean editLogFLG;
    @SerializedName("workOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("partyName")
    @Expose
    private String partyName;
    @SerializedName("logDay")
    @Expose
    private int logDay;
    @SerializedName("logStartTimeCreate")
    @Expose
    private Object logStartTimeCreate;
    @SerializedName("logStartTimeCreateStr")
    @Expose
    private Object logStartTimeCreateStr;
    @SerializedName("logExitFLG")
    @Expose
    private boolean logExitFLG;
    @SerializedName("woCloseFLG")
    @Expose
    private boolean woCloseFLG;
    @SerializedName("startKmsStr")
    @Expose
    private Object startKmsStr;
    @SerializedName("endKmsStr")
    @Expose
    private Object endKmsStr;
    @SerializedName("noOfKmsStr")
    @Expose
    private Object noOfKmsStr;
    @SerializedName("fuelStr")
    @Expose
    private Object fuelStr;
    @SerializedName("startKms")
    @Expose
    private int startKms;
    @SerializedName("endKms")
    @Expose
    private int endKms;
    @SerializedName("noOfKms")
    @Expose
    private int noOfKms;
    @SerializedName("fuel")
    @Expose
    private int fuel;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogDateStr() {
        return logDateStr;
    }

    public void setLogDateStr(String logDateStr) {
        this.logDateStr = logDateStr;
    }

    public String getLogStartTime() {
        return logStartTime;
    }

    public void setLogStartTime(String logStartTime) {
        this.logStartTime = logStartTime;
    }

    public String getLogStartTimeStr() {
        return logStartTimeStr;
    }

    public void setLogStartTimeStr(String logStartTimeStr) {
        this.logStartTimeStr = logStartTimeStr;
    }

    public Object getLogEndTime() {
        return logEndTime;
    }

    public void setLogEndTime(Object logEndTime) {
        this.logEndTime = logEndTime;
    }

    public Object getLogEndTimeStr() {
        return logEndTimeStr;
    }

    public void setLogEndTimeStr(Object logEndTimeStr) {
        this.logEndTimeStr = logEndTimeStr;
    }

    public float getLogHours() {
        return logHours;
    }

    public void setLogHours(float logHours) {
        this.logHours = logHours;
    }

    public Object getLogHoursStr() {
        return logHoursStr;
    }

    public void setLogHoursStr(Object logHoursStr) {
        this.logHoursStr = logHoursStr;
    }

    public String getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
    }

    public String getRemarkCode() {
        return remarkCode;
    }

    public void setRemarkCode(String remarkCode) {
        this.remarkCode = remarkCode;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public boolean isEditLogFLG() {
        return editLogFLG;
    }

    public void setEditLogFLG(boolean editLogFLG) {
        this.editLogFLG = editLogFLG;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getLogDay() {
        return logDay;
    }

    public void setLogDay(int logDay) {
        this.logDay = logDay;
    }

    public Object getLogStartTimeCreate() {
        return logStartTimeCreate;
    }

    public void setLogStartTimeCreate(Object logStartTimeCreate) {
        this.logStartTimeCreate = logStartTimeCreate;
    }

    public Object getLogStartTimeCreateStr() {
        return logStartTimeCreateStr;
    }

    public void setLogStartTimeCreateStr(Object logStartTimeCreateStr) {
        this.logStartTimeCreateStr = logStartTimeCreateStr;
    }

    public boolean isLogExitFLG() {
        return logExitFLG;
    }

    public void setLogExitFLG(boolean logExitFLG) {
        this.logExitFLG = logExitFLG;
    }

    public boolean isWoCloseFLG() {
        return woCloseFLG;
    }

    public void setWoCloseFLG(boolean woCloseFLG) {
        this.woCloseFLG = woCloseFLG;
    }

    public Object getStartKmsStr() {
        return startKmsStr;
    }

    public void setStartKmsStr(Object startKmsStr) {
        this.startKmsStr = startKmsStr;
    }

    public Object getEndKmsStr() {
        return endKmsStr;
    }

    public void setEndKmsStr(Object endKmsStr) {
        this.endKmsStr = endKmsStr;
    }

    public Object getNoOfKmsStr() {
        return noOfKmsStr;
    }

    public void setNoOfKmsStr(Object noOfKmsStr) {
        this.noOfKmsStr = noOfKmsStr;
    }

    public Object getFuelStr() {
        return fuelStr;
    }

    public void setFuelStr(Object fuelStr) {
        this.fuelStr = fuelStr;
    }

    public int getStartKms() {
        return startKms;
    }

    public void setStartKms(int startKms) {
        this.startKms = startKms;
    }

    public int getEndKms() {
        return endKms;
    }

    public void setEndKms(int endKms) {
        this.endKms = endKms;
    }

    public int getNoOfKms() {
        return noOfKms;
    }

    public void setNoOfKms(int noOfKms) {
        this.noOfKms = noOfKms;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
}
