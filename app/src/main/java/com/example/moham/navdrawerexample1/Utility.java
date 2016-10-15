package com.example.moham.navdrawerexample1;

import android.content.Context;

import com.example.moham.navdrawerexample1.SignUp.Inf_Field_ClassModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Stack;

/**
 * Created by moham on 10/13/2016.
 */

public class Utility {
    public static boolean if_someone_loggedin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }
    public static void addModelToFirebase(Inf_Field_ClassModel model, Context context){
        Firebase.setAndroidContext(context);
        Firebase  firebase=new Firebase("https://pharmacyin-89080.firebaseio.com/Users");
        Firebase UserName_child = firebase.child(model.getUname());
        UserName_child.child("FName").setValue(model.getFname());
        UserName_child.child("LName").setValue(model.getLname());
        UserName_child.child("phone").setValue(model.getPhone());
       // UserName_child.child("Image").setValue(model.getImage());
        UserName_child.child("Location").setValue(model.getLocation());



    }
}
