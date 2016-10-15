package com.example.moham.navdrawerexample1.SignUp;

/**
 * Created by moham on 10/15/2016.
 */

class Inf_Field_ClassModel {
    private byte[] image;
    private String Fname;
    private String Lname;
    private String Uname;
    private String Phone;

    byte[] getImage() {
        return image;
    }

    void setImage(byte[] image) {
        this.image = image;
    }

    String getFname() {
        return Fname;
    }

    void setFname(String fname) {
        Fname = fname;
    }

    String getLname() {
        return Lname;
    }

    void setLname(String lname) {
        Lname = lname;
    }

    String getUname() {
        return Uname;
    }

    void setUname(String uname) {
        Uname = uname;
    }

    String getPhone() {
        return Phone;
    }

    void setPhone(String phone) {
        Phone = phone;
    }
}
