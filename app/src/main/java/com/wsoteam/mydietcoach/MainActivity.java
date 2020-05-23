package com.wsoteam.mydietcoach;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.wsoteam.mydietcoach.POJOS.Global;
import com.wsoteam.mydietcoach.ad.AdWorker;
import com.wsoteam.mydietcoach.ad.NativeSpeaker;
import com.wsoteam.mydietcoach.analytics.Ampl;
import com.wsoteam.mydietcoach.calculators.FragmentCalculators;
import com.wsoteam.mydietcoach.common.DBHolder;
import com.wsoteam.mydietcoach.common.FBWork;
import com.wsoteam.mydietcoach.common.GlobalHolder;
import com.wsoteam.mydietcoach.common.db.entities.DietPlanEntity;
import com.wsoteam.mydietcoach.diets.FragmentSections;
import com.wsoteam.mydietcoach.settings.FragmentSettings;
import com.wsoteam.mydietcoach.tracker.FragmentTracker;
import com.wsoteam.mydietcoach.utils.FragmentLoad;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences countOfRun;
    private int COUNT_OF_RUN = 0;
    private final String TAG_OF_COUNT_RUN = "TAG_OF_COUNT_RUN";
    private FragmentManager fragmentManager;
    private List<Fragment> sections = new ArrayList<>();
    private BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener bnvListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.bnv_main: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(0)).commit();
                    getWindow().setStatusBarColor(getResources().getColor(R.color.dark_status_bar));
                    Ampl.Companion.openDiets();
                    return true;
                }
                case R.id.bnv_calclators: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(1)).commit();
                    getWindow().setStatusBarColor(getResources().getColor(R.color.trans_status_bar));
                    Ampl.Companion.openCalculators();
                    return true;
                }
                /*case R.id.bnv_premium : {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(2)).commit();
                    return true;
                }*/
                case R.id.bnv_settings: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(2)).commit();
                    getWindow().setStatusBarColor(getResources().getColor(R.color.trans_status_bar));
                    Ampl.Companion.openSettings();
                    return true;
                }

                case R.id.bnv_tracker: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(3)).commit();
                    getWindow().setStatusBarColor(getResources().getColor(R.color.trans_status_bar));
                    Ampl.Companion.openSettings();
                    return true;
                }

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        AdWorker.INSTANCE.showInter();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDB(savedInstanceState);
        if (getIntent().getExtras() != null
                && getIntent().getExtras().getString(Config.PUSH_TAG) != null
                && getIntent().getExtras().getString(Config.PUSH_TAG).equals(Config.OPEN_FROM_PUSH)){
            Ampl.Companion.openFromPush();
        }
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
        if (DBHolder.INSTANCE.getIfExist().getName().equals(DBHolder.INSTANCE.getNO_DIET_YET())){
            navigationView.getMenu().removeItem(R.id.bnv_tracker);
        }
        navigationView.setOnNavigationItemSelectedListener(bnvListener);
        setDietDataTC(GlobalHolder.INSTANCE.getGlobal());
        additionOneToSharedPreference();
        checkFirstRun();
    }

    private void checkDB(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            if (App.getInstance().getDB().dietDAO().getAll() == null || App.getInstance().getDB().dietDAO().getAll().size() == 0){
                    DBHolder.INSTANCE.setEmpty();
            }
        }
    }

    private void setDietDataTC(Global t) {
        try {
            setDietData(t);
        }catch (Exception ex){
            finishAffinity();
        }
    }

    private void setDietData(Global global) {
        FragmentSections fragmentSections = FragmentSections.newInstance(global);
        if (DBHolder.INSTANCE.getIfExist().getName().equals(DBHolder.INSTANCE.getNO_DIET_YET())) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentSections).commit();
            getWindow().setStatusBarColor(getResources().getColor(R.color.dark_status_bar));
        }else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new FragmentTracker()).commit();
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans_status_bar));
        }
        sections.add(fragmentSections);
        sections.add(new FragmentCalculators());
        //sections.add(new FragmentPremium());
        sections.add(new FragmentSettings());
        sections.add(new FragmentTracker());
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

    private void checkFirstRun() {
        if (countOfRun.getInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN) == 4) {
            AlertDialog.Builder alertDialogGrade = new AlertDialog.Builder(this);
            alertDialogGrade.setTitle(R.string.titleAlertDialog);
            alertDialogGrade.setMessage(R.string.bodyAlertDialog);
            alertDialogGrade.setIcon(R.drawable.evaluate_app);
            alertDialogGrade.setNeutralButton(R.string.laterButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    countOfRun = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = countOfRun.edit();
                    editor.putInt(TAG_OF_COUNT_RUN, 0);
                    editor.commit();
                }
            });
            alertDialogGrade.setNegativeButton(R.string.neverButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //nothing to do
                }
            });
            alertDialogGrade.setPositiveButton(R.string.evaluateButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + MainActivity.this.getPackageName()));
                    startActivity(intent);
                }
            });
            alertDialogGrade.show();

        }
    }

}
