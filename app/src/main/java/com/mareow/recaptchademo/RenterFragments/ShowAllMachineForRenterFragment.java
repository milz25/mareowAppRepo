package com.mareow.recaptchademo.RenterFragments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.Adapters.ShowAllMachineAdapter;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class ShowAllMachineForRenterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mShowAllRecycleView;
    AppCompatTextView mTitle;

     List<RenterMachine> machineList=new ArrayList<>();
    public ShowAllMachineForRenterFragment(List<RenterMachine> machineList) {
        // Required empty public constructor
        this.machineList=machineList;
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
        View view = inflater.inflate(R.layout.fragment_show_all_machine_for_renter, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mTitle=(AppCompatTextView)view.findViewById(R.id.show_all_machine_title);


       /* if (Constants.CATEGORY_CHECK){
            mTitle.setText(machineList.get(0).getCategoryName());
        }else {
            mTitle.setText(machineList.get(0).getSubCategoryName());
        }*/

        String category=machineList.get(0).getCategoryName();
        String subCate=machineList.get(0).getSubCategoryName();

        SpannableString ss1=  new SpannableString(category);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,category.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, category.length(), 0);// set color

        SpannableString ss2=  new SpannableString(subCate);
        ss2.setSpan(new RelativeSizeSpan(0.5f), 0,subCate.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, subCate.length(), 0);

        SpannableStringBuilder builder=new SpannableStringBuilder();
        builder.append(ss1+"\n");
        builder.append(ss2);

        mTitle.setText(builder);


        mShowAllRecycleView = (RecyclerView) view.findViewById(R.id.show_all_machine_recycle);
        mShowAllRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mShowAllRecycleView.setHasFixedSize(false);
        mShowAllRecycleView.addItemDecoration(new SpacesItemDecoration(5));
        mShowAllRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        callAdapter();
    }

    private void callAdapter() {

        if (machineList.size()>0){
            ShowAllMachineAdapter allMachineAdapter=new ShowAllMachineAdapter(getActivity(),machineList);
            mShowAllRecycleView.setAdapter(allMachineAdapter);
        }
    }

}
