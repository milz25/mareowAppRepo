package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.mareow.recaptchademo.DataModels.InvoiceByInvoiceId;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.PaymentByPID;
import com.mareow.recaptchademo.DataModels.ReceiptDTO;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerDrawerFragment.EditInvoiceFragment;
import com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments.UpdateRentalPlanFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.InvoiceMainInnerViewHolder;
import com.google.android.material.snackbar.Snackbar;

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

public class InvoiceMainInnerAdapter extends RecyclerView.Adapter<InvoiceMainInnerViewHolder> {
    Context context;
    List<InvoiceByUser> newInvoiceByUserList;
    ProgressDialog progressDialog;
    InvoiceByInvoiceId invoiceDetails;

    public InvoiceMainInnerAdapter(Context context, List<InvoiceByUser> newInvoiceByUserList) {
        this.context = context;
        this.newInvoiceByUserList=newInvoiceByUserList;

        if (this.context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait................");
        }
    }

    @NonNull
    @Override
    public InvoiceMainInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.invoice_main_adapter_inner_recycle,parent,false);
        return new InvoiceMainInnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceMainInnerViewHolder holder, int position) {

        holder.editInvoiceNo.setText(newInvoiceByUserList.get(position).getInvoiceNo());
        holder.editInvoiceNo.setInputType(InputType.TYPE_NULL);
        holder.editInvoiceDate.setText(convertDate(newInvoiceByUserList.get(position).getInvoiceDate()));
        holder.editInvoiceDate.setInputType(InputType.TYPE_NULL);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSpecificInvoiceApi(newInvoiceByUserList.get(position));
             //callCustomDialog("Invoice Details",newInvoiceByUserList.get(position));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDeleteDialogForInvoice(newInvoiceByUserList.get(position),position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEditFragment(newInvoiceByUserList.get(position));
            }
        });


        if (newInvoiceByUserList.get(position).getReceiptDTOs().size()>0){
            holder.btnEdit.setImageResource(R.drawable.edit_disable_round);
            holder.btnDelete.setImageResource(R.drawable.delete_round_disable);
            holder.btnEdit.setEnabled(false);
            holder.btnDelete.setEnabled(false);
        }



      //  holder.setIsRecyclable(false);


    }

    private void callEditFragment(InvoiceByUser invoiceByUser) {


        Fragment fragment=new EditInvoiceFragment(invoiceByUser);


        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();


    }


    @Override
    public int getItemCount() {
        return newInvoiceByUserList.size();
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


    public  void callCustomDialog(InvoiceByUser invoice, InvoiceByInvoiceId data) {

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
        dInvDate.setText(data.getInvoiceDate());
        dInvCategory=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invCategory);
        dInvCategory.setText(data.getInvCategory());
        dNetAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invnetAm);
        dNetAmount.setText("\u20B9 "+data.getInvoiceNetAmount());
        dPaidAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invpaidAm);
        dPaidAmount.setText("\u20B9 "+invoice.getInvoiceReceiveAmount());
        dDueAmount=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDueAm);
        dDueAmount.setText("\u20B9 "+invoice.getInvoicePendingAmount());

        dInvDocuHeading=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDocumentheading);
        dInvDocument=(AppCompatTextView)dialog.findViewById(R.id.invoice_dailog_invDocument);

        if (invoice.getInvCategoryCode().equals("INV_MSC_OWN") || invoice.getInvCategoryCode().equals("INV_MSC_REN")){
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

                       // showImageDialog(invoiceDetails.getInvoiceDocument());

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


    private void callDeleteDialogForInvoice(InvoiceByUser invoiceByUser,int position) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        LayoutInflater newinInflater=((AppCompatActivity)context).getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.delete_final);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Please confirm, would you like to delete an invoice"+invoiceByUser.getInvoiceNo()+" against work order"+invoiceByUser.getWorkOrderNo());

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callInvoiceDeleteApi(invoiceByUser.getInvoiceId(),position);
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void callInvoiceDeleteApi(int invoiceId,int position) {

        String token= TokenManager.getSessionToken();
        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> deleteCall=apiInterface.deleteInvoiceById("Bearer "+token,invoiceId);
        deleteCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    removeItem(position);
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired((AppCompatActivity)context);
                    }
                    if (response.code()==404){
                        showSnackbar("Record not Found.");
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired((AppCompatActivity)context);
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

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(((AppCompatActivity)context).getWindow().getDecorView().getRootView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void removeItem(int position){
        newInvoiceByUserList.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, newCategoryMachineList.size());
        notifyDataSetChanged();
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
        webSettings.setBuiltInZoomControls(true);
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
}
