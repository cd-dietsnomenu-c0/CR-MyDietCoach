package com.wsoteam.mydietcoach.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.appodeal.ads.Appodeal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wsoteam.mydietcoach.Config;
import com.wsoteam.mydietcoach.Fragments.FragmentSections;
import com.wsoteam.mydietcoach.Fragments.FragmentSplash;
import com.wsoteam.mydietcoach.POJOS.Global;
import com.wsoteam.mydietcoach.R;


public class MainActivity extends AppCompatActivity {
    private final String NAME_DB = "adb";
    private Global global = new Global();
    private SharedPreferences countOfRun;
    private int COUNT_OF_RUN = 0, COUNT_OF_BACK_PRESSED = 0;
    private final String TAG_OF_COUNT_RUN = "TAG_OF_COUNT_RUN";
    private boolean isInter = true;

    @Override
    public void onBackPressed() {
        checkPermissionForShowInter();
        super.onBackPressed();
    }

    private void checkPermissionForShowInter() {
        if (COUNT_OF_BACK_PRESSED % 5 == 0) {

            if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                Amplitude.getInstance().logEvent("show ad");
                Appodeal.show(this, Appodeal.INTERSTITIAL);
            }
        }
        COUNT_OF_BACK_PRESSED += 1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Amplitude.getInstance().logEvent("Run");

        Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                Appodeal.INTERSTITIAL, isInter);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, new FragmentSplash()).commit();

        if (!hasConnection(this)) {
            Toast.makeText(this, R.string.check_your_connect, Toast.LENGTH_SHORT).show();
        }


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GLOBAL");
        databaseReference.push().setValue("sdf");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                global = dataSnapshot.getValue(Global.class);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, FragmentSections.newInstance(global)).commit();
                additionOneToSharedPreference();
                checkFirstRun();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        COUNT_OF_RUN = getPreferences(MODE_PRIVATE).getInt(TAG_OF_COUNT_RUN, 0);


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
