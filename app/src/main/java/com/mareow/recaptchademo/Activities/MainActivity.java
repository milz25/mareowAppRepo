package com.mareow.recaptchademo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.FCMessaging.MyJobIntentService;
import com.mareow.recaptchademo.MainActivityFragments.AccountSettingsFragment;
import com.mareow.recaptchademo.MainActivityFragments.BookmarkFragment;
import com.mareow.recaptchademo.MainActivityFragments.ChangePasswordFragment;
import com.mareow.recaptchademo.MainActivityFragments.InviteOtherFragment;
import com.mareow.recaptchademo.MainActivityFragments.InvoiceFragment;
import com.mareow.recaptchademo.MainActivityFragments.LogoutFragment;
import com.mareow.recaptchademo.MainActivityFragments.MachineModelFragment;
import com.mareow.recaptchademo.MainActivityFragments.MainDashBoardFragment;
import com.mareow.recaptchademo.MainActivityFragments.MessagesFragment;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.MainActivityFragments.NotificationFragment;
import com.mareow.recaptchademo.MainActivityFragments.PaymentFragment;
import com.mareow.recaptchademo.MainActivityFragments.RentalPlanFragment;
import com.mareow.recaptchademo.MainActivityFragments.TermAndConditionFragment;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.MainDetailsFragment.OperatorDashBoradFragment;
import com.mareow.recaptchademo.MainDetailsFragment.SupervisorDashboardFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.AppUpdateChecker;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBarDrawerToggle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TokenForFcm";
    public static NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    public static AppCompatImageView imgIcon;
    private AppCompatImageView imgIconMask;
    public static AppCompatTextView txtUsername;
    private AppCompatTextView txtRole;
    private Toolbar toolbar;
    private boolean TOKEN_CHECK=true;

    public static int navItemIndex = 0;
    // toolbar titles respected to selected nav menu item
    private static String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    AppCompatTextView txtNotificationCount;
    int mCartNotiCount = 0;
    AppCompatTextView txtMessageCount;
    int mCartMessageCount=0;

    RelativeLayout btnProfileEdit;

    public static AppCompatTextView txtTitle;

    public static ActionBar actionBar;
    NoInternetDialog noInternetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        noInternetDialog = new NoInternetDialog.Builder(this)
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
        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTitle=(AppCompatTextView)toolbar.findViewById(R.id.toolbar_title);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Constants.USER_ROLE= TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null);

        if (Constants.USER_ROLE.equals("Supervisor")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_supervisor);
        }

        if (Constants.USER_ROLE.equals("Operator")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_operator);
        }
        if (Constants.USER_ROLE.equals("Renter")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_renter);

            //callSearchRenterMainActivity();
        }

        if (Constants.USER_ROLE.equals("Owner")){
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_owner);
        }

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        imgIcon=(AppCompatImageView)navHeader.findViewById(R.id.icon_header);
        imgIconMask=(AppCompatImageView)navHeader.findViewById(R.id.icon_mask_header);
        btnProfileEdit=(RelativeLayout)navHeader.findViewById(R.id.main_profile_edit);
        btnProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.MY_PROFILE=true;
                Constants.REGISTRATION_DETAIL=false;
                callProfileFragment();
            }
        });


        txtUsername=(AppCompatTextView)navHeader.findViewById(R.id.drawer_header_username);
        txtRole=(AppCompatTextView)navHeader.findViewById(R.id.drawer_header_role);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            Constants.CURRENT_TAG = Constants.TAG_DASH_BOARD;
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

       // onStartJobIntentService();
    }


    public void onStartJobIntentService() {
        Intent mIntent = new Intent(this, MyJobIntentService.class);
        // mIntent.putExtra("maxCountValue", 1000);
        MyJobIntentService.enqueueWork(this, mIntent);
    }
  /*  private void callSearchRenterMainActivity() {
        Intent intent=new Intent(MainActivity.this,RenterMainActivity.class);
        startActivity(intent);
        this.finish();
    }*/

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
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        String firstname=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_FIRST_NAME,null);
        String lastName=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_LASTNAME,null);
        txtUsername.setText(firstname+" "+lastName);
        txtRole.setText("("+Constants.USER_ROLE+")");

        if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLATINUM") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLAT")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SILVER") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SLIVE")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAMOND") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAM")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_diamond));
        }


       /* if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Blue")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Platinum")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Gold")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Silver")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Diamond")){
            imgIconMask.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.category_diamond));
        }*/
        String userImagePath=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USER_IMAGE,null);
        // Loading profile image
        if (userImagePath!=null){

           /* RoundedBitMap roundedBitMap=new RoundedBitMap(this);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
            RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);

            Glide.with(this).load("http://18.204.165.238:8080/mareow-api/"+userImagePath)
                    .asBitmap().centerCrop().placeholder(roundedImageDrawable).into(new BitmapImageViewTarget(imgIcon) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imgIcon.setImageDrawable(circularBitmapDrawable);
                }
            });
*/

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
                    .into(imgIcon);

        }
        else {
            /*Glide.with(this)
                    .load(getResources().getDrawable(R.drawable.profile_header))
                    .transform(new CircleTransform(this))
                    .into(imgIcon);*/
               Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
               imgIcon.setImageBitmap(getCroppedBitmap(bitmap));
             }


     /*   RoundedBitMap roundedBitMap=new RoundedBitMap(this);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.profile);
        RoundedBitmapDrawable roundedImageDrawable =roundedBitMap.createRoundedBitmapImageDrawableWithBorder(bitmap);
        imgIcon.setImageDrawable(roundedImageDrawable);*/


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        //setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
       /* if (getSupportFragmentManager().findFragmentByTag(Constants.CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
          //  toggleFab();
            return;
        }*/

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
        switch (navItemIndex) {
            case 0:
               /* MainDashBoardFragment dashBoardFragment = new MainDashBoardFragment();
                return dashBoardFragment;*/
                if (Constants.USER_ROLE.equals("Supervisor")){
                    SupervisorDashboardFragment dashboardFragment = new SupervisorDashboardFragment();
                    return dashboardFragment;
                }
                if (Constants.USER_ROLE.equals("Operator")){
                    OperatorDashBoradFragment dashBoradFragment = new OperatorDashBoradFragment();
                    return dashBoradFragment;
                }
            case 1:
                WorkOrderFragment workOrderFragment = new WorkOrderFragment();
                return workOrderFragment;
            case 2:
                if (Constants.USER_ROLE.equals("Supervisor")){
                   /* Constants.MY_PROFILE=true;
                    Constants.REGISTRATION_DETAIL=false;
                    MyProfileFragment myProfileFragment=new MyProfileFragment();
                    *//*Intent intent=new Intent(this,DetailsSubmissionActivity.class);
                    startActivity(intent);
                    this.finish();*//*
                    return myProfileFragment;*/
                    InviteOtherFragment inviteOtherFragment = new InviteOtherFragment();
                    return inviteOtherFragment;
                }
                if (Constants.USER_ROLE.equals("Operator")){
                    MachineModelFragment machineModelFragment = new MachineModelFragment();
                    return machineModelFragment;
                }
                if (Constants.USER_ROLE.equals("Renter")){
                    BookmarkFragment bookmarkFragment = new BookmarkFragment();
                    return bookmarkFragment;
                }
                if (Constants.USER_ROLE.equals("Owner")){
                    MachineModelFragment machineModelFragment = new MachineModelFragment();
                    return machineModelFragment;
                }
            case 3:
                if (Constants.USER_ROLE.equals("Supervisor")){
                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                    return changePasswordFragment;
                }
                if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){
                    PaymentFragment paymentFragment = new PaymentFragment();
                    return paymentFragment;
                }
            case 4:
                if (Constants.USER_ROLE.equals("Supervisor")){
                    AccountSettingsFragment accountSettingsFragment = new AccountSettingsFragment();
                    return accountSettingsFragment;
                }
                if (Constants.USER_ROLE.equals("Operator")){
                    /*Intent intent=new Intent(this,DetailsSubmissionActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();*/
                  /*  Constants.MY_PROFILE=true;
                    Constants.REGISTRATION_DETAIL=false;
                    MyProfileFragment myProfileFragment=new MyProfileFragment();
                    return myProfileFragment;*/
                    InviteOtherFragment inviteOtherFragment=new InviteOtherFragment();
                    return inviteOtherFragment;

                }
                if (Constants.USER_ROLE.equals("Renter") ){
                    /*Intent intent=new Intent(this,DetailsSubmissionActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();*/
                   /* Constants.MY_PROFILE=true;
                    Constants.REGISTRATION_DETAIL=false;
                    MyProfileFragment myProfileFragment=new MyProfileFragment();
                    return myProfileFragment;*/
                    InviteOtherFragment inviteOtherFragment=new InviteOtherFragment();
                    return inviteOtherFragment;

                }
                if (Constants.USER_ROLE.equals("Owner")){
                    RentalPlanFragment rentalPlanFragment = new RentalPlanFragment();
                    return rentalPlanFragment;
                }
            case 5:
                if (Constants.USER_ROLE.equals("Supervisor")){
                    LogoutFragment logoutFragment = new LogoutFragment();
                    return logoutFragment;
                }
                if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter") ){
                    ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                    return changePasswordFragment;
                }
                if (Constants.USER_ROLE.equals("Owner")){
                    TermAndConditionFragment termAndConditionFragment = new TermAndConditionFragment();
                    return termAndConditionFragment;
                }
            case 6:
             /*   if (Constants.USER_ROLE.equals("Supervisor")){
                    LogoutFragment logoutFragment = new LogoutFragment();
                    return logoutFragment;
                    //showExitDailog();
                }*/
                if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter") ){
                    AccountSettingsFragment accountSettingsFragment=new AccountSettingsFragment();
                    return accountSettingsFragment;
                }
                if (Constants.USER_ROLE.equals("Owner")){
                    InvoiceFragment invoiceFragment = new InvoiceFragment();
                    return invoiceFragment;
                }
            case 7:
                if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter") ){
                    LogoutFragment logoutFragment=new LogoutFragment();
                    return logoutFragment;
                }
                if (Constants.USER_ROLE.equals("Owner")){
                  /*  Constants.MY_PROFILE=true;
                    Constants.REGISTRATION_DETAIL=false;
                    MyProfileFragment myProfileFragment=new MyProfileFragment();
                    return myProfileFragment;*/
                    InviteOtherFragment inviteOtherFragment=new InviteOtherFragment();
                    return inviteOtherFragment;
                }
            case 8:
              /*  if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter") ){
                    LogoutFragment logoutFragment=new LogoutFragment();
                    return logoutFragment;
                    //showExitDailog();
                }*/
                if (Constants.USER_ROLE.equals("Owner")){
                    ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                    return changePasswordFragment;
                }
            case 9:
                if (Constants.USER_ROLE.equals("Owner")){
                    AccountSettingsFragment accountSettingsFragment=new AccountSettingsFragment();
                    return accountSettingsFragment;
                }
            case 10:
                if (Constants.USER_ROLE.equals("Owner")){
                    LogoutFragment logoutFragment=new LogoutFragment();
                    return logoutFragment;
                }
         /*   case 11:
                if (Constants.USER_ROLE.equals("Owner")){
                    LogoutFragment logoutFragment=new LogoutFragment();
                    return logoutFragment;
                   //showExitDailog();
                }*/
            default:
                return new MainDashBoardFragment();
        }
    }

    public static void setToolbarTitle() {
/*
        if (navItemIndex==0 ||navItemIndex==1){
            txtTitle.setText(activityTitles[navItemIndex]);
            txtTitle.setTextSize(18);
        }else if (navItemIndex==2){
            if (Constants.USER_ROLE.equals("Supervisor")){
                txtTitle.setText(activityTitles[7]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText("Bookmark");
                txtTitle.setTextSize(18);
                return;
            }
        }else if (navItemIndex==3){
            if (Constants.USER_ROLE.equals("Supervisor")){
                txtTitle.setText(activityTitles[8]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")|| Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        }else if (navItemIndex==4){
            if (Constants.USER_ROLE.equals("Supervisor")){
                txtTitle.setText(activityTitles[9]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText(activityTitles[7]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        }else if (navItemIndex==5){
            if (Constants.USER_ROLE.equals("Supervisor")){
                txtTitle.setText(activityTitles[10]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText(activityTitles[8]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        } else if (navItemIndex==6){
            if (Constants.USER_ROLE.equals("Supervisor")){
                txtTitle.setText(activityTitles[11]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText(activityTitles[9]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        }
        else if (navItemIndex==7){
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText(activityTitles[10]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        }
        else if (navItemIndex==8){
            if (Constants.USER_ROLE.equals("Operator")|| Constants.USER_ROLE.equals("Renter")){
                txtTitle.setText(activityTitles[11]);
                txtTitle.setTextSize(18);
                return;
            }
            if (Constants.USER_ROLE.equals("Owner")){
                txtTitle.setText(activityTitles[navItemIndex]);
                txtTitle.setTextSize(18);
                return;
            }
        }
        else if (navItemIndex==9 ||navItemIndex==10){
            txtTitle.setText(activityTitles[navItemIndex]);
            txtTitle.setTextSize(18);
            return;
        }else if (navItemIndex==11){
            txtTitle.setText(activityTitles[11]);
            txtTitle.setTextSize(18);
            return;
        }else if (navItemIndex==14){
            txtTitle.setText("Work Order (Activity Log)");
            txtTitle.setTextSize(16);
        }else if (navItemIndex==13){
            //String title="<b>"+activityTitles[1]+"</b>";
            txtTitle.setTextSize(18);
            txtTitle.setText(activityTitles[1]);
        }else if (navItemIndex==100){
            txtTitle.setTextSize(18);
            txtTitle.setText("Notifications");
        }else if (navItemIndex==101){
            txtTitle.setTextSize(18);
            txtTitle.setText("Messages");
        }*/



        // getSupportActionBar().setTitle(activityTitles[navItemIndex]);


    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        Constants.CURRENT_TAG = Constants.TAG_DASH_BOARD;
                        break;
                    case R.id.nav_work_order:
                        navItemIndex = 1;
                        Constants.CURRENT_TAG = Constants.TAG_WORK_ORDER;
                        break;
                    case R.id.nav_machine_model:
                        navItemIndex = 2;
                        Constants.CURRENT_TAG = Constants.TAG_MACHINE_MODEL;
                        break;
                    case R.id.nav_bookmark:
                        navItemIndex = 2;
                        Constants.CURRENT_TAG = Constants.TAG_BOOKMARK;
                        break;
                    case R.id.nav_payment:
                        navItemIndex = 3;
                        Constants.CURRENT_TAG = Constants.TAG_PAYMENT;
                        break;
                    case R.id.nav_rental_plan:
                        navItemIndex = 4;
                        Constants.CURRENT_TAG = Constants.TAG_RENTAL_PLAN;
                        break;
                    case R.id.nav_term_and_condition:
                        navItemIndex = 5;
                        Constants.CURRENT_TAG = Constants.TAG_INVOICE;
                        break;
                    case R.id.nav_invoice:
                        navItemIndex = 6;
                        Constants.CURRENT_TAG = Constants.TAG_INVOICE;
                        break;
                  /*  case R.id.nav_myprofile:
                        if (Constants.USER_ROLE.equals("Supervisor")){
                            navItemIndex = 2;
                        }
                        if (Constants.USER_ROLE.equals("Operator")){
                            navItemIndex = 4;
                        }
                        if (Constants.USER_ROLE.equals("Renter")){
                            navItemIndex = 4;
                        }
                        if (Constants.USER_ROLE.equals("Owner")){
                            navItemIndex = 7;
                        }
                        Constants.CURRENT_TAG = Constants.TAG_MY_PROFILE;
                        break;*/
                    case R.id.nav_invite_other:
                        if (Constants.USER_ROLE.equals("Supervisor")){
                            navItemIndex = 2;
                        }
                        if (Constants.USER_ROLE.equals("Operator")){
                            navItemIndex = 4;
                        }
                        if (Constants.USER_ROLE.equals("Renter")){
                            navItemIndex = 4;
                        }
                        if (Constants.USER_ROLE.equals("Owner")){
                            navItemIndex = 7;
                        }
                        Constants.CURRENT_TAG = Constants.TAG_INVITE_OTHER;
                        break;
                    case R.id.nav_change_password:
                        if (Constants.USER_ROLE.equals("Supervisor")){
                            navItemIndex = 3;
                        }
                        if (Constants.USER_ROLE.equals("Operator")){
                            navItemIndex = 5;
                        }
                        if (Constants.USER_ROLE.equals("Renter")){
                            navItemIndex = 5;
                        }
                        if (Constants.USER_ROLE.equals("Owner")){
                            navItemIndex = 8;
                        }
                        Constants.CURRENT_TAG = Constants.TAG_CHANGE_PASSWORD;
                        break;
                    case R.id.nav_account_setting:
                        if (Constants.USER_ROLE.equals("Supervisor")){
                            navItemIndex = 4;
                        }
                        if (Constants.USER_ROLE.equals("Operator")){
                            navItemIndex = 6;
                        }
                        if (Constants.USER_ROLE.equals("Renter")){
                            navItemIndex = 6;
                        }
                        if (Constants.USER_ROLE.equals("Owner")){
                            navItemIndex = 9;
                        }
                        Constants.CURRENT_TAG = Constants.TAG_ACCOUNT_SETTING;
                        break;
                    case R.id.nav_logout:
                        if (Constants.USER_ROLE.equals("Supervisor")){
                            navItemIndex = 5;
                        }
                        if (Constants.USER_ROLE.equals("Operator")){
                            navItemIndex = 7;
                        }
                        if (Constants.USER_ROLE.equals("Renter")){
                            navItemIndex = 7;
                        }
                        if (Constants.USER_ROLE.equals("Owner")){
                            navItemIndex = 10;
                        }
                        Constants.CURRENT_TAG = Constants.TAG_LOGOUT;
                        break;
                    default:
                        navItemIndex = 0;
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

        if (navItemIndex==16){
            loadProfileFragments();
            return;
        }

        if (navItemIndex==17){

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            MainActivity.navItemIndex=0;
            MainActivity.navigationView.setCheckedItem(R.id.nav_dashboard);
            Fragment mainDashBoardFragment;
            if (Constants.USER_ROLE.equals("Operator")){
                mainDashBoardFragment = new OperatorDashBoradFragment();
            }else {
                mainDashBoardFragment=new SupervisorDashboardFragment();
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_main, mainDashBoardFragment ); // give your fragment container id in first parameter
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
            if (navItemIndex != 0) {
                navItemIndex = 0;
                Constants.CURRENT_TAG = Constants.TAG_DASH_BOARD;
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
        fragmentTransaction.addToBackStack("MyProfile");
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
                navItemIndex=101;
                setToolbarTitle();
                callMessagesFragment();
                break;
            case R.id.action_notification:
                navItemIndex=100;
                setToolbarTitle();
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
                        TokenExpiredUtils.tokenExpired(MainActivity.this);
                    }else {
                        mCartNotiCount=0;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
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
                        TokenExpiredUtils.tokenExpired(MainActivity.this);
                        TOKEN_CHECK=false;
                    }if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(MainActivity.this);
                        TOKEN_CHECK=false;
                    }else {
                        mCartMessageCount=0;
                        TOKEN_CHECK=true;
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
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
