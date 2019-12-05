package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatedWorkOrder {

    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("noOfSupervisor")
    @Expose
    private int noOfSupervisor;
    @SerializedName("accomodation")
    @Expose
    private boolean accomodation;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("cretatedDate")
    @Expose
    private Object cretatedDate;
    @SerializedName("food")
    @Expose
    private boolean food;
    @SerializedName("transportation")
    @Expose
    private boolean transportation;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("machineBookDate")
    @Expose
    private Object machineBookDate;
    @SerializedName("noOfOperator")
    @Expose
    private int noOfOperator;
    @SerializedName("operatorName")
    @Expose
    private Object operatorName;
    @SerializedName("supervisorName")
    @Expose
    private Object supervisorName;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("ownerId")
    @Expose
    private int ownerId;
    @SerializedName("renterId")
    @Expose
    private int renterId;
    @SerializedName("operatorId")
    @Expose
    private int operatorId;
    @SerializedName("supervisorId")
    @Expose
    private int supervisorId;
    @SerializedName("machineDetailId")
    @Expose
    private int machineDetailId;
    @SerializedName("registrationNo")
    @Expose
    private int registrationNo;
    @SerializedName("capacity")
    @Expose
    private Object capacity;
    @SerializedName("currentLocation")
    @Expose
    private Object currentLocation;
    @SerializedName("openingKmr")
    @Expose
    private int openingKmr;
    @SerializedName("machineDesc")
    @Expose
    private Object machineDesc;
    @SerializedName("igst")
    @Expose
    private boolean igst;
    @SerializedName("planId")
    @Expose
    private int planId;
    @SerializedName("planName")
    @Expose
    private Object planName;
    @SerializedName("planType")
    @Expose
    private Object planType;
    @SerializedName("planUsage")
    @Expose
    private Object planUsage;
    @SerializedName("planDetailId")
    @Expose
    private int planDetailId;
    @SerializedName("extraAttachmentFixedRate")
    @Expose
    private int extraAttachmentFixedRate;
    @SerializedName("extraAttachmentHourlyRate")
    @Expose
    private int extraAttachmentHourlyRate;
    @SerializedName("extraAttachmentFixedRateFlg")
    @Expose
    private boolean extraAttachmentFixedRateFlg;
    @SerializedName("extraAttachmentHourlyRateFlg")
    @Expose
    private boolean extraAttachmentHourlyRateFlg;
    @SerializedName("statusId")
    @Expose
    private int statusId;
    @SerializedName("siteLocation")
    @Expose
    private Object siteLocation;
    @SerializedName("openingHmr")
    @Expose
    private int openingHmr;
    @SerializedName("remarksofQCELorNACIEL")
    @Expose
    private Object remarksofQCELorNACIEL;
    @SerializedName("commitmentMonth")
    @Expose
    private int commitmentMonth;
    @SerializedName("shift")
    @Expose
    private Object shift;
    @SerializedName("load")
    @Expose
    private Object load;
    @SerializedName("monthlyHours")
    @Expose
    private int monthlyHours;
    @SerializedName("hourlyRate")
    @Expose
    private int hourlyRate;
    @SerializedName("gst")
    @Expose
    private boolean gst;
    @SerializedName("days")
    @Expose
    private int days;
    @SerializedName("basicRate")
    @Expose
    private int basicRate;
    @SerializedName("overtime")
    @Expose
    private int overtime;
    @SerializedName("noOfmachineOperator")
    @Expose
    private int noOfmachineOperator;
    @SerializedName("accommodation")
    @Expose
    private boolean accommodation;
    @SerializedName("demobilityFixedAmt")
    @Expose
    private int demobilityFixedAmt;
    @SerializedName("demobilityResponsible")
    @Expose
    private Object demobilityResponsible;
    @SerializedName("mobilityFixedAmt")
    @Expose
    private int mobilityFixedAmt;
    @SerializedName("mobilityPerKmRate")
    @Expose
    private int mobilityPerKmRate;
    @SerializedName("demobilityPerKmRate")
    @Expose
    private int demobilityPerKmRate;
    @SerializedName("mobilityResponsible")
    @Expose
    private Object mobilityResponsible;
    @SerializedName("mobilityAmt")
    @Expose
    private int mobilityAmt;
    @SerializedName("mobilityAmtFlg")
    @Expose
    private boolean mobilityAmtFlg;
    @SerializedName("demobilityAmt")
    @Expose
    private int demobilityAmt;
    @SerializedName("demobilityAmtFlg")
    @Expose
    private boolean demobilityAmtFlg;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("tncCategory")
    @Expose
    private Object tncCategory;
    @SerializedName("tncType")
    @Expose
    private Object tncType;
    @SerializedName("planStartDate")
    @Expose
    private Object planStartDate;
    @SerializedName("planEndDate")
    @Expose
    private Object planEndDate;
    @SerializedName("planDays")
    @Expose
    private int planDays;
    @SerializedName("planHours")
    @Expose
    private int planHours;
    @SerializedName("actualStartDate")
    @Expose
    private Object actualStartDate;
    @SerializedName("actualEndDate")
    @Expose
    private Object actualEndDate;
    @SerializedName("actualDays")
    @Expose
    private int actualDays;
    @SerializedName("actualHours")
    @Expose
    private int actualHours;
    @SerializedName("workOrderStatus")
    @Expose
    private String workOrderStatus;
    @SerializedName("workOrderStatusMeaning")
    @Expose
    private Object workOrderStatusMeaning;
    @SerializedName("planStartDateStr")
    @Expose
    private Object planStartDateStr;
    @SerializedName("planEndDateStr")
    @Expose
    private Object planEndDateStr;
    @SerializedName("actualStartDateStr")
    @Expose
    private Object actualStartDateStr;
    @SerializedName("actualEndDateStr")
    @Expose
    private Object actualEndDateStr;
    @SerializedName("machine")
    @Expose
    private Object machine;
    @SerializedName("workOrderNo")
    @Expose
    private Object workOrderNo;
    @SerializedName("workStartDate")
    @Expose
    private String workStartDate;
    @SerializedName("workStartDateStr")
    @Expose
    private String workStartDateStr;
    @SerializedName("workEndDate")
    @Expose
    private Object workEndDate;
    @SerializedName("workEndDateStr")
    @Expose
    private Object workEndDateStr;
    @SerializedName("operatorStartDate")
    @Expose
    private Object operatorStartDate;
    @SerializedName("operatorStartDateStr")
    @Expose
    private Object operatorStartDateStr;
    @SerializedName("operatorEndDate")
    @Expose
    private Object operatorEndDate;
    @SerializedName("operatorEndDateStr")
    @Expose
    private Object operatorEndDateStr;
    @SerializedName("operatorHours")
    @Expose
    private int operatorHours;
    @SerializedName("supervisorStartDate")
    @Expose
    private Object supervisorStartDate;
    @SerializedName("supervisorStartDateStr")
    @Expose
    private Object supervisorStartDateStr;
    @SerializedName("supervisorEndDate")
    @Expose
    private Object supervisorEndDate;
    @SerializedName("supervisorEndDateStr")
    @Expose
    private Object supervisorEndDateStr;
    @SerializedName("supervisorHours")
    @Expose
    private int supervisorHours;
    @SerializedName("machineOwnerId")
    @Expose
    private int machineOwnerId;
    @SerializedName("statusCode")
    @Expose
    private Object statusCode;
    @SerializedName("operatorDays")
    @Expose
    private int operatorDays;
    @SerializedName("supervisorDays")
    @Expose
    private int supervisorDays;
    @SerializedName("operatorFlg")
    @Expose
    private boolean operatorFlg;
    @SerializedName("agreementFlg")
    @Expose
    private boolean agreementFlg;
    @SerializedName("termsDTO")
    @Expose
    private Object termsDTO;
    @SerializedName("planDescription")
    @Expose
    private Object planDescription;
    @SerializedName("operatorRate")
    @Expose
    private Object operatorRate;
    @SerializedName("operatorFixRateFlg")
    @Expose
    private boolean operatorFixRateFlg;
    @SerializedName("noOfDays")
    @Expose
    private int noOfDays;
    @SerializedName("reqHour")
    @Expose
    private int reqHour;
    @SerializedName("cgstAmt")
    @Expose
    private float cgstAmt;
    @SerializedName("sgstAmt")
    @Expose
    private float sgstAmt;
    @SerializedName("igstAmt")
    @Expose
    private float igstAmt;
    @SerializedName("workOrderEstimateAmount")
    @Expose
    private float workOrderEstimateAmount;
    @SerializedName("activityLogDTOs")
    @Expose
    private Object activityLogDTOs;
    @SerializedName("workOrderOwner")
    @Expose
    private Object workOrderOwner;
    @SerializedName("workOrderRenter")
    @Expose
    private Object workOrderRenter;
    @SerializedName("workPostalCode")
    @Expose
    private Object workPostalCode;
    @SerializedName("extendStatus")
    @Expose
    private Object extendStatus;
    @SerializedName("cancelStatus")
    @Expose
    private Object cancelStatus;
    @SerializedName("woextendFlg")
    @Expose
    private boolean woextendFlg;
    @SerializedName("woCancelFlg")
    @Expose
    private boolean woCancelFlg;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getNoOfSupervisor() {
        return noOfSupervisor;
    }

    public void setNoOfSupervisor(int noOfSupervisor) {
        this.noOfSupervisor = noOfSupervisor;
    }

    public boolean isAccomodation() {
        return accomodation;
    }

    public void setAccomodation(boolean accomodation) {
        this.accomodation = accomodation;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCretatedDate() {
        return cretatedDate;
    }

    public void setCretatedDate(Object cretatedDate) {
        this.cretatedDate = cretatedDate;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Object getMachineBookDate() {
        return machineBookDate;
    }

    public void setMachineBookDate(Object machineBookDate) {
        this.machineBookDate = machineBookDate;
    }

    public int getNoOfOperator() {
        return noOfOperator;
    }

    public void setNoOfOperator(int noOfOperator) {
        this.noOfOperator = noOfOperator;
    }

    public Object getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(Object operatorName) {
        this.operatorName = operatorName;
    }

    public Object getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(Object supervisorName) {
        this.supervisorName = supervisorName;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getMachineDetailId() {
        return machineDetailId;
    }

    public void setMachineDetailId(int machineDetailId) {
        this.machineDetailId = machineDetailId;
    }

    public int getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(int registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Object getCapacity() {
        return capacity;
    }

    public void setCapacity(Object capacity) {
        this.capacity = capacity;
    }

    public Object getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Object currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getOpeningKmr() {
        return openingKmr;
    }

    public void setOpeningKmr(int openingKmr) {
        this.openingKmr = openingKmr;
    }

    public Object getMachineDesc() {
        return machineDesc;
    }

    public void setMachineDesc(Object machineDesc) {
        this.machineDesc = machineDesc;
    }

    public boolean isIgst() {
        return igst;
    }

    public void setIgst(boolean igst) {
        this.igst = igst;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public Object getPlanName() {
        return planName;
    }

    public void setPlanName(Object planName) {
        this.planName = planName;
    }

    public Object getPlanType() {
        return planType;
    }

    public void setPlanType(Object planType) {
        this.planType = planType;
    }

    public Object getPlanUsage() {
        return planUsage;
    }

    public void setPlanUsage(Object planUsage) {
        this.planUsage = planUsage;
    }

    public int getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(int planDetailId) {
        this.planDetailId = planDetailId;
    }

    public int getExtraAttachmentFixedRate() {
        return extraAttachmentFixedRate;
    }

    public void setExtraAttachmentFixedRate(int extraAttachmentFixedRate) {
        this.extraAttachmentFixedRate = extraAttachmentFixedRate;
    }

    public int getExtraAttachmentHourlyRate() {
        return extraAttachmentHourlyRate;
    }

    public void setExtraAttachmentHourlyRate(int extraAttachmentHourlyRate) {
        this.extraAttachmentHourlyRate = extraAttachmentHourlyRate;
    }

    public boolean isExtraAttachmentFixedRateFlg() {
        return extraAttachmentFixedRateFlg;
    }

    public void setExtraAttachmentFixedRateFlg(boolean extraAttachmentFixedRateFlg) {
        this.extraAttachmentFixedRateFlg = extraAttachmentFixedRateFlg;
    }

    public boolean isExtraAttachmentHourlyRateFlg() {
        return extraAttachmentHourlyRateFlg;
    }

    public void setExtraAttachmentHourlyRateFlg(boolean extraAttachmentHourlyRateFlg) {
        this.extraAttachmentHourlyRateFlg = extraAttachmentHourlyRateFlg;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Object getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(Object siteLocation) {
        this.siteLocation = siteLocation;
    }

    public int getOpeningHmr() {
        return openingHmr;
    }

    public void setOpeningHmr(int openingHmr) {
        this.openingHmr = openingHmr;
    }

    public Object getRemarksofQCELorNACIEL() {
        return remarksofQCELorNACIEL;
    }

    public void setRemarksofQCELorNACIEL(Object remarksofQCELorNACIEL) {
        this.remarksofQCELorNACIEL = remarksofQCELorNACIEL;
    }

    public int getCommitmentMonth() {
        return commitmentMonth;
    }

    public void setCommitmentMonth(int commitmentMonth) {
        this.commitmentMonth = commitmentMonth;
    }

    public Object getShift() {
        return shift;
    }

    public void setShift(Object shift) {
        this.shift = shift;
    }

    public Object getLoad() {
        return load;
    }

    public void setLoad(Object load) {
        this.load = load;
    }

    public int getMonthlyHours() {
        return monthlyHours;
    }

    public void setMonthlyHours(int monthlyHours) {
        this.monthlyHours = monthlyHours;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean isGst() {
        return gst;
    }

    public void setGst(boolean gst) {
        this.gst = gst;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(int basicRate) {
        this.basicRate = basicRate;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getNoOfmachineOperator() {
        return noOfmachineOperator;
    }

    public void setNoOfmachineOperator(int noOfmachineOperator) {
        this.noOfmachineOperator = noOfmachineOperator;
    }

    public boolean isAccommodation() {
        return accommodation;
    }

    public void setAccommodation(boolean accommodation) {
        this.accommodation = accommodation;
    }

    public int getDemobilityFixedAmt() {
        return demobilityFixedAmt;
    }

    public void setDemobilityFixedAmt(int demobilityFixedAmt) {
        this.demobilityFixedAmt = demobilityFixedAmt;
    }

    public Object getDemobilityResponsible() {
        return demobilityResponsible;
    }

    public void setDemobilityResponsible(Object demobilityResponsible) {
        this.demobilityResponsible = demobilityResponsible;
    }

    public int getMobilityFixedAmt() {
        return mobilityFixedAmt;
    }

    public void setMobilityFixedAmt(int mobilityFixedAmt) {
        this.mobilityFixedAmt = mobilityFixedAmt;
    }

    public int getMobilityPerKmRate() {
        return mobilityPerKmRate;
    }

    public void setMobilityPerKmRate(int mobilityPerKmRate) {
        this.mobilityPerKmRate = mobilityPerKmRate;
    }

    public int getDemobilityPerKmRate() {
        return demobilityPerKmRate;
    }

    public void setDemobilityPerKmRate(int demobilityPerKmRate) {
        this.demobilityPerKmRate = demobilityPerKmRate;
    }

    public Object getMobilityResponsible() {
        return mobilityResponsible;
    }

    public void setMobilityResponsible(Object mobilityResponsible) {
        this.mobilityResponsible = mobilityResponsible;
    }

    public int getMobilityAmt() {
        return mobilityAmt;
    }

    public void setMobilityAmt(int mobilityAmt) {
        this.mobilityAmt = mobilityAmt;
    }

    public boolean isMobilityAmtFlg() {
        return mobilityAmtFlg;
    }

    public void setMobilityAmtFlg(boolean mobilityAmtFlg) {
        this.mobilityAmtFlg = mobilityAmtFlg;
    }

    public int getDemobilityAmt() {
        return demobilityAmt;
    }

    public void setDemobilityAmt(int demobilityAmt) {
        this.demobilityAmt = demobilityAmt;
    }

    public boolean isDemobilityAmtFlg() {
        return demobilityAmtFlg;
    }

    public void setDemobilityAmtFlg(boolean demobilityAmtFlg) {
        this.demobilityAmtFlg = demobilityAmtFlg;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getTncCategory() {
        return tncCategory;
    }

    public void setTncCategory(Object tncCategory) {
        this.tncCategory = tncCategory;
    }

    public Object getTncType() {
        return tncType;
    }

    public void setTncType(Object tncType) {
        this.tncType = tncType;
    }

    public Object getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Object planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Object getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Object planEndDate) {
        this.planEndDate = planEndDate;
    }

    public int getPlanDays() {
        return planDays;
    }

    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }

    public int getPlanHours() {
        return planHours;
    }

    public void setPlanHours(int planHours) {
        this.planHours = planHours;
    }

    public Object getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Object actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Object getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Object actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public int getActualDays() {
        return actualDays;
    }

    public void setActualDays(int actualDays) {
        this.actualDays = actualDays;
    }

    public int getActualHours() {
        return actualHours;
    }

    public void setActualHours(int actualHours) {
        this.actualHours = actualHours;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Object getWorkOrderStatusMeaning() {
        return workOrderStatusMeaning;
    }

    public void setWorkOrderStatusMeaning(Object workOrderStatusMeaning) {
        this.workOrderStatusMeaning = workOrderStatusMeaning;
    }

    public Object getPlanStartDateStr() {
        return planStartDateStr;
    }

    public void setPlanStartDateStr(Object planStartDateStr) {
        this.planStartDateStr = planStartDateStr;
    }

    public Object getPlanEndDateStr() {
        return planEndDateStr;
    }

    public void setPlanEndDateStr(Object planEndDateStr) {
        this.planEndDateStr = planEndDateStr;
    }

    public Object getActualStartDateStr() {
        return actualStartDateStr;
    }

    public void setActualStartDateStr(Object actualStartDateStr) {
        this.actualStartDateStr = actualStartDateStr;
    }

    public Object getActualEndDateStr() {
        return actualEndDateStr;
    }

    public void setActualEndDateStr(Object actualEndDateStr) {
        this.actualEndDateStr = actualEndDateStr;
    }

    public Object getMachine() {
        return machine;
    }

    public void setMachine(Object machine) {
        this.machine = machine;
    }

    public Object getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(Object workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }

    public String getWorkStartDateStr() {
        return workStartDateStr;
    }

    public void setWorkStartDateStr(String workStartDateStr) {
        this.workStartDateStr = workStartDateStr;
    }

    public Object getWorkEndDate() {
        return workEndDate;
    }

    public void setWorkEndDate(Object workEndDate) {
        this.workEndDate = workEndDate;
    }

    public Object getWorkEndDateStr() {
        return workEndDateStr;
    }

    public void setWorkEndDateStr(Object workEndDateStr) {
        this.workEndDateStr = workEndDateStr;
    }

    public Object getOperatorStartDate() {
        return operatorStartDate;
    }

    public void setOperatorStartDate(Object operatorStartDate) {
        this.operatorStartDate = operatorStartDate;
    }

    public Object getOperatorStartDateStr() {
        return operatorStartDateStr;
    }

    public void setOperatorStartDateStr(Object operatorStartDateStr) {
        this.operatorStartDateStr = operatorStartDateStr;
    }

    public Object getOperatorEndDate() {
        return operatorEndDate;
    }

    public void setOperatorEndDate(Object operatorEndDate) {
        this.operatorEndDate = operatorEndDate;
    }

    public Object getOperatorEndDateStr() {
        return operatorEndDateStr;
    }

    public void setOperatorEndDateStr(Object operatorEndDateStr) {
        this.operatorEndDateStr = operatorEndDateStr;
    }

    public int getOperatorHours() {
        return operatorHours;
    }

    public void setOperatorHours(int operatorHours) {
        this.operatorHours = operatorHours;
    }

    public Object getSupervisorStartDate() {
        return supervisorStartDate;
    }

    public void setSupervisorStartDate(Object supervisorStartDate) {
        this.supervisorStartDate = supervisorStartDate;
    }

    public Object getSupervisorStartDateStr() {
        return supervisorStartDateStr;
    }

    public void setSupervisorStartDateStr(Object supervisorStartDateStr) {
        this.supervisorStartDateStr = supervisorStartDateStr;
    }

    public Object getSupervisorEndDate() {
        return supervisorEndDate;
    }

    public void setSupervisorEndDate(Object supervisorEndDate) {
        this.supervisorEndDate = supervisorEndDate;
    }

    public Object getSupervisorEndDateStr() {
        return supervisorEndDateStr;
    }

    public void setSupervisorEndDateStr(Object supervisorEndDateStr) {
        this.supervisorEndDateStr = supervisorEndDateStr;
    }

    public int getSupervisorHours() {
        return supervisorHours;
    }

    public void setSupervisorHours(int supervisorHours) {
        this.supervisorHours = supervisorHours;
    }

    public int getMachineOwnerId() {
        return machineOwnerId;
    }

    public void setMachineOwnerId(int machineOwnerId) {
        this.machineOwnerId = machineOwnerId;
    }

    public Object getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Object statusCode) {
        this.statusCode = statusCode;
    }

    public int getOperatorDays() {
        return operatorDays;
    }

    public void setOperatorDays(int operatorDays) {
        this.operatorDays = operatorDays;
    }

    public int getSupervisorDays() {
        return supervisorDays;
    }

    public void setSupervisorDays(int supervisorDays) {
        this.supervisorDays = supervisorDays;
    }

    public boolean isOperatorFlg() {
        return operatorFlg;
    }

    public void setOperatorFlg(boolean operatorFlg) {
        this.operatorFlg = operatorFlg;
    }

    public boolean isAgreementFlg() {
        return agreementFlg;
    }

    public void setAgreementFlg(boolean agreementFlg) {
        this.agreementFlg = agreementFlg;
    }

    public Object getTermsDTO() {
        return termsDTO;
    }

    public void setTermsDTO(Object termsDTO) {
        this.termsDTO = termsDTO;
    }

    public Object getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(Object planDescription) {
        this.planDescription = planDescription;
    }

    public Object getOperatorRate() {
        return operatorRate;
    }

    public void setOperatorRate(Object operatorRate) {
        this.operatorRate = operatorRate;
    }

    public boolean isOperatorFixRateFlg() {
        return operatorFixRateFlg;
    }

    public void setOperatorFixRateFlg(boolean operatorFixRateFlg) {
        this.operatorFixRateFlg = operatorFixRateFlg;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public int getReqHour() {
        return reqHour;
    }

    public void setReqHour(int reqHour) {
        this.reqHour = reqHour;
    }

    public float getCgstAmt() {
        return cgstAmt;
    }

    public void setCgstAmt(float cgstAmt) {
        this.cgstAmt = cgstAmt;
    }

    public float getSgstAmt() {
        return sgstAmt;
    }

    public void setSgstAmt(float sgstAmt) {
        this.sgstAmt = sgstAmt;
    }

    public float getIgstAmt() {
        return igstAmt;
    }

    public void setIgstAmt(float igstAmt) {
        this.igstAmt = igstAmt;
    }

    public float getWorkOrderEstimateAmount() {
        return workOrderEstimateAmount;
    }

    public void setWorkOrderEstimateAmount(float workOrderEstimateAmount) {
        this.workOrderEstimateAmount = workOrderEstimateAmount;
    }

    public Object getActivityLogDTOs() {
        return activityLogDTOs;
    }

    public void setActivityLogDTOs(Object activityLogDTOs) {
        this.activityLogDTOs = activityLogDTOs;
    }

    public Object getWorkOrderOwner() {
        return workOrderOwner;
    }

    public void setWorkOrderOwner(Object workOrderOwner) {
        this.workOrderOwner = workOrderOwner;
    }

    public Object getWorkOrderRenter() {
        return workOrderRenter;
    }

    public void setWorkOrderRenter(Object workOrderRenter) {
        this.workOrderRenter = workOrderRenter;
    }

    public Object getWorkPostalCode() {
        return workPostalCode;
    }

    public void setWorkPostalCode(Object workPostalCode) {
        this.workPostalCode = workPostalCode;
    }

    public Object getExtendStatus() {
        return extendStatus;
    }

    public void setExtendStatus(Object extendStatus) {
        this.extendStatus = extendStatus;
    }

    public Object getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Object cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public boolean isWoextendFlg() {
        return woextendFlg;
    }

    public void setWoextendFlg(boolean woextendFlg) {
        this.woextendFlg = woextendFlg;
    }

    public boolean isWoCancelFlg() {
        return woCancelFlg;
    }

    public void setWoCancelFlg(boolean woCancelFlg) {
        this.woCancelFlg = woCancelFlg;
    }

}
