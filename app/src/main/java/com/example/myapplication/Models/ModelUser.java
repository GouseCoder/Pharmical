package com.example.myapplication.Models;

public class ModelUser {
    String email,medname,contact,password,confirmpassword;

    public ModelUser() {

    }

    public ModelUser(String email, String medname, String contact, String password, String confirmpassword) {
        this.email = email;
        this.medname = medname;
        this.contact = contact;
        this.password = password;
        this.confirmpassword = confirmpassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}


