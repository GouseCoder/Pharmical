package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.AdapterSale;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.Date;

public class TodaySaleFragment extends Fragment {
    String createdAt,currentDate;
    Date currentTime;
    FirebaseUser user;
    RecyclerView recyclerView;
    DatabaseReference reference;
    AdapterSale adapterSale;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_today_sale, container, false);
        currentTime = Calendar.getInstance().getTime();
        createdAt = currentTime.toString();
        currentDate = createdAt.substring(0,11);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        recyclerView = view.findViewById(R.id.rvSell);
        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("allales");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setRecyclerView();
        return view;
    }

    private void setRecyclerView(){

        FirebaseRecyclerOptions<ModelSale> options =
                new FirebaseRecyclerOptions.Builder<ModelSale>()
                        .setQuery(reference.orderByChild("createdAt").equalTo(currentDate), ModelSale.class)
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