package app.laundrystation.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import app.laundrystation.common.common;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.FragmentResetPassword;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends BaseObservable {
    public String profileOldPassword;
    public String validationNewPassword;
    public String validationConfirmNewPassword;
    private Context context;


    public ResetPasswordViewModel(Context context) {
        this.context = context;
    }

    public void back() {
        common.removeFragment(new FragmentResetPassword(), context);
    }

    public void userResetPassword() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().updateUserPassword(profileOldPassword, validationNewPassword, validationConfirmNewPassword).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                if (response.body().getMsg().equals(common.userUpdatePassword)) {
                    spotsDialog.dismiss();
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    common.removeFragment(new FragmentResetPassword(), context);
                } else {
                    spotsDialog.dismiss();
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("Repos", "" + t.getMessage());
            }
        });
    }

    @Bindable
    public String getProfileOldPassword() {
        return profileOldPassword;
    }

    public void setProfileOldPassword(String profileOldPassword) {
        this.profileOldPassword = profileOldPassword;
        notifyChange();
    }

    @Bindable

    public String getValidationNewPassword() {
        return validationNewPassword;
    }

    public void setValidationNewPassword(String validationNewPassword) {
        this.validationNewPassword = validationNewPassword;
        notifyChange();

    }

    @Bindable

    public String getValidationConfirmNewPassword() {
        return validationConfirmNewPassword;
    }

    public void setValidationConfirmNewPassword(String validationConfirmNewPassword) {
        this.validationConfirmNewPassword = validationConfirmNewPassword;
        notifyChange();

    }
}
