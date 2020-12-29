package com.oppwa.mobile.connect.dataweb.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.Gson;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.dialog.GooglePayHelper;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.CheckoutResponse;
import com.oppwa.mobile.connect.dataweb.model.DWCheckOutRequest;
import com.oppwa.mobile.connect.dataweb.model.DWStatusRsp;
import com.oppwa.mobile.connect.dataweb.model.LogicDataWeb;
import com.oppwa.mobile.connect.dataweb.model.TokenizeCart;
import com.oppwa.mobile.connect.dataweb.task.CardTokenPaymentRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.CardTokenPaymentRequestListener;
import com.oppwa.mobile.connect.dataweb.task.CardTokenRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.CardTokenRequestListener;
import com.oppwa.mobile.connect.dataweb.task.CheckoutIdRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.CheckoutIdRequestListener;
import com.oppwa.mobile.connect.dataweb.task.PaymentStatusRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.PaymentStatusRequestListener;
import com.oppwa.mobile.connect.dataweb.task.RegisterCardRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.RegisterCardRequestListener;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import com.pixplicity.easyprefs.library.Prefs;

import static com.oppwa.mobile.connect.dataweb.model.DWStatusRsp.dWStatus.dwStatusRsp;


/**
 * Represents a base activity for making the payments with mobile sdk.
 * This activity handles payment callbacks.
 */
