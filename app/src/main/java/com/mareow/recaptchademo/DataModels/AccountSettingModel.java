package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountSettingModel {
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("emailFlg")
    @Expose
    private boolean emailFlg;

    public int getWoExtendedDay() {
        return woExtendedDay;
    }

    public void setWoExtendedDay(int woExtendedDay) {
        this.woExtendedDay = woExtendedDay;
    }

    public int getWoCancelDay() {
        return woCancelDay;
    }

    public void setWoCancelDay(int woCancelDay) {
        this.woCancelDay = woCancelDay;
    }

    public int getMachineListDay() {
        return machineListDay;
    }

    public void setMachineListDay(int machineListDay) {
        this.machineListDay = machineListDay;
    }

    @SerializedName("notificationFlg")
    @Expose
    private boolean notificationFlg;
    @SerializedName("locationFlg")
    @Expose
    private boolean locationFlg;
    @SerializedName("chatFlg")
    @Expose
    private boolean chatFlg;
    @SerializedName("woExtendedDay")
    @Expose
    private int woExtendedDay;
    @SerializedName("woCancelDay")
    @Expose
    private int woCancelDay;
    @SerializedName("machineListDay")
    @Expose
    private int machineListDay;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isEmailFlg() {
        return emailFlg;
    }

    public void setEmailFlg(boolean emailFlg) {
        this.emailFlg = emailFlg;
    }

    public boolean isNotificationFlg() {
        return notificationFlg;
    }

    public void setNotificationFlg(boolean notificationFlg) {
        this.notificationFlg = notificationFlg;
    }

    public boolean isLocationFlg() {
        return locationFlg;
    }

    public void setLocationFlg(boolean locationFlg) {
        this.locationFlg = locationFlg;
    }

    public boolean isChatFlg() {
        return chatFlg;
    }

    public void setChatFlg(boolean chatFlg) {
        this.chatFlg = chatFlg;
    }
}
