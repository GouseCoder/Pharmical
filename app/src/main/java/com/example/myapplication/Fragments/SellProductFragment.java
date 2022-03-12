package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Activities.SellProductActivity;
import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.example.myapplication.helper.DbHandler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SellProductFragment extends Fragment {
    AdapterProduct adapterProduct;
    private CardView cvSearchProduct;
    private RecyclerView recyclerView;
    private TextView tvTotalPrice, tvSalePrice;
    private ProgressBar progressBar;
    private LinearLayout layoutTotal;
    private List<ModelProduct> modelProductList;
    //Bottom Sheet Dialog
    private BottomSheetDialog bottomSheetDialog;
    private String[] cameraPermission;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_product, container, false);

        init(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        cvSearchProduct.setOnClickListener(view1 -> sendToSellProductActivity());
        layoutTotal.setVisibility(View.GONE);

        return view;
    }
    private void sendToSellProductActivity() {
        Intent intent = new Intent(getContext(), SellProductActivity.class);
        startActivityForResult(intent, 200);
    }



    private void init(View view) {
        cvSearchProduct = view.findViewById(R.id.cvSearchProduct);
        layoutTotal = view.findViewById(R.id.layoutTotal);
        recyclerView = view.findViewById(R.id.rvSellEditable);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        tvSalePrice = view.findViewById(R.id.tvSalePrice);
        progressBar = view.findViewById(R.id.progressBar);
        cameraPermission = new String[]{Manifest.permission.CAMERA};
    }

}