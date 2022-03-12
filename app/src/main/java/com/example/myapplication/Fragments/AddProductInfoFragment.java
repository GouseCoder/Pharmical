package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class AddProductInfoFragment extends Fragment {
    String textData = "";
    EditText inputItemName, inputBrandName, inputCategory, inputSize, inputLocation;
    Button additem, addBrand, addloc, addSize, addCategory;
    DatabaseReference databaseReference0, databaseReference1, databaseReference2, databaseReference3, databaseReference4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product_info, container, false);
        inputItemName = view.findViewById(R.id.inputItemName);
        inputBrandName = view.findViewById(R.id.inputBrandName);
        inputCategory = view.findViewById(R.id.inputCategory);
        inputSize = view.findViewById(R.id.inputSize);
        inputLocation = view.findViewById(R.id.inputLocation);
        view.findViewById(R.id.btnAddItem).setOnClickListener(mListener);
        view.findViewById(R.id.btnAddBrand).setOnClickListener(mListener);
        view.findViewById(R.id.btnAddCategory).setOnClickListener(mListener);
        view.findViewById(R.id.btnAddSize).setOnClickListener(mListener);
        view.findViewById(R.id.btnAddLocation).setOnClickListener(mListener);
        databaseReference0 = FirebaseDatabase.getInstance().getReference("ProductName");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("ProductBrand");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("ProductCategory");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("ProductSize");
        databaseReference4 = FirebaseDatabase.getInstance().getReference("ProductLocation");

        return view;
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnAddItem:
                    textData = inputItemName.getText().toString().toLowerCase().trim();
                    databaseReference0.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            inputItemName.setText("");
                            Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case R.id.btnAddBrand:
                    textData = inputBrandName.getText().toString().toLowerCase().trim();
                    databaseReference1.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            inputBrandName.setText("");
                            Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case R.id.btnAddCategory:

                    textData = inputCategory.getText().toString().toLowerCase().trim();
                    databaseReference2.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            inputCategory.setText("");
                            Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.btnAddSize:

                    textData = inputSize.getText().toString().toLowerCase().trim();
                    databaseReference3.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            inputSize.setText("");
                            Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        }
                    });

                case R.id.btnAddLocation:
                    textData = inputLocation.getText().toString().toLowerCase().trim();
                    databaseReference4.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            inputLocation.setText("");
                            Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };


}