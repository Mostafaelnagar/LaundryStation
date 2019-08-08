package app.laundrystation.viewModels;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.models.laundries.SectionModel;

public class LaundrySectionsItemViewModel extends BaseObservable {
    private SectionModel sectionModel;
    public MutableLiveData<SectionModel> itemsOperationsLiveListener;
    public Drawable imageResource;

    public LaundrySectionsItemViewModel(SectionModel sectionModel) {
        this.sectionModel = sectionModel;
        this.itemsOperationsLiveListener = new MutableLiveData<>();
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, Drawable resource) {
        imageView.setImageDrawable(resource);
    }

    @Bindable
    public Drawable getImageResource() {
        imageResource = sectionModel.getImageUrl();
        return imageResource;
    }

    public MutableLiveData<SectionModel> getItemsOperationsLiveListener() {
        return itemsOperationsLiveListener;
    }

    @Bindable
    public String getName() {
         return !TextUtils.isEmpty(sectionModel.getName()) ? sectionModel.getName() : "";
    }

    public SectionModel getSectionModel() {
        return sectionModel;
    }

    public void performClickAction(SectionModel sectionModel) {
        notifyChange();
        itemsOperationsLiveListener.setValue(sectionModel);
    }
}
