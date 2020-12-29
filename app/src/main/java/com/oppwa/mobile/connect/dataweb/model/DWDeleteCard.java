package com.oppwa.mobile.connect.dataweb.model;

import java.util.HashMap;
import java.util.Map;

public class DWDeleteCard {
    String cardId;

    public DWDeleteCard(String cardId){
        this.cardId = cardId;
    }

    public Map<String, String> getParams() {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("id", cardId);
        return queryParams;
    }
}
