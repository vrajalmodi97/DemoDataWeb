package com.oppwa.mobile.connect.dataweb.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oppwa.mobile.connect.dataweb.R;
import com.oppwa.mobile.connect.dataweb.model.Products;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<Products> productsList;

    public ProductAdapter(List<Products> pList){
        this.productsList = pList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ProductHolder(layoutInflater.inflate(R.layout.item_product,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder productHolder, int i) {
        productHolder.render(productsList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.productsList.size();
    }
}
