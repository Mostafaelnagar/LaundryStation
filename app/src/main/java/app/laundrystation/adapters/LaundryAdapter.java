package app.laundrystation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.databinding.HomeItemBinding;
import app.laundrystation.models.laundries.Laundry_Details;
import app.laundrystation.viewModels.LaundryItemViewModel;

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.ViewHolder> implements Filterable {
    List<Laundry_Details> laundry_details;
    List<Laundry_Details> laundryDetailsFilter;
    Context context;

    public LaundryAdapter(List<Laundry_Details> laundry_details, Context context) {
        this.laundry_details = laundry_details;
        laundryDetailsFilter = laundry_details;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Laundry_Details details = laundryDetailsFilter.get(position);
        LaundryItemViewModel itemViewModel = new LaundryItemViewModel(details, position, context);
        itemViewModel.getItemsOperationsLiveListener().observe((LifecycleOwner) context, new Observer<Laundry_Details>() {
            @Override
            public void onChanged(Laundry_Details laundry_details) {
                notifyItemChanged(position);
            }
        });
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return laundryDetailsFilter.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Laundry_Details> laundry_details) {
        if (laundry_details == null || laundry_details.isEmpty()) {
            this.laundryDetailsFilter.clear();
        } else {
            this.laundryDetailsFilter.clear();
            this.laundryDetailsFilter.addAll(laundry_details);
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    laundryDetailsFilter = laundry_details;
                } else {
                    List<Laundry_Details> filteredList = new ArrayList<>();
                    for (Laundry_Details row : laundry_details) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    laundryDetailsFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = laundryDetailsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                laundryDetailsFilter = (ArrayList<Laundry_Details>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomeItemBinding itemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (itemBinding == null) {
                itemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (itemBinding != null) {
                itemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(LaundryItemViewModel viewModel) {
            if (itemBinding != null) {
                itemBinding.setLaundryItemViewModel(viewModel);
            }
        }
    }
}
