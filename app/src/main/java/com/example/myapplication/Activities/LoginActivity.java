package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //FirebaseAuth auth;
    //GoogleSignInClient mGoogleSignInClient;
    Button button;
    TextView registertext;
    ImageView btnGoogle;
    TextInputLayout username_var, password_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init
        button = findViewById(R.id.loginbutton);
        registertext = findViewById(R.id.registertext);
        username_var = findViewById(R.id.username_text_field_design);
        password_var = findViewById(R.id.password);
        btnGoogle = findViewById(R.id.btnGoogle);

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.myapplication.Activities.SignupActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(v->validateData());

    }

    private void validateData()
    {
        String username_ = username_var.getEditText().getText().toString();
        String password_ = password_var.getEditText().getText().toString();
        if(username_.isEmpty()){
            username_var.setError("Enter Name");
            username_var.requestFocus();
            return;
        }
        if (password_.isEmpty())
        {
            password_var.setError("Enter Password");
            password_var.requestFocus();
            return;
        }
        if (password_.length()<7)
        {
            password_var.setError("Enter Valid Password");
            password_var.requestFocus();
            return;
        }
        else
        {
            final String medname_data = username_var.getEditText().getText().toString();
            final String password_data = password_var.getEditText().getText().toString();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference("users");
            Query check_username = myRef.orderByChild("medname").equalTo(medname_data);
            check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        username_var.setError(null);
                        username_var.setErrorEnabled(false);
                        String password_check = dataSnapshot.child(medname_data).child("password").getValue(String.class);
                        if(password_check.equals(password_data)){
                            password_var.setError(null);
                            password_var.setErrorEnabled(false);
                            Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();

                            sendToMain();
                            finish();

                        }else {
                            password_var.setError("Incorrect Password");
                        }
                    }else {
                        username_var.setError("No user found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }




    private void sendToMain()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

}