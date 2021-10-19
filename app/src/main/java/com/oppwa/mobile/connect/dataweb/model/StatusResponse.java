package com.oppwa.mobile.connect.dataweb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusResponse implements Serializable {

    @Expose
    @SerializedName("card")
    private Card card;
    @Expose
    @SerializedName("resultDetails")
    private ResultDetails resultDetails;
    @Expose
    @SerializedName("result")
    private Result result;
    @Expose
    @SerializedName("merchantTransactionId")
    private String merchantTransactionId;
    @Expose
    @SerializedName("descriptor")
    private String descriptor;
    @Expose
    @SerializedName("currency")
    private String currency;
    @Expose
    @SerializedName("amount")
    private String amount;
    @Expose
    @SerializedName("paymentBrand")
    private String paymentBrand;
    @Expose
    @SerializedName("paymentType")
    private String paymentType;
    @Expose
    @SerializedName("registrationId")
    private String registrationId;
    @Expose
    @SerializedName("id")
    private String id;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public ResultDetails getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(ResultDetails resultDetails) {
        this.resultDetails = resultDetails;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentBrand() {
        return paymentBrand;
    }

    public void setPaymentBrand(String paymentBrand) {
        this.paymentBrand = paymentBrand;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Card {
        @Expose
        @SerializedName("expiryYear")
        private String expiryYear;
        @Expose
        @SerializedName("expiryMonth")
        private String expiryMonth;
        @Expose
        @SerializedName("holder")
        private String holder;
        @Expose
        @SerializedName("last4Digits")
        private String last4Digits;
        @Expose
        @SerializedName("bin")
        private String bin;

        public String getExpiryYear() {
            return expiryYear;
        }

        public void setExpiryYear(String expiryYear) {
            this.expiryYear = expiryYear;
        }

        public String getExpiryMonth() {
            return expiryMonth;
        }

        public void setExpiryMonth(String expiryMonth) {
            this.expiryMonth = expiryMonth;
        }

        public String getHolder() {
            return holder;
        }

        public void setHolder(String holder) {
            this.holder = holder;
        }

        public String getLast4Digits() {
            return last4Digits;
        }

        public void setLast4Digits(String last4Digits) {
            this.last4Digits = last4Digits;
        }

        public String getBin() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin = bin;
        }
    }

    public static class ResultDetails {
        @Expose
        @SerializedName("ReferenceNo")
        private String ReferenceNo;
        @Expose
        @SerializedName("TotalAmount")
        private String TotalAmount;
        @Expose
        @SerializedName("AcquirerResponse")
        private String AcquirerResponse;
        @Expose
        @SerializedName("BatchNo")
        private String BatchNo;
        @Expose
        @SerializedName("ReferenceNbr")
        private String ReferenceNbr;
        @Expose
        @SerializedName("ConnectorTxID1")
        private String ConnectorTxID1;
        @Expose
        @SerializedName("clearingInstituteName")
        private String clearingInstituteName;
        @Expose
        @SerializedName("ExtendedDescription")
        private String ExtendedDescription;
        @Expose
        @SerializedName("Response")
        private String Response;

        public String getReferenceNo() {
            return ReferenceNo;
        }

        public void setReferenceNo(String ReferenceNo) {
            this.ReferenceNo = ReferenceNo;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String TotalAmount) {
            this.TotalAmount = TotalAmount;
        }

        public String getAcquirerResponse() {
            return AcquirerResponse;
        }

        public void setAcquirerResponse(String AcquirerResponse) {
            this.AcquirerResponse = AcquirerResponse;
        }

        public String getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(String BatchNo) {
            this.BatchNo = BatchNo;
        }

        public String getReferenceNbr() {
            return ReferenceNbr;
        }

        public void setReferenceNbr(String ReferenceNbr) {
            this.ReferenceNbr = ReferenceNbr;
        }

        public String getConnectorTxID1() {
            return ConnectorTxID1;
        }

        public void setConnectorTxID1(String ConnectorTxID1) {
            this.ConnectorTxID1 = ConnectorTxID1;
        }

        public String getClearingInstituteName() {
            return clearingInstituteName;
        }

        public void setClearingInstituteName(String clearingInstituteName) {
            this.clearingInstituteName = clearingInstituteName;
        }

        public String getExtendedDescription() {
            return ExtendedDescription;
        }

        public void setExtendedDescription(String ExtendedDescription) {
            this.ExtendedDescription = ExtendedDescription;
        }

        public String getResponse() {
            return Response;
        }

        public void setResponse(String Response) {
            this.Response = Response;
        }
    }

    public static class Result {
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("code")
        private String code;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
