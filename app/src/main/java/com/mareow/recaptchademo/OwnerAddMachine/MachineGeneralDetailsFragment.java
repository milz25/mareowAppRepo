package com.mareow.recaptchademo.OwnerAddMachine;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.MultiSelectionForAttachment;
import com.mareow.recaptchademo.Adapters.MultipleSelctionAttachmentOwner;
import com.mareow.recaptchademo.Adapters.MultipleSelctionAttachmentRenter;
import com.mareow.recaptchademo.Adapters.MultipleSingelSelctionAttachmentOwner;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
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
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineGeneralDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineGeneralDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editSegment;
    TextInputEditText editCategory;
    TextInputEditText editSubCategory;
    TextInputEditText editManufacturer;
    TextInputEditText editSpecification;
    TextInputEditText editModel;
    TextInputEditText editAttachment;
    TextInputEditText editDefualtAttachment;
    TextInputLayout modelHint;


    HashMap<String,String>  segmentMap = new HashMap<>();
    HashMap<String, String> categoryMap = new HashMap<>();
    HashMap<String, String> subCategoryMap = new HashMap<>();
    HashMap<String, String> manufacturerMap = new HashMap<>();
    HashMap<String, String> modelNoMap = new HashMap<>();
    HashMap<String, String> attachmentMap = new HashMap<>();
    HashMap<String, String> defaultAttachmentMap = new HashMap<>();




    public static String SEGMENT_CODE=null;
    public static String CATEGORY_CODE=null;
    public static String SUBCATEGORY_CODE=null;
    public static String MANUFACTURER_CODE=null;
    public static int MODEL_CODE=0;
    public static String MODEL_CODE_NAME=null;
    public static String SPECIFICATION_URL=null;

    public static int DEFUALT_ATTACHMENT_CODE=0;
    public static List<Integer> selectedattachmentList=new ArrayList<>();
    public static List<String> defaultSelectedattachmentList=new ArrayList<>();


    ApiInterface apiInterface;
    String[] listItems=null;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();
    ArrayList<String> attachmentListItem=new ArrayList<>();

    boolean[] dcheckedItems;
    ArrayList<Integer> dUserItems=new ArrayList<>();
    ArrayList<String> defaultAttachmentListItem=new ArrayList<>();

    String[] defaultAttachmentArray=null;
    private int INTIAL_CHECk=0;
    private int CHECKED_ITEM=-1;
    public MachineGeneralDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineGeneralDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineGeneralDetailsFragment newInstance(String param1, String param2) {
        MachineGeneralDetailsFragment fragment = new MachineGeneralDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine_general_details, container, false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if(INTIAL_CHECk ==0){

            callGetSegmentData();
            callMachineAttachmentData();
            INTIAL_CHECk++;
        }

        initView(view);
        return view;
    }

    private void initView(View view) {

        modelHint=(TextInputLayout)view.findViewById(R.id.OMD_model_hint);
        editSegment=(TextInputEditText)view.findViewById(R.id.OMD_segament);
        editSegment.setInputType(InputType.TYPE_NULL);
        editCategory=(TextInputEditText)view.findViewById(R.id.OMD_category);
        if (CATEGORY_CODE!=null){
            editCategory.setText(CATEGORY_CODE);
        }
        editCategory.setInputType(InputType.TYPE_NULL);

        editSubCategory=(TextInputEditText)view.findViewById(R.id.OMD_subcategory);
        if (SUBCATEGORY_CODE!=null){
            editSubCategory.setText(SUBCATEGORY_CODE);
        }
        editSubCategory.setInputType(InputType.TYPE_NULL);
        editManufacturer=(TextInputEditText)view.findViewById(R.id.OMD_manufacturer);
        if (MANUFACTURER_CODE!=null){
            editManufacturer.setText(MANUFACTURER_CODE);
        }
        editManufacturer.setInputType(InputType.TYPE_NULL);
        editModel=(TextInputEditText)view.findViewById(R.id.OMD_model);

        if (MODEL_CODE_NAME!=null){
            editModel.setText(MODEL_CODE_NAME);
        }
        editModel.setInputType(InputType.TYPE_NULL);
        editSpecification=(TextInputEditText)view.findViewById(R.id.OMD_specification);
        if (SPECIFICATION_URL!=null){
            editSpecification.setText(SPECIFICATION_URL);
        }
        editSpecification.setInputType(InputType.TYPE_NULL);
        editAttachment=(TextInputEditText)view.findViewById(R.id.OMD_attachment);
        /*if (selectedattachmentList.size()>0){
            for (int i=0;i<selectedattachmentList.size();i++){
                if (i==selectedattachmentList.size()-1){
                    editAttachment.append(selectedattachmentList.get(i));
                }else {
                    editAttachment.append(selectedattachmentList.get(i)+",");
                }

            }
        }*/
        editAttachment.setInputType(InputType.TYPE_NULL);
        editDefualtAttachment=(TextInputEditText)view.findViewById(R.id.OMD_default_attachment);
       /* if (DEFUALT_ATTACHMENT_CODE!=0){
            editDefualtAttachment.setText(String.valueOf(DEFUALT_ATTACHMENT_CODE));
        }*/
        editDefualtAttachment.setInputType(InputType.TYPE_NULL);


        editSegment.setOnClickListener(this);
        editCategory.setOnClickListener(this);
        editSubCategory.setOnClickListener(this);
        editManufacturer.setOnClickListener(this);
        editModel.setOnClickListener(this);
        editAttachment.setOnClickListener(this);
        editDefualtAttachment.setOnClickListener(this);


        editSegment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = true;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=false;
                    Constants.DEFUALT_ATTACHMENT=false;
                    callCustomeDialog(segmentMap, 0,"Segment");
                }
            }
        });

        editCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = true;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=false;
                    Constants.DEFUALT_ATTACHMENT=false;
                    callCustomeDialog(categoryMap, 1,"Category");
                }
            }
        });
        editSubCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = true;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=false;
                    Constants.DEFUALT_ATTACHMENT=false;
                    callCustomeDialog(subCategoryMap, 2,"Subcategory");
                }
            }
        });
        editManufacturer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = true;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=false;
                    Constants.DEFUALT_ATTACHMENT=false;
                    callCustomeDialog(manufacturerMap, 3,"Manufacturer");
                }
            }
        });
        editModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (modelNoMap.size()>0){
                        Constants.SEGMENT = false;
                        Constants.CATEGORY = false;
                        Constants.SUBCATEGORY = false;
                        Constants.MANUFACTURER = false;
                        Constants.MODEL_NO = true;
                        Constants.ATTACHMENT=false;
                        Constants.DEFUALT_ATTACHMENT=false;
                        callCustomeDialog(modelNoMap, 4,"Model");
                    }
                }
            }
        });

        editAttachment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                  /*  Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=true;
                    Constants.DEFUALT_ATTACHMENT=false;
                    callCustomeDialog(attachmentMap, 5,"Attachment");*/
                  if (attachmentMap.size()>0){
                      showCustomMultipleSelectionDialog();
                  }else {
                      Toast.makeText(getActivity(), "No data available!", Toast.LENGTH_SHORT).show();
                  }

                }
            }
        });

        editDefualtAttachment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                    /*Constants.SEGMENT = false;
                    Constants.CATEGORY = false;
                    Constants.SUBCATEGORY = false;
                    Constants.MANUFACTURER = false;
                    Constants.MODEL_NO = false;
                    Constants.ATTACHMENT=false;
                    Constants.DEFUALT_ATTACHMENT=true;
                    callCustomeDialog(defaultAttachmentMap, 6,"Default Attachment");*/

                   // showCustomMultipleSelectionDialog();

                    callFilterSelection();
                }
            }
        });


        /*if (selectedattachmentList.size()>0){
            checkedItems=new boolean[attachmentListItem.size()];
            for (int i=0;i<selectedattachmentList.size();i++){
                for(Map.Entry entry: attachmentMap.entrySet()){
                    if(selectedattachmentList.get(i).equals(entry.getValue())){
                        int index=attachmentListItem.indexOf(entry.getKey());
                        if (!mUserItems.contains(index)){
                            checkedItems[index]=true;
                            mUserItems.add(index);
                        }
                        break; //breaking because its one to one map
                    }
                }
            }
        }*/

        if (Constants.MACHINE_UPDATE){

            editSegment.setEnabled(false);
            editCategory.setEnabled(false);
            editSubCategory.setEnabled(false);
            editManufacturer.setEnabled(false);
            editModel.setEnabled(false);


            if (selectedattachmentList.size()>0){
                for (int i=0;i<selectedattachmentList.size();i++){
                    for(Map.Entry entry: attachmentMap.entrySet()){
                        if(selectedattachmentList.get(i).equals(entry.getValue())){
                            editAttachment.setText((String)entry.getKey());
                            int index=attachmentListItem.indexOf(entry.getKey());
                            if (!mUserItems.contains(index)){
                                checkedItems[index]=true;
                                mUserItems.add(index);
                            }
                            break; //breaking because its one to one map
                        }
                    }
                }
            }

        }



    }

    private void callFilterSelection() {

        if (selectedattachmentList.size()>0){
            defaultAttachmentListItem.clear();
            for (int i=0;i<selectedattachmentList.size();i++){
                for(Map.Entry entry: attachmentMap.entrySet()){
                    if(String.valueOf(selectedattachmentList.get(i)).equals(entry.getValue())){
                        defaultAttachmentListItem.add((String) entry.getKey());
                        break; //breaking because its one to one map
                    }
                }
            }

            dcheckedItems=new boolean[defaultAttachmentListItem.size()];

            if (dUserItems.size()>0){
                for (int i=0;i<dUserItems.size();i++){
                     dcheckedItems[dUserItems.get(i)]=true;
                }
            }


            showDefaultSingelSelectionDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.OMD_segament:
                Constants.SEGMENT = true;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(segmentMap, 0,"Segment");
                break;
            case R.id.OMD_category:
                Constants.SEGMENT = false;
                Constants.CATEGORY = true;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(categoryMap, 1,"Category");
                break;
            case R.id.OMD_subcategory:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = true;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(subCategoryMap, 2,"Subcategory");
                break;
            case R.id.OMD_manufacturer:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = true;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(manufacturerMap, 3,"Manufacturer");
                break;
            case R.id.OMD_model:
                Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = true;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(modelNoMap, 4,"Model");
                break;
            case R.id.OMD_attachment:
               /* Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=true;
                Constants.DEFUALT_ATTACHMENT=false;
                callCustomeDialog(modelNoMap, 5,"Attachment");*/
                if (attachmentMap.size()>0){
                    showCustomMultipleSelectionDialog();
                }else {
                    Toast.makeText(getActivity(), "No data available!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.OMD_default_attachment:
               /* Constants.SEGMENT = false;
                Constants.CATEGORY = false;
                Constants.SUBCATEGORY = false;
                Constants.MANUFACTURER = false;
                Constants.MODEL_NO = false;
                Constants.ATTACHMENT=false;
                Constants.DEFUALT_ATTACHMENT=true;
                callCustomeDialog(defaultAttachmentMap, 6,"Default Attachment");*/
                callFilterSelection();
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
        showPopWindowForView(tilte,listData);
    }
    private void showPopWindowForView(String tilte, ArrayList<String> listData) {
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);

        if (Constants.SEGMENT){
            popupWindow.setAnchorView(editSegment);
            popupWindow.setWidth(editSegment.getMeasuredWidth());
        }else if (Constants.CATEGORY){
            popupWindow.setAnchorView(editCategory);
            popupWindow.setWidth(editCategory.getMeasuredWidth());
        }else if (Constants.SUBCATEGORY){
            popupWindow.setAnchorView(editSubCategory);
            popupWindow.setWidth(editSubCategory.getMeasuredWidth());
        }else if (Constants.MANUFACTURER){
            popupWindow.setAnchorView(editManufacturer);
            popupWindow.setWidth(editManufacturer.getMeasuredWidth());
        }else if (Constants.MODEL_NO) {
            popupWindow.setAnchorView(editModel);
            popupWindow.setWidth(editModel.getMeasuredWidth());
        }else if (Constants.ATTACHMENT){
            popupWindow.setAnchorView(editAttachment);
            popupWindow.setWidth(editAttachment.getMeasuredWidth());
        }else if (Constants.DEFUALT_ATTACHMENT){
            popupWindow.setAnchorView(editDefualtAttachment);
            popupWindow.setWidth(editDefualtAttachment.getMeasuredWidth());
        }


        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Constants.SEGMENT){
                    editSegment.setText(listData.get(position));

                    editCategory.setText("");
                    editSubCategory.setText("");
                    editManufacturer.setText("");
                    editModel.setText("");
                    editSpecification.setText("");


                    SEGMENT_CODE=segmentMap.get(listData.get(position));
                    categoryMap.clear();
                    subCategoryMap.clear();
                    manufacturerMap.clear();
                    modelNoMap.clear();
                    callGetCategoryData(listData.get(position));
                }
                if (Constants.CATEGORY){
                    editCategory.setText(listData.get(position));

                    editSubCategory.setText("");
                    editManufacturer.setText("");
                    modelHint.setHint("Model *");
                    editModel.setInputType(InputType.TYPE_NULL);
                    editModel.setText("");
                    editSpecification.setText("");

                    CATEGORY_CODE=categoryMap.get(listData.get(position));

                    subCategoryMap.clear();
                    manufacturerMap.clear();
                    modelNoMap.clear();
                    callSubCategoryData(listData.get(position));
                }
                if (Constants.SUBCATEGORY){
                    editSubCategory.setText(listData.get(position));

                    editManufacturer.setText("");
                    editModel.setText("");
                    modelHint.setHint("Model *");
                    editModel.setInputType(InputType.TYPE_NULL);
                    editSpecification.setText("");

                    SUBCATEGORY_CODE=subCategoryMap.get(listData.get(position));

                    manufacturerMap.clear();
                    modelNoMap.clear();
                    callMenufacturerData();
                }
                if (Constants.MANUFACTURER){
                    editManufacturer.setText(listData.get(position));

                    editModel.setText("");
                    modelHint.setHint("Model *");
                    editModel.setInputType(InputType.TYPE_NULL);
                    editSpecification.setText("");

                    MANUFACTURER_CODE=manufacturerMap.get(listData.get(position));

                    modelNoMap.clear();
                    callModelNoData();
                }
                if (Constants.MODEL_NO){
                    editModel.setText(listData.get(position));

                    editSpecification.setText("");
                    MODEL_CODE=Integer.parseInt(modelNoMap.get(listData.get(position)));
                    callMachineUrlData();
                }
               /* if (Constants.ATTACHMENT){
                    editAttachment.setText(listData.get(position));
                }
                if (Constants.DEFUALT_ATTACHMENT){
                    editDefualtAttachment.setText(listData.get(position));
                    DEFUALT_ATTACHMENT_CODE=Integer.parseInt(defaultAttachmentMap.get(listData.get(position)));
                }*/

                popupWindow.dismiss();

            }
        });
        popupWindow.show();
    }
    private void callGetSegmentData() {
        TokenManager.showProgressDialog(getActivity());
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<ResponseBody> segmentcall = apiInterface.getPreferedFilteredSegment(partyId);
        segmentcall.enqueue(new Callback<ResponseBody>() {
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

    private void callGetCategoryData(String key) {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> categoryCall = apiInterface.getSegmentCategory(segmentMap.get(key));
        categoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String category = response.body().string();
                        JSONObject jsonObject = new JSONObject(category);
                        parseJSONObject(jsonObject, 1);
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
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSubCategoryData(String key) {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> subCategoryCall = apiInterface.getSegmentSubCategory(categoryMap.get(key));
        subCategoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String subCategory = response.body().string();
                        JSONObject jsonObject = new JSONObject(subCategory);
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
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMenufacturerData() {
        TokenManager.showProgressDialog(getActivity());
        Call<ResponseBody> manufacturerCall = apiInterface.getManufacturer();
        manufacturerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String manufacturer = response.body().string();
                        JSONObject jsonObject = new JSONObject(manufacturer);
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
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callModelNoData() {
        TokenManager.showProgressDialog(getActivity());
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        Call<ResponseBody> modelNoCall = apiInterface.getModelNoCombo(SEGMENT_CODE,CATEGORY_CODE,SUBCATEGORY_CODE,MANUFACTURER_CODE,partyId);
        modelNoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    try {
                        String modelNo = response.body().string();
                        JSONObject jsonObject = new JSONObject(modelNo);
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
                        modelHint.setHint("Model (Other) *");
                        editModel.setInputType(InputType.TYPE_CLASS_TEXT);
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
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMachineUrlData() {
        TokenManager.showProgressDialog(getActivity());
        String token= TokenManager.getSessionToken();
        Call<String> modelNoCall = apiInterface.getMachineUrl("Bearer "+token,MODEL_CODE);
        modelNoCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    editSpecification.setText(response.body().toString());
                    SPECIFICATION_URL=response.body().toString();
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
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMachineAttachmentData() {
        
        TokenManager.showProgressDialog(getActivity());
        String token= TokenManager.getSessionToken();
        Call<ResponseBody> modelNoCall = apiInterface.getAllAttachmentMap();
        modelNoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()) {
                    String attachment = null;
                    try {
                        attachment = response.body().string();
                        JSONObject jsonObject = new JSONObject(attachment);
                        parseJSONObject(jsonObject, 5);
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
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void parseJSONObject(JSONObject jsonObject, int check) {
        if (check==5){
            attachmentListItem.clear();
            checkedItems=null;
        }

        HashMap<String, String> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            if (check==5){
                attachmentListItem.add(key);
            }
            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (check==5)
            checkedItems=new boolean[attachmentListItem.size()];
        setHashMap(commonMap, check);
    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 0:
                segmentMap = map;
                if (SEGMENT_CODE!=null){
                    for(Map.Entry entry: segmentMap.entrySet()){
                        if(SEGMENT_CODE.equals(entry.getValue())){
                            editSegment.setText(String.valueOf(entry.getKey()));
                            break; //breaking because its one to one map
                        }
                    }

                }
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
            case 5:
                 attachmentMap= map;
                break;
            case 6:
                defaultAttachmentMap = map;
                break;
        }

    }

  /*  public void showCustomMultipleSelectionDialog(){
        RecyclerView multipleRecycle;
        AppCompatTextView titleText;
        AppCompatButton btnCancel;
        AppCompatButton btnOk;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_multi_selection_dialog);

        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        multipleRecycle=(RecyclerView)dialog.findViewById(R.id.custom_multi_selection_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_multi_selection_dialog_title);
        btnCancel=(AppCompatButton)dialog.findViewById(R.id.custom_multi_selection_dialog_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk=(AppCompatButton)dialog.findViewById(R.id.custom_multi_selection_dialog_Ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String item="";
                for(int i=0;i<mUserItems.size();i++){
                    item=item+attachmentListItem.get(mUserItems.get(i));
                    if (i!=mUserItems.size()-1){
                        item=item+",";
                    }
                }
                editAttachment.setText("");
                editAttachment.setText(item);

                selectedattachmentList.clear();
                for (int i=0;i<mUserItems.size();i++){
                    if (attachmentMap.containsKey(attachmentListItem.get(mUserItems.get(i))))
                        selectedattachmentList.add(attachmentMap.get(attachmentListItem.get(mUserItems.get(i))));
                }
            }
        });

        titleText.setText("Attachments");
        multipleRecycle.setHasFixedSize(false);
        multipleRecycle.setItemAnimator(new DefaultItemAnimator());
        multipleRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (((AppCompatCheckBox)view).isChecked()){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                        checkedItems[position]=true;
                    }
                }else if (mUserItems.contains(position)){
                    mUserItems.remove(mUserItems.indexOf(position));
                    checkedItems[position]=false;
                }

            }

        };
        MultiSelectionForAttachment recycleAdapter=new MultiSelectionForAttachment(getActivity(),attachmentListItem,listener,checkedItems);
        //  SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(context,listData,listener);
        multipleRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }
*/
  public void showCustomMultipleSelectionDialog(){

      RecyclerView multipleRecycle;
      AppCompatTextView titleText;
      AppCompatImageButton btnClose;
      AppCompatButton btnOk;

      final Dialog dialog=new Dialog(getActivity());
      dialog.setCancelable(true);
      dialog.setContentView(R.layout.custome_attachment_selection);

      dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

      multipleRecycle=(RecyclerView)dialog.findViewById(R.id.attachment_dailog_recycle);
      // titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_multi_selection_dialog_title);
      btnClose=(AppCompatImageButton)dialog.findViewById(R.id.attachment_dailog_close);
      btnClose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
          }
      });
      btnOk=(AppCompatButton)dialog.findViewById(R.id.attachment_dailog_ok);
      btnOk.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
              String item="";
              for(int i=0;i<mUserItems.size();i++){
                  item=item+attachmentListItem.get(mUserItems.get(i));
                  if (i!=mUserItems.size()-1){
                      item=item+",";
                  }
              }
              editAttachment.setText("");
              editAttachment.setText(item);
              selectedattachmentList.clear();
              for (int i=0;i<mUserItems.size();i++){
                  if (attachmentMap.containsKey(attachmentListItem.get(mUserItems.get(i))))
                      selectedattachmentList.add(Integer.parseInt(attachmentMap.get(attachmentListItem.get(mUserItems.get(i)))));
              }

          }
      });

      // titleText.setText("Attachments");
      multipleRecycle.setHasFixedSize(false);
      multipleRecycle.setItemAnimator(new DefaultItemAnimator());
      multipleRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

      RecyclerViewClickListener listener = new RecyclerViewClickListener() {
          @Override
          public void onClick(View view, int position) {
              if (((AppCompatCheckBox)view).isChecked()){
                  if (!mUserItems.contains(position)){
                      mUserItems.add(position);
                      checkedItems[position]=true;
                  }
              }else if (mUserItems.contains(position)){
                  mUserItems.remove(mUserItems.indexOf(position));
                  checkedItems[position]=false;
              }
          }

      };

      MultipleSelctionAttachmentOwner recycleAdapter=new MultipleSelctionAttachmentOwner(getActivity(), attachmentListItem,listener,checkedItems);
      multipleRecycle.setAdapter(recycleAdapter);
      dialog.show();

  }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public void showDefaultMultipleSelectionDialog(){

        RecyclerView multipleRecycle;
        AppCompatTextView titleText;
        AppCompatImageButton btnClose;
        AppCompatButton btnOk;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custome_attachment_selection);

        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        multipleRecycle=(RecyclerView)dialog.findViewById(R.id.attachment_dailog_recycle);
        // titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_multi_selection_dialog_title);
        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.attachment_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk=(AppCompatButton)dialog.findViewById(R.id.attachment_dailog_ok);
        btnOk.setVisibility(View.GONE);
      /*  btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String item="";
                for(int i=0;i<dUserItems.size();i++){
                    item=item+defaultAttachmentListItem.get(dUserItems.get(i));
                    if (i!=dUserItems.size()-1){
                        item=item+",";
                    }
                }
                editDefualtAttachment.setText("");
                editDefualtAttachment.setText(item);
                defaultSelectedattachmentList.clear();
                for (int i=0;i<dUserItems.size();i++){
                    if (attachmentMap.containsKey(defaultAttachmentListItem.get(dUserItems.get(i))))
                        defaultSelectedattachmentList.add(attachmentMap.get(defaultAttachmentListItem.get(dUserItems.get(i))));
                }

            }
        });*/

        // titleText.setText("Attachments");
        multipleRecycle.setHasFixedSize(false);
        multipleRecycle.setItemAnimator(new DefaultItemAnimator());
        multipleRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (((AppCompatRadioButton)view).isChecked()){
                    if (!dUserItems.contains(position)){
                        dUserItems.add(position);
                        dcheckedItems[position]=true;
                    }
                }else if (dUserItems.contains(position)){
                    dUserItems.remove(dUserItems.indexOf(position));
                    dcheckedItems[position]=false;
                }

                dialog.dismiss();
            }

        };

        MultipleSingelSelctionAttachmentOwner recycleAdapter=new MultipleSingelSelctionAttachmentOwner(getActivity(), defaultAttachmentListItem,listener,dcheckedItems);
        multipleRecycle.setAdapter(recycleAdapter);
        dialog.show();

    }



    public void showDefaultSingelSelectionDialog(){

        defaultAttachmentArray=new String[defaultAttachmentListItem.size()];
        for(int i=0;i<defaultAttachmentListItem.size();i++){
            defaultAttachmentArray[i]=defaultAttachmentListItem.get(i);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Set DefaultAttachment");
        alert.setSingleChoiceItems(defaultAttachmentArray, CHECKED_ITEM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                editDefualtAttachment.setText(defaultAttachmentArray[position]);
                CHECKED_ITEM=position;
                DEFUALT_ATTACHMENT_CODE=Integer.parseInt(attachmentMap.get(defaultAttachmentArray[position]));
                dialog.dismiss();
            }
        });

        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();
    }

}
