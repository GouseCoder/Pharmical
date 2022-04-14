package com.example.myapplication.Fragments;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Models.ModelCount;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener{
    FirebaseUser user;
    private TextView tvProductsCount,tvMoreProducts,tvIncome,tvTodaysSaleCount,tvMoreIncome,tvMoreTodaysSale,tvBrandsCount,tvMoreBrands,tvNoticeCount,tvMoreNotice, tvExpiringCount,tvMoreExpiring,tvExpiredCount,tvMoreExpired;
    private ActionBar actionBar;
    private Toolbar toolbar;
    int itemsCount, brandsCount, lessProductsCount;
    DatabaseReference reference1, reference2, reference3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(view);



        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Products");
        reference2 = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("ProductBrand");
        tvMoreProducts.setOnClickListener(this);
        tvMoreTodaysSale.setOnClickListener(this);
        tvMoreIncome.setOnClickListener(this);
        tvMoreNotice.setOnClickListener(this);
        tvMoreExpiring.setOnClickListener(this);
        //tvMoreBrands.setOnClickListener(this);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsCount = (int) snapshot.getChildrenCount();
                tvProductsCount.setText(String.valueOf(itemsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                brandsCount = (int) snapshot.getChildrenCount();
                tvBrandsCount.setText(String.valueOf(brandsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Query query = reference1.orderByChild("productQuantity").endAt(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessProductsCount = (int) snapshot.getChildrenCount();
                tvNoticeCount.setText(String.valueOf(lessProductsCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    private void init(View view)
    {
        //barDailySales = view.findViewById(R.id.barDailySales);
        tvProductsCount = view.findViewById(R.id.tvProductsCount);
        tvMoreProducts = view.findViewById(R.id.tvMoreProducts);
        tvTodaysSaleCount = view.findViewById(R.id.tvTodaysSaleCount);
        tvMoreTodaysSale = view.findViewById(R.id.tvMoreTodaysSale);
        tvIncome = view.findViewById(R.id.tvIncome);
        tvMoreIncome = view.findViewById(R.id.tvMoreIncome);
        tvNoticeCount = view.findViewById(R.id.tvNoticeCount);
        tvMoreNotice = view.findViewById(R.id.tvMoreNotice);
        tvExpiringCount = view.findViewById(R.id.tvExpiringCount);
        tvMoreExpiring = view.findViewById(R.id.tvMoreExpiring);
        tvBrandsCount = view.findViewById(R.id.tvBrandsCount);
        tvMoreBrands = view.findViewById(R.id.tvMoreProducts);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Fragment fragment;
        switch (id)
        {
            case R.id.tvMoreProducts:
                fragment = new ProductsFragment();
                actionBar.setTitle("Products");
                setFragment(fragment);
                break;
            case R.id.tvMoreNotice:
                fragment = new ProductsNoticeFragment();
                actionBar.setTitle("Products Notice");
                setFragment(fragment);
                break;
            case R.id.tvMoreExpiring:
                fragment = new ExpiringProductsFragment();
                actionBar.setTitle("Expiring Products");
                setFragment(fragment);
                break;
        }
    }

    private void setFragment(Fragment fragment)
    {
        getFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
}