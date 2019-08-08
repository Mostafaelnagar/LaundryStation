package app.laundrystation.ui.fragments;


import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentLaundrySectionsBinding;
import app.laundrystation.models.laundries.SectionModel;
import app.laundrystation.viewModels.LaundrySectionsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaundrySectionsFragment extends Fragment {
    FragmentLaundrySectionsBinding sectionsBinding;
    LaundrySectionsViewModel sectionsViewModel;
    private List<SectionModel> sectionModelList;
    private RecyclerView recyclerViewLaundriesSections;

    public LaundrySectionsFragment() {
        // Required empty public constructor
        common c = new common();
        sectionModelList = c.getType(common.LAUNDRY_DETAILS.getSectionId());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sectionsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_laundry_sections, container, false);
        sectionsViewModel = new LaundrySectionsViewModel(sectionModelList);
        sectionsBinding.setLaundrySectionsViewModel(sectionsViewModel);
        initViews();

        return sectionsBinding.getRoot();
    }

    private void initViews() {
        recyclerViewLaundriesSections = sectionsBinding.recSections;
        recyclerViewLaundriesSections.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLaundriesSections.setHasFixedSize(true);
    }
}
