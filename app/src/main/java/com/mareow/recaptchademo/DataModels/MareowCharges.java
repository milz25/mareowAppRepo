package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MareowCharges {
    @SerializedName("commonLookupId")
    @Expose
    private int commonLookupId;
    @SerializedName("lookupType")
    @Expose
    private String lookupType;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("lookupCode")
    @Expose
    private String lookupCode;
    @SerializedName("meaning")
    @Expose
    private String meaning;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("parentFlg")
    @Expose
    private boolean parentFlg;
    @SerializedName("parentType")
    @Expose
    private Object parentType;
    @SerializedName("parentCode")
    @Expose
    private Object parentCode;
    @SerializedName("refFileName")
    @Expose
    private Object refFileName;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("attribute1")
    @Expose
    private String attribute1;
    @SerializedName("attribute2")
    @Expose
    private String attribute2;
    @SerializedName("attribute3")
    @Expose
    private String attribute3;
    @SerializedName("attribute4")
    @Expose
    private Object attribute4;
    @SerializedName("attribute5")
    @Expose
    private Object attribute5;
    @SerializedName("combo")
    @Expose
    private Combo combo;

    public int getCommonLookupId() {
        return commonLookupId;
    }

    public void setCommonLookupId(int commonLookupId) {
        this.commonLookupId = commonLookupId;
    }

    public String getLookupType() {
        return lookupType;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public boolean isParentFlg() {
        return parentFlg;
    }

    public void setParentFlg(boolean parentFlg) {
        this.parentFlg = parentFlg;
    }

    public Object getParentType() {
        return parentType;
    }

    public void setParentType(Object parentType) {
        this.parentType = parentType;
    }

    public Object getParentCode() {
        return parentCode;
    }

    public void setParentCode(Object parentCode) {
        this.parentCode = parentCode;
    }

    public Object getRefFileName() {
        return refFileName;
    }

    public void setRefFileName(Object refFileName) {
        this.refFileName = refFileName;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public Object getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(Object attribute4) {
        this.attribute4 = attribute4;
    }

    public Object getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(Object attribute5) {
        this.attribute5 = attribute5;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

}

