package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateMachine {
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
    @SerializedName("nationPermitCHK")
    @Expose
    private boolean nationPermitCHK;
    @SerializedName("pucCertifiacteCHK")
    @Expose
    private boolean pucCertifiacteCHK;
    @SerializedName("imagesPath")
    @Expose
    private List<String> imagesPath = null;
    @SerializedName("imagesPathUrls")
    @Expose
    private ImagesPathUrls imagesPathUrls;
    @SerializedName("viewImagePathUrls")
    @Expose
    private List<Object> viewImagePathUrls = null;
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
    @SerializedName("registerDate")
    @Expose
    private String registerDate;
    @SerializedName("registerNo")
    @Expose
    private String registerNo;
    @SerializedName("segmentCode")
    @Expose
    private String segmentCode;
    @SerializedName("categoryCode")
    @Expose
    private String categoryCode;
    @SerializedName("subCategoryCode")
    @Expose
    private String subCategoryCode;
    @SerializedName("manufacturerCode")
    @Expose
    private String manufacturerCode;
    @SerializedName("loadCapacityCode")
    @Expose
    private String loadCapacityCode;
    @SerializedName("machineSpec")
    @Expose
    private String machineSpec;
    @SerializedName("machineModelId")
    @Expose
    private int machineModelId;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("currentPostalCode")
    @Expose
    private String currentPostalCode;
    @SerializedName("editmachine")
    @Expose
    private boolean editmachine;
    @SerializedName("engineNo")
    @Expose
    private String engineNo;
    @SerializedName("chessisNo")
    @Expose
    private String chessisNo;
    @SerializedName("uniqueNo")
    @Expose
    private String uniqueNo;
    @SerializedName("attachmentId")
    @Expose
    private List<Integer> attachmentId = null;
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
    @SerializedName("ncbPer")
    @Expose
    private int ncbPer;
    @SerializedName("futureWo")
    @Expose
    private boolean futureWo;
    @SerializedName("ownership")
    @Expose
    private String ownership;

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

    public List<Object> getViewImagePathUrls() {
        return viewImagePathUrls;
    }

    public void setViewImagePathUrls(List<Object> viewImagePathUrls) {
        this.viewImagePathUrls = viewImagePathUrls;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public void setSubCategoryCode(String subCategoryCode) {
        this.subCategoryCode = subCategoryCode;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getLoadCapacityCode() {
        return loadCapacityCode;
    }

    public void setLoadCapacityCode(String loadCapacityCode) {
        this.loadCapacityCode = loadCapacityCode;
    }

    public String getMachineSpec() {
        return machineSpec;
    }

    public void setMachineSpec(String machineSpec) {
        this.machineSpec = machineSpec;
    }

    public int getMachineModelId() {
        return machineModelId;
    }

    public void setMachineModelId(int machineModelId) {
        this.machineModelId = machineModelId;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getCurrentPostalCode() {
        return currentPostalCode;
    }

    public void setCurrentPostalCode(String currentPostalCode) {
        this.currentPostalCode = currentPostalCode;
    }

    public boolean isEditmachine() {
        return editmachine;
    }

    public void setEditmachine(boolean editmachine) {
        this.editmachine = editmachine;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChessisNo() {
        return chessisNo;
    }

    public void setChessisNo(String chessisNo) {
        this.chessisNo = chessisNo;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public List<Integer> getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(List<Integer> attachmentId) {
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

    public int getNcbPer() {
        return ncbPer;
    }

    public void setNcbPer(int ncbPer) {
        this.ncbPer = ncbPer;
    }

    public boolean isFutureWo() {
        return futureWo;
    }

    public void setFutureWo(boolean futureWo) {
        this.futureWo = futureWo;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }


}
