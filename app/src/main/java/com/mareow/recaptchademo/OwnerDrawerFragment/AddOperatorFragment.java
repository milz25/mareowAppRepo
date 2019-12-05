package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.DataModels.AssociatedOperator;
import com.mareow.recaptchademo.DataModels.CreateOperator;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOperatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOperatorFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

     AppCompatImageView mOperatorIcon;
     AppCompatImageView mOperatorIconmask;
     AppCompatImageView mOperatorVerfied;

     AppCompatImageButton mOperatorWorkOrder;
     AppCompatImageButton mOperatorDeassociation;

     TextInputEditText editAboutMySelf;
     TextInputEditText editAbilitytoRun;
     TextInputEditText editBasicRate;
     TextInputEditText editOperators;

    SwitchCompat mSwitchAccomodation;
    SwitchCompat mSwitchTransportation;
    SwitchCompat mSwitchFood;

    SwitchCompat mSwitchAssociatedOperator;

    LinearLayout lnOperatorDetails;

    boolean CHECK_OPERATOR=true;
    int SELECTED_OPERATOR_ID;
    HashMap<String,String>  associatedMap = new HashMap<>();
    ProgressDialog progressDialog;
    List<AssociatedOperator> associatedOperatorsDetails=new ArrayList<>();
    AppCompatButton btnRequestOperator;
    public AddOperatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOperatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOperatorFragment newInstance(String param1, String param2) {
        AddOperatorFragment fragment = new AddOperatorFragment();
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
        View view=inflater.inflate(R.layout.fragment_add_operator, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.................");
        }
        getAssocitedOperator();
        initView(view);
        return view;
    }

    private void initView(View view){

        lnOperatorDetails=(LinearLayout)view.findViewById(R.id.owner_add_operator_details);
        lnOperatorDetails.setVisibility(View.GONE);


        editOperators=(TextInputEditText)view.findViewById(R.id.owner_add_operator_operator);
        editOperators.setInputType(InputType.TYPE_NULL);
        editOperators.setOnClickListener(this);
        editOperators.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    callCustomeDialog(associatedMap,0,"Operator");
                }
            }
        });
        mOperatorIcon=(AppCompatImageView)view.findViewById(R.id.owner_add_operator_icon);
        mOperatorIconmask=(AppCompatImageView)view.findViewById(R.id.owner_add_operator_icon_mask);
        mOperatorVerfied=(AppCompatImageView)view.findViewById(R.id.owner_add_operator_user_verified);


        editAboutMySelf=(TextInputEditText)view.findViewById(R.id.owner_add_operator_about_myself);
        editAbilitytoRun=(TextInputEditText)view.findViewById(R.id.owner_add_operator_ability_to_run);
        editBasicRate=(TextInputEditText)view.findViewById(R.id.owner_add_operator_basic_rate);


        mSwitchAccomodation=(SwitchCompat)view.findViewById(R.id.owner_add_operator_accomodation);
        mSwitchTransportation=(SwitchCompat)view.findViewById(R.id.owner_add_operator_trnsportation);
        mSwitchFood=(SwitchCompat)view.findViewById(R.id.owner_add_operator_food);


        mSwitchAssociatedOperator=(SwitchCompat)view.findViewById(R.id.owner_add_operator_associated_operator);
        mSwitchAssociatedOperator.setChecked(CHECK_OPERATOR);
        mSwitchAssociatedOperator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CHECK_OPERATOR=true;
                    lnOperatorDetails.setVisibility(View.GONE);
                    editOperators.getText().clear();
                    editOperators.clearFocus();
                    getAssocitedOperator();
                }else {
                    CHECK_OPERATOR=false;
                    lnOperatorDetails.setVisibility(View.GONE);
                    editOperators.getText().clear();
                    editOperators.clearFocus();
                    getIndependentOperator();
                }
            }
        });

        btnRequestOperator=(AppCompatButton)view.findViewById(R.id.owner_add_operator_associate);
        btnRequestOperator.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.owner_add_operator_operator:
                callCustomeDialog(associatedMap,0,"Operator");
                break;
            case R.id.owner_add_operator_associate:
                callCreateOperator();
                break;
        }
    }


    private void callCustomeDialog(HashMap<String, String> hashMap, int check,String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();
        for (String key : hashMap.keySet()) {
            listData.add(hashMap.get(key));
        }
        Collections.sort(listData);
        showPopWindowForView(tilte,listData);
    }

    private void showPopWindowForView(String tilte, ArrayList<String> listData) {
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        popupWindow.setAnchorView(editOperators);
        popupWindow.setWidth(editOperators.getMeasuredWidth());
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editOperators.setText(listData.get(position));

                for(Map.Entry entry: associatedMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            String value=(String)entry.getKey();
                            SELECTED_OPERATOR_ID =Integer.parseInt(value);
                            break; //breaking because its one to one map
                        }
                    }

                if (CHECK_OPERATOR){
                    getAssocitedOperatorDetails(SELECTED_OPERATOR_ID);
                }
                popupWindow.dismiss();

            }
        });
        popupWindow.show();
    }


    public void getIndependentOperator(){
        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> callOfferWorkOrder=apiInterface.getIndependentOperator("Bearer "+token,partyId);
        callOfferWorkOrder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    String associatedList = null;
                    try {
                        associatedList = response.body().string();
                        JSONObject jsonObject = new JSONObject(associatedList);
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
                    if (response.code()==403){
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }


    public void getAssocitedOperator(){
        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> callOfferWorkOrder=apiInterface.getAssociatedOperator("Bearer "+token,partyId);
        callOfferWorkOrder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    String associatedList = null;
                    try {
                        associatedList = response.body().string();
                        JSONObject jsonObject = new JSONObject(associatedList);
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
                    if (response.code()==403){
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
                associatedMap.clear();
                associatedMap = map;
                break;
        }

    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        snackbar.show();
    }

    public void getAssocitedOperatorDetails(int operatorId){
        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<AssociatedOperator>> callOfferWorkOrder=apiInterface.getOperatorDetailsAssociatedwithOwner("Bearer "+token,operatorId);
        callOfferWorkOrder.enqueue(new Callback<List<AssociatedOperator>>() {
            @Override
            public void onResponse(Call<List<AssociatedOperator>> call, Response<List<AssociatedOperator>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    associatedOperatorsDetails=response.body();
                    setDataInView();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");
                    }
                    if (response.code()==403){
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
            public void onFailure(Call<List<AssociatedOperator>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void setDataInView() {
        if (associatedOperatorsDetails.size()>0){

            editAboutMySelf.setText(associatedOperatorsDetails.get(0).getAboutOperator());
            editAboutMySelf.setInputType(InputType.TYPE_NULL);
            editAbilitytoRun.setText(associatedOperatorsDetails.get(0).getAbilitytoRunMachine());
            editAbilitytoRun.setInputType(InputType.TYPE_NULL);
            DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
            editBasicRate.setText("\u20B9 "+IndianCurrencyFormat.format(associatedOperatorsDetails.get(0).getRate()));
            editBasicRate.setInputType(InputType.TYPE_NULL);

            if (associatedOperatorsDetails.get(0).getOptUserCategory().equals("User Category: Blue")){
                mOperatorIconmask.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.category_bule));
            }else if (associatedOperatorsDetails.get(0).getOptUserCategory().equals("User Category: Platinum")){
                mOperatorIconmask.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.category_platinum));
            }else if (associatedOperatorsDetails.get(0).getOptUserCategory().equals("User Category: Gold")){
                mOperatorIconmask.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.category_gold));
            }else if (associatedOperatorsDetails.get(0).getOptUserCategory().equals("User Category: Silver")){
                mOperatorIconmask.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.category_silver));
            }else if (associatedOperatorsDetails.get(0).getOptUserCategory().equals("User Category: Diamond")){
                mOperatorIconmask.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.category_diamond));
            }


            if (associatedOperatorsDetails.get(0).getOperatorImage()!=null){

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .transform(new CircleTransform(getActivity()));

                Glide.with(getActivity()).load(associatedOperatorsDetails.get(0).getOperatorImage())
                        .apply(options)
                        .into(mOperatorIcon);


            }
            if (associatedOperatorsDetails.get(0).isIsverifiedOpt()){
                mOperatorVerfied.setImageResource(R.drawable.ic_verify_true);
            }else {
                mOperatorVerfied.setImageResource(R.drawable.ic_verify_false);
            }


            lnOperatorDetails.setVisibility(View.VISIBLE);
        }
    }


    private void callCreateOperator() {

        if (editOperators.getText().toString().isEmpty()){
            showSnackbar("Please select Operator.");
            return;
        }

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();
        CreateOperator createOperator=new CreateOperator();
        createOperator.setOwnerId(String.valueOf(partyId));
        createOperator.setOperatorId(String.valueOf(SELECTED_OPERATOR_ID));

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callCreateOperator=apiInterface.createAssociatedOperator("Bearer "+token,createOperator);
        callCreateOperator.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");
                    }
                    if (response.code()==403){
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





}
