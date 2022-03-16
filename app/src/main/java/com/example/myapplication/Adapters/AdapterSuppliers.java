package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.SellerActivity;
import com.example.myapplication.Models.ModelSupplier;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class AdapterSuppliers extends FirebaseRecyclerAdapter<ModelSupplier, AdapterSuppliers.ViewHolder> {
    private Context context;
    public AdapterSuppliers(Context context, @NonNull FirebaseRecyclerOptions<ModelSupplier> options) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelSupplier model) {
        String SellerName = model.getSellerName();
        String SellerContact = model.getSellerContactNumber();
        holder.tvname.setText(SellerName);
        holder.tvcontact.setText(SellerContact);

        final String sellerID = getRef(position).getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clickonsupplier = new Intent(v.getContext(), SellerActivity.class);
                clickonsupplier.putExtra("sellerID", sellerID);
                context.startActivity(clickonsupplier);

            }
        });
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
