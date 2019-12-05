package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
 * Use the {@link PlanGeneralDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanGeneralDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editLoad;
    TextInputEditText editShift;
    TextInputEditText editBasicRate;
    TextInputEditText editDailyHours;
    TextInputEditText editHoursMonthly;
    TextInputEditText editDaysMonthly;
    public static TextInputEditText editDaysCommitted;
    TextInputEditText editOverTime;
    TextInputEditText editCGST;
    TextInputEditText editSGST;
    TextInputEditText editIGST;

    public static TextInputLayout CommitedHint;


    HashMap<String,String>  loadMap = new HashMap<>();
    HashMap<String,String>  shiftMap = new HashMap<>();

    public static String LOAD_CODE=null;
    public static String SHIFT_CODE=null;
    public static String BASIC_RATE=null;
    public static String DAILY_HOURS=null;
    public static String HOURS_MONTHLY=null;
    public static String DAYS_MONTHLY=null;
    public static String HOURS_COMMITED=null;
    public static String DAYS_COMMITED=null;
    public static String MONTHLY_COMMITED=null;
    public static String OVERTIME=null;
    public static String CGST=null;
    public static String SGST=null;
    public static String IGST_STRING=null;


    public static boolean GST=false;
    public static boolean IGST=false;




    public final String LOAD="LOAD_CAPACITY";
    public final String SHIFT_LOOK="SHIFT";

    AppCompatCheckBox checkBoxGST;
    AppCompatCheckBox checkBoxIGST;

    LinearLayout hideGST;
    LinearLayout hideIGST;

    ApiInterface apiInterface;



    public PlanGeneralDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanGeneralDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanGeneralDetailsFragment newInstance(String param1, String param2) {
        PlanGeneralDetailsFragment fragment = new PlanGeneralDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_plan_general_details, container, false);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        callGetLoadCapacity();
        callGetShift();
        initView(view);
        return view;
    }

    private void initView(View view) {

        CommitedHint=(TextInputLayout)view.findViewById(R.id.ARO_GD_days_committed_hint);
        editLoad=(TextInputEditText)view.findViewById(R.id.ARO_GD_load);
        editLoad.setInputType(InputType.TYPE_NULL);
        editLoad.setOnClickListener(this);
        editLoad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.LOAD_CAPACITY=true;
                    Constants.SHIFT=false;
                    callCustomeDialog(loadMap, 0,"Load");
                }
            }
        });
        editShift=(TextInputEditText)view.findViewById(R.id.ARO_GD_shift);
        editShift.setInputType(InputType.TYPE_NULL);
        editShift.setOnClickListener(this);
        editShift.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.LOAD_CAPACITY=false;
                    Constants.SHIFT=true;
                    callCustomeDialog(shiftMap, 1,"shift");
                }
            }
        });
        editBasicRate=(TextInputEditText)view.findViewById(R.id.ARO_GD_basic_rate);
        if (BASIC_RATE!=null){
            editBasicRate.setText(BASIC_RATE);
        }
        editBasicRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               BASIC_RATE=s.toString();
            }
        });
        editDailyHours=(TextInputEditText)view.findViewById(R.id.ARO_GD_daily_hours);
        if (DAILY_HOURS!=null){
            editDailyHours.setText(DAILY_HOURS);
        }
        editDailyHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")){
                    if (checkDailyHOurs(s.toString()))
                        DAILY_HOURS=s.toString();
                }

            }
        });

        editHoursMonthly=(TextInputEditText)view.findViewById(R.id.ARO_GD_hours_monthly);
        if (HOURS_MONTHLY!=null){
            editHoursMonthly.setText(HOURS_MONTHLY);
        }
        editHoursMonthly.setInputType(InputType.TYPE_NULL);


        editDaysMonthly=(TextInputEditText)view.findViewById(R.id.ARO_GD_days_monthly);
        if (DAYS_MONTHLY!=null){
            editDaysMonthly.setText(DAYS_MONTHLY);
        }
        editDaysMonthly.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkDaysMonthly(s.toString()))
                    DAYS_MONTHLY=s.toString();
            }
        });

        editDaysCommitted=(TextInputEditText)view.findViewById(R.id.ARO_GD_days_committed);
        editDaysCommitted.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PlanMainFragment.PLAN_USAGE_CODE!=null){
                    if (PlanMainFragment.PLAN_USAGE_CODE.equals("DAILY")){
                        if (checkDaily(s.toString()))
                            DAYS_COMMITED=s.toString();
                    }else if (PlanMainFragment.PLAN_USAGE_CODE.equals("MONTHLY")){
                        if(checkMonthy(s.toString()))
                            MONTHLY_COMMITED=s.toString();
                    }else if (PlanMainFragment.PLAN_USAGE_CODE.equals("HOURLY")){
                        if (checkHourly(s.toString()))
                            HOURS_COMMITED=s.toString();
                    }
                }

            }
        });

        editOverTime=(TextInputEditText)view.findViewById(R.id.ARO_GD_overtime);
        if (OVERTIME!=null){
            editOverTime.setText(OVERTIME);
        }
        editOverTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (checkOverTime(s.toString()))
                  OVERTIME=s.toString();
            }
        });

        editCGST=(TextInputEditText)view.findViewById(R.id.ARO_GD_CGST);
        if (CGST!=null){
            editCGST.setText(CGST);
        }
        editCGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (checkCGST(s.toString()))
                   CGST=s.toString();
            }
        });
        editSGST=(TextInputEditText)view.findViewById(R.id.ARO_GD_SGST);
        if (SGST!=null){
            editSGST.setText(SGST);
        }
        editSGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (checkSGST(s.toString()))
                   SGST=s.toString();
            }
        });
        editIGST=(TextInputEditText)view.findViewById(R.id.ARO_GD_IGST_percent);
        if (IGST_STRING!=null){
            editIGST.setText(IGST_STRING);
        }
        editIGST.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (chechIGST(s.toString()))
                  IGST_STRING=s.toString();
            }
        });

        hideGST=(LinearLayout)view.findViewById(R.id.ARO_GD_GST_hide);
        hideGST.setVisibility(View.GONE);
        hideIGST=(LinearLayout)view.findViewById(R.id.ARO_GD_IGST_to_hide);
        hideIGST.setVisibility(View.GONE);

        checkBoxGST=(AppCompatCheckBox)view.findViewById(R.id.ARO_GD_GST_checkbox);
        checkBoxGST.setChecked(GST);
        checkBoxGST.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    hideGST.setVisibility(View.VISIBLE);
                    editCGST.setText("4.0");
                    editSGST.setText("4.0");
                    CGST="4.0";
                    SGST="4.0";
                }else {
                    hideGST.setVisibility(View.GONE);
                }
            }
        });
        checkBoxIGST=(AppCompatCheckBox)view.findViewById(R.id.ARO_GD_IGST_checkbox);
        checkBoxGST.setChecked(IGST);
        checkBoxIGST.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    hideIGST.setVisibility(View.VISIBLE);
                    editIGST.setText("8.0");
                    IGST_STRING="8.0";
                }else {
                    hideIGST.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean checkOverTime(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {
            if (0 <= Integer.parseInt(toString) && 100 >= Integer.parseInt(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 0 and 100.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;
    }

    private boolean chechIGST(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {

            if (8 <= Float.parseFloat(toString) && 36 >= Float.parseFloat(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 8.0 and 36.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;
    }

    private boolean checkSGST(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {

            if (4 <= Float.parseFloat(toString) && 36 >= Float.parseFloat(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 4.0 and 36.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;

    }

    private boolean checkCGST(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {

            if (4 <= Float.parseFloat(toString) && 36 >= Float.parseFloat(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 4.0 and 36.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;

    }

    private boolean checkDaysMonthly(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")){

            if (0 <= Integer.parseInt(toString) && 31 >= Integer.parseInt(toString)){
                trueAndFalse=true;
            }else {
                showSnackbar("This value should be between 0 and 31.");
                trueAndFalse=false;
            }
        }

        return trueAndFalse;

    }

    private boolean checkDailyHOurs(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {
            if (0 <= Integer.parseInt(toString) && 24 >= Integer.parseInt(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 0 and 24.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;
    }

    private boolean checkHourly(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {
            if (0 <= Integer.parseInt(toString) && 8 >= Integer.parseInt(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 0 and 8.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;
    }
    private boolean checkMonthy(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {
            if (0 <= Integer.parseInt(toString) && 12 >= Integer.parseInt(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 0 and 12.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;
    }

    private boolean checkDaily(String toString) {
        boolean trueAndFalse=false;
        if (!toString.equals("")) {
            if (0 <= Integer.parseInt(toString) && 30 >= Integer.parseInt(toString)) {
                trueAndFalse= true;
            } else {
                showSnackbar("This value should be between 0 and 30.");
                trueAndFalse= false;
            }
        }
        return trueAndFalse;

    }

    public void showSnackbar(String msg){

        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ARO_GD_load:
                Constants.LOAD_CAPACITY=true;
                Constants.SHIFT=false;
                callCustomeDialog(loadMap, 0,"Load Capacity");
                break;
            case R.id.ARO_GD_shift:
                Constants.LOAD_CAPACITY=false;
                Constants.SHIFT=true;
                callCustomeDialog(shiftMap, 1,"Load Capacity");
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

        if (Constants.LOAD_CAPACITY){
            popupWindow.setAnchorView(editLoad);
            popupWindow.setWidth(editLoad.getMeasuredWidth());
        }
        if (Constants.SHIFT){
            popupWindow.setAnchorView(editShift);
            popupWindow.setWidth(editShift.getMeasuredWidth());
        }


        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Constants.LOAD_CAPACITY){
                    editLoad.setText(listData.get(position));
                    LOAD_CODE=loadMap.get(listData.get(position));
                }

                if (Constants.SHIFT){
                    editShift.setText(listData.get(position));
                    SHIFT_CODE=shiftMap.get(listData.get(position));

                    setDefaultVauleinViews(listData.get(position));
                }

                popupWindow.dismiss();

            }
        });

        popupWindow.show();
    }

    private void setDefaultVauleinViews(String s) {

            editDailyHours.setText("8");
            DAILY_HOURS="8";
            editDaysMonthly.setText("22");
            DAYS_MONTHLY="22";
            editOverTime.setText("100");
            OVERTIME="100";
            calculateHoursMonthy();
    }

    public void callGetLoadCapacity(){
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> loadCapacity = apiInterface.getCommonLookUp(LOAD);
        loadCapacity.enqueue(new Callback<ResponseBody>() {
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
    public void callGetShift(){
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> shiftCall = apiInterface.getCommonLookUp(SHIFT_LOOK);
        shiftCall.enqueue(new Callback<ResponseBody>() {
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
                loadMap = map;
                if (LOAD_CODE!=null){
                    for(Map.Entry entry: loadMap.entrySet()){
                        if(LOAD_CODE.equals(entry.getKey())){
                            editLoad.setText(String.valueOf(entry.getKey()));
                            break; //breaking because its one to one map
                        }
                    }
                }
                break;
            case 1:
                shiftMap = map;
                if (SHIFT_CODE!=null){
                    for(Map.Entry entry: shiftMap.entrySet()){
                        if(SHIFT_CODE.equals(entry.getKey())){
                            editShift.setText(String.valueOf(entry.getKey()));
                            break; //breaking because its one to one map
                        }
                    }
                }
                break;
        }
    }


      public void calculateHoursMonthy() {
           if (!editDaysMonthly.getText().toString().trim().isEmpty() && !editDailyHours.getText().toString().trim().isEmpty()) {
              editHoursMonthly.setText(String.valueOf(Integer.parseInt(editDaysMonthly.getText().toString().trim())*Integer.parseInt(editDailyHours.getText().toString().trim())));
               HOURS_MONTHLY=editHoursMonthly.getText().toString();
           }
      }



}
