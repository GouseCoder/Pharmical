package com.example.myapplication.Fragments;

import android.os.Bundle;

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
import com.google.firebase.database.FirebaseDatabase;


public class ProductsNoticeFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterProduct adapterProduct;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_notice, container, false);

        recyclerView = view.findViewById(R.id.rvNoticeProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Products").orderByChild("productQuantity").endAt(10), ModelProduct.class)
                        .build();

        adapterProduct = new AdapterProduct(options);
        recyclerView.setAdapter(adapterProduct);


        return view;
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