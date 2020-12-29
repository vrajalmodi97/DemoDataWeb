package com.oppwa.mobile.connect.dataweb.model;

import java.util.HashMap;
import java.util.Map;

public class DWTokenCheckOutReq {
    String Token;
    String ClienteDocId = "";
    String ClientePNombre = "";
    String ClientePApellido = "";
    String ClienteEmail = "";
    String ClienteIP = "";
    String EnvioDireccion = "";
    Double Valor_Base0 = 0.00;
    Double Valor_BaseImp = 0.00;
    Double Valor_IVA = 0.00;
    Double Valor_Total = 0.00;
    int Credito_Tipo =  0;
    int Credito_Meses = 0;

    public DWTokenCheckOutReq(){

    }

    public DWTokenCheckOutReq(String token,String clienteDocId, String clientePNombre, String clientePApellido, String clienteEmail, String clienteIP, String envioDireccion, Double valor_Base0, Double valor_BaseImp, Double valor_IVA, Double valor_Total, int credito_Tipo, int credito_Meses) {
        this.Token = token;
        this.ClienteDocId = clienteDocId;
        this.ClientePNombre = clientePNombre;
        this.ClientePApellido = clientePApellido;
        this.ClienteEmail = clienteEmail;
        this.ClienteIP = clienteIP;
        this.EnvioDireccion = envioDireccion;
        this.Valor_Base0 = valor_Base0;
        this.Valor_BaseImp = valor_BaseImp;
        this.Valor_IVA = valor_IVA;
        this.Valor_Total = valor_Total;
        this.Credito_Tipo = credito_Tipo;
        this.Credito_Meses = credito_Meses;
    }

    public void setClienteDocId(String clienteDocId) {
        this.ClienteDocId = clienteDocId;
    }

    public void setClientePNombre(String clientePNombre) {
        this.ClientePNombre = clientePNombre;
    }

    public void setClientePApellido(String clientePApellido) {
        this.ClientePApellido = clientePApellido;
    }

    public void setClienteEmail(String clienteEmail) {
        this.ClienteEmail = clienteEmail;
    }

    public void setClienteIP(String clienteIP) {
        this.ClienteIP = clienteIP;
    }

    public void setEnvioDireccion(String envioDireccion) {
        this.EnvioDireccion = envioDireccion;
    }

    public void setValor_Base0(Double valor_Base0) {
        this.Valor_Base0 = valor_Base0;
    }

    public void setValor_BaseImp(Double valor_BaseImp) {
        this.Valor_BaseImp = valor_BaseImp;
    }

    public void setValor_IVA(Double valor_IVA) {
        this.Valor_IVA = valor_IVA;
    }

    public void setValor_Total(Double valor_Total) {
        this.Valor_Total = valor_Total;
    }

    public void setCredito_Tipo(int credito_Tipo) {
        this.Credito_Tipo = credito_Tipo;
    }

    public void setCredito_Meses(int credito_Meses) {
        this.Credito_Meses = credito_Meses;
    }

    public String getClienteDocId() {
        return ClienteDocId;
    }

    public String getClientePNombre() {
        return ClientePNombre;
    }

    public String getClientePApellido() {
        return ClientePApellido;
    }

    public String getClienteEmail() {
        return ClienteEmail;
    }

    public String getClienteIP() {
        return ClienteIP;
    }

    public String getEnvioDireccion() {
        return EnvioDireccion;
    }

    public Double getValor_Base0() {
        return Valor_Base0;
    }

    public Double getValor_BaseImp() {
        return Valor_BaseImp;
    }

    public Double getValor_IVA() {
        return Valor_IVA;
    }

    public Double getValor_Total() {
        return Valor_Total;
    }

    public int getCredito_Tipo() {
        return Credito_Tipo;
    }

    public int getCredito_Meses() {
        return Credito_Meses;
    }

    public Map<String, String> getParams() {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("ClienteDocId", DWCheckOutRequest.dW.dWCheckOutRequest.getClienteDocId());
        queryParams.put("ClientePNombre", DWCheckOutRequest.dW.dWCheckOutRequest.getClientePNombre());
        queryParams.put("ClientePApellido", DWCheckOutRequest.dW.dWCheckOutRequest.getClientePApellido());
        queryParams.put("ClienteEmail", DWCheckOutRequest.dW.dWCheckOutRequest.getClienteEmail());
        queryParams.put("ClienteIP", DWCheckOutRequest.dW.dWCheckOutRequest.getClienteIP());
        queryParams.put("EnvioDireccion", DWCheckOutRequest.dW.dWCheckOutRequest.getEnvioDireccion());
        queryParams.put("Valor_Base0", String.format("%.2f", DWCheckOutRequest.dW.dWCheckOutRequest.getValor_Base0()));
        queryParams.put("Valor_BaseImp", String.format("%.2f", DWCheckOutRequest.dW.dWCheckOutRequest.getValor_BaseImp()));
        queryParams.put("Valor_IVA", String.format("%.2f", DWCheckOutRequest.dW.dWCheckOutRequest.getValor_IVA()));
        queryParams.put("Valor_Total", String.format("%.2f", DWCheckOutRequest.dW.dWCheckOutRequest.getValor_Total()));
        queryParams.put("Credito_Tipo", String.valueOf(DWCheckOutRequest.dW.dWCheckOutRequest.getCredito_Tipo()));
        queryParams.put("Credito_Meses", String.valueOf(DWCheckOutRequest.dW.dWCheckOutRequest.getCredito_Meses()));
        return queryParams;
    }

    public static class DWT {
        public static DWTokenCheckOutReq dwTokenCheckOutReq = new DWTokenCheckOutReq();
    }
}
