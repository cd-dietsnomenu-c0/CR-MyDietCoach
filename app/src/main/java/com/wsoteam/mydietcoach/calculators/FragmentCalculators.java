package com.wsoteam.mydietcoach.calculators;

import android.content.Intent;
import android.os.Bundle;
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

import com.wsoteam.mydietcoach.R;
import com.wsoteam.mydietcoach.analytics.Ampl;


public class FragmentCalculators extends Fragment {

    private RecyclerView rvListOfCalculating;
    private String[] listOfTitles;
    private String[] listOfDescriptions;
    private final int NUMBER_OF_BROK = 0, NUMBER_OF_LORENC = 1, NUMBER_OF_IMT = 2, NUMBER_OF_SPK = 3;
    Integer[] gradients = new Integer[]{R.drawable.gradient_brok, R.drawable.gradient_lorenc, R.drawable.gradient_imt, R.drawable.gradient_spk};

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
        rvListOfCalculating.setAdapter(new CalculatingAdapter(listOfTitles, listOfDescriptions, gradients));
    }

    private void fillDataForList() {
        listOfTitles = getResources().getStringArray(R.array.titles_of_calculating_list);
        listOfDescriptions = getResources().getStringArray(R.array.descriptions_of_calculating_list);
    }


    private class CaclulatingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        CardView cvParent;
        LinearLayout llParent;

        public CaclulatingViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_list_calculating, viewGroup, false));

            title = itemView.findViewById(R.id.tvTitleOfItemListCalculating);
            description = itemView.findViewById(R.id.tvDescriptionOfItemListCalculating);
            cvParent = itemView.findViewById(R.id.cvParent);
            llParent = itemView.findViewById(R.id.llParent);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (getAdapterPosition()) {
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

        public void bind(String title, String desription, Integer gradient) {
            this.title.setText(title);
            this.description.setText(desription);
            llParent.setBackgroundResource(gradient);
        }
    }

    private class CalculatingAdapter extends RecyclerView.Adapter<CaclulatingViewHolder> {
        String[] titles, desriptions;
        Integer[] gradients;

        public CalculatingAdapter(String[] titles, String[] desriptions, Integer[] gradients) {
            this.titles = titles;
            this.desriptions = desriptions;
            this.gradients = gradients;
        }

        @NonNull
        @Override
        public CaclulatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new CaclulatingViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CaclulatingViewHolder holder, int position) {
            holder.bind(titles[position], desriptions[position], gradients[position]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
