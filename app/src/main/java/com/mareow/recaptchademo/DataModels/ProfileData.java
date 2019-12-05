package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileData {

    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("paytmAccount")
    @Expose
    private String paytmAccount;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @SerializedName("accountType")
    @Expose
    private String accountType;
    @SerializedName("segment")
    @Expose
    private List<String> segment = null;
    @SerializedName("runAbility")
    @Expose
    private List<Object> runAbility = null;

    @SerializedName("association")
    @Expose
    private String association;
    @SerializedName("associatedTo")
    @Expose
    private String associatedTo;
    @SerializedName("govtProofId")
    @Expose
    private String govtProofId;
    @SerializedName("runCertificatePath")
    @Expose
    private String runCertificatePath;
    @SerializedName("machineCredentialsPath")
    @Expose
    private String machineCredentialsPath;
    @SerializedName("govtProofDocPath")
    @Expose
    private String govtProofDocPath;
    @SerializedName("gstDocumentPath")
    @Expose
    private String gstDocumentPath;
    @SerializedName("userImagePath")
    @Expose
    private String userImagePath;
    @SerializedName("gstNumber")
    @Expose
    private String gstNumber;
    @SerializedName("panNumber")
    @Expose
    private String panNumber;
    @SerializedName("tanNumber")
    @Expose
    private String tanNumber;

    @SerializedName("runCertificateFile")
    @Expose
    private String runCertificateFile;
    @SerializedName("machineCredentialsFile")
    @Expose
    private String machineCredentialsFile;
    @SerializedName("govtProofDocFile")
    @Expose
    private String govtProofDocFile;
    @SerializedName("gstDocumentFile")
    @Expose
    private String gstDocumentFile;
    @SerializedName("userImageFile")
    @Expose
    private String userImageFile;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;
    @SerializedName("payableAtCity")
    @Expose
    private String payableAtCity;
    @SerializedName("referType")
    @Expose
    private String referType;
    @SerializedName("referMobileNo")
    @Expose
    private String referMobileNo;
    @SerializedName("referBy")
    @Expose
    private String referBy;
    @SerializedName("aboutYourself")
    @Expose
    private String aboutYourself;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("address4")
    @Expose
    private String address4;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("editProfile")
    @Expose
    private boolean editProfile;
    @SerializedName("attribute1")
    @Expose
    private boolean attribute1;
    @SerializedName("attribute2")
    @Expose
    private int attribute2;
    @SerializedName("attribute3")
    @Expose
    private boolean attribute3;
    @SerializedName("attribute4")
    @Expose
    private boolean attribute4;
    @SerializedName("attribute5")
    @Expose
    private boolean attribute5;
    @SerializedName("verified")
    @Expose
    private boolean verified;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPaytmAccount() {
        return paytmAccount;
    }

    public void setPaytmAccount(String paytmAccount) {
        this.paytmAccount = paytmAccount;
    }

    public List<String> getSegment() {
        return segment;
    }

    public void setSegment(List<String> segment) {
        this.segment = segment;
    }

    public List<Object> getRunAbility() {
        return runAbility;
    }

    public void setRunAbility(List<Object> runAbility) {
        this.runAbility = runAbility;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }
    public String getAssociatedTo() {
        return associatedTo;
    }

    public void setAssociatedTo(String associatedTo) {
        this.associatedTo = associatedTo;
    }


    public String getGovtProofId() {
        return govtProofId;
    }

    public void setGovtProofId(String govtProofId) {
        this.govtProofId = govtProofId;
    }

    public String getRunCertificatePath() {
        return runCertificatePath;
    }

    public void setRunCertificatePath(String runCertificatePath) {
        this.runCertificatePath = runCertificatePath;
    }

    public String getMachineCredentialsPath() {
        return machineCredentialsPath;
    }

    public void setMachineCredentialsPath(String machineCredentialsPath) {
        this.machineCredentialsPath = machineCredentialsPath;
    }

    public String getGovtProofDocPath() {
        return govtProofDocPath;
    }

    public void setGovtProofDocPath(String govtProofDocPath) {
        this.govtProofDocPath = govtProofDocPath;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getRunCertificateFile() {
        return runCertificateFile;
    }

    public void setRunCertificateFile(String runCertificateFile) {
        this.runCertificateFile = runCertificateFile;
    }

    public String getMachineCredentialsFile() {
        return machineCredentialsFile;
    }

    public void setMachineCredentialsFile(String machineCredentialsFile) {
        this.machineCredentialsFile = machineCredentialsFile;
    }

    public String getGovtProofDocFile() {
        return govtProofDocFile;
    }

    public void setGovtProofDocFile(String govtProofDocFile) {
        this.govtProofDocFile = govtProofDocFile;
    }

    public String getUserImageFile() {
        return userImageFile;
    }

    public void setUserImageFile(String userImageFile) {
        this.userImageFile = userImageFile;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPayableAtCity() {
        return payableAtCity;
    }

    public void setPayableAtCity(String payableAtCity) {
        this.payableAtCity = payableAtCity;
    }

    public String getReferType() {
        return referType;
    }

    public void setReferType(String referType) {
        this.referType = referType;
    }

    public String getReferMobileNo() {
        return referMobileNo;
    }

    public void setReferMobileNo(String referMobileNo) {
        this.referMobileNo = referMobileNo;
    }

    public String getReferBy() {
        return referBy;
    }

    public void setReferBy(String referBy) {
        this.referBy = referBy;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isEditProfile() {
        return editProfile;
    }

    public void setEditProfile(boolean editProfile) {
        this.editProfile = editProfile;
    }

    public boolean isAttribute1() {
        return attribute1;
    }

    public void setAttribute1(boolean attribute1) {
        this.attribute1 = attribute1;
    }

    public int getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(int attribute2) {
        this.attribute2 = attribute2;
    }

    public boolean isAttribute3() {
        return attribute3;
    }

    public void setAttribute3(boolean attribute3) {
        this.attribute3 = attribute3;
    }

    public boolean isAttribute4() {
        return attribute4;
    }

    public void setAttribute4(boolean attribute4) {
        this.attribute4 = attribute4;
    }

    public boolean isAttribute5() {
        return attribute5;
    }

    public void setAttribute5(boolean attribute5) {
        this.attribute5 = attribute5;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    public String getGstDocumentPath() {
        return gstDocumentPath;
    }

    public void setGstDocumentPath(String gstDocumentPath) {
        this.gstDocumentPath = gstDocumentPath;
    }

    public String getGstDocumentFile() {
        return gstDocumentFile;
    }

    public void setGstDocumentFile(String gstDocumentFile) {
        this.gstDocumentFile = gstDocumentFile;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(String tanNumber) {
        this.tanNumber = tanNumber;
    }

}
