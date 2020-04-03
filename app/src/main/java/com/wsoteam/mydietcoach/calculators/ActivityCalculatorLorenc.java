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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wsoteam.mydietcoach.R;
import com.wsoteam.mydietcoach.ad.AdWorker;
import com.wsoteam.mydietcoach.analytics.Ampl;

public class ActivityCalculatorLorenc extends AppCompatActivity {
    EditText edtLorencHeight;
    Button btnCalculate;
    private AdView ban;

    @Override
    public void onBackPressed() {
        AdWorker.INSTANCE.showInter();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_lorenc);
        Ampl.Companion.openCalcualtor("lorenc");
        ban = findViewById(R.id.appodealBannerView);
        ban.loadAd(new AdRequest.Builder().build());
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
