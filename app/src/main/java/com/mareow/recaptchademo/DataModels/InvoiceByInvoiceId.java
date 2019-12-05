package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceByInvoiceId {

    @SerializedName("invoiceId")
    @Expose
    private int invoiceId;
    @SerializedName("invoiceLineDId")
    @Expose
    private int invoiceLineDId;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("invoiceRefNo")
    @Expose
    private String invoiceRefNo;
    @SerializedName("invoiceAmount")
    @Expose
    private float invoiceAmount;
    @SerializedName("workOrderId")
    @Expose
    private int workOrderId;
    @SerializedName("workOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("invoiceNoMeaning")
    @Expose
    private String invoiceNoMeaning;
    @SerializedName("invoice_desc")
    @Expose
    private String invoiceDesc;
    @SerializedName("lessorId")
    @Expose
    private int lessorId;
    @SerializedName("lesseeId")
    @Expose
    private int lesseeId;
    @SerializedName("invoiceDocument")
    @Expose
    private String invoiceDocument;
    @SerializedName("invoiceDocumentPath")
    @Expose
    private String invoiceDocumentPath;
    @SerializedName("invoiceDocumentFileName")
    @Expose
    private String invoiceDocumentFileName;
    @SerializedName("gstFlg")
    @Expose
    private boolean gstFlg;
    @SerializedName("igstFlg")
    @Expose
    private boolean igstFlg;
    @SerializedName("sgstPer")
    @Expose
    private int sgstPer;
    @SerializedName("cgstPer")
    @Expose
    private int cgstPer;
    @SerializedName("igstPer")
    @Expose
    private int igstPer;
    @SerializedName("cgst")
    @Expose
    private float cgst;
    @SerializedName("sgst")
    @Expose
    private float sgst;
    @SerializedName("igst")
    @Expose
    private float igst;
    @SerializedName("invoiceNetAmount")
    @Expose
    private float invoiceNetAmount;
    @SerializedName("invoiceDate")
    @Expose
    private String invoiceDate;
    @SerializedName("invoiceDateStr")
    @Expose
    private String invoiceDateStr;
    @SerializedName("workOrderEstimateAmount")
    @Expose
    private float workOrderEstimateAmount;
    @SerializedName("invCategory")
    @Expose
    private String invCategory;
    @SerializedName("serviceChageFlg")
    @Expose
    private boolean serviceChageFlg;
    @SerializedName("serviceAmountOwn")
    @Expose
    private float serviceAmountOwn;
    @SerializedName("serviceAmountRen")
    @Expose
    private float serviceAmountRen;
    @SerializedName("editInvoice")
    @Expose
    private boolean editInvoice;
    @SerializedName("closeWorkOrderFlg")
    @Expose
    private boolean closeWorkOrderFlg;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInvoiceLineDId() {
        return invoiceLineDId;
    }

    public void setInvoiceLineDId(int invoiceLineDId) {
        this.invoiceLineDId = invoiceLineDId;
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

    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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

    public String getInvoiceNoMeaning() {
        return invoiceNoMeaning;
    }

    public void setInvoiceNoMeaning(String invoiceNoMeaning) {
        this.invoiceNoMeaning = invoiceNoMeaning;
    }

    public String getInvoiceDesc() {
        return invoiceDesc;
    }

    public void setInvoiceDesc(String invoiceDesc) {
        this.invoiceDesc = invoiceDesc;
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

    public String getInvoiceDocument() {
        return invoiceDocument;
    }

    public void setInvoiceDocument(String invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public String getInvoiceDocumentPath() {
        return invoiceDocumentPath;
    }

    public void setInvoiceDocumentPath(String invoiceDocumentPath) {
        this.invoiceDocumentPath = invoiceDocumentPath;
    }

    public String getInvoiceDocumentFileName() {
        return invoiceDocumentFileName;
    }

    public void setInvoiceDocumentFileName(String invoiceDocumentFileName) {
        this.invoiceDocumentFileName = invoiceDocumentFileName;
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

    public int getSgstPer() {
        return sgstPer;
    }

    public void setSgstPer(int sgstPer) {
        this.sgstPer = sgstPer;
    }

    public int getCgstPer() {
        return cgstPer;
    }

    public void setCgstPer(int cgstPer) {
        this.cgstPer = cgstPer;
    }

    public int getIgstPer() {
        return igstPer;
    }

    public void setIgstPer(int igstPer) {
        this.igstPer = igstPer;
    }

    public float getCgst() {
        return cgst;
    }

    public void setCgst(float cgst) {
        this.cgst = cgst;
    }

    public float getSgst() {
        return sgst;
    }

    public void setSgst(float sgst) {
        this.sgst = sgst;
    }

    public float getIgst() {
        return igst;
    }

    public void setIgst(float igst) {
        this.igst = igst;
    }

    public float getInvoiceNetAmount() {
        return invoiceNetAmount;
    }

    public void setInvoiceNetAmount(float invoiceNetAmount) {
        this.invoiceNetAmount = invoiceNetAmount;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDateStr() {
        return invoiceDateStr;
    }

    public void setInvoiceDateStr(String invoiceDateStr) {
        this.invoiceDateStr = invoiceDateStr;
    }

    public float getWorkOrderEstimateAmount() {
        return workOrderEstimateAmount;
    }

    public void setWorkOrderEstimateAmount(float workOrderEstimateAmount) {
        this.workOrderEstimateAmount = workOrderEstimateAmount;
    }

    public String getInvCategory() {
        return invCategory;
    }

    public void setInvCategory(String invCategory) {
        this.invCategory = invCategory;
    }

    public boolean isServiceChageFlg() {
        return serviceChageFlg;
    }

    public void setServiceChageFlg(boolean serviceChageFlg) {
        this.serviceChageFlg = serviceChageFlg;
    }

    public float getServiceAmountOwn() {
        return serviceAmountOwn;
    }

    public void setServiceAmountOwn(float serviceAmountOwn) {
        this.serviceAmountOwn = serviceAmountOwn;
    }

    public float getServiceAmountRen() {
        return serviceAmountRen;
    }

    public void setServiceAmountRen(float serviceAmountRen) {
        this.serviceAmountRen = serviceAmountRen;
    }

    public boolean isEditInvoice() {
        return editInvoice;
    }

    public void setEditInvoice(boolean editInvoice) {
        this.editInvoice = editInvoice;
    }

    public boolean isCloseWorkOrderFlg() {
        return closeWorkOrderFlg;
    }

    public void setCloseWorkOrderFlg(boolean closeWorkOrderFlg) {
        this.closeWorkOrderFlg = closeWorkOrderFlg;
    }

}
