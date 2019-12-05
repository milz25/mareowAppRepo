package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.Adapters.MachineAttachmentAdapter;
import com.mareow.recaptchademo.DataModels.MachineAttachment;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;

import java.util.List;

public class OfferAttachmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AppCompatTextView mNoRecordFound;
    RecyclerView mAttachmentRecycle;

    OfferWorkOrder offerWorkOrder;
    List<MachineAttachment> machineAttachments;
    public OfferAttachmentFragment(OfferWorkOrder offerWorkOrder) {
        // Required empty public constructor
        this.offerWorkOrder=offerWorkOrder;
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
        View view=inflater.inflate(R.layout.fragment_offer_attachment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mNoRecordFound=(AppCompatTextView)view.findViewById(R.id.ODF_attachment_details_no_record_found);
        mAttachmentRecycle=(RecyclerView) view.findViewById(R.id.ODF_attachment_details_recycleview);
        mAttachmentRecycle.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mAttachmentRecycle.setHasFixedSize(false);
        mAttachmentRecycle.setItemAnimator(new DefaultItemAnimator());
        mAttachmentRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        machineAttachments=offerWorkOrder.getWorkorderDTO().getMachine().getMachineAttachmentDTOs();

        if (machineAttachments.size()>0){
            MachineAttachmentAdapter attachmentAdapter=new MachineAttachmentAdapter(getActivity(),machineAttachments);
            mAttachmentRecycle.setAdapter(attachmentAdapter);
        }else {
            mAttachmentRecycle.setVisibility(View.GONE);
            mNoRecordFound.setVisibility(View.VISIBLE);

        }

    }

}
