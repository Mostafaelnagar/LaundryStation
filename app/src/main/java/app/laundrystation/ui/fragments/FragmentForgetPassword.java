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
import app.laundrystation.databinding.FragmentForgetPasswordBinding;
 import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.viewModels.ValidationViewModels;

public class FragmentForgetPassword extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentForgetPasswordBinding resetPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);

        ValidationViewModels validationViewModels = new ValidationViewModels(getContext());
        resetPasswordBinding.setValidationViewModel(validationViewModels);
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<ReqDetailsModel>() {
            @Override
            public void onChanged(ReqDetailsModel reqDetailsModel) {
                if (reqDetailsModel.getMsg().equals(common.chanePasswordMsg)) {
                    SharedPrefManager.getInstance(getContext()).userLogin(reqDetailsModel.getUserData());
                    common.direct_To_Home(getContext());
                } else {
                    Toast.makeText(getContext(), reqDetailsModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return resetPasswordBinding.getRoot();
    }
}
