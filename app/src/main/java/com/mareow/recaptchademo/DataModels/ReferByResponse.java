package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferByResponse {

    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("aboutYourself")
    @Expose
    private Object aboutYourself;
    @SerializedName("accountHolder")
    @Expose
    private Object accountHolder;
    @SerializedName("accountNo")
    @Expose
    private Object accountNo;
    @SerializedName("accountType")
    @Expose
    private Object accountType;
    @SerializedName("association")
    @Expose
    private Object association;
    @SerializedName("bank")
    @Expose
    private Object bank;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("gstNumber")
    @Expose
    private Object gstNumber;
    @SerializedName("ifscCode")
    @Expose
    private Object ifscCode;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("isVerified")
    @Expose
    private int isVerified;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("refMobileNo")
    @Expose
    private Object refMobileNo;
    @SerializedName("referBy")
    @Expose
    private Object referBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("userImage")
    @Expose
    private Object userImage;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("roleId")
    @Expose
    private int roleId;
    @SerializedName("roleName")
    @Expose
    private Object roleName;
    @SerializedName("roleLogic")
    @Expose
    private Object roleLogic;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("partyName")
    @Expose
    private Object partyName;
    @SerializedName("partyCategoty")
    @Expose
    private Object partyCategoty;
    @SerializedName("partyCategotyImg")
    @Expose
    private Object partyCategotyImg;
    @SerializedName("segment")
    @Expose
    private List<Object> segment = null;
    @SerializedName("catagory")
    @Expose
    private Object catagory;
    @SerializedName("subCategory")
    @Expose
    private Object subCategory;
    @SerializedName("manufacturer")
    @Expose
    private Object manufacturer;
    @SerializedName("machineModelNo")
    @Expose
    private List<Object> machineModelNo = null;
    @SerializedName("emailFlg")
    @Expose
    private boolean emailFlg;
    @SerializedName("notificationFlg")
    @Expose
    private boolean notificationFlg;
    @SerializedName("locationFlg")
    @Expose
    private boolean locationFlg;
    @SerializedName("chatFlg")
    @Expose
    private boolean chatFlg;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(Object aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public Object getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Object accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Object getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Object accountNo) {
        this.accountNo = accountNo;
    }

    public Object getAccountType() {
        return accountType;
    }

    public void setAccountType(Object accountType) {
        this.accountType = accountType;
    }

    public Object getAssociation() {
        return association;
    }

    public void setAssociation(Object association) {
        this.association = association;
    }

    public Object getBank() {
        return bank;
    }

    public void setBank(Object bank) {
        this.bank = bank;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(Object gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Object getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(Object ifscCode) {
        this.ifscCode = ifscCode;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getRefMobileNo() {
        return refMobileNo;
    }

    public void setRefMobileNo(Object refMobileNo) {
        this.refMobileNo = refMobileNo;
    }

    public Object getReferBy() {
        return referBy;
    }

    public void setReferBy(Object referBy) {
        this.referBy = referBy;
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

    public Object getUserImage() {
        return userImage;
    }

    public void setUserImage(Object userImage) {
        this.userImage = userImage;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Object getRoleName() {
        return roleName;
    }

    public void setRoleName(Object roleName) {
        this.roleName = roleName;
    }

    public Object getRoleLogic() {
        return roleLogic;
    }

    public void setRoleLogic(Object roleLogic) {
        this.roleLogic = roleLogic;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public Object getPartyName() {
        return partyName;
    }

    public void setPartyName(Object partyName) {
        this.partyName = partyName;
    }

    public Object getPartyCategoty() {
        return partyCategoty;
    }

    public void setPartyCategoty(Object partyCategoty) {
        this.partyCategoty = partyCategoty;
    }

    public Object getPartyCategotyImg() {
        return partyCategotyImg;
    }

    public void setPartyCategotyImg(Object partyCategotyImg) {
        this.partyCategotyImg = partyCategotyImg;
    }

    public List<Object> getSegment() {
        return segment;
    }

    public void setSegment(List<Object> segment) {
        this.segment = segment;
    }

    public Object getCatagory() {
        return catagory;
    }

    public void setCatagory(Object catagory) {
        this.catagory = catagory;
    }

    public Object getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Object subCategory) {
        this.subCategory = subCategory;
    }

    public Object getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Object manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Object> getMachineModelNo() {
        return machineModelNo;
    }

    public void setMachineModelNo(List<Object> machineModelNo) {
        this.machineModelNo = machineModelNo;
    }

    public boolean isEmailFlg() {
        return emailFlg;
    }

    public void setEmailFlg(boolean emailFlg) {
        this.emailFlg = emailFlg;
    }

    public boolean isNotificationFlg() {
        return notificationFlg;
    }

    public void setNotificationFlg(boolean notificationFlg) {
        this.notificationFlg = notificationFlg;
    }

    public boolean isLocationFlg() {
        return locationFlg;
    }

    public void setLocationFlg(boolean locationFlg) {
        this.locationFlg = locationFlg;
    }

    public boolean isChatFlg() {
        return chatFlg;
    }

    public void setChatFlg(boolean chatFlg) {
        this.chatFlg = chatFlg;
    }

}
