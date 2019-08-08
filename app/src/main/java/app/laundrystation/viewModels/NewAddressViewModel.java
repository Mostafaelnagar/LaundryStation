package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.ChooseAddressFragment;
import app.laundrystation.ui.fragments.NewAddressFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAddressViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public String selectedAddress;
    public MutableLiveData<Integer> addressResult = new MutableLiveData<>();
    private String pickLocationText, userAddresses;
    public Double lat, lng;

    public NewAddressViewModel() {
        contextMutableLiveData = new MutableLiveData<>();
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

    public void confirmSelection() {
        if (!TextUtils.isEmpty(userAddresses)) {
            ServiceGenerator.getRequestApi().addNewAddress(lat, lng, getUserAddresses()).enqueue(new Callback<ReqDetailsModel>() {
                @Override
                public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                    Toast.makeText(contextMutableLiveData.getValue(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(contextMutableLiveData.getValue()).userLogin(response.body().getUserData());
                    common.removeFragment(new ChooseAddressFragment(), contextMutableLiveData.getValue());
                }

                @Override
                public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                    Toast.makeText(contextMutableLiveData.getValue(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else
            Toast.makeText(contextMutableLiveData.getValue(), contextMutableLiveData.getValue().getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

    }

    public void back() {
        common.removeFragment(new ChooseAddressFragment(), contextMutableLiveData.getValue());
    }

    @Bindable
    public String getPickLocationText() {
        return pickLocationText;
    }

    public void setPickLocationText(String pickLocationText) {
        this.pickLocationText = pickLocationText;
        notifyChange();
    }

    @Bindable
    public String getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(String userAddresses) {
        this.userAddresses = userAddresses;
        notifyChange();
    }
}
