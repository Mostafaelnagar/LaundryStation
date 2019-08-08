package app.laundrystation.ui.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rengwuxian.materialedittext.MaterialEditText;

import app.laundrystation.R;
import app.laundrystation.databinding.CancelOrderItemBinding;
import app.laundrystation.databinding.FragmentOrderDetailsBinding;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.viewModels.MyOrderDetailViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {
    OrderDetail orderDetail;
    FragmentOrderDetailsBinding detailsBinding;
    MyOrderDetailViewModel detailViewModel;
    RecyclerView recyclerViewDetail;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public OrderDetailsFragment(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        detailViewModel = new MyOrderDetailViewModel(orderDetail);
        detailsBinding.setMyOrderDetailViewModel(detailViewModel);
        detailViewModel.contextMutableLiveData.setValue(getActivity());
        initViews();
        // TODO:stepview
        int status = orderDetail.getOrderStatus();
        if (status == 1) {
            detailsBinding.stepView.go(2, true);
        } else
            detailsBinding.stepView.go(status, true);
        detailViewModel.cancelMutableLiveData.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                showCancelOrder();
                detailViewModel.notifyChange();
            }
        });
        return detailsBinding.getRoot();
    }

    private void initViews() {
        recyclerViewDetail = detailsBinding.recOrderDetail;
        recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDetail.setHasFixedSize(true);
    }

    private void showCancelOrder() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final CancelOrderItemBinding typeItemBinding = (CancelOrderItemBinding) DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.cancel_order_item, null, false);
        dialog.setContentView(typeItemBinding.getRoot());
        final MaterialEditText cancelOrderReason = dialog.findViewById(R.id.cancelOrderReason);
        Button cancelOrderBttun = dialog.findViewById(R.id.cancelOrderBttun);
        cancelOrderBttun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServiceGenerator.getRequestApi().cancelOrders(orderDetail.getId(), cancelOrderReason.getText().toString()).enqueue(new Callback<CreateOrder>() {
                    @Override
                    public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateOrder> call, Throwable t) {
                    }
                });

            }
        });
        dialog.show();
    }
}
