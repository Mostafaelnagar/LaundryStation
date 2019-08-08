package app.laundrystation.viewModels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.laundrystation.adapters.LaundrySectionsAdapter;
import app.laundrystation.models.laundries.SectionModel;

public class LaundrySectionsViewModel extends BaseObservable {
    public List<SectionModel> laundrySections;
    public LaundrySectionsAdapter laundrySectionsAdapter;


    public LaundrySectionsViewModel(List<SectionModel> laundrySections) {
        this.laundrySections = laundrySections;
        laundrySectionsAdapter = new LaundrySectionsAdapter();
        setLaundrySections(laundrySections);
        notifyChange();
    }

    @BindingAdapter({"app:laundrySectionsAdapter", "app:laundrySections"})
    public static void bind(RecyclerView recyclerView, LaundrySectionsAdapter laundrySectionsAdapter, List<SectionModel> laundrySections) {
        recyclerView.setAdapter(laundrySectionsAdapter);
        laundrySectionsAdapter.updateData(laundrySections);
    }

    @Bindable
    public List<SectionModel> getLaundrySections() {
        return laundrySections;
    }

    public void setLaundrySections(List<SectionModel> laundrySections) {
        this.laundrySections = laundrySections;
    }

    @Bindable
    public LaundrySectionsAdapter getLaundrySectionsAdapter() {
        return laundrySectionsAdapter;
    }

    public void setLaundrySectionsAdapter(LaundrySectionsAdapter laundrySectionsAdapter) {
        this.laundrySectionsAdapter = laundrySectionsAdapter;
    }
}
