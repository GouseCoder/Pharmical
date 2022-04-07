package com.example.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    FirebaseAuth fauth;
    Button button;
    TextView registertext, forgotpassword;
    ImageView btnGoogle;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextInputLayout email_var, password_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("You're getting logged in");

        button = findViewById(R.id.loginbutton);
        registertext = findViewById(R.id.registertext);
        forgotpassword = findViewById(R.id.forgotpassword);
        email_var = findViewById(R.id.username_text_field_design);
        password_var = findViewById(R.id.password);
        fauth = FirebaseAuth.getInstance();
        btnGoogle = findViewById(R.id.btnGoogle);

        if(fauth.getCurrentUser()!=null){
            sendToMain();
        }

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotpasswdActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(v->validateData());

    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                sendToMain();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void validateData() {
        String email = email_var.getEditText().getText().toString();
        String password_ = password_var.getEditText().getText().toString();
        if(email.isEmpty()){
            email_var.setError("Enter email");
            email_var.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_var.setError("Enter Valid Email");
            email_var.requestFocus();
            return;
        }
        if (password_.isEmpty()) {
            password_var.setError("Enter Password");
            password_var.requestFocus();
            return;
        }
        if (password_.length() < 7) {
            password_var.setError("Enter Valid Password");
            password_var.requestFocus();
            return;
        } else {
            String email_s = email_var.getEditText().getText().toString();
            String password_s = password_var.getEditText().getText().toString();
            progressDialog.show();
            fauth.signInWithEmailAndPassword(email_s,password_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Loggedin Successfully", Toast.LENGTH_SHORT).show();
                        sendToMain();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
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