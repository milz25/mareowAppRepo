package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssociatedOperator {

    @SerializedName("party_operator_id")
    @Expose
    private int partyOperatorId;
    @SerializedName("ownerId")
    @Expose
    private int ownerId;
    @SerializedName("operatorId")
    @Expose
    private int operatorId;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("abilitytoRunMachine")
    @Expose
    private String abilitytoRunMachine;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("rateFlg")
    @Expose
    private boolean rateFlg;
    @SerializedName("overtime")
    @Expose
    private int overtime;
    @SerializedName("lastUpdatedLogin")
    @Expose
    private String lastUpdatedLogin;
    @SerializedName("operatorName")
    @Expose
    private String operatorName;
    @SerializedName("operatorImage")
    @Expose
    private Object operatorImage;
    @SerializedName("aboutOperator")
    @Expose
    private String aboutOperator;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("workOrderNo")
    @Expose
    private Object workOrderNo;
    @SerializedName("workEndDateStr")
    @Expose
    private Object workEndDateStr;
    @SerializedName("workStatus")
    @Expose
    private Object workStatus;
    @SerializedName("workStartDateStr")
    @Expose
    private Object workStartDateStr;
    @SerializedName("machineName")
    @Expose
    private Object machineName;
    @SerializedName("isverifiedOpt")
    @Expose
    private boolean isverifiedOpt;
    @SerializedName("isverifiedOwn")
    @Expose
    private boolean isverifiedOwn;
    @SerializedName("optUserCategory")
    @Expose
    private String optUserCategory;
    @SerializedName("ownUserCategory")
    @Expose
    private String ownUserCategory;
    @SerializedName("accomodation")
    @Expose
    private String accomodation;
    @SerializedName("transportation")
    @Expose
    private String transportation;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("associatedSinceStr")
    @Expose
    private String associatedSinceStr;
    @SerializedName("operatorStatus")
    @Expose
    private String operatorStatus;
    @SerializedName("operatorAssociationStatus")
    @Expose
    private String operatorAssociationStatus;
    @SerializedName("operatorAssociationStatusCode")
    @Expose
    private String operatorAssociationStatusCode;
    @SerializedName("deAssociationStatus")
    @Expose
    private Object deAssociationStatus;
    @SerializedName("deAssociationStatusCode")
    @Expose
    private Object deAssociationStatusCode;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("ownerImage")
    @Expose
    private Object ownerImage;
    @SerializedName("aboutOwner")
    @Expose
    private Object aboutOwner;

    public int getPartyOperatorId() {
        return partyOperatorId;
    }

    public void setPartyOperatorId(int partyOperatorId) {
        this.partyOperatorId = partyOperatorId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
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

    public String getAbilitytoRunMachine() {
        return abilitytoRunMachine;
    }

    public void setAbilitytoRunMachine(String abilitytoRunMachine) {
        this.abilitytoRunMachine = abilitytoRunMachine;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isRateFlg() {
        return rateFlg;
    }

    public void setRateFlg(boolean rateFlg) {
        this.rateFlg = rateFlg;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public String getLastUpdatedLogin() {
        return lastUpdatedLogin;
    }

    public void setLastUpdatedLogin(String lastUpdatedLogin) {
        this.lastUpdatedLogin = lastUpdatedLogin;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Object getOperatorImage() {
        return operatorImage;
    }

    public void setOperatorImage(Object operatorImage) {
        this.operatorImage = operatorImage;
    }

    public String getAboutOperator() {
        return aboutOperator;
    }

    public void setAboutOperator(String aboutOperator) {
        this.aboutOperator = aboutOperator;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Object getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(Object workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public Object getWorkEndDateStr() {
        return workEndDateStr;
    }

    public void setWorkEndDateStr(Object workEndDateStr) {
        this.workEndDateStr = workEndDateStr;
    }

    public Object getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Object workStatus) {
        this.workStatus = workStatus;
    }

    public Object getWorkStartDateStr() {
        return workStartDateStr;
    }

    public void setWorkStartDateStr(Object workStartDateStr) {
        this.workStartDateStr = workStartDateStr;
    }

    public Object getMachineName() {
        return machineName;
    }

    public void setMachineName(Object machineName) {
        this.machineName = machineName;
    }

    public boolean isIsverifiedOpt() {
        return isverifiedOpt;
    }

    public void setIsverifiedOpt(boolean isverifiedOpt) {
        this.isverifiedOpt = isverifiedOpt;
    }

    public boolean isIsverifiedOwn() {
        return isverifiedOwn;
    }

    public void setIsverifiedOwn(boolean isverifiedOwn) {
        this.isverifiedOwn = isverifiedOwn;
    }

    public String getOptUserCategory() {
        return optUserCategory;
    }

    public void setOptUserCategory(String optUserCategory) {
        this.optUserCategory = optUserCategory;
    }

    public String getOwnUserCategory() {
        return ownUserCategory;
    }

    public void setOwnUserCategory(String ownUserCategory) {
        this.ownUserCategory = ownUserCategory;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getAssociatedSinceStr() {
        return associatedSinceStr;
    }

    public void setAssociatedSinceStr(String associatedSinceStr) {
        this.associatedSinceStr = associatedSinceStr;
    }

    public String getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(String operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public String getOperatorAssociationStatus() {
        return operatorAssociationStatus;
    }

    public void setOperatorAssociationStatus(String operatorAssociationStatus) {
        this.operatorAssociationStatus = operatorAssociationStatus;
    }

    public String getOperatorAssociationStatusCode() {
        return operatorAssociationStatusCode;
    }

    public void setOperatorAssociationStatusCode(String operatorAssociationStatusCode) {
        this.operatorAssociationStatusCode = operatorAssociationStatusCode;
    }

    public Object getDeAssociationStatus() {
        return deAssociationStatus;
    }

    public void setDeAssociationStatus(Object deAssociationStatus) {
        this.deAssociationStatus = deAssociationStatus;
    }

    public Object getDeAssociationStatusCode() {
        return deAssociationStatusCode;
    }

    public void setDeAssociationStatusCode(Object deAssociationStatusCode) {
        this.deAssociationStatusCode = deAssociationStatusCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Object getOwnerImage() {
        return ownerImage;
    }

    public void setOwnerImage(Object ownerImage) {
        this.ownerImage = ownerImage;
    }

    public Object getAboutOwner() {
        return aboutOwner;
    }

    public void setAboutOwner(Object aboutOwner) {
        this.aboutOwner = aboutOwner;
    }

}
