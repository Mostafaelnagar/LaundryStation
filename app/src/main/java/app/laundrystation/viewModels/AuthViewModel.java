package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.services.ServiceGenerator;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends BaseObservable {
    public String userPhone;
    public String userPassword;
    private MutableLiveData<ReqDetailsModel> authResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> addressResult = new MutableLiveData<>();
    private Context context;
    public String userName;
    public String userEmail;
    public String userAddresses, pickLocationText;
    public Double lat, lng;

    public AuthViewModel(Context context) {
        this.context = context;
    }

    //get Address of user
    public void getUserAddress() {
        addressResult.setValue(common.REQUEST_CODE);
    }

    public void setUserAddress(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
        notifyChange();
    }

    //user login
    public void userAuth() {
        //for user login
        notifyChange();
        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPassword)) {
            userLogin(getUserPhone(), getUserPassword());
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }
    }

    //user Register
    public void userRegister() {
        notifyChange();
        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(userName)
                && !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userName)) {
            addNewUser();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }

    }

    //forget Password action
    public void userForgetPassword() {
        common.code = "forget";
        common.validation_Page(context, common.code);
    }

    public void userLogin(String phone, String password) {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().signIn(phone, password).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                spotsDialog.dismiss();
                common.phone_Number = userPhone;
                authResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("Repos", "" + t.getMessage());
            }
        });
    }

    public void addNewUser() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
         ServiceGenerator.getRequestApi().addNewUser(getUserName(), getUserEmail(), getUserPassword(), getUserPhone()
                , lat, lng, SharedPrefManager.getInstance(context).getToken(), common.device_Id, getUserAddresses()).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                if (response.body().getMsg().equalsIgnoreCase(common.register_msg)) {
                    spotsDialog.dismiss();
                    common.phone_Number = getUserPhone();
                    common.code = "confirm";
                    common.validation_Page(context, common.code);
                } else {
                    spotsDialog.dismiss();
                    Toast.makeText(context, "" + response.body().msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("Repos", "" + t.getMessage());
            }
        });
    }

    public void updateUserToken() {
        ServiceGenerator.getRequestApi().updateToken(SharedPrefManager.getInstance(context).getToken()).enqueue(new Callback<CreateOrder>() {
            @Override
            public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CreateOrder> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<ReqDetailsModel> getAuthResponse() {
        return authResponse;
    }

    //setters and getters
    @Bindable
    public String getUserPassword() {
        return userPassword;
    }

    @Bindable
    public String getUserPhone() {
        return userPhone;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    @Bindable
    public String getUserAddresses() {
        return userAddresses;
    }

    @Bindable
    public String getUserEmail() {
        return userEmail;
    }

    @Bindable
    public String getPickLocationText() {
        return pickLocationText;
    }

    public void setPickLocationText(String pickLocationText) {
        this.pickLocationText = pickLocationText;
        notifyChange();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyChange();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyChange();
    }


    public void setUserAddresses(String userAddresses) {
        this.userAddresses = userAddresses;
        notifyChange();
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        notifyChange();
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        notifyChange();

    }
}
