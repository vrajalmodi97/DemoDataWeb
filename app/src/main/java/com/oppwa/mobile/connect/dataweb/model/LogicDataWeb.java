package com.oppwa.mobile.connect.dataweb.model;

import static com.oppwa.mobile.connect.dataweb.common.Constants.COD_ERR;

public class LogicDataWeb {

    public LogicDataWeb(CheckoutResponse dataResponse){
        chRe.checkoutResponse = dataResponse;
    }
    
    public String getCheckoutId() {
        if(chRe.checkoutResponse.CodRsp == COD_ERR) return null;
        if (chRe.checkoutResponse.checkoutId != null) return chRe.checkoutResponse.checkoutId;
        else return null;
    }
    public static class chRe {
        public static CheckoutResponse checkoutResponse;
    }
}
