package app.laundrystation.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.databinding.ServiceTypeItemBinding;
import app.laundrystation.databinding.ServicesItemBinding;
import app.laundrystation.models.OrderObject;
import app.laundrystation.models.laundries.Service;
import app.laundrystation.viewModels.LaundryServicesItemViewModel;

public class LaundryServicesAdapter extends RecyclerView.Adapter<LaundryServicesAdapter.ViewHolder> {
    List<Service> laundryServices;
    private Context context;
    public MutableLiveData<String> totalLiveData;

    List<OrderObject> orderObjectList;
    OrderObject orderObject;
    SparseArray<Integer> itemsCache;


    public LaundryServicesAdapter(MutableLiveData<String> totalLiveData) {
        laundryServices = new ArrayList<>();

        this.totalLiveData = totalLiveData;
        orderObject = new OrderObject();
        orderObjectList = SharedPrefManager.getInstance(context).getCart();
        itemsCache = new SparseArray<>();

        if (orderObjectList == null) orderObjectList = new ArrayList<>();
        for (int i = 0; i < orderObjectList.size(); i++) {
            itemsCache.put(orderObjectList.get(i).getServiceId(), orderObjectList.get(i).getServiceCount());
        }
    }


    @NonNull
    @Override
    public LaundryServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Service service = laundryServices.get(position);
        LaundryServicesItemViewModel itemViewModel = new LaundryServicesItemViewModel(service, context);
        setDataFromSharedPreference(itemViewModel, service);
        plusMinsClicks(itemViewModel);
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        if (laundryServices.size() == 0 && SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal() != 0.0) {
            totalLiveData.setValue(String.valueOf(SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal()));
        }
        return laundryServices.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull LaundryServicesAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull LaundryServicesAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Service> serviceList) {
        this.laundryServices.clear();
        this.laundryServices.addAll(serviceList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ServicesItemBinding sectionsItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (sectionsItemBinding == null) {
                sectionsItemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (sectionsItemBinding != null) {
                sectionsItemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(LaundryServicesItemViewModel viewModel) {
            if (sectionsItemBinding != null) {
                sectionsItemBinding.setLaundryServicesItemViewModel(viewModel);
            }
        }
    }

    private void setDataFromSharedPreference(LaundryServicesItemViewModel itemViewModel, Service service) {
        if (itemsCache.get(service.getId(), -1) != -1) {
            if (orderObjectList == null) return;
            for (int n = 0; n < orderObjectList.size(); n++) {
                if (service.getId() == orderObjectList.get(n).getServiceId()) {
                    itemViewModel.setIroning(orderObjectList.get(n).getServicePrice());
                    itemViewModel.setName(orderObjectList.get(n).getServiceName());
                    itemViewModel.setType(orderObjectList.get(n).getServiceType());
                    itemViewModel.setCountVisibility(1);
                    itemViewModel.setMinusVisibility(1);
                    itemViewModel.getServiceModel().setCount(itemsCache.get(service.getId()));
                } else {

                }
                Log.i("setDataFrom", "setDataFromSharedPreference: " + orderObjectList.get(n).getServiceId());
            }
        } else {
            service.setCount(0);
            itemViewModel.setIroning(service.getIroning());
            itemViewModel.setName(service.getName());
            itemViewModel.setType(context.getResources().getString(R.string.cat_ironing));
        }

        if (SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal() != 0.0) {
            totalLiveData.setValue(String.valueOf(SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal()));
            Log.i("orderTotal", "setDataShared: not" + SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal());
        } else {
            Log.i("orderTotal", "setDataFromSharedPreference: " + SharedPrefManager.getInstance(MyApplication.getInstance()).getTotal());
        }
    }

    private void plusMinsClicks(final LaundryServicesItemViewModel laundryServicesItemViewModel) {

        laundryServicesItemViewModel.getItemsPlusLiveListener().observe((LifecycleOwner) context, new Observer<Service>() {
            @Override
            public void onChanged(Service service) {
                if (service.isShowPopUp()) {
                    inflateServiceTypeView(service, laundryServicesItemViewModel);
                } else {
                }
                itemsCache.put(service.getId(), service.getCount());

                //calcOrderTotal(laundryServicesItemViewModel.getIroning(),service.getCount(),true);
                orderObjectList = SharedPrefManager.getInstance(context).getCart();
                notifyDataSetChanged();
            }
        });
        laundryServicesItemViewModel.itemsMinusLiveListener.observe((LifecycleOwner) context, new Observer<Service>() {
            @Override
            public void onChanged(Service service) {
                if (service.getCount() == 0) {
                    itemsCache.remove(service.getId());
                } else {
                    itemsCache.put(service.getId(), service.getCount());
                }


                orderObjectList = SharedPrefManager.getInstance(context).getCart();
                notifyDataSetChanged();
            }
        });

        laundryServicesItemViewModel.totalLiveData.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                totalLiveData.setValue(s);
            }
        });
    }

    private void inflateServiceTypeView(final Service service, final LaundryServicesItemViewModel laundryServicesItemViewModel) {

        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final ServiceTypeItemBinding typeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.service_type_item, null, false);
        dialog.setContentView(typeItemBinding.getRoot());
        String washPrice = service.getWash();
        String ironingPrice = service.getIroning();
        String washIroningPrice = service.getWashIroning();
        if (washPrice.equals("")) {
            typeItemBinding.radioWashing.setVisibility(View.GONE);
            typeItemBinding.wash.setVisibility(View.GONE);
            typeItemBinding.washingPrice.setVisibility(View.GONE);

        }
        if (ironingPrice.equals("")) {
            typeItemBinding.ironing.setVisibility(View.GONE);
            typeItemBinding.radioIroning.setVisibility(View.GONE);
            typeItemBinding.ironingPrice.setVisibility(View.GONE);

        }
        if (washIroningPrice.equals("")) {
            typeItemBinding.washIroning.setVisibility(View.GONE);
            typeItemBinding.radioWashingIroning.setVisibility(View.GONE);
            typeItemBinding.washingIroningPrice.setVisibility(View.GONE);

        }

        typeItemBinding.washingPrice.setText(washPrice + context.getResources().getString(R.string.coin));
        typeItemBinding.ironingPrice.setText(ironingPrice + context.getResources().getString(R.string.coin));
        typeItemBinding.washingIroningPrice.setText(washIroningPrice + context.getResources().getString(R.string.coin));


        typeItemBinding.btnServiceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadio = typeItemBinding.radioServicesGroup.getCheckedRadioButtonId();
                if (selectedRadio == R.id.radioWashing) {
                    laundryServicesItemViewModel.setIroning(spilt(typeItemBinding.washingPrice.getText().toString(), context.getResources().getString(R.string.coin), ""));
                    laundryServicesItemViewModel.setType(context.getResources().getString(R.string.cat_wash));
                } else if (selectedRadio == R.id.radioIroning) {
                    laundryServicesItemViewModel.setIroning(spilt(typeItemBinding.ironingPrice.getText().toString(), context.getResources().getString(R.string.coin), ""));
                    laundryServicesItemViewModel.setType(context.getResources().getString(R.string.cat_ironing));
                } else {
                    laundryServicesItemViewModel.setIroning(spilt(typeItemBinding.washingIroningPrice.getText().toString(), context.getResources().getString(R.string.coin), ""));
                    laundryServicesItemViewModel.setType(context.getResources().getString(R.string.cat_wash_ironing));
                }


                laundryServicesItemViewModel.getServiceModel().setCount(1);
                laundryServicesItemViewModel.plusAction();
                itemsCache.put(service.getId(), 1);
                orderObjectList = SharedPrefManager.getInstance(context).getCart();
                //calcOrderTotal(laundryServicesItemViewModel.getIroning(),1,true);
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private String spilt(String main, String old, String newt) {
        String rs = main.replace(old, newt); // Replace old with newt
        return rs;
    }
}

