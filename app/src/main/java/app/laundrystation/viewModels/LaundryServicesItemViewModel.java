package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.*;

import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.OrderObject;
import app.laundrystation.models.laundries.Service;

public class LaundryServicesItemViewModel extends BaseObservable {
    private Service service;
    public MutableLiveData<Service> itemsPlusLiveListener;
    public MutableLiveData<Service> itemsMinusLiveListener;
    public String itemCount, ironing, name, type;
    private int countVisibility;
    private int minusVisibility;
    public MutableLiveData<String> totalLiveData;
    public Context context;

    public LaundryServicesItemViewModel(Service service, Context context) {
        this.service = service;
        this.itemsPlusLiveListener = new MutableLiveData<>();
        this.itemsMinusLiveListener = new MutableLiveData<>();
        this.totalLiveData = new MutableLiveData<>();
        this.context = context;
    }

    public MutableLiveData<Service> getItemsPlusLiveListener() {
        return itemsPlusLiveListener;
    }

    public MutableLiveData<Service> getItemsMinusLiveListener() {
        return itemsMinusLiveListener;
    }

    @Bindable
    public String getIroning() {
        return !TextUtils.isEmpty(ironing) ? ironing : "";
    }

    public void setIroning(String ironing) {
        this.ironing = ironing;
        notifyChange();
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public String getType() {
        return !TextUtils.isEmpty(type) ? type : "";
    }

    public void setType(String type) {
        this.type = type;
        notifyChange();
    }

    @Bindable
    public String getName() {
        return !TextUtils.isEmpty(name) ? name : "";
    }

    public Service getServiceModel() {
        return service;
    }

    public void plusAction() {

        if (service.getCount() > 0) {
            if (!service.isShowPopUp()) {
                service.setCount(service.getCount() + 1);

            }
            service.setShowPopUp(false);
            OrderObject orderObject = new OrderObject();
            orderObject.setServiceCount(service.getCount());
            orderObject.setServiceId(service.getId());
            orderObject.setServiceName(service.getName());
            orderObject.setServicePrice(getIroning());
            orderObject.setServiceType(getType());
            SharedPrefManager.getInstance(MyApplication.getInstance().getApplicationContext()).addTocart(orderObject);
        } else {
            service.setShowPopUp(true);
        }
        calcOrderTotal(getIroning(), service.getCount(), true);
        notifyChange();

        itemsPlusLiveListener.setValue(service);


    }

    private void calcOrderTotal(String catPrice, int itemCount, boolean isIncrease) {
        double orderTotal = 0.0;

        if (itemCount > 0 && isIncrease) {
            orderTotal += Double.parseDouble(catPrice);
        }

        if (!isIncrease && itemCount > 0) {
            orderTotal -= Double.parseDouble(catPrice);
        }
        float total = SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal();
        total += orderTotal;
        SharedPrefManager.getInstance(MyApplication.getInstance()).saveTotal(total);
        totalLiveData.setValue(String.valueOf(total));
    }

    public void minusAction() {
        calcOrderTotal(getIroning(), service.getCount(), false);

        if (service.getCount() > 0) {
            service.setCount(service.getCount() - 1);
            SharedPrefManager.getInstance(MyApplication.getInstance().getApplicationContext()).removeFromCart(service.getId());

        } else {

        }
        itemsMinusLiveListener.setValue(service);
        notifyChange();
    }


    @Bindable
    public String getItemCount() {
        return service.getCount() + "";
    }

    public void setItemCount(String itemCount) {
        this.itemCount = service.getCount() + "";
        notifyChange();
    }

    @Bindable
    public int getCountVisibility() {
        return countVisibility;
    }

    public void setCountVisibility(int countVisibility) {
        this.countVisibility = countVisibility;
        notifyChange();
    }

    @BindingAdapter({"bind:serviceImage"})
    public static void loadImage(ImageView view, String serviceImage) {
        Picasso.get()
                .load(serviceImage)
                .placeholder(R.color.overlayBackground)
                .into(view);
    }

    public String getServiceImage() {
        return String.valueOf(!TextUtils.isEmpty(service.getImg()) ? service.getImg() : context.getResources().getDrawable(R.mipmap.background));
    }

    @Bindable
    public int getMinusVisibility() {
        return minusVisibility;
    }

    public void setMinusVisibility(int minusVisibility) {
        this.minusVisibility = minusVisibility;
    }
}
