package app.laundrystation.ui.fragments;
 import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentSignUpBinding;
import app.laundrystation.ui.SelectLocationActivity;
import app.laundrystation.viewModels.AuthViewModel;

public class Sign_UpActivity extends Fragment {
    AuthViewModel authViewModel;
    FragmentSignUpBinding signUpBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        authViewModel = new AuthViewModel(getContext());
        signUpBinding.setAuthViewModel(authViewModel);

        authViewModel.addressResult.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, common.REQUEST_CODE);
                authViewModel.notifyChange();
            }
        });
        return signUpBinding.getRoot();
    }

    String address = "";
    double lat, lng;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == common.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                lat = data.getDoubleExtra("lat", 0.0);
                lng = data.getDoubleExtra("lang", 0.0);
                Log.i("onSuccess", "onActivityResult: " + lat + "  " + lng);
                authViewModel.setUserAddress(lat, lng);
                authViewModel.setPickLocationText(getActivity().getResources().getString(R.string.pickLocationSuccess));
                authViewModel.notifyChange();

            }
        }
    }
}
