package com.example.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Models.ModelUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    Button button4;
    TextInputLayout email_var, medname_var,contact_var, pass_var;
    ProgressDialog progressDialog;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");

        button4 = findViewById(R.id.button4);
        email_var = findViewById(R.id.emailfield);
        medname_var = findViewById(R.id.medicalnmenamefield);
        contact_var = findViewById(R.id.contactfield);
        pass_var = findViewById(R.id.passwordfield);

        fauth = FirebaseAuth.getInstance();
        if(fauth.getCurrentUser()!=null){
            sendToMain();
        }


        button4.setOnClickListener(v->validateData());

    }
        //
    private void validateData(){
        String email = email_var.getEditText().getText().toString();
        String medname = medname_var.getEditText().getText().toString();
        String contact = contact_var.getEditText().getText().toString();
        String password = pass_var.getEditText().getText().toString();
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
        if(medname.isEmpty()){
            medname_var.setError("Enter Name");
            medname_var.requestFocus();
            return;
        }
        if (medname.length()<3 || medname.length()> 30)
        {
            medname_var.setError("Invalid Name Length");
            medname_var.requestFocus();
            return;
        }
        if(contact.isEmpty()){
            contact_var.setError("Enter contact");
            contact_var.requestFocus();
            return;
        }
        if(contact.length()<10){
            contact_var.setError("Number must be 10 digit");
            contact_var.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            pass_var.setError("Enter Password");
            pass_var.requestFocus();
            return;
        }
        if (password.length()<7)
        {
            pass_var.setError("Password Should Be Greater Than 7 Character");
            pass_var.requestFocus();
            return;
        }
        else
        {
                    String email_s = email_var.getEditText().getText().toString();
                    String medname_s = medname_var.getEditText().getText().toString();
                    String contact_s = contact_var.getEditText().getText().toString();
                    String password_s = pass_var.getEditText().getText().toString();

                    fauth.createUserWithEmailAndPassword(email_s,password_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                ModelUser user = new ModelUser(email_s,medname_s,contact_s);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.show();
                                            sendToLogin();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }


        }

    public void sendToLogin()
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }
    private void sendToMain()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

}