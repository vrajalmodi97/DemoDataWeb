package com.oppwa.mobile.connect.dataweb.common;

import java.util.LinkedHashSet;
import java.util.Set;


public class Constants {
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final String LOG_TAG = "msdk.demo";
    public static double TotalBase0 = 0.00;
    public static double TotalBaseImp = 0.00;
    public static double TotalIVA = 0.00;
    public static double Total = 0.00;
    public static String tpayment = "0";

    public static String COD_OK = "OK";
    public static String COD_ERR  = "ER";
    public static String AUT = "00";

    //OPPWA
    public static final String MODE_TEST = "TEST";
    public static final String MODE_LIVE = "LIVE";
    public static final String DEFAULT_BRANDS = "VISA,MASTER";
    public static final String DEFAULT_DISPLAY_AMOUNT = "false";
    public static final String DEFAULT_SKIPCVV = "false";
    public static final String DEFAULT_TOKENIZE = "1";
    public static final String DEFAUL_LANGUAGE = "en_US";
    public static final String DEFAUL_TYPE_PAYMENT = "0";

    //Conection
    public static final String SCHEME = "https";
    public static final String HOST = "msdk.firmasegura.com.ec";
    public static final String PATH_CHECKOUT  = "/msdkApi/checkout";
    public static final String PATH_PAYMENT = "/msdkApi/status";
    public static final String PATH_TOKEN_CREATE = "/msdkApi/token_create";
    public static final String PATH_TOKEN_DELETE = "/msdkApi/token_delete";
    public static final String PATH_TOKEN_PAYMENT = "/msdkApi/token_payment";
}
