package com.calorie.dieta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.calorie.dieta.common.DBHolder;
import com.calorie.dieta.common.GlobalHolder;
import com.calorie.dieta.common.db.entities.HistoryDiet;
import com.calorie.dieta.common.db.utils.Checker;
import com.calorie.dieta.model.Global;
import com.calorie.dieta.presentation.calculators.FragmentCalculators;
import com.calorie.dieta.presentation.diets.FragmentTypes;
import com.calorie.dieta.presentation.history.HistoryListDietsActivity;
import com.calorie.dieta.presentation.profile.ProfileFragment;
import com.calorie.dieta.presentation.tracker.FragmentTracker;
import com.calorie.dieta.presentation.water.FragmentWaterTracker;
import com.calorie.dieta.utils.GradeAlert;
import com.calorie.dieta.utils.ThankToast;
import com.calorie.dieta.utils.ad.AdWorker;
import com.calorie.dieta.utils.analytics.Ampl;
import com.calorie.dieta.utils.analytics.FBAnalytic;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    private final int COUNT_RUN_BETWEEN_SHOW_GRADE = 3;

    private boolean isHasEatTracker = true;

    public static String DIET_HISTORY_KEY = "DIET_HISTORY_KEY";

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
                    Ampl.Companion.openWater();
                    return true;
                }
                /*case R.id.bnv_ads: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(4)).commit();
                    return true;
                }*/

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
        AdWorker.INSTANCE.showInter(this);
        if (lastSectionNumber != MAIN && lastSectionNumber != EAT_TRACKER) {
            switch (lastSectionNumber) {
                case SETTINGS:
                    if (((ProfileFragment) sections.get(SETTINGS)).isCanClose())
                        openInitialFragment();
                    break;
                case WATER_TRACKER:
                    if (((FragmentWaterTracker) sections.get(WATER_TRACKER)).isCanClose()) {
                        openInitialFragment();
                    }
                    break;
                default:
                    openInitialFragment();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void openInitialFragment() {
        if (isHasEatTracker) {
            openSection(EAT_TRACKER);
            navigationView.setSelectedItemId(R.id.bnv_tracker);
        } else {
            openSection(MAIN);
            navigationView.setSelectedItemId(R.id.bnv_main);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDB(savedInstanceState);
        checkIntent(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        Ampl.Companion.showMainScreen();

        if (!hasConnection(this)) {
            Toast.makeText(this, R.string.check_your_connect, Toast.LENGTH_SHORT).show();
        }

        COUNT_OF_RUN = getPreferences(MODE_PRIVATE).getInt(TAG_OF_COUNT_RUN, 0);
        navigationView = findViewById(R.id.bnv_main);
        if (DBHolder.INSTANCE.getIfExist().getName().equals(DBHolder.INSTANCE.getNO_DIET_YET())) {
            navigationView.getMenu().removeItem(R.id.bnv_tracker);
            isHasEatTracker = false;
        }
        navigationView.setOnNavigationItemSelectedListener(bnvListener);
        setDietDataTC(GlobalHolder.INSTANCE.getGlobal());
        additionOneToSharedPreference();
        checkFirstRun();

        /*if (PreferenceProvider.INSTANCE.isHasPremium()) {
            navigationView.getMenu().removeItem(R.id.bnv_ads);
        }*/
    }


    private void checkIntent(Bundle savedInstanceState) {
        if (getIntent().getStringExtra(Config.PUSH_TAG) != null
                && getIntent().getStringExtra(Config.PUSH_TAG).equals(Config.OPEN_FROM_PUSH)) {
            AdWorker.INSTANCE.getShow(this);
        } else if (savedInstanceState == null && getIntent().getSerializableExtra(DIET_HISTORY_KEY) != null) {
            HistoryDiet history = (HistoryDiet) getIntent().getSerializableExtra(DIET_HISTORY_KEY);
            startActivity(HistoryListDietsActivity.Companion.getIntent(history, this));
        }
    }

    private void checkDB(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Checker.INSTANCE.checkDB();
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
        if (fragmentManager.getFragments().size() > 0) {
            clearFM();
        }

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

        /*if (isNeedOpenLastPage){
            switch (PreferenceProvider.INSTANCE.getLastOpenPage()){
                case MAIN : navigationView.setSelectedItemId(R.id.bnv_main);
                case CALCULATORS : navigationView.setSelectedItemId(R.id.bnv_calclators);
                case SETTINGS : navigationView.setSelectedItemId(R.id.bnv_settings);
                case WATER_TRACKER : navigationView.setSelectedItemId(R.id.bnv_water);
                case EAT_TRACKER : navigationView.setSelectedItemId(R.id.bnv_tracker);
            }
            openSection(PreferenceProvider.INSTANCE.getLastOpenPage());
        }*/
    }

    private void clearFM() {
        FragmentTransaction removeTransaction = fragmentManager.beginTransaction();
        for (Fragment frag : fragmentManager.getFragments()) {
            removeTransaction.remove(frag);
        }
        removeTransaction.commit();
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
        FBAnalytic.INSTANCE.goToGrade();
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
        if (countOfRun.getInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN) == COUNT_RUN_BETWEEN_SHOW_GRADE) {
            new GradeAlert().show(getSupportFragmentManager(), "GradeAlert");
        }
    }

    public void sayThank() {
        ThankToast.INSTANCE.showThankToast(this);
    }
}
