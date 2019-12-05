package com.mareow.recaptchademo.FragmentUserDetails;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegmentOwnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SegmentOwnerFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatTextView btnNext;
    AppCompatTextView segmentSpinner;

    ArrayList<String> segmentItems=new ArrayList<>();
    HashMap<String,String> segmentMap=new HashMap<>();
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();

    public SegmentOwnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SegmentOwnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SegmentOwnerFragment newInstance(String param1, String param2) {
        SegmentOwnerFragment fragment = new SegmentOwnerFragment();
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
        View view=inflater.inflate(R.layout.fragment_segment_owner, container, false);
        initView(view);
        callPreferedSegment();
        return view;
    }

    private void initView(View view) {
        btnNext=(AppCompatTextView)view.findViewById(R.id.owner_segment_next);
        segmentSpinner=(AppCompatTextView)view.findViewById(R.id.owner_segment_spinner);
        segmentSpinner.setOnClickListener(this);

        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.owner_segment_next:
                callNextFragment();
                break;
            case R.id.owner_segment_spinner:
                break;
        }
    }
    private void callNextFragment() {
        Fragment referFragment = new GSTDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, referFragment); // give your fragment container id in first parameter
        transaction.commit();
    }


    public void showSpinnerMultiselection(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Choose an items");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                    }
                }else if (mUserItems.contains(position)){
                    mUserItems.remove(position);
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";
                for(int i=0;i<mUserItems.size();i++){
                    item=item+listItems[mUserItems.get(i)];
                    if (i!=mUserItems.size()-1){
                        item=item+",";
                    }
                }

                segmentSpinner.setText(item);
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
                    segmentSpinner.setText("");
                }
            }
        });

        AlertDialog alertDialog=mBuilder.create();
        alertDialog.show();
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

    private void parseJSONObject(JSONObject roleResponse) {
        for(Iterator<String> iter = roleResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            segmentItems.add(key);
            try {
                segmentMap.put(key, roleResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String[] items=new String[segmentItems.size()];
        for(int i=0;i<segmentItems.size();i++)
            items[i]=segmentItems.get(i);
        listItems=items;
        checkedItems=new boolean[listItems.length];
    }
}
