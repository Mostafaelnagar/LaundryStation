package app.laundrystation.viewModels;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.adapters.MyOrderDetailAdapter;
import app.laundrystation.common.common;
import app.laundrystation.models.OrderObject;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.ui.fragments.OrderDetailsFragment;

public class MyOrderDetailViewModel extends BaseObservable {
    public List<OrderObject> myOrderDetail;
    public MyOrderDetailAdapter myOrderDetailAdapter;
    OrderDetail orderDetail;
    public String DelegatedName, OrderTotal, DelegatedPhone, DelegatedImage;
    public MutableLiveData<Context> contextMutableLiveData;
    public MutableLiveData<Integer> cancelMutableLiveData;
    public int cancelVis;

    public MyOrderDetailViewModel(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        contextMutableLiveData = new MutableLiveData<>();
        cancelMutableLiveData = new MutableLiveData<>();
        myOrderDetail = new ArrayList<>();
        myOrderDetail = orderDetail.getServices();
        myOrderDetailAdapter = new MyOrderDetailAdapter();
    }

    @BindingAdapter({"app:myOrderDetailAdapter", "app:myOrderDetail"})
    public static void bind(RecyclerView recyclerView, MyOrderDetailAdapter myOrderDetailAdapter, List<OrderObject> myOrderDetail) {
        recyclerView.setAdapter(myOrderDetailAdapter);
        myOrderDetailAdapter.updateData(myOrderDetail);
    }

    @BindingAdapter({"bind:DelegatedImage"})
    public static void loadImage(ImageView view, String profileImage) {
        Picasso.get()
                .load(profileImage)
                .placeholder(R.drawable.profile_holder)
                .into(view);
    }

    public void back() {
        common.removeFragment(new OrderDetailsFragment(), contextMutableLiveData.getValue());
    }

    public void cancelOrder() {
        cancelMutableLiveData.setValue(1);
    }

    public void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + orderDetail.getDelegate().getPhone()));
        if (ActivityCompat.checkSelfPermission(contextMutableLiveData.getValue(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        contextMutableLiveData.getValue().startActivity(callIntent);
    }


    @Bindable
    public List<OrderObject> getMyOrderDetail() {
        return myOrderDetail;
    }

    public void setMyOrderDetail(List<OrderObject> myOrderDetail) {
        this.myOrderDetail = myOrderDetail;
    }

    @Bindable
    public MyOrderDetailAdapter getMyOrderDetailAdapter() {
        return myOrderDetailAdapter;
    }

    public void setMyOrderDetailAdapter(MyOrderDetailAdapter myOrderDetailAdapter) {
        this.myOrderDetailAdapter = myOrderDetailAdapter;
    }

    @Bindable
    public String getDelegatedName() {
        return !TextUtils.isEmpty(orderDetail.getDelegate().getName()) ? orderDetail.getDelegate().getName() : "-----";
    }

    @Bindable
    public String getOrderTotal() {
        double orderTotal = 0.0;
        for (int i = 0; i < myOrderDetail.size(); i++) {
            orderTotal = orderTotal + myOrderDetail.get(i).getServiceTotal();
        }
        return String.valueOf(orderTotal + contextMutableLiveData.getValue().getResources().getString(R.string.coin));
    }

    @Bindable
    public String getDelegatedPhone() {
        return !TextUtils.isEmpty(orderDetail.getDelegate().getPhone()) ? orderDetail.getDelegate().getPhone() : "-------";
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void setDelegatedName(String delegatedName) {
        DelegatedName = delegatedName;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public void setDelegatedPhone(String delegatedPhone) {
        DelegatedPhone = delegatedPhone;
    }

    public String getDelegatedImage() {
        return String.valueOf(!TextUtils.isEmpty(orderDetail.getDelegate().getImg()) ? orderDetail.getDelegate().getImg() : contextMutableLiveData.getValue().getResources().getDrawable(R.drawable.profile_holder));
    }

    public void setDelegatedImage(String delegatedImage) {
        DelegatedImage = delegatedImage;
    }

    @Bindable
    public int getCancelVis() {
        cancelVis = orderDetail.getOrderStatus();
        return cancelVis != 6 ? View.GONE : View.VISIBLE;
    }

    public void setCancelVis(int saleVisibility) {
        this.cancelVis = cancelVis;
    }

}
