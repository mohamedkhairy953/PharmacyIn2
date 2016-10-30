package com.example.moham.navdrawerexample1.SignUp;

import android.net.Uri;

import java.net.URI;

/**
 * Created by moham on 10/15/2016.
 */

public class Inf_Field_ClassModel {
    private Uri imageUri;
    private String Fname;
    private String Uid;
    private String Lname;
    private String Uname;
    private String Phone;
    private String Location;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getLocation() {
        return Location;
    }

    void setLocation(String location) throws InfModelException {
        if (location.equals(""))
            throw new InfModelException("Please click again to confirm location");
        else
            Location = location;
    }


    public String getFname() {
        return Fname;
    }

    void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    void setLname(String lname) {
        Lname = lname;
    }

    public String getUname() {
        return Uname;
    }

    void setUname(String uname) {
        Uname = uname;
    }

    public String getPhone() {
        return Phone;
    }

    void setPhone(String phone) {
        Phone = phone;
    }
}
