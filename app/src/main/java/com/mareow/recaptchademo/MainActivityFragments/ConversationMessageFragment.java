package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mareow.recaptchademo.Adapters.ConversationRecycleAdapter;
import com.mareow.recaptchademo.DataModels.Message;
import com.mareow.recaptchademo.DataModels.SendMessageModel;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationMessageFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mConversionRecycle;
    AppCompatEditText editMessage;
    AppCompatImageView btnSend;

    int fromId;
    int toId;
    String token;
    List<Message> messageHistory=new ArrayList<>();

    ProgressDialog progressDialog;

    Message newMessage;
    ConversationRecycleAdapter adapter;
    public ConversationMessageFragment(Message message) {
        // Required empty public constructor
         fromId=message.getFrom();
         toId=message.getTo();

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
        View view=inflater.inflate(R.layout.fragment_conversation_message, container, false);
        initView(view);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.............");
        }
        token= TokenManager.getSessionToken();
        callMessageHistoryApi();
        return view;
    }
    private void initView(View view) {

        mConversionRecycle=(RecyclerView)view.findViewById(R.id.conversation_recycle);
        mConversionRecycle.setHasFixedSize(false);
        mConversionRecycle.setItemAnimator(new DefaultItemAnimator());
        mConversionRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));


        editMessage=(AppCompatEditText) view.findViewById(R.id.conversation_edit_mesage);
        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (!s.toString().equals("")){
                  btnSend.setImageResource(R.drawable.send_theme);
              }else {
                  btnSend.setImageResource(R.drawable.send_gray);
              }
            }
        });
        btnSend=(AppCompatImageView) view.findViewById(R.id.conversation_send);
        btnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.conversation_send:
                //callUpdateMessageApi();
                sendMessageApi();
                break;
        }
    }

    private void sendMessageApi() {

        String message=editMessage.getText().toString();
        if (TextUtils.isEmpty(message)){
            editMessage.setError("Enter message");
            editMessage.requestFocus();
            return;
        }

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        SendMessageModel sendMessageModel=new SendMessageModel();
        sendMessageModel.setMessage(message);
        sendMessageModel.setTo(String.valueOf(toId));
        sendMessageModel.setFrom(String.valueOf(partyId));
        sendMessageModel.setCreatedBy(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERNAME,null));

        if (progressDialog!=null)
            progressDialog.show();

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<Message> sendmessageCall=apiInterface.sendMessage("Bearer "+token,sendMessageModel);
        sendmessageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){

                    newMessage=new Message();
                    newMessage.setFromMsg(response.body().getMessage());
                    Date today=new Date();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("E d MMM yyyy HH:mm");
                    String dateString=simpleDateFormat.format(today);
                    newMessage.setFromMsgDateStr(dateString);

                    if (messageHistory.size() > 0){
                       messageHistory.add(messageHistory.size(), newMessage);
                       adapter.notifyDataSetChanged();
                       mConversionRecycle.scrollToPosition(messageHistory.size()-1);
                   }
                    editMessage.setText("");
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }else {
                        showSnackbar("Record not found");
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });

    }

    private void callMessageHistoryApi() {

        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

        Call<List<Message>> historyMessageCall=apiInterface.getMessagesHistory("Bearer "+token,toId,partyId);
        historyMessageCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()){
                    messageHistory.clear();
                    messageHistory=response.body();
                    callMessageHistoryRecycleAdapter();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }else {
                        Toast.makeText(getActivity(), "Record not Found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getContext(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMessageHistoryRecycleAdapter() {
        if (messageHistory.size()>0){
            adapter=new ConversationRecycleAdapter(getActivity(),messageHistory);
            mConversionRecycle.setAdapter(adapter);
            mConversionRecycle.scrollToPosition(messageHistory.size()-1);

        }else {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void callUpdateMessageApi() {

        String message=editMessage.getText().toString();
        if (TextUtils.isEmpty(message)){
            editMessage.setError("Enter message");
            editMessage.requestFocus();
            return;
        }

        if (progressDialog!=null)
            progressDialog.show();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<List<Message>> updateMessageHistoryCall=apiInterface.getUpdatedMessagesHistory("Bearer "+token,toId,partyId);
        updateMessageHistoryCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                     messageHistory.clear();
                     messageHistory=response.body();
                     editMessage.setText("");
                     callMessageHistoryRecycleAdapter();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }else {
                        Toast.makeText(getActivity(), "Record not Found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getContext(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
