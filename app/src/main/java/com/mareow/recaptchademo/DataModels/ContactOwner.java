package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactOwner {
    @SerializedName("machineId")
    @Expose
    private String machineId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("renterId")
    @Expose
    private String renterId;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

}
