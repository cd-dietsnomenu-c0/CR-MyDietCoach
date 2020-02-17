package com.wsoteam.mydietcoach.calculators;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amplitude.api.Amplitude;
import com.appodeal.ads.Appodeal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wsoteam.mydietcoach.R;
import com.wsoteam.mydietcoach.analytics.Ampl;

public class ActivityCalculatorLorenc extends AppCompatActivity {
    EditText edtLorencHeight;
    Button btnCalculate;

    @Override
    public void onBackPressed() {
        checkPermissionForShowInter();
        super.onBackPressed();
    }

    private void checkPermissionForShowInter() {
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
            Ampl.Companion.showAd();
            Amplitude.getInstance().logEvent("show ad");
            Appodeal.show(this, Appodeal.INTERSTITIAL);
            Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                    Appodeal.INTERSTITIAL, true);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_lorenc);
        Ampl.Companion.openCalcualtor("lorenc");
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                Appodeal.INTERSTITIAL|Appodeal.BANNER, true);
        Appodeal.show(this, Appodeal.BANNER_VIEW);

        edtLorencHeight = findViewById(R.id.edtLorencHeight);
        btnCalculate = findViewById(R.id.btnLorencCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createAlertDialog(calculate());
                } catch (Exception e) {
                    Toast.makeText(ActivityCalculatorLorenc.this, R.string.check_your_data, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createAlertDialog(double calculate) {
        Ampl.Companion.openCalcualtor("lorenc");
        TextView tvAlertDialogWeight;
        FloatingActionButton btnOk;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_weight, null);
        tvAlertDialogWeight = view.findViewById(R.id.tvAlertDialogWeight);
        btnOk = view.findViewById(R.id.btnAlertDialogOk);
        tvAlertDialogWeight.setText(String.valueOf(calculate) + " кг");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }

    private double calculate() {
        return Integer.parseInt(edtLorencHeight.getText().toString()) / 2 - 25;
    }
}
