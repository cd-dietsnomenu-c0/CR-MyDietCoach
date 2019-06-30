package com.wsoteam.mydietcoach.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wsoteam.mydietcoach.ObjectHolder;
import com.wsoteam.mydietcoach.POJOS.POJOS.POJO;
import com.wsoteam.mydietcoach.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentListDiets extends Fragment {
    RecyclerView recyclerView;
    int[] drawables = new int[] {R.drawable.w0, R.drawable.w1, R.drawable.w2, R.drawable.w3, R.drawable.w4, R.drawable.w5, R.drawable.w6, R.drawable.w7, R.drawable.w8, R.drawable.w9, R.drawable.w10, R.drawable.w11, R.drawable.w12, R.drawable.w13, R.drawable.w14, R.drawable.w15, R.drawable.w16, R.drawable.w17, R.drawable.w18, R.drawable.w19, R.drawable.w20, R.drawable.w21, R.drawable.w22, R.drawable.w23, R.drawable.w24, R.drawable.w25, R.drawable.w26, R.drawable.w27, R.drawable.w28, R.drawable.w29, R.drawable.w30, R.drawable.w31, R.drawable.w32, R.drawable.w33, R.drawable.w34, R.drawable.w35, R.drawable.w36, R.drawable.w37, R.drawable.w38, R.drawable.w39, R.drawable.w40, R.drawable.w41, R.drawable.w42, R.drawable.w43, R.drawable.w44, R.drawable.w45, R.drawable.w46, R.drawable.w47, R.drawable.w48, R.drawable.w49, R.drawable.w50, R.drawable.w51, R.drawable.w52, R.drawable.w53, R.drawable.w54, R.drawable.w55, R.drawable.w56, R.drawable.w57, R.drawable.w58, R.drawable.w59, R.drawable.w60, R.drawable.w61, R.drawable.w62, R.drawable.w63, R.drawable.w64, R.drawable.w65, R.drawable.w66, R.drawable.w67, R.drawable.w68, R.drawable.w69, R.drawable.w70, R.drawable.w71, R.drawable.w72, R.drawable.w73, R.drawable.w74, R.drawable.w75, R.drawable.w76, R.drawable.w77, R.drawable.w78, R.drawable.w79, R.drawable.w80, R.drawable.w81, R.drawable.w82, R.drawable.w83, R.drawable.w84, R.drawable.w85, R.drawable.w86, R.drawable.w87, R.drawable.w88, R.drawable.w89, R.drawable.w90, R.drawable.w91, R.drawable.w92, R.drawable.w93, R.drawable.w94, R.drawable.w95, R.drawable.w96, R.drawable.w97, R.drawable.w98, R.drawable.w99, R.drawable.w100, R.drawable.w101, R.drawable.w102, R.drawable.w103};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_diets, container, false);
        recyclerView = view.findViewById(R.id.rvListDiets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ItemAdapter(ObjectHolder.getClobalObject().getListOfPOJO().getListOFPOJO()));
        return view;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBack) ImageView ivBack;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public ItemHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_list_diets, viewGroup, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(POJO pojo) {
            tvTitle.setText(pojo.getName());
            Glide.with(getActivity())
                    .load(drawables[getAdapterPosition()])
                    .into(ivBack);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{
        List<POJO> listOFPOJO;

        public ItemAdapter(List<POJO> listOFPOJO) {
            this.listOFPOJO = listOFPOJO;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
            itemHolder.bind(listOFPOJO.get(i));
        }

        @Override
        public int getItemCount() {
            return listOFPOJO.size();
        }
    }
}
