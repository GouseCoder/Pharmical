package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.AdapterProductSale;
import com.example.myapplication.Adapters.AdapterSale;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllSalesFragment extends Fragment {
    DatabaseReference FromPath;
    DatabaseReference ToPath;
    FirebaseUser user;
    RecyclerView recyclerView;
    AdapterSale adapterSale;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_sales, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        recyclerView = view.findViewById(R.id.rvSalesAll);
        ToPath = FirebaseDatabase.getInstance().getReference("users").child(uid).child("allsales");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setRecyclerView();
        return view;
    }

    private void setRecyclerView(){

        FirebaseRecyclerOptions<ModelSale> options =
                new FirebaseRecyclerOptions.Builder<ModelSale>()
                        .setQuery(ToPath, ModelSale.class)
                        .build();

        adapterSale = new AdapterSale(getContext(), options);
        recyclerView.setAdapter(adapterSale);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterSale.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterSale.stopListening();
    }
}