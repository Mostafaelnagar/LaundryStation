package app.laundrystation.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import app.laundrystation.R;
import app.laundrystation.databinding.FragmentChooseAddressBinding;
import app.laundrystation.viewModels.ChooseAddressViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseAddressFragment extends Fragment {
    FragmentChooseAddressBinding addressBinding;
    ChooseAddressViewModel addressViewModel;
    private RecyclerView recyclerViewAddress;

    public ChooseAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addressBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_address, container, false);
        addressViewModel = new ChooseAddressViewModel();
        addressBinding.setChooseAddressViewModel(addressViewModel);
        initViews();

        addressViewModel.contextMutableLiveData.setValue(getActivity());
        return addressBinding.getRoot();

    }
    private void initViews() {
        recyclerViewAddress = addressBinding.recAddresses;
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAddress.setHasFixedSize(true);
    }
}
