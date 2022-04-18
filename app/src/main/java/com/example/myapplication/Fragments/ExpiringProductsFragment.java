package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Adapters.AdapterProductSale;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpiringProductsFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseUser user;
    DatabaseReference reference;
    String Expiring;
    AdapterProduct adapterProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expiring_products, container, false);
        recyclerView = view.findViewById(R.id.rvExpiringProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String currentYear,currentMonth;
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        currentYear = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        currentMonth = calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        Expiring = currentMonth+""+currentYear;
        setRecyclerView();
        return view;
    }

    private void setRecyclerView(){

        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(reference.orderByChild("productExpire").equalTo(Expiring), ModelProduct.class)
                        .build();

        adapterProduct = new AdapterProduct(options);
        recyclerView.setAdapter(adapterProduct);

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


    public int getMonthNumberByName(String monthName)
    {
        int monthNumber = 0;
        switch (monthName.toLowerCase().trim())
        {
            case "january":
                monthNumber = 1;
                break;
            case "february":
                monthNumber = 2;
                break;
            case "march":
                monthNumber = 3;
                break;
            case "april":
                monthNumber = 4;
                break;
            case "may":
                monthNumber = 5;
                break;
            case "june":
                monthNumber = 6;
                break;
            case "july":
                monthNumber = 7;
                break;
            case "august":
                monthNumber = 8;
                break;
            case "september":
                monthNumber = 9;
                break;
            case "october":
                monthNumber = 10;
                break;
            case "november":
                monthNumber = 11;
                break;
            case "december":
                monthNumber = 12;
                break;
            default:
                monthNumber = 0;
        }
        return monthNumber;
    }
}