@SuppressLint("Registered")
public class BasePaymentActivity extends BaseActivity
        implements CheckoutIdRequestListener, PaymentStatusRequestListener, RegisterCardRequestListener, CardTokenRequestListener, CardTokenPaymentRequestListener {

    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";

    protected String resourcePath;
    protected boolean isToken = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath);
        }
    }

    /**
     * Returns <code>true</code> if the Intent contains one of the predefined schemes for the app.
     *
     * @param intent the incoming intent
     * @return <code>true</code> if the Intent contains one of the predefined schemes for the app;
     *         <code>false</code> otherwise
     */
    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESOURCE_PATH, resourcePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Override onActivityResult to get notified when the checkout process is done. */
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        if(!isToken) requestPaymentStatus(resourcePath);  else requestCardTokenStatus(resourcePath);
                    } else {
                        /* Asynchronous transaction is processed in the onNewIntent(). */
                        hideProgressDialog();
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();
                    showAlertDialog(R.string.error_message_cancel);
                    break;
                case 9896:
                    System.out.println("Entro");
                    break;
                case CheckoutActivity.RESULT_ERROR:
                    //Se agrego
                    hideProgressDialog();
                    if(!isToken) requestPaymentStatus(resourcePath);  else requestCardTokenStatus(resourcePath);
                    //PaymentError error = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    //showAlertDialog(R.string.error_message);
            }
        }
    }

    protected void requestCheckoutId(String callbackScheme) {
        showProgressDialog(R.string.progress_message_checkout_id);
        new CheckoutIdRequestAsyncTask(this,DWCheckOutRequest.dW.dWCheckOutRequest).execute();
    }

    protected void requestTokenPayment() {
        showProgressDialog(R.string.progress_message_checkout_id);
        new CardTokenPaymentRequestAsyncTask(this,DWCheckOutRequest.dW.dWCheckOutRequest).execute();
    }



    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialog();

        if (checkoutId == null) {
            showAlertDialog(R.string.error_message);
        }
    }

    @Override
    public void onPaymentStatusTokenReceived(String payment) {
        hideProgressDialog();
        if(payment == null){
            showAlertDialog("No se pudo realizar el pago");
            return;
        }

        if (dwStatusRsp != null){
            String mensajePopUp = "";
            mensajePopUp = "idTrx: " + (dwStatusRsp.getResultId()==null ? "": dwStatusRsp.getResultId()) +
                    "\n\nResultCode: " + (dwStatusRsp.getResultCode() == null ? "": dwStatusRsp.getResultCode()) +
                    "\n\nResultDescription: " + (dwStatusRsp.getResultDescription() == null ? "": dwStatusRsp.getResultDescription() ) +
                    "\n\nDetailExtDesc: " + (dwStatusRsp.getDetailExtDesc()==null ? "": dwStatusRsp.getDetailExtDesc()) +
                    "\n\nDetailAuth:" + (dwStatusRsp.getDetailAuth() == null ? "--": dwStatusRsp.getDetailAuth());
            showAlertDialog(mensajePopUp);
        }else showAlertDialog("Error al realizar el pago");

    }

    @Override
    public void onErrorOccurred() {
        hideProgressDialog();
        showAlertDialog(R.string.error_message);
    }

    @Override
    public void onTokenCardStatusReceived(String paymentStatus) {
        hideProgressDialog();

        //SHARED PREFERENCES
        String mensajePopUp = "";
        if (dwStatusRsp != null){
            mensajePopUp = "idTrx: " + (dwStatusRsp.getResultId()==null ? "": dwStatusRsp.getResultId()) +
                    "\n\nResultCode: " + (dwStatusRsp.getResultCode() == null ? "": dwStatusRsp.getResultCode()) +
                    "\n\nResultDescription: " + (dwStatusRsp.getResultDescription() == null ? "": dwStatusRsp.getResultDescription() ) +
                    "\n\nDetailExtDesc: " + (dwStatusRsp.getDetailExtDesc()==null ? "": dwStatusRsp.getDetailExtDesc()) +
                    "\n\nDetailAuth:" + (dwStatusRsp.getDetailAuth() == null ? "--": dwStatusRsp.getDetailAuth());

            //Tokenize
            if (dwStatusRsp.getCodRsp().equals("OK") && dwStatusRsp.getResultId().length() > 0) {
                TokenizeCart tokenize = new TokenizeCart(dwStatusRsp.getResultId(), dwStatusRsp.getCardBrand(),  dwStatusRsp.getCardHolder(), dwStatusRsp.getCardBin(), dwStatusRsp.getCardLast4());
                Gson gson = new Gson();
                String json = gson.toJson(tokenize);
                Prefs.putString(dwStatusRsp.getResultId(), json);
            }
        }

        showAlertDialog(mensajePopUp);
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {
        hideProgressDialog();

//        if ("OK".equals(paymentStatus)) {
//            showAlertDialog(R.string.message_successful_payment);
//
//            return;
//        }

        String mensajePopUp = "";
        if (dwStatusRsp != null){
            mensajePopUp = "idTrx: " + (dwStatusRsp.getResultId()==null ? "": dwStatusRsp.getResultId()) +
                    "\n\nResultCode: " + (dwStatusRsp.getResultCode() == null ? "": dwStatusRsp.getResultCode()) +
                    "\n\nResultDescription: " + (dwStatusRsp.getResultDescription() == null ? "": dwStatusRsp.getResultDescription() ) +
                    "\n\nDetailExtDesc: " + (dwStatusRsp.getDetailExtDesc()==null ? "": dwStatusRsp.getDetailExtDesc()) +
                    "\n\nDetailAuth:" + (dwStatusRsp.getDetailAuth() == null ? "--": dwStatusRsp.getDetailAuth());
        }

        showAlertDialog(mensajePopUp);
    }

    protected void requestPaymentStatus(String resourcePath) { //puede utilizar el resource PATH si se desea
        showProgressDialog(R.string.progress_message_payment_status);
        String checkoutId = LogicDataWeb.chRe.checkoutResponse.getCheckoutId();
        new PaymentStatusRequestAsyncTask(this).execute(checkoutId);
    }

    protected void requestCardTokenStatus(String resourcePath) { //puede utilizar el resource PATH si se desea
        showProgressDialog(R.string.progress_message_payment_status);
        String checkoutId = LogicDataWeb.chRe.checkoutResponse.getCheckoutId();
        new CardTokenRequestAsyncTask(this).execute(checkoutId);
    }



    //Register Card
    protected void requestRegisterCard() {
        showProgressDialog(R.string.progress_message_register_card);
        new RegisterCardRequestAsyncTask(this)
                .execute();
    }

    @Override
    public void onRegisterCardReceived(String checkoutId) {
        hideProgressDialog();
        if(checkoutId == null || checkoutId.isEmpty()){
            showAlertDialog(R.string.error_message);
        }
    }
}
