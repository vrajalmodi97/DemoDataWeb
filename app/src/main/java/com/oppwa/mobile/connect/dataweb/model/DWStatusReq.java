package com.oppwa.mobile.connect.dataweb.model;

import java.util.HashMap;
import java.util.Map;

public class DWStatusReq {

    String checkoutId;

    public DWStatusReq(String cId){
        this.checkoutId = cId;
    }

    public Map<String, String> getParams() {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("checkoutId", checkoutId);
        return queryParams;
    }
}
