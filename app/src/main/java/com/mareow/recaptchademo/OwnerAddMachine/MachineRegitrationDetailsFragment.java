package com.mareow.recaptchademo.OwnerAddMachine;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineRegitrationDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineRegitrationDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editRegisterNo;
    TextInputEditText editRegisterDate;
    TextInputEditText editEngineNo;
    TextInputEditText editChassisNo;
    TextInputEditText editManufacturYear;
    TextInputEditText editOwnerShip;
    TextInputEditText editMachineFitness;
    TextInputEditText editUniqueNo;


    AppCompatImageView btnChooseRCBOOk;
    AppCompatImageView btnChooseNationalPermit;
    AppCompatImageView btnChoosePUC;
    AppCompatImageView btnChooseRoadTax;

   /* AppCompatCheckBox checkBoxRCBOOk;
    AppCompatCheckBox checkBoxNatinalPermit;
    AppCompatCheckBox checkBoxPUC;
    AppCompatCheckBox checkBoxRoadTax;*/

    AppCompatTextView txtNoFileRCBOOK;
    AppCompatTextView txtNoFileNationalPermit;
    AppCompatTextView txtNoFilePUC;
    AppCompatTextView txtNoFileRoadTax;

    AppCompatImageView btnRegistrationDate;



    public static String mRCBookPath=null;
    public static String mPUCCertificatePath=null;
    public static String mNationalPermitPath=null;
    public static String mRoadTaxPath=null;

    public static String REGISTER_NO=null;
    public static String REGISTER_DATE=null;
    public static String ENGINE_NO=null;
    public static String CHASSIS_NO=null;
    public static String MANUFACTURING_YEAR=null;
    public static String MACHINE_FITNESS=null;
    public static String OWNERSHIP=null;
    public static String UNIQUENO=null;

    public static boolean RC_BOOK_FLAG=false;
    public static boolean PUC_CERTIFICATE_FLAG=false;
    public static boolean NATIONAL_PERMIT_FLAG=false;
    public static boolean ROAD_TAX_FLAG=false;




    public  final int RC_BOOK_CONSTANT=00;
    public  final int PUC_CONSTANT=01;
    public  final int NATIONAL_PERMIT_CONSTANT=02;
    public  final int RAOD_TAx_CONSTANT=03;

    public  final int RC_BOOK_CAMERA=04;
    public  final int PUC_CAMERA=05;
    public  final int NATIONAL_PERMIT_CAMERA=06;
    public  final int RAOD_TAx_CAMERA=07;


    String selecteYear;

    boolean REGISTER_MCFITNESS=false;

    String imageFilePath;
    Uri photoURI;

    boolean GALLERY_CAMERA=false;

    public MachineRegitrationDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineRegitrationDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineRegitrationDetailsFragment newInstance(String param1, String param2) {
        MachineRegitrationDetailsFragment fragment = new MachineRegitrationDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine_regitration_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        editUniqueNo=(TextInputEditText)view.findViewById(R.id.OMD_MRD_unique_no);
        if (UNIQUENO!=null){
            editUniqueNo.setText(UNIQUENO);
        }
        editUniqueNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UNIQUENO=s.toString();
            }
        });
        editRegisterNo=(TextInputEditText)view.findViewById(R.id.OMD_MRD_register_no);
        if (REGISTER_NO!=null){
            editRegisterNo.setText(REGISTER_NO);
        }
        editRegisterNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              REGISTER_NO=s.toString();
            }
        });
        btnRegistrationDate=(AppCompatImageView)view.findViewById(R.id.OMD_MRD_btnregisterDate);
        btnRegistrationDate.setOnClickListener(this);

        editRegisterDate=(TextInputEditText)view.findViewById(R.id.OMD_MRD_registerDate);
        if (REGISTER_DATE!=null){
            editRegisterDate.setText(REGISTER_DATE);
        }
        editRegisterDate.setInputType(InputType.TYPE_NULL);
        editRegisterDate.setOnClickListener(this);
        editRegisterDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    REGISTER_MCFITNESS=true;
                    showDateDialog();
                }
            }
        });
        editEngineNo=(TextInputEditText)view.findViewById(R.id.OMD_MRD_engine_no);
        if (ENGINE_NO!=null){
            editEngineNo.setText(ENGINE_NO);
        }
        editEngineNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              ENGINE_NO=s.toString();
            }
        });
        editChassisNo=(TextInputEditText)view.findViewById(R.id.OMD_MRD_chassis_no);
        if (CHASSIS_NO!=null){
            editChassisNo.setText(CHASSIS_NO);
        }
        editChassisNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CHASSIS_NO=s.toString();
            }
        });
        editManufacturYear=(TextInputEditText)view.findViewById(R.id.OMD_MRD_manufacture_year);
        editManufacturYear.setOnClickListener(this);
        editManufacturYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showYearPicker();
                }
            }
        });
        if (MANUFACTURING_YEAR!=null){
            editManufacturYear.setText(MANUFACTURING_YEAR);
        }

        /*editManufacturYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              MANUFACTURING_YEAR=s.toString();
            }
        });*/
        editOwnerShip=(TextInputEditText)view.findViewById(R.id.OMD_MRD_ownership);
        if (OWNERSHIP!=null){
            editOwnerShip.setText(OWNERSHIP);
        }
        editOwnerShip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              OWNERSHIP=s.toString();
            }
        });
        editMachineFitness=(TextInputEditText)view.findViewById(R.id.OMD_MRD_machine_fitness);
        editMachineFitness.setOnClickListener(this);
        editMachineFitness.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    REGISTER_MCFITNESS=false;
                    showDateDialog();
                }
            }
        });
        if (MACHINE_FITNESS!=null){
            editMachineFitness.setText(MACHINE_FITNESS);
        }



        btnChooseRCBOOk=(AppCompatImageView)view.findViewById(R.id.OMD_MRD_RCBOOK_choosefile);
        btnChooseRCBOOk.setOnClickListener(this);
        btnChooseNationalPermit=(AppCompatImageView)view.findViewById(R.id.OMD_MRD_NP_choosefile);
        btnChooseNationalPermit.setOnClickListener(this);
        btnChoosePUC=(AppCompatImageView)view.findViewById(R.id.OMD_MRD_PUC_choosefile);
        btnChoosePUC.setOnClickListener(this);
        btnChooseRoadTax=(AppCompatImageView)view.findViewById(R.id.OMD_MRD_TAX_choosefile);
        btnChooseRoadTax.setOnClickListener(this);



