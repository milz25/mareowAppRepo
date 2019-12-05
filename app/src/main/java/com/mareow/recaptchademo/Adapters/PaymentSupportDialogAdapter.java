package com.mareow.recaptchademo.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.mareow.recaptchademo.Activities.LoginActivity;
import com.mareow.recaptchademo.DataModels.PaymentByPID;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.FileUtils;
import com.mareow.recaptchademo.ViewHolders.PaymentCustomeDialogViewHolder;


import java.io.File;
import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

public class PaymentSupportDialogAdapter extends RecyclerView.Adapter<PaymentCustomeDialogViewHolder>{
    Context context;
    List<String> mHeadingData;
    List<String> mValueData;
    PaymentByPID b;
    public PaymentSupportDialogAdapter(Context context, List<String> mHeadingData, List<String> mValueData, PaymentByPID b) {

        this.context=context;
        this.mHeadingData=mHeadingData;
        this.mValueData=mValueData;
        this.b=b;
    }

    @NonNull
    @Override
    public PaymentCustomeDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.payment_custom_dialog_adapter,parent,false);
        return new PaymentCustomeDialogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentCustomeDialogViewHolder holder, int position) {

        if (position==mHeadingData.size()-1){
            holder.txtHeading.setText(mHeadingData.get(position));
            if (mValueData.get(position)!=null){
                SpannableString s1 = new SpannableString(mValueData.get(position));
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        //Toast.makeText(context, "Term and Condition", Toast.LENGTH_SHORT).show();
                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(b.getPaymentDocument()));
                        //context.startActivity(browserIntent);
                        String filename=FileUtils.getFileName(context, Uri.parse(b.getPaymentDocumentPath()));
                        if (filename.contains(".pdf") || filename.contains(".PDF") || !filename.contains(".")){
                            showPdfDoc(b.getPaymentDocument());
                        }else {
                            showImageDialog(b.getPaymentDocument());
                        }


                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(context.getResources().getColor(R.color.Blue_Text));
                    }
                };
                s1.setSpan(clickableSpan, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtValue.setText(s1);
                holder.txtValue.setMovementMethod(LinkMovementMethod.getInstance());
            }

        }else {

            holder.txtHeading.setText(mHeadingData.get(position));
            holder.txtValue.setText(mValueData.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return mHeadingData.size();
    }



    private void showImageDialog(String paymentDocument) {

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
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //webSettings.setBuiltInZoomControls(true);
        mWebViewData.loadUrl(paymentDocument);

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

}
