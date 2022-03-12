package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.example.myapplication.helper.DbHandler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SellProductActivity extends AppCompatActivity {
    private AdapterProduct adapterProduct;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        recyclerView = findViewById(R.id.rvSellProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRevyclerView();
    }
    private void setRevyclerView(){
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), ModelProduct.class)
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



}