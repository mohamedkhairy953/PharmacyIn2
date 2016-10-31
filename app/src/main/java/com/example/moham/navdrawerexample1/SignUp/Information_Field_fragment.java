package com.example.moham.navdrawerexample1.SignUp;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.MainActivity;
import com.example.moham.navdrawerexample1.R;
import com.example.moham.navdrawerexample1.Utility;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.R.attr.pivotX;
import static android.R.attr.pivotY;
import static android.R.id.message;
import static android.app.Activity.RESULT_OK;


public class Information_Field_fragment extends Fragment implements View.OnClickListener {
    int numberOfClicks = 0;
    private static final int PHOTO_CODE = 0;
    Button save_btn;
    EditText edtxt_Fname, edtxt_Lname, edtxt_Uname, edtxt_phone;
    ImageView img_view;
    Inf_Field_ClassModel myModel;
    private byte[] image_byte_arrary;
    private LocationManager locationManager;
    private String location_String = "";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ImageButton rotate_btn;
    Uri img_uri;

    public Information_Field_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf_FieldView = inflater.inflate(R.layout.fragment_information__field, container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "Please open your Gps", Toast.LENGTH_LONG).show();
        }

        myModel = new Inf_Field_ClassModel();
        initialize_components(inf_FieldView);

        return inf_FieldView;
    }

    private void initialize_components(View v) {
        save_btn = (Button) v.findViewById(R.id.saveButtonInfField);
        edtxt_Fname = (EditText) v.findViewById(R.id.edittextFirstname);
        edtxt_Lname = (EditText) v.findViewById(R.id.edittextlastname);
        edtxt_Uname = (EditText) v.findViewById(R.id.edittextusername);
        edtxt_phone = (EditText) v.findViewById(R.id.edittextphone);
        img_view = (ImageView) v.findViewById(R.id.img_view_infField);
        rotate_btn = (ImageButton) v.findViewById(R.id.rotate_btn);
        img_view.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        rotate_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.saveButtonInfField:

                try {
                    getloc();
                    setMyModelAttributes();
                    Utility.addModelToFirebase(myModel, getActivity());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Location exception", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_view_infField:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_CODE);
                break;
            case R.id.rotate_btn:
                numberOfClicks++;
                //  rotateImage(img_view, 90);
                switch (numberOfClicks % 4) {
                    case 1:
                        img_view.setRotation(90);
                        break;
                    case 2:
                        img_view.setRotation(180);
                        break;
                    case 3:
                        img_view.setRotation(270);
                        break;
                    case 0:
                        img_view.setRotation(360);
                        break;
                }

        }
    }

    private void setMyModelAttributes() throws InfModelException {
        myModel.setUid(firebaseAuth.getCurrentUser().getUid());
        myModel.setUname(edtxt_Uname.getText().toString().trim());
        myModel.setFname(edtxt_Fname.getText().toString().trim());
        myModel.setPhone(edtxt_phone.getText().toString().trim());
        myModel.setLname(edtxt_Lname.getText().toString().trim());
        myModel.setImageUri(img_uri);
        myModel.setLocation(location_String);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    img_uri = data.getData();
                    try {
                        InputStream img_is = getActivity().getContentResolver().openInputStream(img_uri);
                        final Bitmap img_bitmap = BitmapFactory.decodeStream(img_is);
                        img_view.setImageBitmap(img_bitmap);
                    } catch (OutOfMemoryError b) {
                        Toast.makeText(getActivity(), "too size photo", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Utility.FRAG_NUM_key, Utility.INF_FIELD_FG_NUM);
    }


    private void getloc() throws Exception {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setBearingRequired(true);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(false);
            String bestProvider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(bestProvider, 2000, 10, new LocationListener() {
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }

                @Override
                public void onLocationChanged(final Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    location_String = latitude + "-" + longitude;
                }
            });
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            double longitude = myLocation.getLongitude();
            double latitude = myLocation.getLatitude();
            location_String = latitude + "-" + longitude;

        }
    }

}
