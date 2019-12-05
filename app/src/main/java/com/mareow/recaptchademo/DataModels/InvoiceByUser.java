package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InvoiceByUser implements Serializable {
    @SerializedName("invoiceId")
    @Expose
    private int invoiceId;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("invoiceRefNo")
    @Expose
    private String invoiceRefNo;
    @SerializedName("inactive")
    @Expose
    private boolean inactive;
    @SerializedName("invoiceAmount")
    @Expose
    private float invoiceAmount;
    @SerializedName("invoiceNetAmount")
    @Expose
    private float invoiceNetAmount;
    @SerializedName("invoiceReceiveAmount")
    @Expose
    private float invoiceReceiveAmount;
    @SerializedName("invoicePendingAmount")
    @Expose
    private float invoicePendingAmount;
    @SerializedName("invoiceDate")
    @Expose
    private String invoiceDate;

    @SerializedName("invCategory")
    @Expose
    private String invCategory;

    public String getInvCategory() {
        return invCategory;
    }

    public void setInvCategory(String invCategory) {
        this.invCategory = invCategory;
    }

    public String getInvCategoryCode() {
        return invCategoryCode;
    }

    public void setInvCategoryCode(String invCategoryCode) {
        this.invCategoryCode = invCategoryCode;
    }

    @SerializedName("invCategoryCode")
    @Expose
    private String invCategoryCode;


    @SerializedName("renterId")
    @Expose
    private int renterId;
    @SerializedName("ownerId")
    @Expose
    private int ownerId;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("workOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("workOrderEstimateAmount")
    @Expose
    private float workOrderEstimateAmount;
    @SerializedName("gstFlg")
    @Expose
    private boolean gstFlg;
    @SerializedName("igstFlg")
    @Expose
    private boolean igstFlg;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("invoiceLineId")
    @Expose
    private int invoiceLineId;
    @SerializedName("payDone")
    @Expose
    private boolean payDone;
    @SerializedName("receiptDTOs")
    @Expose
    private List<ReceiptDTO> receiptDTOs = null;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceRefNo() {
        return invoiceRefNo;
    }

    public void setInvoiceRefNo(String invoiceRefNo) {
        this.invoiceRefNo = invoiceRefNo;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public float getInvoiceNetAmount() {
        return invoiceNetAmount;
    }

    public void setInvoiceNetAmount(float invoiceNetAmount) {
        this.invoiceNetAmount = invoiceNetAmount;
    }

    public float getInvoiceReceiveAmount() {
        return invoiceReceiveAmount;
    }

    public void setInvoiceReceiveAmount(float invoiceReceiveAmount) {
        this.invoiceReceiveAmount = invoiceReceiveAmount;
    }

    public float getInvoicePendingAmount() {
        return invoicePendingAmount;
    }

    public void setInvoicePendingAmount(float invoicePendingAmount) {
        this.invoicePendingAmount = invoicePendingAmount;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public float getWorkOrderEstimateAmount() {
        return workOrderEstimateAmount;
    }

    public void setWorkOrderEstimateAmount(float workOrderEstimateAmount) {
        this.workOrderEstimateAmount = workOrderEstimateAmount;
    }

    public boolean isGstFlg() {
        return gstFlg;
    }

    public void setGstFlg(boolean gstFlg) {
        this.gstFlg = gstFlg;
    }

    public boolean isIgstFlg() {
        return igstFlg;
    }

    public void setIgstFlg(boolean igstFlg) {
        this.igstFlg = igstFlg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(int invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public boolean isPayDone() {
        return payDone;
    }

    public void setPayDone(boolean payDone) {
        this.payDone = payDone;
    }

    public List<ReceiptDTO> getReceiptDTOs() {
        return receiptDTOs;
    }

    public void setReceiptDTOs(List<ReceiptDTO> receiptDTOs) {
        this.receiptDTOs = receiptDTOs;
    }

}
