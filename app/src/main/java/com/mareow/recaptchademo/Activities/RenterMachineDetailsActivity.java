package com.mareow.recaptchademo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.RenterMachineDetailsFragment;
import com.mareow.recaptchademo.Utils.Constants;

import java.util.ArrayList;

public class RenterMachineDetailsActivity extends AppCompatActivity {

    Toolbar mToolbar;
    AppCompatTextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_machine_details);

        mToolbar=(Toolbar)findViewById(R.id.renter_main_details_toolbar);
        setSupportActionBar(mToolbar);
        mTitle=(AppCompatTextView)findViewById(R.id.renter_main_details_title);
        mTitle.setText("Machine Details");

        ArrayList<RenterMachine> currentMachine=(ArrayList<RenterMachine>)getIntent().getSerializableExtra("mylist");

        RenterMachineDetailsFragment detailsFragment=new RenterMachineDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("newList",currentMachine);
        detailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_renter, detailsFragment, Constants.CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();


    }
}
