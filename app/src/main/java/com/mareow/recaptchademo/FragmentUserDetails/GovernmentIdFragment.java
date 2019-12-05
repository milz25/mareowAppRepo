package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.DetailsSubmissionActivity;
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
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovernmentIdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovernmentIdFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText mGovId;
    TextInputEditText mGSTNo;

    AppCompatTextView mGovIdHint;
    AppCompatTextView mGstHint;

    AppCompatImageView btnGovChooseFile;
    AppCompatTextView textGovNoFileSelected;

    AppCompatImageView btnGSTChooseFile;
    AppCompatTextView textGSTNoFileSelected;

    FloatingActionButton btnSave;
    LinearLayout sectionGST;


    //AppCompatTextView btnRight;
   // AppCompatTextView btnLeft;

    String[] govIdList;
    int singleSlected=0;
    //ArrayList<String> govIdListArray=new ArrayList<>();

    public static String govId=null;
    public static String govIdName=null;
    public static String govProofPath=null;

    public static String gstNo=null;
    public static String gstProofDocPath=null;

    ArrayList<String> govermnetIdItems=new ArrayList<>();
   // HashMap<String,String> govermentMap=new HashMap<>();

    String imageFilePath;
    Uri photoURI;
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    boolean GOVID_GSTID;
    public GovernmentIdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GovernmentIdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GovernmentIdFragment newInstance(String param1, String param2) {
        GovernmentIdFragment fragment = new GovernmentIdFragment();
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
        View view=inflater.inflate(R.layout.fragment_government_id, container, false);
       // MainActivity.navItemIndex=16;
      //  callGovermentIdApi();
        govermnetIdItems.clear();
        if (Constants.MY_PROFILE){
            for (Map.Entry<String,String> entry : MyProfileFragment.govermentMap.entrySet()) {
                govermnetIdItems.add(entry.getKey());
            }
        }else {

            for (Map.Entry<String,String> entry : DetailsSubmissionActivity.registrationGovermentMap.entrySet()) {
                govermnetIdItems.add(entry.getKey());
            }
        }

        initView(view);
       /* govIdList=getActivity().getResources().getStringArray(R.array.gov_id_array);
        govIdListArray.clear();
        for(int i=0;i<govIdList.length;i++){
             govIdListArray.add(govIdList[i]);
         }*/
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
     /*

        if (!Constants.USER_ROLE.equals("Operator")){
            callGovermentIdApi();
        }*/
        return view;
    }

    private void initView(View view) {

        mGovIdHint=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_gov_proof_hint);
        mGstHint=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_gst_proof_hint);

        mGovId=(TextInputEditText)view.findViewById(R.id.gov_details_frg_gov_id);
        mGovId.setHorizontallyScrolling(false);
        mGovId.setOnClickListener(this);
        mGovId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    //showGovIdDialog();
                    showSpinnerSingleselection();
                }else {

                }
            }
        });

        mGSTNo=(TextInputEditText)view.findViewById(R.id.gov_details_frg_gstno);
        mGSTNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               gstNo=s.toString();
               if (!s.toString().equals("")){
                   mGstHint.setText("GST Document *");
               }else {
                   mGstHint.setText("GST Document");
               }
            }
        });


        btnGovChooseFile=(AppCompatImageView)view.findViewById(R.id.gov_details_frg_gov_choosefile);
        btnGovChooseFile.setOnClickListener(this);

        btnGSTChooseFile=(AppCompatImageView)view.findViewById(R.id.gov_details_frg_gst_choosefile);
        btnGSTChooseFile.setOnClickListener(this);

        textGovNoFileSelected=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_govid_no_file_selected);
        textGSTNoFileSelected=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_gst_no_file_selected);

        sectionGST=(LinearLayout)view.findViewById(R.id.gov_details_frg_gst_ln);

        if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Supervisor")){
            sectionGST.setVisibility(View.GONE);
        }


        if (Constants.MY_PROFILE){

            if (MyProfileFragment.mUSerProfileDataList.getGovtProofId()!=null){

                if (govIdName!=null){
                    mGovId.setText(govIdName);

                    if (Constants.USER_ROLE.equals("Operator")){
                        mGovId.setEnabled(false);
                        mGovId.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                    }


                }else {
                    /*if (MyProfileFragment.mUSerProfileDataList.getGovtProofId().equals("UPT")) {
                        mGovId.setText("Passport");
                    } else if (MyProfileFragment.mUSerProfileDataList.getGovtProofId().equals("UAD")) {
                        mGovId.setText("Aadhar");
                    } else if (MyProfileFragment.mUSerProfileDataList.getGovtProofId().equals("UDL")) {
                        mGovId.setText("Driving Licence");
                    } else if (MyProfileFragment.mUSerProfileDataList.getGovtProofId().equals("UPN")) {
                        mGovId.setText("PAN No");
                    } else if (MyProfileFragment.mUSerProfileDataList.getGovtProofId().equals("UVD")) {
                        mGovId.setText("Voter ID");
                    }*/

                    for (Map.Entry<String,String> entry : MyProfileFragment.govermentMap.entrySet()) {
                        if(entry.getValue().equals(MyProfileFragment.mUSerProfileDataList.getGovtProofId())){
                            mGovId.setText(entry.getKey());
                            break;
                        }
                    }

                 //   mGovId.setText(MyProfileFragment.govermentMap.get(MyProfileFragment.mUSerProfileDataList.getGovtProofId()));
                    govId = MyProfileFragment.mUSerProfileDataList.getGovtProofId();
                    govIdName=mGovId.getText().toString();

                    if (Constants.USER_ROLE.equals("Operator")){
                        mGovId.setEnabled(false);
                        mGovId.setTextColor(getActivity().getResources().getColor(android.R.color.black));

                    }

                }

            }else {

                if (Constants.USER_ROLE.equals("Operator")){
                    //mGovId.setKeyListener(null);
                    govId="UDL";
                    for (Map.Entry<String,String> entry : MyProfileFragment.govermentMap.entrySet()) {
                        if(entry.getValue().equals("UDL")){
                            mGovId.setText(entry.getKey());
                            break;
                        }
                    }

                   // mGovId.setText(MyProfileFragment.govermentMap.get("UDL"));
                    govIdName=mGovId.getText().toString();
                    mGovId.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                    mGovId.setEnabled(false);
                }



            }

            if (MyProfileFragment.mUSerProfileDataList.getGovtProofDocFile()!=null){
                if (govProofPath!=null){
                    textGovNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(govProofPath)));
                }else {
                    textGovNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(MyProfileFragment.mUSerProfileDataList.getGovtProofDocPath())));
                    govProofPath=MyProfileFragment.mUSerProfileDataList.getGovtProofDocPath();
                }
            }

          /*  if (Constants.USER_ROLE.equals("Operator")){
                // mGovId.setKeyListener(null);
                mGovId.setEnabled(false);
                mGovId.setTextColor(getActivity().getResources().getColor(android.R.color.black));
               // mGovId.setOnKeyListener(null);
            }*/

            if (Constants.USER_ROLE.equals("Renter")){

                if (MyProfileFragment.mUSerProfileDataList.getGstNumber()!=null){
                    if (gstNo!=null){
                        mGSTNo.setText(gstNo);
                    }else {
                        mGSTNo.setText(MyProfileFragment.mUSerProfileDataList.getGstNumber());
                        gstNo=MyProfileFragment.mUSerProfileDataList.getGstNumber();
                    }

                    mGstHint.setText("GST Document *");
                }else {
                    mGstHint.setText("GST Document");
                }

                if (MyProfileFragment.mUSerProfileDataList.getGstDocumentFile()!=null){

                    if (gstProofDocPath!=null){
                        textGSTNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(gstProofDocPath)));
                    }else {
                        textGSTNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(MyProfileFragment.mUSerProfileDataList.getGstDocumentPath())));
                        gstProofDocPath=MyProfileFragment.mUSerProfileDataList.getGstDocumentPath();
                    }
                }

            }

        }else {

            if (Constants.USER_ROLE.equals("Operator")){
                //mGovId.setKeyListener(null);
                govId="UDL";
                for (Map.Entry<String,String> entry : DetailsSubmissionActivity.registrationGovermentMap.entrySet()) {
                    if(entry.getValue().equals("UDL")){
                        mGovId.setText(entry.getKey());
                        break;
                    }
                }

                // mGovId.setText(MyProfileFragment.govermentMap.get("UDL"));
                govIdName=mGovId.getText().toString();
                mGovId.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                mGovId.setEnabled(false);

            }



        }


       /* mGovId=(AppCompatTextView) view.findViewById(R.id.gov_details_frg_spinner);
        mGovId.setOnClickListener(this);

        btnChooseFile=(AppCompatImageView) view.findViewById(R.id.gov_details_frg_choosefile);

        btnSave=(FloatingActionButton) view.findViewById(R.id.gov_details_frg_next);
        textNoFileSelected=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_no_file_selected);

         btnChooseFile.setOnClickListener(this);
         btnSave.setOnClickListener(this);*/
