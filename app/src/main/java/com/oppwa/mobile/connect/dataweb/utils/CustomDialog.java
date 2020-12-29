package com.oppwa.mobile.connect.dataweb.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.DWCheckOutRequest;

public class CustomDialog {
    private Activity mActivity = null;
    private AlertDialog alertDialog =  null;
    private RadioGroup radioGroup = null;

    public CustomDialog(Activity m){
        this.mActivity = m;
    }

    public void startDialog(int llR){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View viewLayoutInflater = mActivity.getLayoutInflater().inflate(llR,null, false);
        radioGroup = viewLayoutInflater.findViewById(R.id.gpRadio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rdCorriente:
                        DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Meses(0);
                        DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(0);
                        Constants.tpayment = "0";
                        break;
                    case R.id.rdDiferido:
                        DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(2);
                        Constants.tpayment = "2";
                        break;
                    case R.id.rdDiferidoSin:
                        DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(3);
                        Constants.tpayment = "0";
                        break;
                    default:
                        Toast.makeText(mActivity, "Elija un tipo de diferido valido", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewLayoutInflater.findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        builder.setView(viewLayoutInflater);
        builder.setCancelable(true);
        alertDialog = builder.create();
        if(alertDialog != null){
            alertDialog.show();
        }
    }

    public void dismissDialog(){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }
}
