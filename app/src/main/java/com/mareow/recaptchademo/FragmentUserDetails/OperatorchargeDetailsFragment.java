package com.mareow.recaptchademo.FragmentUserDetails;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OperatorchargeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperatorchargeDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RadioGroup radioGroupAmount;

    SwitchCompat switchAccommodation;
    SwitchCompat switchTransportation;
    SwitchCompat switchFood;

    FloatingActionButton btnSave;

   // AppCompatTextView btnLeft;
    //AppCompatTextView btnRight;

    TextInputEditText editAmount;
    TextInputEditText mWorkAssociation;
    TextInputEditText mAssociatedWith;

    AppCompatImageView btnCertificateChoooseFile;
    AppCompatImageView btnCredentialChoooseFile;

    AppCompatTextView txtCertiNoFileSelected;
    AppCompatTextView txtCerdentialNoFileSelected;

    AppCompatRadioButton fixedRadio;
    AppCompatRadioButton hourlydRadio;


    public static String operatorAmount=null;

    public static boolean AMOUNTTYPE=false;
    public static boolean ACCOMODATION=false;
    public static boolean TRASPORTATION=false;
    public static boolean FOOD=false;

    ArrayList<String> workAssociationItem=new ArrayList<>();
    HashMap<String,String> workAssociationMap=new HashMap<>();
    String[] listItems=null;
    public static String certificateToRunPath=null;
    public static String credentialsForAgencyPath=null;
    public static String OperatorWorkAssociation=null;
    public static String workAssociationKey=null;
    public static String AssociatedWith=null;

    String imageFilePath;
    Uri photoURI;
    boolean CERTI_OR_CREDEN;
    public OperatorchargeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperatorchargeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OperatorchargeDetailsFragment newInstance(String param1, String param2) {
        OperatorchargeDetailsFragment fragment = new OperatorchargeDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_operatorcharge_details, container, false);
        //MainActivity.navItemIndex=16;
        initView(view);
        callWorkAssociation();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initView(View view){

        mAssociatedWith=(TextInputEditText)view.findViewById(R.id.operator_charge_details_frg_associated_with);
        mAssociatedWith.setInputType(InputType.TYPE_NULL);

        mWorkAssociation=(TextInputEditText)view.findViewById(R.id.operator_charge_details_frg_work_association);
        mWorkAssociation.setOnClickListener(this);
        mWorkAssociation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showSpinnerSingleselection();
                }
            }
        });

        btnCertificateChoooseFile=(AppCompatImageView)view.findViewById(R.id.operator_charge_details_frg_segment_certificate);
        btnCredentialChoooseFile=(AppCompatImageView)view.findViewById(R.id.operator_charge_details_frg_segment_credential_agency);

        btnCredentialChoooseFile.setOnClickListener(this);
        btnCertificateChoooseFile.setOnClickListener(this);



        fixedRadio=(AppCompatRadioButton)view.findViewById(R.id.operator_charge_fixed);
        hourlydRadio=(AppCompatRadioButton)view.findViewById(R.id.operator_charge_hourly);

        radioGroupAmount=(RadioGroup)view.findViewById(R.id.operator_charge_amount_radio);
        radioGroupAmount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonAmount = (RadioButton) radioGroupAmount.findViewById(checkedId);
                String amountText=radioButtonAmount.getText().toString();
                if (amountText.equals("Fixed (Monthly)")){
                    AMOUNTTYPE=true;
                }
                if (amountText.equals("Hourly Basis")){
                    AMOUNTTYPE=false;
                }
            }
        });
        switchAccommodation=(SwitchCompat)view.findViewById(R.id.operator_charge_accomodation_switch);
        switchAccommodation.setChecked(ACCOMODATION);
        switchAccommodation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ACCOMODATION=isChecked;

            }
        });
        switchTransportation=(SwitchCompat)view.findViewById(R.id.operator_charge_transportation_switch);
        switchTransportation.setChecked(TRASPORTATION);
        switchTransportation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TRASPORTATION=isChecked;

            }
        });

        switchFood=(SwitchCompat)view.findViewById(R.id.operator_charge_food_switch);
        switchFood.setChecked(FOOD);
        switchFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               FOOD=isChecked;
            }
        });

        editAmount=(TextInputEditText)view.findViewById(R.id.operator_charge_details_frg_amount);
        editAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                operatorAmount=s.toString();
            }
        });
        editAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    operatorAmount=editAmount.getText().toString();
                }
            }
        });

        txtCertiNoFileSelected=(AppCompatTextView)view.findViewById(R.id.segment_details_frg_no_file_selected_certificate);
        txtCerdentialNoFileSelected=(AppCompatTextView)view.findViewById(R.id.segment_details_frg_no_file_selected_agency);
       // btnSave=(FloatingActionButton) view.findViewById(R.id.operator_charge_details_frg_next);
       // btnSave.setOnClickListener(this);
        /* btnRight=(AppCompatTextView)view.findViewById(R.id.operator_charge_details_frg_right);
         btnRight.setOnClickListener(this);

         btnLeft=(AppCompatTextView)view.findViewById(R.id.operator_charge_details_frg_left);
         btnLeft.setOnClickListener(this);*/


        if (Constants.MY_PROFILE){

            if (MyProfileFragment.mUSerProfileDataList.getAssociation()!=null){
                if (workAssociationKey!=null){
                    mWorkAssociation.setText(workAssociationKey);
                }else {
                    mWorkAssociation.setText(MyProfileFragment.mUSerProfileDataList.getAssociation());
                    OperatorWorkAssociation=MyProfileFragment.mUSerProfileDataList.getAssociation();
                }

            }

            if (AssociatedWith==null){
                mAssociatedWith.setVisibility(View.GONE);
            }else {
                mAssociatedWith.setText(AssociatedWith);
                mWorkAssociation.setEnabled(false);
                mWorkAssociation.setTextColor(getActivity().getResources().getColor(android.R.color.black));

            }

           if (MyProfileFragment.mUSerProfileDataList.getRunCertificateFile()!=null || certificateToRunPath!=null){

               if (certificateToRunPath!=null){
                   txtCertiNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(certificateToRunPath)));
               }else {
                   txtCertiNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(MyProfileFragment.mUSerProfileDataList.getRunCertificatePath())));
                   certificateToRunPath=MyProfileFragment.mUSerProfileDataList.getRunCertificatePath();
               }

           }

           if (MyProfileFragment.mUSerProfileDataList.getMachineCredentialsFile()!=null || credentialsForAgencyPath!=null){

               if (credentialsForAgencyPath!=null){
                   txtCerdentialNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(credentialsForAgencyPath)));

               }else {
                   txtCerdentialNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(MyProfileFragment.mUSerProfileDataList.getMachineCredentialsPath())));
                   credentialsForAgencyPath=MyProfileFragment.mUSerProfileDataList.getMachineCredentialsPath();
               }

           }
           if (MyProfileFragment.mUSerProfileDataList.getAttribute2()!=0){

               if (operatorAmount!=null){
                   editAmount.setText(operatorAmount);
               }else {
                   editAmount.setText(String.valueOf(MyProfileFragment.mUSerProfileDataList.getAttribute2()));
                   operatorAmount=String.valueOf(MyProfileFragment.mUSerProfileDataList.getAttribute2());
               }

           }

           if (AMOUNTTYPE){
               fixedRadio.setChecked(true);
           }else {
               hourlydRadio.setChecked(true);
           }

            switchAccommodation.setChecked(MyProfileFragment.mUSerProfileDataList.isAttribute3());
            ACCOMODATION=MyProfileFragment.mUSerProfileDataList.isAttribute3();

            switchTransportation.setChecked(MyProfileFragment.mUSerProfileDataList.isAttribute4());
            TRASPORTATION=MyProfileFragment.mUSerProfileDataList.isAttribute4();


            switchFood.setChecked(MyProfileFragment.mUSerProfileDataList.isAttribute5());
            FOOD=MyProfileFragment.mUSerProfileDataList.isAttribute5();

         /*  if (MyProfileFragment.mUSerProfileDataList.isAttribute5()){
               switchFood.setChecked(true);
               FOOD=true;
           }else {
               switchFood.setChecked(false);
               FOOD=false;
           }*/


        }
        else {

            mAssociatedWith.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.id.operator_charge_details_frg_next:
               // callNextFragment();
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.operator_charge_details_frg_work_association:
                showSpinnerSingleselection();
                break;
            case R.id.operator_charge_details_frg_segment_certificate:

                CERTI_OR_CREDEN=true;
                showAlertDialog();
                //browseDocuments();
                //Intent certificateIntent = new Intent(Intent.ACTION_GET_CONTENT);
               // certificateIntent.setType("*/*");
                //certificateIntent.addCategory(Intent.CATEGORY_OPENABLE);
                //startActivityForResult(certificateIntent,0);
                break;
            case R.id.operator_charge_details_frg_segment_credential_agency:
                CERTI_OR_CREDEN=false;
                showAlertDialog();
               // browseDocuments();
                //Intent agencyIntent = new Intent(Intent.ACTION_GET_CONTENT);
               // agencyIntent.setType("*/*");
                //agencyIntent.addCategory(Intent.CATEGORY_OPENABLE);
               // startActivityForResult(agencyIntent,1);
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

        titleText.setText("Work Association");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                mWorkAssociation.setText(workAssociationItem.get(position));
                workAssociationKey=workAssociationItem.get(position);
                for (int i=0;i<workAssociationMap.size();i++){
                    if (workAssociationMap.containsKey(workAssociationItem.get(position))){
                        OperatorWorkAssociation=workAssociationMap.get(workAssociationItem.get(position));
                    }
                }

            }

        };

        Collections.sort(workAssociationItem);
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),workAssociationItem,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==0){

                Uri uri = data.getData();
                //String checkString=checkFileFormat(uri);
               // if (checkString!=null){
                   // certificateToRunPath= RealFilePath.getPath(getActivity(),data.getData());
                   certificateToRunPath= FileUtils.getPath(getActivity(),uri);
                   /* String extention=FileUtils.getExtension(uri.toString());
                    String mim=FileUtils.getFileName(getActivity(),uri);
                    File  file=FileUtils.getFile(getActivity(),uri);
                    String name=file.getName();
                    String path=file.getAbsolutePath();*/
                   // String extention=FileUtils.getExtension(uri.toString());
                    txtCertiNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(certificateToRunPath)));
               // }else {
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                   // Util.showFileErrorAlert(getActivity());
               // }

            }
            if (requestCode==1){

                   Uri uri1 = data.getData();
               // String checkString=checkFileFormat(uri1);
               /* if (checkString!=null){*/
                    //credentialsForAgencyPath=RealFilePath.getPath(getActivity(),data.getData());
                    credentialsForAgencyPath=FileUtils.getPath(getActivity(),uri1);
                    txtCerdentialNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(credentialsForAgencyPath)));


              /*  }else {*/
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                    //Util.showFileErrorAlert(getActivity());
               /* }*/

            }

            if (requestCode==5){
                txtCertiNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(certificateToRunPath)));
            }
            if (requestCode==6){
                txtCerdentialNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(credentialsForAgencyPath)));
            }

        }

    }

    private String checkFileFormat(Uri uri){
        boolean validFileName=false;
        String uriString = uri.toString();
        File file=new File(uri.getPath());
        String displayName = null;

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e){
                Log.d("Error",e.getMessage());
            }
            finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = file.getName();
        }

       /* if (displayName.contains(".doc") || displayName.contains(".docx")) {
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

        }*/
        return displayName;

    }

    private void GetAllValues() {
        int seletedAmountId=radioGroupAmount.getCheckedRadioButtonId();
        RadioButton radioButtonAmount = (RadioButton) radioGroupAmount.findViewById(seletedAmountId);
        String amountText=radioButtonAmount.getText().toString();

        if (amountText.equals("Fixed(Monthly)")){
            AMOUNTTYPE=true;
        }
        if (amountText.equals("Hourly Basis")){
            AMOUNTTYPE=false;
        }
       if (switchAccommodation.isChecked()){
           ACCOMODATION=true;
       }else {
           ACCOMODATION=false;
       }
       if (switchTransportation.isChecked()){
           TRASPORTATION=true;
       }else {
           TRASPORTATION=false;
       }

       if (switchFood.isChecked()){
           FOOD=true;
       }else {
           FOOD=false;
       }


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
                if (Constants.MY_PROFILE){
                    if (MyProfileFragment.mUSerProfileDataList.getAssociation()!=null){
                        if (workAssociationKey!=null){
                            mWorkAssociation.setText(workAssociationKey);
                        }else {
                            if (MyProfileFragment.mUSerProfileDataList.getAssociation().equals(workAssResponse.getString(key))){
                                mWorkAssociation.setText(key);
                            }
                        }
                    }
                }else {

                    if (workAssociationKey!=null){
                        mWorkAssociation.setText(workAssociationKey);
                        OperatorWorkAssociation=workAssResponse.getString(workAssociationKey);
                    }else {

                        if (workAssResponse.getString(key).equals("INDEP")){
                            mWorkAssociation.setText(key);
                            workAssociationKey=key;
                            OperatorWorkAssociation=workAssResponse.getString(key);
                        }
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

   /* public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }*/


    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        //"application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        //"text/plain",
                        "application/pdf",
                        "image/*"
                        /*"application/zip"*/};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }

        if (CERTI_OR_CREDEN){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 0);
        }else {
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 1);
        }
    }



    public void showAlertDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from FileSystem"/*, "Cancel"*/};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.camera_layout, null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.camera_title);
        AppCompatTextView cameraTitle=(AppCompatTextView)view.findViewById(R.id.camera_Camera);
        AppCompatTextView galleryTitle=(AppCompatTextView)view.findViewById(R.id.camera_Gallery);
        AppCompatImageButton btnClose=(AppCompatImageButton)view.findViewById(R.id.camera_dialog_close);
        titleText.setText("Add Photo!");

        cameraTitle.setText(options[0]);
        galleryTitle.setText(options[1]);

        cameraTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
                alertDialog.dismiss();
            }
        });

        galleryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseDocuments();
                alertDialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "Error::Creating File", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(), "com.mareow.recaptchademo.provider", photoFile);
                getContext().grantUriPermission("com.android.camera",photoURI,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                pictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (CERTI_OR_CREDEN){
                    startActivityForResult(pictureIntent, 5);
                }else {
                    startActivityForResult(pictureIntent, 6);
                }

            }
        }

    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();

        if (CERTI_OR_CREDEN){
            certificateToRunPath=imageFilePath;
        }else {
            credentialsForAgencyPath=imageFilePath;
        }
        // mAboutYourSelfPath = imageFilePath;
        //mProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
    }
}
