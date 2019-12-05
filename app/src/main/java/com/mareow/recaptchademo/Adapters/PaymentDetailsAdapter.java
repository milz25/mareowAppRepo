package com.mareow.recaptchademo.Adapters;

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
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mareow.recaptchademo.DataModels.Download;
import com.mareow.recaptchademo.DataModels.InvoiceByInvoiceId;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.ProfileData;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.FCMessaging.DownloadInvoiceService;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.PaymentDetailsViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsViewHolder> {
    public static final String MESSAGE_INVOICE ="Message_Invoice" ;
    Context context;
    List<InvoiceByUser> invoiceByUserList;
    ProgressDialog progressDialog;
    ProfileData profileData;
    AppCompatTextView mProgress_text;
    ProgressBar mProgressBar;
    Dialog dialog;
    InvoiceByInvoiceId invoiceDetails;
    public  PaymentDetailsAdapter(Context context, List<InvoiceByUser> invoiceByUserList) {
        this.context=context;
        this.invoiceByUserList=invoiceByUserList;

        if (context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait..............");
        }

    }

    @NonNull
    @Override
    public PaymentDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.payment_details_adapter,parent,false);
        return new PaymentDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentDetailsViewHolder holder, int position) {

        holder.editInvoiceNo.setText(invoiceByUserList.get(position).getInvoiceNo());
        holder.editInvoiceNo.setInputType(InputType.TYPE_NULL);
        holder.editInvoiceCategory.setText(invoiceByUserList.get(position).getInvCategory());
        holder.editInvoiceCategory.setInputType(InputType.TYPE_NULL);
        holder.editInvoicePayableAmount.setText("\u20B9 "+invoiceByUserList.get(position).getInvoiceNetAmount());
        holder.editInvoicePayableAmount.setInputType(InputType.TYPE_NULL);
        holder.editInvoiceDueAmount.setText("\u20B9 "+invoiceByUserList.get(position).getInvoicePendingAmount());
        holder.editInvoiceDueAmount.setInputType(InputType.TYPE_NULL);

        holder.btnInvoiceMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSpecificInvoiceApi(invoiceByUserList.get(position));
               // callCustomDialog(invoiceByUserList.get(position));
            }
        });

        holder.btnBankDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBankDetailsApi(invoiceByUserList.get(position));
            }
        });

        holder.btnInvoiceDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver();
                downloadInvoiceDocument(invoiceByUserList.get(position));
            }
        });

        if (invoiceByUserList.get(position).getInvCategoryCode().equals("INV_MSC_OWN") || invoiceByUserList.get(position).getInvCategoryCode().equals("INV_MSC_REN")){

            holder.btnInvoiceDownload.setVisibility(View.VISIBLE);
        }else {
            holder.btnInvoiceDownload.setVisibility(View.GONE);
        }

        holder.paymentDetailsRecycle.setHasFixedSize(false);
        holder.paymentDetailsRecycle.setItemAnimator(new DefaultItemAnimator());
        holder.paymentDetailsRecycle.setLayoutManager(new LinearLayoutManager(context));


        InvoiceWithinPaymentAdapter withinPaymentAdapter=new InvoiceWithinPaymentAdapter(context,invoiceByUserList.get(position).getReceiptDTOs());
        holder.paymentDetailsRecycle.setAdapter(withinPaymentAdapter);




    }



    @Override
    public int getItemCount() {
        return invoiceByUserList.size();
    }

    private void callSpecificInvoiceApi(InvoiceByUser invoice) {
        progressDialog.show();
        String token= new TokenManager(context).getSessionToken();
        int invoiceId=invoice.getInvoiceId();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<InvoiceByInvoiceId> paymentCall=apiInterface.getInvoiceByInvoiceId("Bearer "+token,invoiceId);
        paymentCall.enqueue(new Callback<InvoiceByInvoiceId>() {
            @Override
            public void onResponse(Call<InvoiceByInvoiceId> call, Response<InvoiceByInvoiceId> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    invoiceDetails=response.body();
                    callCustomDialog(invoice,invoiceDetails);
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(context, "Record not found", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<InvoiceByInvoiceId> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void callCustomDialog(InvoiceByUser data, InvoiceByInvoiceId invoiceDetails) {

        AppCompatTextView mTitle;
     //   RecyclerView mPaymentRecyclerView;

        AppCompatTextView dWorkNo;
        AppCompatTextView dInvNo;
        AppCompatTextView dInvDate;
        AppCompatTextView dInvCategory;
        AppCompatCheckBox dTax;
        AppCompatTextView dNetAmount;
        AppCompatTextView dPaidAmount;
        AppCompatTextView dDueAmount;
        AppCompatTextView dInvDocument;
        AppCompatTextView dInvDocuHeading;

        List<String> mHeadingData=new ArrayList<>();
        List<String> mValueData=new ArrayList<>();

        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.invoice_custome_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_title);
        mTitle.setText(data.getInvoiceNo());

        dWorkNo=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_wono);
        dWorkNo.setText(data.getWorkOrderNo());
        dInvNo=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invno);
        dInvNo.setText(data.getInvoiceNo());
        dInvDate=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invdate);
        dInvDate.setText(invoiceDetails.getInvoiceDateStr());
        dInvCategory=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invCategory);
        dInvCategory.setText(data.getInvCategory());
        dNetAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invnetAm);
        dNetAmount.setText("\u20B9 "+data.getInvoiceNetAmount());
        dPaidAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invpaidAm);
        dPaidAmount.setText("\u20B9 "+data.getInvoiceReceiveAmount());
        dDueAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDueAm);
        dDueAmount.setText("\u20B9 "+data.getInvoicePendingAmount());

        dInvDocuHeading=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDocumentheading);
        dInvDocument=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDocument);

        if (data.getInvCategoryCode().equals("INV_MSC_OWN") || data.getInvCategoryCode().equals("INV_MSC_REN")){
            dInvDocuHeading.setVisibility(View.GONE);
            dInvDocument.setVisibility(View.GONE);
        }else {
            if (invoiceDetails.getInvoiceDocumentFileName()!=null){
                SpannableString s1 = new SpannableString(invoiceDetails.getInvoiceDocumentFileName());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        //Toast.makeText(context, "Term and Condition", Toast.LENGTH_SHORT).show();
                        // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(invoiceDetails.getInvoiceDocument()));
                        //  context.startActivity(browserIntent);

                        String filename= invoiceDetails.getInvoiceDocumentFileName();
                        if (filename.contains(".pdf") || filename.contains(".PDF") || !filename.contains(".")){
                            showPdfDoc(invoiceDetails.getInvoiceDocument());
                        }else {
                            showImageDialog(invoiceDetails.getInvoiceDocument());
                        }

                        //showImageDialog(invoiceDetails.getInvoiceDocument());

                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(context.getResources().getColor(R.color.Blue_Text));
                    }
                };
                s1.setSpan(clickableSpan, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dInvDocument.setText(s1);
                dInvDocument.setMovementMethod(LinkMovementMethod.getInstance());
            }

        }


        dTax=(AppCompatCheckBox)dialog.findViewById(R.id.invoice_dailog_tax);
        dTax.setChecked(data.isGstFlg());
        dTax.setKeyListener(null);

       /* mPaymentRecyclerView=(RecyclerView) dialog.findViewById(R.id.payment_dailog_recycle);
        mPaymentRecyclerView.setHasFixedSize(false);
        mPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(context));*/


      /*  mHeadingData.add("Work Order #");
        mValueData.add(data.getWorkOrderNo());
        mHeadingData.add("Invoice #");
        mValueData.add(data.getInvoiceNo());
        mHeadingData.add("Invoice Date");
        mValueData.add(convertDate(data.getInvoiceDate()));
        mHeadingData.add("Category");
        mValueData.add(data.getInvCategory());

        *//*mHeadingData.add("Invoice Amount");
        mValueData.add("\u20B9 "+data.getInvoiceAmount());*//*

        mHeadingData.add("Tax");
        if (data.isGstFlg()){
            mValueData.add("Included");
        }else {
            mValueData.add("Excluded");
        }

        mHeadingData.add("Net Amount");
        mValueData.add("\u20B9 "+data.getInvoiceNetAmount());
        mHeadingData.add("Paid Amount");
        mValueData.add("\u20B9 "+data.getInvoiceReceiveAmount());
        mHeadingData.add("Due Amount");
        mValueData.add("\u20B9 "+data.getInvoicePendingAmount());*/


      /*  mHeadingData.add("Status");

        if (data.getStatus().equals("INV_PEN")){
            mValueData.add("Due");
        }else if(data.getStatus().equals("PAY_PAR_REC")){
            mValueData.add("Paid(Partially)");
        }else if(data.getStatus().equals("INV_REC")){
            mValueData.add("Paid");
        }
*/

       /* String boldText="Net Invoice Amount";
        SpannableString ss1=  new SpannableString(boldText);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,boldText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 0, boldText.length(), 0);

        SpannableStringBuilder c=new SpannableStringBuilder();
        c.append(ss1);

        String normalText="\u20B9 "+data.getInvoiceNetAmount();
        SpannableString ss2=  new SpannableString(normalText);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,normalText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 0, normalText.length(), 0);

        SpannableStringBuilder c2=new SpannableStringBuilder();
        c2.append(ss2);*/



      //  PaymentCustomeDialogAdapter customeDialogAdapter=new PaymentCustomeDialogAdapter(context,mHeadingData,mValueData, false);
       // mPaymentRecyclerView.setAdapter(customeDialogAdapter);

        btnClose=(AppCompatImageButton) dialog.findViewById(R.id.payment_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    private void showImageDialog(String invoiceDocument) {

        AppCompatTextView mTitle;
        WebView mWebViewData;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.term_and_condition_popup_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText("Support Document");
        mWebViewData=(WebView)dialog.findViewById(R.id.RBD_popdialog_web_data);

       // mWebViewData.setWebViewClient(new Callback());
        WebSettings webSettings = mWebViewData.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //webSettings.setBuiltInZoomControls(true);
        mWebViewData.loadUrl(invoiceDocument);

        //mWebViewData.loadData(data,"text/html",null);
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

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
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


    private void callInvoiceBankDetailsDialog(ProfileData profileData){

        AppCompatTextView mTitle;
        RecyclerView mPaymentRecyclerView;

        List<String> mHeadingData=new ArrayList<>();
        List<String> mValueData=new ArrayList<>();

        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.payment_custom_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.payment_dailog_title);
        mTitle.setText("Bank Details");

        mPaymentRecyclerView=(RecyclerView) dialog.findViewById(R.id.payment_dailog_recycle);
        mPaymentRecyclerView.setHasFixedSize(false);
        mPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        mHeadingData.add("A/C Holder (Drawn In Favour Of)");
        if (profileData.getAccountHolder()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getAccountHolder());
        }

        mHeadingData.add("Bank");
        if (profileData.getBank()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getBank());
        }
        mHeadingData.add("Account Type");
        if (profileData.getAccountType()==null){
            mValueData.add(" - ");
        }else {

            if (profileData.getAccountType().equals("CA")){
                mValueData.add("Current Account");
            }else {
                mValueData.add("Saving Account");
            }

        }


        mHeadingData.add("Payable At (City)");
        if (profileData.getPayableAtCity()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getPayableAtCity());
        }




        mHeadingData.add("Account #");
        if (profileData.getAccountNo()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getAccountNo());
        }

        mHeadingData.add("IFSC Code");
        if (profileData.getIfscCode()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getIfscCode());
        }

        mHeadingData.add("Paytm Account (Mobile #)");
        if (profileData.getPaytmAccount()==null){
            mValueData.add(" - ");
        }else {
            mValueData.add(profileData.getPaytmAccount());
        }

        PaymentBankDialogAdapter customeDialogAdapter=new PaymentBankDialogAdapter(context,mHeadingData,mValueData, false);
        mPaymentRecyclerView.setAdapter(customeDialogAdapter);

        btnClose=(AppCompatImageButton) dialog.findViewById(R.id.payment_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    private void callBankDetailsApi(InvoiceByUser invoiceByUser) {
        if (progressDialog!=null)
        progressDialog.show();
        String token= new TokenManager(context).getSessionToken();
        int ownerId=invoiceByUser.getOwnerId();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileData> paymentCall=apiInterface.getOwnerBankDetails("Bearer "+token,ownerId);
        paymentCall.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    profileData=response.body();
                    callInvoiceBankDetailsDialog(profileData);
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(context, "Record not found", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadInvoiceDocument(InvoiceByUser invoiceByUser) {

       /* ArrayList<InvoiceByUser> invoiceByUserArrayList=new ArrayList<>();
        invoiceByUserArrayList.add(invoiceByUser);

        Intent intent= new Intent(context, DownloadInvoiceService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("invoice", invoiceByUserArrayList);
        intent.putExtras(bundle);
        context.startService(intent);
        displayAlert();*/

        AppCompatTextView mTitle;
        PDFView pdfView;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pdf_viewer_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText("Support Document");
        pdfView=(PDFView)dialog.findViewById(R.id.pdfView);

        String urlString=ApiClient.BASE_URL+"invoices/downloadInvoicePDF/"+invoiceByUser.getInvoiceId();

        FileLoader.with(context)
                .load(urlString,false) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("mareow", FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        int code=response.getCode();

                        File loadedFile = response.getBody();
                        // do something with the file
                        pdfView.fromFile(loadedFile)
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onDrawAll(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

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
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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

            if(intent.getAction().equals(MESSAGE_INVOICE)){

                Download download = intent.getParcelableExtra("download");
                mProgressBar.setProgress(download.getProgress());

                if(download.getProgress() == 100){
                    if (Constants.FAILD_TO_DOWNLOAD){
                        showSnackbar("Failed to download");
                        mProgress_text.setText("Failed to download");
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
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_download);
        // set the custom dialog components - text, image and button
        mProgressBar=(ProgressBar)dialog.findViewById(R.id.progress);
        mProgress_text= (AppCompatTextView) dialog.findViewById(R.id.progress_text);

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_INVOICE);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }


    private void showPdfDoc(String paymentDocument) {

        AppCompatTextView mTitle;
        PDFView pdfView;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pdf_viewer_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText("Support Document");
        pdfView=(PDFView)dialog.findViewById(R.id.pdfView);

        FileLoader.with(context)
                .load(paymentDocument,false) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("mareow", FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File loadedFile = response.getBody();
                        // do something with the file
                        pdfView.fromFile(loadedFile)
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onDrawAll(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

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
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(((Activity)context).getCurrentFocus(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
