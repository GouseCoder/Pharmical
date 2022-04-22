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
import android.widget.ProgressBar;
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

public class DashboardFragment extends Fragment{
    FirebaseUser user;
    private TextView tvProductsCount,tvBrandsCount,tvNoticeCount, tvExpiringCount;
    private ActionBar actionBar;
    private Toolbar toolbar;
    int itemsCount, brandsCount, lessProductsCount;
    DatabaseReference reference1, reference2, reference3;
    ProgressBar products_pro,brands_pro,notice_pro,expiring_pro;

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

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsCount = (int) snapshot.getChildrenCount();
                products_pro.setProgress(itemsCount);
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
                brands_pro.setProgress(brandsCount);
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
                notice_pro.setProgress(lessProductsCount);
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
        products_pro = view.findViewById(R.id.products_pro);
        tvProductsCount = view.findViewById(R.id.tvProductsCount);
        brands_pro = view.findViewById(R.id.brands_pro);
        tvBrandsCount = view.findViewById(R.id.tvBrandsCount);
        notice_pro = view.findViewById(R.id.notice_pro);
        tvNoticeCount = view.findViewById(R.id.tvNoticeCount);


    }

}