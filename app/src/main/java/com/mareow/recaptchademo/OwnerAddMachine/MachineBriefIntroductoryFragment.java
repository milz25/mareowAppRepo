package com.mareow.recaptchademo.OwnerAddMachine;


import android.app.Activity;
import android.app.DatePickerDialog;
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

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineBriefIntroductoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineBriefIntroductoryFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MACHINE_IMAGE_PATH = 007;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editMachineName;
    TextInputEditText editState;
    TextInputEditText editCity;
    TextInputEditText editOdometerReading;
    TextInputEditText editBriefIntro;
    TextInputEditText editAvailableDate;
    TextInputEditText editDistrict;
    TextInputEditText editPostalCode;
    TextInputEditText editMachineRun;
    TextInputEditText editLoadCapacity;

    AppCompatImageView btnChooseFile;
    AppCompatTextView txtNoFileSelected;

    AppCompatImageView btnAvailableDate;


    HashMap<String,String>  stateMap = new HashMap<>();
    HashMap<String, String> districtMap = new HashMap<>();
    HashMap<String, String> cityMap = new HashMap<>();
    HashMap<String, String> postalcodeMap = new HashMap<>();
    HashMap<String, String> loadCapacityMap = new HashMap<>();


    public static String STATE_CODE=null;
    public static String DISTRICT_CODE=null;
    public  String CITY_CODE=null;
    public static String CITY_CURRENT_LOCATION;
    public String POSTAL_CODE_CODE=null;
    public static String POSTAL_CURRENT_CODE=null;
    public static String LOAD_CAPACITY_CODE=null;
    public static String BRIFE_INTRO=null;
    public static String MACHINE_RUN=null;
    public static String ODOMETER_READTING=null;
    public static String MACHINE_NAME=null;
    String LOAD_CAPACITY="LOAD_CAPACITY";

    public static String AVAILABLE_DATE=null;

    public final int MACHINE_PITCHURE_DOC=510;
    public final int MACHINE_PITCHURE_CAMERA=511;

    ApiInterface apiInterface;
    String token;

    String imageFilePath;
    Uri photoURI;

    public static List<String> mMachineImagePath=new ArrayList<>();
    public MachineBriefIntroductoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineBriefIntroductoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineBriefIntroductoryFragment newInstance(String param1, String param2) {
        MachineBriefIntroductoryFragment fragment = new MachineBriefIntroductoryFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine_brief_introductory, container, false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        token=TokenManager.getSessionToken();
        callGetStateData();
        callGetLoadCapacity();
        initView(view);
        return view;
    }

    private void initView(View view) {

        editMachineName=(TextInputEditText)view.findViewById(R.id.OMD_BID_machineName);
        if (MACHINE_NAME!=null){
            editMachineName.setText(MACHINE_NAME);
        }
        editMachineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              MACHINE_NAME=s.toString();
            }
        });

        editState=(TextInputEditText)view.findViewById(R.id.OMD_BID_state);
        if (STATE_CODE!=null){
            editState.setText(STATE_CODE);
        }
        editState.setInputType(InputType.TYPE_NULL);
        editState.setOnClickListener(this);
        editState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.STATE = true;
                    Constants.DISTRICT = false;
                    Constants.CITY = false;
                    Constants.POSTAL_CODE = false;
                    Constants.LOAD_CAPACITY=false;
                    callCustomeDialog(stateMap, 0,"State");
                }
            }
        });



        editCity=(TextInputEditText)view.findViewById(R.id.OMD_BID_city);
        if (CITY_CURRENT_LOCATION!=null){
            editCity.setText(CITY_CURRENT_LOCATION);
        }
        editCity.setInputType(InputType.TYPE_NULL);
        editCity.setOnClickListener(this);
        editCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.STATE = false;
                    Constants.DISTRICT = false;
                    Constants.CITY = true;
                    Constants.POSTAL_CODE = false;
                    Constants.LOAD_CAPACITY=false;
                    callCustomeDialog(cityMap, 2,"City");
                }
            }
        });

        editDistrict=(TextInputEditText)view.findViewById(R.id.OMD_BID_district);
        if (DISTRICT_CODE!=null){
            editDistrict.setText(DISTRICT_CODE);
        }
        editDistrict.setInputType(InputType.TYPE_NULL);
        editDistrict.setOnClickListener(this);
        editDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.STATE = false;
                    Constants.DISTRICT = true;
                    Constants.CITY = false;
                    Constants.POSTAL_CODE = false;
                    Constants.LOAD_CAPACITY=false;
                    callCustomeDialog(districtMap, 1,"District");
                }
            }
        });

        editPostalCode=(TextInputEditText)view.findViewById(R.id.OMD_BID_postalcode);
        if (POSTAL_CURRENT_CODE!=null){
            editPostalCode.setText(POSTAL_CURRENT_CODE);
        }
        editPostalCode.setInputType(InputType.TYPE_NULL);
        editPostalCode.setOnClickListener(this);
        editPostalCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.STATE = false;
                    Constants.DISTRICT = false;
                    Constants.CITY = false;
                    Constants.POSTAL_CODE = true;
                    Constants.LOAD_CAPACITY=false;
                    callCustomeDialog(postalcodeMap, 3,"PostalCode");
                }
            }
        });



        btnAvailableDate=(AppCompatImageView)view.findViewById(R.id.OMD_BID_btnavailabledate);
        btnAvailableDate.setOnClickListener(this);
        editAvailableDate=(TextInputEditText)view.findViewById(R.id.OMD_BID_availabledate);
        if (AVAILABLE_DATE!=null){
            editAvailableDate.setText(Util.convertYYYYddMMtoDDmmYYYY(AVAILABLE_DATE));
        }
        editAvailableDate.setInputType(InputType.TYPE_NULL);
        editAvailableDate.setOnClickListener(this);
        editAvailableDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideKeyboardFrom(getContext(),v);
                    showDateDialog();
                }
            }
        });

        editOdometerReading=(TextInputEditText)view.findViewById(R.id.OMD_BID_odometer);
        if (ODOMETER_READTING!=null){
            editOdometerReading.setText(ODOMETER_READTING);
        }
        editOdometerReading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              ODOMETER_READTING=s.toString();
            }
        });
        editBriefIntro=(TextInputEditText)view.findViewById(R.id.OMD_BID_briefintro);
        if (BRIFE_INTRO!=null){
            editBriefIntro.setText(BRIFE_INTRO);
        }
        editBriefIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                BRIFE_INTRO=s.toString();
            }
        });
        editMachineRun=(TextInputEditText)view.findViewById(R.id.OMD_BID_machine_run);
        if (MACHINE_RUN!=null){
            editMachineRun.setText(MACHINE_RUN);
        }
        editMachineRun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               MACHINE_RUN=s.toString();
            }
        });


        editLoadCapacity=(TextInputEditText)view.findViewById(R.id.OMD_BID_loadcapacity);
        if (LOAD_CAPACITY_CODE!=null){
           editLoadCapacity.setText(LOAD_CAPACITY_CODE);
        }
        editLoadCapacity.setInputType(InputType.TYPE_NULL);
        editLoadCapacity.setOnClickListener(this);
        editLoadCapacity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideKeyboardFrom(getContext(),v);
                    Constants.STATE = false;
                    Constants.DISTRICT = false;
                    Constants.CITY = false;
                    Constants.POSTAL_CODE = false;
                    Constants.LOAD_CAPACITY=true;
                    callCustomeDialog(loadCapacityMap, 4,"Load Capacity");
                }
            }
        });


        btnChooseFile=(AppCompatImageView)view.findViewById(R.id.OMD_BID_choosefile);
        btnChooseFile.setOnClickListener(this);
        txtNoFileSelected=(AppCompatTextView)view.findViewById(R.id.OMD_BID_no_file_selected);

        if (mMachineImagePath.size()>0){
            txtNoFileSelected.setText("");
            for (int i=0;i<mMachineImagePath.size();i++){
                if (mMachineImagePath.get(i)!=null){
                    txtNoFileSelected.append(FileUtils.getFileName(getActivity(),Uri.parse(mMachineImagePath.get(i)))+",");
                }

            }
        }


        if (Constants.MACHINE_UPDATE){

            editAvailableDate.setEnabled(false);
            editCity.setEnabled(false);
            editPostalCode.setEnabled(false);

            editState.setVisibility(View.GONE);
            editDistrict.setVisibility(View.GONE);

            editOdometerReading.setEnabled(false);
            editMachineRun.setEnabled(false);


        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.OMD_BID_availabledate:
                hideKeyboardFrom(getContext(),v);
                showDateDialog();
                break;
            case R.id.OMD_BID_btnavailabledate:
                hideKeyboardFrom(getContext(),v);
                showDateDialog();
                break;
            case R.id.OMD_BID_state:
                Constants.STATE = true;
                Constants.DISTRICT = false;
                Constants.CITY = false;
                Constants.POSTAL_CODE = false;
                Constants.LOAD_CAPACITY=false;
                callCustomeDialog(stateMap, 0,"State");
                break;
            case R.id.OMD_BID_district:
                Constants.STATE = false;
                Constants.DISTRICT = true;
                Constants.CITY = false;
                Constants.POSTAL_CODE = false;
                Constants.LOAD_CAPACITY=false;
                callCustomeDialog(districtMap, 1,"District");
                break;
            case R.id.OMD_BID_city:
                Constants.STATE = false;
                Constants.DISTRICT = false;
                Constants.CITY = true;
                Constants.POSTAL_CODE = false;
                Constants.LOAD_CAPACITY=false;
                callCustomeDialog(cityMap, 2,"City");
                break;
            case R.id.OMD_BID_postalcode:
                Constants.STATE = false;
                Constants.DISTRICT = false;
                Constants.CITY = false;
                Constants.POSTAL_CODE = true;
                Constants.LOAD_CAPACITY=false;
                callCustomeDialog(postalcodeMap, 3,"PostalCode");
                break;
            case R.id.OMD_BID_loadcapacity:
                hideKeyboardFrom(getContext(),v);
                Constants.STATE = false;
                Constants.DISTRICT = false;
                Constants.CITY = false;
                Constants.POSTAL_CODE = false;
                Constants.LOAD_CAPACITY=true;
                callCustomeDialog(loadCapacityMap, 4,"Load Capacity");
                break;
            case R.id.OMD_BID_choosefile:
               showAlertDialog();
                break;
        }
    }

    private void callCustomeDialog(HashMap<String, String> hashMap, int check, String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();
        if (!Constants.LOAD_CAPACITY){
            for (String key : hashMap.keySet()) {
                listData.add(hashMap.get(key));
            }
        }else {
            for (String key : hashMap.keySet()) {
                listData.add(key);
            }
        }


        Collections.sort(listData);
        showPopWindowForView(tilte,listData);
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

               editAvailableDate.setText(dateString);
                AVAILABLE_DATE=dateString;

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(todayTime);
        StartTime .show();
    }


    private void showPopWindowForView(String tilte, ArrayList<String> listData) {

        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        if (Constants.STATE){
            popupWindow.setAnchorView(editState);
            popupWindow.setWidth(editState.getMeasuredWidth());
        }else if (Constants.DISTRICT){
            popupWindow.setAnchorView(editDistrict);
            popupWindow.setWidth(editDistrict.getMeasuredWidth());
        }else if (Constants.CITY){
            popupWindow.setAnchorView(editCity);
            popupWindow.setWidth(editCity.getMeasuredWidth());
        }else if (Constants.POSTAL_CODE){
            popupWindow.setAnchorView(editPostalCode);
            popupWindow.setWidth(editPostalCode.getMeasuredWidth());
        }else if (Constants.LOAD_CAPACITY){
            popupWindow.setAnchorView(editLoadCapacity);
            popupWindow.setWidth(editLoadCapacity.getMeasuredWidth());
        }

        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Constants.STATE){
                    editState.setText(listData.get(position));

                    editDistrict.setText("");
                    editCity.setText("");
                    editPostalCode.setText("");
                    for(Map.Entry entry: stateMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            STATE_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }

                   // STATE_CODE=stateMap.get(listData.get(position));
                    districtMap.clear();
                    cityMap.clear();
                    postalcodeMap.clear();
                    callGetDistrictData(STATE_CODE);
                }
                if (Constants.DISTRICT){
                    editDistrict.setText(listData.get(position));

                    editCity.setText("");
                    editPostalCode.setText("");

                    for(Map.Entry entry: districtMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            DISTRICT_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }
                   // DISTRICT_CODE=districtMap.get(listData.get(position));

                    cityMap.clear();
                    postalcodeMap.clear();
                    callGetCityData(DISTRICT_CODE);
                }
                if (Constants.CITY){
                    editCity.setText(listData.get(position));

                    editPostalCode.setText("");

                    for(Map.Entry entry: cityMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            CITY_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }
                    CITY_CURRENT_LOCATION=listData.get(position);
                   // CITY_CODE=cityMap.get(listData.get(position));
                    callGetPostalCodeData(CITY_CODE);
                }
                if (Constants.POSTAL_CODE){
                    editPostalCode.setText(listData.get(position));


                    for(Map.Entry entry: postalcodeMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            POSTAL_CODE_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }
                    POSTAL_CURRENT_CODE=listData.get(position);
                   //POSTAL_CODE_CODE=postalcodeMap.get(listData.get(position));

                }
                if (Constants.LOAD_CAPACITY){
                    editLoadCapacity.setText(listData.get(position));

                   /* for(Map.Entry entry: loadCapacityMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            LOAD_CAPACITY_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }*/
                    LOAD_CAPACITY_CODE=loadCapacityMap.get(listData.get(position));
                }

                popupWindow.dismiss();

            }
        });

        popupWindow.show();
    }

    private void callGetStateData() {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> stateCall = apiInterface.getStateOFIndiaList("Bearer "+token);
        stateCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetDistrictData(String key) {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> districCall = apiInterface.getDistrictList("Bearer "+token,key);
        districCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetCityData(String key) {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> cityCall = apiInterface.getCityList("Bearer "+token,key);
        cityCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetPostalCodeData(String key) {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> postalCodeCall = apiInterface.getPostalCodeList("Bearer "+token,key);
        postalCodeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


       public void callGetLoadCapacity(){
           TokenManager.showProgressDialog(getActivity());
           Call<ResponseBody> loadCapacity = apiInterface.getCommonLookUp(LOAD_CAPACITY);
           loadCapacity.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   TokenManager.dissmisProgress();
                   if (response.isSuccessful()) {
                       try {
                           String segment = response.body().string();
                           JSONObject jsonObject = new JSONObject(segment);
                           parseJSONObject(jsonObject, 4);
                       } catch (IOException e) {
                           e.printStackTrace();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   } else {
                       if (response.code()==401){
                           TokenExpiredUtils.tokenExpired(getActivity());
                       }
                       if (response.code()==404){
                           Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                       }
                   }
               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   TokenManager.dissmisProgress();
                   Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                stateMap = map;
                break;
            case 1:
                districtMap = map;
                break;
            case 2:
                cityMap = map;
                break;
            case 3:
                postalcodeMap = map;
                break;
            case 4:
                 loadCapacityMap= map;
                break;
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
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }

            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), MACHINE_PITCHURE_DOC);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (resultCode== Activity.RESULT_OK){
            if (requestCode==MACHINE_IMAGE_PATH){
                Uri uri = data.getData();
                mMachineImagePath= FileUtils.getPath(getActivity(),uri);
                txtNoFileSelected.setText(FileUtils.getFileName(getActivity(), Uri.parse(mMachineImagePath)));
            }
        }*/

        if (resultCode== Activity.RESULT_OK){

            if (requestCode==MACHINE_PITCHURE_DOC){

               // Uri uri = data.getData();

               // mUploadingDocument= FileUtils.getPath(getActivity(),uri);
               // txtNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(mUploadingDocument)));
                txtNoFileSelected.setText("");
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    //do something with the image (save it to some directory or whatever you need to do with it here)

                        String mUploadingDocument= FileUtils.getPath(getActivity(),imageUri);
                        txtNoFileSelected.append(FileUtils.getFileName(getActivity(),Uri.parse(mUploadingDocument))+",");

                    }
                } else if(data.getData() != null) {
                    Uri imagePathuri = data.getData();
                   //do something with the image (save it to some directory or whatever you need to do with it here)
                    String mUploadingDocument= FileUtils.getPath(getActivity(),imagePathuri);
                    txtNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(mUploadingDocument)));

                    mMachineImagePath.add(mUploadingDocument);
                }

            }

            if (requestCode==MACHINE_PITCHURE_CAMERA){
                // Uri uri = data.getData();
                // mSupportingDocument= FileUtils.getPath(getActivity(),uri);
                if (txtNoFileSelected.getText().toString().isEmpty()){
                    txtNoFileSelected.setText(FileUtils.getFileName(getActivity(),Uri.parse(imageFilePath)));
                }else {
                    txtNoFileSelected.setText(","+FileUtils.getFileName(getActivity(),Uri.parse(imageFilePath)));
                }


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
                startActivityForResult(pictureIntent, MACHINE_PITCHURE_CAMERA);
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
        //mProfileImage=imageFilePath;
        //txtNoFileSelected.setText(imageFilePath);
        //Bitmap bmp_post_news = BitmapFactory.decodeFile(imageFilePath);
        //mIcon.setImageBitmap(bmp_post_news);
        return image;
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
