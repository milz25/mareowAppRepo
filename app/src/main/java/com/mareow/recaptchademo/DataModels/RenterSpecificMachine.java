package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RenterSpecificMachine implements Serializable {

    @SerializedName("machineId")
    @Expose
    private int machineId;
    @SerializedName("machineName")
    @Expose
    private String machineName;
    @SerializedName("availibilityDate")
    @Expose
    private String availibilityDate;
    @SerializedName("machineDesc")
    @Expose
    private String machineDesc;
    @SerializedName("currentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("rcbookCHK")
    @Expose
    private boolean rcbookCHK;
    @SerializedName("chessisNo")
    @Expose
    private String chessisNo;
    @SerializedName("currentPostalCode")
    @Expose
    private String currentPostalCode;
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
    @SerializedName("manufacturerYear")
    @Expose
    private String manufacturerYear;
    @SerializedName("registerNo")
    @Expose
    private String registerNo;
    @SerializedName("segmentCode")
    @Expose
    private String segmentCode;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryCode")
    @Expose
    private String categoryCode;
    @SerializedName("subCategory")
    @Expose
    private int subCategory;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("subCategoryCode")
    @Expose
    private String subCategoryCode;
    @SerializedName("manufacturerId")
    @Expose
    private int manufacturerId;
    @SerializedName("manufacturerName")
    @Expose
    private String manufacturerName;
    @SerializedName("manufacturerCode")
    @Expose
    private String manufacturerCode;
    @SerializedName("loadCapacity")
    @Expose
    private String loadCapacity;
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
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("engineNo")
    @Expose
    private String engineNo;
    @SerializedName("isverified")
    @Expose
    private boolean isverified;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("ownership")
    @Expose
    private String ownership;
    @SerializedName("imagesPath")
    @Expose
    private List<String> imagesPath = null;
    @SerializedName("imagesPathUrls")
    @Expose
    private ImagesPathUrls imagesPathUrls = null;
    @SerializedName("viewImagePathUrls")
    @Expose
    private List<String> viewImagePathUrls = null;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("favouriteId")
    @Expose
    private int favouriteId;
    @SerializedName("favouriteFlg")
    @Expose
    private boolean favouriteFlg;
    @SerializedName("ownerLocation")
    @Expose
    private String ownerLocation;
    @SerializedName("ownerCreatedDateStr")
    @Expose
    private String ownerCreatedDateStr;
    @SerializedName("isverifiedOwner")
    @Expose
    private boolean isverifiedOwner;
    @SerializedName("userCategory")
    @Expose
    private String userCategory;
    @SerializedName("attachmentId")
    @Expose
    private List<String> attachmentId = null;
    @SerializedName("defaultAttachment")
    @Expose
    private int defaultAttachment;
    @SerializedName("termsconditioncheck")
    @Expose
    private boolean termsconditioncheck;
    @SerializedName("availabelstatus")
    @Expose
    private String availabelstatus;
    @SerializedName("availabelstatusCode")
    @Expose
    private String availabelstatusCode;
    @SerializedName("usageflg")
    @Expose
    private String usageflg;
    @SerializedName("runHours")
    @Expose
    private float runHours;
    @SerializedName("odometer")
    @Expose
    private int odometer;
    @SerializedName("machineReviewDTO")
    @Expose
    private MachineReviewDTO machineReviewDTO;
    @SerializedName("showDateInfoMsg")
    @Expose
    private boolean showDateInfoMsg;
    @SerializedName("assignTowd")
    @Expose
    private boolean assignTowd;
    @SerializedName("assoToplan")
    @Expose
    private boolean assoToplan;

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

    public String getAvailibilityDate() {
        return availibilityDate;
    }

    public void setAvailibilityDate(String availibilityDate) {
        this.availibilityDate = availibilityDate;
    }

    public String getMachineDesc() {
        return machineDesc;
    }

    public void setMachineDesc(String machineDesc) {
        this.machineDesc = machineDesc;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isRcbookCHK() {
        return rcbookCHK;
    }

    public void setRcbookCHK(boolean rcbookCHK) {
        this.rcbookCHK = rcbookCHK;
    }

    public String getChessisNo() {
        return chessisNo;
    }

    public void setChessisNo(String chessisNo) {
        this.chessisNo = chessisNo;
    }

    public String getCurrentPostalCode() {
        return currentPostalCode;
    }

    public void setCurrentPostalCode(String currentPostalCode) {
        this.currentPostalCode = currentPostalCode;
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

    public String getManufacturerYear() {
        return manufacturerYear;
    }

    public void setManufacturerYear(String manufacturerYear) {
        this.manufacturerYear = manufacturerYear;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public void setSubCategoryCode(String subCategoryCode) {
        this.subCategoryCode = subCategoryCode;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(String loadCapacity) {
        this.loadCapacity = loadCapacity;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public boolean isIsverified() {
        return isverified;
    }

    public void setIsverified(boolean isverified) {
        this.isverified = isverified;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public List<String> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(List<String> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public ImagesPathUrls getImagesPathUrls() {
        return imagesPathUrls;
    }

    public void setImagesPathUrls(ImagesPathUrls imagesPathUrls) {
        this.imagesPathUrls = imagesPathUrls;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getOwnerLocation() {
        return ownerLocation;
    }

    public void setOwnerLocation(String ownerLocation) {
        this.ownerLocation = ownerLocation;
    }

    public String getOwnerCreatedDateStr() {
        return ownerCreatedDateStr;
    }

    public void setOwnerCreatedDateStr(String ownerCreatedDateStr) {
        this.ownerCreatedDateStr = ownerCreatedDateStr;
    }

    public boolean isIsverifiedOwner() {
        return isverifiedOwner;
    }

    public void setIsverifiedOwner(boolean isverifiedOwner) {
        this.isverifiedOwner = isverifiedOwner;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public List<String> getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(List<String> attachmentId) {
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

    public String getAvailabelstatus() {
        return availabelstatus;
    }

    public void setAvailabelstatus(String availabelstatus) {
        this.availabelstatus = availabelstatus;
    }

    public String getAvailabelstatusCode() {
        return availabelstatusCode;
    }

    public void setAvailabelstatusCode(String availabelstatusCode) {
        this.availabelstatusCode = availabelstatusCode;
    }

    public String getUsageflg() {
        return usageflg;
    }

    public void setUsageflg(String usageflg) {
        this.usageflg = usageflg;
    }

    public float getRunHours() {
        return runHours;
    }

    public void setRunHours(float runHours) {
        this.runHours = runHours;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public MachineReviewDTO getMachineReviewDTO() {
        return machineReviewDTO;
    }

    public void setMachineReviewDTO(MachineReviewDTO machineReviewDTO) {
        this.machineReviewDTO = machineReviewDTO;
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

    public List<String> getViewImagePathUrls() {
        return viewImagePathUrls;
    }

    public void setViewImagePathUrls(List<String> viewImagePathUrls) {
        this.viewImagePathUrls = viewImagePathUrls;
    }
}
