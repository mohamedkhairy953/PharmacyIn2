package com.example.moham.navdrawerexample1.SignUp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.LoginActivity;
import com.example.moham.navdrawerexample1.MainActivity;
import com.example.moham.navdrawerexample1.NavDrawer_Activity;
import com.example.moham.navdrawerexample1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;


public class User_signup_fragment extends Fragment implements View.OnClickListener {

    Button btn_register;
    EditText email_edittext, password_edittext;
    TextView textView_signup, textview_toolbar;
    ProgressDialog progressDialog;
    Toolbar toolbar_signup;
    private FirebaseAuth firebaseAuth;


    public User_signup_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        View view_userSignUp = inflater.inflate(R.layout.fragment_user_signup, container, false);
        initialize_components(view_userSignUp);
        return view_userSignUp;
    }

    private void initialize_components(View v) {
        btn_register = (Button) v.findViewById(R.id.button_register);
        email_edittext = (EditText) v.findViewById(R.id.edittextemail);
        password_edittext = (EditText) v.findViewById(R.id.edittextpassword);
        textView_signup = (TextView) v.findViewById(R.id.textview_signup);
        textview_toolbar = (TextView) v.findViewById(R.id.textview_toolbar);
        toolbar_signup = (Toolbar) v.findViewById(R.id.toolbar_signup);
        textview_toolbar.setText("Sign up");
        progressDialog = new ProgressDialog(getContext());
        btn_register.setOnClickListener(this);
        textView_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_register) {
            signUpMethod();
        } else if (v == textView_signup) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void signUpMethod() {
        String email = email_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(getActivity(), " please insert your mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(getActivity(), " please insert your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registeration mail ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                   switchToFragment(new Information_Field_fragment());
                } else {
                    Toast.makeText(getActivity(), task.getResult().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    void switchToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_signup, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

