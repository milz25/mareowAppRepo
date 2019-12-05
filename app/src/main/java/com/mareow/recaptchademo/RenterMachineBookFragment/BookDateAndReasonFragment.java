package com.mareow.recaptchademo.RenterMachineBookFragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.MultipleSelctionAttachmentRenter;
import com.mareow.recaptchademo.DataModels.MachineAttachment;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.MachineBookingDetails;
import com.mareow.recaptchademo.RenterFragments.RentalPlanForRenterFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;


public class BookDateAndReasonFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editStartDate;
    TextInputEditText editEndDate;

    TextInputEditText editBookingReason;

    public static TextInputEditText editRentalPlan;
    TextInputEditText editNatureOfProject;
    TextInputEditText ediAttachments;
    TextInputEditText editDateOfCommencemet;
    TextInputEditText editExpectedDateAvailability;


    AppCompatImageView btnStartDate;
    AppCompatImageView btnEndDate;
    AppCompatImageView btnCommemcemtDate;
    AppCompatImageView btnExpectedDate;

    AppCompatImageView showPlanDetails;


    RenterSpecificMachine renterSpecificMachine;
    ProgressDialog progressDialog;

    List<String> attachmentName=new ArrayList<>();
    HashMap<String,String> attachmentMap=new HashMap<>();

    String[] listItems=null;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();
    boolean START_DATE=false;
    boolean END_DATE=false;
    boolean COOMMEM_DATE=false;
    boolean EXPECTED_DATE=false;


    public static String booking_start_date;
    public static String booking_end_date;
    public static String booking_planName;
    public static String booking_Reason;
    public static String booking_NatureOfProject;
    public static String mCommemcemetDate;
    public static String mExpectedDateDate;

    public static String booking_Reason_text=null;


    public static List<MachineAttachment> selectedAttachment=new ArrayList<>();

    List<RentalPlan> rentalPlansList=new ArrayList<>();
    ArrayList<String> rentalPlanName=new ArrayList<>();

    List<MachineAttachment> machineAttachmentsList=new ArrayList<>();
    public static List<RentalPlan> selectedPlan=new ArrayList<>();

    boolean REASON_RENTAL=false;

    HashMap<String,String>  bookingReasonMap = new HashMap<>();
    ArrayList<String> reasonList=new ArrayList<>();
    private int INTIAL_CHECk=0;
    ArrayList<String> attachmentItems=new ArrayList<>();

    public static float totalAmountForWorkOrder=0;
    public BookDateAndReasonFragment(RenterSpecificMachine specificMachine) {
        // Required empty public constructor
        renterSpecificMachine=specificMachine;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_book_date_and_reason, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.............");
        }

        initView(view);

        if (INTIAL_CHECk==0){

            callRentalPlanApi();
            callAttachmentApi();
            callBookingReason();

            INTIAL_CHECk++;
        }else {

            if (selectedPlan.size()>0){
                calculateEstimatedAmount(selectedPlan.get(0));
            }

        }

        return view;
    }

    private void initView(View view) {
        /*editDateOfCommencemet=(TextInputEditText)view.findViewById(R.id.RBD_book_commencemetDate);
        editDateOfCommencemet.setInputType(InputType.TYPE_NULL);
        editDateOfCommencemet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_DATE=false;
                    END_DATE=false;
                    COOMMEM_DATE=true;
                    EXPECTED_DATE=false;
                    showDateDialog();
                }else {
                    mCommemcemetDate=editDateOfCommencemet.getText().toString();
                }
            }
        });*/

      /*  editExpectedDateAvailability=(TextInputEditText)view.findViewById(R.id.RBD_book_expectedDate);
        editExpectedDateAvailability.setInputType(InputType.TYPE_NULL);
        editExpectedDateAvailability.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_DATE=false;
                    END_DATE=false;
                    COOMMEM_DATE=false;
                    EXPECTED_DATE=true;
                    showDateDialog();
                }else {
                    mExpectedDateDate=editExpectedDateAvailability.getText().toString();
                }
            }
        });*/

        editStartDate=(TextInputEditText)view.findViewById(R.id.RBD_book_startdate);
        editStartDate.setText(Constants.MACHINE_BOOK_START_DATE);
        editStartDate.setInputType(InputType.TYPE_NULL);
        editStartDate.setOnClickListener(this);
        editStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_DATE=true;
                    END_DATE=false;
                    COOMMEM_DATE=false;
                    EXPECTED_DATE=false;
                    showDateDialog();
                }else {
                    Constants.MACHINE_BOOK_START_DATE=editStartDate.getText().toString();
                }
            }
        });
        editEndDate=(TextInputEditText)view.findViewById(R.id.RBD_book_enddate);
        editEndDate.setText(Constants.MACHINE_BOOK_END_DATE);
        editEndDate.setInputType(InputType.TYPE_NULL);
        editEndDate.setOnClickListener(this);
        editEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_DATE=false;
                    END_DATE=true;
                    COOMMEM_DATE=false;
                    EXPECTED_DATE=false;
                    showDateDialog();
                }else {
                    Constants.MACHINE_BOOK_END_DATE=editEndDate.getText().toString();
                }
            }
        });


        editRentalPlan=(TextInputEditText)view.findViewById(R.id.RBD_book_rentalPlan);
        editRentalPlan.setInputType(InputType.TYPE_NULL);
        editRentalPlan.setOnClickListener(this);
        editRentalPlan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    REASON_RENTAL=false;
                    popWindowList("rental",rentalPlanName);
                }
            }
        });



        editBookingReason=(TextInputEditText)view.findViewById(R.id.RBD_book_reaons);
        editBookingReason.setInputType(InputType.TYPE_NULL);
        editBookingReason.setOnClickListener(this);
        editBookingReason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    REASON_RENTAL=true;
                    popWindowList("reason",reasonList);
                }
            }
        });

        editNatureOfProject=(TextInputEditText)view.findViewById(R.id.RBD_book_natureofproject);
        editNatureOfProject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                booking_NatureOfProject=s.toString();
            }
        });

        ediAttachments=(TextInputEditText)view.findViewById(R.id.RBD_book_attachment);
        ediAttachments.setInputType(InputType.TYPE_NULL);
        ediAttachments.setOnClickListener(this);
        ediAttachments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (machineAttachmentsList.size()>0){
                        showCustomMultipleSelectionDialog();
                    }else {
                        Toast.makeText(getContext(), "No attachments availble for this machine", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        showPlanDetails=(AppCompatImageView) view.findViewById(R.id.RBD_book_showAll_rentalPlan);
        showPlanDetails.setOnClickListener(this);
        /*SpannableString s1 = new SpannableString("(Show All)");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                callRentalPlanFrgment();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                 ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.Blue_Text));
            }
        };
        s1.setSpan(clickableSpan, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        showAllRentalPlan.setText(s1);
        showAllRentalPlan.setMovementMethod(LinkMovementMethod.getInstance());*/

        /*btnStartDate=(AppCompatImageView) view.findViewById(R.id.RBD_book_btnStartDate);
        btnStartDate.setOnClickListener(this);
        btnEndDate=(AppCompatImageView) view.findViewById(R.id.RBD_book_btnEndDate);
        btnEndDate.setOnClickListener(this);
        btnCommemcemtDate=(AppCompatImageView) view.findViewById(R.id.RBD_book_btncommencemetDate);
        btnCommemcemtDate.setOnClickListener(this);
        btnExpectedDate=(AppCompatImageView) view.findViewById(R.id.RBD_book_btnExpetedDate);
        btnExpectedDate.setOnClickListener(this);
*/


      //  setData();

        /*if (selectedAttachment.size()>0){
            checkedItems=new boolean[machineAttachmentsList.size()];
            for (int i=0;i<selectedAttachment.size();i++){
                for (int j=0;j<machineAttachmentsList.size();j++){
                     if (machineAttachmentsList.get(j).equals(selectedAttachment.get(i))){
                         if (!mUserItems.contains(j)){
                             checkedItems[j]=true;
                             mUserItems.add(j);
                         }
                     }
                }

            }
        }*/

    }

    private void callRentalPlanFrgment() {

        RentalPlanForRenterFragment rentalPlanForRenterFragment=new RentalPlanForRenterFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("machineDetails",renterSpecificMachine);
        rentalPlanForRenterFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, rentalPlanForRenterFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.RBD_book_rentalPlan:
                REASON_RENTAL=false;
                popWindowList("rental",rentalPlanName);
                break;
            case R.id.RBD_book_attachment:
                if (attachmentItems.size()>0){
                    showCustomMultipleSelectionDialog();
                }else {
                    Toast.makeText(getContext(), "No attachments availble for this machine", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.RBD_book_startdate:
                START_DATE=true;
                END_DATE=false;
                COOMMEM_DATE=false;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_enddate:
                START_DATE=false;
                END_DATE=true;
                COOMMEM_DATE=false;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_reaons:
                REASON_RENTAL=true;
                popWindowList("reason",reasonList);
                break;
            case R.id.RBD_book_showAll_rentalPlan:
                showPlanDetailsDialog(selectedPlan.get(0));
                break;
          /*  case R.id.RBD_book_expectedDate:
                START_DATE=false;
                END_DATE=false;
                COOMMEM_DATE=false;
                EXPECTED_DATE=true;
                showDateDialog();
                break;
            case R.id.RBD_book_commencemetDate:
                START_DATE=false;
                END_DATE=false;
                COOMMEM_DATE=true;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_btnStartDate:
                START_DATE=true;
                END_DATE=false;
                COOMMEM_DATE=false;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_btnEndDate:
                START_DATE=false;
                END_DATE=true;
                COOMMEM_DATE=false;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_btncommencemetDate:
                START_DATE=false;
                END_DATE=false;
                COOMMEM_DATE=true;
                EXPECTED_DATE=false;
                showDateDialog();
                break;
            case R.id.RBD_book_btnExpetedDate:
                START_DATE=false;
                END_DATE=false;
                COOMMEM_DATE=false;
                EXPECTED_DATE=true;
                showDateDialog();
                break;*/
        }
    }

    private void showPlanDetailsDialog(RentalPlan rentalPlan) {



        AppCompatTextView txtPlanType;
        AppCompatTextView txtPlanName;
        AppCompatTextView txtPlanUsage;
        AppCompatTextView txtPlanDescription;
        ExpandableCardView expandableCardViewGeneral;
        ExpandableCardView expandableCardViewAttachment;
        ExpandableCardView expandableCardViewOperator;
        ExpandableCardView expandableCardViewMobility;



        String[] parents = new String[]{"General Details", "Attachment","Operator","Mobility Of Machine"};
        AppCompatImageButton btnClose;

        // ExpandableListView mExpandableList;
        //  ExpandableAdapter expandableAdapter;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rental_plan_details_dialogs);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        txtPlanType=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plantype);
        txtPlanName=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planname);
        txtPlanUsage=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planusage);
        txtPlanDescription=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plandescription);


        txtPlanType.setText(rentalPlan.getPlanType());
        txtPlanName.setText(rentalPlan.getPlanName());
        txtPlanUsage.setText(rentalPlan.getPlanUsage());
        txtPlanDescription.setText(rentalPlan.getPlanDescription());


        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.rental_plan_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        expandableCardViewGeneral=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandablegeneral);
        expandableCardViewAttachment=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandableattachment);
        expandableCardViewOperator=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandableoperator);
        expandableCardViewMobility=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandablemobility);



        expandableCardViewGeneral.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){
                   if (expandableCardViewAttachment.isExpanded()){
                       expandableCardViewAttachment.collapse();
                   }else if (expandableCardViewOperator.isExpanded()){
                       expandableCardViewOperator.collapse();
                   }else if (expandableCardViewMobility.isExpanded()){
                       expandableCardViewMobility.collapse();
                   }
                }
            }
        });
        expandableCardViewAttachment.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewOperator.isExpanded()){
                        expandableCardViewOperator.collapse();
                    }else if (expandableCardViewMobility.isExpanded()){
                        expandableCardViewMobility.collapse();
                    }
                }
            }
        });

        expandableCardViewOperator.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewAttachment.isExpanded()){
                        expandableCardViewAttachment.collapse();
                    }else if (expandableCardViewMobility.isExpanded()){
                        expandableCardViewMobility.collapse();
                    }

                }
            }
        });

        expandableCardViewMobility.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewAttachment.isExpanded()){
                        expandableCardViewAttachment.collapse();
                    }else if (expandableCardViewOperator.isExpanded()){
                        expandableCardViewOperator.collapse();
                    }
                }
            }
        });
        // AppCompatTextView generalTitle=(AppCompatTextView)expandableCardViewGeneral.findViewById(R.id.parent_txt_general);
        // generalTitle.setText("General Details");
        // generalTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);


        AppCompatTextView load = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_load);
        AppCompatTextView shift = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_shift);
        AppCompatTextView basic_rate = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_basicrate);
        AppCompatTextView hours_Monthly = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_hourMonthly);
        AppCompatTextView daily_Hours = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_dailyhours);
        AppCompatTextView days_Monthly = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_daysmonthly);
        AppCompatTextView committed_Name = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_committed_name);
        AppCompatTextView committed_Month = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_committed_month);
        AppCompatTextView overtime = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_overtime);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        load.setText(rentalPlan.getLoad());
        shift.setText(rentalPlan.getShift());
        basic_rate.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlan.getBasicRate()));
        hours_Monthly.setText(""+rentalPlan.getMonthlyHours());
        if (rentalPlan.getShift().equals("Single Shift")){
            daily_Hours.setText(String.valueOf(rentalPlan.getDailyHours()));
        }else {
            daily_Hours.setText(String.valueOf(rentalPlan.getDailyHours()));
        }

        days_Monthly.setText(""+rentalPlan.getMinDays());
        if (rentalPlan.getPlanUsageCode().equals("DAILY")){
            committed_Name.setText("Days (Committed)");
            committed_Month.setText(""+rentalPlan.getDailyMinHours());
        }else if (rentalPlan.getPlanUsageCode().equals("MONTHLY")){
            committed_Name.setText("Months (Committed)");
            committed_Month.setText(""+rentalPlan.getCommitmentMonth());
        }else if (rentalPlan.getPlanUsageCode().equals("HOURLY")){
            committed_Name.setText("Hours (Committed)");
            committed_Month.setText(""+rentalPlan.getDailyMinHours());
        }


        overtime.setText(""+rentalPlan.getOvertime());


        //AppCompatTextView attatmentTitle=(AppCompatTextView)expandableCardViewAttachment.parentLayout.findViewById(R.id.parent_txt_attachment);
        // attatmentTitle.setText("Attachment");
        //  attatmentTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox att_basic_rate = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_basicrate_check);
        AppCompatCheckBox extra_att = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_extra_attachment_check);
        AppCompatTextView extra_att_rate = (AppCompatTextView) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_rate);
        AppCompatTextView extra_att_rate_heading=(AppCompatTextView)expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_rate_heading);
        att_basic_rate.setChecked(rentalPlan.isExtraAttachment());
        att_basic_rate.setKeyListener(null);
        extra_att.setChecked(rentalPlan.isExtraAttachmentRateFlg());
        extra_att.setKeyListener(null);
        if (rentalPlan.isExtraAttachmentRateFlg()){
            extra_att_rate_heading.setText("Attachment Rate (Per Hr.)");
        }else {
            extra_att_rate_heading.setText("Attachment Rate (Fixed)");
        }
        extra_att_rate.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlan.getExtraAttachmentRate()));

        // AppCompatTextView operatorTitle=(AppCompatTextView)expandableCardViewOperator.parentLayout.findViewById(R.id.parent_txt_operator);
        //operatorTitle.setText("Operator");
        // operatorTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox operatorInclude = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_oprator_check);
        AppCompatTextView operatorRate = (AppCompatTextView) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_fixed_amount);
        AppCompatCheckBox accomodation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_accomodation);
        AppCompatCheckBox transportation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_transportation);
        AppCompatCheckBox food = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_food);
        AppCompatTextView operatorRateHeading=(AppCompatTextView) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_fixed_amount_heading);

        operatorInclude.setChecked(rentalPlan.isOperatorFlg());
        operatorInclude.setKeyListener(null);
        if (rentalPlan.isRateFlg()){
            operatorRateHeading.setText("Amount (Fixed)");
        }else {
            operatorRateHeading.setText("Amount (Per Hr.)");
        }
        operatorRate.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlan.getRate()));
        accomodation.setChecked(rentalPlan.isAccomodation());
        accomodation.setKeyListener(null);
        transportation.setChecked(rentalPlan.isTransportation());
        transportation.setKeyListener(null);
        food.setChecked(rentalPlan.isFood());
        food.setKeyListener(null);

        //  AppCompatTextView mobilityTitle=(AppCompatTextView)expandableCardViewMobility.findViewById(R.id.parent_txt_mobility);
        //  mobilityTitle.setText("Mobility Of Machine");
        //  mobilityTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        LinearLayout mobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobolity_ln);
        LinearLayout demobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobolity_ln);

        AppCompatTextView mobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobility_amount);
        AppCompatTextView demobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityAmount);
        AppCompatTextView mobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_responsibility);
        AppCompatTextView demobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityResponsibility);

        AppCompatTextView mobiAmountHeading=(AppCompatTextView)expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobility_amount_heading);
        AppCompatTextView demobiAmountHeading=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityAmount_heading);


        if (!rentalPlan.getMobilityResponsible().equals("O")){
            mobilityLn.setVisibility(View.GONE);
        }
        if (!rentalPlan.getDemobilityResponsible().equals("O")){
            demobilityLn.setVisibility(View.GONE);
        }

        if (rentalPlan.getMobilityResponsible().equals("O")){
            mobilityresposibility.setText("Owner");
        }else if (rentalPlan.getMobilityResponsible().equals("C")){
            mobilityresposibility.setText("Client");
        }else if (rentalPlan.getMobilityResponsible().equals("T")){
            mobilityresposibility.setText("Transporter");
        }


        if (rentalPlan.isTakingAmount()){
            mobiAmountHeading.setText("Mobility Amount (Fixed)");
        }else {
            mobiAmountHeading.setText("Mobility Amount (Per Hr.)");
        }

        mobilityAmount.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlan.getMobilityAmt()));

        if (rentalPlan.getDemobilityResponsible().equals("O")){
            demobilityresposibility.setText("Owner");
        }else if (rentalPlan.getDemobilityResponsible().equals("C")){
            demobilityresposibility.setText("Client");
        }else if (rentalPlan.getDemobilityResponsible().equals("T")){
            demobilityresposibility.setText("Transporter");
        }

        if (rentalPlan.isResponsibilityAmount()){
            demobiAmountHeading.setText("Mobility Amount (Fixed)");
        }else {
            demobiAmountHeading.setText("Mobility Amount (Per Hr.)");
        }

        demobilityAmount.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlan.getDemobilityAmt()));



        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }



    private void callAttachmentApi() {
        int machineId=renterSpecificMachine.getMachineId();
        String token=TokenManager.getSessionToken();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<MachineAttachment>> attachmentCall=apiInterface.getAllAttachmentForMachine("Bearer "+token,machineId);
        attachmentCall.enqueue(new Callback<List<MachineAttachment>>() {
            @Override
            public void onResponse(Call<List<MachineAttachment>> call, Response<List<MachineAttachment>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    machineAttachmentsList.clear();
                    machineAttachmentsList=response.body();
                    callSetArrayforDialogs();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            //Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
                            ediAttachments.setText("--No attachments--");
                            ediAttachments.setEnabled(false);
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MachineAttachment>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSetArrayforDialogs() {

        attachmentItems=new ArrayList<>();
        selectedAttachment.clear();
        checkedItems=new boolean[machineAttachmentsList.size()];
        for(int i=0;i<machineAttachmentsList.size();i++){
            attachmentItems.add(machineAttachmentsList.get(i).getAttachmentName());
            if (machineAttachmentsList.get(i).isDefaultCheck()){
                checkedItems[i]=true;
                mUserItems.add(i);
                ediAttachments.setText(machineAttachmentsList.get(i).getAttachmentName());
                selectedAttachment.add(machineAttachmentsList.get(i));
            }
        }
    }

   /* private void showattchamentDialog() {
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
        AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
        titleText.setText("Attachments");
        mBuilder.setCustomTitle(view);
        // mBuilder.setTitle("Segments");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                    }
                }else if(mUserItems.contains(position)){
                        mUserItems.remove(mUserItems.indexOf(position));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";
                if (mUserItems.size()==0){
                    item="";
                }else {
                    for(int i=0;i<mUserItems.size();i++){
                        item=item+listItems[mUserItems.get(i)];
                        if (i!=mUserItems.size()-1){
                            item=item+", ";
                        }
                    }
                }
                ediAttachments.setText("");
                ediAttachments.setText(item);
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
                    ediAttachments.setText("");
                }
            }
        });

        AlertDialog alertDialog=mBuilder.create();
        alertDialog.show();
    }*/

      public void showDateDialog(){
          Calendar newCalendar = Calendar.getInstance();
          long todayTime=newCalendar.getTime().getTime();

          DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
              public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  Calendar newDate = Calendar.getInstance();
                  newDate.set(year, month, dayOfMonth);
                  String dateString="";
                  if ((month+1)<10){

                      if (dayOfMonth<10){
                          dateString="0"+String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(month+1)+"/"+String.valueOf(year);
                      }else {
                          dateString=String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(month+1)+"/"+String.valueOf(year);
                      }

                  }else {

                      if (dayOfMonth<10){
                          dateString="0"+String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
                      }else {
                          dateString=String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
                      }

                  }

                 if (START_DATE){


                     if (Constants.MACHINE_BOOK_END_DATE != null){
                         String toDateString=Constants.MACHINE_BOOK_END_DATE;

                         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                         Date seletedDate = null;
                         Date toDate=null;
                         try {
                             seletedDate = sdf.parse(dateString);
                             toDate=sdf.parse(toDateString);
                         } catch (ParseException e) {
                             e.printStackTrace();
                         }
                         if (seletedDate.before(toDate)|| seletedDate.compareTo(toDate)==0) {
                             editStartDate.setText(dateString);
                             // booking_start_date=dateString;
                             Constants.MACHINE_BOOK_START_DATE=dateString;
                             calculateEstimatedAmount(selectedPlan.get(0));
                         }else {
                             // showSnackbar("Selected Date is greater than to Date :"+toDateString);
                             showSnackbar( "Selected Date is greater than end Date :"+toDateString);
                         }
                     }else {

                         editStartDate.setText(dateString);
                         // booking_start_date=dateString;
                         Constants.MACHINE_BOOK_START_DATE=dateString;
                         calculateEstimatedAmount(selectedPlan.get(0));
                     }

                 }if (END_DATE){

                      if (Constants.MACHINE_BOOK_START_DATE!=null){
                          String fromDateString=Constants.MACHINE_BOOK_START_DATE;

                          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                          Date seletedDate = null;
                          Date fromDate=null;
                          try {
                              seletedDate = sdf.parse(dateString);
                              fromDate=sdf.parse(fromDateString);
                          } catch (ParseException e) {
                              e.printStackTrace();
                          }
                          if (seletedDate.after(fromDate) || seletedDate.compareTo(fromDate)==0) {
                              editEndDate.setText(dateString);
                              //   booking_end_date=dateString;
                              Constants.MACHINE_BOOK_END_DATE=dateString;
                              calculateEstimatedAmount(selectedPlan.get(0));
                          }else {
                              // showSnackbar("Selected Date is Lesser than from Date :"+fromDateString);
                              showSnackbar("Selected Date is Lesser than start Date :"+fromDateString);
                          }
                      }else {

                          editEndDate.setText(dateString);
                          //   booking_end_date=dateString;
                          Constants.MACHINE_BOOK_END_DATE=dateString;
                          calculateEstimatedAmount(selectedPlan.get(0));
                      }

                 }
                 if (COOMMEM_DATE){
                     editDateOfCommencemet.setText(dateString);
                     mCommemcemetDate=dateString;
                 }

                 if (EXPECTED_DATE){
                     editExpectedDateAvailability.setText(dateString);
                     mExpectedDateDate=dateString;
                 }

              }

          }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

          StartTime.getDatePicker().setMinDate(todayTime);
          StartTime .show();
      }

    private void popWindowList(String title,ArrayList<String> listData) {
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);
        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        if (REASON_RENTAL){
            popupWindow.setAnchorView(editBookingReason);
            popupWindow.setWidth(editBookingReason.getMeasuredWidth());
        }else {
            popupWindow.setAnchorView(editRentalPlan);
            popupWindow.setWidth(editRentalPlan.getMeasuredWidth());
        }

        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (REASON_RENTAL){
                    editBookingReason.setText(listData.get(position));
                    booking_Reason_text=listData.get(position);
                    booking_Reason=bookingReasonMap.get(listData.get(position));

                }else {
                    String[] rental=listData.get(position).split(" - ");
                    editRentalPlan.setText(rental[2]);
                    selectedPlan.clear();
                    selectedPlan.add(rentalPlansList.get(position));
                    calculateEstimatedAmount(rentalPlansList.get(position));
                    booking_planName=listData.get(position);
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    private void callRentalPlanApi() {
        String token= TokenManager.getSessionToken();
        int partyId=renterSpecificMachine.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RentalPlan>> rentalPlanCall=apiInterface.getAllPlanForMachine("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<List<RentalPlan>>() {
            @Override
            public void onResponse(Call<List<RentalPlan>> call, Response<List<RentalPlan>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    rentalPlansList.clear();
                    rentalPlansList=response.body();
                    setListName();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            //Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RentalPlan>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListName() {
        rentalPlanName.clear();
        selectedPlan.clear();
        if (rentalPlansList.size()>0) {
            if (selectedPlan.size()==0){
                selectedPlan.add(rentalPlansList.get(0));
                calculateEstimatedAmount(rentalPlansList.get(0));
                editRentalPlan.setText(rentalPlansList.get(0).getPlanName());
                booking_planName=rentalPlansList.get(0).getPlanName();
                for (int i = 0; i < rentalPlansList.size(); i++) {
                    rentalPlanName.add(rentalPlansList.get(i).getPlanType()+" - "+rentalPlansList.get(i).getPlanUsageCode()+" - "+rentalPlansList.get(i).getPlanName());
                }
            }

        }else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        androidx.appcompat.app.AlertDialog.Builder alertDialog=new androidx.appcompat.app.AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.error);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Owner have not define rental plan for this machine.");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Cancel");
       AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
       buttonNegative.setVisibility(View.GONE);
        androidx.appcompat.app.AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

       /* buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // getActivity().getSupportFragmentManager().popBackStack();
                if (Constants.USER_ROLE.equals("Renter")){
                    callRenterHomeFragments();
                }else if (Constants.USER_ROLE.equals("Owner")){
                    callOwnerMainFragment();
                }else {
                    callMainFragments();
                }
            }
        });*/
        dialog.show();
    }

    private SpannableStringBuilder SpannableStringBuilder(final String text, final float reduceBy) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(reduceBy);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(text);
        ssBuilder.setSpan(
                smallSizeText,
                0,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssBuilder;
    }

     public long getDaysBetweenDates(String start, String end) {
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

    private long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        if (startDate.equals(endDate)){
            return 1;
        }
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


    private void callBookingReason() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> attachmentCall=apiInterface.getBookingReason();
        attachmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject jsonObject, int check) {
        HashMap<String, String> commonMap = new HashMap<>();
        reasonList.clear();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            reasonList.add(key);
            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

            Map.Entry<String,String> entry = commonMap.entrySet().iterator().next();
            editBookingReason.setText(entry.getKey());
            booking_Reason=entry.getValue();
            booking_Reason_text=entry.getKey();

        setHashMap(commonMap, check);
    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 0:
                bookingReasonMap = map;
                break;
        }

    }

    public void calculateEstimatedAmount(RentalPlan rentalPlan){

       /* float subAmount=0;
        long numberOfDays = getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE, Constants.MACHINE_BOOK_END_DATE);

        if (rentalPlan.getMinDays() > numberOfDays) {

            if (rentalPlan.getShift().equals("Single Shift")) {
                subAmount = rentalPlan.getMinDays() * 8 * rentalPlan.getBasicRate();
            } else if (rentalPlan.getShift().equals("Double Shift")) {
                subAmount = rentalPlan.getMinDays() * 8 * 2 * rentalPlan.getBasicRate();
            }

        } else {

            if (rentalPlan.getShift().equals("Single Shift")) {
                subAmount = numberOfDays * 8 * rentalPlan.getBasicRate();
            } else if (rentalPlan.getShift().equals("Double Shift")) {
                subAmount = numberOfDays * 8 * 2 * rentalPlan.getBasicRate();
            }

        }

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        String eString="Estimated Amount";
        String amountString="\u20B9 "+IndianCurrencyFormat.format(subAmount);

        String GstandIGst="(* GST / IGST and Mobility Amount Excluded )";

        MachineBookingDetails.txtEstimatedCosttext.setText(eString);
        MachineBookingDetails.txtEstimatedCostValue.setText(amountString);
        MachineBookingDetails.txtEstimatedGst.setText(GstandIGst);

        return subAmount;*/

        long numberOfDays = getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE, Constants.MACHINE_BOOK_END_DATE);


        if (numberOfDays==-1){
            showSnackbar("Seleted end date greater than start date");
            return;
        }

        String planUsage=rentalPlan.getPlanUsageCode();
        int dailyHours=rentalPlan.getDailyHours();
        float basicRate=rentalPlan.getBasicRate();
        String shift=rentalPlan.getShift();
        float extraAttachmentRate=rentalPlan.getExtraAttachmentRate();
        boolean rateFlg=rentalPlan.isRateFlg();
        float rate=rentalPlan.getRate();
        int monthlyHours=rentalPlan.getMonthlyHours();
        float mobilityAmt=rentalPlan.getMobilityAmt();
        int minDays=rentalPlan.getMinDays();
        int extraAttachmentCount=0;
        for (int i=0;i<selectedAttachment.size();i++){
            if (!selectedAttachment.get(i).isDefaultCheck()){
                extraAttachmentCount=extraAttachmentCount+1;
            }
        }

        boolean extraAttachmentRateFlg=false;
        int dailyMinHours=rentalPlan.getDailyMinHours();
        long requiredHour=0;
        if (numberOfDays>=1){
            requiredHour=(numberOfDays*(int)dailyHours);
        }
        int commitmentMonth=rentalPlan.getCommitmentMonth();


        long totalHour=0;
        float totalAmount=0;
        float totalAttachmetRate=0;
        float totalBasicRate=basicRate;


        if(planUsage.equals("MONTHLY")){
         //   $("#reqHour").val((diffDays * parseInt(dailyHours)));
			/* if(diffDays >= 30){
				totalHour=diffDays*dailyHours;
			}else if(diffDays < 30){
				totalHour=30*dailyHours;
			} */
            if((numberOfDays*dailyHours) > (monthlyHours*commitmentMonth)){
                totalHour=(numberOfDays*dailyHours);
            }else{
                totalHour=(monthlyHours*commitmentMonth);
            }
            if(extraAttachmentCount >=1){
                totalAttachmetRate=extraAttachmentRate*extraAttachmentCount;
                if(extraAttachmentRateFlg==true){
                    totalBasicRate=Math.round(totalBasicRate)+(Math.round((totalAttachmetRate)) / (monthlyHours));
                }else{
                    totalBasicRate=Math.round(totalBasicRate) + Math.round((totalAttachmetRate));
                }
            }
            if(rateFlg==true){
                totalBasicRate = Math.round((totalBasicRate))+(Math.round((rate)) / (monthlyHours));
            }else{
                totalBasicRate=Math.round((totalBasicRate)) + Math.round((rate));
            }
            totalAmount=(totalBasicRate*totalHour)+(mobilityAmt);
            if(shift == "Double Shift"){
                totalAmount=totalAmount*2;
            }
        }else if(planUsage.equals("DAILY")){
           // $("#reqHour").val((diffDays * parseInt(dailyHours)));
            if(numberOfDays >= minDays){
                totalHour=numberOfDays*dailyHours;
            }else if(numberOfDays <= minDays){
                totalHour=minDays*dailyHours;
            }
            if(extraAttachmentCount >=1){
                totalAttachmetRate=extraAttachmentRate*extraAttachmentCount;
                if(extraAttachmentRateFlg==true){
                    totalBasicRate=Math.round(totalBasicRate)+(Math.round((totalAttachmetRate)) / (monthlyHours));
                }else{
                    totalBasicRate=Math.round(totalBasicRate) + Math.round((totalAttachmetRate));
                }
            }
            if(rateFlg==true){
                totalBasicRate = Math.round(totalBasicRate)+(Math.round((rate)) / (monthlyHours));
            }else{
                totalBasicRate=Math.round(totalBasicRate) + Math.round((rate));
            }
            totalAmount=(totalBasicRate*totalHour)+(mobilityAmt);
            if(shift == "Double Shift"){
                totalAmount=totalAmount*2;
            }
        }else if(planUsage.equals("HOURLY")){
           // $("#reqHour").val(requiredHour);
            if((requiredHour) >= (dailyMinHours)){
                totalHour=requiredHour;
            }else if((requiredHour) <= (dailyMinHours)){
                totalHour=dailyMinHours;
            }
            if(extraAttachmentCount >=1){
                totalAttachmetRate=extraAttachmentRate*extraAttachmentCount;
                if(extraAttachmentRateFlg==true){
                    totalBasicRate=Math.round(totalBasicRate)+(Math.round((totalAttachmetRate)) / (monthlyHours));
                }else{
                    totalBasicRate=Math.round(totalBasicRate) + Math.round((totalAttachmetRate));
                }
            }
            if(rateFlg==true){
                totalBasicRate = Math.round(totalBasicRate)+(Math.round((rate)) / (monthlyHours));
            }else{
                totalBasicRate=Math.round(totalBasicRate) + Math.round((rate));
            }
            totalAmount=(totalBasicRate*totalHour)+(mobilityAmt);
            if(shift == "Double Shift"){
                totalAmount=totalAmount*2;
            }
        }

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        String amountString="\u20B9 "+IndianCurrencyFormat.format(totalAmount);
        String eString="Estimated Amount :";
        String GstandIGst="( * GST / IGST and Mobility Amount Excluded )";


        MachineBookingDetails.txtEstimatedCosttext.setText(eString);
        MachineBookingDetails.txtEstimatedCostValue.setText(amountString);
        MachineBookingDetails.txtEstimatedGst.setText(GstandIGst);

        totalAmountForWorkOrder=totalAmount;


    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }



    public void showCustomMultipleSelectionDialog(){

        RecyclerView multipleRecycle;
        AppCompatTextView titleText;
        AppCompatImageButton btnClose;
        AppCompatButton btnOk;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custome_attachment_selection);

        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        multipleRecycle=(RecyclerView)dialog.findViewById(R.id.attachment_dailog_recycle);
       // titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_multi_selection_dialog_title);
        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.attachment_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk=(AppCompatButton)dialog.findViewById(R.id.attachment_dailog_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String item="";
                for(int i=0;i<mUserItems.size();i++){
                    item=item+attachmentItems.get(mUserItems.get(i));
                    if (i!=mUserItems.size()-1){
                        item=item+",";
                    }
                }
                ediAttachments.setText("");
                ediAttachments.setText(item);
                selectedAttachment.clear();
                    if (mUserItems.size()>0){
                        for (int i=0;i<mUserItems.size();i++){
                            selectedAttachment.add(machineAttachmentsList.get(mUserItems.get(i)));
                        }
                    }

                    if (selectedAttachment.size()>0){
                        calculateEstimatedAmount(selectedPlan.get(0));
                    }
            }
        });

       // titleText.setText("Attachments");
        multipleRecycle.setHasFixedSize(false);
        multipleRecycle.setItemAnimator(new DefaultItemAnimator());
        multipleRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (((AppCompatCheckBox)view).isChecked()){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                        checkedItems[position]=true;
                    }
                }else if (mUserItems.contains(position)){
                    mUserItems.remove(mUserItems.indexOf(position));
                    checkedItems[position]=false;
                }


            }

        };

        MultipleSelctionAttachmentRenter recycleAdapter=new MultipleSelctionAttachmentRenter(getActivity(), attachmentItems,listener,checkedItems,machineAttachmentsList);
        //  SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(context,listData,listener);
        multipleRecycle.setAdapter(recycleAdapter);
        dialog.show();

    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

}
