package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ContactUsFragment extends Fragment {
    EditText recepientmail, subject, message;
    private String  mobileNumber;
    private ImageView btnCall;
    private String[] callPermission;
    Button btnSendQuery;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        init(view);
        mobileNumber = "9324579903";
        btnSendQuery.setOnClickListener(v -> sendQuery());
        btnCall.setOnClickListener(v->makeCall());
        return view;
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
        boolean permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                == (PackageManager.PERMISSION_GRANTED);
        return permission;
    }

    private void requestCallPermission()
    {
        ActivityCompat.requestPermissions(getActivity(),callPermission,100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0)
        {
            boolean callPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (callPermissionAccepted)
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                checkCallPermission();
        }
    }

    public void sendQuery(){
        String Recepient = recepientmail.getText().toString().trim();
        String subjectText = subject.getText().toString().trim();
        String Message = message.getText().toString().trim();


        sendEmail(Recepient, subjectText, Message);
    }

    private void sendEmail(String recepient, String subjectText, String Message) {


        recepient = "pharmicalinventory@gmail.com";
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto"));
        mEmailIntent.setType("text/plain");
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String [] {recepient});
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, Message);

        try {
            startActivity(Intent.createChooser(mEmailIntent, "Choose an email Client"));
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void init(View view)
    {
        recepientmail = view.findViewById(R.id.inputEmail);
        subject = view.findViewById(R.id.inputSubject);
        message = view.findViewById(R.id.inputMessage);
        btnSendQuery = view.findViewById(R.id.btnsend);
        btnCall = view.findViewById(R.id.btnCall);
        callPermission = new String[]{Manifest.permission.CALL_PHONE};
    }
}