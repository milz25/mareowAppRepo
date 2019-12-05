package com.mareow.recaptchademo.RenterFragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.RatingDialogAdapter;
import com.mareow.recaptchademo.Adapters.RenterMachineImagePagerAdapter;
import com.mareow.recaptchademo.Adapters.RenterSimilarAdapter;
import com.mareow.recaptchademo.DataModels.ContactOwner;
import com.mareow.recaptchademo.DataModels.FeedbackForOwner;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.MainActivityFragments.MessagesFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterMachineBookFragment.MachineBookingDateFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.location.LocationListener;
import android.os.Handler;
import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RenterMachineDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RenterMachineDetailsFragment extends Fragment implements LocationListener,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatImageView machineImage;
    AppCompatImageView mFavoriteImage;
    AppCompatImageView mVerifiedImage;


    TextInputEditText mRegistrationNo;
    TextInputEditText mManufactureYear;
    TextInputEditText mExpectedDate;
    TextInputEditText mCommencementDate;
    TextInputEditText mCapacity;
    TextInputEditText mRemarks;
    TextInputEditText mOdometer;
    TextInputEditText mMachineRun;
    TextInputEditText mMachineDiscription;
    TextInputEditText mMachineCateSubCate;
    TextInputEditText mMachineMFGModel;
    TextInputEditText mMachineName;


    TextInputEditText mMachineCurrentLocation;
    FloatingActionButton btnMap;

    TextInputEditText mMachineReviews;
    MaterialRatingBar mMachineRatingValue;

    TextInputEditText mOwnerLocation;
    TextInputEditText mOwnerAssociated;
    TextInputEditText mAboutOwner;
    TextInputEditText mOwnerReviews;
    MaterialRatingBar mOwnerRatingValue;
    TextInputEditText mContactOwner;
    FloatingActionButton btnContactOwner;

    RecyclerView mSimilarRecycleView;
    //AppCompatTextView btnRentalPlan;

   // OptionsFabLayout fabWithOption;

    ArrayList<RenterMachine> machineDetailsList=new ArrayList<>();
    RenterSpecificMachine machineReview;
    FeedbackForOwner ownerFeedBack;
    ProgressDialog progressDialog;
    LinearLayout machineRatingClick;
    LinearLayout ownerRatingClick;

    AppCompatButton footerTxt;
     LocationManager locationManager;
     LocationListener locationListener;
     double mLatitude;
     double mLongitude;
    private int currentIndex;
    private int startIndex;
    private int endIndex;
    Handler nextHandler=new Handler();
    Handler preHandler=new Handler();
    List<RenterMachine> machineListForRenter;
    boolean FAVORITE=false;
    List<String> question=new ArrayList<>();
    List<String> ratingvalue=new ArrayList<>();
    List<RentalPlan> rentalPlansList=new ArrayList<>();

    ViewPager mImageSlider;
    CirclePageIndicator imageIndicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    RelativeLayout footer;

    public static String commencementDate=null;
    public static String remarkQCEL="";
    public static String expectedDeliveryDate=null;

    public RenterMachineDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RenterMachineDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RenterMachineDetailsFragment newInstance(String param1, String param2) {
        RenterMachineDetailsFragment fragment = new RenterMachineDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_renter_machine_details, container, false);
        RenterMainActivity.txtRenterTitle.setText("Machine Details");
        Bundle bundle=getArguments();
        machineDetailsList=(ArrayList<RenterMachine>) bundle.getSerializable("newList");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait......");
        callSpecificMachineDetails();
       // callOwnerFeedbackapi();
        initView(view);
        return view;
    }

    public void initView(View view){
      //  viewFlipper=(ViewFlipper)view.findViewById(R.id.able_to_run_machine_viewflipper)


        mMachineCateSubCate=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_category);
        mMachineCateSubCate.setInputType(InputType.TYPE_NULL);
        mMachineMFGModel=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_mfg_model);
        mMachineMFGModel.setInputType(InputType.TYPE_NULL);
        mMachineName=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_mfg_machinename);
        mMachineName.setInputType(InputType.TYPE_NULL);
        mImageSlider=(ViewPager)view.findViewById(R.id.RMD_machine_viewpager);
        imageIndicator=(CirclePageIndicator)view.findViewById(R.id.imageslider_indicator);

        mMachineDiscription=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_desc);
        //machineImage=(AppCompatImageView)view.findViewById(R.id.RMD_machine_image);
        mFavoriteImage=(AppCompatImageView)view.findViewById(R.id.RMD_machine_favorite);
        mVerifiedImage=(AppCompatImageView)view.findViewById(R.id.RMD_machine_verified);

        footerTxt=(AppCompatButton) view.findViewById(R.id.RMD_machine_estimated_cost_text);
        footerTxt.setOnClickListener(this);

        mFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FAVORITE){
                    mFavoriteImage.setImageResource(R.drawable.ic_bookmark_border);
                    FAVORITE=false;
                }else {
                    mFavoriteImage.setImageResource(R.drawable.ic_bookmark);
                    FAVORITE=true;
                }
                callBookMarkForMachineAPI();
            }
        });

        mRegistrationNo=(TextInputEditText)view.findViewById(R.id.RMD_machine_registration_no);
        mManufactureYear=(TextInputEditText)view.findViewById(R.id.RMD_machine_manufacturing_year);
        mExpectedDate=(TextInputEditText)view.findViewById(R.id.RMD_machine_expected_date);
        mCommencementDate=(TextInputEditText)view.findViewById(R.id.RMD_machine_commencement_date);
        mCommencementDate.setInputType(InputType.TYPE_NULL);
        mCommencementDate.setOnClickListener(this);
        mCommencementDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDateDialog();
                }
            }
        });

        mCapacity=(TextInputEditText)view.findViewById(R.id.RMD_machine_capacity);
        mRemarks=(TextInputEditText)view.findViewById(R.id.RMD_machine_remarks);
        mRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               remarkQCEL=s.toString();
            }
        });

        mOdometer=(TextInputEditText)view.findViewById(R.id.RMD_machine_odometer_reading);
        mMachineRun=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_run);
        machineRatingClick=(LinearLayout)view.findViewById(R.id.RMD_machine_machine_ratingmachineclick);
        machineRatingClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.clear();
                ratingvalue.clear();

                question.add("Condition");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getCondition()));
                question.add("Efficiency");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getEfficiency()));
                question.add("Fuel Efficiency");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getFuelEfficiency()));
                question.add("Maintained");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getMaintained()));

                callRatinBarDialog(question,ratingvalue,"Machine Reviews");
            }
        });


        ownerRatingClick=(LinearLayout)view.findViewById(R.id.RMD_machine_machine_ratingownerclick);
        ownerRatingClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.clear();
                ratingvalue.clear();

                question.add("Work Commitment");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkCommitment()));
                question.add("Query Response");
                ratingvalue.add(String.valueOf(ownerFeedBack.getQueryResponse()));
                question.add("Working Efficiency");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkingEfficiency()));
                question.add("Work Attitude");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkAttitude()));

                callRatinBarDialog(question,ratingvalue,"Owner Reviews");
            }
        });
        mMachineCurrentLocation=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_current_location);
        btnMap=(FloatingActionButton) view.findViewById(R.id.RMD_machine_machine_button_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + machineReview.getLatitude() + "," + machineReview.getLongitude() + " (" + machineReview.getCurrentLocation() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

        mMachineReviews=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_ratingtext);
        mMachineRatingValue=(MaterialRatingBar) view.findViewById(R.id.RMD_machine_machine_ratingvalue);
        mMachineRatingValue.setOnClickListener(this::initView);


        mOwnerLocation=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_owner_location);
        mOwnerAssociated=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_owner_assoication);
       /* mAboutOwner=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_about_owner);*/
        mOwnerReviews=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_total_reviews);
        mOwnerRatingValue=(MaterialRatingBar)view.findViewById(R.id.RMD_machine_machine_owner_review_value);
        mOwnerRatingValue.setOnClickListener(this::initView);


        /*mContactOwner=(TextInputEditText)view.findViewById(R.id.RMD_machine_machine_owner_contact);*/
        btnContactOwner=(FloatingActionButton)view.findViewById(R.id.RMD_machine_machine_contact_owner);
        btnContactOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMessageFragment();
            }
        });

      /*  btnRentalPlan=(AppCompatTextView)view.findViewById(R.id.RMD_machine_rentalPlan);
        btnRentalPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRentalPlanFrgment();
            }
        });
*/
        mSimilarRecycleView=(RecyclerView)view.findViewById(R.id.RMD_machine_nearby_recycle);
        mSimilarRecycleView.setNestedScrollingEnabled(false);
        mSimilarRecycleView.setHasFixedSize(false);
        mSimilarRecycleView.setItemAnimator(new DefaultItemAnimator());
        mSimilarRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        /*fabWithOption=(OptionsFabLayout)view.findViewById(R.id.RMD_machine_fabwithoption);
        fabWithOption.setMiniFabsColors(R.color.theme_orange,R.color.colorPrimary,R.color.Blue_Text);
        fabWithOption.setMainFabOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Main fab clicked!", Toast.LENGTH_SHORT).show();
                if (fabWithOption.isOptionsMenuOpened()){
                    fabWithOption.closeOptionsMenu();
                }
            }
        });
*/

       /* fabWithOption.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
            @Override
            public void onMiniFabSelected(MenuItem fabItem) {
                switch (fabItem.getItemId()) {
                    case R.id.fab_book:
                        callBookMachineFragments();
                        break;
                    case R.id.fab_plan:
                        callRentalPlanFrgment();
                        break;
                    case R.id.fab_date:
                        callRentalBookDateFrgment();
                        break;
                    case R.id.RMD_machine_machine_ratingvalue:
                        question.clear();
                        ratingvalue.clear();

                        question.add("Condition");
                        ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getCondition()));
                        question.add("Efficiency");
                        ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getEfficiency()));
                        question.add("Fuel Efficiency");
                        ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getFuelEfficiency()));
                        question.add("Maintained");
                        ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getMaintained()));

                        callRatinBarDialog(question,ratingvalue,"Machine Reviews");

                        break;
                    case R.id.RMD_machine_machine_owner_review_value:
                        question.clear();
                        ratingvalue.clear();

                        question.add("Work Commitment");
                        ratingvalue.add(String.valueOf(ownerFeedBack.getWorkCommitment()));
                        question.add("Query Response");
                        ratingvalue.add(String.valueOf(ownerFeedBack.getQueryResponse()));
                        question.add("Working Efficiency");
                        ratingvalue.add(String.valueOf(ownerFeedBack.getWorkingEfficiency()));
                        question.add("Work Attitude");
                        ratingvalue.add(String.valueOf(ownerFeedBack.getWorkAttitude()));

                        callRatinBarDialog(question,ratingvalue,"Owner Reviews");
                        break;

                    default:
                        break;
                }
            }
        });*/


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.RMD_machine_machine_ratingvalue:
                question.clear();
                ratingvalue.clear();

                question.add("Condition");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getCondition()));
                question.add("Efficiency");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getEfficiency()));
                question.add("Fuel Efficiency");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getFuelEfficiency()));
                question.add("Maintained");
                ratingvalue.add(String.valueOf(machineReview.getMachineReviewDTO().getMaintained()));

                callRatinBarDialog(question,ratingvalue,"Machine Reviews");

                break;
            case R.id.RMD_machine_machine_owner_review_value:
                question.clear();
                ratingvalue.clear();

                question.add("Work Commitment");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkCommitment()));
                question.add("Query Response");
                ratingvalue.add(String.valueOf(ownerFeedBack.getQueryResponse()));
                question.add("Working Efficiency");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkingEfficiency()));
                question.add("Work Attitude");
                ratingvalue.add(String.valueOf(ownerFeedBack.getWorkAttitude()));

                callRatinBarDialog(question,ratingvalue,"Owner Reviews");
                break;
            case R.id.RMD_machine_estimated_cost_text:
                callBookMachineFragments();
                break;
            case R.id.RMD_machine_commencement_date:
                showDateDialog();
                break;
            default:
                break;
        }
    }

    private void callMessageFragment() {

       /* Fragment fragment = new MessagesFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack("Messages");
        fragmentTransaction.commitAllowingStateLoss();*/
        AppCompatImageButton btnClose;
        AppCompatImageView btnSend;
        TextInputEditText editMessage;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.contact_owner_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

       // mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
       // mTitle.setText();
      //  mWebViewData=(WebView)dialog.findViewById(R.id.RBD_popdialog_web_data);
       // mWebViewData.loadData(data,"text/html",null);
        btnSend=(AppCompatImageView)dialog.findViewById(R.id.contact_owner_send);
        editMessage=(TextInputEditText) dialog.findViewById(R.id.contact_owner_editmassage);
        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (!s.toString().equals("")){
                  btnSend.setImageResource(R.drawable.send_theme);
              }else {
                  btnSend.setImageResource(R.drawable.send_gray);
              }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editMessage.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "write your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                callSendMessageApi(editMessage.getText().toString());
                dialog.dismiss();
            }
        });

        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.contact_owner_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();



    }

    private void callSendMessageApi(String s) {

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ContactOwner contactOwner=new ContactOwner();
        contactOwner.setMessage(s);
        contactOwner.setMachineId(String.valueOf(machineReview.getMachineId()));
        contactOwner.setOwnerId(String.valueOf(machineReview.getPartyId()));
        contactOwner.setRenterId(String.valueOf(partyId));

        progressDialog.show();
        String token=TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> contactCall=apiInterface.contactOwnerByRenter("Bearer "+token,contactOwner);
        contactCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                   showSnackbar(response.body().getMessage());
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                           // Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



    }

    private void callRentalBookDateFrgment(){
        MachineBookingDateFragment machineBookingDateFragment=new MachineBookingDateFragment();

       /* Bundle bundle=new Bundle();
        bundle.putSerializable("machineDetails",machineReview);
        rentalPlanForRenterFragment.setArguments(bundle);*/
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, machineBookingDateFragment);
        fragmentTransaction.addToBackStack("BookDates");
        fragmentTransaction.commitAllowingStateLoss();

       }
    private void callRentalPlanFrgment() {

        RentalPlanForRenterFragment rentalPlanForRenterFragment=new RentalPlanForRenterFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("machineDetails",machineReview);
        rentalPlanForRenterFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, rentalPlanForRenterFragment);
        fragmentTransaction.addToBackStack("RentalPlan");
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void callBookMachineFragments() {

          MachineBookingDetails machineBookingDetails=new MachineBookingDetails();
          Bundle bundle=new Bundle();
          bundle.putSerializable("machineDetails",machineReview);
          machineBookingDetails.setArguments(bundle);

          FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
          fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
          fragmentTransaction.replace(R.id.fragment_container_main, machineBookingDetails);
          fragmentTransaction.addToBackStack("MachineBook");
          fragmentTransaction.commitAllowingStateLoss();

    }

    private void callBookMarkForMachineAPI() {
        progressDialog.show();
        String token=TokenManager.getSessionToken();
        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        int machineId=machineDetailsList.get(0).getMachineId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> bookmarkCall=apiInterface.bookMarkFavoriteMachine("Bearer "+token,machineId,partyId);
        bookmarkCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public void callSpecificMachineDetails(){

        String token=TokenManager.getSessionToken();
        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        int machineId=machineDetailsList.get(0).getMachineId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<RenterSpecificMachine> renterSpecificMachineCall=apiInterface.getSpecificMachineForRenter("Bearer "+token,machineId,partyId);
        renterSpecificMachineCall.enqueue(new Callback<RenterSpecificMachine>() {
            @Override
            public void onResponse(Call<RenterSpecificMachine> call, Response<RenterSpecificMachine> response) {
                progressDialog.show();
                if (response.isSuccessful()){
                    machineReview=response.body();
                    callOwnerFeedbackapi();
                   // callRentalPlanApi();
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
            public void onFailure(Call<RenterSpecificMachine> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    public void callOwnerFeedbackapi(){
        String token=TokenManager.getSessionToken();
        int partyId=machineReview.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<FeedbackForOwner> ownerFeedbackCall=apiInterface.getFeedBackForOwner("Bearer "+token,partyId);
        ownerFeedbackCall.enqueue(new Callback<FeedbackForOwner>() {
            @Override
            public void onResponse(Call<FeedbackForOwner> call, Response<FeedbackForOwner> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    ownerFeedBack=response.body();
                    setAllDataview();
                    callRenterMaichineForMainPage();
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
            public void onFailure(Call<FeedbackForOwner> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setAllDataview() {

        if (machineReview.isIsverified()){
            mVerifiedImage.setImageResource(R.drawable.ic_verify_true);
        }else {
            mVerifiedImage.setImageResource(R.drawable.ic_verify_false);
        }

        if (machineReview.isFavouriteFlg()){
            mFavoriteImage.setImageResource(R.drawable.ic_bookmark);
        }else {
            mFavoriteImage.setImageResource(R.drawable.ic_bookmark_border);
        }


        if (machineReview.isFavouriteFlg()){
            FAVORITE=true;
        }else {
            FAVORITE=false;
        }



        RenterMachineImagePagerAdapter adapter;
        List<String> imagePaths=new ArrayList<>();
        if (machineReview.getViewImagePathUrls().size()>0){
          /*  startIndex=0;
            endIndex=machineReview.getImagesPath().size()-1;
            currentIndex=0;

            nextImage();*/
          for (int i=0;i<machineReview.getViewImagePathUrls().size();i++){

              if (machineReview.getViewImagePathUrls().get(i)!=null){
                  imagePaths.add(machineReview.getViewImagePathUrls().get(i));
              }
          }


            adapter=new RenterMachineImagePagerAdapter(getActivity(),imagePaths);
            mImageSlider.setAdapter(adapter);

        }else {
            imagePaths.add(getString(R.string.machine_search));
            adapter=new RenterMachineImagePagerAdapter(getActivity(),imagePaths);
        }

        NUM_PAGES =imagePaths.size();
        mImageSlider.setAdapter(adapter);


        imageIndicator.setViewPager(mImageSlider);
        final float density = getResources().getDisplayMetrics().density;
        imageIndicator.setRadius(5 * density);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mImageSlider.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


        imageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });



        if (machineReview.getRegisterNo().equals("")){
            mRegistrationNo.setText(" ");
        }else {
            mRegistrationNo.setText(machineReview.getRegisterNo());
        }
        mRegistrationNo.setInputType(InputType.TYPE_NULL);

        mManufactureYear.setText(machineReview.getManufacturerYear());
        mManufactureYear.setInputType(InputType.TYPE_NULL);

        mMachineCateSubCate.setText(machineReview.getCategoryName()+" / "+machineReview.getSubCategoryName());
        mMachineMFGModel.setText(machineReview.getManufacturerName()+" / "+machineReview.getModelNo());
        mMachineName.setText(machineReview.getMachineName());
        mExpectedDate.setText(Util.convertYYYYddMMtoDDmmYYYY(machineReview.getAvailibilityDate()));
        mExpectedDate.setInputType(InputType.TYPE_NULL);

        expectedDeliveryDate=machineReview.getAvailibilityDate();

        String dateString=machineReview.getAvailibilityDate();
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = formats.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        String commmentCementDate=formats.format(date);

        mCommencementDate.setText(Util.convertYYYYddMMtoDDmmYYYY(commmentCementDate));
        mCommencementDate.setInputType(InputType.TYPE_NULL);
        commencementDate=commmentCementDate;

        if (machineReview.getLoadCapacity()==null){
            mCapacity.setText(" ");
        }else {
            mCapacity.setText(machineReview.getLoadCapacity());
        }


        mCapacity.setInputType(InputType.TYPE_NULL);

        mRemarks.setText(" ");
        mRemarks.setInputType(InputType.TYPE_NULL);

        mOdometer.setText(String.valueOf(machineReview.getOdometer()));
        mOdometer.setInputType(InputType.TYPE_NULL);

        mMachineRun.setText(String.valueOf(machineReview.getRunHours()));
        mMachineRun.setInputType(InputType.TYPE_NULL);

        mMachineDiscription.setText(machineReview.getMachineDesc());
        mMachineDiscription.setInputType(InputType.TYPE_NULL);

        mMachineCurrentLocation.setText(machineReview.getCurrentLocation()+" "+machineReview.getCurrentPostalCode());
        mMachineCurrentLocation.setInputType(InputType.TYPE_NULL);

        mMachineReviews.setText(machineReview.getMachineReviewDTO().getReviews()+" - Reviews");
        mMachineReviews.setInputType(InputType.TYPE_NULL);

        String ratingValueForMachine=String.valueOf(machineReview.getOverallRating());
        mMachineRatingValue.setRating(Float.parseFloat(ratingValueForMachine));
        mMachineRatingValue.setIsIndicator(true);




        mOwnerLocation.setText(machineReview.getOwnerLocation());
        mOwnerLocation.setInputType(InputType.TYPE_NULL);
        mOwnerAssociated.setText(machineReview.getOwnerCreatedDateStr());
        mOwnerAssociated.setInputType(InputType.TYPE_NULL);

      /*  mAboutOwner.setText(machineReview.getAboutOwner());
        mAboutOwner.setInputType(InputType.TYPE_NULL);*/

        mOwnerReviews.setText(ownerFeedBack.getReviews()+" - Reviews");
        mOwnerReviews.setInputType(InputType.TYPE_NULL);
        String ravalOwner=String.valueOf(ownerFeedBack.getTotalRating());
        mOwnerRatingValue.setRating(Float.parseFloat(ravalOwner));
        mOwnerRatingValue.setIsIndicator(true);


       /* DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        String eString="Estimated Amount";
        String amountString="\u20B9 "+IndianCurrencyFormat.format(calculateEstimatedAmount());

        String GstandIGst="( * GST / IGST Amount Excluded )";
           if (rentalPlansList.size()>0) {
               if (rentalPlansList.get(0).isGst()) {
                   GstandIGst = "( * GST / IGST Amount Included )";
               } else {
                   GstandIGst = "( * GST / IGST Amount Excluded )";

               }
           }

        SpannableString ss1=  new SpannableString(eString);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,eString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, eString.length(), 0);// set color

        SpannableString ss2=  new SpannableString(amountString);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,amountString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, amountString.length(), 0);

        SpannableString ss3=  new SpannableString(GstandIGst);
        //ss3.setSpan(new AbsoluteSizeSpan(getActivity().getResources().getDimensionPixelSize(R.dimen.text2),true), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss3.setSpan(new RelativeSizeSpan(0.5f), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.theme_orange)), 0, GstandIGst.length(), 0);


        SpannableStringBuilder builder=new SpannableStringBuilder();
        builder.append(ss1+"\n");
        builder.append(ss2+"\n");
        builder.append(ss3);
        footerTxt.setText(builder);*/
       /* RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                RenterMachine machine=machineListForRenter.get(position);
                CallMachineDetailsFragment(machine);
            }
        };

        RenterSimilarAdapter similarAdapter=new RenterSimilarAdapter(getActivity(),machineListForRenter,listener);
        mSimilarRecycleView.setAdapter(similarAdapter);
*/

    }

    private void CallMachineDetailsFragment(RenterMachine renterMachine) {

        ArrayList<RenterMachine> machines=new ArrayList<>();
        machines.add(renterMachine);
        RenterMachineDetailsFragment detailsFragment=new RenterMachineDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("newList",machines);
        detailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }


    @Override
    public void onLocationChanged(Location location) {
        mLatitude=location.getLatitude();
        mLongitude=location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void callRenterMaichineForMainPage(){
        progressDialog.show();

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Map<String,String> queryMap=new HashMap<>();

        queryMap.put("favPartyId",String.valueOf(partyId));
        queryMap.put("latitude",String.valueOf(mLatitude));
        queryMap.put("longitude",String.valueOf(mLongitude));

        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RenterMachine>> machineCall=apiInterface.getMachineForRenter("Bearer "+token,queryMap);
        machineCall.enqueue(new Callback<List<RenterMachine>>() {
            @Override
            public void onResponse(Call<List<RenterMachine>> call, Response<List<RenterMachine>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    machineListForRenter = response.body();
                    callSimilarRecycle();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){

                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getContext(), "mError", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RenterMachine>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSimilarRecycle() {

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                RenterMachine machine=machineListForRenter.get(position);
                CallMachineDetailsFragment(machine);
            }
        };

        RenterSimilarAdapter similarAdapter=new RenterSimilarAdapter(getActivity(),machineListForRenter,listener);
        mSimilarRecycleView.setAdapter(similarAdapter);

    }


    @Override
    public void onStop() {
        nextHandler.removeCallbacksAndMessages(null);
        preHandler.removeCallbacksAndMessages(null);
        super.onStop();
    }


   private void  callRatinBarDialog(List<String> questions,List<String> ratingvalues,String title){

       RecyclerView mRecycleview;
       AppCompatTextView mTile;
       AppCompatImageButton btnClose;
       Dialog rankDialog = new Dialog(getActivity());
       rankDialog.setContentView(R.layout.rating_dialog_layout);


       rankDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

       mRecycleview=(RecyclerView)rankDialog.findViewById(R.id.rating_dialog_recycle);
       mTile=(AppCompatTextView)rankDialog.findViewById(R.id.rating_dialog_title);
       mTile.setText(title);

       btnClose=(AppCompatImageButton)rankDialog.findViewById(R.id.rating_dialog_close);
       btnClose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               rankDialog.dismiss();
           }
       });
       mRecycleview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
       mRecycleview.setHasFixedSize(false);
       mRecycleview.setItemAnimator(new DefaultItemAnimator());
       mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));

       RatingDialogAdapter ratingDialogAdapter=new RatingDialogAdapter(getActivity(),questions,ratingvalues);
       mRecycleview.setAdapter(ratingDialogAdapter);

       rankDialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
       rankDialog.show();
   }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


  /*  private void callRentalPlanApi() {
        String token= TokenManager.getSessionToken();
        int partyId=machineReview.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RentalPlan>> rentalPlanCall=apiInterface.getAllPlanForMachine("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<List<RentalPlan>>() {
            @Override
            public void onResponse(Call<List<RentalPlan>> call, Response<List<RentalPlan>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    rentalPlansList=response.body();
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
            public void onFailure(Call<List<RentalPlan>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

   /* public float calculateEstimatedAmount(){

        float subAmount=0;
        if (rentalPlansList.size()>0) {
            RentalPlan rentalPlan = rentalPlansList.get(0);

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
        }
        return subAmount;

    }*/



   /* public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
*/


    public void showDateDialog(){

        Date startDate=null;
        SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=dateFormat1.parse(expectedDeliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        long minDate=calendar.getTime().getTime();


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

                mCommencementDate.setText(dateString);
                commencementDate=formateDateFromstring(dateString);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(minDate);
        StartTime .show();
    }

    public static String formateDateFromstring(String date){
        String outputDate=null;
        SimpleDateFormat df_input = new SimpleDateFormat("DD/MM/YYYY");
        SimpleDateFormat df_output = new SimpleDateFormat("YYYY-MM-DD");

        try {
            Date parsed = df_input.parse(date);
             outputDate = df_output.format(parsed);
        } catch (ParseException e) {
        }

        return outputDate;

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
