package app.laundrystation.viewModels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.adapters.MyOrdersAdapter;
import app.laundrystation.common.common;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.models.orders.OrderRequest;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.MyOrdersFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersViewModels extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    private List<OrderDetail> myOrderDetails;
    private MyOrdersAdapter myOrderAdapter;
    public MutableLiveData<OrderRequest> orderRequest;
    private int emptyOrders;

    public MyOrdersViewModels() {
        contextMutableLiveData = new MutableLiveData<>();
        myOrderDetails = new ArrayList<>();
        myOrderAdapter = new MyOrdersAdapter();
        orderRequest = new MutableLiveData<>();
    }

    @BindingAdapter({"app:myOrderAdapter", "app:myOrderDetails"})
    public static void bind(RecyclerView recyclerView, MyOrdersAdapter myOrderAdapter, List<OrderDetail> orderDetails) {
        recyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.updateData(orderDetails);
    }

    public void back() {
        if (common.fragmentCount(contextMutableLiveData.getValue()) == 0)
            ((Activity) contextMutableLiveData.getValue()).finish();
        else
            common.removeFragment(new MyOrdersFragment(), contextMutableLiveData.getValue());
    }

    @Bindable
    public List<OrderDetail> getMyOrderDetails() {
        return myOrderDetails;
    }

    public MutableLiveData<OrderRequest> getMyOrders() {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getMyOrders().enqueue(new Callback<OrderRequest>() {
            @Override
            public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {

                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    orderRequest.setValue(response.body());
                    myOrderDetails = orderRequest.getValue().getData();
                    setEmptyOrders(myOrderDetails.size());

                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderRequest> call, Throwable t) {
                spotsDialog.dismiss();

                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return orderRequest;
    }

    public void setMyOrderDetails(List<OrderDetail> myOrderDetails) {
        this.myOrderDetails = myOrderDetails;
    }

    @Bindable
    public MyOrdersAdapter getMyOrderAdapter() {
        return myOrderAdapter;
    }

    public void setMyOrderAdapter(MyOrdersAdapter myOrderAdapter) {
        this.myOrderAdapter = myOrderAdapter;
    }

    public int getEmptyOrders() {
        return emptyOrders == 0 ? View.VISIBLE : View.GONE;

    }

    public void setEmptyOrders(int emptyOrders) {
        this.emptyOrders = emptyOrders;
        notifyPropertyChanged(emptyOrders);
    }
}
