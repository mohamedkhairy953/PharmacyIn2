package com.example.moham.navdrawerexample1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.MainActivity;
import com.example.moham.navdrawerexample1.NavDrawer_Activity;
import com.example.moham.navdrawerexample1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText loginemail_edittext, loginpassword_edittext;
    TextView textView_signin;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextView textview_toolbar;
    Toolbar toolbar_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            // profile activity
            finish();
            startActivity(new Intent(LoginActivity.this, NavDrawer_Activity.class));
        }
        btn_login = (Button) findViewById(R.id.button_login);
        loginemail_edittext = (EditText) findViewById(R.id.signin_edittextemail);
        loginpassword_edittext = (EditText) findViewById(R.id.signin_edittextpassword);
        textView_signin = (TextView) findViewById(R.id.textview_signin);
        textview_toolbar = (TextView) findViewById(R.id.textview_toolbar);
        toolbar_signin = (Toolbar) findViewById(R.id.toolbar_signin);
        textview_toolbar.setText("Sign in");
        progressDialog = new ProgressDialog(this);
        btn_login.setOnClickListener(this);
        textView_signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_login) {
            LoginMethod();
        } else if (v == textView_signin) {
            // go to signup activity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void LoginMethod() {
        String email = loginemail_edittext.getText().toString().trim();
        String password = loginpassword_edittext.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(LoginActivity.this, " please insert your mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(LoginActivity.this, " please insert your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Signing in your e_mail ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, NavDrawer_Activity.class));
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

