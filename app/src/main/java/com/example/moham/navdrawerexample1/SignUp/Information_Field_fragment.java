package com.example.moham.navdrawerexample1.SignUp;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.R;
import com.example.moham.navdrawerexample1.Utility;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.R.id.message;
import static android.app.Activity.RESULT_OK;


public class Information_Field_fragment extends Fragment implements View.OnClickListener {
    //TODO image view rotations

    private static final int PHOTO_CODE = 0;
    Button save_btn;
    EditText edtxt_Fname, edtxt_Lname, edtxt_Uname, edtxt_phone;
    ImageView img_view;
    Inf_Field_ClassModel myModel;
    private byte[] image_byte_arrary;
    private LocationManager locationManager;
    private String location_String;

    public Information_Field_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf_FieldView = inflater.inflate(R.layout.fragment_information__field, container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
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
        img_view.setOnClickListener(this);
        save_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButtonInfField:
                setMyModelAttributes();
                getloc();
                Utility.addModelToFirebase(myModel, getActivity());
                break;
            case R.id.img_view_infField:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_CODE);
                break;
        }
    }

    private void setMyModelAttributes() {
        myModel.setFname(edtxt_Fname.getText().toString().trim());
        myModel.setLname(edtxt_Lname.getText().toString().trim());
        myModel.setUname(edtxt_Uname.getText().toString().trim());
        myModel.setPhone(edtxt_phone.getText().toString().trim());
        myModel.setLocation(location_String);
        myModel.setImage(image_byte_arrary);
        Toast.makeText(getActivity(), myModel.getLocation(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    final Uri img_uri = data.getData();
                    try {
                        InputStream img_is = getActivity().getContentResolver().openInputStream(img_uri);
                        final Bitmap img_bitmap = BitmapFactory.decodeStream(img_is);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        image_byte_arrary = byteArrayOutputStream.toByteArray();
                        img_view.setImageBitmap(img_bitmap);
                    } catch (OutOfMemoryError b) {
                        Toast.makeText(getActivity(), "too size photo", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void getloc() {
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
                    Toast.makeText(getActivity(), "on location changed ", Toast.LENGTH_SHORT).show();
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

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS must be enabled to continue, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
