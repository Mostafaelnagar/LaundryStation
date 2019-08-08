package app.laundrystation.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.MyContextWrapper;
import app.laundrystation.common.RealTimeReceiver;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.ui.fragments.ChatFragment;
import app.laundrystation.ui.fragments.FragmentHome;
import app.laundrystation.ui.fragments.FragmentMenu;
import app.laundrystation.ui.fragments.FragmentNotifications;
import app.laundrystation.ui.fragments.LaundrySectionsFragment;
import app.laundrystation.ui.fragments.MyOrdersFragment;
import app.laundrystation.ui.fragments.OrderDetailsFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements RealTimeReceiver.MessageReceiverListener {
    FragmentHome fragment_home;
    FragmentNotifications fragment_notifications;
    FragmentMenu fragment_menu;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, SharedPrefManager.getInstance(newBase).getCurrentLanguage(newBase)));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/font1.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());
        setContentView(R.layout.activity_home);

        fragment_home = new FragmentHome();
        fragment_notifications = new FragmentNotifications();
        fragment_menu = new FragmentMenu();

        if (getIntent() != null) {
            String type = getIntent().getStringExtra("type");
            if (type.equals("new_message")) {
                common.replaceFragment(this, R.id.home_Main_Container, new MyOrdersFragment());
            } else if (type.equals("new_order_status")) {
                common.replaceFragment(this, R.id.home_Main_Container, new MyOrdersFragment());
            } else
                setFragment(new FragmentHome());
        }

        BottomNav bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_home).addColorAtive(R.color.white).addColorInative(R.color.black));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_notification).addColorAtive(R.color.white).addColorInative(R.color.black));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.ic_menu).addColorAtive(R.color.white).addColorInative(R.color.black));
        bottomNav.build();
        bottomNav.setTabSelectedListener(new BottomNav.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                switch (i) {
                    case 0:
                        setFragment(fragment_home);
                        break;
                    case 1:
                        setFragment(fragment_notifications);
                        break;
                    case 2:
                        setFragment(fragment_menu);
                        break;

                }
            }

            @Override
            public void onTabLongSelected(int i) {

            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_Home_Container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Fragment laundryFrag = getSupportFragmentManager().findFragmentById(R.id.frame_Laundry_Details);
            Fragment frag_Other = getSupportFragmentManager().findFragmentById(R.id.home_Main_Container);

            if (laundryFrag instanceof LaundrySectionsFragment) {
                if (SharedPrefManager.getInstance(HomeActivity.this).getCart() != null)
                    common.waring_Message(HomeActivity.this);
            } else if (frag_Other instanceof ChatFragment) {
                common.removeFragment(new ChatFragment(), HomeActivity.this);
            } else if (frag_Other instanceof MyOrdersFragment) {
                common.removeFragment(new MyOrdersFragment(), HomeActivity.this);
            } else if (frag_Other instanceof OrderDetailsFragment) {
                common.removeFragment(new OrderDetailsFragment(), HomeActivity.this);
            } else {
                super.onBackPressed();
            }
        } else {
            Fragment homeMain = getSupportFragmentManager().findFragmentById(R.id.frame_Home_Container);
            if (homeMain instanceof FragmentMenu || homeMain instanceof FragmentHome || homeMain instanceof FragmentNotifications)
                showExit();

        }
    }


    private void showExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setMessage(getBaseContext().getString(R.string.exitText))
                .setCancelable(false)
                .setPositiveButton(getBaseContext().getString(R.string.logOutAccept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton(getBaseContext().getString(R.string.logOutReject), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMessageChanged(String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setMessageReceiverListener(this);
    }
}
