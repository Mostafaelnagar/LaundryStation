package app.laundrystation.common;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import app.laundrystation.databinding.UpdateUserAddressItemBinding;
import app.laundrystation.viewModels.LaundryServicesItemViewModel;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding sectionsItemBinding;

    public BaseViewHolder(@NonNull View itemView) {
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


}
