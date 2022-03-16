package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.SellProductActivity;
import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Adapters.AdapterSaleEditable;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.example.myapplication.helper.DbHandler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class SellProductFragment extends Fragment {

    private CardView cvSearchProduct;
    private RecyclerView recyclerView;
    private TextView tvTotalPrice, tvSalePrice;
    private ProgressBar progressBar;
    private LinearLayout layoutTotal;
    private List<ModelProduct> modelProductList;
    private List<ModelSale> modelSaleList;
    private AdapterSaleEditable adapterSaleEditable;
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
        adapterSaleEditable = new AdapterSaleEditable(getContext(), modelSaleList, SellProductFragment.this);
        recyclerView.setAdapter(adapterSaleEditable);

        cvSearchProduct.setOnClickListener(view1 -> sendToSellProductActivity());
        layoutTotal.setVisibility(View.GONE);

        return view;
    }
    private void sendToSellProductActivity() {
        Intent intent = new Intent(getContext(), SellProductActivity.class);
        startActivityForResult(intent, 200);
    }

    public void updateTotalValue(int totalPrice, int salePrice) {
        tvTotalPrice.setText(String.valueOf(totalPrice));
        tvSalePrice.setText(String.valueOf(salePrice));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sale, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && data != null) {
            String productId = data.getStringExtra("intentProductId");
            Toast.makeText(getContext(), productId, Toast.LENGTH_SHORT).show();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
            //prepareToSellProduct();
        }
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