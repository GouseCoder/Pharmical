package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ProductsNoticeFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterProduct adapterProduct;
    FirebaseUser user;
    DatabaseReference reference;
    Query query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_notice, container, false);

        recyclerView = view.findViewById(R.id.rvNoticeProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Products");


        if(query == reference.orderByChild("productQuantity").endAt(10)){
            Notify();
        }
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").
                                child(uid).child("Products").orderByChild("productQuantity")
                                .endAt(10), ModelProduct.class)
                        .build();

        adapterProduct = new AdapterProduct(options);
        recyclerView.setAdapter(adapterProduct);

        return view;
    }

    private void Notify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"My notification");
        builder.setContentTitle("Alert!! Less Products");
        builder.setContentText("Some products are less in your medical,check now");
        builder.setSmallIcon(R.drawable.mainlogo2);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(1,builder.build());
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterProduct.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterProduct.stopListening();
    }
}