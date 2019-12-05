package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.DataModels.AddMachineModel;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOperatorMachineModelFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText mSegmentSpinner;
    TextInputEditText mCategorySpinner;
    TextInputEditText mSubCategorySpinner;
    TextInputEditText mManufacturerSpinner;
    TextInputEditText mModelNoSpinner;

    TextInputEditText edit_Url;
    AppCompatButton btnSave;

    ProgressDialog progressDialog;
    HashMap<String,String>  segmentMap = new HashMap<>();
    HashMap<String, String> categoryMap = new HashMap<>();
    HashMap<String, String> subCategoryMap = new HashMap<>();
    HashMap<String, String> manufacturerMap = new HashMap<>();
    HashMap<String, String> modelNoMap = new HashMap<>();

    String SEGMENT_CODE=null;
    String CATEGORY_CODE=null;
    String SUBCATEGORY_CODE=null;
    String MANUFACTURER_CODE=null;
    int MODEL_CODE;



    ApiInterface apiInterface;

    public AddOperatorMachineModelFragment() {
        // Required empty public constructor
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
        MainActivity.navItemIndex=15;
        MainActivity.txtTitle.setText("Machine Model");
        View view = inflater.inflate(R.layout.fragment_add_machine_model, container, false);
        initView(view);
        if(getActivity()!=null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait.......");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        callGetSegmentData();
        closeKeyBoard();
        return view;
    }

    private void closeKeyBoard() {
        View view=getActivity().getCurrentFocus();
        if (view!=null){
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void initView(View view) {

        mSegmentSpinner = (TextInputEditText) view.findViewById(R.id.add_operator_machine_segment);
        mSegmentSpinner.setInputType(InputType.TYPE_NULL);
        mCategorySpinner = (TextInputEditText) view.findViewById(R.id.add_operator_machine_category);
        mCategorySpinner.setInputType(InputType.TYPE_NULL);
        mSubCategorySpinner = (TextInputEditText) view.findViewById(R.id.add_operator_machine_subcategory);
        mSubCategorySpinner.setInputType(InputType.TYPE_NULL);
        mManufacturerSpinner = (TextInputEditText) view.findViewById(R.id.add_operator_machine_manufacturer);
        mManufacturerSpinner.setInputType(InputType.TYPE_NULL);
        mModelNoSpinner = (TextInputEditText) view.findViewById(R.id.add_operator_machine_modelno);
        mModelNoSpinner.setInputType(InputType.TYPE_NULL);

        edit_Url = (TextInputEditText) view.findViewById(R.id.add_operator_machine_url);
        edit_Url.setKeyListener(null);

        mSegmentSpinner.setOnClickListener(this);
        mCategorySpinner.setOnClickListener(this);
        mSubCategorySpinner.setOnClickListener(this);
        mManufacturerSpinner.setOnClickListener(this);
        mModelNoSpinner.setOnClickListener(this);

       mSegmentSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus){
                   Constants.SEGMENT = true;
                   Constants.CATEGORY = false;
                   Constants.SUBCATEGORY = false;
                   Constants.MANUFACTURER = false;
                   Constants.MODEL_NO = false;
                   callCustomeDialog(segmentMap, 0,"Segment");
               }
           }
       });

        mCategorySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = true;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    callCustomeDialog(categoryMap, 1,"Category");
                }
            }
        });
        mSubCategorySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = true;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    callCustomeDialog(subCategoryMap, 2,"Subcategory");
                }
            }
        });
        mManufacturerSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = true;
                    Constants.MODEL_NO = false;
                    callCustomeDialog(manufacturerMap, 3,"Manufacturer");
                }
            }
        });
        mModelNoSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = true;
                    if (categoryMap.size()>0 && subCategoryMap.size()>0 && manufacturerMap.size()>0 && modelNoMap.size()==0){
                        showSnackbar("No model available for this category.");
                    }else {
                        callCustomeDialog(modelNoMap, 4,"Model");
                    }

                }
            }
        });

        btnSave = (AppCompatButton) view.findViewById(R.id.add_operator_machine_save);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_operator_machine_save:
                callSaveMachineModel();
                break;
            case R.id.add_operator_machine_segment:
                Constants.SEGMENT = true;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                callCustomeDialog(segmentMap, 0,"Segment");
                break;
            case R.id.add_operator_machine_category:
                Constants.SEGMENT = false;
                Constants.CATEGORY = true;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                callCustomeDialog(categoryMap, 1,"Category");
                break;
            case R.id.add_operator_machine_subcategory:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = true;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                callCustomeDialog(subCategoryMap, 2,"Subcategory");
                break;
            case R.id.add_operator_machine_manufacturer:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = true;
                Constants.MODEL_NO = false;
                callCustomeDialog(manufacturerMap, 3,"Manufacturer");
                break;
            case R.id.add_operator_machine_modelno:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = true;
                if (categoryMap.size()>0 && subCategoryMap.size()>0 && manufacturerMap.size()>0 && modelNoMap.size()==0){
                    showSnackbar("No model available for this category.");
                }else {
                    callCustomeDialog(modelNoMap, 4,"Model");
                }
                break;
        }
    }

    private void callCustomeDialog(HashMap<String, String> hashMap, int check,String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();
        for (String key : hashMap.keySet()) {
            listData.add(key);
        }

        Collections.sort(listData);
        if (listData.size()==0){
            showSnackbar("Please select above value first.");
        }else {
            ShowSpinnerDialog(tilte,listData);
        }

    }

    private void callSaveMachineModel() {
        String segment = mSegmentSpinner.getText().toString();
        String category = mCategorySpinner.getText().toString();
        String subCategory = mSubCategorySpinner.getText().toString();
        String manufacturer = mManufacturerSpinner.getText().toString();
        String model_no = mModelNoSpinner.getText().toString();
        String url = edit_Url.getText().toString();


        if (segment.isEmpty()) {
            mSegmentSpinner.setError("Segment");
            mSegmentSpinner.requestFocus();
            return;
        }
       /* if (category.isEmpty()) {
            mCategorySpinner.setError("Category");
            mCategorySpinner.requestFocus();
            return;
        }
        if (subCategory.isEmpty()) {
            mSubCategorySpinner.setError("SubCategory");
            mSubCategorySpinner.requestFocus();
            return;
        }
        if (manufacturer.isEmpty()) {
            mManufacturerSpinner.setError("Manufacturer");
            mManufacturerSpinner.requestFocus();
            return;
        }*/
        if (model_no.isEmpty()) {
            mModelNoSpinner.setError("Model_No");
            mModelNoSpinner.requestFocus();
            return;
        }

        String token = TokenManager.getSessionToken();
        int partyId = TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID, 0);

        AddMachineModel addMachineModel = new AddMachineModel();
        addMachineModel.setPartyId(String.valueOf(partyId));
        if (segmentMap.containsKey(segment)) {
            addMachineModel.setSegmentCode(segmentMap.get(segment));
        }


        if (categoryMap.containsKey(category)) {
            addMachineModel.setCatagoryCode(categoryMap.get(category));
        }


        if (subCategoryMap.containsKey(subCategory)) {
            addMachineModel.setSubCategoryCode(subCategoryMap.get(subCategory));
        }


        if (manufacturerMap.containsKey(manufacturer)) {
            addMachineModel.setManufacturerCode(manufacturerMap.get(manufacturer));
        }


        if (modelNoMap.containsKey(model_no)) {
            addMachineModel.setMachineModelId(modelNoMap.get(model_no));
        }

        addMachineModel.setUrl(edit_Url.getText().toString());
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> addMachineModelCall = apiInterface.addOperatorMachineModel("Bearer " + token, addMachineModel);
        addMachineModelCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   // getActivity().getSupportFragmentManager().popBackStack();
                    showSnackbar(response.body().getMessage());
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            // Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
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

    private void callGetSegmentData() {
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        progressDialog.show();
        Call<ResponseBody> segmentcall = apiInterface.getPreferedFilteredSegment(partyId);
        segmentcall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseSegmentJSONObject(jsonObject, 0);
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetCategoryData(String seg,String cat,String subCat,String manu) {
        String token=TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
         if (CATEGORY_CODE==null){
             cat="null";
         }
         if (SUBCATEGORY_CODE==null){
             subCat="null";
         }
         if (MANUFACTURER_CODE==null){
             manu="null";
         }
        progressDialog.show();
        Call<ResponseBody> categoryCall = apiInterface.getSegmentBaseCatSubManuModel("Bearer "+token,seg,cat,subCat,manu,partyId);
        categoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String category = response.body().string();
                        JSONObject jsonObject = new JSONObject(category);
                        parsenewJSONObject(jsonObject, 1);
                        //callSubCategoryData();
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSubCategoryData(String key) {
        progressDialog.show();
        Call<ResponseBody> subCategoryCall = apiInterface.getSegmentSubCategory(categoryMap.get(key));
        subCategoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String subCategory = response.body().string();
                        JSONObject jsonObject = new JSONObject(subCategory);
                       // parseJSONObject(jsonObject, 2);
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMenufacturerData() {
        progressDialog.show();
        Call<ResponseBody> manufacturerCall = apiInterface.getManufacturer();
        manufacturerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String manufacturer = response.body().string();
                        JSONObject jsonObject = new JSONObject(manufacturer);
                       // parseJSONObject(jsonObject, 3);
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callModelNoData() {
        progressDialog.show();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<ResponseBody> modelNoCall = apiInterface.getModelNoCombo(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE,partyId);
        modelNoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String modelNo = response.body().string();
                        JSONObject jsonObject = new JSONObject(modelNo);
                       // parseJSONObject(jsonObject, 4);
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
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMachineUrlData() {
        progressDialog.show();
        String token=TokenManager.getSessionToken();
        Call<String> modelNoCall = apiInterface.getMachineUrl("Bearer "+token,MODEL_CODE);
        modelNoCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    edit_Url.setText(response.body().toString());
                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseSegmentJSONObject(JSONObject jsonObject, int check) {
        HashMap<String, String> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        segmentMap=commonMap;
        SEGMENT_CODE=segmentMap.get(segmentMap.keySet().toArray()[0]);
        callGetCategoryData(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE);

       // setHashMap(commonMap, check);
    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 0:
                segmentMap = map;
                break;
            case 1:
                categoryMap = map;
                break;
            case 2:
                subCategoryMap = map;
                break;
            case 3:
                manufacturerMap = map;
                break;
            case 4:
                modelNoMap = map;
                break;
        }

    }

    private void ShowSpinnerDialog(String title, final ArrayList<String> listData){
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText(title);
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                if (Constants.SEGMENT){

                    mSegmentSpinner.setText(listData.get(position));
                    mCategorySpinner.setText("");
                    mSubCategorySpinner.setText("");
                    mManufacturerSpinner.setText("");
                    mModelNoSpinner.setText("");
                    edit_Url.setText("");


                    SEGMENT_CODE=segmentMap.get(listData.get(position));
                    CATEGORY_CODE=null;
                    SUBCATEGORY_CODE=null;
                    MANUFACTURER_CODE=null;
                    categoryMap.clear();
                    subCategoryMap.clear();
                    manufacturerMap.clear();
                    modelNoMap.clear();
                    callGetCategoryData(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE);
                }
                if (Constants.CATEGORY){
                    mCategorySpinner.setText(listData.get(position));

                    mSubCategorySpinner.setText("");
                    mManufacturerSpinner.setText("");
                    mModelNoSpinner.setText("");
                    edit_Url.setText("");

                    CATEGORY_CODE=categoryMap.get(listData.get(position));
                    SUBCATEGORY_CODE=null;
                    MANUFACTURER_CODE=null;
                    subCategoryMap.clear();
                    manufacturerMap.clear();
                    modelNoMap.clear();

                    callGetCategoryData(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE);
                   // callSubCategoryData(listData.get(position));
                }
                if (Constants.SUBCATEGORY){
                    mSubCategorySpinner.setText(listData.get(position));

                    mManufacturerSpinner.setText("");
                    mModelNoSpinner.setText("");
                    edit_Url.setText("");

                    SUBCATEGORY_CODE=subCategoryMap.get(listData.get(position));
                    MANUFACTURER_CODE=null;
                    manufacturerMap.clear();
                    modelNoMap.clear();
                    callGetCategoryData(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE);

                   // callMenufacturerData();
                }
                if (Constants.MANUFACTURER){
                    mManufacturerSpinner.setText(listData.get(position));

                    mModelNoSpinner.setText("");
                    edit_Url.setText("");

                    MANUFACTURER_CODE=manufacturerMap.get(listData.get(position));
                    modelNoMap.clear();
                    callGetCategoryData(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE);
                    //callModelNoData();
                }
                if (Constants.MODEL_NO){
                    mModelNoSpinner.setText(listData.get(position));

                    edit_Url.setText("");
                    MODEL_CODE=Integer.parseInt(modelNoMap.get(listData.get(position)));
                    callMachineUrlData();
                }
            }

        };

        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),listData,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }


    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void parsenewJSONObject(JSONObject jsonObject, int check) {
        try {
            JSONObject categoryObject=jsonObject.getJSONObject("Category");
            Iterator<String> catkey = categoryObject.keys();
            while(catkey.hasNext()) {
                String key = catkey.next();
               categoryMap.put(key,categoryObject.getString(key));

            }

            JSONObject subCategoryObject=jsonObject.getJSONObject("SubCategory");
            Iterator<String> subCatkeys = subCategoryObject.keys();
            while(subCatkeys.hasNext()) {
                String key = subCatkeys.next();
                subCategoryMap.put(key,subCategoryObject.getString(key));

            }
            JSONObject manufactureObject=jsonObject.getJSONObject("Manufacturer");
            Iterator<String> manufacturekeys = manufactureObject.keys();
            while(manufacturekeys.hasNext()) {
                String key = manufacturekeys.next();
                manufacturerMap.put(key,manufactureObject.getString(key));

            }

            JSONObject modelObject=jsonObject.getJSONObject("Model");
            Iterator<String> modelkeys = modelObject.keys();
            while(modelkeys.hasNext()) {
                String key = modelkeys.next();
                modelNoMap.put(key,modelObject.getString(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
