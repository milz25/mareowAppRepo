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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.mareow.recaptchademo.DataModels.PaymentByPID;
import com.mareow.recaptchademo.DataModels.ReceiptDTO;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.FCMessaging.DownloadPaymentService;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.EditPaymentFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.InvoiceWithinPaymentViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceWithinPaymentAdapter extends RecyclerView.Adapter<InvoiceWithinPaymentViewHolder> {
    public static final String MESSAGE_PAYMENT ="Message_Payment" ;
    Context context;
    List<ReceiptDTO> receiptDTOs;
    ProgressDialog progressDialog;
    PaymentByPID specificPayment;
    AppCompatTextView mProgress_text;
    ProgressBar mProgressBar;
    Dialog dialog;

    public InvoiceWithinPaymentAdapter(Context context, List<ReceiptDTO> receiptDTOs) {
        this.context=context;
        this.receiptDTOs=receiptDTOs;
        if (context!=null){

            progressDialog=new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.........");
        }

        Collections.reverse(this.receiptDTOs);

    }

    @NonNull
    @Override
    public InvoiceWithinPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.invoice_within_payment_details_adapter,parent,false);
        return new InvoiceWithinPaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceWithinPaymentViewHolder holder, int position) {

        if (Constants.USER_ROLE.equals("Renter")){
            holder.acknowSection.setVisibility(View.GONE);
        }

        if (Constants.USER_ROLE.equals("Owner")){
            if (receiptDTOs.get(position).isAcknowledgement()){
                holder.acknowSection.setVisibility(View.GONE);
            }else {
                holder.acknowSection.setVisibility(View.VISIBLE);
            }
        }

        if (!receiptDTOs.get(position).isAcknowledgement()){
            holder.btnReciept.setVisibility(View.GONE);
        }else {
            holder.btnReciept.setVisibility(View.VISIBLE);
        }

         holder.editPaymentNo.setText(receiptDTOs.get(position).getPaymentReceiptNo());
         holder.editPaymentNo.setInputType(InputType.TYPE_NULL);

         holder.editPaymentDate.setText(convertDate(receiptDTOs.get(position).getReceiptDate()));
         holder.editPaymentDate.setInputType(InputType.TYPE_NULL);

         holder.editPaidAmount.setText("\u20B9 "+receiptDTOs.get(position).getPaymentAmount());
         holder.editPaidAmount.setInputType(InputType.TYPE_NULL);

         holder.btnMore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 callPaymentDetails(receiptDTOs.get(position));
             }
         });

         if (receiptDTOs.get(position).isAcknowledgement()){
             holder.btnEditPayment.setEnabled(false);
             holder.btnEditPayment.setImageResource(R.drawable.edit_disable_final);
         }else {
             holder.btnEditPayment.setEnabled(true);
             holder.btnEditPayment.setImageResource(R.drawable.edit_final);
         }


         holder.btnEditPayment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               specificPaymentEdit(receiptDTOs.get(position));
             }
         });
         holder.btnReciept.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 registerReceiver();
                 downloadPaymentDocument(receiptDTOs.get(position));
             }
         });

         holder.btnAcknowlegement.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 callAcknowlegementDialog(receiptDTOs.get(position));
             }
         });

    }

    private void callAcknowlegementDialog(ReceiptDTO receiptDTO) {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        LayoutInflater newinInflater=((Activity)context).getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.payment_acknoelegement);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Do you want to acknowledgement the payment ?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callAcknowlegementApi(receiptDTO);
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        dialog.show();
    }

    private void callAcknowlegementApi(ReceiptDTO receiptDTO) {
        progressDialog.show();
        String token= new TokenManager(context).getSessionToken();
        int paymentId=receiptDTO.getPaymentId();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> paymentCall=apiInterface.acknowledgementByOwnerForPayment("Bearer "+token,paymentId,partyId);
        paymentCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return receiptDTOs.size();
    }

    public String convertDate(String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    private void callPaymentDetails(ReceiptDTO receiptDTO) {
        progressDialog.show();
        String token= new TokenManager(context).getSessionToken();
        int paymentId=receiptDTO.getPaymentId();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentByPID> paymentCall=apiInterface.getPaymentByPaymentId("Bearer "+token,paymentId);
        paymentCall.enqueue(new Callback<PaymentByPID>() {
            @Override
            public void onResponse(Call<PaymentByPID> call, Response<PaymentByPID> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    specificPayment=response.body();
                    callCustomDialog("Payment Details",specificPayment);
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
            public void onFailure(Call<PaymentByPID> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void callCustomDialog(String title, PaymentByPID paymentByPID) {
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
        mTitle.setText(paymentByPID.getReceiptNo());

        mPaymentRecyclerView=(RecyclerView) dialog.findViewById(R.id.payment_dailog_recycle);
        mPaymentRecyclerView.setHasFixedSize(false);
        mPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        mHeadingData.add("Work Order #");
        mValueData.add(paymentByPID.getWorkOrderNo());
        mHeadingData.add("Invoice #");
        mValueData.add(paymentByPID.getInvoiceNo());
        mHeadingData.add("Receipt #");
        mValueData.add(paymentByPID.getReceiptNo());
        mHeadingData.add("Receipt Date");
        mValueData.add(convertDate(paymentByPID.getPaymentDate()));

        mHeadingData.add("Mode Of Payment");
        mValueData.add(paymentByPID.getPaymentTypeMeaning());
        mHeadingData.add("DD/Cheque #");
        mValueData.add(paymentByPID.getChequeDD());

        mHeadingData.add("Bank");
        mValueData.add(paymentByPID.getBankName());

        mHeadingData.add("Transaction #");
        mValueData.add(paymentByPID.getTransactionNo());

        mHeadingData.add("Paid Amount");
        mValueData.add("\u20B9 "+paymentByPID.getPaymentAmount());

        mHeadingData.add("Supporting Docs.");
        mValueData.add(FileUtils.getFileName(context, Uri.parse(paymentByPID.getPaymentDocumentPath())));


        /*if (data.getWorkorderDTO().isGst()){
            mHeadingData.add("Estimated Amount"+"( * GST / IGST Amount Included )");
        }else {
            mHeadingData.add("Estimated Amount"+"( * GST / IGST Amount Excluded )");
        }

        mValueData.add("\u20B9 "+data.getWorkorderDTO().getWorkOrderEstimateAmount());*/

        PaymentSupportDialogAdapter customeDialogAdapter=new PaymentSupportDialogAdapter(context,mHeadingData,mValueData, paymentByPID);
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

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

   private void  specificPaymentEdit(ReceiptDTO receiptDTO){

       Fragment fragment = new EditPaymentFragment(receiptDTO);
       FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.fragment_container_main, fragment);
       fragmentTransaction.addToBackStack("PaymentDetails");
       fragmentTransaction.commitAllowingStateLoss();
   }

    private void downloadPaymentDocument(ReceiptDTO receiptDTO) {
        /*ArrayList<ReceiptDTO> receiptDTOArrayList=new ArrayList<>();
        receiptDTOArrayList.add(receiptDTO);

        Intent intent= new Intent(context, DownloadPaymentService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("payment", receiptDTOArrayList);
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

        String urlString=ApiClient.BASE_URL+"payments/downloadReceiptPDF/"+receiptDTO.getPaymentId();

        FileLoader.with(context)
                .load(urlString,false) //2nd parameter is optioal, pass true to force load from network
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PAYMENT)){

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
        intentFilter.addAction(MESSAGE_PAYMENT);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
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
