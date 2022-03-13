package com.example.myapplication.Models;

public class ModelUser {
    String email,medname,contact;

    public ModelUser() {

    }

    public ModelUser(String email, String medname, String contact) {
        this.email = email;
        this.medname = medname;
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}


