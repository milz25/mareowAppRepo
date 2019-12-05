package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRoleResponse {

    @SerializedName("Operator")
    @Expose
    private String operator;
    @SerializedName("Supervisor")
    @Expose
    private String supervisor;
    @SerializedName("Owner")
    @Expose
    private String owner;
    @SerializedName("Renter")
    @Expose
    private String renter;
    @SerializedName("Manufacturer")
    @Expose
    private String manufacturer;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
