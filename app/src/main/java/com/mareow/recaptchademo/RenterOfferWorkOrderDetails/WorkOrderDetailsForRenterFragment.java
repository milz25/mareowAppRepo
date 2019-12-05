package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.android.material.snackbar.Snackbar;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.Download;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.FCMessaging.DownloadService;
import com.mareow.recaptchademo.MainDetailsFragment.WorkOrderCancelFragment;
import com.mareow.recaptchademo.MainDetailsFragment.WorkOrderExtendFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.Util;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkOrderDetailsForRenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkOrderDetailsForRenterFragment extends Fragment implements View.OnClickListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String MESSAGE_PROGRESS ="Message_Progress" ;
    AppCompatTextView mProgress_text;
    ProgressBar mProgressBar;
    Dialog dialog;

    public ViewPager mWorkOrderDetailsViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;


    AppCompatTextView workNo;
    AppCompatTextView workDates;
  //  AppCompatTextView workStatus;

    AppCompatTextView mEstimatedCost;
    AppCompatButton btnOfferAccepted;

    AppCompatImageView woStatus;

    OfferWorkOrder offerWorkOrder;

    AppCompatImageView woAgreement;
    AppCompatImageView woExtended;
    AppCompatImageView woCancel;

    RelativeLayout mFooter;
   // OptionsFabLayout fabWithOption;

    RapidFloatingActionLayout mRapidLayout;
    RapidFloatingActionButton mRapidActionButton;
    RapidFloatingActionHelper rfabHelper;


    FragmentManager childFragmentManager;
    AppCompatActivity activity;
    ProgressDialog progressDialog;
    public WorkOrderDetailsForRenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkOrderDetailsForRenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkOrderDetailsForRenterFragment newInstance(String param1, String param2) {
        WorkOrderDetailsForRenterFragment fragment = new WorkOrderDetailsForRenterFragment();
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
        View view=inflater.inflate(R.layout.fragment_work_order_details_for_renter, container, false);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Work Order Details");
        }else {
            OwnerMainActivity.txtOwnerTitle.setText("Work Order Details");
        }

        childFragmentManager=getChildFragmentManager();
        Bundle bundle=getArguments();
        offerWorkOrder=(OfferWorkOrder) bundle.getSerializable("offerDetails");

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...............");
        }

        activity= (AppCompatActivity) getActivity();

        registerReceiver();
        initView(view);
        return view;
    }


    private void initView(View view) {

        //Header

        mWorkOrderDetailsViewPager = (ViewPager)view.findViewById(R.id.work_details_viewpager);
        llPagerDots = (LinearLayout)view.findViewById(R.id.work_details_pager_dots);

        workNo=(AppCompatTextView)view.findViewById(R.id.work_details_header_workorder_no);
        workDates=(AppCompatTextView)view.findViewById(R.id.work_details_header_date);
        woStatus=(AppCompatImageView)view.findViewById(R.id.work_details_header_status);

        workNo.setText("Work Order # "+offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        workDates.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkEndDate()));

        if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("CLOSE")){
            woStatus.setImageResource(R.drawable.wo_close_final);
        }else {
            woStatus.setImageResource(R.drawable.wo_open_final);
        }

        mFooter=(RelativeLayout)view.findViewById(R.id.work_details_footer);

       /* woAgreement=(AppCompatImageView)view.findViewById(R.id.work_details_agreement);
        woAgreement.setOnClickListener(this);
        woExtended=(AppCompatImageView)view.findViewById(R.id.work_details_woextend);
        woExtended.setOnClickListener(this);
        woCancel=(AppCompatImageView)view.findViewById(R.id.work_details_wocancel);
        woCancel.setOnClickListener(this);*/


       /* if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("CLOSE")) {
            *//*woAgreement.setVisibility(View.GONE);
            woExtended.setVisibility(View.GONE);
            woCancel.setVisibility(View.GONE);

            mFooter.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
            mFooter.setVerticalGravity(Gravity.CENTER_VERTICAL);*//*


        }*/
     /*   //footer
        mEstimatedCost=(AppCompatTextView)view.findViewById(R.id.offer_details_footer_estimated_cost);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        String price="\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkOrderAmt());

        SpannableString ss2=  new SpannableString(price);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,price.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, price.length(), 1);

        String eString="Estimated Amount :";
        SpannableString ss1=  new SpannableString(eString);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,eString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, eString.length(), 0);// set color

       *//* SpannableString ss2=  new SpannableString(amountString);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,amountString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, amountString.length(), 0);*//*

        String GstandIGst="";
        if (offerWorkOrder.getWorkorderDTO().isGst()){
            GstandIGst="( * GST / IGST Amount Included )";
        }else {
            GstandIGst="( * GST / IGST Amount Excluded )";
        }

        SpannableString ss3=  new SpannableString(GstandIGst);
        //ss3.setSpan(new AbsoluteSizeSpan(getActivity().getResources().getDimensionPixelSize(R.dimen.text2),true), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss3.setSpan(new RelativeSizeSpan(0.5f), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.theme_orange)), 0, GstandIGst.length(), 0);


        SpannableStringBuilder builder=new SpannableStringBuilder();
        builder.append(ss1);
        builder.append(ss2+"\n");
        builder.append(ss3);
        mEstimatedCost.setText(builder);

        btnOfferAccepted=(AppCompatButton)view.findViewById(R.id.offer_details_footer_accept_offer);
        btnOfferAccepted.setOnClickListener(this);*/


        mWorkOrderDetailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               /* if (position==0){
                    RenterMainActivity.navItemIndexRenter=21;
                }else {
                    RenterMainActivity.navItemIndexRenter=20;
                }*/
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_dots);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(mWorkOrderDetailsViewPager);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);


        mRapidLayout=(RapidFloatingActionLayout)view.findViewById(R.id.wo_rfal);
        mRapidActionButton=(RapidFloatingActionButton)view.findViewById(R.id.wo_rfab);

        if (Constants.USER_ROLE.equals("Owner")){

            RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
            rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
            List<RFACLabelItem> items = new ArrayList<>();

            if (offerWorkOrder.getWorkorderDTO().isAgreementFlg()){
                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Agreement")
                        .setResId(R.drawable.agreement_square)
                        .setLabelColor(Color.BLACK)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.theme_orange))
                        .setWrapper(0)
                );
            }

            if (offerWorkOrder.getWorkorderDTO().isWoCancelFlg()){
                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Cancel")
                        .setResId(R.drawable.wo_close_final)
                        .setLabelColor(Color.BLACK)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.colorPrimary))
                        .setWrapper(0)
                );
            }

            if (offerWorkOrder.getWorkorderDTO().getExtendStatus().equals("EXTEND")){

                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Extend")
                        .setResId(R.drawable.wo_extend)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.theme_orange))
                        .setLabelColor(Color.BLACK)
                        .setLabelSizeSp(14)
                        .setWrapper(1)
                );

            }

            if (items.size()>0){

                rfaContent.setItems(items)
                        .setIconShadowRadius(5)
                        .setIconShadowColor(0xff888888)
                        .setIconShadowDy(5);
                rfabHelper = new RapidFloatingActionHelper(
                        getActivity(),
                        mRapidLayout,
                        mRapidActionButton,
                        rfaContent).build();

            }else {
                mRapidLayout.setVisibility(View.GONE);
            }

        }

        if (Constants.USER_ROLE.equals("Renter")){

            RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
            rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
            List<RFACLabelItem> items = new ArrayList<>();
            if (offerWorkOrder.getWorkorderDTO().isAgreementFlg()){
                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Agreement")
                        .setResId(R.drawable.agreement_square)
                        .setLabelColor(Color.BLACK)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.theme_orange))
                        .setWrapper(0)
                );
            }

            if (offerWorkOrder.getWorkorderDTO().isWoextendFlg()){
                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Extend")
                        .setResId(R.drawable.wo_extend)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.theme_orange))
                        .setLabelColor(Color.BLACK)
                        .setLabelSizeSp(14)
                        .setWrapper(1)
                );
            }

            if (offerWorkOrder.getWorkorderDTO().isWoCancelFlg()){
                items.add(new RFACLabelItem<Integer>()
                        .setLabel("Cancel")
                        .setResId(R.drawable.wo_close_final)
                        .setIconNormalColor(getActivity().getResources().getColor(android.R.color.white))
                        .setLabelColor(Color.BLACK)
                        .setIconPressedColor(getActivity().getResources().getColor(R.color.theme_orange))
                        .setWrapper(2)
                );
            }

            if (items.size()>0){

                rfaContent.setItems(items)
                        .setIconShadowRadius(5)
                        .setIconShadowColor(0xff888888)
                        .setIconShadowDy(5);
                rfabHelper = new RapidFloatingActionHelper(
                        getActivity(),
                        mRapidLayout,
                        mRapidActionButton,
                        rfaContent).build();
            }else {
                mRapidLayout.setVisibility(View.GONE);
            }


        }

        /* fabWithOption=(OptionsFabLayout)view.findViewById(R.id.work_details_fabwithoption);
         //fabWithOption.setMiniFabsColors(R.color.theme_orange,R.color.colorPrimary,R.color.Blue_Text);
         fabWithOption.setMainFabOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabWithOption.isOptionsMenuOpened()){
                    fabWithOption.closeOptionsMenu();
                }
            }
        });





        fabWithOption.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
            @Override
            public void onMiniFabSelected(MenuItem fabItem) {
                switch (fabItem.getItemId()) {
                    case R.id.fab_agreement:
                        callDownloadAgreementsApi();
                        break;
                    case R.id.fab_extend:
                        callWorkOrderExtendApi(offerWorkOrder);
                        break;
                    case R.id.fab_cancel:
                        callWorkOrderCancelApi(offerWorkOrder);
                        break;
                    default:
                        break;
                }
            }
        });*/




        if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("CLOSE")) {
            /*woAgreement.setVisibility(View.GONE);
            woExtended.setVisibility(View.GONE);
            woCancel.setVisibility(View.GONE);

            mFooter.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
            mFooter.setVerticalGravity(Gravity.CENTER_VERTICAL);*/
            //fabWithOption.setVisibility(View.GONE);
            mRapidActionButton.setVisibility(View.GONE);
            mRapidLayout.setVisibility(View.GONE);

        }


    }

    private void setupViewPager(ViewPager viewPager) {


        if (getActivity()!=null && isAdded()) {

          //  activity= (AppCompatActivity) getActivity();

            adapter = new DetailActivityViewPagerAdapter(childFragmentManager);

            adapter.addFragment(new OfferGeneralDetailsFragment(offerWorkOrder), "GeneralDetails");
            adapter.addFragment(new OfferMachineDetailsFragment(offerWorkOrder), "MachineDetails");
            adapter.addFragment(new OfferAttachmentFragment(offerWorkOrder), "AttachmentDetails");
            adapter.addFragment(new OfferRentalPlanFragment(offerWorkOrder), "RentalPlanDetails");
            adapter.addFragment(new OfferOperatorandSupervisorFragment(offerWorkOrder), "OperSuperDetails");
            adapter.addFragment(new OfferMobilizeDetailsFragment(offerWorkOrder), "MobilizedDetails");
            adapter.addFragment(new OfferTandCFragment(offerWorkOrder), "TermAndCondition");
            adapter.addFragment(new RunningLogFragment(offerWorkOrder), "RunningLogDetials");


            viewPager.setAdapter(adapter);
        }
    }


    private void setupPagerIndidcatorDots() {

        ivArrayDotsPager = new ImageView[adapter.getCount()];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void callDownloadAgreementsApi() {

      /*  ArrayList<OfferWorkOrder> workOrderArrayList=new ArrayList<>();
        workOrderArrayList.add(offerWorkOrder);

        Intent intent= new Intent(getActivity(), DownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("workorder", workOrderArrayList);
        intent.putExtras(bundle);
        getActivity().startService(intent);
        displayAlert();
*/

        AppCompatTextView mTitle;
        PDFView pdfView;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pdf_viewer_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText("Support Document");
        pdfView=(PDFView)dialog.findViewById(R.id.pdfView);

        String urlString=ApiClient.BASE_URL+"workOrders/downloadPDF/"+offerWorkOrder.getWorkorderDTO().getWorkOrderId();

        FileLoader.with(getActivity())
                .load(urlString,false) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("mareow", FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        int code =response.getCode();
                        File loadedFile = response.getBody();
                            pdfView.fromFile(loadedFile)
                                    .password(null)
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .onDraw(new OnDrawListener() {
                                        @Override
                                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                            Toast.makeText(getActivity(), "on Draw", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .onDrawAll(new OnDrawListener() {
                                        @Override
                                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                            Toast.makeText(getActivity(), "on DrawAll", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .onPageError(new OnPageErrorListener() {
                                        @Override
                                        public void onPageError(int page, Throwable t) {
                                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .onPageChange(new OnPageChangeListener() {
                                        @Override
                                        public void onPageChanged(int page, int pageCount) {
                                            Toast.makeText(getActivity(), "on pageOn", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .onTap(new OnTapListener() {
                                        @Override
                                        public boolean onTap(MotionEvent e) {
                                            return true;
                                        }
                                    })
                                    .onRender(new OnRenderListener() {
                                        @Override
                                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                            pdfView.fitToWidth();
                                        }
                                    })
                                    .enableAnnotationRendering(true)
                                    .invalidPageColor(Color.WHITE)
                                    .load();


                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.RBD_popdialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();






    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                Download download = intent.getParcelableExtra("download");
                mProgressBar.setProgress(download.getProgress());

                if(download.getProgress() == 100){
                    if (Constants.FAILD_TO_DOWNLOAD){
                        showSnackbar("Failed to Download");
                        mProgress_text.setText("Failed to download.");
                        dialog.dismiss();
                    }else {
                        mProgress_text.setText("File Download Complete");
                        dialog.dismiss();
                    }
                } else {
                    mProgress_text.setText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
                }

            }
        }
    };


    private void displayAlert()
    {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_download);
        // set the custom dialog components - text, image and button
        mProgressBar=(ProgressBar)dialog.findViewById(R.id.progress);
        mProgress_text= (AppCompatTextView) dialog.findViewById(R.id.progress_text);

        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    private void registerReceiver(){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private void callWorkOrderExtendApi(OfferWorkOrder workOrder) {

        Fragment workOrderExtendFragment=new WorkOrderExtendFragment(workOrder);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, workOrderExtendFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    private void callWorkOrderCancelApi(OfferWorkOrder workOrder) {

        if (Constants.USER_ROLE.equals("Renter")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN")){
                showSnackbar("You already generate cancel request for this workorder ");
                return;
            }
        }

        if (Constants.USER_ROLE.equals("Owner")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
                showSnackbar("You already generate cancel request for this workorder ");
                return;
            }
        }

        Fragment workOrderCancelFragment=new WorkOrderCancelFragment(workOrder);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, workOrderCancelFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        String value=item.getLabel();
        switch (item.getLabel()) {
            case "Agreement":
                callDownloadAgreementsApi();
                break;
            case "Extend":
                callWorkOrderExtendApi(offerWorkOrder);
                break;
            case "Cancel":
                callWorkOrderCancelApi(offerWorkOrder);
                break;
            default:
                break;
        }

        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        String value=item.getLabel();
        Toast.makeText(getContext(),value, Toast.LENGTH_SHORT).show();

        switch (item.getLabel()) {
            case "Agreement":
                callDownloadAgreementsApi();
                break;
            case "Extend":
                callWorkOrderExtendApi(offerWorkOrder);
                break;
            case "Cancel":
                callWorkOrderCancelApi(offerWorkOrder);
                break;
            default:
                break;
        }

        rfabHelper.toggleContent();
    }

    public void showSnackbar(String msg){

        Snackbar snackbar= Snackbar.make(activity.getCurrentFocus(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }

}
