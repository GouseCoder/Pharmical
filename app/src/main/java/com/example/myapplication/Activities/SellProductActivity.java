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
    private List<ModelProduct> products;
    FirebaseUser user;
    DatabaseReference reference, reference1;
    String itemList, brandList, categoryList, sizeList, locationList, ManufactureList, ExpireList;
    int PriceList, QuantityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        recyclerView = findViewById(R.id.rvSellEditable);
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

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productCategory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productCategory = snapshot.getValue(String.class);
                categoryList = productCategory;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productName = snapshot.getValue(String.class);
                itemList = productName;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productSize").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productSize = snapshot.getValue(String.class);
                sizeList = productSize;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productBrand").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productBrand = snapshot.getValue(String.class);
                brandList = productBrand;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productPrice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int productPrice = snapshot.getValue(Integer.class);
                PriceList = productPrice;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productLocation = snapshot.getValue(String.class);
                locationList = productLocation;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productQuantity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int productQuantity = snapshot.getValue(int.class);
                QuantityList = productQuantity;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productManufacture").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productManufacture = snapshot.getValue(String.class);
                ManufactureList = productManufacture;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productId).child("productExpire").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productExpire = snapshot.getValue(String.class);
                ExpireList = productExpire;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ModelSale sale = new ModelSale(productID, categoryList, itemList, sizeList,
                brandList, PriceList, locationList, QuantityList, ManufactureList, ExpireList,
                saleQuantity, saleDiscount, salePrice, createdAt,productTotalPrice);

        reference1.push().setValue(sale);


    }
    private void setRecyclerView(){
        FirebaseRecyclerOptions<ModelSale> options = new FirebaseRecyclerOptions.Builder<ModelSale>()
                .setQuery(reference1, ModelSale.class)
                .build();

        adapterSaleEditable = new AdapterSaleEditable(getApplicationContext(),options);
        recyclerView.setAdapter(adapterSaleEditable);
        }

    private int ConvertIntoNumeric(String xVal)
    {
        try
        {
            return Integer.parseInt(xVal);
        }
        catch(Exception ex)
        {
            return 0;
        }
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