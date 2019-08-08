package app.laundrystation.ui.fragments;


 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.laundrystation.R;
 import app.laundrystation.common.MyApplication;
 import app.laundrystation.common.SharedPrefManager;
 import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentServicesBinding;
import app.laundrystation.models.laundries.Service;
import app.laundrystation.viewModels.LaundryServicesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {
    List<Service> serviceList;
    private FragmentServicesBinding servicesBinding;
    private LaundryServicesViewModel servicesViewModel;
    private RecyclerView recyclerViewLaundriesServices;

    public ServicesFragment(int position) {
         serviceList = common.getServices(position);
    }

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        servicesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);
        servicesViewModel = new LaundryServicesViewModel(serviceList);
        servicesBinding.setLaundryServicesViewModel(servicesViewModel);
        servicesViewModel.notifyChange();
        initViews();
        servicesViewModel.totalMutableLiveData.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String total = servicesViewModel.laundryServicesAdapter.totalLiveData.getValue().toString();
                servicesViewModel.setOrderTotal(total);
            }
        });
        servicesViewModel.contextMutableLiveData.setValue(getActivity());
        return servicesBinding.getRoot();
    }

    private void initViews() {
        recyclerViewLaundriesServices = servicesBinding.recyclerViewServicesDetails;
        recyclerViewLaundriesServices.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLaundriesServices.setHasFixedSize(true);
    }
}
