package com.example.moham.navdrawerexample1.SignUp;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.LoginActivity;
import com.example.moham.navdrawerexample1.MainActivity;
import com.example.moham.navdrawerexample1.NavDrawer_Activity;
import com.example.moham.navdrawerexample1.R;
import com.example.moham.navdrawerexample1.Utility;
import com.google.firebase.auth.FirebaseAuth;


public class Signup_type_fragment extends Fragment implements View.OnClickListener {


    Button signup_as_doc, signup_as_user;
    TextView to_login;
    private LocationManager locationManager;

    public Signup_type_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_signuptype = inflater.inflate(R.layout.fragment_signup_type, container, false);
        initialize_components(view_signuptype);
        Toast.makeText(getActivity(), "PGps", Toast.LENGTH_LONG).show();

        if (Utility.if_someone_loggedin()) {

            startActivity(new Intent(getActivity(), NavDrawer_Activity.class));
            getActivity().finish();
        } else {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getActivity(), "Please open your Gps", Toast.LENGTH_LONG).show();
            }
        }
        return view_signuptype;
    }

    private void initialize_components(View v) {
        signup_as_doc = (Button) v.findViewById(R.id.doc_signup_btn_signuptype);
        signup_as_user = (Button) v.findViewById(R.id.user_signup_btn_signuptype);
        to_login = (TextView) v.findViewById(R.id.textview_to_login);
        signup_as_doc.setOnClickListener(this);
        signup_as_user.setOnClickListener(this);
        to_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doc_signup_btn_signuptype:
                switchToFragment(new Doctor_signup_fragment());
                break;
            case R.id.user_signup_btn_signuptype:
                switchToFragment(new User_signup_fragment());
                break;
            case R.id.textview_to_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

        }
    }

    void switchToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_signup, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Utility.FRAG_NUM_key, Utility.SIGNUP_TYP_FG_NUM);
    }
}
