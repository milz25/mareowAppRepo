package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanMainFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editPlanType;
    TextInputEditText editPlanUsage;
    TextInputEditText editPlanName;
    TextInputEditText editPlanDescription;

    TextInputEditText editPlanDedicatedto;
    TextInputEditText editPlanValidDays;

    LinearLayout planSpecificSection;
    HashMap<String,String>  planTypeMap = new HashMap<>();
    HashMap<String, String> planUsageMap = new HashMap<>();

    String PLAN_TYPE="PLAN_TYPE";
    String PLAN_USAGE="PLAN_USAGE";

    public static String PLAN_TYPE_CODE=null;
    public static String PLAN_USAGE_CODE=null;
    public static String PLAN_NAME=null;
    public static String PLAN_DESCRIPTION=null;

    public static String PLAN_VALID_DAYS=null;
    public static String PLAN_DEDICATED_TO=null;



    ApiInterface apiInterface;


    public PlanMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanMainFragment newInstance(String param1, String param2) {
        PlanMainFragment fragment = new PlanMainFragment();
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
        View view=inflater.inflate(R.layout.fragment_plan_main, container, false);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
         planTypeMap.clear();
         planUsageMap.clear();
        callPlanTypeandUSAGEApi();
        callPlanUSAGEApi();
        initView(view);
        return view;
    }

    private void initView(View view) {

        editPlanDedicatedto=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_dedicated_to_renter);
        editPlanValidDays=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_valid_days);
        editPlanValidDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")){
                    if (Integer.parseInt(s.toString())>15){
                        Toast.makeText(getActivity(), "THis Should be 1 to 15", Toast.LENGTH_SHORT).show();
                    }else {
                        PLAN_VALID_DAYS=s.toString();
                    }
                }

            }
        });
        planSpecificSection=(LinearLayout)view.findViewById(R.id.plan_specific_section);
        planSpecificSection.setVisibility(View.GONE);

        editPlanType=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_type);
        editPlanType.setInputType(InputType.TYPE_NULL);
        editPlanType.setOnClickListener(this);
        editPlanType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.PLAN_TYPE=true;
                    Constants.PLAN_USAGE=false;
                    callCustomeDialog(planTypeMap,0,"Plan_Type");
                }
            }
        });

        editPlanUsage=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_usage);
        editPlanUsage.setInputType(InputType.TYPE_NULL);
        editPlanUsage.setOnClickListener(this);
        editPlanUsage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideKeyboardFrom(getActivity(),v);
                    Constants.PLAN_TYPE=false;
                    Constants.PLAN_USAGE=true;
                    callCustomeDialog(planUsageMap,1,"Plan_Usage");
                }
            }
        });

        editPlanName=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_name);
        if (PLAN_NAME!=null){
            editPlanName.setText(PLAN_NAME);
        }
        editPlanName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                PLAN_NAME=s.toString();
            }
        });
        editPlanDescription=(TextInputEditText)view.findViewById(R.id.ARO_AP_plan_description);

        if (PLAN_DESCRIPTION!=null){
            editPlanDescription.setText(PLAN_DESCRIPTION);
        }
        editPlanDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               PLAN_DESCRIPTION=s.toString();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ARO_AP_plan_type:
                Constants.PLAN_TYPE=true;
                Constants.PLAN_USAGE=false;
                callCustomeDialog(planTypeMap,0,"Plan_Type");
                break;
            case R.id.ARO_AP_plan_usage:
                hideKeyboardFrom(getActivity(),v);
                Constants.PLAN_TYPE=false;
                Constants.PLAN_USAGE=true;
                callCustomeDialog(planUsageMap,1,"Plan_Usage");
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

        if (Constants.PLAN_TYPE){
            popupWindow.setAnchorView(editPlanType);
            popupWindow.setWidth(editPlanType.getMeasuredWidth());
        }else if (Constants.PLAN_USAGE){
            popupWindow.setAnchorView(editPlanUsage);
            popupWindow.setWidth(editPlanUsage.getMeasuredWidth());
        }

        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Constants.PLAN_TYPE){
                    editPlanType.setText(listData.get(position));

                    /*for(Map.Entry entry: planTypeMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            PLAN_TYPE_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }*/

                 PLAN_TYPE_CODE=planTypeMap.get(listData.get(position));

                 if (PLAN_TYPE_CODE.equals("TEMP")){
                     planSpecificSection.setVisibility(View.VISIBLE);
                 }else {
                     planSpecificSection.setVisibility(View.GONE);
                 }

                }
                if (Constants.PLAN_USAGE){
                    editPlanUsage.setText(listData.get(position));

                   /* for(Map.Entry entry: planUsageMap.entrySet()){
                        if(listData.get(position).equals(entry.getValue())){
                            PLAN_USAGE_CODE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }*/

                   PLAN_USAGE_CODE=planUsageMap.get(listData.get(position));

                        if (PLAN_USAGE_CODE.equals("DAILY")){
                            PlanGeneralDetailsFragment.CommitedHint.setHint("Days (Committed) *");
                            PlanGeneralDetailsFragment.editDaysCommitted.setText("22");
                        }else if (PLAN_USAGE_CODE.equals("MONTHLY")){
                            PlanGeneralDetailsFragment.CommitedHint.setHint("Months (Committed) *");
                            PlanGeneralDetailsFragment.editDaysCommitted.setText("1");
                        }else if (PLAN_USAGE_CODE.equals("HOURLY")){
                            PlanGeneralDetailsFragment.CommitedHint.setHint("Hours (Committed) *");
                            PlanGeneralDetailsFragment.editDaysCommitted.setText("8");
                        }
                    }


                popupWindow.dismiss();

            }
        });

        popupWindow.show();
    }

    public void callPlanTypeandUSAGEApi(){
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> planTypeCall = apiInterface.getCommonLookUp(PLAN_TYPE);
        planTypeCall.enqueue(new Callback<ResponseBody>() {
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

    public void callPlanUSAGEApi(){
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> planUsageCall = apiInterface.getCommonLookUp(PLAN_USAGE);
        planUsageCall.enqueue(new Callback<ResponseBody>() {
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
                planTypeMap = map;
                if (PLAN_TYPE_CODE!=null){
                    for(Map.Entry entry: planTypeMap.entrySet()){
                        if(PLAN_TYPE_CODE.equals(entry.getKey())){
                            editPlanType.setText(String.valueOf(entry.getKey()));
                            break; //breaking because its one to one map
                        }
                    }
                }
                break;
            case 1:
                planUsageMap = map;
                if (PLAN_USAGE_CODE!=null){
                       for(Map.Entry entry: planUsageMap.entrySet()){
                        if(PLAN_USAGE_CODE.equals(entry.getValue())){
                            editPlanUsage.setText(String.valueOf(entry.getKey()));
                            break; //breaking because its one to one map
                        }
                    }

                    if (PLAN_USAGE_CODE.equals("DAILY")){
                        PlanGeneralDetailsFragment.CommitedHint.setHint("Days (Committed) *");
                        PlanGeneralDetailsFragment.editDaysCommitted.setText("22");
                    }else if (PLAN_USAGE_CODE.equals("MONTHLY")){
                        PlanGeneralDetailsFragment.CommitedHint.setHint("Months (Committed) *");
                        PlanGeneralDetailsFragment.editDaysCommitted.setText("1");
                    }else if (PLAN_USAGE_CODE.equals("HOURLY")){
                        PlanGeneralDetailsFragment.CommitedHint.setHint("Hours (Committed) *");
                        PlanGeneralDetailsFragment.editDaysCommitted.setText("8");
                    }

                }
                break;
        }
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
