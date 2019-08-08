package app.laundrystation.viewModels;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.adapters.AddressesAdapter;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.register.Address;
import app.laundrystation.ui.fragments.ChooseAddressFragment;
import app.laundrystation.ui.fragments.NewAddressFragment;

public class ChooseAddressViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public AddressesAdapter addressAdapter;
    public List<Address> addressList;

    public ChooseAddressViewModel() {
        contextMutableLiveData = new MutableLiveData<>();
        addressAdapter = new AddressesAdapter();
        addressList = new ArrayList<>();
        addressList = SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getUserData().getAddresses();
    }

    @BindingAdapter({"app:addressAdapter", "app:addressList"})
    public static void bind(RecyclerView recyclerView, AddressesAdapter addressesAdapter, List<Address> addressList) {
        recyclerView.setAdapter(addressesAdapter);
        addressesAdapter.updateData(addressList);
    }

    @Bindable
    public List<Address> getAddressList() {
        if (common.address != null) {
            addressList.add(common.selectedAddress);
            return addressList;
        }
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void newAddress() {
        common.confirmation_Page(contextMutableLiveData.getValue(), R.id.home_Main_Container, new NewAddressFragment());
    }

    public void confirmAddress() {
        common.removeFragment(new ChooseAddressFragment(), contextMutableLiveData.getValue());
    }

    public void back() {
        common.removeFragment(new ChooseAddressFragment(), contextMutableLiveData.getValue());
     }
}
