package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.ui.fragments.ChatFragment;

public class MyOrdersItemViewModels extends BaseObservable {
    OrderDetail orderDetail;
    public MutableLiveData<OrderDetail> itemsOperationsLiveListener;
    Context context;
    public String[] orderStatus;

    public MyOrdersItemViewModels(OrderDetail orderDetail, Context context) {
        this.orderDetail = orderDetail;
        this.context = context;
        itemsOperationsLiveListener = new MutableLiveData<>();
        orderStatus = context.getResources().getStringArray(R.array.orderStatus);

    }

    public MutableLiveData<OrderDetail> getItemsOperationsLiveListener() {
        return itemsOperationsLiveListener;
    }

    @Bindable
    public String getLaundryName() {
        return !TextUtils.isEmpty(orderDetail.getLaundry().getName()) ? orderDetail.getLaundry().getName() : "";
    }

    @Bindable
    public String getOrderTotal() {
        return !TextUtils.isEmpty(String.valueOf(orderDetail.getServices().get(0).getServiceTotal())) ? context.getResources().getString(R.string.coin) + orderDetail.getServices().get(0).getServiceTotal() : "";
    }

    @Bindable
    public String getOrderNumber() {
        return String.valueOf(orderDetail.getId());
    }

    @Bindable
    public String getOrderStatus() {
        String status = null;
        if (orderDetail.getOrderStatus() == 0)
            status = orderStatus[0];
        else if (orderDetail.getOrderStatus() <= 5)
            status = orderStatus[orderDetail.getOrderStatus() - 1];
        return !TextUtils.isEmpty(status) ? status : "لم يتم القبول";
    }

    @Bindable
    public String getOrderDate() {
        return !TextUtils.isEmpty(orderDetail.getCreatedAt()) ? orderDetail.getCreatedAt() : "";
    }

    public OrderDetail getMyOrders() {
        return orderDetail;
    }

    public void performClickAction(OrderDetail orderDetail) {
        notifyChange();
        itemsOperationsLiveListener.setValue(orderDetail);
    }

    public void startChat() {
        if (orderDetail.getDelegateId() == 0) {
            Toast.makeText(context, context.getResources().getString(R.string.orderEmpty), Toast.LENGTH_SHORT).show();
        } else {
            common.confirmation_Page(context, R.id.home_Main_Container, new ChatFragment(orderDetail));
        }
    }
}
