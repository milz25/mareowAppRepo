package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RoundedBitMap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import android.graphics.Bitmap.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TellAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TellAboutFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton btnSave;
   // AppCompatTextView btnRight;
    //AppCompatTextView btnLeft;

    //AppCompatImageView btnChooseFile;
    //AppCompatTextView txtNoFileSelected;
    AppCompatEditText edit_TellYourSelf;
    AppCompatImageView mProfileImage;

   // public static String mAboutYourSelf=null;
   // public static String mAboutYourSelfPath=null;

    String imageFilePath;
    Uri photoURI;
    String referType;
    private static final int CROP_PIC = 1001;
    RoundedBitMap roundedBitMap;
    ProgressDialog progressDialog;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public TellAboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TellAboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TellAboutFragment newInstance(String param1, String param2) {
        TellAboutFragment fragment = new TellAboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_tell_about, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..........");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        initView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(getActivity(), perms)) {

            } else {
                EasyPermissions.requestPermissions(this, "Please allow this permission for proper functionality.", 10, perms);
            }
        }
        return view;
    }

    private void initView(View view) {
        edit_TellYourSelf = (AppCompatEditText) view.findViewById(R.id.tell_about_frg_about_yourself);
        //btnChooseFile=(AppCompatImageView) view.findViewById(R.id.tell_about_frg_choose_file);
        //btnChooseFile.setOnClickListener(this);
        // txtNoFileSelected=(AppCompatTextView)view.findViewById(R.id.tell_about_frg_no_file_selected);

        btnSave = (FloatingActionButton) view.findViewById(R.id.tell_about_frg_save);
      /*  btnRight = (AppCompatTextView) view.findViewById(R.id.tell_about_frg_right);
        btnLeft = (AppCompatTextView) view.findViewById(R.id.tell_about_frg_left);*/

        btnSave.setOnClickListener(this);
      /*  btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);*/

        mProfileImage = (AppCompatImageView) view.findViewById(R.id.tell_me_about_profile_image);
        mProfileImage.setOnClickListener(this);

        roundedBitMap = new RoundedBitMap(getContext());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        //RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);
        mProfileImage.setImageBitmap(getCroppedBitmap(bitmap));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*case R.id.tell_about_frg_skip:
              Toast.makeText(getContext(), "Skip", Toast.LENGTH_SHORT).show();
              startMainActivity();
              break;*/
            // case R.id.tell_about_frg_choose_file:
            //Intent govIdIntent = new Intent(Intent.ACTION_GET_CONTENT);
            //govIdIntent.setType("*/*");
            //startActivityForResult(govIdIntent,4);
            //showAlertDialog();
            //  break;
            case R.id.tell_me_about_profile_image:
                showAlertDialog();
                break;
            case R.id.tell_about_frg_save:
                getAllData();
                break;
          /*  case R.id.tell_about_frg_right:
                Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tell_about_frg_left:
                Fragment referDetailsFragment = new ReferDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, referDetailsFragment); // give your fragment container id in first parameter
                transaction.commit();
                break;*/
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void getAllData() {
        String tellMeABout = edit_TellYourSelf.getText().toString();
        if (tellMeABout.isEmpty()) {
            edit_TellYourSelf.setError("Enter about your self");
            edit_TellYourSelf.requestFocus();
            return;
        }
       // mAboutYourSelf = tellMeABout;
        apiSaveCall();
    }


    public void showAlertDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                   /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);*/
                    openCameraIntent();
                    Toast.makeText(getContext(), "Camera", Toast.LENGTH_SHORT).show();
                } else if (options[item].equals("Choose from Gallery")) {
                   /* Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);*/
                    pickImage();
                    Toast.makeText(getContext(), "Gallery", Toast.LENGTH_SHORT).show();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    public void pickImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
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
                photoURI = FileProvider.getUriForFile(getActivity(), "com.example.recaptchademo.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                pictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(pictureIntent, 1);
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
      //  mAboutYourSelfPath = imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        return image;
    }


    private void apiSaveCall() {
        if (SegmentandMachineOperatorFragment.OperatorSegment.size() == 0) {
            Toast.makeText(getActivity(), "Please select Segment", Toast.LENGTH_SHORT).show();
            return;
        } else if (SegmentandMachineOperatorFragment.OperatorWorkAssociation == null) {
            Toast.makeText(getActivity(), "Please select work association", Toast.LENGTH_SHORT).show();
            return;
        } else if (OperatorchargeDetailsFragment.operatorAmount == null) {
            Toast.makeText(getActivity(), "Please select operator amount", Toast.LENGTH_SHORT).show();
            return;
        } else if (AddressFragment.mAddress1 == null) {
            Toast.makeText(getActivity(), "Please select address1", Toast.LENGTH_SHORT).show();
            return;
        } else if (AddressFragment.mCity == null) {
            Toast.makeText(getActivity(), "Please select city", Toast.LENGTH_SHORT).show();
            return;
        } else if (AddressFragment.mPincode == null) {
            Toast.makeText(getActivity(), "Please select pincode", Toast.LENGTH_SHORT).show();
            return;
        } else if (AddressFragment.mState == null) {
            Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
            return;
        } else if (AddressFragment.mCountry == null) {
            Toast.makeText(getActivity(), "Please select country", Toast.LENGTH_SHORT).show();
            return;
        } else if (GovernmentIdFragment.govId == null) {
            Toast.makeText(getActivity(), "Please select goverment id", Toast.LENGTH_SHORT).show();
            return;
        } else if (GovernmentIdFragment.govProofPath == null) {
            Toast.makeText(getActivity(), "Please select goverment proof", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Constants.MY_PROFILE) {

            if (BankDetailsFragment.mAccountHolder == null) {
                Toast.makeText(getActivity(), "Please select account holder", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mPayableAt == null) {
                Toast.makeText(getActivity(), "Please select payable at", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mBank == null) {
                Toast.makeText(getActivity(), "Please select bank name", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mAccountNo == null) {
                Toast.makeText(getActivity(), "Please select goverment proof", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mIFSCCode == null) {
                Toast.makeText(getActivity(), "Please select bank iFSC", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mPaytmAccountNo == null) {
                Toast.makeText(getActivity(), "Please select paytm number", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (ReferDetailsFragment.mRefer_Type != null) {
            if (ReferDetailsFragment.mRefer_Type.equals("'mareow' User Refferd")) {
                referType = "UR";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Others")) {
                referType = "OTH";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Advertisment")) {
                referType = "ADDH";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Marketing Team")) {
                referType = "MT";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Sales Team")) {
                referType = "ST";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Owner")) {
                referType = "REF_OWN";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Renter")) {
                referType = "REF_REN";
            }
        }

        MultipartBody.Part uRCPart=null;
        if (OperatorchargeDetailsFragment.certificateToRunPath != null) {

            File URCFile = new File(OperatorchargeDetailsFragment.certificateToRunPath);
            RequestBody uRCImage = RequestBody.create(MediaType.parse("image/*"), URCFile);
            uRCPart = MultipartBody.Part.createFormData("URC", URCFile.getName(), uRCImage);
        }
        MultipartBody.Part uMCPart=null;
        if (OperatorchargeDetailsFragment.credentialsForAgencyPath != null) {
            File UMCFile = new File(OperatorchargeDetailsFragment.credentialsForAgencyPath);
            RequestBody UMCImage = RequestBody.create(MediaType.parse("image/*"), UMCFile);
            uMCPart = MultipartBody.Part.createFormData("UMC", UMCFile.getName(), UMCImage);
        }

        File UGPFile = new File(GovernmentIdFragment.govProofPath);
        RequestBody uGCImage = RequestBody.create(MediaType.parse("image/*"), UGPFile);
        MultipartBody.Part uGCPart = null;

        if (GovernmentIdFragment.govId.equals("UPT")) {
            uGCPart = MultipartBody.Part.createFormData("UPT", UGPFile.getName(), uGCImage);
        }
        if (GovernmentIdFragment.govId.equals("UAD")) {
            uGCPart = MultipartBody.Part.createFormData("UAD", UGPFile.getName(), uGCImage);
        }
        if (GovernmentIdFragment.govId.equals("UVD")) {
            uGCPart = MultipartBody.Part.createFormData("UVD", UGPFile.getName(), uGCImage);
        }
        if (GovernmentIdFragment.govId.equals("UDL")) {
            uGCPart = MultipartBody.Part.createFormData("UDL", UGPFile.getName(), uGCImage);
        }
        if (GovernmentIdFragment.govId.equals("UPN")) {
            uGCPart = MultipartBody.Part.createFormData("UPN", UGPFile.getName(), uGCImage);
        }



        MultipartBody.Part uPIPart=null;
     //  if (TellAboutFragment.mAboutYourSelfPath!=null){
      //     File UPIfile = new File(TellAboutFragment.mAboutYourSelfPath);
          // RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
     //      uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
       //}

        JSONObject requestBody = getRequestBody();

        RequestBody draBody = null;

        try {
            draBody = RequestBody.create(MediaType.parse("text/plain"), requestBody.toString(1));
            //Log.d(TAG, "requestUploadSurvey: RequestBody : " + requestBody.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        String token = TokenManager.getSessionToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
      /*  Call<StatuTitleMessageResponse> updateCall = apiInterface.updateProfileDetails("Bearer " + token, uRCPart, uMCPart, uGCPart, uPIPart, draBody);
        updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 401) {
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse stm = new ForgotPasswordResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), ForgotPasswordResponse.class);
                            Toast.makeText(getContext(), stm.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("Error code:", e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private JSONObject getRequestBody() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", String.valueOf(TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID, 0)));
            jsonObject.put("firstName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME, null));
            jsonObject.put("lastName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME, null));
            jsonObject.put("userName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERNAME, null));
            jsonObject.put("email", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL, null));
            jsonObject.put("mobileNo", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO, null));

            JSONArray segmentList = new JSONArray();
            for (int i = 0; i < SegmentandMachineOperatorFragment.OperatorSegment.size(); i++) {
                segmentList.put(SegmentandMachineOperatorFragment.OperatorSegment.get(i));
            }
            jsonObject.put("segment", segmentList);
            jsonObject.put("association", SegmentandMachineOperatorFragment.OperatorWorkAssociation);
            jsonObject.put("address1", AddressFragment.mAddress1);
                jsonObject.put("address2", AddressFragment.mAddress2);
                jsonObject.put("address3", AddressFragment.mAddress3);


                jsonObject.put("address4", AddressFragment.mAddress4);


            jsonObject.put("city", AddressFragment.mCity);
            jsonObject.put("postal_code", AddressFragment.mPincode);
            jsonObject.put("state", AddressFragment.mState);
            jsonObject.put("country", AddressFragment.mCountry);

            jsonObject.put("govtProofId", GovernmentIdFragment.govId);
            jsonObject.put("accountHolder", BankDetailsFragment.mAccountHolder);
            jsonObject.put("accountNo", BankDetailsFragment.mAccountNo);
            jsonObject.put("bank", BankDetailsFragment.mBank);
            jsonObject.put("ifscCode", BankDetailsFragment.mIFSCCode);
            jsonObject.put("payableAtCity", BankDetailsFragment.mPayableAt);
            jsonObject.put("paytmAccount", BankDetailsFragment.mPaytmAccountNo);


            if (ReferDetailsFragment.mRefer_Type != null) {
                jsonObject.put("referType", referType);
                jsonObject.put("referMobileNo", ReferDetailsFragment.mRefer_MobileNo);
                jsonObject.put("referBy", ReferDetailsFragment.mRefer_By);
            }


            //jsonObject.put("aboutYourself", TellAboutFragment.mAboutYourSelf);
            jsonObject.put("attribute1", OperatorchargeDetailsFragment.AMOUNTTYPE);
            jsonObject.put("attribute2", Float.parseFloat(OperatorchargeDetailsFragment.operatorAmount));
            jsonObject.put("attribute3", OperatorchargeDetailsFragment.ACCOMODATION);
            jsonObject.put("attribute4", OperatorchargeDetailsFragment.TRASPORTATION);
            jsonObject.put("attribute5", OperatorchargeDetailsFragment.FOOD);

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

            } else {
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this, 10, perms)
                                .setRationale("Please allow this permission for proper functionality.")
                                .setPositiveButtonText("Ok")
                                .build());
            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // indicate image type and Uri
            cropIntent.setDataAndType(photoURI, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 260);
            cropIntent.putExtra("outputY", 260);
            cropIntent.putExtra("scaleUpIfNeeded", true);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast.makeText(getActivity(), "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                performCrop();
            } else if (requestCode == 2) {
                if (data == null) {
                    //Display an error
                    return;
                }
                photoURI = data.getData();
                performCrop();
            }
            if (requestCode == CROP_PIC) {
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
              //  photoURI=extras.getParcelable("data");
                // RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(thePic);
                mProfileImage.setImageBitmap(getCroppedBitmap(thePic));
            }
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

}
