package com.oppwa.mobile.connect.dataweb.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.oppwa.mobile.connect.dataweb.R;

public class ProgressDialogDTF {
    private Context mContext = null;
    private ProgressDialog progressDialog;

    public ProgressDialogDTF(Context ctx){
        this.mContext = ctx;
    }


    public void showProgressDialog(String mensaje) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(mensaje);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }

    public void showAlertDialog(String message) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .setCancelable(false)
                .show();
    }

}
