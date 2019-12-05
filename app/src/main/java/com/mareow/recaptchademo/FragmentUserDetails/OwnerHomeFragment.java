package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.Activities.DetailsSubmissionActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RealFilePath;
import com.mareow.recaptchademo.Utils.RoundedBitMap;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerHomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout btnGeneralDetails;
    LinearLayout btnSegmentAndGovernmentId;
    LinearLayout btnOperatorSpecific;
    LinearLayout btnAddressDetails;
    LinearLayout btnBankDetails;
    LinearLayout btnReferDetails;



    AppCompatImageView mIcon;
    AppCompatImageView mIconMask;
    AppCompatImageView mVerified;

    AppCompatTextView mUsername;
    AppCompatTextView mUserRole;
    AppCompatTextView mUserEmail;
    AppCompatTextView mUserMobile;
    ProgressDialog progressDialog;
    AppCompatTextView btnSkip;
    String imageFilePath;
    Uri photoURI;
    private final int CROP_PIC = 1001;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    //public static String mAboutYourSelf=null;
    public static String mAboutYourSelfPathOwner=null;

    public static String ownerProfileImage=null;



    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerHomeFragment newInstance(String param1, String param2) {
        OwnerHomeFragment fragment = new OwnerHomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_owner_home, container, false);
        OwnerMainActivity.navItemIndexOwner=17;

        initView(view);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(getActivity(),perms)){

            }else {
                EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initView(View view) {

        btnGeneralDetails=(LinearLayout) view.findViewById(R.id.owner_home_general_details);
        //btnOperatorSpecific=(LinearLayout) view.findViewById(R.id.operator_home_operator_specific);
        btnAddressDetails=(LinearLayout) view.findViewById(R.id.owner_home_address_details);
        btnBankDetails=(LinearLayout) view.findViewById(R.id.owner_home_bank_details);
        btnSegmentAndGovernmentId=(LinearLayout) view.findViewById(R.id.owner_home_gov_details);
        btnReferDetails=(LinearLayout) view.findViewById(R.id.owner_home_refer_details);

        btnGeneralDetails.setOnClickListener(this);
        btnSegmentAndGovernmentId.setOnClickListener(this);
//        btnOperatorSpecific.setOnClickListener(this);
        btnAddressDetails.setOnClickListener(this);
        btnBankDetails.setOnClickListener(this);
        btnReferDetails.setOnClickListener(this);


        mIcon=(AppCompatImageView)view.findViewById(R.id.icon_owner);
        mIcon.setOnClickListener(this);
        // mIcon.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_translate_anim));
        mIconMask=(AppCompatImageView)view.findViewById(R.id.icon_mask_owner);

        mVerified=(AppCompatImageView)view.findViewById(R.id.owner_home_user_verified);


      /*  if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLATINUM") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLAT")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SILVER") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SLIVE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAMOND") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAM")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_diamond));
        }*/


        if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Blue")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Platinum")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Gold")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Silver")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Diamond")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_diamond));
        }


        mUsername=(AppCompatTextView)view.findViewById(R.id.owner_home_header_username);
        mUserEmail=(AppCompatTextView)view.findViewById(R.id.owner_home_header_email);
        mUserMobile=(AppCompatTextView)view.findViewById(R.id.owner_home_header_mobile);
        mUserRole=(AppCompatTextView)view.findViewById(R.id.owner_home_header_role);


        if (Constants.MY_PROFILE){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .transform(new CircleTransform(getContext()));

            if (MyProfileFragment.mUSerProfileDataList.getUserImagePath()!=null){
                /* Glide.with(getActivity()).load("http://18.204.165.238:8080/mareow-api/"+MainDashBoardFragment.mUserProfileData.getUserImagePath())
                        .placeholder(R.drawable.profile)
                        .transform(new CircleTransform(getActivity()))
                        .into(mProfile);*/

                if (ownerProfileImage!=null){
                    Glide.with(getActivity()).load(Uri.fromFile(new File(ownerProfileImage)))
                            .apply(options)
                            .into(mIcon);
                }else {

                    Glide.with(getActivity()).load(MyProfileFragment.mUSerProfileDataList.getUserImageFile())
                            .apply(options)
                            .into(mIcon);

                    mAboutYourSelfPathOwner=MyProfileFragment.mUSerProfileDataList.getUserImagePath();

                    Glide.with(getActivity()).load(MyProfileFragment.mUSerProfileDataList.getUserImageFile())
                            .apply(options)
                            .into(RenterMainActivity.imgIconRenter);

                }

                // profileImageFile=new File(MyProfileFragment.userNewProfileData.getUserImageFile());

            }else {
                if (ownerProfileImage!=null){
                    Glide.with(getActivity()).load(Uri.fromFile(new File(ownerProfileImage)))
                            .apply(options)
                            .into(mIcon);
                }else {
                    setmProfileImage();
                }
            }

            mUsername.setText(MyProfileFragment.mUSerProfileDataList.getFirstName()
                    +" "+MyProfileFragment.mUSerProfileDataList.getLastName());

            OwnerMainActivity.txtOwnerUsername.setText(MyProfileFragment.mUSerProfileDataList.getFirstName()+" "+MyProfileFragment.mUSerProfileDataList.getLastName());

            mUserEmail.setText(MyProfileFragment.mUSerProfileDataList.getEmail());
            mUserMobile.setText(MyProfileFragment.mUSerProfileDataList.getMobileNo());
            mUserRole.setText("("+ TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null)+")");
            if (MyProfileFragment.mUSerProfileDataList.isVerified()){
                mVerified.setImageResource(R.drawable.ic_verify_true);
            }else {
                mVerified.setImageResource(R.drawable.ic_verify_false);
            }

        }else {

            setmProfileImage();
            mUsername.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null)
                    +" "+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null));
            mUserEmail.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL,null));
            mUserMobile.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO,null));
            mUserRole.setText("("+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null)+")");
            if (TokenManager.getUserDetailsPreference().getBoolean(Constants.PREF_KEY_IS_VERIFIED,false)){
                mVerified.setImageResource(R.drawable.ic_verify_true);
            }else {
                mVerified.setImageResource(R.drawable.ic_verify_false);
            }


        }

    }

    private void setmProfileImage(){

        RoundedBitMap roundedBitMap=new RoundedBitMap(getActivity());
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
        //  RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);
        mIcon.setImageBitmap(getCircularBitmap(bitmap));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.owner_home_general_details:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(1,true);
                }else {
                    MyProfileFragment.myProfileViewPager.setCurrentItem(1,true);
                }
                break;
            case R.id.owner_home_gov_details:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(3,true);
                }else {
                  /*  Toast.makeText(getContext(), "Operator charge", Toast.LENGTH_SHORT).show();
                    Fragment addFragment=new OperatorchargeDetailsFragment();
                    loadFrgaments(addFragment);*/
                    MyProfileFragment.myProfileViewPager.setCurrentItem(3,true);
                }
                break;
           /* case R.id.renter_home_operator_specific:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(2,true);
                }else {
                 *//*   Toast.makeText(getContext(), "Address", Toast.LENGTH_SHORT).show();
                    Fragment govFragment=new AddressFragment();
                    loadFrgaments(govFragment);*//*
                    MyProfileFragment.myProfileViewPager.setCurrentItem(2,true);
                }
                break;*/
            case R.id.owner_home_address_details:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(2,true);
                }else {
                   /* Toast.makeText(getContext(), "Government Id", Toast.LENGTH_SHORT).show();
                    Fragment gstFragment=new GovernmentIdFragment();
                    loadFrgaments(gstFragment);*/
                    MyProfileFragment.myProfileViewPager.setCurrentItem(2,true);
                }
                break;
            case R.id.owner_home_bank_details:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(4,true);
                }else {
                  /*  Toast.makeText(getContext(), "Bank", Toast.LENGTH_SHORT).show();
                    Fragment bankFragment=new BankDetailsFragment();
                    loadFrgaments(bankFragment);*/
                    MyProfileFragment.myProfileViewPager.setCurrentItem(4,true);
                }
                break;
            case R.id.owner_home_refer_details:
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(5,true);
                }else {
                    MyProfileFragment.myProfileViewPager.setCurrentItem(5,true);
                }
                break;
            case R.id.icon_owner:
                showAlertDialog();
                break;
        }

    }

    public void showAlertDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery"/*, "Cancel"*/};
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
                pickImage();
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
                photoURI = FileProvider.getUriForFile(getActivity(), "com.mareow.recaptchademo.provider", photoFile);
                getContext().grantUriPermission("com.android.camera",photoURI,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
        mAboutYourSelfPathOwner = imageFilePath;
        ownerProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                // performCrop();
                Bitmap bitmap=null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mIcon.setImageBitmap(getCircularBitmap(bitmap));

            } else if (requestCode == 2) {
                if (data == null) {
                    //Display an error
                    return;
                }
                photoURI = data.getData();

                mAboutYourSelfPathOwner= RealFilePath.getPath(getActivity(),data.getData());
                ownerProfileImage= RealFilePath.getPath(getActivity(),data.getData());

                Bitmap bitmap=null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIcon.setImageBitmap(getCircularBitmap(bitmap));

            }
            if (requestCode == CROP_PIC) {
                Bundle extras = data.getExtras();
                //mAboutYourSelfPath=data.getData().getPath();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                //RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(thePic);
                // RoundedBitMap roundedBitMap=new RoundedBitMap(getActivity());
                // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
                // RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(thePic);
                //mProfile.setImageBitmap(getCroppedBitmap(thePic));
                mIcon.setImageBitmap(getCroppedBitmap(thePic));
            }
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED &&grantResults[2]==PackageManager.PERMISSION_GRANTED
                    && grantResults[3]==PackageManager.PERMISSION_GRANTED &&grantResults[4]==PackageManager.PERMISSION_GRANTED){

            }else {
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,10,perms)
                                .setRationale("Please allow this permission for proper functionality.")
                                .setPositiveButtonText("Ok")
                                .build());
            }
        }
    }


    protected Bitmap getCircularBitmap(Bitmap srcBitmap) {
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap dstBitmap = Bitmap.createBitmap(
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Canvas canvas = new Canvas(dstBitmap);

        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);

        RectF rectF = new RectF(rect);

        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Calculate the left and top of copied bitmap
        float left = (squareBitmapWidth-srcBitmap.getWidth())/2;
        float top = (squareBitmapWidth-srcBitmap.getHeight())/2;

        canvas.drawBitmap(srcBitmap, left, top, paint);

        // Free the native object associated with this bitmap.
        srcBitmap.recycle();

        // Return the circular bitmap
        return dstBitmap;
    }

}
