package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteMachine {

    @SerializedName("favouriteId")
    @Expose
    private int favouriteId;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("lastUpdatedLogin")
    @Expose
    private Object lastUpdatedLogin;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("machine")
    @Expose
    private RenterMachine machine;
    @SerializedName("party")
    @Expose
    private Object party;

    public int getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
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

    public Object getLastUpdatedLogin() {
        return lastUpdatedLogin;
    }

    public void setLastUpdatedLogin(Object lastUpdatedLogin) {
        this.lastUpdatedLogin = lastUpdatedLogin;
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

    public RenterMachine getMachine() {
        return machine;
    }

    public void setMachine(RenterMachine machine) {
        this.machine = machine;
    }

    public Object getParty() {
        return party;
    }

    public void setParty(Object party) {
        this.party = party;
    }
}
