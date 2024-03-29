package com.mareow.recaptchademo.RenterFragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.MainActivityFragments.PaymentFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


public class AddPaymentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editWorkNo;
    TextInputEditText editRecieptNo;
    TextInputEditText editRecieptDate;
    TextInputEditText editInvoiceNo;
    TextInputEditText editInvoiceAmount;
    TextInputEditText editModeOfPayment;
    TextInputEditText editBankName;
    TextInputEditText editCheuque_DD_No;
    TextInputEditText editBankTransactionNo;
    TextInputEditText editPaymentAmount;


    AppCompatImageView btnRecieptDate;
    AppCompatTextView txtNoFileChoose;
    AppCompatImageView btnChooseFile;

    FloatingActionButton btnSave;
    public String RECEIPT_NO=null;

    public final int ADD_PAYMENT_DOC=502;
    public final int ADD_PAYMENT_DOC_CAMERA=503;

    public String mSupportingDocument=null;
    ProgressDialog progressDialog;

    List<InvoiceByUser> invoiceByUserList;
    OfferWorkOrder workOrderDetails;
    ArrayList<String> invoiceNoList=new ArrayList<>();


    HashMap<String,String>  paymentMap = new HashMap<>();
    String PAYMENT_TYPE_CODE;

    boolean MODE_OF_PAYMENT=false;
    String stringMonth;
    String stringYear;

    String selectedInvoiceNo=null;
    int selectedInvoiceID;
    String seletedInvoiceAmount=null;
    String seletedPaymentAmount=null;
    String seletedInvoiceDate=null;
    String seletedInvoicePendingAmount=null;
    int selectedInvoiceOwnerId;
    int selectedWorkOrderId;
    String selectedWorkOrderNo;
    String selectedInvoiceNetAmount=null;
    int selectedInvoiceLineId;
    DecimalFormat IndianCurrencyFormat;

    TextInputLayout mBankHint;

    HashMap<String,InvoiceByUser> invoiceByWorkOrderId = new HashMap<>();
    String imageFilePath;
    Uri photoURI;
    public AddPaymentFragment(OfferWorkOrder workOrderDetails, List<InvoiceByUser> invoiceByUserList) {
        // Required empty public constructor
        this.workOrderDetails=workOrderDetails;
        this.invoiceByUserList=invoiceByUserList;
        invoiceNoList.clear();
        for (int i=0;i<invoiceByUserList.size();i++){
            invoiceNoList.add(invoiceByUserList.get(i).getInvoiceNo());
        }

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
        View view=inflater.inflate(R.layout.fragment_add_payment, container, false);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Add Payment");
        }else if (Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Add Payment");
        }else {
            MainActivity.txtTitle.setText("Add Payment");
        }

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.....................");
        }
        getReceiptNoApi();
        getPaymentTypeApi();
        getInvoicebyWorkOrderId();
        initView(view);
        return view;
    }



    private void initView(View view) {
        mBankHint=(TextInputLayout)view.findViewById(R.id.add_payment_bank_name_hint);
        IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        editWorkNo=(TextInputEditText)view.findViewById(R.id.add_payment_work_no);
        editWorkNo.setText(workOrderDetails.getWorkorderDTO().getWorkOrderNo());
        editWorkNo.setInputType(InputType.TYPE_NULL);


        editRecieptNo=(TextInputEditText)view.findViewById(R.id.add_payment_reciept_no);
        editRecieptNo.setInputType(InputType.TYPE_NULL);


        editRecieptDate=(TextInputEditText)view.findViewById(R.id.add_payment_reciept_date);
        editRecieptDate.setOnClickListener(this);
        editRecieptDate.setInputType(InputType.TYPE_NULL);
        editRecieptDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDateDialog();
                }
            }
        });


        editInvoiceNo=(TextInputEditText)view.findViewById(R.id.add_payment_invoice_no);
        editInvoiceNo.setInputType(InputType.TYPE_NULL);
        editInvoiceNo.setOnClickListener(this);
        editInvoiceNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    MODE_OF_PAYMENT=false;
                    showPopWindowForView("InvoiceNo",invoiceNoList);
                }
            }
        });

        editInvoiceAmount=(TextInputEditText)view.findViewById(R.id.add_payment_invoice_amount);
        editInvoiceAmount.setInputType(InputType.TYPE_NULL);

        editModeOfPayment=(TextInputEditText)view.findViewById(R.id.add_payment_mode_of_payment);
        editModeOfPayment.setInputType(InputType.TYPE_NULL);
        editModeOfPayment.setOnClickListener(this);
        editModeOfPayment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideKeyboardFrom(getContext(),v);
                    MODE_OF_PAYMENT=true;
                    callCustomeDialog(paymentMap, 1,"payment_type");
                }
            }
        });

        editCheuque_DD_No=(TextInputEditText)view.findViewById(R.id.add_payment_cheque_dd_no);
        editBankName=(TextInputEditText)view.findViewById(R.id.add_payment_bank_name);
        editBankTransactionNo=(TextInputEditText)view.findViewById(R.id.add_payment_bank_transac_no);

        editPaymentAmount=(TextInputEditText)view.findViewById(R.id.add_payment_payment_amount);
        editPaymentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                seletedPaymentAmount=s.toString();
            }
        });

        btnRecieptDate=(AppCompatImageView) view.findViewById(R.id.add_payment_reciept_date_image);
        btnRecieptDate.setOnClickListener(this);

        btnChooseFile=(AppCompatImageView)view.findViewById(R.id.add_payment_choosefile);
        btnChooseFile.setOnClickListener(this);
        txtNoFileChoose=(AppCompatTextView)view.findViewById(R.id.add_payment_no_file_selected);

        btnSave=(FloatingActionButton)view.findViewById(R.id.add_payment_save);
        btnSave.setOnClickListener(this);


        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        stringYear=String.valueOf(year);
        int month=calendar.get(Calendar.MONTH)+1;
        if (month<10){
          stringMonth="0"+String.valueOf(month);
        }else {
            stringMonth=String.valueOf(month);
        }

        Date today=calendar.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String todayString=simpleDateFormat.format(today);
        editRecieptDate.setText(todayString);
        if (invoiceNoList.size()>0){

        for (int i=0;i<invoiceByUserList.size();i++){
            if (invoiceNoList.get(0).equals(invoiceByUserList.get(i).getInvoiceNo())){
                editInvoiceNo.setText(invoiceNoList.get(0));

                editInvoiceAmount.setText("\u20B9 "+IndianCurrencyFormat.format(invoiceByUserList.get(i).getInvoicePendingAmount()));

                seletedPaymentAmount=String.valueOf(invoiceByUserList.get(i).getInvoicePendingAmount());

                selectedInvoiceNo=invoiceByUserList.get(i).getInvoiceNo();
                selectedInvoiceID=invoiceByUserList.get(i).getInvoiceId();
                seletedInvoicePendingAmount=String.valueOf(invoiceByUserList.get(i).getInvoicePendingAmount());
                seletedInvoiceAmount=String.valueOf(invoiceByUserList.get(i).getInvoiceAmount());
                seletedInvoiceDate=invoiceByUserList.get(i).getInvoiceDate();
                selectedInvoiceOwnerId=invoiceByUserList.get(i).getOwnerId();
                selectedInvoiceNetAmount=String.valueOf(invoiceByUserList.get(i).getInvoiceNetAmount());
                selectedWorkOrderId=invoiceByUserList.get(i).getWorkOrderId();
                selectedWorkOrderNo=invoiceByUserList.get(i).getWorkOrderNo();

                editPaymentAmount.setText(seletedPaymentAmount);
                break;
            }
        }

    }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add_payment_choosefile:
                showAlertDialog();
                break;
            case R.id.add_payment_reciept_date:
               showDateDialog();
                break;
            case R.id.add_payment_reciept_date_image:
                showDateDialog();
                break;
            case R.id.add_payment_mode_of_payment:
                hideKeyboardFrom(getContext(),v);
                MODE_OF_PAYMENT=true;
                callCustomeDialog(paymentMap, 1,"payment_type");
                break;
            case R.id.add_payment_save:
                callPaymentSaveApi();
                break;
            case R.id.add_payment_invoice_no:
                MODE_OF_PAYMENT=false;
                showPopWindowForView("InvoiceNo",invoiceNoList);
                break;
        }
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


        if (MODE_OF_PAYMENT){
            popupWindow.setAnchorView(editModeOfPayment);
            popupWindow.setWidth(editModeOfPayment.getMeasuredWidth());
        }else {
            popupWindow.setAnchorView(editInvoiceNo);
            popupWindow.setWidth(editInvoiceNo.getMeasuredWidth());
        }


        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (MODE_OF_PAYMENT){
                    editModeOfPayment.setText(listData.get(position));
                    PAYMENT_TYPE_CODE=paymentMap.get(listData.get(position));
                    if (PAYMENT_TYPE_CODE.equals("PAYTM")){
                        editCheuque_DD_No.setText("");
                        editCheuque_DD_No.setEnabled(false);
                        //mBankHint.setHint("Bank Name");
                        editBankName.setText("");
                        editBankName.setEnabled(false);
                    }else {
                        editCheuque_DD_No.setEnabled(true);
                        editBankName.setEnabled(true);
                    }
                }else {
                    editInvoiceNo.setText(listData.get(position));
                    //getInvoiceDetails(listData.get(position));
                    for (int i=0;i<invoiceByUserList.size();i++){
                        if (listData.get(position).equals(invoiceByUserList.get(i).getInvoiceNo())){
                            editInvoiceAmount.setText("\u20B9 "+IndianCurrencyFormat.format(invoiceByUserList.get(i).getInvoicePendingAmount()));

                            seletedPaymentAmount=String.valueOf(invoiceByUserList.get(i).getInvoicePendingAmount());
                            selectedInvoiceNo=invoiceByUserList.get(i).getInvoiceNo();
                            selectedInvoiceID=invoiceByUserList.get(i).getInvoiceId();

                            seletedInvoiceAmount=String.valueOf(invoiceByUserList.get(i).getInvoiceAmount());
                            seletedInvoicePendingAmount=String.valueOf(invoiceByUserList.get(i).getInvoicePendingAmount());

                            seletedInvoiceDate=invoiceByUserList.get(i).getInvoiceDate();
                            selectedInvoiceOwnerId=invoiceByUserList.get(i).getOwnerId();

                            selectedInvoiceNetAmount=String.valueOf(invoiceByUserList.get(i).getInvoiceNetAmount());
                            selectedWorkOrderId=invoiceByUserList.get(i).getWorkOrderId();
                            selectedWorkOrderNo=invoiceByUserList.get(i).getWorkOrderNo();

                            editPaymentAmount.setText(seletedPaymentAmount);
                            break;
                        }
                    }

                }

                popupWindow.dismiss();
            }
        });

        popupWindow.show();
    }

    private void getInvoiceDetails(String invoiceNo){


    }

    private void callPaymentSaveApi() {

        String token=TokenManager.getSessionToken();
        if (editInvoiceNo.getText().toString().isEmpty()){
            showSnackBar("Please select invoice No.");
            return;
        }
        if (editModeOfPayment.getText().toString().isEmpty()){
            showSnackBar("Please select mode of payment.");
            return;
        }
        if (editRecieptDate.getText().toString().isEmpty()){
            showSnackBar("Please select receipt date");
            return;
        }

        if (mSupportingDocument==null){
           showSnackBar("Please select supporting document file.");
           return;
        }

        if (editPaymentAmount.getText().toString().isEmpty()){
            showSnackBar("Please enter payment amount.");
            return;
        }else if (Float.parseFloat(seletedPaymentAmount)>Float.parseFloat(seletedInvoiceAmount)){
            showSnackBar("Payment amount is greater than invoice amount.");
            return;
        }


        if (PAYMENT_TYPE_CODE.equals("CCARD") || PAYMENT_TYPE_CODE.equals("NETB")){

            if (editBankName.getText().toString().isEmpty()){
                showSnackBar("Please select bank name");
                return;
            }

        }

        if (PAYMENT_TYPE_CODE.equals("DRAFT") || PAYMENT_TYPE_CODE.equals("CHEQUE")){

            if (editBankName.getText().toString().isEmpty()){
                showSnackBar("Please select bank name");
                return;
            }

            if (editCheuque_DD_No.getText().toString().isEmpty()){
                showSnackBar("Please select Cheque / DD #");
                return;
            }

        }



        MultipartBody.Part supportPart=null;
        File supportfile= new File(mSupportingDocument);
        RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), supportfile);
        supportPart = MultipartBody.Part.createFormData("PAYDOC", supportfile.getName(), supportImage);

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
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.createPayment("Bearer "+token,supportPart,draBody);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    showConfirmDialog(response.body().getMessage());
                    callMainPaymentFragment();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackBar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    }
                    if (response.code()==403){
                        //TokenExpiredUtils.tokenExpired(getActivity());
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackBar(mError.getMessage());
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
                showSnackBar(t.getMessage());
            }
        });
    }

    private void callMainPaymentFragment() {

        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Fragment fragment = new PaymentFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    public void showDateDialog(){

        SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        Date todays=new Date();
        String startDateString= seletedInvoiceDate;
        String endDateString=dateFormat1.format(todays);
        Date startDate=null;
        Date endDate=null;
        try {
            startDate=dateFormat1.parse(startDateString);
            endDate=dateFormat1.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Calendar setCalendar = Calendar.getInstance();

        setCalendar.setTime(startDate);
        long minDate=setCalendar.getTime().getTime();

        setCalendar.setTime(endDate);
        long maxDate=setCalendar.getTime().getTime();

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

                editRecieptDate.setText(dateString);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(minDate);
        StartTime.getDatePicker().setMaxDate(maxDate);

        StartTime .show();
    }

    public String convertDate(String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

            if (requestCode==ADD_PAYMENT_DOC){
                Uri uri = data.getData();
                mSupportingDocument= FileUtils.getPath(getActivity(),uri);
                txtNoFileChoose.setText(FileUtils.getFileName(getActivity(),Uri.parse(mSupportingDocument)));

            }
            if (requestCode==ADD_PAYMENT_DOC_CAMERA){
               // Uri uri = data.getData();
               // mSupportingDocument= FileUtils.getPath(getActivity(),uri);
                txtNoFileChoose.setText(FileUtils.getFileName(getActivity(),Uri.parse(mSupportingDocument)));

            }

        }
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
                startActivityForResult(pictureIntent, ADD_PAYMENT_DOC_CAMERA);
            }
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
        mSupportingDocument = imageFilePath;
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

        startActivityForResult(Intent.createChooser(intent,"ChooseFile"),ADD_PAYMENT_DOC);

    }


      public void getReceiptNoApi(){
          progressDialog.show();
          ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
          Call<ResponseBody> callOfferWorkOrder=apiInterface.getCommonLookUp(Constants.RECIEPT_NO);
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
                          showSnackBar("Record Not Found");
                      }

                  }
              }
              @Override
              public void onFailure(Call<ResponseBody> call, Throwable t) {
                  progressDialog.dismiss();
                  showSnackBar(t.getMessage());
              }
          });
      }


    public void getPaymentTypeApi(){
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> callpayment=apiInterface.getCommonLookUp(Constants.PAYMENT_TYPE);
        callpayment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){

                    String receipt = null;
                    try {
                        receipt = response.body().string();
                        JSONObject jsonObject = new JSONObject(receipt);
                        parseJSONObject(jsonObject, 1);
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
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showSnackBar(t.getMessage());
            }
        });
    }


    private void showSnackBar(String message) {
        Snackbar snackbar= Snackbar.make(getView(),message,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void parseJSONObject(JSONObject jsonObject, int check) {
        HashMap<String, String> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            if (check==0){
                RECEIPT_NO=key;
                editRecieptNo.setText("PAY-"+stringYear+"-"+stringMonth+"-0000"+RECEIPT_NO);
            }

            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (check==1){
            setHashMap(commonMap, check);
        }

    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 1:
                paymentMap = map;
                break;
        }

    }


    private JSONObject getRequestBody() {

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("invoiceId",selectedInvoiceID);
            jsonObject.put("invoiceNo",selectedInvoiceNo);
            jsonObject.put("workOrderNo",selectedWorkOrderNo);
            jsonObject.put("workOrderId",selectedWorkOrderId);
            jsonObject.put("paymentAmount",seletedPaymentAmount);
            jsonObject.put("invoiceAmount",seletedInvoiceAmount);
            jsonObject.put("invoiceNetAmount",selectedInvoiceNetAmount);
            jsonObject.put("invoicePendingAmount",seletedInvoicePendingAmount);
            jsonObject.put("paymentType",PAYMENT_TYPE_CODE);
            jsonObject.put("receiptNo",editRecieptNo.getText().toString());
            jsonObject.put("cheque_DD",editCheuque_DD_No.getText().toString());
            jsonObject.put("paymentDate", convertddmmyyToYYYYMMDD(editRecieptDate.getText().toString()));
            jsonObject.put("transactionNo",editBankTransactionNo.getText().toString());
            jsonObject.put("bankName",editBankName.getText().toString());
            jsonObject.put("paymentDocumentPath",mSupportingDocument);
            jsonObject.put("lessorId",selectedWorkOrderId);
            jsonObject.put("lesseeId",partyId);
            jsonObject.put("receiptId",0);
            jsonObject.put("number","null");

            InvoiceByUser invoiceByUser=invoiceByWorkOrderId.get(String.valueOf(selectedInvoiceID));
            selectedInvoiceLineId=invoiceByUser.getInvoiceLineId();
            jsonObject.put("invoiceLineId",selectedInvoiceLineId);
            jsonObject.put("editPayment",false);


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

    private void showConfirmDialog(String msg) {
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void getInvoicebyWorkOrderId() {
        progressDialog.show();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> callOfferWorkOrder=apiInterface.getInvoiceByWorkorderId(workOrderDetails.getWorkorderDTO().getWorkOrderId(),partyId);
        callOfferWorkOrder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){

                    String invoiceResponse = null;
                    try {
                        invoiceResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(invoiceResponse);
                        parseInvoiceJSONObject(jsonObject, 0);
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
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showSnackBar(t.getMessage());
            }
        });
    }

    private void parseInvoiceJSONObject(JSONObject jsonObject, int i) {

        HashMap<String,InvoiceByUser> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
            String key = iter.next();
            try {

                JSONObject inObject=jsonObject.getJSONObject(key);
                InvoiceByUser invoiceByUser=new InvoiceByUser();
                invoiceByUser.setInvoiceLineId(inObject.getInt("invoiceLineId"));
                invoiceByUser.setInvoiceDate(inObject.getString("invoiceDateStr"));
                commonMap.put(key,invoiceByUser );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        invoiceByWorkOrderId=commonMap;

    }


    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
