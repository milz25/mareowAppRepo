package com.mareow.recaptchademo.MainActivityFragments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.MessageRecycleAdapter;
import com.mareow.recaptchademo.DataModels.Message;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mMessageRecycle;
    AppCompatTextView txtNoRecord;

    String token;
    int partyId;
    List<Message> messageList=new ArrayList<>();
    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_messages, container, false);
        initView(view);
        token= TokenManager.getSessionToken();
        partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);

        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Messages");
        }else if (Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Messages");
        }else {
            MainActivity.txtTitle.setText("Messages");
        }
        callgetAllMessagesApi();
        return view;
    }

    private void initView(View view) {

        mMessageRecycle=(RecyclerView)view.findViewById(R.id.mesasge_frg_recycle);
        mMessageRecycle.setHasFixedSize(false);
        mMessageRecycle.setItemAnimator(new DefaultItemAnimator());
        mMessageRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        txtNoRecord=(AppCompatTextView)view.findViewById(R.id.message_frg_no_record);

    }


    private void callgetAllMessagesApi() {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<Message>> messageCall=apiInterface.getUserMessages("Bearer "+token,partyId);
        messageCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()){
                    messageList=response.body();
                    callMessageRecycleAdapter();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }else {
                        showSnackbar("No Record Found");
                        mMessageRecycle.setVisibility(View.GONE);
                        txtNoRecord.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMessageRecycleAdapter() {
        if (messageList.size()>0){
            RecyclerViewClickListener listener=new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                    Message message=messageList.get(position);
                    Fragment fragment = new ConversationMessageFragment(message);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_main, fragment);
                    fragmentTransaction.addToBackStack("conversation");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            };

            MessageRecycleAdapter messageRecycleAdapter=new MessageRecycleAdapter(getActivity(),messageList,listener);
            mMessageRecycle.setAdapter(messageRecycleAdapter);
        }else {
            mMessageRecycle.setVisibility(View.GONE);
            txtNoRecord.setVisibility(View.VISIBLE);
        }
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
