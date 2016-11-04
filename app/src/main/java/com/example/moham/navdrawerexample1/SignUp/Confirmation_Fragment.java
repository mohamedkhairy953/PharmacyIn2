package com.example.moham.navdrawerexample1.SignUp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.R;
import com.example.moham.navdrawerexample1.Utility;
import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 * Use the {@link Confirmation_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Confirmation_Fragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private String confirmationCode;
    private TextView textResendCode;
    private EditText edttextConfirm;


    public Confirmation_Fragment() {
        // Required empty public constructor
    }


    public static Confirmation_Fragment newInstance(String param1) {
        Confirmation_Fragment fragment = new Confirmation_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            confirmationCode = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation_, container, false);
        intializeComponents(view);
        if (edttextConfirm.getText().toString().equals(confirmationCode)) {
            Toast.makeText(getActivity(), " Confirmed ", Toast.LENGTH_SHORT).show();
            switchToFragment(new Information_Field_fragment());
        }
        return view;
    }

    private void intializeComponents(View view) {
        edttextConfirm = (EditText) view.findViewById(R.id.edttextconfirm);
        textResendCode = (TextView) view.findViewById(R.id.textresendcode);
        textResendCode.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    void switchToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_signup, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Utility.FRAG_NUM_key, Utility.CONFIRMATION_FG_NUM);
    }
}
