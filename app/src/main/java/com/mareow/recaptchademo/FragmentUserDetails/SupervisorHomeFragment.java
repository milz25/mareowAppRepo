package com.mareow.recaptchademo.FragmentUserDetails;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.Activities.DetailsSubmissionActivity;
import com.mareow.recaptchademo.Activities.MainActivity;
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
import com.bumptech.glide.Priority;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupervisorHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupervisorHomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout btnAddressDetails;
    private LinearLayout btnGovernmentId;
    private LinearLayout btnReferFriends;
    //private LinearLayout btnTellAbout;
    private LinearLayout btnGeneralDetail;

   // LinearLayout sectionGeneralDetails;

    private AppCompatTextView mUsername;
    private AppCompatTextView mRole;
    private AppCompatTextView mEmail;
    private AppCompatTextView mMobile;

    private AppCompatImageView mIcon;
    private AppCompatImageView mIconMask;
    private AppCompatImageView mVerified;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    String imageFilePath;
    Uri photoURI;
    private final int CROP_PIC_SUP = 1000;

    //public static String mAboutYourSelf=null;
    public static String mAboutYourSelfPathSuper=null;
    public static String superProfileImage=null;


    public SupervisorHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SupervisorHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupervisorHomeFragment newInstance(String param1, String param2) {
        SupervisorHomeFragment fragment = new SupervisorHomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_supervisor_home, container, false);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(getActivity(),perms)){

            }else {
                EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }
        MainActivity.navItemIndex=17;
        initView(view);
        return view;
    }

    private void initView(View view) {

        btnAddressDetails=(LinearLayout) view.findViewById(R.id.super_home_address_details);
        btnGovernmentId=(LinearLayout)view.findViewById(R.id.super_home_gov_id_details);
        btnReferFriends=(LinearLayout)view.findViewById(R.id.super_home_refer_details);
        //btnTellAbout=(RelativeLayout)view.findViewById(R.id.super_home_ln4);
        btnGeneralDetail=(LinearLayout)view.findViewById(R.id.super_home_general_details);
        //sectionGeneralDetails=(LinearLayout)view.findViewById(R.id.super_home_general_details);

       // btnSave=(AppCompatTextView)view.findViewById(R.id.home_save);
        //btnSkip=(AppCompatTextView)view.findViewById(R.id.home_skip);

        btnAddressDetails.setOnClickListener(this);
        btnGovernmentId.setOnClickListener(this);
        btnReferFriends.setOnClickListener(this);
      //  btnTellAbout.setOnClickListener(this);
      //  btnSave.setOnClickListener(this);
      //  btnSkip.setOnClickListener(this);
        btnGeneralDetail.setOnClickListener(this);

        mIcon=(AppCompatImageView)view.findViewById(R.id.icon_sup);
        mIcon.setOnClickListener(this);
        mIconMask=(AppCompatImageView)view.findViewById(R.id.icon_mask_sup);
        mVerified=(AppCompatImageView)view.findViewById(R.id.super_home_user_verified);

       /* if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLATINUM") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLAT")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SILVER") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SLIVE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAMOND") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAM")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_diamond));
        }
*/


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

        mUsername=(AppCompatTextView)view.findViewById(R.id.super_home_header_username);
        mRole=(AppCompatTextView)view.findViewById(R.id.super_home_header_role);
        mEmail=(AppCompatTextView)view.findViewById(R.id.super_home_header_email);
        mMobile=(AppCompatTextView)view.findViewById(R.id.super_home_header_mobile);




        if (Constants.MY_PROFILE){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .transform(new CircleTransform(getActivity()));
            if (MyProfileFragment.mUSerProfileDataList.getUserImagePath()!=null){

              /*  RoundedBitMap roundedBitMap=new RoundedBitMap(getActivity());
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
                RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);
*/

                if (superProfileImage!=null){
                    Glide.with(getActivity()).load(Uri.fromFile(new File(superProfileImage)))
                            .apply(options)
                            .into(mIcon);
                }else {

                    Glide.with(getActivity()).load(MyProfileFragment.mUSerProfileDataList.getUserImageFile())
                            .apply(options)
                            .into(mIcon);

                    mAboutYourSelfPathSuper=MyProfileFragment.mUSerProfileDataList.getUserImagePath();
                    Glide.with(getActivity()).load(MyProfileFragment.mUSerProfileDataList.getUserImageFile())
                            .apply(options)
                            .into( MainActivity.imgIcon);
                }





            }else {
                if (superProfileImage!=null){
                    Glide.with(getActivity()).load(Uri.fromFile(new File(superProfileImage)))
                            .apply(options)
                            .into(mIcon);
                }else {
                    setmProfileImage();
                }

            }

            mUsername.setText(MyProfileFragment.mUSerProfileDataList.getFirstName()
                    +" "+MyProfileFragment.mUSerProfileDataList.getLastName());
            MainActivity.txtUsername.setText(MyProfileFragment.mUSerProfileDataList.getFirstName()+" "+MyProfileFragment.mUSerProfileDataList.getLastName());
            mEmail.setText(MyProfileFragment.mUSerProfileDataList.getEmail());
            mMobile.setText(MyProfileFragment.mUSerProfileDataList.getMobileNo());
            mRole.setText("("+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null)+")");

            if (MyProfileFragment.mUSerProfileDataList.isVerified()){
                mVerified.setImageResource(R.drawable.ic_verify_true);
            }else {
                mVerified.setImageResource(R.drawable.ic_verify_false);
            }

        }else {

            setmProfileImage();
            mUsername.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null)
                    +" "+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null));
            mEmail.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL,null));
            mMobile.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO,null));
            mRole.setText("("+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null)+")");

            if (TokenManager.getUserDetailsPreference().getBoolean(Constants.PREF_KEY_IS_VERIFIED,false)){
                mVerified.setImageResource(R.drawable.ic_verify_true);
            }else {
                mVerified.setImageResource(R.drawable.ic_verify_false);
            }
        }

       /* setmProfileImage();
        mUsername.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null)+" "+
                          TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null));

        mRole.setText("("+TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null)+")");

        mEmail.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL,null));
        mMobile.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO,null));

        if (Constants.MY_PROFILE){
           // sectionGeneralDetails.setVisibility(View.VISIBLE);
        }
        if (Constants.REGISTRATION_DETAIL){
         //   sectionGeneralDetails.setVisibility(View.GONE);
        }*/

    }


    private void setmProfileImage(){
        RoundedBitMap roundedBitMap=new RoundedBitMap(getActivity());
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
       // RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);
        mIcon.setImageBitmap(getCircularBitmap(bitmap));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.super_home_general_details:
               /* Toast.makeText(getContext(), "Address", Toast.LENGTH_SHORT).show();
                Fragment addFragment=new AddressFragment();
                loadFrgaments(addFragment);*/
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(1,true);
                }else {
                    MyProfileFragment.myProfileViewPager.setCurrentItem(1,true);
                }
                break;
            case R.id.super_home_address_details:
             /*   Toast.makeText(getContext(), "Government Id", Toast.LENGTH_SHORT).show();
                Fragment govFragment=new GovernmentIdFragment();
                loadFrgaments(govFragment);*/
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(2,true);
                }else {
                    MyProfileFragment.myProfileViewPager.setCurrentItem(2,true);
                }
                break;
            case R.id.super_home_gov_id_details:
               /* Toast.makeText(getContext(), "Refer Frineds", Toast.LENGTH_SHORT).show();
                Fragment referFragment=new ReferDetailsFragment();
                loadFrgaments(referFragment);*/
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(3,true);
                }else {

                    MyProfileFragment.myProfileViewPager.setCurrentItem(3,true);
                }
                break;
            case R.id.super_home_refer_details:
             /*   Toast.makeText(getContext(), "Tell about Yourself", Toast.LENGTH_SHORT).show();
                Fragment tellAboutFragment=new TellAboutFragment();
                loadFrgaments(tellAboutFragment);*/
                if (Constants.REGISTRATION_DETAIL){
                    DetailsSubmissionActivity.detailsActivityViewPager.setCurrentItem(4,true);
                }else {
                    MyProfileFragment.myProfileViewPager.setCurrentItem(4,true);
                }
                break;
            case R.id.icon_sup:
                showAlertDialog();
                break;

        }

    }

  /*  private void callMainActivity() {
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }*/

    private void loadFrgaments(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, fragment ); // give your fragment container id in first parameter
        transaction.commit();

    }

    private void showAlertDialog() {

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

    private void pickImage() {
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
        mAboutYourSelfPathSuper = imageFilePath;
        superProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        return image;
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
            startActivityForResult(cropIntent, CROP_PIC_SUP);
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
                //performCrop();
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

                mAboutYourSelfPathSuper= RealFilePath.getPath(getActivity(),data.getData());
                superProfileImage=RealFilePath.getPath(getActivity(),data.getData());

                Bitmap bitmap=null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIcon.setImageBitmap(getCircularBitmap(bitmap));

                //performCrop();
            }
            if (requestCode == CROP_PIC_SUP) {
                Bundle extras = data.getExtras();
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
