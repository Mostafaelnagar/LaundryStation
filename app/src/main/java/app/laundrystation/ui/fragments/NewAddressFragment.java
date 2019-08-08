package app.laundrystation.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentNewAddressBinding;
import app.laundrystation.ui.SelectLocationActivity;
import app.laundrystation.viewModels.NewAddressViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAddressFragment extends Fragment {
    FragmentNewAddressBinding newAddressBinding;
    NewAddressViewModel viewModel;

    public NewAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newAddressBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_address, container, false);
        viewModel = new NewAddressViewModel();
        newAddressBinding.setNewAddressViewModel(viewModel);
        viewModel.contextMutableLiveData.setValue(getActivity());
        viewModel.addressResult.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, common.REQUEST_CODE);
                viewModel.notifyChange();
            }
        });
        return newAddressBinding.getRoot();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == common.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                double lat = data.getDoubleExtra("lat", 0.0);
                double lng = data.getDoubleExtra("lang", 0.0);
                viewModel.setUserAddress(lat, lng);
                viewModel.setPickLocationText(getActivity().getResources().getString(R.string.pickLocationSuccess));
                viewModel.notifyChange();

            }
        }
    }
}
