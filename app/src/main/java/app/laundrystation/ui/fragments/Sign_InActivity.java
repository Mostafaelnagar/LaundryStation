package app.laundrystation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentSignInBinding;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.viewModels.AuthViewModel;

public class Sign_InActivity extends Fragment {
    AuthViewModel authViewModel;
    FragmentSignInBinding fragmentSignInBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        authViewModel = new AuthViewModel(getContext());
        fragmentSignInBinding.setAuthViewModel(authViewModel);
        authViewModel.getAuthResponse().observe(this, new Observer<ReqDetailsModel>() {
            @Override
            public void onChanged(ReqDetailsModel request_details) {
                String status = request_details.getMsg();
                if (status.equalsIgnoreCase(common.confirmation_Login_msg)) {
                    common.code = "confirm";
                    common.validation_Page(getContext(), common.code);
                } else if (status.equalsIgnoreCase(common.Login__Succuss_msg)) {
                    SharedPrefManager.getInstance(getContext()).userLogin(request_details.getUserData());
                    common.direct_To_Home(getContext());
                    authViewModel.updateUserToken();
                } else {
                    Toast.makeText(getContext(), "" + request_details.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return fragmentSignInBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
