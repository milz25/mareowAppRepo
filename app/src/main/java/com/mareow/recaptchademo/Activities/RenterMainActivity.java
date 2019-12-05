package com.mareow.recaptchademo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.FCMessaging.MyJobIntentService;
import com.mareow.recaptchademo.FCMessaging.NotifyService;
import com.mareow.recaptchademo.MainActivityFragments.AccountSettingsFragment;
import com.mareow.recaptchademo.MainActivityFragments.BookmarkFragment;
import com.mareow.recaptchademo.MainActivityFragments.ChangePasswordFragment;
import com.mareow.recaptchademo.MainActivityFragments.InviteOtherFragment;
import com.mareow.recaptchademo.MainActivityFragments.LogoutFragment;
import com.mareow.recaptchademo.MainActivityFragments.MessagesFragment;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.MainActivityFragments.NotificationFragment;
import com.mareow.recaptchademo.MainActivityFragments.PaymentFragment;
import com.mareow.recaptchademo.MainDetailsFragment.RenterDashboardFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.RenterMachineDetailsFragment;
import com.mareow.recaptchademo.RenterFragments.RenterMainHomeFragment;
import com.mareow.recaptchademo.RenterFragments.RenterOfferFragment;
import com.mareow.recaptchademo.RenterFragments.WorkOrderRenterFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.AppUpdateChecker;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterMainActivity extends AppCompatActivity {

    private static final String TAG = "TokenForFcm";
    public static NavigationView navigationViewRenter;
    private DrawerLayout drawer;
    private View navHeader;
    public static AppCompatImageView imgIconRenter;
    private AppCompatImageView imgIconMaskRenter;
    public static AppCompatTextView txtRenterUsername;
    private AppCompatTextView txtRole;
    private Toolbar toolbar;
    private boolean TOKEN_CHECK=true;
    public static int navItemIndexRenter = 0;
    // toolbar titles respected to selected nav menu item
    private static String[] activityRenterTitles;


    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    AppCompatTextView txtNotificationCount;
    int mCartNotiCount = 0;
    AppCompatTextView txtMessageCount;
    int mCartMessageCount=0;

    public static AppCompatTextView txtRenterTitle;
    private NotifyService notifyService;
    private Intent mServiceIntent;
    Context ctx;
    RelativeLayout btnProfileEdit;
    public Context getCtx() {
        return ctx;
    }
    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ctx=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_main);

        /*noInternetDialog = new NoInternetDialog.Builder(this)
                .setBgGradientStart(this.getResources().getColor(R.color.primary_white)) // Start color for background gradient
                .setBgGradientCenter(this.getResources().getColor(R.color.colorPrimary)) // Center color for background gradient
                .setBgGradientEnd(this.getResources().getColor(R.color.colorPrimaryDark)) // End color for background gradient// Background gradient orientation (possible values see below)// Type of background gradient (possible values see below)
                // .setDialogRadius() // Set custom radius for background gradient
                // .setTitleTypeface() // Set custom typeface for title text
                // .setMessageTypeface() // Set custom typeface for message text
                .setButtonColor(this.getResources().getColor(R.color.theme_orange)) // Set custom color for dialog buttons
                .setButtonTextColor(this.getResources().getColor(R.color.colorPrimary)) // Set custom text color for dialog buttons
                .setButtonIconsColor(this.getResources().getColor(R.color.colorPrimary)) // Set custom color for icons of dialog buttons
                .setWifiLoaderColor(this.getResources().getColor(R.color.theme_orange)) // Set custom color for wifi loader
                //.setConnectionCallback() // Set a Callback for network status
                .setCancelable(false) // Set cancelable status for dialog
                .build();


        if (this != null && !isDestroyed()){

            AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
            appUpdateChecker.checkForUpdate(false);
        }*/



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtRenterTitle=(AppCompatTextView)toolbar.findViewById(R.id.toolbar_title);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationViewRenter = (NavigationView) findViewById(R.id.nav_view);


        navHeader = navigationViewRenter.getHeaderView(0);
        imgIconRenter=(AppCompatImageView)navHeader.findViewById(R.id.icon_header);
        imgIconMaskRenter=(AppCompatImageView)navHeader.findViewById(R.id.icon_mask_header);


        btnProfileEdit=(RelativeLayout)navHeader.findViewById(R.id.main_profile_edit);
        btnProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.MY_PROFILE=true;
                Constants.REGISTRATION_DETAIL=false;
                callProfileFragment();
            }
        });

        txtRenterUsername=(AppCompatTextView)navHeader.findViewById(R.id.drawer_header_username);
        txtRole=(AppCompatTextView)navHeader.findViewById(R.id.drawer_header_role);

        // load toolbar titles from string resources
        activityRenterTitles = getResources().getStringArray(R.array.nav_item_activity_renter_titles);


        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndexRenter = 0;
            Constants.CURRENT_TAG = Constants.TAG_HOME;
            loadHomeFragment();
        }

        TOKEN_CHECK=callUnReadMessage();
        if (TOKEN_CHECK){
            callUnReadNotification();
        }else {
            return;
        }
       // getFcmToken();

        //startService(new Intent(this, NotifyService.class));
       /* notifyService=new NotifyService(getCtx());

        mServiceIntent = new Intent(getCtx(),NotifyService.class);
        if (!isMyServiceRunning(NotifyService.class)) {
            startService(mServiceIntent);
        }
*/

      //  onStartJobIntentService();

    }

     public void onStartJobIntentService() {
        Intent mIntent = new Intent(this, MyJobIntentService.class);
       // mIntent.putExtra("maxCountValue", 1000);
        MyJobIntentService.enqueueWork(this, mIntent);
    }

  /*  @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }*/


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

   /* private void getFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        Log.d(TAG, token);
                        Toast.makeText(RenterMainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    private void loadNavHeader() {
        // name, website
        String firstname=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null);
        String lastName=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null);
        txtRenterUsername.setText(firstname+" "+lastName);
        txtRole.setText("("+Constants.USER_ROLE+")");

        if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLATINUM") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLAT")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SILVER") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SLIVE")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAMOND") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAM")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_diamond));
        }


       /* if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Blue")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Platinum")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Gold")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Silver")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Diamond")){
            imgIconMaskRenter.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_diamond));
        }*/
        String userImagePath=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USER_IMAGE,null);
        // Loading profile image
        if (userImagePath!=null){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .transform(new CircleTransform(this));



            Glide.with(this).load(userImagePath)
                    .apply(options)
                    .into(imgIconRenter);

        }
        else {
            /*Glide.with(this)
                    .load(getResources().getDrawable(R.drawable.profile_header))
                    .transform(new CircleTransform(this))
                    .into(imgIcon);*/
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
            imgIconRenter.setImageBitmap(getCroppedBitmap(bitmap));
        }

    }


    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(Constants.CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //  toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container_main, fragment,Constants.CURRENT_TAG);
               /* if (navItemIndex==6 || navItemIndex==8 || navItemIndex==11){
                    fragmentTransaction.addToBackStack("logout");
                }*/
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //  toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndexRenter) {
            case 0:
                RenterMainHomeFragment renterMainHomeFragment = new RenterMainHomeFragment();
                return renterMainHomeFragment;
            case 1:
                RenterDashboardFragment mainDashBoardFragment = new RenterDashboardFragment();
                return mainDashBoardFragment;
            case 2:
                BookmarkFragment bookmarkFragment = new BookmarkFragment();
                return bookmarkFragment;
            case 3:
                RenterOfferFragment renterOfferFragment=new RenterOfferFragment();
                return renterOfferFragment;
            case 4:
                WorkOrderRenterFragment workOrderFragment=new WorkOrderRenterFragment();
                return workOrderFragment;

            case 5:
                PaymentFragment paymentFragment = new PaymentFragment();
                return paymentFragment;

          /*  case 6:
                MyProfileFragment myProfileFragment=new MyProfileFragment();
                return myProfileFragment;*/
            case 6:
                InviteOtherFragment inviteOtherFragment=new InviteOtherFragment();
                return inviteOtherFragment;
            case 7:
                ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                return changePasswordFragment;
            case 8:
                AccountSettingsFragment accountSettingsFragment=new AccountSettingsFragment();
                return accountSettingsFragment;
            case 9:
                LogoutFragment logoutFragment=new LogoutFragment();
                return logoutFragment;

            default:
                return new RenterMainHomeFragment();
        }
    }

    public static void setToolbarTitle() {
        txtRenterTitle.setText(activityRenterTitles[navItemIndexRenter]);
        txtRenterTitle.setTextSize(18);
    }

    private void selectNavMenu() {
        navigationViewRenter.getMenu().getItem(navItemIndexRenter).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationViewRenter.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndexRenter = 0;
                        Constants.CURRENT_TAG = Constants.TAG_HOME;
                        break;
                    case R.id.nav_dashboard:
                        navItemIndexRenter = 1;
                        Constants.CURRENT_TAG = Constants.TAG_DASH_BOARD;
                        break;
                    case R.id.nav_bookmark:
                        navItemIndexRenter = 2;
                        Constants.CURRENT_TAG = Constants.TAG_BOOKMARK;
                        break;
                    case R.id.nav_offer:
                        navItemIndexRenter = 3;
                        Constants.CURRENT_TAG = Constants.TAG_OFFER;
                        break;
                    case R.id.nav_work_order:
                        navItemIndexRenter = 4;
                        Constants.CURRENT_TAG = Constants.TAG_WORK_ORDER;
                        break;
                    case R.id.nav_payment:
                        navItemIndexRenter = 5;
                        Constants.CURRENT_TAG = Constants.TAG_PAYMENT;
                        break;
                   /* case R.id.nav_myprofile:
                        navItemIndexRenter = 6;
                        Constants.CURRENT_TAG = Constants.TAG_MY_PROFILE;
                        break;*/
                    case R.id.nav_invite_other:
                        navItemIndexRenter = 6;
                        Constants.CURRENT_TAG = Constants.TAG_INVITE_OTHER;
                        break;
                    case R.id.nav_change_password:
                        navItemIndexRenter = 7;
                        Constants.CURRENT_TAG = Constants.TAG_CHANGE_PASSWORD;
                        break;
                    case R.id.nav_account_setting:
                        navItemIndexRenter = 8;
                        Constants.CURRENT_TAG = Constants.TAG_ACCOUNT_SETTING;
                        break;
                    case R.id.nav_logout:
                        navItemIndexRenter = 9;
                        Constants.CURRENT_TAG = Constants.TAG_LOGOUT;
                        break;
                    default:
                        navItemIndexRenter = 0;
                        break;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (navItemIndexRenter==16){
            loadProfileFragments();
            return;
        }

        if (navItemIndexRenter==17){

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            RenterMainActivity.navItemIndexRenter=0;
            RenterMainActivity.navigationViewRenter.setCheckedItem(R.id.nav_home);
            Fragment renterMainHomeFragment = new RenterMainHomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_main, renterMainHomeFragment ); // give your fragment container id in first parameter
            transaction.commitAllowingStateLoss();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home

            if (navItemIndexRenter==20){
                loadMacineDetailsFragments();
                return;
            }

            if (navItemIndexRenter != 0) {
                navItemIndexRenter = 0;
                Constants.CURRENT_TAG = Constants.TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();

    }

    private void loadProfileFragments() {

        MyProfileFragment myProfileFragment=new MyProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, myProfileFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void loadMacineDetailsFragments(){

        RenterMachineDetailsFragment renterMachineDetailsFragment=new RenterMachineDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, renterMachineDetailsFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        final MenuItem menuItem1 = menu.findItem(R.id.action_notification);

        View actionNotiView = MenuItemCompat.getActionView(menuItem1);
        txtNotificationCount = (AppCompatTextView) actionNotiView.findViewById(R.id.noti_badge);
        setupNotiBadge();
        actionNotiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });


        final MenuItem menuItem2 = menu.findItem(R.id.action_message);
        View actionMessageView = MenuItemCompat.getActionView(menuItem2);
        txtMessageCount = (AppCompatTextView) actionMessageView.findViewById(R.id.message_badge);
        setupMessageBadge();

        actionMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem2);
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_message:
                txtRenterTitle.setText("Message");
                callMessagesFragment();
                break;
            case R.id.action_notification:
                txtRenterTitle.setText("Notification");
                callNotificationFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callMessagesFragment() {
        mCartMessageCount=0;
        setupMessageBadge();

        Fragment fragment = new MessagesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack("Messages");
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void callNotificationFragment() {
        mCartNotiCount=0;
        setupNotiBadge();

        Fragment fragment = new NotificationFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack("Notification");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setupNotiBadge() {
        if (txtNotificationCount != null) {
            if (mCartNotiCount == 0) {
                if (txtNotificationCount.getVisibility() != View.GONE) {
                    txtNotificationCount.setVisibility(View.GONE);
                }
            } else {
                txtNotificationCount.setText(String.valueOf(Math.min(mCartNotiCount, 99)));
                if (txtNotificationCount.getVisibility() != View.VISIBLE) {
                    txtNotificationCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setupMessageBadge() {
        if (txtMessageCount != null) {
            if (mCartMessageCount == 0) {
                if (txtMessageCount.getVisibility () != View.GONE) {
                    txtMessageCount.setVisibility(View.GONE);
                }
            } else {
                txtMessageCount.setText(String.valueOf(Math.min(mCartMessageCount, 99)));
                if (txtMessageCount.getVisibility() != View.VISIBLE) {
                    txtMessageCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private void callUnReadNotification(){
        String token=TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<String> unReadNotiCall=apiInterface.getUnReadNotification("Bearer "+token,partyId);
        unReadNotiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    mCartNotiCount=Integer.parseInt(data);
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(RenterMainActivity.this);
                    } if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(RenterMainActivity.this);
                    }else {
                        mCartNotiCount=0;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RenterMainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean callUnReadMessage(){
        String token=TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<String> unReadmessageCall=apiInterface.getUnReadMessage("Bearer "+token,partyId);
        unReadmessageCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    mCartMessageCount=Integer.parseInt(data);
                    TOKEN_CHECK=true;
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(RenterMainActivity.this);
                        TOKEN_CHECK=false;
                    } if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(RenterMainActivity.this);
                        TOKEN_CHECK=false;
                    }else {
                        mCartMessageCount=0;
                        TOKEN_CHECK=true;
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RenterMainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                TOKEN_CHECK=true;
            }
        });

        return TOKEN_CHECK;
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        invokeFragmentManagerNoteStateNotSaved();
        super.onSaveInstanceState(outState);
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        /**
         * For post-Honeycomb devices
         */
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        try {
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!"Activity".equals(cls.getSimpleName()));
            Field fragmentMgrField = cls.getDeclaredField("mFragments");
            fragmentMgrField.setAccessible(true);

            Object fragmentMgr = fragmentMgrField.get(this);
            cls = fragmentMgr.getClass();

            Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved", new Class[] {});
            noteStateNotSavedMethod.invoke(fragmentMgr, new Object[] {});
            Log.d("DLOutState", "Successful call for noteStateNotSaved!!!");
        } catch (Exception ex) {
            Log.e("DLOutState", "Exception on worka FM.noteStateNotSaved", ex);
        }
    }

    public void callProfileFragment(){

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }

        Fragment fragment = new MyProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack("Profile");
        fragmentTransaction.commitAllowingStateLoss();
    }

}
