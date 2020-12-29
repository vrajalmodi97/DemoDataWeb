package com.oppwa.mobile.connect.dataweb.model;

import java.util.HashMap;
import java.util.Map;

public class TokenizeCart {
    String cardBin;
    String resultId;
    String cardBrand;
    String cardLast4;
    String cardHolder;

    public TokenizeCart(){}
    public TokenizeCart(String resultId, String cardBrand,  String cardHolder, String cardBin, String cardLast4){
        this.resultId = resultId;
        this.cardBrand = cardBrand;
        this.cardHolder = cardHolder;
        this.cardBin = cardBin;
        this.cardLast4 = cardLast4;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardLast4() {
        return cardLast4;
    }

    public void setCardLast4(String cardLast4) {
        this.cardLast4 = cardLast4;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Map<String, String> getParams()
    {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("CardBin", cardBin);
        queryParams.put("ResultId", resultId);
        queryParams.put("CardBrand", cardBrand);
        queryParams.put("CardLast4", cardLast4);
        queryParams.put("CardHolder", cardHolder);
        return queryParams;
    }

}
