package com.oppwa.mobile.connect.dataweb.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.DWStatusReq;
import com.oppwa.mobile.connect.dataweb.model.DWStatusRsp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Represents an async task to request a payment status from the server.
 */
public class PaymentStatusRequestAsyncTask extends AsyncTask<String, Void, String> {

    private PaymentStatusRequestListener listener;


    public String getEncodedParams(Map<String,String> params, String scheme, String host, String path) {
        String urlBase = scheme + "://" + host + path;
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> key: params.entrySet()){
            String value = null;
            try{
                value = URLEncoder.encode(key.getValue(),"UTF-8");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            if(sb.length() == 0){
                sb.append(urlBase);
                sb.append("?");
            }else if(sb.length() > 0){
                sb.append("&");
            }
            sb.append(key.getKey()).append("=").append(value);
        }
        return sb.toString();
    }

    public PaymentStatusRequestAsyncTask(PaymentStatusRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }

        String chechoutId = params[0];

        if (chechoutId != null) {
            return requestPaymentStatus(chechoutId);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String paymentStatus) {
        if (listener != null) {
            if (paymentStatus == null) {
                listener.onErrorOccurred();

                return;
            }

            listener.onPaymentStatusReceived(paymentStatus);
        }
    }

    private String requestPaymentStatus(String chechoutId) {
        if (chechoutId == null) {
            return null;
        }

        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String paymentStatus = null;

        try {
            urlString = getEncodedParams(new DWStatusReq(chechoutId).getParams(),Constants.SCHEME,Constants.HOST,Constants.PATH_PAYMENT);

            Log.d(Constants.LOG_TAG, "Status request url: " + urlString);

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            paymentStatus = response.toString();
            Gson gson = new Gson();
            DWStatusRsp dwStatusRsp = gson.fromJson(paymentStatus,DWStatusRsp.class);
            DWStatusRsp.dWStatus.dwStatusRsp = dwStatusRsp;
            Log.d(Constants.LOG_TAG, "Status: " + paymentStatus);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return paymentStatus;
    }
}
