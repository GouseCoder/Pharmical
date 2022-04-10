package com.example.myapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class AdapterSale extends FirebaseRecyclerAdapter<ModelSale, AdapterSale.ViewHolder> {
    private List<ModelSale> modelSaleList;
    private Context context;

    public AdapterSale(Context context, @NonNull FirebaseRecyclerOptions<ModelSale> options, ModelSale modelSales) {
        super(options);
        this.context = context;
        this.modelSaleList = (List<ModelSale>) modelSales;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelSale model) {
        ModelSale sale = modelSaleList.get(position);

        String productCategory = sale.getProductCategory();
        String productName = sale.getProductName();
        String productSize = sale.getProductSize();
        String productBrand = sale.getProductBrand();
        int productPrice = sale.getProductPrice();
        String productLocation = sale.getProductLocation();
        String productManufacture = sale.getProductManufacture();
        String productExpire = sale.getProductExpire();
        int sellQuantity = sale.getSaleQuantity();
        int saleDiscount = sale.getSaleDiscount();
        int salePrice = sale.getSalePrice();
        int saleQuantity = sale.getSaleQuantity();
        String createdAt = sale.getCreatedAt();

        holder.tvProductName.setText(productName);
        holder.tvProductSize.setText("("+productSize+")");
        holder.tvProductCategory.setText(productCategory);
        holder.tvSaleTime.setText(createdAt);
        holder.tvProductPrice.setText(String.valueOf(productPrice));
        holder.tvSaleQuantity.setText(String.valueOf(sellQuantity));
        holder.tvSalePrice.setText(String.valueOf(salePrice));
        holder.tvProductBrand.setText(productBrand);
        holder.tvProductManufacture.setText(productManufacture);
        holder.tvProductExpire.setText(productExpire);
        holder.tvCount.setText(String.valueOf(position+1));
        holder.tvTotalPrice.setText(String.valueOf(productPrice*saleQuantity));
        holder.cvSell.setOnLongClickListener(view -> {
            showDeleteAlert(holder,sale,position);
            return true;
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    private void showDeleteAlert(ViewHolder holder, ModelSale sale, int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure want to delete?");
        builder.setMessage("You are going to delete "+sale.getProductName()+". The Sale Quantity of this product was "+sale.getSaleQuantity()+" and the total price was " +sale.getSalePrice());
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //deleteSoldProduct(sale.getSaleId(),position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Deletion Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void updateList(List<ModelSale> list){
        modelSaleList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modelSaleList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProductName, tvProductSize, tvProductCategory, tvSaleTime, tvProductPrice, tvSaleQuantity, tvSalePrice, tvProductBrand, tvProductManufacture, tvProductExpire,tvCount,tvTotalPrice;
        private CardView cvSell;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductSize = itemView.findViewById(R.id.tvProductSize);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            tvSaleTime = itemView.findViewById(R.id.tvSaleTime);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvSaleQuantity = itemView.findViewById(R.id.tvSaleQuantity);
            tvSalePrice = itemView.findViewById(R.id.tvSalePrice);
            tvProductBrand = itemView.findViewById(R.id.tvProductBrand);
            tvProductManufacture = itemView.findViewById(R.id.tvProductManufacture);
            tvProductExpire = itemView.findViewById(R.id.tvProductExpire);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvCount = itemView.findViewById(R.id.tvCount);
            cvSell = itemView.findViewById(R.id.cvSell);
        }
    }
}
