package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.etc.Scanner;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;


public class AddProductFragment extends Fragment {
    int price, quantity;
    String  barCode, manMonthName, manYearName, expMonthName, expYearName = "";
    EditText inputProductPrice, inputProductQuantity, inputProductBarcode;
    Button btnAddProduct;
    private TextInputLayout tilBarcode;
    SearchableSpinner spbrand, spcategory, spsize, splocation, spitem, spinnerManufactureMonth, spinnerManufactureYear, spinnerExpireMonth, spinnerExpireYear;;
    DatabaseReference databaseReference0, databaseReference1, databaseReference2, databaseReference3, databaseReference4;
    ArrayList<String> brandList = new ArrayList<String>();
    ArrayList<String>categoryList = new ArrayList<String>();
    ArrayList<String>sizeList = new ArrayList<String>();
    ArrayList<String>locationList = new ArrayList<String>();
    ArrayList<String>itemList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        init(view);
        setSpinner();

        tilBarcode.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });




        //addProduct();

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String brands = ds.getValue(String.class);
                    brandList.add(brands);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, brandList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spbrand.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                    String categories = ds2.getValue(String.class);
                    categoryList.add(categories);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spcategory.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds3 : dataSnapshot.getChildren()) {
                    String sizes = ds3.getValue(String.class);
                    sizeList.add(sizes);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sizeList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spsize.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds4 : dataSnapshot.getChildren()) {
                    String locations = ds4.getValue(String.class);
                    locationList.add(locations);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, locationList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                splocation.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds5 : dataSnapshot.getChildren()) {
                    String items = ds5.getValue(String.class);
                    itemList.add(items);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spitem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });





        return view;
    }


    private void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setCaptureActivity(Scanner.class);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Sell Products By Scanning Bar Code");
        intentIntegrator.forSupportFragment(AddProductFragment.this).initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null)
                inputProductBarcode.setText(String.valueOf(result.getContents()));
            else
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init(View view) {

        databaseReference0 = FirebaseDatabase.getInstance().getReference("ProductName");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("ProductBrand");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("ProductCategory");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("ProductSize");
        databaseReference4 = FirebaseDatabase.getInstance().getReference("ProductLocation");

        spbrand = view.findViewById(R.id.spinnerBrand);
        spcategory = view.findViewById(R.id.spinnerCategory);
        spsize = view.findViewById(R.id.spinnerSize);
        splocation = view.findViewById(R.id.spinnerLocation);
        spitem = view.findViewById(R.id.spinnerItem);
        spinnerManufactureMonth = view.findViewById(R.id.spinnerManufactureMonth);
        spinnerManufactureYear = view.findViewById(R.id.spinnerManufactureYear);
        spinnerExpireMonth = view.findViewById(R.id.spinnerExpireMonth);
        spinnerExpireYear = view.findViewById(R.id.spinnerExpireYear);
        inputProductPrice = view.findViewById(R.id.inputProductPrice);
        inputProductQuantity = view.findViewById(R.id.inputProductQuantity);
        inputProductBarcode = view.findViewById(R.id.inputProductBarcode);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        tilBarcode = view.findViewById(R.id.tilBarcode);
        barCode = inputProductBarcode.getText().toString().trim();



    }

    private void setSpinner() {
        ArrayList<String> manMonth = new ArrayList<>();
        ArrayList<String> manYear = new ArrayList<>();
        ArrayList<String> expMonth = new ArrayList<>();
        ArrayList<String> expYear = new ArrayList<>();

        manMonth.add("January");
        manMonth.add("February");
        manMonth.add("March");
        manMonth.add("April");
        manMonth.add("May");
        manMonth.add("June");
        manMonth.add("July");
        manMonth.add("August");
        manMonth.add("September");
        manMonth.add("October");
        manMonth.add("November");
        manMonth.add("December");

        manYear.add("2024");
        manYear.add("2023");
        manYear.add("2022");
        manYear.add("2021");
        manYear.add("2020");
        manYear.add("2019");
        manYear.add("2018");
        manYear.add("2017");
        manYear.add("2016");
        manYear.add("2015");
        manYear.add("2014");
        manYear.add("2013");
        manYear.add("2012");

        expMonth.add("January");
        expMonth.add("February");
        expMonth.add("March");
        expMonth.add("April");
        expMonth.add("May");
        expMonth.add("June");
        expMonth.add("July");
        expMonth.add("August");
        expMonth.add("September");
        expMonth.add("October");
        expMonth.add("November");
        expMonth.add("December");


        expYear.add("2027");
        expYear.add("2026");
        expYear.add("2025");
        expYear.add("2024");
        expYear.add("2023");
        expYear.add("2022");
        expYear.add("2021");
        expYear.add("2020");
        expYear.add("2019");
        expYear.add("2018");
        expYear.add("2017");
        expYear.add("2016");
        expYear.add("2015");
        expYear.add("2014");
        expYear.add("2013");
        expYear.add("2012");

        try {
            spinnerManufactureMonth.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, manMonth));
            spinnerManufactureYear.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, manYear));
            spinnerExpireMonth.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, expMonth));
            spinnerExpireYear.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, expYear));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        spinnerManufactureMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manMonthName = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerManufactureYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manYearName = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerExpireMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expMonthName = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerExpireYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expYearName = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getMonthNumberByName(String monthName)
    {
        String monthNumber = "0";
        switch (monthName.toLowerCase().trim())
        {
            case "january":
                monthNumber = "01";
                break;
            case "february":
                monthNumber = "02";
                break;
            case "march":
                monthNumber = "03";
                break;
            case "april":
                monthNumber = "04";
                break;
            case "may":
                monthNumber = "05";
                break;
            case "june":
                monthNumber = "06";
                break;
            case "july":
                monthNumber = "07";
                break;
            case "august":
                monthNumber = "08";
                break;
            case "september":
                monthNumber = "09";
                break;
            case "october":
                monthNumber = "10";
                break;
            case "november":
                monthNumber = "11";
                break;
            case "december":
                monthNumber = "12";
                break;
            default:
                monthNumber = "0";
        }
        return monthNumber;
    }





}