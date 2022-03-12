package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ModelSupplier;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterSuppliers extends FirebaseRecyclerAdapter<ModelSupplier, AdapterSuppliers.ViewHolder> {

    public AdapterSuppliers(@NonNull FirebaseRecyclerOptions<ModelSupplier> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelSupplier model) {
        String SellerName = model.getSellerName();
        String SellerContact = model.getSellerContactNumber();

        holder.tvname.setText(SellerName);
        holder.tvcontact.setText(SellerContact);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seller,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cvSeller;
        TextView tvname, tvcontact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvSeller = itemView.findViewById(R.id.cvSeller);
            tvname = itemView.findViewById(R.id.tvSellerName);
            tvcontact = itemView.findViewById(R.id.tvSellerMobile);
        }
    }
}
