package com.example.moham.navdrawerexample1.SignUp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moham.navdrawerexample1.R;
import com.example.moham.navdrawerexample1.Utility;


public class Doctor_signup_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public Doctor_signup_fragment() {
        // Required empty public constructor
    }

    public static Doctor_signup_fragment newInstance(String param1, String param2) {
        Doctor_signup_fragment fragment = new Doctor_signup_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_signup, container, false);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Utility.FRAG_NUM_key, Utility.DOCTOR_SIGNUP_FG_NUM);
    }

}
