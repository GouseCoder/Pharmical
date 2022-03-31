package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Models.ModelSupplier;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSupplierFragment extends Fragment {
    FirebaseUser user;
    String currentuserId;
    EditText Name, Email, Contact, Address, DistributingProduct;
    Button btnaddseller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_supplier, container, false);
        init(view);
        btnaddseller.setOnClickListener(v -> validateData());
        return view;
    }

    private void validateData() {
        String sellerName = Name.getText().toString().trim();
        String sellerEmail = Email.getText().toString().trim();
        String sellerContact = Contact.getText().toString().trim();
        String sellerAddress = Address.getText().toString().trim();
        if (sellerName.isEmpty()) {
            Name.setError("Enter Name");
            Name.requestFocus();
            return;
        }
        if (sellerName.length() < 4) {
            Name.setError("Name Should Be Up To 4 Character");
            Name.requestFocus();
            return;
        }
        if (sellerEmail.isEmpty()){
            Email.setError("Enter email");
            Email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(sellerEmail).matches())
        if (sellerContact.isEmpty()) {
            Contact.setError("Enter Mobile Number");
            Contact.requestFocus();
            return;
        }
        if (sellerContact.length() != 10) {
            Contact.setError("Enter Valid Mobile Number");
            Contact.requestFocus();
            return;
        }
        if (sellerAddress.isEmpty()) {
            Address.setError("Enter Address");
            Address.requestFocus();
            return;
        }
        if (sellerAddress.length() < 7) {
            Address.setError("Address Should Be Up To 7 Character");
            Address.requestFocus();
            return;
        }
        else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebasedatabase.getReference("users").child(uid).child("Suppliers");

            String name_s = Name.getText().toString();
            String email_s = Email.getText().toString();
            String contact_s = Contact.getText().toString();
            String address_s = Address.getText().toString();
            String distributingProduct_s = DistributingProduct.getText().toString();

            ModelSupplier storingdatass = new ModelSupplier(name_s, email_s, contact_s, address_s, distributingProduct_s);
            myRef.child(name_s).setValue(storingdatass);
            Toast.makeText(getContext(),"Supplier Added",Toast.LENGTH_SHORT).show();
            makeEmpty();
        }

    }

    private void makeEmpty(){
        Name.setText("");
        Email.setText("");
        Contact.setText("");
        Address.setText("");
        DistributingProduct.setText("");
    }


    private void init(View view){
        Name = view.findViewById(R.id.inputSellerName);
        Email = view.findViewById(R.id.inputSellerEmail);
        Contact = view.findViewById(R.id.inputSellerMobile);
        Address = view.findViewById(R.id.inputSellerAddress);
        DistributingProduct = view.findViewById(R.id.inputSellerProduct);
        btnaddseller = view.findViewById(R.id.btnAddSeller);

    }
}