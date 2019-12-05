package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookMachine {
    @SerializedName("machineId")
    @Expose
    private int machineId;
    @SerializedName("lessorId")
    @Expose
    private int lessorId;
    @SerializedName("lesseeId")
    @Expose
    private int lesseeId;
    @SerializedName("workOrderStartDate")
    @Expose
    private String workOrderStartDate;
    @SerializedName("workOrderEndDate")
    @Expose
    private String workOrderEndDate;
    @SerializedName("dateRangeStr")
    @Expose
    private String dateRangeStr;
    @SerializedName("siteLocation")
    @Expose
    private String siteLocation;
    @SerializedName("currentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("plan")
    @Expose
    private int plan;
    @SerializedName("ammount")
    @Expose
    private float ammount;
    @SerializedName("requireDay")
    @Expose
    private int requireDay;
    @SerializedName("operatorCost")
    @Expose
    private String operatorCost;
    @SerializedName("extraAttachment")
    @Expose
    private String extraAttachment;
    @SerializedName("commencementDate")
    @Expose
    private String commencementDate;
    @SerializedName("expectedDateDelivery")
    @Expose
    private String expectedDateDelivery;
    @SerializedName("remarksQcelNaciel")
    @Expose
    private String remarksQcelNaciel;
    @SerializedName("extraAttachmentStr")
    @Expose
    private String extraAttachmentStr;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("reqHour")
    @Expose
    private float reqHour;
    @SerializedName("machineBook")
    @Expose
    private String machineBook;
    @SerializedName("natureOfProject")
    @Expose
    private String natureOfProject;

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getLessorId() {
        return lessorId;
    }

    public void setLessorId(int lessorId) {
        this.lessorId = lessorId;
    }

    public int getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(int lesseeId) {
        this.lesseeId = lesseeId;
    }

    public String getWorkOrderStartDate() {
        return workOrderStartDate;
    }

    public void setWorkOrderStartDate(String workOrderStartDate) {
        this.workOrderStartDate = workOrderStartDate;
    }

    public String getWorkOrderEndDate() {
        return workOrderEndDate;
    }

    public void setWorkOrderEndDate(String workOrderEndDate) {
        this.workOrderEndDate = workOrderEndDate;
    }

    public String getDateRangeStr() {
        return dateRangeStr;
    }

    public void setDateRangeStr(String dateRangeStr) {
        this.dateRangeStr = dateRangeStr;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }

    public int getRequireDay() {
        return requireDay;
    }

    public void setRequireDay(int requireDay) {
        this.requireDay = requireDay;
    }

    public String getOperatorCost() {
        return operatorCost;
    }

    public void setOperatorCost(String operatorCost) {
        this.operatorCost = operatorCost;
    }

    public String getExtraAttachment() {
        return extraAttachment;
    }

    public void setExtraAttachment(String extraAttachment) {
        this.extraAttachment = extraAttachment;
    }

    public String getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(String commencementDate) {
        this.commencementDate = commencementDate;
    }

    public String getExpectedDateDelivery() {
        return expectedDateDelivery;
    }

    public void setExpectedDateDelivery(String expectedDateDelivery) {
        this.expectedDateDelivery = expectedDateDelivery;
    }

    public String getRemarksQcelNaciel() {
        return remarksQcelNaciel;
    }

    public void setRemarksQcelNaciel(String remarksQcelNaciel) {
        this.remarksQcelNaciel = remarksQcelNaciel;
    }

    public String getExtraAttachmentStr() {
        return extraAttachmentStr;
    }

    public void setExtraAttachmentStr(String extraAttachmentStr) {
        this.extraAttachmentStr = extraAttachmentStr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public float getReqHour() {
        return reqHour;
    }

    public void setReqHour(float reqHour) {
        this.reqHour = reqHour;
    }

    public String getMachineBook() {
        return machineBook;
    }

    public void setMachineBook(String machineBook) {
        this.machineBook = machineBook;
    }

    public String getNatureOfProject() {
        return natureOfProject;
    }

    public void setNatureOfProject(String natureOfProject) {
        this.natureOfProject = natureOfProject;
    }

}
