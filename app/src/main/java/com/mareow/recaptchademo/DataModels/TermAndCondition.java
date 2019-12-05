package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermAndCondition {

    @SerializedName("generalTermsdescription")
    @Expose
    private String generalTermsdescription;

    public String getGeneralTermsdescription() {
        return generalTermsdescription;
    }

    public void setGeneralTermsdescription(String generalTermsdescription) {
        this.generalTermsdescription = generalTermsdescription;
    }
}
