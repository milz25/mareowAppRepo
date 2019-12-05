package com.mareow.recaptchademo.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeOfTermAndCondition {
    @SerializedName("generalTermsdescription")
    @Expose
    private String generalTermsdescription;
    @SerializedName("commercialTermsdescription")
    @Expose
    private String commercialTermsdescription;
    @SerializedName("paymentTermsdescription")
    @Expose
    private String paymentTermsdescription;
    @SerializedName("logisticsTermsdescription")
    @Expose
    private String logisticsTermsdescription;
    @SerializedName("cancellationTermsdescription")
    @Expose
    private String cancellationTermsdescription;

    public String getGeneralTermsdescription() {
        return generalTermsdescription;
    }

    public void setGeneralTermsdescription(String generalTermsdescription) {
        this.generalTermsdescription = generalTermsdescription;
    }

    public String getCommercialTermsdescription() {
        return commercialTermsdescription;
    }

    public void setCommercialTermsdescription(String commercialTermsdescription) {
        this.commercialTermsdescription = commercialTermsdescription;
    }

    public String getPaymentTermsdescription() {
        return paymentTermsdescription;
    }

    public void setPaymentTermsdescription(String paymentTermsdescription) {
        this.paymentTermsdescription = paymentTermsdescription;
    }

    public String getLogisticsTermsdescription() {
        return logisticsTermsdescription;
    }

    public void setLogisticsTermsdescription(String logisticsTermsdescription) {
        this.logisticsTermsdescription = logisticsTermsdescription;
    }

    public String getCancellationTermsdescription() {
        return cancellationTermsdescription;
    }

    public void setCancellationTermsdescription(String cancellationTermsdescription) {
        this.cancellationTermsdescription = cancellationTermsdescription;
    }
}
