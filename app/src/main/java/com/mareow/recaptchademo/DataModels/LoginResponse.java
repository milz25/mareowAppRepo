package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

   /* @SerializedName("userId")
    @Expose
    private Integer userId;
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
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gstNumber")
    @Expose
    private Object gstNumber;
    @SerializedName("ifscCode")
    @Expose
    private Object ifscCode;
    @SerializedName("inactive")
    @Expose
    private Boolean inactive;
    @SerializedName("isVerified")
    @Expose
    private Integer isVerified;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refMobileNo")
    @Expose
    private Object refMobileNo;
    @SerializedName("referBy")
    @Expose
    private Object referBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleLogic")
    @Expose
    private String roleLogic;
    @SerializedName("partyId")
    @Expose
    private Integer partyId;
    @SerializedName("partyName")
    @Expose
    private String partyName;
    @SerializedName("partyCategoty")
    @Expose
    private String partyCategoty;
    @SerializedName("partyCategotyImg")
    @Expose
    private String partyCategotyImg;
    @SerializedName("segment")
    @Expose
    private List<String> segment = null;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
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

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLogic() {
        return roleLogic;
    }

    public void setRoleLogic(String roleLogic) {
        this.roleLogic = roleLogic;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyCategoty() {
        return partyCategoty;
    }

    public void setPartyCategoty(String partyCategoty) {
        this.partyCategoty = partyCategoty;
    }

    public String getPartyCategotyImg() {
        return partyCategotyImg;
    }

    public void setPartyCategotyImg(String partyCategotyImg) {
        this.partyCategotyImg = partyCategotyImg;
    }

    public List<String> getSegment() {
        return segment;
    }

    public void setSegment(List<String> segment) {
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

}*/

    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("aboutYourself")
    @Expose
    private String aboutYourself;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("accountType")
    @Expose
    private String accountType;
    @SerializedName("association")
    @Expose
    private Object association;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gstNumber")
    @Expose
    private Object gstNumber;
    @SerializedName("ifscCode")
    @Expose
    private Object ifscCode;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refMobileNo")
    @Expose
    private Object refMobileNo;
    @SerializedName("referBy")
    @Expose
    private Object referBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("roleId")
    @Expose
    private int roleId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleLogic")
    @Expose
    private String roleLogic;
    @SerializedName("partyId")
    @Expose
    private int partyId;
    @SerializedName("partyName")
    @Expose
    private String partyName;
    @SerializedName("partyCategoty")
    @Expose
    private String partyCategoty;
    @SerializedName("partyCategotyImg")
    @Expose
    private String partyCategotyImg;
    @SerializedName("segment")
    @Expose
    private List<String> segment = null;
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
    @SerializedName("verified")
    @Expose
    private boolean verified;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Object getAssociation() {
        return association;
    }

    public void setAssociation(Object association) {
        this.association = association;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
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

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLogic() {
        return roleLogic;
    }

    public void setRoleLogic(String roleLogic) {
        this.roleLogic = roleLogic;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyCategoty() {
        return partyCategoty;
    }

    public void setPartyCategoty(String partyCategoty) {
        this.partyCategoty = partyCategoty;
    }

    public String getPartyCategotyImg() {
        return partyCategotyImg;
    }

    public void setPartyCategotyImg(String partyCategotyImg) {
        this.partyCategotyImg = partyCategotyImg;
    }

    public List<String> getSegment() {
        return segment;
    }

    public void setSegment(List<String> segment) {
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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}

