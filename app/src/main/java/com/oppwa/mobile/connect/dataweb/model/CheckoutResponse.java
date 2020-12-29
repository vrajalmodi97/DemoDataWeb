package com.oppwa.mobile.connect.dataweb.model;

public class CheckoutResponse {
    String CodRsp = "ERR";
    String MsjRsp = "";
    String checkoutId = null;

    public CheckoutResponse(){}

    public CheckoutResponse(String codRsp, String msjRsp, String checkoutId) {
        this.CodRsp = codRsp;
        this.MsjRsp = msjRsp;
        this.checkoutId = checkoutId;
    }

    public String getCodRsp() {
        return CodRsp;
    }

    public String getMsjRsp() {
        return MsjRsp;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public static class DWToken {
        public static CheckoutResponse DWcheckoutResponse = new CheckoutResponse();
    }
}