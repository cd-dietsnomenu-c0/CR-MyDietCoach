package com.meal.planner.presentation.calculators;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meal.planner.R;
import com.meal.planner.utils.ad.AdWorker;
import com.meal.planner.utils.ad.NativeSpeaker;
import com.meal.planner.presentation.calculators.controllers.CalculatingAdapter;
import com.google.android.gms.ads.nativead.NativeAd;

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
        setMargins();
        rvListOfCalculating.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new CalculatingAdapter(listOfTitles, listOfDescriptions, gradients, new ArrayList<>(),
                position -> startCalculator(position));
        rvListOfCalculating.setAdapter(adapter);
        AdWorker.INSTANCE.observeOnNativeList(new NativeSpeaker() {
            @Override
            public void loadFin(@NotNull ArrayList<NativeAd> nativeList) {
                adapter.insertAds(nativeList);
            }
        });
    }

    private void setMargins() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, height, 0, 0);
        rvListOfCalculating.setLayoutParams(params);
    }

    public void startCalculator(Integer position) {
        Intent intent = new Intent();
        switch (position) {
            case NUMBER_OF_BROK:
                intent = new Intent(getActivity(), ActivityCalculatorBrok.class);
                break;
            case NUMBER_OF_LORENC:
                intent = new Intent(getActivity(), ActivityCalculatorLorenc.class);
                break;
            case NUMBER_OF_IMT:
                intent = new Intent(getActivity(), ActivityCalculatorIMT.class);
                break;
            case NUMBER_OF_SPK:
                intent = new Intent(getActivity(), ActivityCalculatorSPK.class);
                break;
        }
        startActivity(intent);
    }

    private void fillDataForList() {
        listOfTitles = getResources().getStringArray(R.array.titles_of_calculating_list);
        listOfDescriptions = getResources().getStringArray(R.array.descriptions_of_calculating_list);
    }

}
