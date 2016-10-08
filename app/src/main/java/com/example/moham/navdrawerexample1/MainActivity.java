package com.example.moham.navdrawerexample1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_register;
    EditText email_edittext, password_edittext;
    TextView textView_signup,textview_toolbar;
    ProgressDialog progressDialog;
    Toolbar toolbar_signup;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            // profile activity
            finish();
             startActivity(new Intent(MainActivity.this,NavDrawer_Activity.class));
        }
        btn_register = (Button) findViewById(R.id.button_register);
        email_edittext = (EditText) findViewById(R.id.edittextemail);
        password_edittext = (EditText) findViewById(R.id.edittextpassword);
        textView_signup = (TextView) findViewById(R.id.textview_signup);
        textview_toolbar = (TextView) findViewById(R.id.textview_toolbar);
        toolbar_signup= (Toolbar) findViewById(R.id.toolbar_signup);
        textview_toolbar.setText("Sign up");
        progressDialog = new ProgressDialog(this);
        btn_register.setOnClickListener(this);
        textView_signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_register) {
            signUpMethod();
        } else if (v == textView_signup) {
            // go to login activity
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    private void signUpMethod() {
        String email = email_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(MainActivity.this, " please insert your mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(MainActivity.this, " please insert your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registeration mail ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(MainActivity.this,NavDrawer_Activity.class));
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
