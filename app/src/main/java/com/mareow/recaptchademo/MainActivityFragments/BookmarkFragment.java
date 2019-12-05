package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.RenterBookMarkMachineAdapter;
import com.mareow.recaptchademo.DataModels.FavoriteMachine;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.RenterMachineDetailsFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.SpacesItemDecoration;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final int DEFAULT_SPAN=2;
    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    List<FavoriteMachine> favoriteMachinesList=new ArrayList<>();
    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bookmark, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..............");
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Bookmark Machine");
        }else {
            MainActivity.txtTitle.setText("Bookmark Machine");
        }
        callBookMArlkApi();
        initView(view);
        return view;
    }


    private void initView(View view) {

        mRecyclerView=(RecyclerView)view.findViewById(R.id.book_mark_frg_recycle);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),DEFAULT_SPAN);
        mRecyclerView.setLayoutManager(gridLayoutManager);


    }

    private void callBookMArlkApi() {
            progressDialog.show();
            String token= TokenManager.getSessionToken();
            int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
            progressDialog.show();
            ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
            Call<List<FavoriteMachine>> bookmarkCall=apiInterface.bookMarkForRenter("Bearer "+token,partyId);
            bookmarkCall.enqueue(new Callback<List<FavoriteMachine>>() {
                @Override
                public void onResponse(Call<List<FavoriteMachine>> call, Response<List<FavoriteMachine>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        favoriteMachinesList=response.body();
                        callRecycleAdapter();
                    }else {
                        if (response.code()==401){
                            TokenExpiredUtils.tokenExpired(getActivity());
                        }
                        if (response.code()==404){
                            Gson gson = new GsonBuilder().create();
                            StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                            try {
                                mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                               // Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<FavoriteMachine>> call, Throwable t) {
                    Toast.makeText(getActivity(),"Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


    }

    private void callRecycleAdapter() {
        if (favoriteMachinesList.size()>0){
            List<RenterMachine> renterFavoriteMachines=new ArrayList<>();

            for (int i=0;i<favoriteMachinesList.size();i++){
                renterFavoriteMachines.add(favoriteMachinesList.get(i).getMachine());
            }


            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {

                    RenterMachine machine=renterFavoriteMachines.get(position);
                    CallMachineDetailsFragment(machine);
                }
            };

            RenterBookMarkMachineAdapter adapter=new RenterBookMarkMachineAdapter(getActivity(),renterFavoriteMachines,listener);
           // RenterSimilarAdapter adapter=new RenterSimilarAdapter(getActivity(),renterFavoriteMachines,listener);
            mRecyclerView.setAdapter(adapter);
        }

    }


    private void CallMachineDetailsFragment(RenterMachine renterMachine) {

        ArrayList<RenterMachine> machines=new ArrayList<>();
        machines.add(renterMachine);
        RenterMachineDetailsFragment detailsFragment=new RenterMachineDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("newList",machines);
        detailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, detailsFragment);
        fragmentTransaction.addToBackStack("bookmark");
        fragmentTransaction.commitAllowingStateLoss();
    }





}
