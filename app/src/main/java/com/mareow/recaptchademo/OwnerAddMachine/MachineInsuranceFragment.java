package com.mareow.recaptchademo.OwnerAddMachine;


import android.app.Activity;
import android.graphics.Point;
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
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.CalendarDialog;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Use the {@link MachineInsuranceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineInsuranceFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextInputEditText editInsuranceType;
    TextInputEditText editInsuranceCompany;
    TextInputEditText editInsuranceDate;
    TextInputEditText editInsuranceClaimedYear;
    TextInputEditText editMachineAge;
    TextInputEditText editNCB;

    AppCompatCheckBox checkBoxInsurance;
    ApiInterface apiInterface;

    public static final String INSURANCE_TYPE="INSURANCE_TYPE";
    HashMap<String, String> insuranceMap = new HashMap<>();
    ArrayList<String> insuranceList=new ArrayList<>();

    public static String INSURANCE_TYPE_CODE =null;
    public static boolean INSURANCE_FALG=true;
    public static String INSURANCE_COMPANY=null;
    public static String INSURANCE_START_DATE=null;
    public static String INSURANCE_END_DATE=null;
    public static String INSURANCE_CLAIMED_YEAR=null;
    public static String INSURANCE_NCB=null;
    public static String MACHINE_AGE=null;



    public MachineInsuranceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineInsuranceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineInsuranceFragment newInstance(String param1, String param2) {
        MachineInsuranceFragment fragment = new MachineInsuranceFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine_insurance, container, false);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        callInsuranceTypeApi();
        initView(view);
        return view;
    }

    private void initView(View view) {

        checkBoxInsurance=(AppCompatCheckBox)view.findViewById(R.id.OMD_MID_insurance_checkbox);
        checkBoxInsurance.setChecked(INSURANCE_FALG);
        checkBoxInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editInsuranceCompany.setEnabled(true);
                    editInsuranceType.setEnabled(true);
                    editInsuranceDate.setEnabled(true);
                    editInsuranceClaimedYear.setEnabled(true);
                    editMachineAge.setEnabled(true);
                    editNCB.setEnabled(true);
                    INSURANCE_FALG=true;
                }else {
                    editInsuranceCompany.setEnabled(false);
                    editInsuranceType.setEnabled(false);
                    editInsuranceDate.setEnabled(false);
                    editInsuranceClaimedYear.setEnabled(false);
                    editMachineAge.setEnabled(false);
                    editNCB.setEnabled(false);
                    INSURANCE_FALG=false;
                }
            }
        });

        editInsuranceCompany=(TextInputEditText)view.findViewById(R.id.OMD_MID_insurance_company);
        if (INSURANCE_COMPANY!=null){
            editInsuranceCompany.setText(INSURANCE_COMPANY);
        }
        editInsuranceCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              INSURANCE_COMPANY=s.toString();
            }
        });
        editInsuranceType=(TextInputEditText)view.findViewById(R.id.OMD_MID_insurance_type);
        if (INSURANCE_TYPE_CODE!=null){
            editInsuranceType.setText(INSURANCE_TYPE_CODE);
        }
        editInsuranceType.setInputType(InputType.TYPE_NULL);
        editInsuranceType.setOnClickListener(this);
        editInsuranceType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    callPopupWindow("InsuranceType",insuranceList);
                }
            }
        });
        editInsuranceDate=(TextInputEditText)view.findViewById(R.id.OMD_MID_insurance_date);
        if (INSURANCE_START_DATE!=null && INSURANCE_END_DATE!=null){
            editInsuranceDate.setText(INSURANCE_START_DATE+" to "+INSURANCE_END_DATE);
        }
        editInsuranceDate.setInputType(InputType.TYPE_NULL);
        editInsuranceDate.setOnClickListener(this);
        editInsuranceDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                   callCosmosCalendarDialog();
                }
            }
        });


        editInsuranceClaimedYear=(TextInputEditText)view.findViewById(R.id.OMD_MID_Insurance_claimedyear);
        if (INSURANCE_CLAIMED_YEAR!=null){
            editInsuranceClaimedYear.setText(INSURANCE_CLAIMED_YEAR);
        }
        editInsuranceClaimedYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                INSURANCE_CLAIMED_YEAR=s.toString();
            }
        });
        editMachineAge=(TextInputEditText)view.findViewById(R.id.OMD_MID_machine_age);
        if (MACHINE_AGE!=null){
            editMachineAge.setText(MACHINE_AGE);
        }
        editMachineAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               MACHINE_AGE=s.toString();
            }
        });
        editNCB=(TextInputEditText)view.findViewById(R.id.OMD_MID_NCB);
        if (INSURANCE_NCB!=null){
            editNCB.setText(INSURANCE_NCB);
        }
        editNCB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                INSURANCE_NCB=s.toString();
            }
        });

        checkBoxInsurance.setChecked(INSURANCE_FALG);
        editInsuranceCompany.setEnabled(true);
        editInsuranceType.setEnabled(true);
        editInsuranceDate.setEnabled(true);
        editInsuranceClaimedYear.setEnabled(true);
        editMachineAge.setEnabled(true);
        editNCB.setEnabled(true);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.OMD_MID_insurance_type:
                callPopupWindow("InsuranceType",insuranceList);
                break;
            case R.id.OMD_MID_insurance_date:
                callCosmosCalendarDialog();
                break;
        }
    }

    private void callPopupWindow(String tilte, ArrayList<String> listData) {
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);
        popupWindow.setAnchorView(editInsuranceType);
        popupWindow.setWidth(editInsuranceType.getMeasuredWidth());
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editInsuranceType.setText(listData.get(position));
                INSURANCE_TYPE_CODE=insuranceMap.get(listData.get(position));
                popupWindow.dismiss();

            }
        });
        popupWindow.show();
    }


    public void callInsuranceTypeApi(){
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> loadCapacity = apiInterface.getCommonLookUp(INSURANCE_TYPE);
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
        insuranceMap.clear();
        insuranceList.clear();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            insuranceList.add(key);
            try {
                insuranceMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // setHashMap(commonMap, check);
    }

    private void callCosmosCalendarDialog() {

       /* CalendarDialog calendarDialog=new CalendarDialog(getActivity(), new OnDaysSelectionListener() {
            @Override
            public void onDaysSelected(List<Day> selectedDays) {
                if (selectedDays.size()>0){
                    Toast.makeText(getActivity(), selectedDays.get(0).getCalendar().getTime().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendarDialog.setTitle("StartDate & EndDate");
        calendarDialog.setSelectionType(SelectionType.RANGE);
        calendarDialog.setCancelable(false);
        calendarDialog.show();*/

        CalendarDialog calendarDialog=new CalendarDialog(getActivity());
        calendarDialog.setOnDaysSelectionListener(new OnDaysSelectionListener() {
            @Override
            public void onDaysSelected(List<Day> selectedDays) {
                if (selectedDays.size()>0){
                    Date startDate=selectedDays.get(0).getCalendar().getTime();
                    Date endDate=selectedDays.get(selectedDays.size()-1).getCalendar().getTime();
                    editInsuranceDate.setText(convertddMMYYYYtoYYYYMMdd(startDate)+" to "+convertddMMYYYYtoYYYYMMdd(endDate));



                    SimpleDateFormat input=new SimpleDateFormat("YYYY-MM-dd");
                    // SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd")
                    INSURANCE_START_DATE=input.format(startDate);
                    INSURANCE_END_DATE=input.format(endDate);

                   /* for (int i=0;i<selectedDays.size();i++){
                        Toast.makeText(getActivity(), selectedDays.get(i).getCalendar().getTime().toString(), Toast.LENGTH_SHORT).show();
                    }*/

                }
            }
        });

        calendarDialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        calendarDialog.show();


    }

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public String convertddMMYYYYtoYYYYMMdd(Date date){

        String outputDate="";
        SimpleDateFormat input=new SimpleDateFormat("dd/MM/yyyy");
       // SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");

        outputDate=input.format(date);

        return outputDate;
    }
}
