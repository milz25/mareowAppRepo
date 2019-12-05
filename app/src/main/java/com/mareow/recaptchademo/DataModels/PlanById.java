package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanById {
    @SerializedName("planId")
    @Expose
    private int planId;
    @SerializedName("accomodation")
    @Expose
    private boolean accomodation;
    @SerializedName("basicRate")
    @Expose
    private int basicRate;
    @SerializedName("cgst")
    @Expose
    private int cgst;
    @SerializedName("commitmentMonth")
    @Expose
    private int commitmentMonth;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("dailyHours")
    @Expose
    private int dailyHours;
    @SerializedName("dailyMinHours")
    @Expose
    private int dailyMinHours;
    @SerializedName("days")
    @Expose
    private int days;
    @SerializedName("demobilityAmt")
    @Expose
    private int demobilityAmt;
    @SerializedName("responsibilityAmount")
    @Expose
    private boolean responsibilityAmount;
    @SerializedName("demobilityResponsible")
    @Expose
    private String demobilityResponsible;
    @SerializedName("extraAttachment")
    @Expose
    private boolean extraAttachment;
    @SerializedName("extraAttachmentRateFlg")
    @Expose
    private boolean extraAttachmentRateFlg;
    @SerializedName("extraAttachmentRate")
    @Expose
    private float extraAttachmentRate;
    @SerializedName("food")
    @Expose
    private boolean food;
    @SerializedName("checkGstFlg")
    @Expose
    private boolean checkGstFlg;
    @SerializedName("gst")
    @Expose
    private boolean gst;
    @SerializedName("igstFlg")
    @Expose
    private boolean igstFlg;
    @SerializedName("igst")
    @Expose
    private int igst;
    @SerializedName("defaultAttachment")
    @Expose
    private boolean defaultAttachment;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("load")
    @Expose
    private String load;
    @SerializedName("minDays")
    @Expose
    private int minDays;
    @SerializedName("takingAmount")
    @Expose
    private boolean takingAmount;
    @SerializedName("mobilityAmt")
    @Expose
    private float mobilityAmt;
    @SerializedName("mobilityResponsible")
    @Expose
    private String mobilityResponsible;
    @SerializedName("monthlyHours")
    @Expose
    private int monthlyHours;
    @SerializedName("operatorFlg")
    @Expose
    private boolean operatorFlg;
    @SerializedName("overtime")
    @Expose
    private int overtime;
    @SerializedName("planDescription")
    @Expose
    private String planDescription;
    @SerializedName("planName")
    @Expose
    private String planName;
    @SerializedName("planNameShort")
    @Expose
    private String planNameShort;
    @SerializedName("sgst")
    @Expose
    private int sgst;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("transportation")
    @Expose
    private boolean transportation;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("rateFlg")
    @Expose
    private boolean rateFlg;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("planUsage")
    @Expose
    private String planUsage;
    @SerializedName("planUsageCode")
    @Expose
    private String planUsageCode;
    @SerializedName("planType")
    @Expose
    private String planType;
    @SerializedName("renterId")
    @Expose
    private int renterId;
    @SerializedName("planStartDate")
    @Expose
    private Object planStartDate;
    @SerializedName("planStartDateStr")
    @Expose
    private Object planStartDateStr;
    @SerializedName("planEndDate")
    @Expose
    private Object planEndDate;
    @SerializedName("planEndDateStr")
    @Expose
    private Object planEndDateStr;
    @SerializedName("planValidDays")
    @Expose
    private int planValidDays;
    @SerializedName("totalAmmount")
    @Expose
    private int totalAmmount;
    @SerializedName("machines")
    @Expose
    private List<Integer> machines = null;
    @SerializedName("assoMachines")
    @Expose
    private List<AssoMachine> assoMachines = null;
    @SerializedName("viewAssoMachinesTab")
    @Expose
    private boolean viewAssoMachinesTab;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public boolean isAccomodation() {
        return accomodation;
    }

    public void setAccomodation(boolean accomodation) {
        this.accomodation = accomodation;
    }

    public int getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(int basicRate) {
        this.basicRate = basicRate;
    }

    public int getCgst() {
        return cgst;
    }

    public void setCgst(int cgst) {
        this.cgst = cgst;
    }

    public int getCommitmentMonth() {
        return commitmentMonth;
    }

    public void setCommitmentMonth(int commitmentMonth) {
        this.commitmentMonth = commitmentMonth;
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

    public int getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(int dailyHours) {
        this.dailyHours = dailyHours;
    }

    public int getDailyMinHours() {
        return dailyMinHours;
    }

    public void setDailyMinHours(int dailyMinHours) {
        this.dailyMinHours = dailyMinHours;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getDemobilityAmt() {
        return demobilityAmt;
    }

    public void setDemobilityAmt(int demobilityAmt) {
        this.demobilityAmt = demobilityAmt;
    }

    public boolean isResponsibilityAmount() {
        return responsibilityAmount;
    }

    public void setResponsibilityAmount(boolean responsibilityAmount) {
        this.responsibilityAmount = responsibilityAmount;
    }

    public String getDemobilityResponsible() {
        return demobilityResponsible;
    }

    public void setDemobilityResponsible(String demobilityResponsible) {
        this.demobilityResponsible = demobilityResponsible;
    }

    public boolean isExtraAttachment() {
        return extraAttachment;
    }

    public void setExtraAttachment(boolean extraAttachment) {
        this.extraAttachment = extraAttachment;
    }

    public boolean isExtraAttachmentRateFlg() {
        return extraAttachmentRateFlg;
    }

    public void setExtraAttachmentRateFlg(boolean extraAttachmentRateFlg) {
        this.extraAttachmentRateFlg = extraAttachmentRateFlg;
    }

    public float getExtraAttachmentRate() {
        return extraAttachmentRate;
    }

    public void setExtraAttachmentRate(float extraAttachmentRate) {
        this.extraAttachmentRate = extraAttachmentRate;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isCheckGstFlg() {
        return checkGstFlg;
    }

    public void setCheckGstFlg(boolean checkGstFlg) {
        this.checkGstFlg = checkGstFlg;
    }

    public boolean isGst() {
        return gst;
    }

    public void setGst(boolean gst) {
        this.gst = gst;
    }

    public boolean isIgstFlg() {
        return igstFlg;
    }

    public void setIgstFlg(boolean igstFlg) {
        this.igstFlg = igstFlg;
    }

    public int getIgst() {
        return igst;
    }

    public void setIgst(int igst) {
        this.igst = igst;
    }

    public boolean isDefaultAttachment() {
        return defaultAttachment;
    }

    public void setDefaultAttachment(boolean defaultAttachment) {
        this.defaultAttachment = defaultAttachment;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public boolean isTakingAmount() {
        return takingAmount;
    }

    public void setTakingAmount(boolean takingAmount) {
        this.takingAmount = takingAmount;
    }

    public float getMobilityAmt() {
        return mobilityAmt;
    }

    public void setMobilityAmt(float mobilityAmt) {
        this.mobilityAmt = mobilityAmt;
    }

    public String getMobilityResponsible() {
        return mobilityResponsible;
    }

    public void setMobilityResponsible(String mobilityResponsible) {
        this.mobilityResponsible = mobilityResponsible;
    }

    public int getMonthlyHours() {
        return monthlyHours;
    }

    public void setMonthlyHours(int monthlyHours) {
        this.monthlyHours = monthlyHours;
    }

    public boolean isOperatorFlg() {
        return operatorFlg;
    }

    public void setOperatorFlg(boolean operatorFlg) {
        this.operatorFlg = operatorFlg;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanNameShort() {
        return planNameShort;
    }

    public void setPlanNameShort(String planNameShort) {
        this.planNameShort = planNameShort;
    }

    public int getSgst() {
        return sgst;
    }

    public void setSgst(int sgst) {
        this.sgst = sgst;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public boolean isTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isRateFlg() {
        return rateFlg;
    }

    public void setRateFlg(boolean rateFlg) {
        this.rateFlg = rateFlg;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getPlanUsage() {
        return planUsage;
    }

    public void setPlanUsage(String planUsage) {
        this.planUsage = planUsage;
    }

    public String getPlanUsageCode() {
        return planUsageCode;
    }

    public void setPlanUsageCode(String planUsageCode) {
        this.planUsageCode = planUsageCode;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public Object getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Object planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Object getPlanStartDateStr() {
        return planStartDateStr;
    }

    public void setPlanStartDateStr(Object planStartDateStr) {
        this.planStartDateStr = planStartDateStr;
    }

    public Object getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Object planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Object getPlanEndDateStr() {
        return planEndDateStr;
    }

    public void setPlanEndDateStr(Object planEndDateStr) {
        this.planEndDateStr = planEndDateStr;
    }

    public int getPlanValidDays() {
        return planValidDays;
    }

    public void setPlanValidDays(int planValidDays) {
        this.planValidDays = planValidDays;
    }

    public int getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(int totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

    public List<Integer> getMachines() {
        return machines;
    }

    public void setMachines(List<Integer> machines) {
        this.machines = machines;
    }

    public List<AssoMachine> getAssoMachines() {
        return assoMachines;
    }

    public void setAssoMachines(List<AssoMachine> assoMachines) {
        this.assoMachines = assoMachines;
    }

    public boolean isViewAssoMachinesTab() {
        return viewAssoMachinesTab;
    }

    public void setViewAssoMachinesTab(boolean viewAssoMachinesTab) {
        this.viewAssoMachinesTab = viewAssoMachinesTab;
    }

}

