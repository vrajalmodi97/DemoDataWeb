package com.oppwa.mobile.connect.dataweb.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.common.Constants;
import com.oppwa.mobile.connect.dataweb.model.Products;
import com.squareup.picasso.Picasso;

public class ProductHolder extends RecyclerView.ViewHolder {
    private View view;
    private TextView title;
    private TextView description;
    private ImageView img;
    private CheckBox checkBox;


    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }

    public void render(final Products products){
        title =  view.findViewById(R.id.title_product);
        title.setText(products.getTitle());

        description = view.findViewById(R.id.title_description);
        description.setText(products.getDescription());

        img = view.findViewById(R.id.img_product);
        Picasso.get().load(products.getImg()).into(img);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        checkBox = view.findViewById(R.id.chProduct);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Double TotalBase0Tmp = (products.getIva() == false)? products.getPrice() : 0.00;
                Double TotalBaseImpTmp = (products.getIva() == false)? 0.00 : products.getPrice();
                Double TotalIVATmp = (products.getIva() == false)? 0.00 : products.getPrice() * 0.12;
                Double TotalTmp = TotalBase0Tmp + TotalBaseImpTmp + TotalIVATmp;

                if(isChecked){
                    Constants.TotalBase0 += TotalBase0Tmp;
                    Constants.TotalBaseImp += TotalBaseImpTmp;
                    Constants.TotalIVA += TotalIVATmp;
                    Constants.Total += TotalTmp;
                    Toast.makeText(view.getContext(), "Producto Agregado a su carrito", Toast.LENGTH_SHORT).show();
                }
                else {
                    Constants.TotalBase0 -= TotalBase0Tmp;
                    Constants.TotalBaseImp -= TotalBaseImpTmp;
                    Constants.TotalIVA -= TotalIVATmp;
                    Constants.Total -= TotalTmp;
                    Toast.makeText(view.getContext(), "Este Producto no esta en su carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
