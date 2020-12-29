package com.oppwa.mobile.connect.dataweb.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.CheckoutResponse;
import com.oppwa.mobile.connect.dataweb.model.DWCheckOutRequest;
import com.oppwa.mobile.connect.dataweb.model.LogicDataWeb;
import com.oppwa.mobile.connect.dataweb.model.TokenizeCart;
import com.oppwa.mobile.connect.dataweb.task.CardIdPaymentListener;
import com.oppwa.mobile.connect.dataweb.task.CardTokenDeleteRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.task.CardTokenDeleteRequestListener;
import com.oppwa.mobile.connect.dataweb.task.PaymentStatusRequestAsyncTask;
import com.oppwa.mobile.connect.dataweb.utils.GetIp;
import com.oppwa.mobile.connect.dataweb.utils.ProgressDialogDTF;
import com.pixplicity.easyprefs.library.Prefs;
import com.vinaygaba.creditcardview.CardNumberFormat;
import com.vinaygaba.creditcardview.CardType;
import com.vinaygaba.creditcardview.CreditCardView;

import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     CardListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
@SuppressLint("ValidFragment")
public class CardListDialogFragment extends BottomSheetDialogFragment {
    private List<TokenizeCart> tokenizeCartList;
    final private CardIdPaymentListener cardIdPaymentListener;

    public CardListDialogFragment(List<TokenizeCart> tokenizeCarts, CardIdPaymentListener _cardIdPaymentListener) {
        this.tokenizeCartList = tokenizeCarts;
        this.cardIdPaymentListener = _cardIdPaymentListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new cardAdapter(tokenizeCartList));
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements CardTokenDeleteRequestListener {
        private View view;
        private CreditCardView cardItem;
        private AlertDialog alertDialog =  null;
        private CardTokenDeleteRequestListener listener = this;
        private Context mContext = null;
        ProgressDialogDTF progressDialogDTF = null;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            mContext = itemView.getContext();
            progressDialogDTF = new ProgressDialogDTF(mContext);
        }

        public void render(final TokenizeCart tokenizeCart){
            cardItem = view.findViewById(R.id.cardItem);
            cardItem.setCardName(tokenizeCart.getCardHolder());

            String number = tokenizeCart.getCardBin() + "XXXX" + tokenizeCart.getCardLast4();
            cardItem.setCardNumber(number);
            cardItem.setCardNumberFormat(CardNumberFormat.MASKED_ALL_BUT_LAST_FOUR);
            cardItem.setCardBackBackground(R.drawable.cardbackground_world);
            cardItem.setType(CardType.AUTO);
            cardItem.setIsEditable(false);
            cardItem.setIsCvvEditable(false);
            cardItem.setIsCardNameEditable(false);
            cardItem.setIsCardNumberEditable(false);
            cardItem.setIsExpiryDateEditable(false);
            cardItem.setExpiryDate("XX/XX");

            cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDialog(R.layout.card_dialog, view, tokenizeCart);
                }
            });
        }


        public void startDialog(int llR, View view, final TokenizeCart tokenizeCart){
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            final View viewLayoutInflater = getLayoutInflater().inflate(llR,null, false);

            viewLayoutInflater.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                }
            });

            viewLayoutInflater.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                    progressDialogDTF.showProgressDialog("Eliminando Tarjeta");
                    String resultId = tokenizeCart.getResultId();
                    new CardTokenDeleteRequestAsyncTask(listener).execute(resultId);
                }
            });

            viewLayoutInflater.findViewById(R.id.btnPayment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String resultId = tokenizeCart.getResultId();
                    dismissDialog();
                    dismiss();
                    cardIdPaymentListener.paymentStart(resultId);
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

        @Override
        public void onErrorOccurred() {
            progressDialogDTF.hideProgressDialog();
            progressDialogDTF.showAlertDialog("Error al eliminar: " + CheckoutResponse.DWToken.DWcheckoutResponse.getMsjRsp());

        }

        @Override
        public void onTokenCardDeleteReceived(String cardId) {
            progressDialogDTF.hideProgressDialog();
            if(CheckoutResponse.DWToken.DWcheckoutResponse.getCodRsp().equals("OK")){
                progressDialogDTF.showAlertDialog("Se elimino correctamente");
                Prefs.remove(cardId);
                dismiss();
            }else{
                progressDialogDTF.showAlertDialog("Error al eliminar: " + CheckoutResponse.DWToken.DWcheckoutResponse.getMsjRsp() );
            }
        }
    }

    private class cardAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<TokenizeCart> tokenizeCartList;

        cardAdapter(List<TokenizeCart> item) {
            this.tokenizeCartList = item;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ViewHolder(layoutInflater.inflate(R.layout.fragment_item_list_dialog_list_dialog_item,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.render(tokenizeCartList.get(position));
        }

        @Override
        public int getItemCount() {
            return this.tokenizeCartList.size();
        }

    }

}