package com.oppwa.mobile.connect.dataweb.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSecurityPolicyMode;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.checkout.meta.CheckoutStorePaymentDetailsMode;
import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.adapter.ProductAdapter;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.ConfigResponse;
import com.oppwa.mobile.connect.dataweb.model.DWCheckOutRequest;
import com.oppwa.mobile.connect.dataweb.model.DWStatusRsp;
import com.oppwa.mobile.connect.dataweb.model.Products;
import com.oppwa.mobile.connect.dataweb.model.TokenizeCart;
import com.oppwa.mobile.connect.dataweb.task.CardIdPaymentListener;
import com.oppwa.mobile.connect.dataweb.utils.GetIp;
import com.oppwa.mobile.connect.provider.Connect;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAULT_BRANDS;
import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAULT_DISPLAY_AMOUNT;
import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAULT_SKIPCVV;
import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAULT_TOKENIZE;
import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAUL_LANGUAGE;
import static com.oppwa.mobile.connect.dataweb.common.Constants.DEFAUL_TYPE_PAYMENT;
import static com.oppwa.mobile.connect.dataweb.common.Constants.MODE_LIVE;
import static com.oppwa.mobile.connect.dataweb.common.Constants.MODE_TEST;


/**
 * Represents an activity for making payments via {@link CheckoutActivity}.
 */
