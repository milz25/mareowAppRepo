package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggession {

    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("suggession")
    @Expose
    private String suggession;
    @SerializedName("userName")
    @Expose
    private String userName;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getSuggession() {
        return suggession;
    }

    public void setSuggession(String suggession) {
        this.suggession = suggession;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
