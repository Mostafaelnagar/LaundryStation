package app.laundrystation.ui.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.laundrystation.R;
import app.laundrystation.databinding.FragmentMyOrdersBinding;
import app.laundrystation.models.orders.OrderRequest;
import app.laundrystation.viewModels.MyOrdersViewModels;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {
    private RecyclerView recyclerViewLaundries;
    private FragmentMyOrdersBinding myOrdersBinding;
    private MyOrdersViewModels myOrdersViewModels;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myOrdersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_orders, container, false);
        myOrdersViewModels = new MyOrdersViewModels();
        myOrdersBinding.setOrderViewModel(myOrdersViewModels);
        initViews();
        myOrdersViewModels.contextMutableLiveData.setValue(getActivity());
        myOrdersViewModels.getMyOrders();
        myOrdersViewModels.orderRequest.observe(getActivity(), new Observer<OrderRequest>() {
            @Override
            public void onChanged(OrderRequest orderDetail) {
                 myOrdersViewModels.notifyChange();
            }
        });
        return myOrdersBinding.getRoot();
    }

    private void initViews() {
        recyclerViewLaundries = myOrdersBinding.recMyOrders;
        recyclerViewLaundries.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLaundries.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        myOrdersViewModels.getMyOrders();
    }
}
