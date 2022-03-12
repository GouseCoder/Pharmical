package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends FirebaseRecyclerAdapter<ModelProduct, AdapterProduct.ViewHolder> {
    private List<ModelProduct> products;
    private Context context;



    public AdapterProduct(@NonNull FirebaseRecyclerOptions<ModelProduct> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelProduct model) {
        String productName = model.getProductName();
        String productBrand = model.getProductBrand();
        String productManufacture = model.getProductManufacture();
        String productExpire = model.getProductExpire();
        String productLocation =  model.getProductLocation();
        String productSize = model.getProductSize();
        String productCategory = model.getProductCategory();
        int productQuantity = model.getProductQuantity();
        int productPrice = model.getProductPrice();

        holder.tvProductName.setText(productName);
        holder.tvProductBrand.setText(productBrand);
        holder.tvProductLocation.setText("Location : "+productLocation);
        holder.tvProductManufacture.setText("Manufacture : "+productManufacture);
        holder.tvProductExpire.setText("Expire : "+productExpire);
        holder.tvProductQuantity.setText("Quantity : "+String.valueOf(productQuantity));
        holder.tvProductPrice.setText("Price : "+String.valueOf(productPrice));
        holder.tvProductSize.setText("("+productSize+")");
        holder.tvProductCategory.setText(productCategory);
        holder.tvCount.setText(String.valueOf(position+1));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new ViewHolder(view);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName,tvProductLocation, tvProductCategory, tvProductSize, tvProductQuantity, tvProductBrand, tvProductPrice, tvProductManufacture, tvProductExpire, tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductLocation = itemView.findViewById(R.id.tvProductLocation);
            tvProductSize = itemView.findViewById(R.id.tvProductSize);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductBrand = itemView.findViewById(R.id.tvProductBrand);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductExpire = itemView.findViewById(R.id.tvProductExpire);
            tvProductManufacture = itemView.findViewById(R.id.tvProductManufacture);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            tvCount = itemView.findViewById(R.id.tvCount);

        }
    }


    public void filterList(List<ModelProduct> filtredList){
        products = new ArrayList<>();
        notifyDataSetChanged();
    }







}

