package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.models.laundries.Laundry_Details;
import app.laundrystation.ui.fragments.FragmentLaundryDetails;

public class LaundryItemViewModel extends BaseObservable {
    private Laundry_Details laundry_details;
    private int saleVisibility;
    private int position;
    private Context context;

    private MutableLiveData<Laundry_Details> itemsOperationsLiveListener;

    public LaundryItemViewModel(Laundry_Details laundry_details, int position, Context context) {
        this.laundry_details = laundry_details;
        this.itemsOperationsLiveListener = new MutableLiveData<>();
        this.position = position;
        this.context = context;
    }

    public MutableLiveData<Laundry_Details> getItemsOperationsLiveListener() {
        return itemsOperationsLiveListener;
    }

    public String getLaundryImage() {
        return String.valueOf(!TextUtils.isEmpty(laundry_details.getImg()) ? laundry_details.getImg() : context.getResources().getDrawable(R.mipmap.background));
    }

    @BindingAdapter({"bind:laundryImage"})
    public static void loadImage(ImageView view, String laundryImage) {
        Picasso.get()
                .load(laundryImage)
                .placeholder(R.color.overlayBackground)
                .into(view);
    }

    @Bindable
    public String getName() {
        return !TextUtils.isEmpty(laundry_details.getName()) ? laundry_details.getName() : "";
    }

    public Laundry_Details getLaundry_details() {
        return laundry_details;
    }

    @Bindable
    public int getSaleVisibility() {
        saleVisibility = laundry_details.getHasOffers();
        return saleVisibility != 0 ? View.VISIBLE : View.GONE;
    }

    public void setSaleVisibility(int saleVisibility) {
        this.saleVisibility = saleVisibility;
    }

    public void performClickAction(Laundry_Details laundry_details) {
        notifyChange();
        itemsOperationsLiveListener.setValue(laundry_details);
        common.LAUNDRY_DETAILS = laundry_details;
        common.confirmation_Page(context, R.id.home_Main_Container, new FragmentLaundryDetails());
    }
}
