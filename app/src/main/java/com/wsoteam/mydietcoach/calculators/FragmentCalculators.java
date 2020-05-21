package com.wsoteam.mydietcoach.calculators;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.wsoteam.mydietcoach.R;
import com.wsoteam.mydietcoach.ad.AdWorker;
import com.wsoteam.mydietcoach.ad.NativeSpeaker;
import com.wsoteam.mydietcoach.analytics.Ampl;
import com.wsoteam.mydietcoach.calculators.controllers.CalculatingAdapter;
import com.wsoteam.mydietcoach.calculators.controllers.ClickItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class FragmentCalculators extends Fragment {

    private RecyclerView rvListOfCalculating;
    private String[] listOfTitles;
    private String[] listOfDescriptions;
    private final int NUMBER_OF_BROK = 0, NUMBER_OF_LORENC = 1, NUMBER_OF_IMT = 2, NUMBER_OF_SPK = 3;
    private Integer[] gradients = new Integer[]{R.drawable.gradient_brok,
            R.drawable.gradient_lorenc, R.drawable.gradient_imt, R.drawable.gradient_spk};
    private CalculatingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_of_calculating, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillDataForList();
        rvListOfCalculating = view.findViewById(R.id.rvListOfCalculating);
        rvListOfCalculating.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new CalculatingAdapter(listOfTitles, listOfDescriptions, gradients, new ArrayList<>(),
                position -> startCalculator(position));
        rvListOfCalculating.setAdapter(adapter);
        AdWorker.INSTANCE.observeOnNativeList(new NativeSpeaker() {
            @Override
            public void loadFin(@NotNull ArrayList<UnifiedNativeAd> nativeList) {
                adapter.insertAds(nativeList);
            }
        });
        Log.e("LOL", "calc");
    }

    public void startCalculator(Integer position){
        switch (position) {
            case NUMBER_OF_BROK:
                Intent intentBrok = new Intent(getActivity(), ActivityCalculatorBrok.class);
                startActivity(intentBrok);
                break;
            case NUMBER_OF_LORENC:
                Intent intentLorenc = new Intent(getActivity(), ActivityCalculatorLorenc.class);
                startActivity(intentLorenc);
                break;
            case NUMBER_OF_IMT:
                Intent intentIMT = new Intent(getActivity(), ActivityCalculatorIMT.class);
                startActivity(intentIMT);
                break;
            case NUMBER_OF_SPK:
                Intent intentSPK = new Intent(getActivity(), ActivityCalculatorSPK.class);
                startActivity(intentSPK);
                break;
        }
    }

    private void fillDataForList() {
        listOfTitles = getResources().getStringArray(R.array.titles_of_calculating_list);
        listOfDescriptions = getResources().getStringArray(R.array.descriptions_of_calculating_list);
    }

}
