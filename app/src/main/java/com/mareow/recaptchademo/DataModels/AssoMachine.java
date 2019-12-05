package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssoMachine {
    @SerializedName("machineId")
    @Expose
    private int machineId;
    @SerializedName("machineName")
    @Expose
    private String machineName;
    @SerializedName("rcbookCHK")
    @Expose
    private boolean rcbookCHK;
    @SerializedName("nationPermitCHK")
    @Expose
    private boolean nationPermitCHK;
    @SerializedName("pucCertifiacteCHK")
    @Expose
    private boolean pucCertifiacteCHK;
    @SerializedName("roadTaxCHK")
    @Expose
    private boolean roadTaxCHK;
    @SerializedName("insurance")
    @Expose
    private boolean insurance;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("subCategory")
    @Expose
    private int subCategory;
    @SerializedName("manufacturerId")
    @Expose
    private int manufacturerId;
    @SerializedName("machineModelId")
    @Expose
    private int machineModelId;
    @SerializedName("modelNo")
    @Expose
    private String modelNo;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("overallRating")
    @Expose
    private int overallRating;
    @SerializedName("noOfFeedbacks")
    @Expose
    private int noOfFeedbacks;
    @SerializedName("noOfComments")
    @Expose
    private int noOfComments;
    @SerializedName("isverified")
    @Expose
    private boolean isverified;
    @SerializedName("latitude")
    @Expose
    private int latitude;
    @SerializedName("longitude")
    @Expose
    private int longitude;
    @SerializedName("imagesPath")
    @Expose
    private List<Object> imagesPath = null;
    @SerializedName("imagesPathUrls")
    @Expose
    private ImagesPathUrls imagesPathUrls;
    @SerializedName("viewImagePathUrls")
    @Expose
    private List<Object> viewImagePathUrls = null;
    @SerializedName("favouriteId")
    @Expose
    private int favouriteId;
    @SerializedName("favouriteFlg")
    @Expose
    private boolean favouriteFlg;
    @SerializedName("isverifiedOwner")
    @Expose
    private boolean isverifiedOwner;
    @SerializedName("attachmentId")
    @Expose
    private List<Object> attachmentId = null;
    @SerializedName("defaultAttachment")
    @Expose
    private int defaultAttachment;
    @SerializedName("termsconditioncheck")
    @Expose
    private boolean termsconditioncheck;
    @SerializedName("runHours")
    @Expose
    private int runHours;
    @SerializedName("odometer")
    @Expose
    private int odometer;
    @SerializedName("showDateInfoMsg")
    @Expose
    private boolean showDateInfoMsg;
    @SerializedName("assignTowd")
    @Expose
    private boolean assignTowd;
    @SerializedName("assoToplan")
    @Expose
    private boolean assoToplan;
    @SerializedName("disableDeleteFlg")
    @Expose
    private boolean disableDeleteFlg;

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public boolean isRcbookCHK() {
        return rcbookCHK;
    }

    public void setRcbookCHK(boolean rcbookCHK) {
        this.rcbookCHK = rcbookCHK;
    }

    public boolean isNationPermitCHK() {
        return nationPermitCHK;
    }

    public void setNationPermitCHK(boolean nationPermitCHK) {
        this.nationPermitCHK = nationPermitCHK;
    }

    public boolean isPucCertifiacteCHK() {
        return pucCertifiacteCHK;
    }

    public void setPucCertifiacteCHK(boolean pucCertifiacteCHK) {
        this.pucCertifiacteCHK = pucCertifiacteCHK;
    }

    public boolean isRoadTaxCHK() {
        return roadTaxCHK;
    }

    public void setRoadTaxCHK(boolean roadTaxCHK) {
        this.roadTaxCHK = roadTaxCHK;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public int getMachineModelId() {
        return machineModelId;
    }

    public void setMachineModelId(int machineModelId) {
        this.machineModelId = machineModelId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public int getNoOfFeedbacks() {
        return noOfFeedbacks;
    }

    public void setNoOfFeedbacks(int noOfFeedbacks) {
        this.noOfFeedbacks = noOfFeedbacks;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public boolean isIsverified() {
        return isverified;
    }

    public void setIsverified(boolean isverified) {
        this.isverified = isverified;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public List<Object> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(List<Object> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public ImagesPathUrls getImagesPathUrls() {
        return imagesPathUrls;
    }

    public void setImagesPathUrls(ImagesPathUrls imagesPathUrls) {
        this.imagesPathUrls = imagesPathUrls;
    }

    public List<Object> getViewImagePathUrls() {
        return viewImagePathUrls;
    }

    public void setViewImagePathUrls(List<Object> viewImagePathUrls) {
        this.viewImagePathUrls = viewImagePathUrls;
    }

    public int getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
    }

    public boolean isFavouriteFlg() {
        return favouriteFlg;
    }

    public void setFavouriteFlg(boolean favouriteFlg) {
        this.favouriteFlg = favouriteFlg;
    }

    public boolean isIsverifiedOwner() {
        return isverifiedOwner;
    }

    public void setIsverifiedOwner(boolean isverifiedOwner) {
        this.isverifiedOwner = isverifiedOwner;
    }

    public List<Object> getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(List<Object> attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getDefaultAttachment() {
        return defaultAttachment;
    }

    public void setDefaultAttachment(int defaultAttachment) {
        this.defaultAttachment = defaultAttachment;
    }

    public boolean isTermsconditioncheck() {
        return termsconditioncheck;
    }

    public void setTermsconditioncheck(boolean termsconditioncheck) {
        this.termsconditioncheck = termsconditioncheck;
    }

    public int getRunHours() {
        return runHours;
    }

    public void setRunHours(int runHours) {
        this.runHours = runHours;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public boolean isShowDateInfoMsg() {
        return showDateInfoMsg;
    }

    public void setShowDateInfoMsg(boolean showDateInfoMsg) {
        this.showDateInfoMsg = showDateInfoMsg;
    }

    public boolean isAssignTowd() {
        return assignTowd;
    }

    public void setAssignTowd(boolean assignTowd) {
        this.assignTowd = assignTowd;
    }

    public boolean isAssoToplan() {
        return assoToplan;
    }

    public void setAssoToplan(boolean assoToplan) {
        this.assoToplan = assoToplan;
    }

    public boolean isDisableDeleteFlg() {
        return disableDeleteFlg;
    }

    public void setDisableDeleteFlg(boolean disableDeleteFlg) {
        this.disableDeleteFlg = disableDeleteFlg;
    }

}

