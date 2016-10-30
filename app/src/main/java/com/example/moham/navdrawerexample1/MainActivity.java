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

import com.example.moham.navdrawerexample1.SignUp.Doctor_signup_fragment;
import com.example.moham.navdrawerexample1.SignUp.Information_Field_fragment;
import com.example.moham.navdrawerexample1.SignUp.Signup_type_fragment;
import com.example.moham.navdrawerexample1.SignUp.User_signup_fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* if (Utility.if_someone_loggedin())
            startActivity(new Intent(MainActivity.this, NavDrawer_Activity.class));*/
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_signup, new Signup_type_fragment()).commit();

        } else
            switch (savedInstanceState.getInt(Utility.FRAG_NUM_key, 33)) {
                case Utility.DOCTOR_SIGNUP_FG_NUM:
                    getSupportFragmentManager().beginTransaction().add(R.id.container_signup, new Doctor_signup_fragment()).commit();

                    break;
                case Utility.INF_FIELD_FG_NUM:
                    getSupportFragmentManager().beginTransaction().add(R.id.container_signup, new Information_Field_fragment()).commit();

                    break;
                case Utility.USER_SIGNUP_FG_NUM:
                    getSupportFragmentManager().beginTransaction().add(R.id.container_signup, new User_signup_fragment()).commit();

                    break;
                case Utility.SIGNUP_TYP_FG_NUM:
                    getSupportFragmentManager().beginTransaction().add(R.id.container_signup, new Signup_type_fragment()).commit();

                    break;

            }

    }


}
