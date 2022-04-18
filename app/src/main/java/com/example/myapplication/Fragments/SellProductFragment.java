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
import com.example.myapplication.Adapters.AdapterProductSale;
import com.example.myapplication.Adapters.AdapterSaleEditable;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.example.myapplication.helper.DbHandler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellProductFragment extends Fragment {
    FirebaseUser user;
    private CardView cvSearchProduct;
    private RecyclerView recyclerView;
    private TextView tvTotalPrice, tvSalePrice;
    private LinearLayout layoutTotal;
    DatabaseReference reference0;
    private AdapterProductSale adapterProductSale;
    private Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_product, container, false);

        init(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference0 = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products");
        setRecyclerView();

        return view;
    }
    private void setRecyclerView(){

        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(reference0, ModelProduct.class)
                        .build();

        adapterProductSale = new AdapterProductSale(getContext(), options);
        recyclerView.setAdapter(adapterProductSale);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapterProductSale.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterProductSale.stopListening();
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
        recyclerView = view.findViewById(R.id.rvSellProducts);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        tvSalePrice = view.findViewById(R.id.tvSalePrice);
        intent = requireActivity().getIntent();
    }

}