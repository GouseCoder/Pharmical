package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpasswdActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordBtn;
    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswd);

        progressDialog = new ProgressDialog(ForgotpasswdActivity.this);
        progressDialog.setTitle("Reset Password");
        progressDialog.setMessage("Password is resetting");

        emailEditText = findViewById(R.id.resetemail);
        resetPasswordBtn = findViewById(R.id.resetbtn);
        auth = FirebaseAuth.getInstance();

        resetPasswordBtn.setOnClickListener(v -> validateData());
    }

    private void validateData(){
        String email = emailEditText.getText().toString();
        if(email.isEmpty()){
            emailEditText.setError("Enter email");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Enter Valid Email");
            emailEditText.requestFocus();
            return;
        }
        else
        {
            progressDialog.show();
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Check your email to set password!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Something wrong happened, Try again", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}
