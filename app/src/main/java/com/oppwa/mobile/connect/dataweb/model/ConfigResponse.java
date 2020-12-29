package com.oppwa.mobile.connect.dataweb.model;

import com.oppwa.mobile.connect.dataweb.common.Constants;

public class ConfigResponse{
    String configBrands = "VISA,MASTER,AMEX";
    String configAmbiente = Constants.MODE_TEST;
    String configMostrarTotal = "true";
    String configSkipCVV = "true";
    String configTokenize = "1";
    String pagoDiferidos = "3,6,9";
    String estiloIdioma = "es_ES";

    public String getConfigBrands() {
        return configBrands;
    }

    public void setConfigBrands(String configBrands) {
        this.configBrands = configBrands;
    }

    public String getConfigAmbiente() {
        return configAmbiente;
    }

    public void setConfigAmbiente(String configAmbiente) {
        this.configAmbiente = configAmbiente;
    }

    public String getConfigMostrarTotal() {
        return configMostrarTotal;
    }

    public void setConfigMostrarTotal(String configMostrarTotal) {
        this.configMostrarTotal = configMostrarTotal;
    }

    public String getConfigSkipCVV() {
        return configSkipCVV;
    }

    public void setConfigSkipCVV(String configSkipCVV) {
        this.configSkipCVV = configSkipCVV;
    }

    public String getConfigTokenize() {
        return configTokenize;
    }

    public void setConfigTokenize(String configTokenize) {
        this.configTokenize = configTokenize;
    }

    public String getPagoDiferidos() {
        return pagoDiferidos;
    }

    public void setPagoDiferidos(String pagoDiferidos) {
        this.pagoDiferidos = pagoDiferidos;
    }

    public String getEstiloIdioma() {
        return estiloIdioma;
    }

    public void setEstiloIdioma(String estiloIdioma) {
        this.estiloIdioma = estiloIdioma;
    }
}