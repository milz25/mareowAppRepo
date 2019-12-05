package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReceiptDTO implements Serializable {

    @SerializedName("paymentAmount")
    @Expose
    private float paymentAmount;
    @SerializedName("paymentId")
    @Expose
    private int paymentId;
    @SerializedName("invoiceId")
    @Expose
    private int invoiceId;
    @SerializedName("invoiceLineId")
    @Expose
    private int invoiceLineId;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("acknowledgement")
    @Expose
    private boolean acknowledgement;
    @SerializedName("paymentReceiptNo")
    @Expose
    private String paymentReceiptNo;
    @SerializedName("receiptDate")
    @Expose
    private String receiptDate;
    @SerializedName("receiptDateStr")
    @Expose
    private String receiptDateStr;
    @SerializedName("invoicePendingAmount")
    @Expose
    private float invoicePendingAmount;
    @SerializedName("receiptStatusMeaning")
    @Expose
    private String receiptStatusMeaning;
    @SerializedName("receiptStatusCode")
    @Expose
    private String receiptStatusCode;

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(int invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public boolean isAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(boolean acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getPaymentReceiptNo() {
        return paymentReceiptNo;
    }

    public void setPaymentReceiptNo(String paymentReceiptNo) {
        this.paymentReceiptNo = paymentReceiptNo;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptDateStr() {
        return receiptDateStr;
    }

    public void setReceiptDateStr(String receiptDateStr) {
        this.receiptDateStr = receiptDateStr;
    }

    public float getInvoicePendingAmount() {
        return invoicePendingAmount;
    }

    public void setInvoicePendingAmount(float invoicePendingAmount) {
        this.invoicePendingAmount = invoicePendingAmount;
    }

    public String getReceiptStatusMeaning() {
        return receiptStatusMeaning;
    }

    public void setReceiptStatusMeaning(String receiptStatusMeaning) {
        this.receiptStatusMeaning = receiptStatusMeaning;
    }

    public String getReceiptStatusCode() {
        return receiptStatusCode;
    }

    public void setReceiptStatusCode(String receiptStatusCode) {
        this.receiptStatusCode = receiptStatusCode;
    }

}
