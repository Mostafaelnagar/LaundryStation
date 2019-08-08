package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;

public class LaundryDetailsViewModel extends BaseObservable {
    private Context context;
    public MutableLiveData<Integer> backMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> rateMutableLiveData = new MutableLiveData<>();
    public float Rating;

    public LaundryDetailsViewModel(Context context) {
        this.context = context;
        SharedPrefManager.getInstance(context).removeCart();
    }

    public void back() {
        notifyChange();
        backMutableLiveData.setValue(1);
    }

    public void openLaundryLocationMap() {
        notifyChange();
        backMutableLiveData.setValue(2);
    }

    public void rate() {
        notifyChange();
        backMutableLiveData.setValue(2);
    }

    public MutableLiveData<Integer> getRateMutableLiveData() {
        return rateMutableLiveData;
    }

    public MutableLiveData<Integer> getBackMutableLiveData() {
        return backMutableLiveData;
    }

    @Bindable
    public String getLaundryName() {
        return common.LAUNDRY_DETAILS.getName();
    }

//    @BindingAdapter("android:rating")
//    public void setRating(RatingBar view, float rating) {
//        if (view.getRating() != rating) {
//            view.setRating(rating);
//        }
//        Log.i("setRating", "setRating: " + rating);
//        Log.i("setRating", "setRating: common " + common.LAUNDRY_DETAILS.getRate());
//    }
//
//    @Bindable
//    public float getRating() {
//        return common.LAUNDRY_DETAILS.getRate();
//    }


    @Bindable
    public String getLaundryAddress() {
        return !TextUtils.isEmpty(common.LAUNDRY_DETAILS.getAddress()) ? common.LAUNDRY_DETAILS.getAddress() : "";
    }

    @Bindable
    public String getLaundryDesc() {
        return !TextUtils.isEmpty(common.LAUNDRY_DETAILS.getDescription()) ? common.LAUNDRY_DETAILS.getDescription() : "";
    }

    public String getLaundryImage() {
        // The URL will usually come from a model (i.e Profile)
        return String.valueOf(!TextUtils.isEmpty(common.LAUNDRY_DETAILS.getImg()) ? common.LAUNDRY_DETAILS.getImg() : context.getResources().getDrawable(R.mipmap.background));
    }

    @BindingAdapter({"bind:laundryImage"})
    public static void loadImage(ImageView view, String laundryImage) {
        Picasso.get()
                .load(laundryImage)
                .placeholder(R.color.overlayBackground)
                .into(view);
    }

}
