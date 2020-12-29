package com.oppwa.mobile.connect.dataweb.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.CheckoutResponse;
import com.oppwa.mobile.connect.dataweb.model.DWCheckOutRequest;
import com.oppwa.mobile.connect.dataweb.model.LogicDataWeb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;
    private DWCheckOutRequest dwCheckOutRequest;

    public Map<String, String> getParams() {
        Locale.setDefault(Locale.US);
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

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener, DWCheckOutRequest dwCheckOutReq) {
        this.listener = listener;
        this.dwCheckOutRequest = dwCheckOutReq;
    }

    @Override
    protected String doInBackground(String... params) {
        return requestCheckoutId();
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId() {
        String urlString = getEncodedParams(getParams(),Constants.SCHEME,Constants.HOST,Constants.PATH_CHECKOUT);
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);

            JsonReader reader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            CheckoutResponse checkoutResponse = ReadCheckoutResponse(reader);
            LogicDataWeb checkoutObj = new LogicDataWeb(checkoutResponse);
            checkoutId = checkoutObj.getCheckoutId();
            Log.d(Constants.LOG_TAG, "Checkout ID: " + checkoutId);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return checkoutId;
    }
}