package com.jundev.weightloss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jundev.weightloss.model.Global;
import com.jundev.weightloss.utils.ad.AdWorker;
import com.jundev.weightloss.utils.analytics.Ampl;
import com.jundev.weightloss.presentation.calculators.FragmentCalculators;
import com.jundev.weightloss.common.DBHolder;
import com.jundev.weightloss.common.FBWork;
import com.jundev.weightloss.common.GlobalHolder;
import com.jundev.weightloss.presentation.diets.FragmentTypes;
import com.jundev.weightloss.presentation.profile.ProfileFragment;
import com.jundev.weightloss.presentation.tracker.FragmentTracker;
import com.jundev.weightloss.utils.GradeAlert;
import com.jundev.weightloss.utils.ThankToast;
import com.jundev.weightloss.presentation.water.FragmentWaterTracker;
import com.jundev.weightloss.utils.res.TextCutActvity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences countOfRun;
    private int COUNT_OF_RUN = 0;
    private final String TAG_OF_COUNT_RUN = "TAG_OF_COUNT_RUN";
    private FragmentManager fragmentManager;
    private List<Fragment> sections = new ArrayList<>();
    private BottomNavigationView navigationView;

    private boolean isNeedShowThank = false;

    private int lastSectionNumber = 0;

    private final int MAIN = 0;
    private final int CALCULATORS = 1;
    private final int SETTINGS = 2;
    private final int EAT_TRACKER = 3;
    private final int WATER_TRACKER = 4;

    private BottomNavigationView.OnNavigationItemSelectedListener bnvListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bnv_main: {
                    openSection(MAIN);
                    Ampl.Companion.openDiets();
                    return true;
                }
                case R.id.bnv_calclators: {
                    openSection(CALCULATORS);
                    Ampl.Companion.openCalculators();
                    return true;
                }
                /*case R.id.bnv_premium : {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(2)).commit();
                    return true;
                }*/
                case R.id.bnv_settings: {
                    openSection(SETTINGS);
                    Ampl.Companion.openSettings();
                    return true;
                }
                case R.id.bnv_tracker: {
                    openSection(EAT_TRACKER);
                    Ampl.Companion.openTracker();
                    return true;
                }
                case R.id.bnv_water: {
                    openSection(WATER_TRACKER);
                    Ampl.Companion.openCalculators();
                    return true;
                }
            }
            return false;
        }
    };

    private void openSection(int numberSection) {
        fragmentManager.beginTransaction().hide(sections.get(lastSectionNumber)).commit();
        fragmentManager.beginTransaction().show(sections.get(numberSection)).commit();
        lastSectionNumber = numberSection;
    }

    @Override
    public void onBackPressed() {
        AdWorker.INSTANCE.showInter();
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof ProfileFragment) {
            if (((ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)).bsBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                super.onBackPressed();
            }else {
                ((ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)).bsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDB(savedInstanceState);
        checkIntent();
        fragmentManager = getSupportFragmentManager();
        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("LOL", "onSuccess");
            }
        });
        FBWork.Companion.getFCMToken();
        Ampl.Companion.run();

        if (!hasConnection(this)) {
            Toast.makeText(this, R.string.check_your_connect, Toast.LENGTH_SHORT).show();
        }

        COUNT_OF_RUN = getPreferences(MODE_PRIVATE).getInt(TAG_OF_COUNT_RUN, 0);
        navigationView = findViewById(R.id.bnv_main);
        if (DBHolder.INSTANCE.getIfExist().getName().equals(DBHolder.INSTANCE.getNO_DIET_YET())) {
            navigationView.getMenu().removeItem(R.id.bnv_tracker);
        }
        navigationView.setOnNavigationItemSelectedListener(bnvListener);
        setDietDataTC(GlobalHolder.INSTANCE.getGlobal());
        additionOneToSharedPreference();
        checkFirstRun();

        //startActivity(new Intent(this, TextCutActvity.class));
    }

    private void checkIntent() {
        if (getIntent().getStringExtra(Config.PUSH_TAG) != null
                && getIntent().getStringExtra(Config.PUSH_TAG).equals(Config.OPEN_FROM_PUSH)){
            AdWorker.INSTANCE.getShow();
        }
    }

    private void checkDB(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (App.getInstance().getDB().dietDAO().getAll() == null || App.getInstance().getDB().dietDAO().getAll().size() == 0) {
                DBHolder.INSTANCE.setEmpty();
            }
        }
    }

    private void setDietDataTC(Global t) {
        try {
            setDietData();
        } catch (Exception ex) {
            finishAffinity();
        }
    }

    private void setDietData() {
        sections.add(new FragmentTypes());
        sections.add(new FragmentCalculators());
        sections.add(new ProfileFragment());
        sections.add(new FragmentTracker());
        sections.add(new FragmentWaterTracker());

        for (int i = 0; i < sections.size(); i++) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, sections.get(i)).hide(sections.get(i)).commit();
        }

        if (DBHolder.INSTANCE.getIfExist().getName().equals(DBHolder.INSTANCE.getNO_DIET_YET())) {
            fragmentManager.beginTransaction().show(sections.get(MAIN)).commit();
            getWindow().setStatusBarColor(getResources().getColor(R.color.dark_status_bar));
            lastSectionNumber = MAIN;
        } else {
            fragmentManager.beginTransaction().show(sections.get(EAT_TRACKER)).commit();
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans_status_bar));
            lastSectionNumber = EAT_TRACKER;
        }

    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void additionOneToSharedPreference() {
        countOfRun = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = countOfRun.edit();
        editor.putInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN + 1);
        editor.commit();
    }

    public void goToMarket() {
        isNeedShowThank = true;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + MainActivity.this.getPackageName()));
        startActivity(intent);
    }

    public void rateLater() {
        countOfRun = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = countOfRun.edit();
        editor.putInt(TAG_OF_COUNT_RUN, 0);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedShowThank) {
            ThankToast.INSTANCE.showThankToast(this);
            isNeedShowThank = false;
        }
    }

    private void checkFirstRun() {
        if (countOfRun.getInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN) == 4) {
            new GradeAlert().show(getSupportFragmentManager(), "GradeAlert");
        }
    }

    public void sayThank() {
        ThankToast.INSTANCE.showThankToast(this);
    }
}
