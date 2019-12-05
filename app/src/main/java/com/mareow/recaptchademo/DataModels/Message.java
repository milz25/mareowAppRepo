package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("unreadCount")
    @Expose
    private Object unreadCount;
    @SerializedName("communicationId")
    @Expose
    private int communicationId;
    @SerializedName("from")
    @Expose
    private int from;
    @SerializedName("to")
    @Expose
    private int to;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("communicateType")
    @Expose
    private Object communicateType;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("toMsg")
    @Expose
    private String toMsg;
    @SerializedName("toMsgDate")
    @Expose
    private Object toMsgDate;
    @SerializedName("toMsgDateStr")
    @Expose
    private String toMsgDateStr;
    @SerializedName("fromMsg")
    @Expose
    private String fromMsg;
    @SerializedName("fromMsgDate")
    @Expose
    private String fromMsgDate;
    @SerializedName("fromMsgDateStr")
    @Expose
    private String fromMsgDateStr;
    @SerializedName("readFlg")
    @Expose
    private boolean readFlg;
    @SerializedName("senderId")
    @Expose
    private int senderId;
    @SerializedName("imagePath")
    @Expose
    private Object imagePath;
    @SerializedName("fromImagePath")
    @Expose
    private Object fromImagePath;
    @SerializedName("toImagePath")
    @Expose
    private Object toImagePath;
    @SerializedName("conversationName")
    @Expose
    private Object conversationName;
    @SerializedName("isverify")
    @Expose
    private boolean isverify;
    @SerializedName("aboutYourself")
    @Expose
    private Object aboutYourself;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("userCategory")
    @Expose
    private Object userCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Object unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(int communicationId) {
        this.communicationId = communicationId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(Object communicateType) {
        this.communicateType = communicateType;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public String getToMsg() {
        return toMsg;
    }

    public void setToMsg(String toMsg) {
        this.toMsg = toMsg;
    }

    public Object getToMsgDate() {
        return toMsgDate;
    }

    public void setToMsgDate(Object toMsgDate) {
        this.toMsgDate = toMsgDate;
    }

    public String getToMsgDateStr() {
        return toMsgDateStr;
    }

    public void setToMsgDateStr(String toMsgDateStr) {
        this.toMsgDateStr = toMsgDateStr;
    }

    public String getFromMsg() {
        return fromMsg;
    }

    public void setFromMsg(String fromMsg) {
        this.fromMsg = fromMsg;
    }

    public String getFromMsgDate() {
        return fromMsgDate;
    }

    public void setFromMsgDate(String fromMsgDate) {
        this.fromMsgDate = fromMsgDate;
    }

    public String getFromMsgDateStr() {
        return fromMsgDateStr;
    }

    public void setFromMsgDateStr(String fromMsgDateStr) {
        this.fromMsgDateStr = fromMsgDateStr;
    }

    public boolean isReadFlg() {
        return readFlg;
    }

    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Object getImagePath() {
        return imagePath;
    }

    public void setImagePath(Object imagePath) {
        this.imagePath = imagePath;
    }

    public Object getFromImagePath() {
        return fromImagePath;
    }

    public void setFromImagePath(Object fromImagePath) {
        this.fromImagePath = fromImagePath;
    }

    public Object getToImagePath() {
        return toImagePath;
    }

    public void setToImagePath(Object toImagePath) {
        this.toImagePath = toImagePath;
    }

    public Object getConversationName() {
        return conversationName;
    }

    public void setConversationName(Object conversationName) {
        this.conversationName = conversationName;
    }

    public boolean isIsverify() {
        return isverify;
    }

    public void setIsverify(boolean isverify) {
        this.isverify = isverify;
    }

    public Object getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(Object aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(Object userCategory) {
        this.userCategory = userCategory;
    }

}

