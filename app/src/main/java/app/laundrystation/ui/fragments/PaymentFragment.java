package app.laundrystation.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.databinding.FragmentPaymentBinding;
import app.laundrystation.viewModels.PaymentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    FragmentPaymentBinding paymentBinding;
    PaymentViewModel paymentViewModel;
    float total;
    Bundle bundle;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        bundle = getArguments();
        if (bundle != null) {
        }
        paymentViewModel = new PaymentViewModel();
        paymentBinding.setPaymentViewModel(paymentViewModel);
        paymentViewModel.contextMutableLiveData.setValue(getActivity());
        return paymentBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefManager.getInstance(MyApplication.getInstance()).saveTotal(bundle.getFloat("Total"));
    }


}
