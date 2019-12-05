package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorMachineCount {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("machineId")
    @Expose
    private int machineId;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("machineFullName")
    @Expose
    private String machineFullName;
    @SerializedName("modelName")
    @Expose
    private Object modelName;
    @SerializedName("modelNo")
    @Expose
    private Object modelNo;
    @SerializedName("segment")
    @Expose
    private Object segment;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("subcategory")
    @Expose
    private Object subcategory;
    @SerializedName("manufacturer")
    @Expose
    private Object manufacturer;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("verified")
    @Expose
    private boolean verified;
    @SerializedName("userRole")
    @Expose
    private Object userRole;
    @SerializedName("verifiedCount")
    @Expose
    private int verifiedCount;
    @SerializedName("userCategory")
    @Expose
    private Object userCategory;

    @SerializedName("categoryImage")
    @Expose
    private String categoryImage;



    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMachineFullName() {
        return machineFullName;
    }

    public void setMachineFullName(String machineFullName) {
        this.machineFullName = machineFullName;
    }

    public Object getModelName() {
        return modelName;
    }

    public void setModelName(Object modelName) {
        this.modelName = modelName;
    }

    public Object getModelNo() {
        return modelNo;
    }

    public void setModelNo(Object modelNo) {
        this.modelNo = modelNo;
    }

    public Object getSegment() {
        return segment;
    }

    public void setSegment(Object segment) {
        this.segment = segment;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Object subcategory) {
        this.subcategory = subcategory;
    }

    public Object getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Object manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Object getUserRole() {
        return userRole;
    }

    public void setUserRole(Object userRole) {
        this.userRole = userRole;
    }

    public int getVerifiedCount() {
        return verifiedCount;
    }

    public void setVerifiedCount(int verifiedCount) {
        this.verifiedCount = verifiedCount;
    }

    public Object getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(Object userCategory) {
        this.userCategory = userCategory;
    }

}
