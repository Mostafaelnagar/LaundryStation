package app.laundrystation.ui.fragments;

import android.os.Bundle;
import android.util.Log;
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
import app.laundrystation.databinding.FragmentCodeConfirmationBinding;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.viewModels.ValidationViewModels;

public class FragmentCodeValidation extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCodeConfirmationBinding confirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_code_confirmation, container, false);

        ValidationViewModels validationViewModels = new ValidationViewModels(getContext());
        confirmationBinding.setValidationViewModel(validationViewModels);
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<ReqDetailsModel>() {
            @Override
            public void onChanged(ReqDetailsModel reqDetailsModel) {
                if (reqDetailsModel.getStatus() == 200) {
                    if (common.code.equals("confirm")) {
                        SharedPrefManager.getInstance(getContext()).userLogin(reqDetailsModel.getUserData());
                        common.direct_To_Home(getContext());
                    } else {
                        common.confirmation_Page(getContext(), R.id.validation_FrameLayout, new FragmentForgetPassword());
                    }
                } else
                    Toast.makeText(getContext(), "" + reqDetailsModel.getMsg(), Toast.LENGTH_LONG).show();

            }
        });
        return confirmationBinding.getRoot();
    }

}
