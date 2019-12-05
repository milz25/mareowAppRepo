package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateOwnerMachine {
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
    private List<Object> imagesPath = null;
    @SerializedName("imagesPathUrls")
    @Expose
    private List<Object> imagesPathUrls = null;
    @SerializedName("roadTaxCHK")
    @Expose
    private boolean roadTaxCHK;
    @SerializedName("insurance")
    @Expose
    private boolean insurance;
    @SerializedName("insuranceCompanyName")
    @Expose
    private String insuranceCompanyName;
    @SerializedName("insuranceEndDate")
    @Expose
    private String insuranceEndDate;
    @SerializedName("insuranceStartDate")
    @Expose
    private String insuranceStartDate;
    @SerializedName("insuranceType")
    @Expose
    private String insuranceType;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("machineAge")
    @Expose
    private String machineAge;
    @SerializedName("manufacturerYear")
    @Expose
    private String manufacturerYear;
    @SerializedName("registerDate")
    @Expose
    private String registerDate;
    @SerializedName("mcFitnessDate")
    @Expose
    private String mcFitnessDate;
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
    @SerializedName("lastIncClaim")
    @Expose
    private String lastIncClaim;
    @SerializedName("ncbPer")
    @Expose
    private int ncbPer;
    @SerializedName("futureWo")
    @Expose
    private boolean futureWo;
    @SerializedName("futureWoSDate")
    @Expose
    private String futureWoSDate;
    @SerializedName("futureWoEDate")
    @Expose
    private String futureWoEDate;
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

    public List<Object> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(List<Object> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public List<Object> getImagesPathUrls() {
        return imagesPathUrls;
    }

    public void setImagesPathUrls(List<Object> imagesPathUrls) {
        this.imagesPathUrls = imagesPathUrls;
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

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public String getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public void setInsuranceEndDate(String insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }

    public String getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public void setInsuranceStartDate(String insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
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

    public String getMachineAge() {
        return machineAge;
    }

    public void setMachineAge(String machineAge) {
        this.machineAge = machineAge;
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

    public String getMcFitnessDate() {
        return mcFitnessDate;
    }

    public void setMcFitnessDate(String mcFitnessDate) {
        this.mcFitnessDate = mcFitnessDate;
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

    public String getLastIncClaim() {
        return lastIncClaim;
    }

    public void setLastIncClaim(String lastIncClaim) {
        this.lastIncClaim = lastIncClaim;
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

    public String getFutureWoSDate() {
        return futureWoSDate;
    }

    public void setFutureWoSDate(String futureWoSDate) {
        this.futureWoSDate = futureWoSDate;
    }

    public String getFutureWoEDate() {
        return futureWoEDate;
    }

    public void setFutureWoEDate(String futureWoEDate) {
        this.futureWoEDate = futureWoEDate;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

}
