package com.wsoteam.mydietcoach.calculators;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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


public class ActivityCalculatorBrok extends AppCompatActivity {
    private EditText edtBrokGrowth, edtBrokGirth, edtBrokAge;
    private RadioButton rbFemale, rbMale;
    private Button btnCalculate;
    int growth, girth, age, femaleDownFlag = 14, femaleUpFlag = 18, maleDownFlag = 17, maleUpFlag = 20, minNumber = 0;
    double idealWeight;
    boolean ast = false, normo = false, hyper = false;

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
        setContentView(R.layout.activity_calculator_brok);
        Ampl.Companion.openCalcualtor("brok");
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, "7fd0642d87baf8b8e03f806d1605348bb83e4148cf2a9aa6",
                Appodeal.INTERSTITIAL|Appodeal.BANNER, true);
        Appodeal.show(this, Appodeal.BANNER_VIEW);

        edtBrokGrowth = findViewById(R.id.edtBrokGrowth);
        edtBrokGirth = findViewById(R.id.edtBrokGirth);
        edtBrokAge = findViewById(R.id.edtBrokAge);
        rbFemale = findViewById(R.id.rdBrokFemale);
        rbMale = findViewById(R.id.rdBrokMale);
        btnCalculate = findViewById(R.id.btnBrokCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parseFieldsInToInteger()) {
                    if (checkErrorFromFields()) {
                        calculate();
                        createAlertDialog(idealWeight);
                    }
                }
            }
        });
    }

    private void calculate() {
        Ampl.Companion.useCalcualtor("brok");
        if (age <= 40) {
            if (growth <= 165) {
                idealWeight = ((growth - 100) * 0.9 - ((growth - 100) * 0.1)) * checkTypeOfBody();
            }
            if (growth >= 166 && growth <= 175) {
                idealWeight = ((growth - 105) * 0.9 - ((growth - 105) * 0.1)) * checkTypeOfBody();
            }
            if (growth >= 176) {
                idealWeight = ((growth - 110) * 0.9 - ((growth - 110) * 0.1)) * checkTypeOfBody();
            }
        } else {
            if (growth <= 165) {
                idealWeight = ((growth - 100) * 0.9 + ((growth - 100) * 0.07)) * checkTypeOfBody();
            }
            if (growth >= 166 && growth <= 175) {
                idealWeight = ((growth - 105) * 0.9 + ((growth - 105) * 0.07)) * checkTypeOfBody();
            }
            if (growth >= 176) {
                idealWeight = ((growth - 110) * 0.9 + ((growth - 110) * 0.07)) * checkTypeOfBody();
            }
        }
    }

    private double checkTypeOfBody() {
        if ((rbFemale.isChecked() && girth <= femaleDownFlag) ||
                (rbMale.isChecked() && girth <= maleDownFlag)) {
            return 0.9;
        } else {
            if ((rbFemale.isChecked() && girth >= femaleUpFlag) ||
                    (rbMale.isChecked() && girth >= maleUpFlag)) {
                return 1.1;
            } else {
                if ((rbFemale.isChecked() && girth > femaleDownFlag && girth < femaleUpFlag) ||
                        (rbMale.isChecked() && girth > maleDownFlag && girth < maleUpFlag)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    private boolean parseFieldsInToInteger() {
        try {
            growth = Integer.parseInt(edtBrokGrowth.getText().toString());
            girth = Integer.parseInt(edtBrokGirth.getText().toString());
            age = Integer.parseInt(edtBrokAge.getText().toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkErrorFromFields() {
        if (growth <= minNumber || girth <= minNumber || !(rbMale.isChecked() || rbFemale.isChecked())) {
            Toast.makeText(this, R.string.check_your_data, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void createAlertDialog(double calculate) {
        TextView tvAlertDialogWeight;
        FloatingActionButton fab;

        ast = false;
        normo = false;
        hyper = false;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_weight, null);
        tvAlertDialogWeight = view.findViewById(R.id.tvAlertDialogWeight);
        fab = view.findViewById(R.id.btnAlertDialogOk);
        tvAlertDialogWeight.setText(String.valueOf((int) calculate) + " кг");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }
}