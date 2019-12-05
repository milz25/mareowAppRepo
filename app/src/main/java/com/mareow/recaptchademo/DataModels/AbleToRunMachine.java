package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbleToRunMachine {
    @SerializedName("operatorMachineId")
    @Expose
    private int operatorMachineId;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("segment")
    @Expose
    private List<Object> segment = null;
    @SerializedName("segmentCode")
    @Expose
    private String segmentCode;
    @SerializedName("segmentMeaning")
    @Expose
    private String segmentMeaning;
    @SerializedName("catagoryCode")
    @Expose
    private String catagoryCode;
    @SerializedName("catagoryMeaning")
    @Expose
    private String catagoryMeaning;
    @SerializedName("subCategoryCode")
    @Expose
    private String subCategoryCode;
    @SerializedName("subCategoryMeaning")
    @Expose
    private String subCategoryMeaning;
    @SerializedName("manufacturerCode")
    @Expose
    private String manufacturerCode;
    @SerializedName("manufacturerMeaning")
    @Expose
    private String manufacturerMeaning;
    @SerializedName("modelNo")
    @Expose
    private String modelNo;
    @SerializedName("machineModelId")
    @Expose
    private int machineModelId;
    @SerializedName("modelName")
    @Expose
    private String modelName;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("modelImage")
    @Expose
    private Object modelImage;

    public int getOperatorMachineId() {
        return operatorMachineId;
    }

    public void setOperatorMachineId(int operatorMachineId) {
        this.operatorMachineId = operatorMachineId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
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

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    public List<Object> getSegment() {
        return segment;
    }

    public void setSegment(List<Object> segment) {
        this.segment = segment;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getSegmentMeaning() {
        return segmentMeaning;
    }

    public void setSegmentMeaning(String segmentMeaning) {
        this.segmentMeaning = segmentMeaning;
    }

    public String getCatagoryCode() {
        return catagoryCode;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getCatagoryMeaning() {
        return catagoryMeaning;
    }

    public void setCatagoryMeaning(String catagoryMeaning) {
        this.catagoryMeaning = catagoryMeaning;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public void setSubCategoryCode(String subCategoryCode) {
        this.subCategoryCode = subCategoryCode;
    }

    public String getSubCategoryMeaning() {
        return subCategoryMeaning;
    }

    public void setSubCategoryMeaning(String subCategoryMeaning) {
        this.subCategoryMeaning = subCategoryMeaning;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerMeaning() {
        return manufacturerMeaning;
    }

    public void setManufacturerMeaning(String manufacturerMeaning) {
        this.manufacturerMeaning = manufacturerMeaning;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public int getMachineModelId() {
        return machineModelId;
    }

    public void setMachineModelId(int machineModelId) {
        this.machineModelId = machineModelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getModelImage() {
        return modelImage;
    }

    public void setModelImage(Object modelImage) {
        this.modelImage = modelImage;
    }
}
