package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    FirebaseUser user;
    private String sellerID, mobileNumber;
    private Intent intent;
    private TextView tvSellerName,tvSellerAddress, tvSellerEmail, tvdistributingProduct;
    private Button btnSellerContact;
    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private String[] callPermission;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        init();

        btnSellerContact.setOnClickListener(v->makeCall());

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        sellerID = getIntent().getExtras().get("sellerID").toString();
        reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Suppliers").child(sellerID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String SellerName = snapshot.child("sellerName").getValue().toString();
                String SellerAddress = snapshot.child("sellerAddress").getValue().toString();
                String SellerContact = snapshot.child("sellerContactNumber").getValue().toString();
                String SellerEmail = snapshot.child("sellerEmail").getValue().toString();
                String distributingProduct = snapshot.child("distributingProduct").getValue().toString();

                mobileNumber = SellerContact;
                tvSellerName.setText(SellerName);
                tvSellerAddress.setText(SellerAddress);
                tvSellerEmail.setText(SellerEmail);
                tvdistributingProduct.setText(distributingProduct);
                btnSellerContact.setText(SellerContact);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void makeCall()
    {
        if (checkCallPermission())
        {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+mobileNumber));
            startActivity(intent);
        }
        else
            requestCallPermission();
    }

    private boolean checkCallPermission()
    {
        boolean permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                == (PackageManager.PERMISSION_GRANTED);
        return permission;
    }

    private void requestCallPermission()
    {
        ActivityCompat.requestPermissions(this,callPermission,100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0)
        {
            boolean callPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (callPermissionAccepted)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                checkCallPermission();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init()
    {

        tvSellerName = findViewById(R.id.tvSellerName);
        tvSellerAddress = findViewById(R.id.tvSellerAddress);
        tvSellerEmail = findViewById(R.id.tvSellerEmail);
        tvdistributingProduct = findViewById(R.id.tvdistributingProduct);
        btnSellerContact = findViewById(R.id.btnSellerContact);
        intent = getIntent();
        callPermission = new String[]{Manifest.permission.CALL_PHONE};
    }
}