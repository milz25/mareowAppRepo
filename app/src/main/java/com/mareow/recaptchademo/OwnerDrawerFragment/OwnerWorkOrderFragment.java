package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.RenterWorkOrderAdapter;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterOfferWorkOrderDetails.OfferDetailsFragment;
import com.mareow.recaptchademo.RenterOfferWorkOrderDetails.WorkOrderDetailsForRenterFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerWorkOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerWorkOrderFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView searchWork;
    AppCompatImageView btnWorkFilter;
    RecyclerView mWorkRecyclerView;
    ProgressDialog progressDialog;

    AppCompatTextView workNoRecordFound;


    List<OfferWorkOrder> WorkOrderList=new ArrayList<>();

    ArrayList<String> StatusList=new ArrayList<>();
    HashMap<String,String> StatusMap=new HashMap<>();


    RenterWorkOrderAdapter ownerWorkOrderAdapter;
    boolean FilterStartDate=false;

    TextInputEditText btnDialogFromDate;
    TextInputEditText btnDialogToDate;
    TextInputEditText btnDialogStatus;

    String workStartDate=null;
    String workEndDate=null;
    String woStatus=null;

    private static final int PERMISSION_REQUEST_CODE = 900;

    public OwnerWorkOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerWorkOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerWorkOrderFragment newInstance(String param1, String param2) {
        OwnerWorkOrderFragment fragment = new OwnerWorkOrderFragment();
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
        View view=inflater.inflate(R.layout.fragment_owner_work_order, container, false);
        OwnerMainActivity.txtOwnerTitle.setText("Work Order");
        initView(view);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait........");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        if(checkPermission()){
            callRenterWorkOrder();
            CallWorkOrderStatusApi();
        } else {
            requestPermission();
        }
        return view;
    }

    private void initView(View view) {

        searchWork=(SearchView)view.findViewById(R.id.owner_workorder_search_view);
        searchWork.setOnQueryTextListener(this);
        btnWorkFilter=(AppCompatImageView)view.findViewById(R.id.owner_workorder_filter);
        btnWorkFilter.setOnClickListener(this);

        workNoRecordFound=(AppCompatTextView)view.findViewById(R.id.owner_workorder_no_recordd_found);

        mWorkRecyclerView=(RecyclerView)view.findViewById(R.id.owner_workorder_recycle);
        mWorkRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mWorkRecyclerView.setHasFixedSize(false);
        mWorkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWorkRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.owner_workorder_filter:
               // ShowFilterDialog();
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }


    private void callRenterWorkOrder() {

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();
        String status="ORDER";

        if (progressDialog!=null)
        progressDialog.show();

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<OfferWorkOrder>> callOfferWorkOrder=apiInterface.getofferWorkOrderForRenter("Bearer "+token,partyId,status);
        callOfferWorkOrder.enqueue(new Callback<List<OfferWorkOrder>>() {
            @Override
            public void onResponse(Call<List<OfferWorkOrder>> call, Response<List<OfferWorkOrder>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    WorkOrderList=response.body();
                    callOfferRecycle();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        workNoRecordFound.setVisibility(View.VISIBLE);
                        mWorkRecyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<OfferWorkOrder>> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callOfferRecycle() {

        if (WorkOrderList.size()>0){

            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {

                    //Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
                  /*  Constants.CHECK_OFFER=false;
                    Fragment offerDetailsFragment = new OfferDetailsFragment();
                    OfferWorkOrder offerWorkOrder=WorkOrderList.get(position);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("offerDetails",offerWorkOrder);
                    offerDetailsFragment.setArguments(bundle);


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_main, offerDetailsFragment);// give your fragment container id in first parameter
                    transaction.addToBackStack("offerDetails");
                    transaction.commitAllowingStateLoss();*/
                    Constants.CHECK_OFFER=false;
                    Fragment workOrderDetailsForRenterFragment = new WorkOrderDetailsForRenterFragment();
                    OfferWorkOrder offerWorkOrder=WorkOrderList.get(position);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("offerDetails",offerWorkOrder);
                    workOrderDetailsForRenterFragment.setArguments(bundle);


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_main, workOrderDetailsForRenterFragment);// give your fragment container id in first parameter
                    transaction.addToBackStack("offerDetails");
                    transaction.commitAllowingStateLoss();

                }
            };

            ownerWorkOrderAdapter=new RenterWorkOrderAdapter(getActivity(),WorkOrderList,listener);
            mWorkRecyclerView.setAdapter(ownerWorkOrderAdapter);

        }
    }


    private void CallWorkOrderStatusApi() {
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> woStatusCall=null;
        woStatusCall=apiInterface.getWorkOrderStatusByRole(partyId);
        woStatusCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String roleResponse = response.body().string();
                        JSONObject jsonObject=new JSONObject(roleResponse);
                        parseJSONObject(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject woResponse) {
        StatusList.clear();
        StatusMap.clear();
        //StatusList.add("All");
        for(Iterator<String> iter = woResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            StatusList.add(key);
            try {
                StatusMap.put(key, woResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       /* int size=StatusList.size();
        workStatusList=new String[size];
        workStatusList[0]="All";
        for (int i=1;i<=StatusList.size();i++){
            workStatusList[i]=StatusList.get(i);
        }*/
    }



    public void  ShowFilterDialog(){

        AppCompatImageView btnImageFromDate;
        AppCompatImageView btnImageToDate;

        final TextInputLayout btnStatusHint;

        AppCompatButton btnDialogReset;
        AppCompatButton btnDialogSave;

        AppCompatImageButton btnDialogClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_custom_layout);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnStatusHint=(TextInputLayout)dialog.findViewById(R.id.filter_dialog_statushint);
        btnStatusHint.setHint("Status");

        btnDialogStatus=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_status);
        btnDialogStatus.setInputType(InputType.TYPE_NULL);
        if (woStatus!=null){
            btnDialogStatus.setText(woStatus);
        }else {
            btnStatusHint.setHint("Status");
        }
        btnDialogStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    callPopwindow();
                }
            }
        });

        btnDialogStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopwindow();
            }
        });


        btnDialogFromDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_fromDate);
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

      /*  btnImageFromDate=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_fromDateimage);
        btnImageFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=true;
                calenderDialogView();
            }
        });*/
        btnDialogToDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_toDate);
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
        /*btnImageToDate=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_toDateImage);
        btnImageToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterStartDate=false;
                calenderDialogView();
            }
        });*/
        btnDialogReset=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_resets);
        btnDialogReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownerWorkOrderAdapter!=null){
                    ownerWorkOrderAdapter.updateList(WorkOrderList);
                }

                btnDialogStatus.setText("Status");
                btnDialogFromDate.setText("From Date");
                btnDialogToDate.setText("To Date");

                woStatus=null;
                workStartDate=null;
                workEndDate=null;

                dialog.dismiss();
            }

        });

        btnDialogSave=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_save);
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (woStatus==null){
                    if (workStartDate==null && workEndDate==null){
                        Toast.makeText(getActivity(), "Require search value.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                List<OfferWorkOrder> newList=new ArrayList<>();
                List<OfferWorkOrder> resultList = new ArrayList<>();
                newList.clear();
                resultList.clear();
                if (woStatus!=null){
                    for (OfferWorkOrder offerWorkOrder:WorkOrderList){
                        if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatusMeaning().equals(woStatus)){
                            newList.add(offerWorkOrder);
                        }
                    }

                }
                if (workStartDate!=null && workEndDate!=null) {

                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date sDate = null;
                    //Date eDate=null;
                    try {
                        sDate = dateFormat.parse(startDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    try {
                        eDate = dateFormat.parse(endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<Date> listDate = getDaysBetweenDates(sDate, eDate);
                    for (int i = 0; i < listDate.size(); i++) {
                        Date btDate = listDate.get(i);
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String newAddDateString = dateFormat1.format(btDate);
                        //searchWorkOrder.setQuery(newAddDateString,true);
                        if (newList.size()>0){
                            for (OfferWorkOrder offerWorkOrder : newList) {
                                if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(newAddDateString) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(newAddDateString)) {
                                    if (!resultList.contains(offerWorkOrder))
                                        resultList.add(offerWorkOrder);
                                }

                            }
                        }else {
                            for (OfferWorkOrder offerWorkOrder : WorkOrderList) {
                                if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(newAddDateString) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(newAddDateString)) {
                                    if (!resultList.contains(offerWorkOrder))
                                        resultList.add(offerWorkOrder);
                                }

                            }
                        }

                    }
                }
                if (workStartDate!=null && workEndDate==null){
                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    if (newList.size()>0){
                        for (OfferWorkOrder offerWorkOrder : newList) {
                            if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(startDate) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(startDate)) {
                                if (!resultList.contains(offerWorkOrder))
                                    resultList.add(offerWorkOrder);
                            }

                        }
                    }else {
                        for (OfferWorkOrder offerWorkOrder : WorkOrderList) {
                            if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(startDate) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(startDate)) {
                                if (!resultList.contains(offerWorkOrder))
                                    resultList.add(offerWorkOrder);
                            }

                        }
                    }
                }

                if (workEndDate!=null && workStartDate==null){
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    if (newList.size()>0){
                        for (OfferWorkOrder offerWorkOrder : newList) {
                            if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(endDate) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(endDate)) {
                                if (!resultList.contains(offerWorkOrder))
                                    resultList.add(offerWorkOrder);
                            }

                        }
                    }else {
                        for (OfferWorkOrder offerWorkOrder : WorkOrderList) {
                            if (offerWorkOrder.getWorkorderDTO().getWorkStartDate().equals(endDate) || offerWorkOrder.getWorkorderDTO().getWorkEndDate().equals(endDate)) {
                                if (!resultList.contains(offerWorkOrder))
                                    resultList.add(offerWorkOrder);
                            }
                        }
                    }
                }

                if (newList.size()>0){
                    if (resultList.size()>0){
                        if (ownerWorkOrderAdapter!=null){
                            ownerWorkOrderAdapter.updateList(resultList);
                        }
                    }else {
                        if (ownerWorkOrderAdapter!=null){
                            ownerWorkOrderAdapter.updateList(newList);
                        }
                    }
                }else if (resultList.size()>0){
                    if (ownerWorkOrderAdapter!=null){
                        ownerWorkOrderAdapter.updateList(resultList);
                    }
                }else if(newList.size()==0 && resultList.size()==0){
                    if (ownerWorkOrderAdapter!=null){
                        ownerWorkOrderAdapter.updateList(newList);
                    }
                }
                dialog.dismiss();
            }
        });

        btnDialogClose=(AppCompatImageButton)dialog.findViewById(R.id.filter_dialog_close);
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    public String convertddMMYYYYtoYYYYMMdd(String dateString){

        String outputDate="";
        SimpleDateFormat input=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=input.parse(dateString);
            outputDate=output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();

        /*Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }*/

        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date  startDate = (Date)formatter.parse(startdate);
        //Date  endDate = (Date)formatter.parse(enddate);

        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =enddate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = startdate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for(int i=0;i<dates.size();i++){
            Date lDate =(Date)dates.get(i);
            //String ds = formatter.format(lDate);
            //System.out.println(" Date is ..." + ds);
        }

        return dates;
    }


    public void calenderDialogView(){
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String dateString;
                if (monthOfYear<10){
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

                if (FilterStartDate){
                    btnDialogFromDate.setText(dateString);
                    workStartDate=btnDialogFromDate.getText().toString().trim();
                }else {
                    btnDialogToDate.setText(dateString);
                    workEndDate=btnDialogToDate.getText().toString();
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime .show();
    }


    public void callPopwindow(){
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),StatusList);
        popupWindow.setAnchorView(btnDialogStatus);
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setWidth(btnDialogStatus.getMeasuredWidth());
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnDialogStatus.setText(StatusList.get(position));
                woStatus=StatusList.get(position);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callRenterWorkOrder();
                    CallWorkOrderStatusApi();
                } else {
                    Snackbar.make(getView(),"Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

}
