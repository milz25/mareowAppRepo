package com.mareow.recaptchademo.OwnerTermAndConditionFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentCenter;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentLeft;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentRight;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_At;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Bold;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Hr;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Image;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Link;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListBullet;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListNumber;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Quote;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Strikethrough;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Subscript;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Superscript;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Underline;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Video;
import com.chinalwb.are.styles.toolitems.IARE_ToolItem;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.TypeOfTermAndCondition;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OwnerGeneralTermsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IARE_Toolbar mToolbar;

    private AREditText mEditText;

    private boolean scrollerAtEnd;

    AppCompatTextView mTitle;
    AREditText arEditText;

    String title;
    String termsdescription;

    FloatingActionButton btnUpdateTerms;
    ProgressDialog progressDialog;
    TypeOfTermAndCondition termAndConditionData;
    public OwnerGeneralTermsFragment(String termsdescription, String title,TypeOfTermAndCondition termAndConditionData) {
        // Required empty public constructor
        this.title = title;
        this.termsdescription = termsdescription;
        this.termAndConditionData=termAndConditionData;
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
        View view = inflater.inflate(R.layout.fragment_owner_general_terms, container, false);

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...................");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {

       /* arEditText=(AREditText)view.findViewById(R.id.arEditText);
        arEditText.fromHtml(termsdescription);*/
/*

        arEditor=(AREditor)view.findViewById(R.id.areditor);
        arEditor.setExpandMode(AREditor.ExpandMode.FULL);
        arEditor.setHideToolbar(false);
        arEditor.setToolbarAlignment(AREditor.ToolbarAlignment.BOTTOM);
*/


        initToolbar(view);

        mTitle = (AppCompatTextView) view.findViewById(R.id.terms_title);
        mTitle.setText(title);

        btnUpdateTerms=(FloatingActionButton)view.findViewById(R.id.terms_frg_update);
        btnUpdateTerms.setOnClickListener(this);


    }


    private void initToolbar(View view) {
        mToolbar = view.findViewById(R.id.areToolbar);

        IARE_ToolItem bold = new ARE_ToolItem_Bold();
        IARE_ToolItem italic = new ARE_ToolItem_Italic();
        IARE_ToolItem underline = new ARE_ToolItem_Underline();
        IARE_ToolItem strikethrough = new ARE_ToolItem_Strikethrough();
        IARE_ToolItem quote = new ARE_ToolItem_Quote();
        IARE_ToolItem listNumber = new ARE_ToolItem_ListNumber();
        IARE_ToolItem listBullet = new ARE_ToolItem_ListBullet();
        IARE_ToolItem hr = new ARE_ToolItem_Hr();
        IARE_ToolItem link = new ARE_ToolItem_Link();
        IARE_ToolItem subscript = new ARE_ToolItem_Subscript();
        IARE_ToolItem superscript = new ARE_ToolItem_Superscript();
        IARE_ToolItem left = new ARE_ToolItem_AlignmentLeft();
        IARE_ToolItem center = new ARE_ToolItem_AlignmentCenter();
        IARE_ToolItem right = new ARE_ToolItem_AlignmentRight();
        IARE_ToolItem image = new ARE_ToolItem_Image();
        IARE_ToolItem video = new ARE_ToolItem_Video();
        IARE_ToolItem at = new ARE_ToolItem_At();
        mToolbar.addToolbarItem(bold);
        mToolbar.addToolbarItem(italic);
        mToolbar.addToolbarItem(underline);
        mToolbar.addToolbarItem(strikethrough);
        mToolbar.addToolbarItem(quote);
        mToolbar.addToolbarItem(listNumber);
        mToolbar.addToolbarItem(listBullet);
        mToolbar.addToolbarItem(hr);
        mToolbar.addToolbarItem(link);
        mToolbar.addToolbarItem(subscript);
        mToolbar.addToolbarItem(superscript);
        mToolbar.addToolbarItem(left);
        mToolbar.addToolbarItem(center);
        mToolbar.addToolbarItem(right);
        mToolbar.addToolbarItem(image);
        mToolbar.addToolbarItem(video);
       // mToolbar.addToolbarItem(at);

        mEditText = view.findViewById(R.id.arEditText);
        mEditText.setToolbar(mToolbar);

        setHtml();

        initToolbarArrow(view);
    }

    private void setHtml() {
       /* String html = "<p style=\"text-align: center;\"><strong>New Feature in 0.1.2</strong></p>\n" +
                "<p style=\"text-align: center;\">&nbsp;</p>\n" +
                "<p style=\"text-align: left;\"><span style=\"color: #3366ff;\">In this release, you have a new usage with ARE.</span></p>\n" +
                "<p style=\"text-align: left;\">&nbsp;</p>\n" +
                "<p style=\"text-align: left;\"><span style=\"color: #3366ff;\">AREditText + ARE_Toolbar, you are now able to control the position of the input area and where to put the toolbar at and, what ToolItems you'd like to have in the toolbar. </span></p>\n" +
                "<p style=\"text-align: left;\">&nbsp;</p>\n" +
                "<p style=\"text-align: left;\"><span style=\"color: #3366ff;\">You can not only define the Toolbar (and it's style), you can also add your own ARE_ToolItem with your style into ARE.</span></p>\n" +
                "<p style=\"text-align: left;\">&nbsp;</p>\n" +
                "<p style=\"text-align: left;\"><span style=\"color: #ff00ff;\"><em><strong>Why not give it a try now?</strong></em></span></p>";*/
        mEditText.fromHtml(termsdescription);
    }


    private void initToolbarArrow(View view) {
        final ImageView imageView = view.findViewById(R.id.arrow);
        if (this.mToolbar instanceof ARE_ToolbarDefault) {
            ((ARE_ToolbarDefault) mToolbar).getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollX = ((ARE_ToolbarDefault) mToolbar).getScrollX();
                    int scrollWidth = ((ARE_ToolbarDefault) mToolbar).getWidth();
                    int fullWidth = ((ARE_ToolbarDefault) mToolbar).getChildAt(0).getWidth();

                    if (scrollX + scrollWidth < fullWidth) {
                        imageView.setImageResource(R.drawable.ic_arrow_right);
                        scrollerAtEnd = false;
                    } else {
                        imageView.setImageResource(R.drawable.ic_arrow_left);
                        scrollerAtEnd = true;
                    }
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollerAtEnd) {
                    ((ARE_ToolbarDefault) mToolbar).smoothScrollBy(-Integer.MAX_VALUE, 0);
                    scrollerAtEnd = false;
                } else {
                    int hsWidth = ((ARE_ToolbarDefault) mToolbar).getChildAt(0).getWidth();
                    ((ARE_ToolbarDefault) mToolbar).smoothScrollBy(hsWidth, 0);
                    scrollerAtEnd = true;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.terms_frg_update:
                updateTerms();
                break;
        }
    }

    private void updateTerms() {

        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        TypeOfTermAndCondition updatedTermAndConditionData=new TypeOfTermAndCondition();

        if (title.equals("General Terms")){

            String general=mEditText.getHtml();
            updatedTermAndConditionData.setGeneralTermsdescription(mEditText.getHtml());
            updatedTermAndConditionData.setCommercialTermsdescription(termAndConditionData.getCommercialTermsdescription());
            updatedTermAndConditionData.setCancellationTermsdescription(termAndConditionData.getCancellationTermsdescription());
            updatedTermAndConditionData.setLogisticsTermsdescription(termAndConditionData.getLogisticsTermsdescription());
            updatedTermAndConditionData.setPaymentTermsdescription(termAndConditionData.getPaymentTermsdescription());


        }
        if (title.equals("Commercial Terms")){


            updatedTermAndConditionData.setCommercialTermsdescription(mEditText.getHtml());
            updatedTermAndConditionData.setGeneralTermsdescription(termAndConditionData.getGeneralTermsdescription());
            updatedTermAndConditionData.setCancellationTermsdescription(termAndConditionData.getCancellationTermsdescription());
            updatedTermAndConditionData.setLogisticsTermsdescription(termAndConditionData.getLogisticsTermsdescription());
            updatedTermAndConditionData.setPaymentTermsdescription(termAndConditionData.getPaymentTermsdescription());


        }
        if (title.equals("Payment Terms")){


            updatedTermAndConditionData.setPaymentTermsdescription(mEditText.getHtml());
            updatedTermAndConditionData.setCommercialTermsdescription(termAndConditionData.getCommercialTermsdescription());
            updatedTermAndConditionData.setCancellationTermsdescription(termAndConditionData.getCancellationTermsdescription());
            updatedTermAndConditionData.setLogisticsTermsdescription(termAndConditionData.getLogisticsTermsdescription());
            updatedTermAndConditionData.setGeneralTermsdescription(termAndConditionData.getGeneralTermsdescription());


        }
        if (title.equals("Logistics Terms")){


            updatedTermAndConditionData.setLogisticsTermsdescription(mEditText.getHtml());
            updatedTermAndConditionData.setCommercialTermsdescription(termAndConditionData.getCommercialTermsdescription());
            updatedTermAndConditionData.setCancellationTermsdescription(termAndConditionData.getCancellationTermsdescription());
            updatedTermAndConditionData.setGeneralTermsdescription(termAndConditionData.getGeneralTermsdescription());
            updatedTermAndConditionData.setPaymentTermsdescription(termAndConditionData.getPaymentTermsdescription());


        }

        if (title.equals("Cancellation Terms")){


            updatedTermAndConditionData.setCancellationTermsdescription(mEditText.getHtml());
            updatedTermAndConditionData.setCommercialTermsdescription(termAndConditionData.getCommercialTermsdescription());
            updatedTermAndConditionData.setGeneralTermsdescription(termAndConditionData.getGeneralTermsdescription());
            updatedTermAndConditionData.setLogisticsTermsdescription(termAndConditionData.getLogisticsTermsdescription());
            updatedTermAndConditionData.setPaymentTermsdescription(termAndConditionData.getPaymentTermsdescription());


        }

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateCall=apiInterface.updatesTermsAndCondition("Bearer "+token,partyId,updatedTermAndConditionData);
        updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record not Found.");
                    }
                    if (response.code()==403){

                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(),mError.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void callMainTermsFragments() {


    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

}