package com.mareow.recaptchademo.RenterFragments;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.PaymentCustomeDialogAdapter;
import com.mareow.recaptchademo.Adapters.PaymentDetailsAdapter;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PaymentDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // FloatingActionButton btnBankDetails;
    FloatingActionButton btnAddPayment;

    RecyclerView mPaymentDetailsRecycle;
    TextInputEditText editWorkOrder;
    AppCompatImageView btnWorkOrderMore;

    AppCompatImageView btnWorkOrderStatus;

    List<InvoiceByUser> invoiceByUserList;
    OfferWorkOrder workOrderDetails;
    public PaymentDetailsFragment(List<InvoiceByUser> invoiceByUserList, OfferWorkOrder workOrders) {
        // Required empty public constructor
        this.invoiceByUserList=invoiceByUserList;
        this.workOrderDetails=workOrders;
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
        View view=inflater.inflate(R.layout.fragment_payment_details, container, false);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Payment Details");
        }else if(Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Payment Details");
        }else {
            MainActivity.txtTitle.setText("Payment Details");
        }

        initView(view);
        return view;
    }

    private void initView(View view) {
      /*  btnBankDetails=(FloatingActionButton)view.findViewById(R.id.payment_details_bank_details);
        btnBankDetails.setOnClickListener(this);*/
        btnWorkOrderStatus=(AppCompatImageView)view.findViewById(R.id.payment_details_workorder_status);

        if (workOrderDetails.getWorkorderDTO().getWorkOrderStatus().equals("WO: Closed")){
            btnWorkOrderStatus.setImageResource(R.drawable.wo_close_final);
        }else {
            btnWorkOrderStatus.setImageResource(R.drawable.wo_open_final);
        }

        btnAddPayment=(FloatingActionButton)view.findViewById(R.id.payment_details_add_payment);
        btnAddPayment.setOnClickListener(this);

        btnWorkOrderMore=(AppCompatImageView) view.findViewById(R.id.payment_details_workorder_more);
        btnWorkOrderMore.setOnClickListener(this);

        editWorkOrder=(TextInputEditText)view.findViewById(R.id.payment_details_workorder);
        editWorkOrder.setText(invoiceByUserList.get(0).getWorkOrderNo());
        editWorkOrder.setInputType(InputType.TYPE_NULL);


        mPaymentDetailsRecycle=(RecyclerView)view.findViewById(R.id.payment_details_recycle);
        mPaymentDetailsRecycle.setHasFixedSize(false);
        mPaymentDetailsRecycle.setItemAnimator(new DefaultItemAnimator());
        mPaymentDetailsRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        PaymentDetailsAdapter paymentDetailsAdapter=new PaymentDetailsAdapter(getActivity(),invoiceByUserList);
        mPaymentDetailsRecycle.setAdapter(paymentDetailsAdapter);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.payment_details_add_payment:
                callAddPayment();
                break;
         /*   case R.id.payment_details_bank_details:
               //callCustomDialog();
                break;*/
            case R.id.payment_details_workorder_more:
                callCustomDialog("Work Order Details",workOrderDetails);
                break;

        }
    }

    public void callCustomDialog(String title, OfferWorkOrder data) {
        AppCompatTextView mTitle;
        RecyclerView mPaymentRecyclerView;

        List<String> mHeadingData=new ArrayList<>();
        List<String> mValueData=new ArrayList<>();

        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.payment_custom_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.payment_dailog_title);
        mTitle.setText(data.getWorkorderDTO().getWorkOrderNo());

        mPaymentRecyclerView=(RecyclerView) dialog.findViewById(R.id.payment_dailog_recycle);
        mPaymentRecyclerView.setHasFixedSize(false);
        mPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mHeadingData.add("Owner");
        mValueData.add(data.getWorkorderDTO().getWorkOrderOwner());
        mHeadingData.add("WO Date");
        mValueData.add(convertDate(data.getWorkorderDTO().getWorkStartDate())+" to "+convertDate(data.getWorkorderDTO().getWorkEndDate()));
        mHeadingData.add("Duration");
        mValueData.add(String.valueOf(data.getWorkorderDTO().getNoOfDays())+" Days"+" & "+String.valueOf(data.getWorkorderDTO().getReqHour()+" Hours"));
        mHeadingData.add("Machine");
        mValueData.add(data.getWorkorderDTO().getMachine().getMachineName());

        mHeadingData.add("Rental Plan");
        mValueData.add(data.getWorkorderDTO().getPlanName()+"("+data.getWorkorderDTO().getPlanUsage()+")");
        mHeadingData.add("Site Location");
        mValueData.add(data.getWorkLocationSite());

        mHeadingData.add("Operator");
        mValueData.add(data.getWorkorderDTO().getOperatorName());

        mHeadingData.add("Supervisor");
        mValueData.add(data.getWorkorderDTO().getSupervisorName());

        mHeadingData.add("Est.Amount");
        mValueData.add("\u20B9 "+data.getWorkOrderAmt());


        /*if (data.getWorkorderDTO().isGst()){
            mHeadingData.add("( * GST / IGST and Mobility Amount Included )");
        }else {
            mHeadingData.add("( GST / IGST and Mobility Amount Excluded ) *");
        }*/
        mHeadingData.add("(* GST / IGST and Mobility Amount Excluded )");
        mValueData.add("");

        PaymentCustomeDialogAdapter customeDialogAdapter=new PaymentCustomeDialogAdapter(getActivity(),mHeadingData,mValueData,true);
        mPaymentRecyclerView.setAdapter(customeDialogAdapter);

        btnClose=(AppCompatImageButton) dialog.findViewById(R.id.payment_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    private void callAddPayment() {

        List<InvoiceByUser> pendingInvoice=new ArrayList<>();
        pendingInvoice.clear();

        if (Constants.USER_ROLE.equals("Renter")){

            for (int i=0;i<invoiceByUserList.size();i++){
                if (invoiceByUserList.get(i).getInvoicePendingAmount()>0){
                    pendingInvoice.add(invoiceByUserList.get(i));
                }
            }
        }else if (Constants.USER_ROLE.equals("Owner")){
            for (int i=0;i<invoiceByUserList.size();i++){
                if (invoiceByUserList.get(i).getInvCategoryCode().equals("INV_MSC_OWN")){
                    if (invoiceByUserList.get(i).getInvoicePendingAmount()>0){
                        pendingInvoice.add(invoiceByUserList.get(i));
                    }
                }

            }
        }


        /*Fragment fragment = new AddPaymentFragment(workOrderDetails,pendingInvoice);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack("PaymentDetails");
        fragmentTransaction.commitAllowingStateLoss();*/

        if (pendingInvoice.size()>0){
            Fragment fragment = new AddPaymentFragment(workOrderDetails,pendingInvoice);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_main, fragment);
            fragmentTransaction.addToBackStack("PaymentDetails");
            fragmentTransaction.commitAllowingStateLoss();
        }else {
            showSnackbar("No pending invoice available for this WO.");
        }

        
    }

    public String convertDate(String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    private void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


}
