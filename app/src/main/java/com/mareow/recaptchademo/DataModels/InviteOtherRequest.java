package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InviteOtherRequest {

    @SerializedName("inviteTo")
    @Expose
    private List<String> inviteTo = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("partyId")
    @Expose
    private String partyId;

    public List<String> getInviteTo() {
        return inviteTo;
    }

    public void setInviteTo(List<String> inviteTo) {
        this.inviteTo = inviteTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
