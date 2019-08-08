package app.laundrystation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.UserAddressItemBinding;
import app.laundrystation.models.register.Address;
import app.laundrystation.viewModels.ChooseAddressItemViewModel;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private List<Address> addressList;
    private Context context;
    private int lastSelectedPosition = -1;
    public MutableLiveData<Address> addressMutableLiveData;

    public AddressesAdapter() {
        addressList = new ArrayList<>();
        addressMutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_address_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder holder, final int position) {
        Address address = addressList.get(position);
        ChooseAddressItemViewModel itemViewModel = new ChooseAddressItemViewModel(address);
        holder.selectionState.setChecked(position == lastSelectedPosition);
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull AddressesAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AddressesAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Address> addresses) {
        this.addressList.clear();
        this.addressList.addAll(addresses);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserAddressItemBinding addressItemBinding;
        RadioButton selectionState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    addressMutableLiveData.setValue(addressList.get(getAdapterPosition()));
                    common.selectedAddress = addressMutableLiveData.getValue();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            selectionState.setOnClickListener(clickListener);
        }

        void bind() {
            if (addressItemBinding == null) {
                addressItemBinding = DataBindingUtil.bind(itemView);
                selectionState = addressItemBinding.selectionState;
            }
        }

        void unbind() {
            if (addressItemBinding != null) {
                addressItemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(ChooseAddressItemViewModel viewModel) {
            if (addressItemBinding != null) {
                addressItemBinding.setChooseAddressItemViewModel(viewModel);
            }
        }


    }
}
