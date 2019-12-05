package com.mareow.recaptchademo.RenterFragments;


        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.graphics.Point;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;

        import androidx.appcompat.widget.AppCompatButton;
        import androidx.appcompat.widget.AppCompatImageButton;
        import androidx.appcompat.widget.AppCompatImageView;
        import androidx.appcompat.widget.AppCompatTextView;
        import androidx.appcompat.widget.SearchView;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.DatePicker;
        import android.widget.ListPopupWindow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.mareow.recaptchademo.Activities.RenterMainActivity;
        import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
        import com.mareow.recaptchademo.Adapters.RenterMainCategoryAdapter;
        import com.mareow.recaptchademo.Adapters.RenterMainSubcategoryAdapter;
        import com.mareow.recaptchademo.DataModels.CategoryImage;
        import com.mareow.recaptchademo.DataModels.CommonManufacuter;
        import com.mareow.recaptchademo.DataModels.RenterMachine;
        import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
        import com.mareow.recaptchademo.R;
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
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;
        import java.util.Set;

        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RenterMainHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RenterMainHomeFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Toolbar mToolbar;
    RecyclerView mCategoryRecyclerView;
    RecyclerView mSubCategoryRecyclerView;
    List<RenterMachine> machineListForRenter=new ArrayList<>();

    List<RenterMachine> categoryList=new ArrayList<>();
    ProgressDialog progressDialog;
    Map<String,String> queryMap=new HashMap<>();
    List<RenterMachine> subCategory=new ArrayList<>();

    String workStartDate=null;
    String workEndDate=null;
    String worklocation=null;


    SearchView mMachineSearch;
    AppCompatImageView mMachineFilter;

    TextInputEditText btnDialogFromDate;
    TextInputEditText btnDialogToDate;
    TextInputEditText btnDialogLocation;
    TextInputEditText btnCategory;
    TextInputEditText btnSubCategory;
    TextInputEditText btnManufacturer;
    TextInputEditText btnModel;


    boolean FilterStartDate=false;

    boolean CATEGORY=false;
    boolean SUBCATEGORY=false;
    boolean MANUFACTURER=false;
    boolean MODEL=false;

    AppCompatTextView renterName;

    List<String> segmentList=new ArrayList<>();

    List<CategoryImage> categoryImageList=new ArrayList<>();

    HashMap<String,String> categoryMap=new HashMap<>();
    HashMap<String,String> subCategoryMap=new HashMap<>();
    HashMap<String,String> manufacturerMap=new HashMap<>();

    List<CommonManufacuter> manufacuterList=new ArrayList<>();

    String CATEGORY_CODE=null;
    String SUBCATEGORY_CODE=null;
    String MANUFACUTER_CODE=null;


    RenterMainSubcategoryAdapter subadapter;
    boolean [] SELECTED_CATEGORY;
    public RenterMainHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RenterMainHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RenterMainHomeFragment newInstance(String param1, String param2) {
        RenterMainHomeFragment fragment = new RenterMainHomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_renter_home2, container, false);
        RenterMainActivity.txtRenterTitle.setText("Home");
        Set<String> set = TokenManager.getUserDetailsPreference().getStringSet(Constants.PERFERED_SEGEMNT, null);
        segmentList.addAll(set);

        callCategoryData();
        callFilterCategoryData();
        callFilterManufacturerData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        queryMap.clear();

        workStartDate=null;
        workEndDate=null;
        worklocation=null;

        machineListForRenter.clear();
        categoryList.clear();
        subCategory.clear();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...........");

        Date today=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String todayString=simpleDateFormat.format(today);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(today);
        c1.add(Calendar.DATE, 4);
        Date endDate = c1.getTime();
        String endDateString=simpleDateFormat.format(endDate);


        workStartDate=todayString;
        workEndDate=endDateString;

        Constants.MACHINE_BOOK_START_DATE=workStartDate;
        Constants.MACHINE_BOOK_END_DATE=workEndDate;


        callRenterMaichineForMainPage();

        mCategoryRecyclerView=(RecyclerView)view.findViewById(R.id.renter_main_category_recycle);
        mCategoryRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mCategoryRecyclerView.setHasFixedSize(false);
        mCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));



        mSubCategoryRecyclerView=(RecyclerView)view.findViewById(R.id.renter_main_subcategory_recycle);
        mSubCategoryRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mSubCategoryRecyclerView.setHasFixedSize(false);
        mSubCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSubCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMachineSearch=(SearchView)view.findViewById(R.id.renter_main_search_view);
        //mMachineSearch.setInputType(InputType.TYPE_NULL);
        //mMachineSearch.setOnClickListener(this);
        //getActivity().onSearchRequested();

        // SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        //   mMachineSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
      /* mMachineSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus){
                   Intent intent=new Intent(getActivity(), SearchableActivity.class);
                   startActivity(intent);
               }else {
                   Toast.makeText(getActivity(), "srsdsdd", Toast.LENGTH_SHORT).show();
               }
           }
       });*/
        mMachineSearch.setOnQueryTextListener(this);

        mMachineFilter=(AppCompatImageView)view.findViewById(R.id.renter_main_filter);
        mMachineFilter.setOnClickListener(this);

        renterName=(AppCompatTextView)view.findViewById(R.id.renter_main_machineTag);
        renterName.setText("Which kind of machine are you looking for...");



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.renter_main_filter:
                callFliterDialog();
                break;
           /* case R.id.renter_main_search_view:
                ArrayList<RenterMachine> allmachines=new ArrayList<>();
                allmachines.addAll(machineListForRenter);
               Intent intent=new Intent(getActivity(),SearchableActivity.class);
               intent.putExtra("AllMachineList",allmachines);
               startActivity(intent);
               break;*/
        }
    }

    private void callRenterMaichineForMainPage(){

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        progressDialog.show();
        queryMap.clear();
        queryMap.put("favPartyId",String.valueOf(partyId));
        if (workStartDate!=null){
            queryMap.put("startDate",workStartDate);
        }
        if (workEndDate!=null){
            queryMap.put("endDate",workEndDate);
        }

        if (worklocation!=null){
            queryMap.put("location",worklocation);
        }

        if (CATEGORY_CODE!=null){
            queryMap.put("category",CATEGORY_CODE);
        }
        if (SUBCATEGORY_CODE!=null){
            queryMap.put("subCategory",SUBCATEGORY_CODE);
        }
        if (MANUFACUTER_CODE!=null){
            queryMap.put("manufacturer",MANUFACUTER_CODE);
        }

        String token= new TokenManager(getActivity()).getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RenterMachine>> machineCall=apiInterface.getMachineForRenter("Bearer "+token,queryMap);
        machineCall.enqueue(new Callback<List<RenterMachine>>() {
            @Override
            public void onResponse(Call<List<RenterMachine>> call, Response<List<RenterMachine>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    machineListForRenter.clear();
                    machineListForRenter=response.body();
                    filterMachineData(machineListForRenter);
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
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
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


    public void showSnackbar(String msg){

        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }


    public void filterMachineData(List<RenterMachine> machineList){

        subCategory.clear();
        if (!Constants.CATEGORY_CHECK) {
            categoryList.clear();

            for (int i = 0; i < machineList.size(); i++) {
                boolean check = false;
                if (categoryList.size() > 0) {
                    for (int j = 0; j < categoryList.size(); j++) {
                        if (categoryList.get(j).getCategoryName().equals(machineList.get(i).getCategoryName())) {
                            check = true;
                        }
                    }
                    if (!check) {
                        categoryList.add(machineList.get(i));
                    }
                } else {
                    categoryList.add(machineList.get(i));
                }
            }

            SELECTED_CATEGORY=new boolean[categoryList.size()];
        }

        for (int i = 0; i< machineList.size(); i++){
            boolean check=false;
            if (subCategory.size()>0){
                for (int j=0;j<subCategory.size();j++){
                    if (subCategory.get(j).getSubCategoryName().equals(machineList.get(i).getSubCategoryName())){
                        check=true;
                    }
                }
                if (!check){
                    subCategory.add(machineList.get(i));
                }
            }else {
                subCategory.add(machineList.get(i));
            }
        }


        if (!Constants.CATEGORY_CHECK){
            setCategoryRecycleAdapter();
        }

        setSubCategoryRecyclerViewAdapter();

    }

    public void setCategoryRecycleAdapter(){

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                for (int i=0;i<SELECTED_CATEGORY.length;i++){
                    if (i==position){
                        if (SELECTED_CATEGORY[i]){
                            SELECTED_CATEGORY[i]=false;
                            //Constants.CATEGORY_CHECK=false;
                        }else {
                            SELECTED_CATEGORY[i]=true;
                            Constants.CATEGORY_CHECK=true;
                        }
                    }else {
                        SELECTED_CATEGORY[i]=false;
                        // Constants.CATEGORY_CHECK=false;
                    }
                }

                if (Constants.CATEGORY_CHECK==true){

                    RenterMachine machine=categoryList.get(position);
                    List<RenterMachine> showAllMachine=new ArrayList<>();
                    showAllMachine.clear();
                    for (int i=0;i<machineListForRenter.size();i++){

                        if (machine.getCategoryName().equals(machineListForRenter.get(i).getCategoryName())){
                            showAllMachine.add(machineListForRenter.get(i));
                        }

                    }

                    filterMachineData(showAllMachine);
                }
               /* RenterMachine machine=categoryList.get(position);
                List<RenterMachine> showAllMachine=new ArrayList<>();
                showAllMachine.clear();
                for (int i=0;i<machineListForRenter.size();i++){

                    if (machine.getCategoryName().equals(machineListForRenter.get(i).getCategoryName())){
                        showAllMachine.add(machineListForRenter.get(i));
                    }

                }*/
                if (Constants.CATEGORY_CHECK==false)
                    filterMachineData(machineListForRenter);

                Constants.CATEGORY_CHECK=false;
              /*  Fragment showAllMachineForRenterFragment = new ShowAllMachineForRenterFragment(showAllMachine);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, showAllMachineForRenterFragment);// give your fragment container id in first parameter
                transaction.addToBackStack("MachineMain");
                transaction.commitAllowingStateLoss();*/
            }
        };

        Collections.sort(categoryList, new Comparator<RenterMachine>() {
            @Override
            public int compare(RenterMachine u1, RenterMachine u2) {
                return u1.getCategoryName().compareToIgnoreCase(u2.getCategoryName());
            }
        });

        RenterMainCategoryAdapter catadapter=new RenterMainCategoryAdapter(getActivity(),categoryList,categoryImageList,listener);
        mCategoryRecyclerView.setAdapter(catadapter);


        /*SelectionTracker selectionTracker = new SelectionTracker.Builder<>(
                "my-category-selection",
                mCategoryRecyclerView,
                new MachineKeyProvider(1, categoryList),
                new MachineLookup(mCategoryRecyclerView),
                StorageStrategy.createLongStorage()
        )
                .withOnDragInitiatedListener(new OnDragInitiatedListener() {
                    @Override
                    public boolean onDragInitiated(@NonNull MotionEvent e) {
                       // Log.d(TAG, "onDragInitiated");
                        return true;
                    }
                }).build();

        catadapter.setSelectionTracker(selectionTracker);*/

    }


    public void setSubCategoryRecyclerViewAdapter(){

        Collections.sort(subCategory, new Comparator<RenterMachine>() {
            @Override
            public int compare(RenterMachine u1, RenterMachine u2) {
                return u1.getCategoryName().compareToIgnoreCase(u2.getCategoryName());
            }
        });

        subadapter=new RenterMainSubcategoryAdapter(getActivity(),subCategory,machineListForRenter);
        mSubCategoryRecyclerView.setAdapter(subadapter);
    }


    /*@Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput=newText.toLowerCase();
        List<RenterMachine> newList=new ArrayList<>();
        for (RenterMachine renterMachine:machineListForRenter){
                if (renterMachine.getMachineName().toLowerCase().startsWith(userInput)){
                    newList.add(renterMachine);
                }

        }
        if (subadapter!=null){
            subadapter.updateList(newList);
        }

        return false;
    }*/

    private void callFliterDialog(){

        AppCompatButton btnDialogReset;
        AppCompatButton btnDialogSave;

        AppCompatImageButton btnDialogClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_filter_for_renter);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btnCategory=(TextInputEditText)dialog.findViewById(R.id.renter_filter_dailog_category);
        if (CATEGORY_CODE!=null){
            for (Map.Entry<String,String> entry : categoryMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(CATEGORY_CODE)){
                    btnCategory.setText(key);
                    break;
                }
            }
        }
        btnCategory.setInputType(InputType.TYPE_NULL);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CATEGORY=true;
                SUBCATEGORY=false;
                MANUFACTURER=false;
                MODEL=false;
                callCustomeDialog(categoryMap,0,"Category");
            }
        });
        btnCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    CATEGORY=true;
                    SUBCATEGORY=false;
                    MANUFACTURER=false;
                    MODEL=false;
                    callCustomeDialog(categoryMap,0,"Category");
                }
            }
        });
        btnSubCategory=(TextInputEditText)dialog.findViewById(R.id.renter_filter_dailog_subcategory);
        if (SUBCATEGORY_CODE!=null){
            for (Map.Entry<String,String> entry : subCategoryMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(SUBCATEGORY_CODE)){
                    btnSubCategory.setText(key);
                    break;
                }
            }
        }
        btnSubCategory.setInputType(InputType.TYPE_NULL);
        btnSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CATEGORY=false;
                SUBCATEGORY=true;
                MANUFACTURER=false;
                MODEL=false;
                callCustomeDialog(subCategoryMap,1,"SubCategory");
            }
        });
        btnSubCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    CATEGORY=false;
                    SUBCATEGORY=true;
                    MANUFACTURER=false;
                    MODEL=false;
                    callCustomeDialog(subCategoryMap,1,"SubCategory");
                }
            }
        });

        btnManufacturer=(TextInputEditText)dialog.findViewById(R.id.renter_filter_dailog_manufacture);
        if (MANUFACUTER_CODE!=null){
            for (Map.Entry<String,String> entry : manufacturerMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value.equals(MANUFACUTER_CODE)){
                    btnManufacturer.setText(key);
                    break;
                }
            }
        }
        btnManufacturer.setInputType(InputType.TYPE_NULL);
        btnManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CATEGORY=false;
                SUBCATEGORY=false;
                MANUFACTURER=true;
                MODEL=false;
                callCustomeDialog(manufacturerMap,2,"Manufacturer");
            }
        });
        btnManufacturer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    CATEGORY=false;
                    SUBCATEGORY=false;
                    MANUFACTURER=true;
                    MODEL=false;
                    callCustomeDialog(manufacturerMap,2,"Manufacturer");
                }
            }
        });

        /* btnModel=(TextInputEditText)dialog.findViewById(R.id.renter_filter_dailog_model);
        btnModel.setInputType(InputType.TYPE_NULL);
        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CATEGORY=false;
                SUBCATEGORY=false;
                MANUFACTURER=false;
                MODEL=true;
                callPopwindow();
            }
        });
        btnModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    CATEGORY=false;
                    SUBCATEGORY=false;
                    MANUFACTURER=false;
                    MODEL=true;
                    callPopwindow();
                }
            }
        });*/

        btnDialogLocation=(TextInputEditText) dialog.findViewById(R.id.renter_filter_dailog_location);
        if (worklocation!=null){
            btnDialogLocation.setText(worklocation);
        }

        btnDialogFromDate=(TextInputEditText) dialog.findViewById(R.id.renter_filter_dailog_startDate);
        btnDialogFromDate.setInputType(InputType.TYPE_NULL);
        btnDialogFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=true;
                calenderDialogView();
            }
        });
        btnDialogFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    FilterStartDate=true;
                    calenderDialogView();
                }
            }
        });
        if (workStartDate!=null){
            btnDialogFromDate.setText(workStartDate);
            btnDialogFromDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }

       /* btnImageFromDate=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_fromDateimage);
        btnImageFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterStartDate=true;
                calenderDialogView();

            }
        });
*/
        btnDialogToDate=(TextInputEditText) dialog.findViewById(R.id.renter_filter_dailog_endDate);
        btnDialogToDate.setInputType(InputType.TYPE_NULL);
        btnDialogToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=false;
                calenderDialogView();
            }
        });

        btnDialogToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    FilterStartDate=false;
                    calenderDialogView();
                }
            }
        });
        if (workEndDate!=null){
            btnDialogToDate.setText(workEndDate);
            btnDialogToDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }
      /*  btnImageToDate=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_toDateImage);
        btnImageToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=false;
                calenderDialogView();
            }
        });*/
        btnDialogReset=(AppCompatButton) dialog.findViewById(R.id.renter_filter_dailog_reset);
        btnDialogReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnCategory.setText("");
                btnSubCategory.setText("");
                btnManufacturer.setText("");
                btnDialogLocation.setText("");
                btnDialogFromDate.setText("");
                btnDialogToDate.setText("");


                worklocation=null;
                workStartDate=null;
                workEndDate=null;
                CATEGORY_CODE=null;
                SUBCATEGORY_CODE=null;
                MANUFACUTER_CODE=null;

                mMachineFilter.setImageResource(R.drawable.filter_empty);
                dialog.dismiss();

                Date today=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                String todayString=simpleDateFormat.format(today);

                Calendar c1 = Calendar.getInstance();
                c1.setTime(today);
                c1.add(Calendar.DATE, 5);
                Date endDate = c1.getTime();
                String endDateString=simpleDateFormat.format(endDate);


                workStartDate=todayString;
                workEndDate=endDateString;

                Constants.MACHINE_BOOK_START_DATE=workStartDate;
                Constants.MACHINE_BOOK_END_DATE=workEndDate;


                callRenterMaichineForMainPage();


            }

        });

        btnDialogSave=(AppCompatButton) dialog.findViewById(R.id.renter_filter_dailog_save);
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workStartDate=btnDialogFromDate.getText().toString().trim();
                workEndDate=btnDialogToDate.getText().toString().trim();
                worklocation=btnDialogLocation.getText().toString().trim();



                Constants.MACHINE_BOOK_START_DATE=workStartDate;
                Constants.MACHINE_BOOK_END_DATE=workEndDate;


                callRenterMaichineForMainPage();
                dialog.dismiss();

            }
        });

        btnDialogClose=(AppCompatImageButton)dialog.findViewById(R.id.renter_filter_dailog_close);
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    public void calenderDialogView(){


        final Calendar newCalendar = Calendar.getInstance();
        long todayTime=newCalendar.getTime().getTime();
        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                String dateString;
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

                if (FilterStartDate){

                    if (!btnDialogToDate.getText().toString().isEmpty()){
                        String toDateString=btnDialogToDate.getText().toString();

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
                            btnDialogFromDate.setText(dateString);
                            workStartDate=btnDialogFromDate.getText().toString().trim();
                        }else {
                            // showSnackbar("Selected Date is greater than to Date :"+toDateString);
                            Toast.makeText(getActivity(), "Selected Date is greater than to Date :"+toDateString, Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        btnDialogFromDate.setText(dateString);
                        workStartDate=btnDialogFromDate.getText().toString().trim();
                    }

                }else {
                    if (!btnDialogFromDate.getText().toString().isEmpty()){
                        String fromDateString=btnDialogFromDate.getText().toString();

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
                            btnDialogToDate.setText(dateString);
                            workEndDate=btnDialogToDate.getText().toString();
                        }else {
                            // showSnackbar("Selected Date is Lesser than from Date :"+fromDateString);
                            Toast.makeText(getActivity(), "Selected Date is Lesser than from Date :"+fromDateString, Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        btnDialogToDate.setText(dateString);
                        workEndDate=btnDialogToDate.getText().toString();
                    }
                }

                mMachineFilter.setImageResource(R.drawable.filter_with_value);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(todayTime);
        StartTime .show();
    }

    private void callCategoryData() {

        StringBuilder segment=new StringBuilder();

        for (int i=0;i<segmentList.size();i++){
            if (i==segmentList.size()-1){
                segment.append(segmentList.get(i));
                break;
            }
            segment.append(segmentList.get(i)+",");
        }
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        //int partyid=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<List<CategoryImage>> machineCall=apiInterface.getCategoryImages(segment.toString());
        machineCall.enqueue(new Callback<List<CategoryImage>>() {
            @Override
            public void onResponse(Call<List<CategoryImage>> call, Response<List<CategoryImage>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    categoryImageList=response.body();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<CategoryImage>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callCustomeDialog(HashMap<String, String> hashMap, int check,String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();
        for (String key : hashMap.keySet()) {
            listData.add(key);
        }
        Collections.sort(listData);
        callPopwindow(tilte,listData);
    }


    public void callPopwindow(String title,ArrayList<String> listData){

        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());
        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        if (CATEGORY){
            popupWindow.setAnchorView(btnCategory);
            popupWindow.setWidth(btnCategory.getMeasuredWidth());
        }
        if (SUBCATEGORY){
            popupWindow.setAnchorView(btnSubCategory);
            popupWindow.setWidth(btnSubCategory.getMeasuredWidth());
        }
        if (MANUFACTURER){
            popupWindow.setAnchorView(btnManufacturer);
            popupWindow.setWidth(btnManufacturer.getMeasuredWidth());
        }
        if (MODEL){
            popupWindow.setAnchorView(btnModel);
            popupWindow.setWidth(btnModel.getMeasuredWidth());
        }
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (CATEGORY){
                    btnCategory.setText(listData.get(position));
                    CATEGORY_CODE=categoryMap.get(listData.get(position));
                }
                if (SUBCATEGORY){
                    btnSubCategory.setText(listData.get(position));
                    SUBCATEGORY_CODE=subCategoryMap.get(listData.get(position));
                }
                if (MANUFACTURER){
                    btnManufacturer.setText(listData.get(position));
                    MANUFACUTER_CODE=manufacturerMap.get(listData.get(position));
                }


                mMachineFilter.setImageResource(R.drawable.filter_with_value);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    private void callFilterCategoryData() {
        StringBuilder segment=new StringBuilder();
        for (int i=0;i<segmentList.size();i++){
            if (i==segmentList.size()-1){
                segment.append(segmentList.get(i));
                break;
            }
            segment.append(segmentList.get(i)+",");
        }
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        //int partyid=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<ResponseBody> machineCall=apiInterface.getMultipleSegmentBaseCategory("PREFERED_SEGMENT",segment.toString());
        machineCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String roleResponse = response.body().string();
                        JSONObject jsonObject=new JSONObject(roleResponse);
                        parseJSONObject(jsonObject,0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callFilterSubcategoryData();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackbar(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void callFilterSubcategoryData() {
        StringBuilder segment=new StringBuilder();

        List<String> list=new ArrayList<>();
        for (Map.Entry<String,String> entry : categoryMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            list.add(value);
        }

        for (int i=0;i<list.size();i++){
            if (1==list.size()-1){
                segment.append(list.get(i));
                break;
            }
            segment.append(list.get(i)+",");
        }

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        //int partyid=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<ResponseBody> machineCall=apiInterface.getMultipleSegmentBaseCategory("CATEGORY",segment.toString());
        machineCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String roleResponse = response.body().string();
                        JSONObject jsonObject=new JSONObject(roleResponse);
                        parseJSONObject(jsonObject,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
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
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callFilterManufacturerData() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        //int partyid=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<List<CommonManufacuter>> machineCall=apiInterface.getCommonManufacture();
        machineCall.enqueue(new Callback<List<CommonManufacuter>>() {
            @Override
            public void onResponse(Call<List<CommonManufacuter>> call, Response<List<CommonManufacuter>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    manufacuterList=response.body();
                    for (int i=0;i<manufacuterList.size();i++){
                        manufacturerMap.put(manufacuterList.get(i).getLabel(),manufacuterList.get(i).getValue());
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
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<CommonManufacuter>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
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
                categoryMap = map;
                break;
            case 1:
                subCategoryMap = map;
                break;
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //use the query to search your data somehow
        // Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        String userInput=newText.toLowerCase();
        List<RenterMachine> newList=new ArrayList<>();

        for (RenterMachine renterMachine:machineListForRenter){
            if (renterMachine.getMachineName().toLowerCase().startsWith(userInput)){
                newList.add(renterMachine);
            }
        }
        subCategory.clear();
        for (int i = 0; i< newList.size(); i++){
            boolean check=false;
            if (subCategory.size()>0){
                for (int j=0;j<subCategory.size();j++){
                    if (subCategory.get(j).getSubCategoryName().equals(newList.get(i).getSubCategoryName())){
                        check=true;
                    }
                }
                if (!check){
                    subCategory.add(newList.get(i));
                }
            }else {
                subCategory.add(newList.get(i));
            }
        }

        if (subadapter!=null){
           subadapter.updateList(subCategory,newList);
        }

        return false;
    }
}