/*
         btnRight=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_right);
         btnRight.setOnClickListener(this);

        btnLeft=(AppCompatTextView)view.findViewById(R.id.gov_details_frg_left);
        btnLeft.setOnClickListener(this);*/


      if (govId!=null){
          mGovIdHint.setText("Government Proof *");
      }else {
          mGovIdHint.setText("Government Proof");
      }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gov_details_frg_gov_choosefile:
                GOVID_GSTID=true;
               // browseDocuments();
                showAlertDialog();
              // Intent govIdIntent = new Intent(Intent.ACTION_GET_CONTENT);
               //govIdIntent.setType("*/*");
              // startActivityForResult(govIdIntent,3);
                break;
            case R.id.gov_details_frg_gst_choosefile:
                GOVID_GSTID=false;
                showAlertDialog();
              //  browseDocuments();
                //Intent gstIntent = new Intent(Intent.ACTION_GET_CONTENT);
               // gstIntent.setType("*/*");
              //  startActivityForResult(gstIntent,4);
                break;
            case R.id.gov_details_frg_gov_id:
              //showGovIdDialog();
                showSpinnerSingleselection();
                break;
        }
    }


   /* public void showGovIdDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Government ID");
        mBuilder.setSingleChoiceItems(govIdList,singleSlected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                singleSlected=position;
                mGovId.setText(govIdList[position]);
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK){

            if (requestCode==3){
                Uri uri = data.getData();
                //String checkString=checkFileFormat(uri);
                //if (checkString!=null){
                    govProofPath= FileUtils.getPath(getActivity(),uri);
                    textGovNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(govProofPath)));
                //}else {
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                  //  Util.showFileErrorAlert(getActivity());
               // }
            }

            if (requestCode==4){
                Uri uri1 = data.getData();
                //String checkString=checkFileFormat(uri1);
               // if (checkString!=null){
                    gstProofDocPath= FileUtils.getPath(getActivity(),uri1);
                    textGSTNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(gstProofDocPath)));
                //}else {
                    //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                //    Util.showFileErrorAlert(getActivity());
              //  }
            }
            if (requestCode==5){
              //  Uri uri1 = data.getData();
                //String checkString=checkFileFormat(uri1);
                // if (checkString!=null){
                //govProofPath= FileUtils.getPath(getActivity(),photoURI);
                textGovNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(govProofPath)));
                //}else {
                //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                //    Util.showFileErrorAlert(getActivity());
                //  }
            }
            if (requestCode==6){
                //String checkString=checkFileFormat(uri1);
                // if (checkString!=null){
                //gstProofDocPath= RealFilePath.getPath(getActivity(),photoURI);
                textGSTNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(gstProofDocPath)));
                //}else {
                //Toast.makeText(getActivity(), "Please select proper file format for this document.", Toast.LENGTH_SHORT).show();
                //    Util.showFileErrorAlert(getActivity());
                //  }
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

      /*  if (displayName.contains(".doc") || displayName.contains(".docx")) {
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
    private void showSpinnerSingleselection() {
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("Government IDs");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                mGovId.setText(govermnetIdItems.get(position));
                govIdName=govermnetIdItems.get(position);

                if (Constants.MY_PROFILE){
                    govId=MyProfileFragment.govermentMap.get(govermnetIdItems.get(position));
                }else {
                    govId=DetailsSubmissionActivity.registrationGovermentMap.get(govermnetIdItems.get(position));
                }

                mGovIdHint.setText("Government Proof *");

               /* if (govIdListArray.get(position).equals("Passport")){
                    govId="UPT";
                }else if (govIdListArray.get(position).equals("Aadhar")){
                    govId="UAD";
                }else if (govIdListArray.get(position).equals("Driving Licence")){
                    govId="UDL";
                }else if (govIdListArray.get(position).equals("PAN No")){
                    govId="UPN";
                }else if (govIdListArray.get(position).equals("Voter ID")){
                    govId="UVD";
                }*/


                }

        };
        Collections.sort(govermnetIdItems);
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),govermnetIdItems,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }


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

        if (GOVID_GSTID){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 3);
        }else {
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 4);
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

                if (GOVID_GSTID){
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

        if (GOVID_GSTID){
            govProofPath=imageFilePath;
        }else {
            gstProofDocPath=imageFilePath;
        }
       // mAboutYourSelfPath = imageFilePath;
        //mProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
    }


    private void callGovermentIdApi() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> govermentIdCall=apiInterface.getGovermentIdCombo("GOVERMENT_ID","GOVID");
        govermentIdCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        //parseJSONObject(jsonObject);
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

   /* private void parseJSONObject(JSONObject segmentResponse) {
        govermnetIdItems.clear();
        govermentMap.clear();
        for (Iterator<String> iter = segmentResponse.keys(); iter.hasNext(); ) {
            String key = iter.next();
            govermnetIdItems.add(key);
            try {
                govermentMap.put(key, segmentResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}
