package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.myapplication.Adapters.AdapterProduct;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterProduct adapterProduct;
    FirebaseUser user;
    EditText inputSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = view.findViewById(R.id.rvProducts);
        inputSearch = view.findViewById(R.id.inputSearchProduct);


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Products"), ModelProduct.class)
                        .build();

        adapterProduct = new AdapterProduct(options);
        recyclerView.setAdapter(adapterProduct);

        return view;
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

    private void searchData(){
        String product = inputSearch.getText().toString().trim();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseRecyclerOptions<ModelProduct> options =
                new FirebaseRecyclerOptions.Builder<ModelProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Products")
                                .orderByChild("ProductName").startAt(product).endAt(product+"\ufaff"), ModelProduct.class)
                        .build();

        adapterProduct = new AdapterProduct(options);
        adapterProduct.startListening();
        recyclerView.setAdapter(adapterProduct);
    }
}
