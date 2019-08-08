package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.BR;
import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.OrderObject;
import app.laundrystation.models.cities.Cities;
import app.laundrystation.models.cities.CityRequest;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.ChooseAddressFragment;
import app.laundrystation.ui.fragments.FragmentLaundryDetails;
import app.laundrystation.ui.fragments.LaundrySectionsFragment;
import app.laundrystation.ui.fragments.PaymentFragment;
import app.laundrystation.ui.fragments.ServicesFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public String userAddress;
    public String paymentMethod;
    public List<OrderObject> orderObjectList;
    public OrderObject orderObject;
    private String servicesName, services_id, services_type, services_count, servicesPrice, lat, lng, city;
    int delivery;
    public List<Cities> citiesDetails;
    public CityRequest cityRequest;
    public ArrayAdapter<Cities> citiesAdapter;
    public PaymentViewModel(  ) {
         contextMutableLiveData = new MutableLiveData<>();
        orderObjectList = SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getCart();
        orderObject = new OrderObject();
        citiesDetails = new ArrayList<>();
        citiesDetails = common.citiesList;
        cityRequest = new CityRequest();
    }

    public void selectAddress() {
        common.confirmation_Page(contextMutableLiveData.getValue(), R.id.home_Main_Container, new ChooseAddressFragment());
    }


    @BindingAdapter({"app:citiesAdapter", "app:citiesDetails"})
    public static void bind(Spinner spinner, ArrayAdapter citiesAdapter, List<Cities> citiesDetails) {
        if (spinner.getAdapter() == null || spinner.getAdapter().getCount() < 1) {
            citiesAdapter = new ArrayAdapter<Cities>(spinner.getContext(), R.layout.support_simple_spinner_dropdown_item);
            citiesAdapter.addAll(citiesDetails);
            spinner.setAdapter(citiesAdapter);
        }
    }


    public void setCitiesDetails(List<Cities> citiesDetails) {
        this.citiesDetails = citiesDetails;
    }

    public List<Cities> getCitiesDetails() {
        return citiesDetails;
    }

    @Bindable
    public String getUserAddress() {
        notifyChange();
        if (common.selectedAddress.getAddress() != null) {
            lat = common.selectedAddress.getLat();
            lng = common.selectedAddress.getLng();
            return !TextUtils.isEmpty(common.selectedAddress.getAddress()) ? common.selectedAddress.getAddress() : "";
        } else {
            String address = SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getUserData().getAddresses().get(0).getAddress();
            lat = SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getUserData().getAddresses().get(0).getLat();
            lng = SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getUserData().getAddresses().get(0).getLng();
            return !TextUtils.isEmpty(address) ? address : "";
        }
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void createOrder() {
        for (int i = 0; i < orderObjectList.size(); i++) {
            if (i == 0) {
                servicesName = orderObjectList.get(i).getServiceName();
                services_id = orderObjectList.get(i).getServiceId() + "";
                services_type = orderObjectList.get(i).getServiceType();
                services_count = orderObjectList.get(i).getServiceCount() + "";
                servicesPrice = orderObjectList.get(i).getServicePrice();
            } else {
                servicesName = servicesName + "," + orderObjectList.get(i).getServiceName();
                services_id = services_id + "," + orderObjectList.get(i).getServiceId();
                services_type = services_type + "," + orderObjectList.get(i).getServiceType();
                services_count = services_count + "," + orderObjectList.get(i).getServiceCount();
                servicesPrice = servicesPrice + "," + orderObjectList.get(i).getServicePrice();
            }
        }
        if (getUserAddress() != null && paymentMethod != null) {
            final SpotsDialog dialog = new SpotsDialog(contextMutableLiveData.getValue());
            dialog.show();
            ServiceGenerator.getRequestApi().createOrder(services_id, servicesName, services_type, services_count, servicesPrice, city
                    , delivery, getUserAddress(), common.LAUNDRY_DETAILS.getId(), lat, lng).enqueue(new Callback<CreateOrder>() {
                @Override
                public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                    if (response.body().getStatus() == 200) {
                        dialog.dismiss();
                        Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(contextMutableLiveData.getValue()).removeCart();
                        common.removeFragment(new PaymentFragment(), contextMutableLiveData.getValue());
                        common.removeFragment(new LaundrySectionsFragment(), contextMutableLiveData.getValue());
                        common.removeFragment(new FragmentLaundryDetails(), contextMutableLiveData.getValue());
                    } else {
                        dialog.dismiss();
                        Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CreateOrder> call, Throwable t) {
                    dialog.dismiss();
                    Log.i("onFailure", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(contextMutableLiveData.getValue(), "" + contextMutableLiveData.getValue().getString(R.string.paymentMethodEmpty), Toast.LENGTH_SHORT).show();
        }
        Log.i("createOrder", "createOrder:  payment" + paymentMethod + "address" + getUserAddress());
    }

    public void back() {
         common.removeFragment(new PaymentFragment(), contextMutableLiveData.getValue());
    }

    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
        if (id == R.id.radioOnHand) {
            paymentMethod = contextMutableLiveData.getValue().getResources().getString(R.string.payment_on_deliver);
        }
        if (id == R.id.radioOnlinePayment) {
            paymentMethod = contextMutableLiveData.getValue().getResources().getString(R.string.payment_online);
        }
    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
        delivery = citiesDetails.get(pos).getDelivery();
        city = citiesDetails.get(pos).getName();
    }

}