/*
        checkBoxRCBOOk=(AppCompatCheckBox)view.findViewById(R.id.OMD_MRD_Rc_book_checkbox);
        checkBoxRCBOOk.setChecked(RC_BOOK_FLAG);
        checkBoxRCBOOk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RC_BOOK_FLAG=true;
                }else {
                    RC_BOOK_FLAG=false;
                }
            }
        });
        checkBoxNatinalPermit=(AppCompatCheckBox)view.findViewById(R.id.OMD_MRD_national_permit_checkbox);
        checkBoxNatinalPermit.setChecked(NATIONAL_PERMIT_FLAG);
        checkBoxNatinalPermit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NATIONAL_PERMIT_FLAG=true;
                }else {
                    NATIONAL_PERMIT_FLAG=false;
                }
            }
        });
        checkBoxPUC=(AppCompatCheckBox)view.findViewById(R.id.OMD_MRD_PUC_checkbox);
        checkBoxPUC.setChecked(PUC_CERTIFICATE_FLAG);
        checkBoxPUC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                     PUC_CERTIFICATE_FLAG=true;
                }else {
                    PUC_CERTIFICATE_FLAG=false;
                }
            }
        });
        checkBoxRoadTax=(AppCompatCheckBox)view.findViewById(R.id.OMD_MRD_road_tax_checkbox);
        checkBoxRoadTax.setChecked(ROAD_TAX_FLAG);
        checkBoxRoadTax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ROAD_TAX_FLAG=true;
                }else {
                    ROAD_TAX_FLAG=false;
                }
            }
        });
*/



        txtNoFileRCBOOK=(AppCompatTextView)view.findViewById(R.id.OMD_MRD_RCBOOK_no_file_selected);
        txtNoFileNationalPermit=(AppCompatTextView)view.findViewById(R.id.OMD_MRD_NP_no_file_selected);
        txtNoFilePUC=(AppCompatTextView)view.findViewById(R.id.OMD_MRD_PUC_no_file_selected);
        txtNoFileRoadTax=(AppCompatTextView)view.findViewById(R.id.OMD_MRD_TAX_no_file_selected);







    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.OMD_MRD_btnregisterDate:
                 REGISTER_MCFITNESS=true;
                 showDateDialog();
                 break;
             case R.id.OMD_MRD_registerDate:
                 REGISTER_MCFITNESS=true;
                 showDateDialog();
                 break;
             case R.id.OMD_MRD_RCBOOK_choosefile:
                 Constants.RC_BOOK=true;
                 Constants.PUC_CERTIFICATE=false;
                 Constants.NATIONAL_PERMIT=false;
                 Constants.ROAD_TAX=false;
                 showAlertDialog();
                 break;
             case R.id.OMD_MRD_NP_choosefile:
                 Constants.RC_BOOK=false;
                 Constants.PUC_CERTIFICATE=false;
                 Constants.NATIONAL_PERMIT=true;
                 Constants.ROAD_TAX=false;
                 showAlertDialog();
                 break;
             case R.id.OMD_MRD_PUC_choosefile:
                 Constants.RC_BOOK=false;
                 Constants.PUC_CERTIFICATE=true;
                 Constants.NATIONAL_PERMIT=false;
                 Constants.ROAD_TAX=false;
                 showAlertDialog();
                 break;
             case R.id.OMD_MRD_TAX_choosefile:
                 Constants.RC_BOOK=false;
                 Constants.PUC_CERTIFICATE=false;
                 Constants.NATIONAL_PERMIT=false;
                 Constants.ROAD_TAX=true;
                 showAlertDialog();
                 break;
             case R.id.OMD_MRD_manufacture_year:
                showYearPicker();
                 break;
             case R.id.OMD_MRD_machine_fitness:
                 REGISTER_MCFITNESS=false;
                 showDateDialog();
                 break;


         }
    }


    public void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        long todayTime=newCalendar.getTime().getTime();
        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String dateString="";
                if ((monthOfYear+1)<10){
                    if (dayOfMonth<10){
                        dateString="0"+String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }else {
                        dateString=String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }

                }else {
                    if (dayOfMonth<10){
                        dateString="0"+String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }else {
                        dateString=String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }

                }

                if (REGISTER_MCFITNESS){
                    editRegisterDate.setText(dateString);
                    REGISTER_DATE=dateString;
                }else {
                    editMachineFitness.setText(dateString);
                    MACHINE_FITNESS=dateString;
                }



            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMaxDate(todayTime);
        StartTime .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){

            if (GALLERY_CAMERA){
                if (requestCode==RC_BOOK_CONSTANT){
                    Uri uri = data.getData();
                    mRCBookPath= FileUtils.getPath(getActivity(),uri);
                    txtNoFileRCBOOK.setText(FileUtils.getFileName(getActivity(),Uri.parse(mRCBookPath)));
                }
                if (requestCode==NATIONAL_PERMIT_CONSTANT){
                    Uri uri1 = data.getData();
                    mNationalPermitPath=FileUtils.getPath(getActivity(),uri1);
                    txtNoFileNationalPermit.setText(FileUtils.getFileName(getActivity(),Uri.parse(mNationalPermitPath)));
                }
                if (requestCode==PUC_CONSTANT){
                    Uri uri1 = data.getData();
                    mPUCCertificatePath=FileUtils.getPath(getActivity(),uri1);
                    txtNoFilePUC.setText(FileUtils.getFileName(getActivity(),Uri.parse(mPUCCertificatePath)));
                }
                if (requestCode==RAOD_TAx_CONSTANT){
                    Uri uri1 = data.getData();
                    mRoadTaxPath=FileUtils.getPath(getActivity(),uri1);
                    txtNoFileRoadTax.setText(FileUtils.getFileName(getActivity(),Uri.parse(mRoadTaxPath)));
                }

            }else {
                if (requestCode==RC_BOOK_CAMERA){
                    txtNoFileRCBOOK.setText(FileUtils.getFileName(getActivity(),Uri.parse(mRCBookPath)));
                }
                if (requestCode==NATIONAL_PERMIT_CAMERA){
                    txtNoFileNationalPermit.setText(FileUtils.getFileName(getActivity(),Uri.parse(mNationalPermitPath)));
                }
                if (requestCode==PUC_CAMERA){
                    txtNoFilePUC.setText(FileUtils.getFileName(getActivity(),Uri.parse(mPUCCertificatePath)));
                }
                if (requestCode==RAOD_TAx_CAMERA){
                    txtNoFileRoadTax.setText(FileUtils.getFileName(getActivity(),Uri.parse(mRoadTaxPath)));
                }

            }

        }

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

        GALLERY_CAMERA=true;
        if (Constants.RC_BOOK){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), RC_BOOK_CONSTANT);
        }else if (Constants.PUC_CERTIFICATE){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), PUC_CONSTANT);
        }else if (Constants.NATIONAL_PERMIT){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), NATIONAL_PERMIT_CONSTANT);
        }else if (Constants.ROAD_TAX){
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), RAOD_TAx_CONSTANT);
        }



     }




    private void showYearPicker() {


        NumberPicker numberPicker;
        AppCompatTextView btnCancel;
        AppCompatTextView btnOk;
        AppCompatImageButton btnClose;
        final Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.month_year_picker_dialog);

        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        btnClose=(AppCompatImageButton)d.findViewById(R.id.yeardialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
       // btnCancel = (AppCompatTextView) d.findViewById(R.id.yeardialog_cancel);
        btnOk = (AppCompatTextView) d.findViewById(R.id.yeardialog_Ok);
        numberPicker = (NumberPicker) d.findViewById(R.id.yeardialog_numberPicker);

        Calendar calendar=Calendar.getInstance();
        int maxYear=calendar.get(Calendar.YEAR);
        numberPicker.setMinValue(1900);
        numberPicker.setMaxValue(maxYear);
        numberPicker.setValue(maxYear);

        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selecteYear=String.valueOf(newVal);
            }
        });

       /* btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              d.dismiss();
            }
        });*/

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editManufacturYear.setText(selecteYear);
                MANUFACTURING_YEAR=selecteYear;
                d.dismiss();
            }
        });

        d.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();

    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
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

                GALLERY_CAMERA=false;

                if (Constants.RC_BOOK){
                    startActivityForResult(Intent.createChooser(pictureIntent,"ChooseFile"), RC_BOOK_CAMERA);
                }else if (Constants.PUC_CERTIFICATE){
                    startActivityForResult(Intent.createChooser(pictureIntent,"ChooseFile"), PUC_CAMERA);
                }else if (Constants.NATIONAL_PERMIT){
                    startActivityForResult(Intent.createChooser(pictureIntent,"ChooseFile"), NATIONAL_PERMIT_CAMERA);
                }else if (Constants.ROAD_TAX){
                    startActivityForResult(Intent.createChooser(pictureIntent,"ChooseFile"), RAOD_TAx_CAMERA);
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
        if (Constants.RC_BOOK){
            mRCBookPath=imageFilePath;
        }else if (Constants.PUC_CERTIFICATE){
            mPUCCertificatePath=imageFilePath;
        }else if (Constants.NATIONAL_PERMIT){
            mNationalPermitPath=imageFilePath;
        }else if (Constants.ROAD_TAX){
            mRoadTaxPath=imageFilePath;
        }
        //mProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
    }


}
