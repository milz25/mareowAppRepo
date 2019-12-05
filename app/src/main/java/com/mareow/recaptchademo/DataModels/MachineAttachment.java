package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MachineAttachment implements Serializable {

    @SerializedName("attachmentId")
    @Expose
    private int attachmentId;
    @SerializedName("attachmentName")
    @Expose
    private String attachmentName;
    @SerializedName("defaultCheck")
    @Expose
    private boolean defaultCheck;

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public boolean isDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(boolean defaultCheck) {
        this.defaultCheck = defaultCheck;
    }
}
