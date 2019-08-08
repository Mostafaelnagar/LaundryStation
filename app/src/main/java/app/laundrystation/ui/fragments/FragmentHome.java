package app.laundrystation.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentHomeBinding;
import app.laundrystation.viewModels.Laundries_ViewModels;
import lib.kingja.switchbutton.SwitchMultiButton;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerViewLaundries;
    FragmentHomeBinding homeBinding;
    Laundries_ViewModels laundriesViewModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        laundriesViewModels = new Laundries_ViewModels(getActivity());
        homeBinding.setLaundryViewModel(laundriesViewModels);
        laundriesViewModels.getCities();
        initViews();
        setSwitchListener();
        return homeBinding.getRoot();
    }


    private void initViews() {

        recyclerViewLaundries = homeBinding.recLaundries;
        recyclerViewLaundries.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLaundries.setHasFixedSize(true);
        getLaundryData(null, null, null, 10);
    }

    private void setSwitchListener() {

        homeBinding.SwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if (position == 2) {
                    getLaundryData(null, null, null, 10);
                } else if (position == 1) {
                    double lat = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getUserData().getAddresses().get(0).getLat());
                    double lng = Double.parseDouble(SharedPrefManager.getInstance(getActivity()).getUserData().getAddresses().get(0).getLng());
                    getLaundryData(null, lat, lng, 5);
                } else {
                    getLaundryData(1, null, null, 10);
                }
            }
        });
    }

    private void getLaundryData(Integer offer, Double lat, Double lng, final int page) {
        laundriesViewModels.sendLaundriesRequest(offer, lat, lng, page);
        laundriesViewModels.getLaundries();
        SharedPrefManager.getInstance(getActivity()).saveTotal((float) 0.0);
    }
}
