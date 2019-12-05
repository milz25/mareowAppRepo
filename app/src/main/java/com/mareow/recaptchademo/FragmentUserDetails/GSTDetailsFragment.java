package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GSTDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GSTDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText edit_GSTNo;
    FloatingActionButton btnSave;
    AppCompatImageView chooseFile;
    AppCompatTextView NoFileSelected;

    public static Uri gstDocPath=null;

   // AppCompatTextView btnRight;
    //AppCompatTextView btnLeft;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public GSTDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GSTDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GSTDetailsFragment newInstance(String param1, String param2) {
        GSTDetailsFragment fragment = new GSTDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_gstdetails, container, false);
        initView(view);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(getActivity(),perms)){

            }else {
                EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }
        return view;
    }

    private void initView(View view) {
        edit_GSTNo=(TextInputEditText)view.findViewById(R.id.gst_details_frg_gst_no);

        btnSave=(FloatingActionButton) view.findViewById(R.id.gst_details_frg_next);
        chooseFile=(AppCompatImageView)view.findViewById(R.id.gst_details_frg_choose_file);
        NoFileSelected=(AppCompatTextView)view.findViewById(R.id.gst_details_frg_no_file_selected);

        btnSave.setOnClickListener(this);
        chooseFile.setOnClickListener(this);

       /* btnRight=(AppCompatTextView)view.findViewById(R.id.gst_details_frg_right);
        btnRight.setOnClickListener(this);

        btnLeft=(AppCompatTextView)view.findViewById(R.id.gst_details_frg_left);
        btnLeft.setOnClickListener(this);
*/

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.gst_details_frg_next:
                //callNextFragment();
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gst_details_frg_choose_file:
                Intent GSTIntent = new Intent(Intent.ACTION_GET_CONTENT);
                GSTIntent.setType("*/*");
                startActivityForResult(GSTIntent,4);
                break;
           /* case R.id.gst_details_frg_right:
                callNextFragment();
                break;
            case R.id.gst_details_frg_left:
                //callNextFragment();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
*/
        }
    }

    private void callNextFragment() {
        Fragment referFragment = new BankDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, referFragment); // give your fragment container id in first parameter
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED &&grantResults[2]==PackageManager.PERMISSION_GRANTED){

            }else {
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,10,perms)
                                .setRationale("Please allow this permission for proper functionality.")
                                .setPositiveButtonText("Ok")
                                .build());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 4) {
                gstDocPath = data.getData();
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                String displayName = null;
                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }
                NoFileSelected.setText(displayName);
            }
        }
    }
}
