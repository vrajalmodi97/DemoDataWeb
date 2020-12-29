package com.oppwa.mobile.connect.dataweb.model;

public class DWStatusRsp {
    String CodRsp;
    String MsjRsp;
    String ResultId;
    String RegistrationId;
    String ResultCode;
    String ResultStatus;
    String ResultDescription;
    String CardBrand;
    String CardBin;
    String CardLast4;
    String CardHolder;
    String DetailAcquirer;
    String DetailResponse;
    String DetailAuth;
    String DetailBatchNo;
    String DetailRef;
    String DetailExtDesc;
    String DetailInterest;
    String DetailTotal;

    public  DWStatusRsp(){}

    public DWStatusRsp(String codRsp, String msjRsp, String resultId, String registrationId, String resultCode, String resultStatus, String resultDescription, String cardBrand, String cardBin, String cardLast4, String cardHolder, String detailAcquirer, String detailResponse, String detailAuth, String detailBatchNo, String detailRef, String detailExtDesc, String detailInterest, String detailTotal) {
        CodRsp = codRsp;
        MsjRsp = msjRsp;
        ResultId = resultId;
        RegistrationId = registrationId;
        ResultCode = resultCode;
        ResultStatus = resultStatus;
        ResultDescription = resultDescription;
        CardBrand = cardBrand;
        CardBin = cardBin;
        CardLast4 = cardLast4;
        CardHolder = cardHolder;
        DetailAcquirer = detailAcquirer;
        DetailResponse = detailResponse;
        DetailAuth = detailAuth;
        DetailBatchNo = detailBatchNo;
        DetailRef = detailRef;
        DetailExtDesc = detailExtDesc;
        DetailInterest = detailInterest;
        DetailTotal = detailTotal;
    }

    public String getCodRsp() {
        return CodRsp;
    }

    public void setCodRsp(String codRsp) {
        CodRsp = codRsp;
    }

    public String getMsjRsp() {
        return MsjRsp;
    }

    public void setMsjRsp(String msjRsp) {
        MsjRsp = msjRsp;
    }

    public String getResultId() {
        return ResultId;
    }

    public void setResultId(String resultId) {
        ResultId = resultId;
    }

    public String getRegistrationId() {
        return RegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        RegistrationId = registrationId;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultStatus() {
        return ResultStatus;
    }

    public void setResultStatus(String resultStatus) {
        ResultStatus = resultStatus;
    }

    public String getResultDescription() {
        return ResultDescription;
    }

    public void setResultDescription(String resultDescription) {
        ResultDescription = resultDescription;
    }

    public String getCardBrand() {
        return CardBrand;
    }

    public void setCardBrand(String cardBrand) {
        CardBrand = cardBrand;
    }

    public String getCardBin() {
        return CardBin;
    }

    public void setCardBin(String cardBin) {
        CardBin = cardBin;
    }

    public String getCardLast4() {
        return CardLast4;
    }

    public void setCardLast4(String cardLast4) {
        CardLast4 = cardLast4;
    }

    public String getCardHolder() {
        return CardHolder;
    }

    public void setCardHolder(String cardHolder) {
        CardHolder = cardHolder;
    }

    public String getDetailAcquirer() {
        return DetailAcquirer;
    }

    public void setDetailAcquirer(String detailAcquirer) {
        DetailAcquirer = detailAcquirer;
    }

    public String getDetailResponse() {
        return DetailResponse;
    }

    public void setDetailResponse(String detailResponse) {
        DetailResponse = detailResponse;
    }

    public String getDetailAuth() {
        return DetailAuth;
    }

    public void setDetailAuth(String detailAuth) {
        DetailAuth = detailAuth;
    }

    public String getDetailBatchNo() {
        return DetailBatchNo;
    }

    public void setDetailBatchNo(String detailBatchNo) {
        DetailBatchNo = detailBatchNo;
    }

    public String getDetailRef() {
        return DetailRef;
    }

    public void setDetailRef(String detailRef) {
        DetailRef = detailRef;
    }

    public String getDetailExtDesc() {
        return DetailExtDesc;
    }

    public void setDetailExtDesc(String detailExtDesc) {
        DetailExtDesc = detailExtDesc;
    }

    public String getDetailInterest() {
        return DetailInterest;
    }

    public void setDetailInterest(String detailInterest) {
        DetailInterest = detailInterest;
    }

    public String getDetailTotal() {
        return DetailTotal;
    }

    public void setDetailTotal(String detailTotal) {
        DetailTotal = detailTotal;
    }

    public static class dWStatus {
        public static DWStatusRsp dwStatusRsp = null;
    }

}
