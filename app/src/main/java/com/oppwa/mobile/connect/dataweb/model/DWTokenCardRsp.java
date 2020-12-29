package com.oppwa.mobile.connect.dataweb.model;

public class DWTokenCardRsp {
    String CodRsp = "ERR";
    String MsjRsp = "";
    String ResultId = null;

    public DWTokenCardRsp(){}

    public DWTokenCardRsp(String codRsp, String msjRsp, String ResultId) {
        this.CodRsp = codRsp;
        this.MsjRsp = msjRsp;
        this.ResultId = ResultId;
    }

    public String getCodRsp() {
        return CodRsp;
    }

    public String getMsjRsp() {
        return MsjRsp;
    }

    public String getResultId() {
        return ResultId;
    }

}
