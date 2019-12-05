package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentByPID {
    @SerializedName("invoiceId")
    @Expose
    private String invoiceId;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("workOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("workOrderId")
    @Expose
    private String workOrderId;
    @SerializedName("paymentAmount")
    @Expose
    private float paymentAmount;
    @SerializedName("invoiceAmount")
    @Expose
    private float invoiceAmount;
    @SerializedName("invoiceNetAmount")
    @Expose
    private float invoiceNetAmount;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("receiptId")
    @Expose
    private int receiptId;
    @SerializedName("receiptNo")
    @Expose
    private String receiptNo;
    @SerializedName("paymentTypeMeaning")
    @Expose
    private String paymentTypeMeaning;
    @SerializedName("cheque_DD")
    @Expose
    private String chequeDD;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("paymentDateStr")
    @Expose
    private String paymentDateStr;
    @SerializedName("transactionNo")
    @Expose
    private String transactionNo;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("paymentDocument")
    @Expose
    private String paymentDocument;
    @SerializedName("paymentDocumentPath")
    @Expose
    private String paymentDocumentPath;
    @SerializedName("lessorId")
    @Expose
    private int lessorId;
    @SerializedName("lesseeId")
    @Expose
    private int lesseeId;
    @SerializedName("invoiceLineId")
    @Expose
    private String invoiceLineId;
    @SerializedName("invoicePendingAmount")
    @Expose
    private int invoicePendingAmount;
    @SerializedName("editPayment")
    @Expose
    private boolean editPayment;
    @SerializedName("paymentDocumentFileName")
    @Expose
    private String paymentDocumentFileName;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public void setInvoiceNetAmount(int invoiceNetAmount) {
        this.invoiceNetAmount = invoiceNetAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getPaymentTypeMeaning() {
        return paymentTypeMeaning;
    }

    public void setPaymentTypeMeaning(String paymentTypeMeaning) {
        this.paymentTypeMeaning = paymentTypeMeaning;
    }

    public String getChequeDD() {
        return chequeDD;
    }

    public void setChequeDD(String chequeDD) {
        this.chequeDD = chequeDD;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDateStr() {
        return paymentDateStr;
    }

    public void setPaymentDateStr(String paymentDateStr) {
        this.paymentDateStr = paymentDateStr;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPaymentDocument() {
        return paymentDocument;
    }

    public void setPaymentDocument(String paymentDocument) {
        this.paymentDocument = paymentDocument;
    }

    public String getPaymentDocumentPath() {
        return paymentDocumentPath;
    }

    public void setPaymentDocumentPath(String paymentDocumentPath) {
        this.paymentDocumentPath = paymentDocumentPath;
    }

    public int getLessorId() {
        return lessorId;
    }

    public void setLessorId(int lessorId) {
        this.lessorId = lessorId;
    }

    public int getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(int lesseeId) {
        this.lesseeId = lesseeId;
    }

    public String getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(String invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public int getInvoicePendingAmount() {
        return invoicePendingAmount;
    }

    public void setInvoicePendingAmount(int invoicePendingAmount) {
        this.invoicePendingAmount = invoicePendingAmount;
    }

    public boolean isEditPayment() {
        return editPayment;
    }

    public void setEditPayment(boolean editPayment) {
        this.editPayment = editPayment;
    }

    public String getPaymentDocumentFileName() {
        return paymentDocumentFileName;
    }

    public void setPaymentDocumentFileName(String paymentDocumentFileName) {
        this.paymentDocumentFileName = paymentDocumentFileName;
    }

}
