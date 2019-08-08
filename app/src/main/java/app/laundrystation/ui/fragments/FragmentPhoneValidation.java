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
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentPhoneConfirmationBinding;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.viewModels.ValidationViewModels;

public class FragmentPhoneValidation extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPhoneConfirmationBinding confirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_confirmation, container, false);
        ValidationViewModels validationViewModels = new ValidationViewModels(getContext());
        confirmationBinding.setValidationViewModel(validationViewModels);
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<ReqDetailsModel>() {
            @Override
            public void onChanged(ReqDetailsModel reqDetailsModel) {
                if (reqDetailsModel.getStatus() == 200) {
                    common.confirmation_Page(getContext(), R.id.validation_FrameLayout, new FragmentCodeValidation());
                } else {
                    Toast.makeText(getContext(), reqDetailsModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return confirmationBinding.getRoot();
    }


}
