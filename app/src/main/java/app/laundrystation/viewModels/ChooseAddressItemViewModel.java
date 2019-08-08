package app.laundrystation.viewModels;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import app.laundrystation.models.register.Address;

public class ChooseAddressItemViewModel extends BaseObservable {
    private Address objectAddress;
    public String address;

    public ChooseAddressItemViewModel(Address objectAddress) {
        this.objectAddress = objectAddress;
    }


    @Bindable
    public String getAddress() {
        return !TextUtils.isEmpty(objectAddress.getAddress()) ? objectAddress.getAddress() : "";
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
    }

}
