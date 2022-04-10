package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


import com.example.myapplication.Adapters.AdapterSaleEditable;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SellProductActivity extends AppCompatActivity {
    String productId;
    private AdapterSaleEditable adapterSaleEditable;
    private RecyclerView recyclerView;
    private TextView tvTotalPrice, tvSalePrice;
    FirebaseUser user;
    DatabaseReference reference, reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        recyclerView = findViewById(R.id.rvSellEditable);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvSalePrice = findViewById(R.id.tvSalePrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        productId = getIntent().getExtras().get("productID").toString();
        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId);
        reference1 = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales");

        getProducts();
        setRecyclerView();

    }
    private void getProducts() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        int saleQuantity = 0;
        int saleDiscount = 0;
        int salePrice = 0;
        int productTotalPrice = 0;
        Date currentTime = Calendar.getInstance().getTime();
        String createdAt = currentTime.toString();
        String productID = productId;

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String categoryList = snapshot.child("productCategory").getValue().toString();
                    String itemList = snapshot.child("productName").getValue().toString();
                    String sizeList = snapshot.child("productSize").getValue().toString();
                    String brandList = snapshot.child("productBrand").getValue().toString();
                    int PriceList = Integer.parseInt(snapshot.child("productPrice").getValue().toString());
                    String Locationlist = snapshot.child("productLocation").getValue().toString();
                    int ProductQuantity = Integer.parseInt(snapshot.child("productQuantity").getValue().toString());
                    String ManufactureList = snapshot.child("productManufacture").getValue().toString();
                    String ExpireList = snapshot.child("productExpire").getValue().toString();

                ModelSale sale = new ModelSale(productID, categoryList, itemList, sizeList,
                        brandList, PriceList, Locationlist, ProductQuantity, ManufactureList, ExpireList,
                        saleQuantity, saleDiscount, salePrice, createdAt);

                reference1.push().setValue(sale);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void setRecyclerView(){
        FirebaseRecyclerOptions<ModelSale> options = new FirebaseRecyclerOptions.Builder<ModelSale>()
                .setQuery(reference1, ModelSale.class)
                .build();

        adapterSaleEditable = new AdapterSaleEditable(options);
        recyclerView.setAdapter(adapterSaleEditable);
        }

    public void updateTotalValue(int totalPrice, int salePrice) {
        tvTotalPrice.setText(String.valueOf(totalPrice));
        tvSalePrice.setText(String.valueOf(salePrice));
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterSaleEditable.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterSaleEditable.stopListening();
    }

}