package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.DataModels.InvoiceByInvoiceId;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.MareowCharges;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.UpdatedWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditInvoiceFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editWorkOrderNo;
    TextInputEditText editInvoiceDate;
    TextInputEditText editInvoiceNo;
    TextInputEditText editInvoiceAmount;
    TextInputEditText editRemainingAmount;
    TextInputEditText editInvoiceCategory;
    AppCompatCheckBox checkBoxInvoceGST;
    AppCompatCheckBox checkBoxInvoceIGST;
    TextInputEditText editInvoiceNetAmount;
    TextInputEditText editmareowServiceCharge;
    TextInputEditText editRemarks;

    TextInputEditText editCGST;
    TextInputEditText editSGST;
    TextInputEditText editIGST;



    AppCompatImageButton btnInvoiceDate;
    AppCompatTextView txtNoFileChoose;
    AppCompatImageView btnChooseFile;

    public String mUploadingDocument=null;


    HashMap<String,String> invoiceCategoryMap = new HashMap<>();
    HashMap<String,String>  workOrderMap = new HashMap<>();
    String INVOICE_CATEGORY_CODE=null;
    String WORK_ORDER_ID;
    String SELECTED_WORK_ORDER_NO=null;

    FloatingActionButton btnSave;

    FrameLayout gstFrame;
    FrameLayout igstFrame;



    ProgressDialog progressDialog;
    List<OfferWorkOrder> workOrder=new ArrayList<>();
    UpdatedWorkOrder updatedWorkOrder;

    float CGST_AMOUNT=0;
    float SGST_AMOUNT=0;
    float IGST_AMOUNT=0;

    List<MareowCharges> mareowChargesList=new ArrayList<>();

    float mareowServiceChargesForRenter;
    float mareowServiceChargesForOwner;

    boolean INVOICE_CATEGORY_CHECK=false;

    String imageFilePath;
    Uri photoURI;
    public final int EDIT_INVOICE_DOC=504;
    public final int EDIT_INVOICE_DOC_CAMERA=505;

    public String SELECTED_INVOICE_AMOUNT="";
    InvoiceByInvoiceId invoiceDetails;
    InvoiceByUser invoiceByUser;
    public EditInvoiceFragment(InvoiceByUser invoiceByUser) {
        // Required empty public constructor
        this.invoiceByUser=invoiceByUser;

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
        View view=inflater.inflate(R.layout.fragment_edit_invoice, container, false);
        OwnerMainActivity.txtOwnerTitle.setText("Edit Invoice");

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.....................");
        }

        initView(view);
        getInvoiceCategoryApi();
        getMareowChargeApi();
        return view;

    }



    private void initView(View view) {

        gstFrame=(FrameLayout)view.findViewById(R.id.edit_invoice_invoice_gstframe);
        igstFrame=(FrameLayout)view.findViewById(R.id.edit_invoice_invoice_igstframe);

        editRemarks=(TextInputEditText)view.findViewById(R.id.edit_invoice_Remarks);

        editRemainingAmount=(TextInputEditText)view.findViewById(R.id.edit_invoice_Remaining_Estimated_Amount);
        editRemainingAmount.setInputType(InputType.TYPE_NULL);

        editWorkOrderNo=(TextInputEditText)view.findViewById(R.id.edit_invoice_work_order_no);
        editWorkOrderNo.setText(invoiceByUser.getWorkOrderNo());
        editWorkOrderNo.setInputType(InputType.TYPE_NULL);



        editInvoiceNo=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_no);
        editInvoiceNo.setText(invoiceByUser.getInvoiceRefNo());



        editInvoiceDate=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_date);
        editInvoiceDate.setOnClickListener(this);
        editInvoiceDate.setInputType(InputType.TYPE_NULL);
        editInvoiceDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDateDialog();
                }
            }
        });


        //editInvoiceNo=(TextInputEditText)view.findViewById(R.id.add_payment_invoice_no);
        editInvoiceAmount=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_amount);
        editInvoiceAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SELECTED_INVOICE_AMOUNT=s.toString();
                calulateMaeowCharges();
            }
        });

        editInvoiceCategory=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_category);
        editInvoiceCategory.setInputType(InputType.TYPE_NULL);
        editInvoiceCategory.setOnClickListener(this);
        editInvoiceCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    INVOICE_CATEGORY_CHECK=true;
                    callCustomeDialog(invoiceCategoryMap, 1,"invoice_category");
                }
            }
        });

        checkBoxInvoceGST=(AppCompatCheckBox) view.findViewById(R.id.edit_invoice_invoice_gstcheck);
        checkBoxInvoceGST.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gstFrame.setVisibility(View.VISIBLE);
                }else {
                    gstFrame.setVisibility(View.GONE);
                }
            }
        });

        checkBoxInvoceIGST=(AppCompatCheckBox) view.findViewById(R.id.edit_invoice_invoice_igstcheck);
        checkBoxInvoceIGST.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    igstFrame.setVisibility(View.VISIBLE);
                }else {
                    igstFrame.setVisibility(View.GONE);
                }
            }
        });


        editInvoiceNetAmount=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_net_amount);
        editInvoiceNetAmount.setInputType(InputType.TYPE_NULL);
        editmareowServiceCharge=(TextInputEditText)view.findViewById(R.id.edit_invoice_mareow_service_charge);
        editmareowServiceCharge.setInputType(InputType.TYPE_NULL);

        btnInvoiceDate=(AppCompatImageButton)view.findViewById(R.id.edit_invoice_invoice_date_image);
        btnInvoiceDate.setOnClickListener(this);

        btnChooseFile=(AppCompatImageView)view.findViewById(R.id.edit_invoice_choosefile);
        btnChooseFile.setOnClickListener(this);
        txtNoFileChoose=(AppCompatTextView)view.findViewById(R.id.edit_invoice_no_file_selected);

        btnSave=(FloatingActionButton)view.findViewById(R.id.edit_invoice_save);
        btnSave.setOnClickListener(this);

        editCGST=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_editCGST);
        editSGST=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_editSGST);
        editIGST=(TextInputEditText)view.findViewById(R.id.edit_invoice_invoice_editIGST);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_invoice_choosefile:
                showAlertDialog();
                break;
            case R.id.edit_invoice_invoice_date:
                showDateDialog();
                break;
            case R.id.edit_invoice_invoice_date_image:
                showDateDialog();
                break;
            case R.id.edit_invoice_save:
                callEditInvoiceApi();
                break;
            case R.id.edit_invoice_invoice_category:
                INVOICE_CATEGORY_CHECK=true;
                callCustomeDialog(invoiceCategoryMap, 1,"invoice_category");
                break;
        }
    }


    private void getSpecificInvoiceDetails() {
        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<InvoiceByInvoiceId> callOfferWorkOrder=apiInterface.getInvoiceByInvoiceId("Bearer "+token,invoiceByUser.getInvoiceId());
        callOfferWorkOrder.enqueue(new Callback<InvoiceByInvoiceId>() {
            @Override
            public void onResponse(Call<InvoiceByInvoiceId> call, Response<InvoiceByInvoiceId> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    invoiceDetails=response.body();
                    setViewValue();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");
                    }
                    if(response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }

                }
            }
            @Override
            public void onFailure(Call<InvoiceByInvoiceId> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void setViewValue() {

        editInvoiceDate.setText(invoiceDetails.getInvoiceDateStr());
        editInvoiceCategory.setText(invoiceDetails.getInvoiceNoMeaning());
        INVOICE_CATEGORY_CODE=invoiceDetails.getInvCategory();

        editInvoiceAmount.setText(String.valueOf(invoiceDetails.getInvoiceAmount()));
        SELECTED_INVOICE_AMOUNT=String.valueOf(invoiceDetails.getInvoiceAmount());
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        editRemainingAmount.setText("\u20B9 "+IndianCurrencyFormat.format(invoiceDetails.getWorkOrderEstimateAmount()));

        editRemarks.setText(invoiceDetails.getInvoiceDesc());
        checkBoxInvoceGST.setChecked(invoiceDetails.isGstFlg());
        checkBoxInvoceIGST.setChecked(invoiceDetails.isIgstFlg());

        mUploadingDocument=invoiceDetails.getInvoiceDocumentPath();
        txtNoFileChoose.setText(invoiceDetails.getInvoiceDocumentFileName());

        calulateMaeowCharges();


    }

    public void getInvoiceCategoryApi(){
        if (progressDialog!=null){
            progressDialog.show();
        }
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> callOfferWorkOrder=apiInterface.getCommonLookUp(Constants.INVOICE_CATEGORY);
        callOfferWorkOrder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){

                    String receipt = null;
                    try {
                        receipt = response.body().string();
                        JSONObject jsonObject = new JSONObject(receipt);
                        parseJSONObject(jsonObject, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void parseJSONObject(JSONObject jsonObject, int check) {
        HashMap<String, String> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setHashMap(commonMap, check);

    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 0:
                invoiceCategoryMap = map;
                break;
        }
    }

    public void getMareowChargeApi(){
        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<MareowCharges>> callOfferWorkOrder=apiInterface.getMareowCharges("Bearer "+token,Constants.MAREOW_CHARGES);
        callOfferWorkOrder.enqueue(new Callback<List<MareowCharges>>() {
            @Override
            public void onResponse(Call<List<MareowCharges>> call, Response<List<MareowCharges>> response) {
                if (response.isSuccessful()){
                    mareowChargesList=response.body();
                    getSpecificInvoiceDetails();
                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");
                    }
                    if(response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }

                }
            }
            @Override
            public void onFailure(Call<List<MareowCharges>> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    public void showSnackbar(String msg){

        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }


    private void callCustomeDialog(HashMap<String, String> hashMap, int check, String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();

        for (String key : hashMap.keySet()) {
            listData.add(key);
        }

        Collections.sort(listData);
        showPopWindowForView(tilte,listData);

    }

    private void showPopWindowForView(String tilte, ArrayList<String> listData) {

        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        if (INVOICE_CATEGORY_CHECK){

            popupWindow.setAnchorView(editInvoiceCategory);
            popupWindow.setWidth(editInvoiceCategory.getMeasuredWidth());
        }

        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (INVOICE_CATEGORY_CHECK){
                    editInvoiceCategory.setText(listData.get(position));
                    INVOICE_CATEGORY_CODE=invoiceCategoryMap.get(listData.get(position));
                }

                popupWindow.dismiss();

            }
        });

        popupWindow.show();
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
                startActivityForResult(pictureIntent, EDIT_INVOICE_DOC_CAMERA);
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
        mUploadingDocument = imageFilePath;
        //mProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
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

        startActivityForResult(Intent.createChooser(intent,"ChooseFile"),EDIT_INVOICE_DOC);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

            if (requestCode==EDIT_INVOICE_DOC){
                Uri uri = data.getData();

                mUploadingDocument= FileUtils.getPath(getActivity(),uri);
                txtNoFileChoose.setText(FileUtils.getFileName(getActivity(),Uri.parse(mUploadingDocument)));

            }

            if (requestCode==EDIT_INVOICE_DOC_CAMERA){
                // Uri uri = data.getData();
                // mSupportingDocument= FileUtils.getPath(getActivity(),uri);
                txtNoFileChoose.setText(FileUtils.getFileName(getActivity(),Uri.parse(mUploadingDocument)));

            }

        }
    }

    public void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
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

                editInvoiceDate.setText(dateString);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime .show();
    }


    private void callEditInvoiceApi() {

        String token=TokenManager.getSessionToken();

        if (editWorkOrderNo.getText().toString().isEmpty()){
            showSnackbar("Please select workorder No.");
            return;
        }
        if (editInvoiceNo.getText().toString().isEmpty()){
            showSnackbar("Please select invoice No.");
            return;
        }
        if (editInvoiceCategory.getText().toString().isEmpty()){
            showSnackbar("Please select invoice category.");
            return;
        }
        if (editInvoiceAmount.getText().toString().isEmpty()){
            showSnackbar("Please select invoice amount");
            return;
        }

        if (editInvoiceNetAmount.getText().toString().isEmpty()){
            showSnackbar("Please select invoice net amount");
            return;
        }

        if (editInvoiceDate.getText().toString().isEmpty()){
            showSnackbar("Please select invoice date.");
            return;
        }

        if (mUploadingDocument==null){
            showSnackbar("Please select supporting document file.");
            return;
        }

        if (Float.parseFloat(editInvoiceAmount.getText().toString().trim())>invoiceDetails.getWorkOrderEstimateAmount()){
            showSnackbar("Invoice amount is greater than invoice net amount");
            return;
        }




        MultipartBody.Part uploadPart=null;
        if (mUploadingDocument.startsWith("uploads/")){

        }else {

            File supportfile= new File(mUploadingDocument);
            RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), supportfile);
            uploadPart = MultipartBody.Part.createFormData("INVC", supportfile.getName(), supportImage);

        }


        JSONObject requestBody = getRequestBody();
        RequestBody draBody = null;

        try {
            draBody = RequestBody.create(MediaType.parse("text/plain"), requestBody.toString(1));
            //Log.d(TAG, "requestUploadSurvey: RequestBody : " + requestBody.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.createInvoice("Bearer "+token,uploadPart,draBody);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });


    }


    private JSONObject getRequestBody() {

        JSONObject jsonObject = new JSONObject();
        try {

            String invoiceStatus=null;
            int parttId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
            jsonObject.put("invoiceId",invoiceDetails.getInvoiceId());
            jsonObject.put("invoiceLineDId",invoiceDetails.getInvoiceLineDId());
            jsonObject.put("invoiceNo",invoiceDetails.getInvoiceNo());
            jsonObject.put("invoiceRefNo",editInvoiceNo.getText().toString());
            jsonObject.put("invoiceAmount",SELECTED_INVOICE_AMOUNT);
            jsonObject.put("workOrderNo",invoiceDetails.getWorkOrderNo());
            jsonObject.put("workOrderId",invoiceDetails.getWorkOrderId());
            jsonObject.put("invoice_desc",editRemarks.getText().toString());
            jsonObject.put("lessorId",parttId);
            jsonObject.put("lesseeId",invoiceDetails.getLesseeId());
            jsonObject.put("invoiceStatus",invoiceStatus);
            jsonObject.put("invoiceDocumentPath",mUploadingDocument);
            jsonObject.put("gstFlg",checkBoxInvoceGST.isChecked());
            jsonObject.put("igstFlg",checkBoxInvoceIGST.isChecked());
            jsonObject.put("sgstPer",String.valueOf(invoiceDetails.getSgstPer()));
            jsonObject.put("cgstPer",String.valueOf(invoiceDetails.getCgstPer()));
            jsonObject.put("igstPer",String.valueOf(invoiceDetails.getIgstPer()));
            jsonObject.put("cgst",String.valueOf(CGST_AMOUNT));
            jsonObject.put("sgst",String.valueOf(SGST_AMOUNT));
            jsonObject.put("igst",String.valueOf(IGST_AMOUNT));


           /* if (checkBoxInvoceGST.isChecked()){
                jsonObject.put("cgst",String.valueOf(CGST_AMOUNT));
                jsonObject.put("sgst",String.valueOf(SGST_AMOUNT));
            }

            if (checkBoxInvoceIGST.isChecked()){
                jsonObject.put("igst",String.valueOf(IGST_AMOUNT));
            }*/


            jsonObject.put("invoiceNetAmount",SELECTED_INVOICE_AMOUNT);
            jsonObject.put("invoiceDate",convertddmmyyToYYYYMMDD(editInvoiceDate.getText().toString()));
            jsonObject.put("workOrderEstimateAmount",String.valueOf(invoiceDetails.getWorkOrderEstimateAmount()));
            jsonObject.put("invCategory",INVOICE_CATEGORY_CODE);
            jsonObject.put("serviceAmountOwn",String.valueOf(mareowServiceChargesForOwner));
            jsonObject.put("serviceAmountRen",String.valueOf(mareowServiceChargesForRenter));
            jsonObject.put("editInvoice",true);

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String convertddmmyyToYYYYMMDD(String stringDate){

        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputdate = input.parse(stringDate);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void calulateMaeowCharges(){

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        if (!SELECTED_INVOICE_AMOUNT.equals("")) {


            List<MareowCharges> mareowChargesForRenter = new ArrayList<>();
            for (int i = 0; i < mareowChargesList.size(); i++) {
                if (mareowChargesList.get(i).getMeaning().equals("mareow - Service Charge (Renter)")) {
                    mareowChargesForRenter.add(mareowChargesList.get(i));
                }
            }

            List<MareowCharges> mareowChargesListForOwner = new ArrayList<>();
            for (int i = 0; i < mareowChargesList.size(); i++) {
                if (mareowChargesList.get(i).getMeaning().equals("mareow - Service Charge (Owner)")) {
                    mareowChargesListForOwner.add(mareowChargesList.get(i));
                }
            }


            for (int i = 0; i < mareowChargesForRenter.size(); i++) {
                if (mareowChargesForRenter.get(i).getAttribute2() != null) {
                    if (Float.parseFloat(SELECTED_INVOICE_AMOUNT) >= Float.parseFloat(mareowChargesForRenter.get(i).getAttribute1()) && Float.parseFloat(SELECTED_INVOICE_AMOUNT) <= Float.parseFloat(mareowChargesForRenter.get(i).getAttribute2())) {
                        mareowServiceChargesForRenter = (Float.parseFloat(SELECTED_INVOICE_AMOUNT) * Float.parseFloat(mareowChargesForRenter.get(i).getAttribute3())) / 100;
                    }
                } else {
                    if (Float.parseFloat(SELECTED_INVOICE_AMOUNT) >= Float.parseFloat(mareowChargesForRenter.get(i).getAttribute1()))
                        mareowServiceChargesForRenter = (Float.parseFloat(SELECTED_INVOICE_AMOUNT) * Float.parseFloat(mareowChargesForRenter.get(i).getAttribute3())) / 100;
                }
            }


            mareowServiceChargesForOwner = (mareowServiceChargesForRenter * Float.parseFloat(mareowChargesListForOwner.get(0).getAttribute3())) / 100;

            editInvoiceNetAmount.setText("\u20B9 " + IndianCurrencyFormat.format(Float.parseFloat(SELECTED_INVOICE_AMOUNT)));

            editmareowServiceCharge.setText("\u20B9 " + IndianCurrencyFormat.format(mareowServiceChargesForOwner));

            
        }

    }


}
