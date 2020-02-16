package com.wsoteam.mydietcoach;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.appodeal.ads.Appodeal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.wsoteam.mydietcoach.calculators.FragmentCalculators;
import com.wsoteam.mydietcoach.diets.FragmentSections;
import com.wsoteam.mydietcoach.POJOS.Global;
import com.wsoteam.mydietcoach.inapp.BillingManager;
import com.wsoteam.mydietcoach.premium.FragmentPremium;
import com.wsoteam.mydietcoach.settings.FragmentSettings;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences countOfRun;
    private int COUNT_OF_RUN = 0;
    private final String TAG_OF_COUNT_RUN = "TAG_OF_COUNT_RUN";
    private boolean isInter = true;
    private FragmentManager fragmentManager;
    private List<Fragment> sections = new ArrayList<>();
    private BottomNavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener bnvListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.bnv_main: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(0)).commit();
                    return true;
                }
                case R.id.bnv_calclators: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(1)).commit();
                    return true;
                }
                /*case R.id.bnv_premium : {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(2)).commit();
                    return true;
                }*/
                case R.id.bnv_settings: {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, sections.get(2)).commit();
                    return true;
                }

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        checkPermissionForShowInter();
        super.onBackPressed();
    }

    private void checkPermissionForShowInter() {
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
            Amplitude.getInstance().logEvent("show ad");
            Appodeal.show(this, Appodeal.INTERSTITIAL);
            Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                    Appodeal.INTERSTITIAL, isInter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AsyncLoadFoodList().execute();

        Amplitude.getInstance().logEvent("Run");
        Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                Appodeal.INTERSTITIAL, isInter);

        fragmentManager = getSupportFragmentManager();
        if (!hasConnection(this)) {
            Toast.makeText(this, R.string.check_your_connect, Toast.LENGTH_SHORT).show();
        }

        COUNT_OF_RUN = getPreferences(MODE_PRIVATE).getInt(TAG_OF_COUNT_RUN, 0);
        BillingManager.INSTANCE.startSubscription(this);
        navigationView = findViewById(R.id.bnv_main);
        navigationView.setOnNavigationItemSelectedListener(bnvListener);
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

    private class AsyncLoadFoodList extends AsyncTask<Void, Void, Global> {
        @Override
        protected void onPostExecute(Global global) {
            FragmentSections fragmentSections = FragmentSections.newInstance(global);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentSections).commit();
            additionOneToSharedPreference();
            checkFirstRun();
            sections.add(fragmentSections);
            sections.add(new FragmentCalculators());
            //sections.add(new FragmentPremium());
            sections.add(new FragmentSettings());
        }

        @Override
        protected Global doInBackground(Void... voids) {
            String json;
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<Global> jsonAdapter = moshi.adapter(Global.class);
            try {
                InputStream inputStream = getAssets().open("adb.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                Log.e("LOL", String.valueOf(size));
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, "UTF-8");

                Global global = jsonAdapter.fromJson(json);

                return global;
            } catch (Exception e) {

            }
            return null;
        }
    }

}
