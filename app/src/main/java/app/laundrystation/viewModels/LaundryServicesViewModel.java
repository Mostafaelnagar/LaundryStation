package app.laundrystation.viewModels;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.laundrystation.BR;
import app.laundrystation.R;
import app.laundrystation.adapters.LaundryServicesAdapter;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.laundries.Service;
import app.laundrystation.ui.fragments.PaymentFragment;

public class LaundryServicesViewModel extends BaseObservable {
    public List<Service> serviceList;
    public LaundryServicesAdapter laundryServicesAdapter;
    public MutableLiveData<String> totalMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Context> contextMutableLiveData = new MutableLiveData<>();
    public String orderTotal;

    public LaundryServicesViewModel(List<Service> serviceList) {
        this.serviceList = serviceList;
        laundryServicesAdapter = new LaundryServicesAdapter(totalMutableLiveData);
        setLaundrySections(serviceList);

        notifyChange();
    }

    @BindingAdapter({"app:laundryServicesAdapter", "app:laundryServices"})
    public static void bind(RecyclerView recyclerView, LaundryServicesAdapter laundryServicesAdapter, List<Service> serviceList) {
        recyclerView.setAdapter(laundryServicesAdapter);
        laundryServicesAdapter.updateData(serviceList);
    }

    @Bindable
    public List<Service> getLaundryServices() {
        return serviceList;
    }

    public void setLaundrySections(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Bindable
    public LaundryServicesAdapter getLaundryServicesAdapter() {
        return laundryServicesAdapter;
    }

    public void setLaundryServicesAdapter(LaundryServicesAdapter laundryServicesAdapter) {
        this.laundryServicesAdapter = laundryServicesAdapter;
    }

    @Bindable
    public String getOrderTotal() {
        String totalText = " " + contextMutableLiveData.getValue().getResources().getString(R.string.order_total_textAfter);
        return !TextUtils.isEmpty(totalMutableLiveData.getValue()) ? totalText + totalMutableLiveData.getValue() + contextMutableLiveData.getValue().getResources().getString(R.string.coin) : MyApplication.getInstance().getResources().getString(R.string.order_amount);
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
        notifyPropertyChanged(BR.orderTotal);
    }

    public void setOrder() {
        if (SharedPrefManager.getInstance(MyApplication.getInstance()).getCart() != null && SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal() != 0.0) {
            Bundle bundle = new Bundle();
            bundle.putFloat("Total", SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal());
            PaymentFragment paymentFragment = new PaymentFragment();
            paymentFragment.setArguments(bundle);
            common.confirmation_Page(contextMutableLiveData.getValue(), R.id.home_Main_Container, paymentFragment);
            SharedPrefManager.getInstance(MyApplication.getInstance()).saveTotal((float) 0.0);
        } else {
            Toast.makeText(contextMutableLiveData.getValue(), "" + contextMutableLiveData.getValue().getResources().getString(R.string.emptyCart), Toast.LENGTH_SHORT).show();
        }
    }
}
