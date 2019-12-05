package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMachineModel {
    @SerializedName("partyId")
    @Expose
    private String partyId;
    @SerializedName("segmentCode")
    @Expose
    private String segmentCode;
    @SerializedName("catagoryCode")
    @Expose
    private String catagoryCode;
    @SerializedName("subCategoryCode")
    @Expose
    private String subCategoryCode;
    @SerializedName("manufacturerCode")
    @Expose
    private String manufacturerCode;
    @SerializedName("machineModelId")
    @Expose
    private String machineModelId;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getCatagoryCode() {
        return catagoryCode;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public void setSubCategoryCode(String subCategoryCode) {
        this.subCategoryCode = subCategoryCode;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getMachineModelId() {
        return machineModelId;
    }

    public void setMachineModelId(String machineModelId) {
        this.machineModelId = machineModelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
