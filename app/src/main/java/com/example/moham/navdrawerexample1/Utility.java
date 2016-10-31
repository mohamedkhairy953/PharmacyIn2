package com.example.moham.navdrawerexample1;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.SignUp.Inf_Field_ClassModel;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.channels.Channels;
import java.util.Stack;

/**
 * Created by moham on 10/13/2016.
 */

public class Utility {
    public final static int SIGNUP_TYP_FG_NUM = 1;
    public final static int USER_SIGNUP_FG_NUM = 2;
    public final static int DOCTOR_SIGNUP_FG_NUM = 3;
    public final static int INF_FIELD_FG_NUM = 4;
    public static String FRAG_NUM_key = "fg_num";

    public static boolean if_someone_loggedin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }

    public static void addModelToFirebase(Inf_Field_ClassModel model, Activity context) {
        Firebase.setAndroidContext(context);
        Firebase firebase = new Firebase("https://pharmacyin-89080.firebaseio.com/Users");
        Firebase UserName_child = firebase.child(model.getUid());
        UserName_child.child("FName").setValue(model.getFname());
        UserName_child.child("UName").setValue(model.getUname());
        UserName_child.child("LName").setValue(model.getLname());
        UserName_child.child("phone").setValue(model.getPhone());
        UserName_child.child("Location").setValue(model.getLocation());
        try {
            storeImageToFirebase(model.getImageUri(), model.getUid(), context);
        } catch (Exception d) {
            Toast.makeText(context, d.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private static void storeImageToFirebase(Uri uri, String uid, final Activity context) {
        StorageReference firebase_ref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pharmacyin-89080.appspot.com");
        UploadTask uploadTask = firebase_ref.child("Photos").child(uid).putFile(uri);
        uploadTask.addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
