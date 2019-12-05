package com.mareow.recaptchademo.RenterFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterMachineBookFragment.BookDateAndReasonFragment;
import com.mareow.recaptchademo.RenterMachineBookFragment.BookTermAndConditionFragment;
import com.mareow.recaptchademo.RenterMachineBookFragment.BookingSummaryFragment;
import com.mareow.recaptchademo.RenterMachineBookFragment.SiteLocationFragment;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.ZoomOutPageTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineBookingDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineBookingDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static ViewPager mRentalBookingDetailsViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter renterBookMachineAdapter;
    public static AppCompatImageView btnBookNow;

    public static AppCompatTextView txtEstimatedCostValue;
    public static AppCompatTextView txtEstimatedCosttext;
    public static AppCompatTextView txtEstimatedGst;

    LinearLayout bookNowSection;
    RenterSpecificMachine specificMachineDetails;
    FragmentManager childFragmentManager;
    ProgressDialog progressDialog;

    public MachineBookingDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineBookingDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineBookingDetails newInstance(String param1, String param2) {
        MachineBookingDetails fragment = new MachineBookingDetails();
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
        View view=inflater.inflate(R.layout.fragment_booking_dates, container, false);
        RenterMainActivity.txtRenterTitle.setText("Machine Details");
        Bundle bundle=getArguments();
        specificMachineDetails=(RenterSpecificMachine) bundle.getSerializable("machineDetails");

        childFragmentManager=getChildFragmentManager();
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..............");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {

        mRentalBookingDetailsViewPager = (ViewPager)view.findViewById(R.id.RBD_viewpager);
        mRentalBookingDetailsViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        llPagerDots = (LinearLayout)view.findViewById(R.id.RBD_pager_dots);
        mRentalBookingDetailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==0){
                    if (BookDateAndReasonFragment.selectedPlan.size()>0){
                        BookDateAndReasonFragment.editRentalPlan.setText(BookDateAndReasonFragment.selectedPlan.get(0).getPlanName());
                        BookDateAndReasonFragment.booking_planName=BookDateAndReasonFragment.selectedPlan.get(0).getPlanName();
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    RenterMainActivity.navItemIndexRenter=21;
                }else {
                    RenterMainActivity.navItemIndexRenter=20;
                }

                if (position==ivArrayDotsPager.length-1){
                  //  bookNowSection.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bookNowSection.getLayoutParams();
                    params.addRule(RelativeLayout.LEFT_OF, btnBookNow.getId());
                    params.setMargins(0,8,0,5);
                    bookNowSection.setLayoutParams(params);

                   // bookNowSection.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
                    btnBookNow.setVisibility(View.VISIBLE);
                    BookingSummaryFragment.editTextBookingReason.setText(BookDateAndReasonFragment.booking_Reason_text);
                    BookingSummaryFragment.editTextRentalPlan.setText(BookDateAndReasonFragment.booking_planName);
                    BookingSummaryFragment.editTextBookingStartDates.setText(Constants.MACHINE_BOOK_START_DATE);
                    BookingSummaryFragment.editTextBookingEndDates.setText(Constants.MACHINE_BOOK_END_DATE);

                    if (BookDateAndReasonFragment.selectedPlan.get(0).getPlanUsageCode().equals("HOURLY")){
                        BookingSummaryFragment.txtNumberOfdays.setText(String.valueOf(getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE)));
                       // BookingSummaryFragment.txtNumberOfdays.setInputType(InputType.TYPE_NULL);
                        BookingSummaryFragment.txtOpratingHours.setText("");
                        BookingSummaryFragment.txtOpratingHours.setInputType(InputType.TYPE_CLASS_NUMBER);

                    }else {

                        BookingSummaryFragment.txtNumberOfdays.setText(String.valueOf(getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE)));
                        BookingSummaryFragment.txtOpratingHours.setText(String.valueOf(getDaysBetweenDates(Constants.MACHINE_BOOK_START_DATE,Constants.MACHINE_BOOK_END_DATE)*BookDateAndReasonFragment.selectedPlan.get(0).getDailyMinHours()));
                        BookingSummaryFragment.txtOpratingHours.setInputType(InputType.TYPE_NULL);
                    }


                    int count=0;
                    for (int i=0;i<BookDateAndReasonFragment.selectedAttachment.size();i++){

                        if (BookDateAndReasonFragment.selectedAttachment.get(i).isDefaultCheck()){

                        }else {
                            count++;
                        }
                    }
                    BookingSummaryFragment.editTextExtraAttachments.setText(String.valueOf(count));

                }else {

                   // bookNowSection.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bookNowSection.getLayoutParams();
                    params.setMargins(0,8,0,5);
                    bookNowSection.setLayoutParams(params);
                    //bookNowSection.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                    btnBookNow.setVisibility(View.GONE);
                }

                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_dots);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(mRentalBookingDetailsViewPager);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);

        txtEstimatedCosttext=(AppCompatTextView)view.findViewById(R.id.RBD_footer_estimated_cost_text);
        txtEstimatedCostValue=(AppCompatTextView)view.findViewById(R.id.RBD_footer_estimated_cost_value);
        txtEstimatedGst=(AppCompatTextView)view.findViewById(R.id.RBD_footer_estimated_gst_text);

        btnBookNow=(AppCompatImageView) view.findViewById(R.id.RBD_footer_bookNow);
        btnBookNow.setVisibility(View.GONE);




        bookNowSection=(LinearLayout)view.findViewById(R.id.amount);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,8,0,5);
        bookNowSection.setLayoutParams(params);

        //bookNowSection.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

    }


    private void setupViewPager(ViewPager viewPager) {

        if (getActivity()!=null && isAdded()){

            renterBookMachineAdapter = new DetailActivityViewPagerAdapter(childFragmentManager);
            renterBookMachineAdapter.addFragment(new BookDateAndReasonFragment(specificMachineDetails), "BookDates");
            renterBookMachineAdapter.addFragment(new SiteLocationFragment(), "SiteLocation");
            renterBookMachineAdapter.addFragment(new BookTermAndConditionFragment(specificMachineDetails), "TermAndCondition");
            renterBookMachineAdapter.addFragment(new BookingSummaryFragment(specificMachineDetails), "BookingSummary");
            viewPager.setAdapter(renterBookMachineAdapter);

        }


    }


    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[renterBookMachineAdapter.getCount()];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }

   /* private void callBookingReason() {
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
                editBookingReason.setText(key);
                booking_Reason=jsonObject.getString(key);
                booking_Reason_text=key;
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

    private void callRentalPlanApi() {
        String token= TokenManager.getSessionToken();
        int partyId=specificMachineDetails.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RentalPlan>> rentalPlanCall=apiInterface.getAllPlanForMachine("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<List<RentalPlan>>() {
            @Override
            public void onResponse(Call<List<RentalPlan>> call, Response<List<RentalPlan>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
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
    }*/

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

     /*   long fromCalender = new Calender.getInstance();
        fromCalender.set...// set the from dates
        long toCalender = Calender.getInstance();
        fromCalender.set...// set the to dates

        long diffmili = fromCalender - toCalender;

        long hours = TimeUnit.MILLISECONDS.toHours(diffmili);
        long days = TimeUnit.MILLISECONDS.toDays(diffmili);
        long min = TimeUnit.MILLISECONDS.toMinutes(diffmili);
        long sec = TimeUnit.MILLISECONDS.toSeconds(diffmili);*/


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


}

