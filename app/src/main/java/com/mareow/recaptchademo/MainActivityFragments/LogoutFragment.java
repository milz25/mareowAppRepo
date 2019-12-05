package com.mareow.recaptchademo.MainActivityFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mareow.recaptchademo.Activities.LoginActivity;
import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.MainDetailsFragment.OperatorDashBoradFragment;
import com.mareow.recaptchademo.MainDetailsFragment.OwnerDashboradFragment;
import com.mareow.recaptchademo.MainDetailsFragment.SupervisorDashboardFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.RenterMainHomeFragment;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogoutFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogoutFragment newInstance(String param1, String param2) {
        LogoutFragment fragment = new LogoutFragment();
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
       View view=inflater.inflate(R.layout.fragment_logout, container, false);
       if (Constants.USER_ROLE.equals("Renter")){
           RenterMainActivity.txtRenterTitle.setText("Logout");
       }else if(Constants.USER_ROLE.equals("Owner")){
          OwnerMainActivity.txtOwnerTitle.setText("Logout");
       }else {
           MainActivity.txtTitle.equals("Logout");
       }

       showExitDailog();
       initView(view);
       //showLogoutDialog();
        return view;
    }

    private void initView(View view) {
        //showExitDailog();
    }

    @Override
    public void onClick(View v) {

    }

   /* private void showLogoutDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.logout_custom_dialog);
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button\
        int width = (int)(getActivity().getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getActivity().getResources().getDisplayMetrics().heightPixels*0.80);


        LinearLayout btnLinear=(LinearLayout) dialog.findViewById(R.id.logout_dialog_ln);
        AppCompatImageView btnClear=(AppCompatImageView)dialog.findViewById(R.id.logout_dialog_clear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainFragments();
            }
        });
        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                TokenManager.clearSession();
                callLoginActivity();
            }
        });
        AppCompatButton dialogButton = (AppCompatButton) dialog.findViewById(R.id.logout_dialog_btnlogout);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                TokenManager.clearSession();
                callLoginActivity();
            }
        });
        dialog.getWindow().setLayout(width, height);
        dialog.show();

    }
*/
    private void callLoginActivity() {
        Intent intent=new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    public  void callMainFragments(){

        MainActivity.navItemIndex=0;
        MainActivity.navigationView.setCheckedItem(R.id.nav_dashboard);
        Fragment mainDashBoardFragment=null;

        if (Constants.USER_ROLE.equals("Supervisor")){
           mainDashBoardFragment = new SupervisorDashboardFragment();
        }
        if (Constants.USER_ROLE.equals("Operator")){
           mainDashBoardFragment = new OperatorDashBoradFragment();
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, mainDashBoardFragment ); // give your fragment container id in first parameter
        transaction.commitAllowingStateLoss();
    }

    public  void callRenterHomeFragments(){

        RenterMainActivity.navItemIndexRenter=0;
        RenterMainActivity.navigationViewRenter.setCheckedItem(R.id.nav_home);
        Fragment mainDashBoardFragment = new RenterMainHomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, mainDashBoardFragment ); // give your fragment container id in first parameter
        transaction.commitAllowingStateLoss();
    }

    private void callOwnerMainFragment() {

        OwnerMainActivity.navItemIndexOwner=0;
        OwnerMainActivity.navigationViewOwner.setCheckedItem(R.id.nav_dashboard);
        Fragment mainDashBoardFragment = new OwnerDashboradFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, mainDashBoardFragment ); // give your fragment container id in first parameter
        transaction.commit();
    }

    private void showExitDailog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
        LayoutInflater newinInflater=getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.logout_final);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Are you sure, you want to logout?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                TokenManager.clearSession();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                callLoginActivity();
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // getActivity().getSupportFragmentManager().popBackStack();
                if (Constants.USER_ROLE.equals("Renter")){
                    callRenterHomeFragments();
                }else if (Constants.USER_ROLE.equals("Owner")){
                    callOwnerMainFragment();
                }else {
                    callMainFragments();
                }
            }
        });
        dialog.show();
        
    }



}
