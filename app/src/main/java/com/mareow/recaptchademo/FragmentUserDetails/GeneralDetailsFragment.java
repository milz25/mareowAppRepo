package com.mareow.recaptchademo.FragmentUserDetails;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.MultipleSelectionAdapter;
import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.DataModels.NewUser;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
 * Use the {@link GeneralDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //AppCompatImageView mProfileImage;

    TextInputEditText editFirstname;
    TextInputEditText editLastname;
    TextInputEditText editDescribeYourSelf;
    TextInputEditText editSegment;


    FloatingActionButton btnSave;


    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    NewUser newUser=new NewUser();

    Uri photoURI;
    String imageFilePath;
    private static final int CROP_PIC=1001;

    ArrayList<String> segmentItems=new ArrayList<>();
    HashMap<String,String> segmentMap=new HashMap<>();

    String[] listItems=null;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();

    public static String firstName=null;
    public static String lastName=null;
    public static String mAboutYourSelf=null;
    public static List<String> segmentList=new ArrayList<>();

    public List<String> segmentNameValue=new ArrayList<>();
    List<AbleToRunMachine> ableToRunMachineList=new ArrayList<>();

    public GeneralDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralDetailsFragment newInstance(String param1, String param2) {
        GeneralDetailsFragment fragment = new GeneralDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_profile, container, false);
       // MainActivity.navItemIndex=17;
        initView(view);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //apiInterface= ApiClient.getClient().create(ApiInterface.class);
       // progressDialog=new ProgressDialog(getContext());
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       // progressDialog.setMessage("Please wait.........");
        callPreferedSegment();
        if (Constants.MY_PROFILE){
            callMachineDetailsApi();
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }
    private void initView(View view) {
       // mProfileImage=(AppCompatImageView)view.findViewById(R.id.myprofile_profileimage);
       // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
        //RoundedBitmapDrawable roundedImageDrawable = createRoundedBitmapImageDrawableWithBorder(bitmap);
        //mProfileImage.setImageDrawable(roundedImageDrawable);

      //  mProfileImage.setOnClickListener(this);

        editFirstname=(TextInputEditText)view.findViewById(R.id.myprofile_firstname);
        editFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firstName=s.toString();
            }
        });
        editFirstname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    firstName=editFirstname.getText().toString();
                }
            }
        });

        editLastname=(TextInputEditText)view.findViewById(R.id.myprofile_lastname);
        editLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lastName=s.toString();
            }
        });
        editLastname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    lastName=editLastname.getText().toString();
                }
            }
        });

        editDescribeYourSelf=(TextInputEditText)view.findViewById(R.id.myprofile_describe_yourself);
        editDescribeYourSelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAboutYourSelf=s.toString();
            }
        });

        editDescribeYourSelf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAboutYourSelf=editDescribeYourSelf.getText().toString();
                }
            }
        });

        editSegment=(TextInputEditText)view.findViewById(R.id.myprofile_segment);
        editSegment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideKeyBoard();
                    //showSpinnerMultiselection();
                    showCustomMultipleSelectionDialog();
                    //showSpinnerMultiselection();
                }
            }
        });




        editSegment.setOnClickListener(this);

      /*  editUsername=(TextInputEditText)view.findViewById(R.id.myprofile_username);
        editEmail=(TextInputEditText)view.findViewById(R.id.myprofile_email);
        editMobile=(TextInputEditText)view.findViewById(R.id.myprofile_mobile);
        btnSave=(FloatingActionButton) view.findViewById(R.id.myprofile_update);
        btnSave.setOnClickListener(this);*/

      if (Constants.USER_ROLE.equals("Supervisor")){
          editSegment.setVisibility(View.GONE);
      }

        if (Constants.MY_PROFILE){

            if (firstName!=null){
                editFirstname.setText(firstName);
            }else {
                editFirstname.setText(MyProfileFragment.mUSerProfileDataList.getFirstName());
                firstName=MyProfileFragment.mUSerProfileDataList.getFirstName();
            }

            if (lastName!=null){
                editLastname.setText(lastName);
            }else {
                editLastname.setText(MyProfileFragment.mUSerProfileDataList.getLastName());
                lastName=MyProfileFragment.mUSerProfileDataList.getLastName();
            }


            if (MyProfileFragment.mUSerProfileDataList.getAboutYourself()!=null){
                if (mAboutYourSelf!=null){
                    editDescribeYourSelf.setText(mAboutYourSelf);
                }else {
                    editDescribeYourSelf.setText(MyProfileFragment.mUSerProfileDataList.getAboutYourself());
                    mAboutYourSelf=MyProfileFragment.mUSerProfileDataList.getAboutYourself();
                }

            }

        }else {

            if (firstName!=null){
                editFirstname.setText(firstName);
            }else {
                editFirstname.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null));
                firstName=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null);
            }

            if (lastName!=null){
                editLastname.setText(lastName);
            }else {
                editLastname.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null));
                lastName=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null);
            }


            /*editSegment.setText("Construction Machineries");
            segmentList.add("CM");*/
        }


    }

    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editSegment.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.myprofile_segment:
                hideKeyBoard();
               // showSpinnerMultiselection();
                //showSpinnerMultiselection();
                showCustomMultipleSelectionDialog();
                break;
        }
    }


    private void callPreferedSegment() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> segmentCall=apiInterface.getPreferedSegment();
        segmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject);
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
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject segmentResponse) {
        segmentItems.clear();
        segmentMap.clear();
        listItems=null;
        for(Iterator<String> iter = segmentResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            segmentItems.add(key);
            try {
                segmentMap.put(key,segmentResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String[] items=new String[segmentItems.size()];
        for(int i=0;i<segmentItems.size();i++)
            items[i]=segmentItems.get(i);
        listItems=items;
        checkedItems=new boolean[listItems.length];
        if (Constants.MY_PROFILE){
            segmentList=MyProfileFragment.mUSerProfileDataList.getSegment();
            segmentNameValue.clear();
            mUserItems.clear();
            String newitems="";
            for (int i=0;i<segmentList.size();i++){
                for (String o : segmentMap.keySet()) {
                    if (segmentMap.get(o).equals(segmentList.get(i))) {
                        if (i==segmentList.size()-1){
                            newitems=newitems+o;
                        }else {
                            newitems=newitems+o+", ";
                        }
                        segmentNameValue.add(o);
                    }
                }
              }
             editSegment.setText(newitems);
            for (int i=0;i<segmentNameValue.size();i++){
                for (int j=0;j<segmentItems.size();j++){
                    if (segmentItems.get(j).equals(segmentNameValue.get(i))){
                        checkedItems[j]=true;
                        mUserItems.add(j);
                    }
                }
            }
        }else {
               if (segmentList.size()==0){
                       String segvalue=null;
                       for (Map.Entry<String,String> entry : segmentMap.entrySet()) {
                           if (entry.getValue().equals("CM")){
                               segmentList.add("CM");
                               segvalue=entry.getKey();
                               editSegment.setText(entry.getKey());
                           }
                       }

                       for (int i=0;i<segmentItems.size();i++){
                           if (segmentItems.get(i).equals(segvalue)){
                               checkedItems[i]=true;
                               mUserItems.add(i);
                           }
                       }


               }else {
                   String newitems="";
                   segmentNameValue.clear();
                   mUserItems.clear();
                   for (int i=0;i<segmentList.size();i++){
                       for (String o : segmentMap.keySet()) {
                           if (segmentMap.get(o).equals(segmentList.get(i))) {
                               if (i==segmentList.size()-1){
                                   newitems=newitems+o;
                               }else {
                                   newitems=newitems+o+", ";
                               }
                               segmentNameValue.add(o);
                           }
                       }
                   }
                   editSegment.setText(newitems);
                   for (int i=0;i<segmentNameValue.size();i++){
                       for (int j=0;j<segmentItems.size();j++){
                           if (segmentItems.get(j).equals(segmentNameValue.get(i))){
                               checkedItems[j]=true;
                               mUserItems.add(j);
                           }
                       }
                   }
               }
        }
    }
   /* public void showSpinnerMultiselection(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
        AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
        titleText.setText("Segments");
        mBuilder.setCustomTitle(view);
       // mBuilder.setTitle("Segments");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                    }
                }else if (mUserItems.contains(position)){
                    if (ableToRunMachineList.size()>0){
                        for (int i=0;i<ableToRunMachineList.size();i++){
                            if (listItems[position].equals(ableToRunMachineList.get(i).getSegmentMeaning())){
                                checkedItems[position]=true;
                               // ((CheckBox)dialog).setChecked(true);
                            }
                        }
                    }else {
                        mUserItems.remove(mUserItems.indexOf(position));
                    }


                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";
                if (mUserItems.size()==0){
                    item="";
                }else {
                    for(int i=0;i<mUserItems.size();i++){
                        item=item+listItems[mUserItems.get(i)];
                        if (i!=mUserItems.size()-1){
                            item=item+", ";
                        }
                    }
                }
                editSegment.setText("");
                editSegment.setText(item);

                segmentList.clear();
                for (int i=0;i<mUserItems.size();i++){
                    if (segmentMap.containsKey(segmentItems.get(mUserItems.get(i))))
                        segmentList.add(segmentMap.get(segmentItems.get(mUserItems.get(i))));
                }
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<checkedItems.length;i++){
                    checkedItems[i]=false;
                    mUserItems.clear();
                    editSegment.setText("");
                }
            }
        });

        AlertDialog alertDialog=mBuilder.create();
        alertDialog.show();
    }
*/


    public void showCustomMultipleSelectionDialog(){
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
                        item=item+segmentItems.get(mUserItems.get(i));
                        if (i!=mUserItems.size()-1){
                            item=item+",";
                        }
                    }
                editSegment.setText("");
                editSegment.setText(item);

                segmentList.clear();
                for (int i=0;i<mUserItems.size();i++){
                    if (segmentMap.containsKey(segmentItems.get(mUserItems.get(i))))
                        segmentList.add(segmentMap.get(segmentItems.get(mUserItems.get(i))));
                }
            }
        });

        titleText.setText("Segment");
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

        MultipleSelectionAdapter recycleAdapter=new MultipleSelectionAdapter(getActivity(),segmentItems,listener,checkedItems,ableToRunMachineList);
        //  SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(context,listData,listener);
        multipleRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }
  private void callMachineDetailsApi() {

     // progressDialog.show();
      String token= TokenManager.getSessionToken();
      int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
      ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
      Call<List<AbleToRunMachine>> ableToRunCall=apiInterface.getAbleToRunMachineDetails("Bearer "+token,partyId);
      ableToRunCall.enqueue(new Callback<List<AbleToRunMachine>>() {
          @Override
          public void onResponse(Call<List<AbleToRunMachine>> call, Response<List<AbleToRunMachine>> response) {
             // progressDialog.dismiss();
              if (response.isSuccessful()){

                  ableToRunMachineList=response.body();

              }else {
                  if (response.code() == 401) {
                      TokenExpiredUtils.tokenExpired(getActivity());
                  }
                  if (response.code() == 404) {
                      Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                  }
              }
          }

          @Override
          public void onFailure(Call<List<AbleToRunMachine>> call, Throwable t) {
             // progressDialog.dismiss();
              Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
  }


}
