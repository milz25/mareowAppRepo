package com.mareow.recaptchademo.RenterMachineBookFragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.BookMachine;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.FragmentUserDetails.RenterHomeFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.MachineBookingDetails;
import com.mareow.recaptchademo.RenterFragments.RenterMachineDetailsFragment;
import com.mareow.recaptchademo.RenterFragments.RenterMainHomeFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingSummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editTextSeletedMachine;
    public static TextInputEditText editTextBookingStartDates;
    public static TextInputEditText editTextBookingEndDates;
    TextInputEditText editTextBookingDays;

    public static TextInputEditText editTextBookingReason;

    public static TextInputEditText editTextRentalPlan;
    AppCompatCheckBox editTextOperator;
    public static TextInputEditText editTextExtraAttachments;

    AppCompatTextView txtEstimated;
    AppCompatTextView txtEstimatedPrice;
    public static TextInputEditText txtNumberOfdays;
    public static TextInputEditText txtOpratingHours;
    AppCompatTextView txtGsttext;

    LinearLayout mBookNow;

    RenterSpecificMachine renterSpecificMachine;
    ProgressDialog progressDialog;
    String brformachine=null;
    public BookingSummaryFragment(RenterSpecificMachine renterSpecificMachine) {
        // Required empty public constructor
        this.renterSpecificMachine=renterSpecificMachine;
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
        View view=inflater.inflate(R.layout.fragment_booking_summary, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.......");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {

        editTextSeletedMachine=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_machinename);
        editTextSeletedMachine.setText(renterSpecificMachine.getMachineName());
        editTextSeletedMachine.setInputType(InputType.TYPE_NULL);

        editTextBookingStartDates=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_bookingstartdate);
      //  editTextBookingStartDates.setText(Constants.MACHINE_BOOK_START_DATE);
        editTextBookingStartDates.setInputType(InputType.TYPE_NULL);

        editTextBookingEndDates=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_bookingenddate);
        //editTextBookingEndDates.setText(Constants.MACHINE_BOOK_END_DATE);
        editTextBookingEndDates.setInputType(InputType.TYPE_NULL);


        editTextBookingReason=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_booking_reason);
       // editTextBookingReason.setText(BookDateAndReasonFragment.booking_Reason_text);
        editTextBookingReason.setInputType(InputType.TYPE_NULL);

        editTextRentalPlan=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_rental_plan);
      //  editTextRentalPlan.setText(BookDateAndReasonFragment.booking_planName);
        editTextRentalPlan.setInputType(InputType.TYPE_NULL);

        //txtGsttext=(AppCompatTextView)view.findViewById(R.id.RBD_book_summary_estimated_gst);


        editTextOperator=(AppCompatCheckBox) view.findViewById(R.id.RBD_book_summary_operator);
        editTextOperator.setKeyListener(null);
        if (BookDateAndReasonFragment.selectedPlan.size()>0){
            if (BookDateAndReasonFragment.selectedPlan.get(0).isOperatorFlg()){
                editTextOperator.setChecked(true);
            }else {
                editTextOperator.setChecked(false);
            }
        }



        editTextExtraAttachments=(TextInputEditText)view.findViewById(R.id.RBD_book_summary_attachments);
        editTextExtraAttachments.setInputType(InputType.TYPE_NULL);



        txtNumberOfdays=(TextInputEditText) view.findViewById(R.id.RBD_book_summary_noofdays);
        txtNumberOfdays.setText(String.valueOf(getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE)));
        txtNumberOfdays.setInputType(InputType.TYPE_NULL);

        txtOpratingHours=(TextInputEditText) view.findViewById(R.id.RBD_book_summary_oprating_hours);
       // txtOpratingHours.setText(String.valueOf(getHoursBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE)));
       // txtOpratingHours.setInputType(InputType.TYPE_NULL);




        //txtEstimated=(AppCompatTextView)view.findViewById(R.id.RBD_book_summary_estimated_text);

      /*  DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        String GstandIGst="";
        if (BookDateAndReasonFragment.selectedPlan.size()>0){
            if (BookDateAndReasonFragment.selectedPlan.get(0).isGst()){
                GstandIGst="( * GST / IGST Amount Included )";
            }else {
                GstandIGst="( * GST / IGST Amount Excluded )";

            }
        }
*/
       // txtEstimatedPrice=(AppCompatTextView)view.findViewById(R.id.RBD_book_summary_estimated_value);
     /*   String price="0";
        if (BookDateAndReasonFragment.selectedPlan.size()>0){
            price="Book Now  "+"( \u20B9 "+IndianCurrencyFormat.format(BookDateAndReasonFragment.selectedPlan.get(0).getTotalAmmount())+" )";
        }else {
            price="Book Now  "+"( \u20B9 "+"0"+" )";
        }*/

       // txtEstimatedPrice.setText(price);
       // txtEstimated.setText("Estimate Amount");
       // txtGsttext.setText(GstandIGst);

        MachineBookingDetails.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });


    }



    public  long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(endDate);
            c1.add(Calendar.DATE, 1);

            endDate=c1.getTime();

            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }


    private  long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        if (startDate.equals(endDate)){
            return 1;
        }
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    private long getHoursBetweenDates(String startDateString, String endDateString){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate=null, endDate=null;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(startDateString);
            endDate = dateFormat.parse(endDateString);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(endDate);
            c1.add(Calendar.DATE, 1);

            endDate=c1.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        int hours = (int)secs / 3600;
        secs = secs % 3600;
        int mins = (int)secs / 60;
        secs = secs % 60;
        return hours;
    }

    private SpannableString spannableString(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
       // ColorStateList redColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{0xffa10901});
       // TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  spannableString.setSpan(new BackgroundColorSpan(0xFFFCFF48), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.5f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void callBookMachineApi() {

       /* if (BookDateAndReasonFragment.booking_NatureOfProject==null || TextUtils.isEmpty(BookDateAndReasonFragment.booking_NatureOfProject)){
            Toast.makeText(getActivity(), "Enter Nature of Project", Toast.LENGTH_SHORT).show();
            return;
        }else if (SiteLocationFragment.mAddress1==null && TextUtils.isEmpty(SiteLocationFragment.mAddress1)){
            Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
            return;
        }else if (SiteLocationFragment.mCity==null && TextUtils.isEmpty(SiteLocationFragment.mCity)){
            Toast.makeText(getActivity(), "Enter City", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (SiteLocationFragment.mPincode==null && TextUtils.isEmpty(SiteLocationFragment.mPincode)){
            Toast.makeText(getActivity(), "Enter Pincode", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (SiteLocationFragment.mState==null && TextUtils.isEmpty(SiteLocationFragment.mState)){
            Toast.makeText(getActivity(), "Enter state", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (SiteLocationFragment.mCountry==null && TextUtils.isEmpty(SiteLocationFragment.mCountry)){
            Toast.makeText(getActivity(), "Enter Country", Toast.LENGTH_SHORT).show();
            return;
        }*/
        /*else if (BookDateAndReasonFragment.mCommemcemetDate==null && TextUtils.isEmpty(BookDateAndReasonFragment.mCommemcemetDate)){
            Toast.makeText(getActivity(), "Enter Date of Commencement", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (BookDateAndReasonFragment.mExpectedDateDate==null && TextUtils.isEmpty(BookDateAndReasonFragment.mExpectedDateDate)){
            Toast.makeText(getActivity(), "Enter Expected Date", Toast.LENGTH_SHORT).show();
            return;
        }*/




        BookMachine bookMachine=new BookMachine();

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);

        bookMachine.setMachineId(renterSpecificMachine.getMachineId());
        bookMachine.setWorkOrderStartDate(convertDDMMYYYYtoYYYYYMMDD(Constants.MACHINE_BOOK_START_DATE));
        bookMachine.setWorkOrderEndDate(convertDDMMYYYYtoYYYYYMMDD(Constants.MACHINE_BOOK_END_DATE));
        bookMachine.setDateRangeStr(Constants.MACHINE_BOOK_START_DATE+"-"+Constants.MACHINE_BOOK_END_DATE);
        bookMachine.setSiteLocation(SiteLocationFragment.mAddress1);
        bookMachine.setCurrentLocation(renterSpecificMachine.getCurrentLocation());
        bookMachine.setPlan(BookDateAndReasonFragment.selectedPlan.get(0).getPlanId());
        bookMachine.setAmmount(BookDateAndReasonFragment.totalAmountForWorkOrder);
        bookMachine.setRequireDay((int)getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE));
        if (editTextOperator.isChecked())
        bookMachine.setOperatorCost("Included");
        else {
            bookMachine.setOperatorCost("Excluded");
        }

        bookMachine.setExtraAttachment(editTextExtraAttachments.getText().toString());

        if (BookDateAndReasonFragment.selectedAttachment.size()==0){
            bookMachine.setExtraAttachmentStr("");
        }else {

            StringBuilder attaamentId=new StringBuilder();
            for (int i=0;i<BookDateAndReasonFragment.selectedAttachment.size();i++){
                    if (i==BookDateAndReasonFragment.selectedAttachment.size()-1){
                        attaamentId.append(BookDateAndReasonFragment.selectedAttachment.get(i).getAttachmentId());
                    }else {
                        attaamentId.append(BookDateAndReasonFragment.selectedAttachment.get(i).getAttachmentId()+",");
                    }

            }

            bookMachine.setExtraAttachmentStr(attaamentId.toString());

        }

        bookMachine.setCommencementDate(RenterMachineDetailsFragment.commencementDate);
        bookMachine.setExpectedDateDelivery(RenterMachineDetailsFragment.expectedDeliveryDate);
        bookMachine.setRemarksQcelNaciel(RenterMachineDetailsFragment.remarkQCEL);

        bookMachine.setCity(SiteLocationFragment.mCity);
        bookMachine.setCountry(SiteLocationFragment.mCountry);
        bookMachine.setPostalCode(SiteLocationFragment.mPincode);
        bookMachine.setState(SiteLocationFragment.mState);
        bookMachine.setDistrict(SiteLocationFragment.mAddress1+SiteLocationFragment.mAddress2+SiteLocationFragment.mAddress3+SiteLocationFragment.mAddress4);
        bookMachine.setReqHour(Float.parseFloat(txtOpratingHours.getText().toString()));
        bookMachine.setNatureOfProject(BookDateAndReasonFragment.booking_NatureOfProject);
        bookMachine.setMachineBook(BookDateAndReasonFragment.booking_Reason);
        bookMachine.setLessorId(renterSpecificMachine.getPartyId());
        bookMachine.setLesseeId(partyId);



            String token= TokenManager.getSessionToken();
            progressDialog.show();
            ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
            Call<StatuTitleMessageResponse> bookMachineCall=apiInterface.bookMachineForRenter("Bearer "+token,bookMachine);
            bookMachineCall.enqueue(new Callback<StatuTitleMessageResponse>() {
                @Override
                public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        showSnackbar(response.body().getMessage());
                        callHomeFragmentData();
                    }else {
                        if (response.code()==401){
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
                    progressDialog.dismiss();
                    showSnackbar(t.getMessage());
                }
            });

    }

    private void callHomeFragmentData() {

        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment fragment = new RenterMainHomeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void showConfirmDialog() {

        if (BookDateAndReasonFragment.booking_NatureOfProject==null || TextUtils.isEmpty(BookDateAndReasonFragment.booking_NatureOfProject)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(0);
            showSnackbar( "Enter Nature of Project");
            return;
        }else if (SiteLocationFragment.mAddress1==null || TextUtils.isEmpty(SiteLocationFragment.mAddress1)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(1);
            showSnackbar("Enter Location");
            return;
        }else if (SiteLocationFragment.mCity==null || TextUtils.isEmpty(SiteLocationFragment.mCity)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(1);
            showSnackbar( "Enter City");
            return;
        }
        else if (SiteLocationFragment.mPincode==null || TextUtils.isEmpty(SiteLocationFragment.mPincode)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(1);
            showSnackbar("Enter Pincode");
            return;
        }
        else if (SiteLocationFragment.mState==null || TextUtils.isEmpty(SiteLocationFragment.mState)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(1);
            showSnackbar( "Enter state");
            return;
        }
        else if (SiteLocationFragment.mCountry==null || TextUtils.isEmpty(SiteLocationFragment.mCountry)){
            MachineBookingDetails.mRentalBookingDetailsViewPager.setCurrentItem(1);
            showSnackbar( "Enter Country");
            return;
        }

        if (BookDateAndReasonFragment.selectedPlan.get(0).getPlanUsageCode().equals("HOURLY")){
            String oh=txtOpratingHours.getText().toString();
            if (Integer.parseInt(oh)>24 && !oh.isEmpty()){
                showSnackbar("Operating Hours should not be greater than 24 Hours");
                return;
            }
        }


        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);

        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.wo_offer);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Confirm Machine Booking?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Offer");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("Cancel");
       // buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               callBookMachineApi();
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public String convertDDMMYYYYtoYYYYYMMDD(String date){

        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date inputdate = input.parse(date);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showSnackbar(String msg){

        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }




}
