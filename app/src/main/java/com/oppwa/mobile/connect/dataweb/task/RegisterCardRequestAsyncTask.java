package com.oppwa.mobile.connect.dataweb.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.CheckoutResponse;
import com.oppwa.mobile.connect.dataweb.model.LogicDataWeb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterCardRequestAsyncTask extends AsyncTask<String, Void, String>  {

    private RegisterCardRequestListener listener;

    public RegisterCardRequestAsyncTask(RegisterCardRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        return requestCardRegister();
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onRegisterCardReceived(checkoutId);
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


    private String requestCardRegister() {
        String urlString = Constants.SCHEME + "://" + Constants.HOST + Constants.PATH_TOKEN_CREATE;
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);

            JsonReader reader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            reader.setLenient(true);
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