public class CheckoutUIActivity extends BasePaymentActivity implements CardIdPaymentListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AlertDialog alertDialog =  null;
    private RadioGroup radioGroup = null;
    CardIdPaymentListener cardIdPaymentListener = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_ui);

        findViewById(R.id.button_proceed_card_exists).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.Total > 0) {
                    Map<String, ?> allEntries = Prefs.getAll();
                    Gson gson = new Gson();
                    TokenizeCart tokenizeCart;
                    List<TokenizeCart> tokenizeCartList = new ArrayList<TokenizeCart>();;
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        tokenizeCart = gson.fromJson(entry.getValue().toString(),TokenizeCart.class);
                        tokenizeCartList.add(tokenizeCart);
                    }
                    if(tokenizeCartList.size() > 0){
                        CardListDialogFragment cardExist = new CardListDialogFragment(tokenizeCartList, cardIdPaymentListener);
                        cardExist.show(getSupportFragmentManager(),"TAG");
                    }else {
                        Toast.makeText(getApplicationContext(), "No posee tarjetas almacenadas", Toast.LENGTH_LONG).show();
                    }
                }
                else Toast.makeText(getApplicationContext(), "Elija un producto al menos", Toast.LENGTH_LONG).show();

            }
        });

        findViewById(R.id.button_proceed_to_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //requestCheckoutId(getString(R.string.checkout_ui_callback_scheme));
                isToken = false;
                if(Constants.Total > 0) startDialog(R.layout.custom_dialog_form, null);
                else Toast.makeText(getApplicationContext(), "Elija un producto al menos", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button_register_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isToken = true;
                requestRegisterCard();
            }
        });

        List<Products> productsList = new ArrayList<Products>();
        productsList.add(new Products(
                "Bebida en Lata",
                "$1.50 Producto grava iva", R.drawable.lat,
                1.50,
                true
        ));
        productsList.add(new Products(
                "Camisa Gris",
                "$5.00 Producto grava iva",
                R.drawable.camisa,
                5.00,
                true
        ));
        productsList.add(new Products(
                "Muestra Gratuita",
                "$1.00 Producto no graba iva",
                R.drawable.sample,
                1.00
        ));
        RecyclerView rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ProductAdapter(productsList);
        rvProduct.setAdapter(mAdapter);

    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        super.onCheckoutIdReceived(checkoutId);

        if (checkoutId != null) {
            openCheckoutUI(checkoutId, new ConfigResponse(),false);
        }
    }

    @Override
    public void onRegisterCardReceived(String checkoutId) {
        super.onRegisterCardReceived(checkoutId);
        if (checkoutId != null) {
            openCheckoutUI(checkoutId, new ConfigResponse(),true);
        }
    }

    private void openCheckoutUI(String checkoutId, ConfigResponse params, Boolean isToken) {
        if(checkoutId == null || checkoutId.isEmpty()) {
            return;
        }

        Connect.ProviderMode mode = Connect.ProviderMode.TEST;

        //Config_Brands
        String Config_Brands = params.getConfigBrands();
        Config_Brands = Config_Brands == null ? DEFAULT_BRANDS: Config_Brands;

        //Config_Ambiente
        String Config_Ambiente = params.getConfigAmbiente();
        Config_Ambiente = Config_Ambiente == null ? MODE_TEST : Config_Ambiente;
        mode = Config_Ambiente.equals(MODE_LIVE) ? Connect.ProviderMode.LIVE : Connect.ProviderMode.TEST;

        //Config_MostrarTotal
        String Config_MostrarTotal = params.getConfigMostrarTotal();
        Config_MostrarTotal = Config_MostrarTotal == null ? DEFAULT_DISPLAY_AMOUNT: Config_MostrarTotal;

        //Config_SkipCVV
        String Config_SkipCVV = params.getConfigSkipCVV();
        Config_SkipCVV = Config_SkipCVV == null? DEFAULT_SKIPCVV: Config_SkipCVV;

        //Config_Tokenize
        String Config_Tokenize = params.getConfigTokenize(); //0 Nunca, 1 Consultar, 2 Siempre
        Config_Tokenize = Config_Tokenize == null? DEFAULT_TOKENIZE: Config_Tokenize;

        String Pago_Diferidos = params.getPagoDiferidos();
        Pago_Diferidos = Pago_Diferidos == null ? "" : Pago_Diferidos;

        //Estilo_Idioma
        String Estilo_Idioma = params.getEstiloIdioma();
        Estilo_Idioma = Estilo_Idioma == null? DEFAUL_LANGUAGE: Estilo_Idioma;

        //Config_TipoPayment
        String Config_TipoPayment = Constants.tpayment;
        Config_TipoPayment = Config_TipoPayment == null? DEFAUL_TYPE_PAYMENT: Config_TipoPayment;

        Set<String> paymentBrands = new LinkedHashSet<String>();
        String[] brands = Config_Brands.split(",");
        for (String br : brands)
        {
            paymentBrands.add(br);
        }

        CheckoutSettings chk = new CheckoutSettings(checkoutId, paymentBrands, mode);
        chk.setLocale(Estilo_Idioma);
        if(!isToken) {
            if (Config_MostrarTotal.equals("false")) {
                chk.setTotalAmountRequired(false);
            } else {
                chk.setTotalAmountRequired(true);
            }

            if (Config_SkipCVV.equals("true")) {
                chk.setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS);
            }
            if (Config_SkipCVV.equals("false")) {
                chk.setSkipCVVMode(CheckoutSkipCVVMode.NEVER);
            }

            for (String br : brands) {
                chk.setSecurityPolicyModeForBrand(br, CheckoutSecurityPolicyMode.DEVICE_AUTH_REQUIRED_IF_AVAILABLE);
            }

            if (Config_Tokenize.equals("0")) {
                chk.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.NEVER);
            } else if (Config_Tokenize.equals("1")) {
                chk.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.PROMPT);
            } else if (Config_Tokenize.equals("2")) {
                chk.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.ALWAYS);
            }

            if (!Config_TipoPayment.equals(DEFAUL_TYPE_PAYMENT)) {
                String[] diferidos = "".split(",");
                Integer[] difInt = null;
                if (Pago_Diferidos.length() > 0) {
                    diferidos = Pago_Diferidos.split(",");
                    difInt = new Integer[diferidos.length];
                    for (int i = 0; i < diferidos.length; i++) {
                        difInt[i] = Integer.parseInt(diferidos[i]);
                    }
                }
                if (diferidos.length != 0) {
                    chk.setInstallmentEnabled(true);
                    chk.setInstallmentOptions(difInt);
                }
            }
        }
        chk.setShopperResultUrl("checkoutui://callback");
        Intent intent = chk.createCheckoutActivityIntent(this);
        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
    }

    public void startDialog(int llR, final String cardID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewLayoutInflater = getLayoutInflater().inflate(llR,null, false);
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
                        Toast.makeText(getApplicationContext(), "Elija un tipo de diferido valido", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewLayoutInflater.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

        viewLayoutInflater.findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView temp = viewLayoutInflater.findViewById(R.id.edtCI);
                if(!validateForm(temp)) return;
                DWCheckOutRequest.dW.dWCheckOutRequest.setClienteDocId(temp.getText().toString());

                temp = viewLayoutInflater.findViewById(R.id.edtName);
                if(!validateForm(temp)) return;
                DWCheckOutRequest.dW.dWCheckOutRequest.setClientePNombre(temp.getText().toString());

                temp = viewLayoutInflater.findViewById(R.id.edtLastName);
                if(!validateForm(temp)) return;
                DWCheckOutRequest.dW.dWCheckOutRequest.setClientePApellido(temp.getText().toString());

                temp = viewLayoutInflater.findViewById(R.id.edtEmail);
                if(!validateForm(temp)) return;
                DWCheckOutRequest.dW.dWCheckOutRequest.setClienteEmail(temp.getText().toString());

                DWCheckOutRequest.dW.dWCheckOutRequest.setClienteIP(GetIp.getIPAddress(true));

                temp = viewLayoutInflater.findViewById(R.id.edtAddress);
                if(!validateForm(temp)) return;
                DWCheckOutRequest.dW.dWCheckOutRequest.setEnvioDireccion(temp.getText().toString());

                DWCheckOutRequest.dW.dWCheckOutRequest.setValor_Base0(Constants.TotalBase0);
                DWCheckOutRequest.dW.dWCheckOutRequest.setValor_BaseImp(Constants.TotalBaseImp);
                DWCheckOutRequest.dW.dWCheckOutRequest.setValor_IVA(Constants.TotalIVA);
                DWCheckOutRequest.dW.dWCheckOutRequest.setValor_Total(Constants.Total);

                RadioButton radioButton = viewLayoutInflater.findViewById(R.id.rdCorriente);
                if(radioButton.isChecked()){
                    DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(0);
                    DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Meses(0);
                    Constants.tpayment = "0";
                }

                radioButton = viewLayoutInflater.findViewById(R.id.rdDiferido);
                if(radioButton.isChecked()){
                    DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(2);
                    Constants.tpayment = "2";
                }

                radioButton = viewLayoutInflater.findViewById(R.id.rdDiferidoSin);
                if(radioButton.isChecked()){
                    DWCheckOutRequest.dW.dWCheckOutRequest.setCredito_Tipo(3);
                    Constants.tpayment = "3";
                }
                dismissDialog();
                if(cardID != null && cardID.length() > 0){
                    DWCheckOutRequest.dW.dWCheckOutRequest.setToken(cardID);
                    requestTokenPayment();
                }else{
                    requestCheckoutId(getString(R.string.checkout_ui_callback_scheme));
                }

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

    public Boolean validateForm(TextView temp){
        if(temp.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Porfavor rellene los campos antes de usar", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void paymentStart(String CardID) {
        if(CardID!=null && CardID.length()>0) startDialog(R.layout.custom_dialog_form, CardID);
        else showAlertDialog("Error al obtener el identificador");
    }
}
