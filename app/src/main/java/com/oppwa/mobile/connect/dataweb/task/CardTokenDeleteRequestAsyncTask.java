package com.oppwa.mobile.connect.dataweb.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.google.gson.Gson;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.CheckoutResponse;
import com.oppwa.mobile.connect.dataweb.model.DWDeleteCard;
import com.oppwa.mobile.connect.dataweb.model.DWStatusReq;
import com.oppwa.mobile.connect.dataweb.model.DWStatusRsp;
import com.oppwa.mobile.connect.dataweb.model.LogicDataWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class CardTokenDeleteRequestAsyncTask extends AsyncTask<String, Void, String> {
    private CardTokenDeleteRequestListener listener;

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

    public CardTokenDeleteRequestAsyncTask(CardTokenDeleteRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }
        String cardId = params[0];
        if(cardId != null) return requestTokenCardDelete(cardId);
        else return null;
    }

    @Override
    protected void onPostExecute(String codRsp) {
        if (listener != null) {
            if (codRsp == null) {
                listener.onErrorOccurred();
                return;
            }
            listener.onTokenCardDeleteReceived(codRsp);
        }
    }

    private CheckoutResponse ReadCheckoutResponse(JsonReader reader) throws IOException {
        String codRsp="";
        String msjRsp="";
        String checkoutId = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("CodRsp")) {
                codRsp = reader.nextString();
            } else if (name.equals("MsjRsp")) {
                msjRsp = reader.nextString();
            } else if (name.equals("checkoutId")) {
                JsonToken check = reader.peek();

                if (check != JsonToken.NULL){
                    checkoutId = reader.nextString();
                }else {
                    reader.nextNull();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        return new CheckoutResponse(codRsp,msjRsp,checkoutId);
    }

    private String requestTokenCardDelete(String cardId) {
        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String CodRsp = null;

        try {
            urlString = getEncodedParams(new DWDeleteCard(cardId).getParams(), Constants.SCHEME,Constants.HOST,Constants.PATH_TOKEN_DELETE);

            Log.d(Constants.LOG_TAG, "Status request url: " + urlString);

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);

            JsonReader reader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            CheckoutResponse checkoutResponse = ReadCheckoutResponse(reader);
            CheckoutResponse.DWToken.DWcheckoutResponse  = checkoutResponse;
            CodRsp = checkoutResponse.getCodRsp();
            Log.d(Constants.LOG_TAG, "Checkout ID: " + CodRsp);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return cardId;
    }
}
