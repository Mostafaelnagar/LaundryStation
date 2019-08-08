package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.services.ServiceGenerator;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidationViewModels extends BaseObservable {
    private Context context;
    private MutableLiveData<ReqDetailsModel> data = new MutableLiveData<>();
    public String userPhone;
    public String pinCodeConfirmation;
    public String validationNewPassword;
    public String validationConfirmNewPassword;

    public ValidationViewModels(Context context) {
        this.context = context;
    }

    //user send phone for confirmation
    public void userConfirmPhone() {
        if (!TextUtils.isEmpty(userPhone)) {
            send_Code();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();
        }
    }

    //confirm code sent to the user
    public void userConfirmCode() {
        if (!TextUtils.isEmpty(pinCodeConfirmation)) {
            validate_Code();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }
    }

    public void userResetPassword() {
        if (!TextUtils.isEmpty(common.phone_Number) && !TextUtils.isEmpty(validationNewPassword)
                && !TextUtils.isEmpty(validationConfirmNewPassword)) {
            change_Password();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();
        }
    }

    public void validate_Code() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().confirmation_Code(common.phone_Number, getPinCodeConfirmation()).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                spotsDialog.dismiss();
                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                spotsDialog.dismiss();
            }
        });
    }

    public void send_Code() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().send_Code(userPhone).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                data.setValue(response.body());
                common.phone_Number = userPhone;
                spotsDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                spotsDialog.dismiss();
            }
        });
    }

    public void change_Password() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().change_Password(common.phone_Number, validationNewPassword, validationConfirmNewPassword).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                if (response.body().getStatus() == 200) {
                    data.setValue(response.body());
                    spotsDialog.dismiss();
                } else {
                    spotsDialog.dismiss();
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.getMessage());
                spotsDialog.dismiss();
            }
        });
    }

    public MutableLiveData<ReqDetailsModel> getValidationResponse() {
        return data;
    }

    //================================================//
    @Bindable
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        notifyChange();
        this.userPhone = userPhone;
    }

    @Bindable
    public String getPinCodeConfirmation() {
        return pinCodeConfirmation;
    }

    public void setPinCodeConfirmation(String pinCodeConfirmation) {
        notifyChange();
        this.pinCodeConfirmation = pinCodeConfirmation;
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
