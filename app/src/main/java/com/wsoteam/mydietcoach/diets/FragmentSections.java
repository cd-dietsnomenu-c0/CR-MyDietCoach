package com.wsoteam.mydietcoach.diets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wsoteam.mydietcoach.Config;
import com.wsoteam.mydietcoach.POJOS.Global;
import com.wsoteam.mydietcoach.POJOS.Section;
import com.wsoteam.mydietcoach.R;
import com.wsoteam.mydietcoach.diets.controllers.SectionAdapter;
import com.wsoteam.mydietcoach.diets.items.ActivityListItems;
import com.wsoteam.mydietcoach.diets.items.article.ActivityArticle;

import java.util.ArrayList;

public class FragmentSections extends Fragment {
    private RecyclerView rvSections;
    private Global global;
    private ArrayList<Section> sectionArrayList;


    public static FragmentSections newInstance(Global global) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.ID_SECTIONS_ARGS, global);
        FragmentSections fragmentSections = new FragmentSections();
        fragmentSections.setArguments(bundle);
        return fragmentSections;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_sections, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        global = (Global) getArguments().getSerializable(Config.ID_SECTIONS_ARGS);
        sectionArrayList = (ArrayList<Section>) global.getSectionsArray();

        rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSections.setAdapter(new SectionAdapter(sectionArrayList, getResources().getStringArray(R.array.images), new ItemClick(){
            @Override
            public void click(int position) {
                Intent intent = new Intent(getActivity(), ActivityListItems.class);
                if(sectionArrayList.get(position).getArrayOfSubSections().size() == 1){
                    intent = new Intent(getActivity(), ActivityArticle.class);
                    intent.putExtra(Config.ITEM_DATA, sectionArrayList.get(position).getArrayOfSubSections().get(0));
                }else {
                    intent.putExtra(Config.SECTION_DATA, sectionArrayList.get(position));
                }
                startActivity(intent);
            }
        }));
    }
}
