package com.example.moham.navdrawerexample1.SignUp;

/**
 * Created by moham on 10/15/2016.
 */

public class Inf_Field_ClassModel {
    private byte[] image;
    private String Fname;
    private String Lname;
    private String Uname;
    private String Phone;
    private String Location;

    public String getLocation() {
        return Location;
    }

    void setLocation(String location) {
        Location = location;
    }

    public byte[] getImage() {
        return image;
    }

     void setImage(byte[] image) {
        this.image = image;
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
