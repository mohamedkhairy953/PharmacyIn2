package com.example.moham.navdrawerexample1.SignUp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moham.navdrawerexample1.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class Information_Field_fragment extends Fragment implements View.OnClickListener {


    private static final int PHOTO_CODE = 0;
    Button save_btn;
    EditText edtxt_Fname, edtxt_Lname, edtxt_Uname, edtxt_phone;
    ImageView img_view;
    Inf_Field_ClassModel myModel;
    private byte[] image_byte_arrary;

    public Information_Field_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inf_FieldView = inflater.inflate(R.layout.fragment_information__field, container, false);
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
                //TODO send model to some method which save data into firebase database
                //TODO image view rotations
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
        myModel.setImage(image_byte_arrary);
        Toast.makeText(getActivity(), myModel.getUname(), Toast.LENGTH_SHORT).show();
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
}
