package com.example.moham.navdrawerexample1;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Stack;

/**
 * Created by moham on 10/13/2016.
 */

public class Utility {
    public static boolean if_someone_loggedin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }
}
