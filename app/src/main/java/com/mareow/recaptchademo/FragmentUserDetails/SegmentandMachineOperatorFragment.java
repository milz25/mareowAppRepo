package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegmentandMachineOperatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SegmentandMachineOperatorFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatTextView mSpinnerSegment;
    AppCompatTextView mSpinnerWorkAssoc;

    AppCompatImageView btnChooseCerfitication;
    AppCompatImageView btnChooseAgency;

    AppCompatTextView NoFileSeletedCerti;
    AppCompatTextView NoFileSeletedAgency;

    FloatingActionButton btnSave;
    //AppCompatTextView btnLeft;
    //AppCompatTextView btnRight;

    ArrayList<String> segmentItems=new ArrayList<>();
    HashMap<String,String> segmentMap=new HashMap<>();
    ArrayList<String> workAssociationItem=new ArrayList<>();
    HashMap<String,String> workAssociationMap=new HashMap<>();

    String itemsSelettedString="";
    String[] listItems=null;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();
    String[] wrokAssociationList;
    boolean[] workChecked;
    int singleSlected=0;
    public static List<String> OperatorSegment=new ArrayList<>();
    public static String OperatorWorkAssociation=null;

    //public static Uri certificateToRunPath=null;
 //   public static Uri credentialsForAgencyPath=null;

    Uri photoURI;
    String imageFilePath;

    boolean CERTIFICATE=false;
    boolean CREDENTIALS=false;
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public SegmentandMachineOperatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SegmentandMachineOperatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SegmentandMachineOperatorFragment newInstance(String param1, String param2) {
        SegmentandMachineOperatorFragment fragment = new SegmentandMachineOperatorFragment();
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
        View view=inflater.inflate(R.layout.fragment_segment_and_machine, container, false);
        initView(view);
        wrokAssociationList=getActivity().getResources().getStringArray(R.array.work_type_array);
        workChecked=new boolean[wrokAssociationList.length];
        callPreferedSegment();
        callWorkAssociation();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(getActivity(),perms)){

            }else {
              EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }
        return view;
    }

    public void initView(View view){
        mSpinnerSegment=(AppCompatTextView) view.findViewById(R.id.segment_details_frg_spinner_segment);
        mSpinnerSegment.setText("Construction Machineries");
        mSpinnerSegment.setOnClickListener(this);
        mSpinnerWorkAssoc=(AppCompatTextView) view.findViewById(R.id.segment_details_frg_spinner_work);
        mSpinnerWorkAssoc.setOnClickListener(this);

        btnChooseCerfitication=(AppCompatImageView) view.findViewById(R.id.segment_details_frg_certificate);
        btnChooseCerfitication.setOnClickListener(this);
        NoFileSeletedCerti=(AppCompatTextView)view.findViewById(R.id.segment_details_frg_no_file_selected_certificate);

        btnChooseAgency=(AppCompatImageView) view.findViewById(R.id.segment_details_frg_agency);
        btnChooseAgency.setOnClickListener(this);
        NoFileSeletedAgency=(AppCompatTextView)view.findViewById(R.id.segment_details_frg_no_file_selected_agency);

        btnSave=(FloatingActionButton) view.findViewById(R.id.segment_machine_details_frg_next);
        btnSave.setOnClickListener(this);

     /*   btnRight=(AppCompatTextView)view.findViewById(R.id.segment_machine_details_frg_right);
        btnRight.setOnClickListener(this);

        btnLeft=(AppCompatTextView)view.findViewById(R.id.segment_machine_details_frg_left);
        btnLeft.setOnClickListener(this);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.segment_details_frg_certificate:
                Intent certificateIntent = new Intent(Intent.ACTION_GET_CONTENT);
                certificateIntent.setType("*/*");
                startActivityForResult(certificateIntent,0);
               // CERTIFICATE=true;
               // CREDENTIALS=false;
                //showAlertDialog();
                break;
            case R.id.segment_details_frg_agency:
                Intent agencyIntent = new Intent(Intent.ACTION_GET_CONTENT);
                agencyIntent.setType("*/*");
                //agencyIntent.setType("file/*");
                startActivityForResult(agencyIntent,1);

               // CERTIFICATE=false;
               // CREDENTIALS=true;
               // showAlertDialog();
                break;
            case R.id.segment_machine_details_frg_next:
                //callNextFragment();
                Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.segment_details_frg_spinner_segment:
                showSpinnerMultiselection();
                break;
            case R.id.segment_details_frg_spinner_work:
                showSpinnerSingleselection();
                break;
        }

    }

    private void showSpinnerSingleselection() {

        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                mSpinnerWorkAssoc.setText(workAssociationItem.get(position));
            }

        };


        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),workAssociationItem,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }




    private void callPreferedSegment() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> segmentCall=apiInterface.getPreferedSegment();
        segmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject segmentResponse) {
        segmentItems.clear();
        segmentMap.clear();
        listItems=null;
        for(Iterator<String> iter = segmentResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            segmentItems.add(key);
            try {
                segmentMap.put(key,segmentResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String[] items=new String[segmentItems.size()];
        for(int i=0;i<segmentItems.size();i++)
            items[i]=segmentItems.get(i);
        listItems=items;
        checkedItems=new boolean[listItems.length];

        for (int i=0;i<listItems.length;i++){
            if (listItems[i].equals("Construction Machineries")){
                checkedItems[i]=true;
                mUserItems.add(i);
            }

        }

    }


    private void callNextFragment() {
        if (mSpinnerSegment.equals("Select Segment")){
            mSpinnerSegment.setText("Enter Segment");
            mSpinnerSegment.requestFocus();
            return;
        }
        if (mSpinnerWorkAssoc.equals("Select Association")){
            mSpinnerWorkAssoc.setText("Enter wrok association");
            mSpinnerWorkAssoc.requestFocus();
            return;
        }


        for (int i=0;i<mUserItems.size();i++){
            if (segmentMap.containsKey(listItems[mUserItems.get(i)]))
            OperatorSegment.add(segmentMap.get(listItems[mUserItems.get(i)]));
        }

        OperatorWorkAssociation=mSpinnerWorkAssoc.getText().toString().trim();

        if (NoFileSeletedCerti.equals("No file selected")){
        //   certificateToRunPath =null;
        }

        if (NoFileSeletedAgency.equals("No file selected")){
           // credentialsForAgencyPath=null;
        }


        Fragment referFragment = new OperatorchargeDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, referFragment); // give your fragment container id in first parameter
        transaction.commit();
    }


    public void showSpinnerMultiselection(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Segments");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                    }
                }else if (mUserItems.contains(position)){
                    mUserItems.remove(mUserItems.indexOf(position));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item;
                if (mUserItems.size()==0){
                    item="Select Segment";
                }else {
                    item="";
                    for(int i=0;i<mUserItems.size();i++){
                        item=item+listItems[mUserItems.get(i)];
                        if (i!=mUserItems.size()-1){
                            item=item+",";
                        }
                    }
                }
                mSpinnerSegment.setText("");
                mSpinnerSegment.setText(item);
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<checkedItems.length;i++){
                    checkedItems[i]=false;
                    mUserItems.clear();
                    mSpinnerSegment.setText("Select Segment");
                }
            }
        });

        AlertDialog alertDialog=mBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK){
            if (requestCode==0){

                Uri uri = data.getData();
                String checkString=checkFileFormat(uri);
                if (checkString!=null){
                  //  certificateToRunPath=data.getData();
                    NoFileSeletedCerti.setText(checkString);
                }else {
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                    Util.showFileErrorAlert(getActivity());
                }

            }
            if (requestCode==1){

                Uri uri1 = data.getData();
                String checkString=checkFileFormat(uri1);
                if (checkString!=null){
                   // credentialsForAgencyPath=data.getData();
                    NoFileSeletedAgency.setText(checkString);
                }else {
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                    Util.showFileErrorAlert(getActivity());
                }

            }

        }

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

        private String checkFileFormat(Uri uri){
            boolean validFileName=false;
            String uriString = uri.toString();
            File file=new File(uriString);
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
                displayName = file.getName();
            }

            if (displayName.contains(".doc") || displayName.contains(".docx")) {
                // Word document
                validFileName=true;
            } else if (displayName.contains(".pdf")) {
                // PDF file
                validFileName=true;
            } else if (displayName.contains(".xls") || displayName.contains(".xlsx")) {
                // Excel file
                validFileName=true;
            }else if (displayName.contains(".jpg") || displayName.contains(".jpeg") || displayName.contains(".png")) {
                // JPG file
                validFileName=true;
            }

            if (validFileName){

            }else {
                displayName=null;

            }

            return displayName;

        }


    private void callWorkAssociation() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> segmentCall=apiInterface.getLookupForWorkAssociation();
        segmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObjectWorkAssociation(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObjectWorkAssociation(JSONObject workAssResponse) {

        workAssociationItem.clear();
        workAssociationMap.clear();
        listItems=null;
        for(Iterator<String> iter = workAssResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            workAssociationItem.add(key);
            try {
                workAssociationMap.put(key,workAssResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
