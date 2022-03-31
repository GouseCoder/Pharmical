package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Adapters.AdapterSuppliers;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSupplier;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SuppliersFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterSuppliers adapterSuppliers;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suppliers, container, false);

        recyclerView = view.findViewById(R.id.rvSellers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseRecyclerOptions<ModelSupplier> options =
                new FirebaseRecyclerOptions.Builder<ModelSupplier>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Suppliers"), ModelSupplier.class)
                        .build();

        adapterSuppliers = new AdapterSuppliers(getContext(), options);
        recyclerView.setAdapter(adapterSuppliers);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterSuppliers.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterSuppliers.stopListening();
    }
}