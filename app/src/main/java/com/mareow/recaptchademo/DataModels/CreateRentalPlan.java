package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRentalPlan {
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("accomodation")
    @Expose
    private boolean accomodation;
    @SerializedName("basicRate")
    @Expose
    private String basicRate;
    @SerializedName("cgst")
    @Expose
    private float cgst;
    @SerializedName("commitmentMonth")
    @Expose
    private String commitmentMonth;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("dailyHours")
    @Expose
    private String dailyHours;
    @SerializedName("dailyMinHours")
    @Expose
    private String dailyMinHours;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("demobilityAmt")
    @Expose
    private float demobilityAmt;
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
    @SerializedName("gst")
    @Expose
    private boolean gst;
    @SerializedName("igstFlg")
    @Expose
    private boolean igstFlg;
    @SerializedName("igst")
    @Expose
    private float igst;
    @SerializedName("defaultAttachment")
    @Expose
    private boolean defaultAttachment;
    @SerializedName("inactive")
    @Expose
    private String inactive;
    @SerializedName("load")
    @Expose
    private String load;
    @SerializedName("minDays")
    @Expose
    private String minDays;
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
    private String monthlyHours;
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
    private float sgst;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("transportation")
    @Expose
    private boolean transportation;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
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
    private String renterId;
    @SerializedName("planStartDate")
    @Expose
    private String planStartDate;
    @SerializedName("planStartDateStr")
    @Expose
    private String planStartDateStr;
    @SerializedName("planEndDate")
    @Expose
    private String planEndDate;
    @SerializedName("planEndDateStr")
    @Expose
    private String planEndDateStr;
    @SerializedName("planValidDays")
    @Expose
    private String planValidDays;
    @SerializedName("totalAmmount")
    @Expose
    private String totalAmmount;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public boolean getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(boolean accomodation) {
        this.accomodation = accomodation;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public float getCgst() {
        return cgst;
    }

    public void setCgst(float cgst) {
        this.cgst = cgst;
    }

    public String getCommitmentMonth() {
        return commitmentMonth;
    }

    public void setCommitmentMonth(String commitmentMonth) {
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

    public String getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(String dailyHours) {
        this.dailyHours = dailyHours;
    }

    public String getDailyMinHours() {
        return dailyMinHours;
    }

    public void setDailyMinHours(String dailyMinHours) {
        this.dailyMinHours = dailyMinHours;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public float getDemobilityAmt() {
        return demobilityAmt;
    }

    public void setDemobilityAmt(float demobilityAmt) {
        this.demobilityAmt = demobilityAmt;
    }

    public boolean getResponsibilityAmount() {
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

    public boolean getExtraAttachment() {
        return extraAttachment;
    }

    public void setExtraAttachment(boolean extraAttachment) {
        this.extraAttachment = extraAttachment;
    }

    public boolean getExtraAttachmentRateFlg() {
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

    public boolean getFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean getGst() {
        return gst;
    }

    public void setGst(boolean gst) {
        this.gst = gst;
    }

    public boolean getIgstFlg() {
        return igstFlg;
    }

    public void setIgstFlg(boolean igstFlg) {
        this.igstFlg = igstFlg;
    }

    public float getIgst() {
        return igst;
    }

    public void setIgst(float igst) {
        this.igst = igst;
    }

    public boolean getDefaultAttachment() {
        return defaultAttachment;
    }

    public void setDefaultAttachment(boolean defaultAttachment) {
        this.defaultAttachment = defaultAttachment;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getMinDays() {
        return minDays;
    }

    public void setMinDays(String minDays) {
        this.minDays = minDays;
    }

    public boolean getTakingAmount() {
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

    public String getMonthlyHours() {
        return monthlyHours;
    }

    public void setMonthlyHours(String monthlyHours) {
        this.monthlyHours = monthlyHours;
    }

    public boolean getOperatorFlg() {
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

    public float getSgst() {
        return sgst;
    }

    public void setSgst(float sgst) {
        this.sgst = sgst;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public boolean getTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getRateFlg() {
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

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanStartDateStr() {
        return planStartDateStr;
    }

    public void setPlanStartDateStr(String planStartDateStr) {
        this.planStartDateStr = planStartDateStr;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanEndDateStr() {
        return planEndDateStr;
    }

    public void setPlanEndDateStr(String planEndDateStr) {
        this.planEndDateStr = planEndDateStr;
    }

    public String getPlanValidDays() {
        return planValidDays;
    }

    public void setPlanValidDays(String planValidDays) {
        this.planValidDays = planValidDays;
    }

    public String getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(String totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

}
