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
import com.example.myapplication.Adapters.AdapterProductSale;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.example.myapplication.helper.DbHandler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellProductActivity extends AppCompatActivity {
    private AdapterProductSale adapterProductSale;
    private RecyclerView recyclerView;
    private List<ModelProduct> products;
    private String productId;
    DatabaseReference reference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        recyclerView = findViewById(R.id.rvSellProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //productId = getIntent().getExtras().get("productId").toString();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products");
        setRevyclerView();


    }
    private void setRevyclerView(){
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(reference, ModelProduct.class)
                        .build();

        adapterProductSale = new AdapterProductSale(getApplicationContext(), options);
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



}