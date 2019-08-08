package app.laundrystation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.SectionsItemBinding;
import app.laundrystation.models.laundries.SectionModel;
import app.laundrystation.ui.fragments.ServicesFragment;
import app.laundrystation.viewModels.LaundrySectionsItemViewModel;

public class LaundrySectionsAdapter extends RecyclerView.Adapter<LaundrySectionsAdapter.ViewHolder> {
    private List<SectionModel> laundrySections;
    private Context context;

    public LaundrySectionsAdapter() {
        laundrySections = new ArrayList<>();
    }


    @NonNull
    @Override
    public LaundrySectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sections_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final SectionModel sectionModel = laundrySections.get(position);
        LaundrySectionsItemViewModel itemViewModel = new LaundrySectionsItemViewModel(sectionModel);
        itemViewModel.getItemsOperationsLiveListener().observe((LifecycleOwner) context, new Observer<SectionModel>() {
            @Override
            public void onChanged(SectionModel laundry_details) {
                common.confirmation_Page(context, R.id.frame_Laundry_Details, new ServicesFragment(sectionModel.getSection_id()));
                notifyItemChanged(position);
            }
        });
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return laundrySections.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull LaundrySectionsAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull LaundrySectionsAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<SectionModel> laundry_details) {

        this.laundrySections.clear();
        this.laundrySections.addAll(laundry_details);

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SectionsItemBinding sectionsItemBinding;

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

        void setViewModel(LaundrySectionsItemViewModel viewModel) {
            if (sectionsItemBinding != null) {
                sectionsItemBinding.setLaundrySectionsItemViewModel(viewModel);
            }
        }
    }
}
