package app.laundrystation.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import app.laundrystation.R;
import app.laundrystation.models.OrderObject;

public class MyOrderDetailItemViewModel extends BaseObservable {
    private OrderObject orderDetail;
    private Context context;
    public String serviceImage;

    public MyOrderDetailItemViewModel(OrderObject orderDetail, Context context) {
        this.orderDetail = orderDetail;

        this.context = context;
    }

    @Bindable
    public String getItemName() {
        return !TextUtils.isEmpty(orderDetail.getServiceName()) ? orderDetail.getServiceName() : "";
    }

    @BindingAdapter({"bind:serviceImage"})
    public static void loadImage(ImageView view, String profileImage) {
        if (profileImage != null)
            Picasso.get()
                    .load(profileImage)
                    .placeholder(R.color.overlayBackground)
                    .into(view);
    }

    public String getServiceImage() {
        Log.i("loadImage", "loadImage: "+orderDetail.getImg());

        return !TextUtils.isEmpty(orderDetail.getImg()) ? orderDetail.getImg() : "";
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    @Bindable
    public String getItemCount() {
        String itemCount = "X" + orderDetail.getServiceCount();
        return !TextUtils.isEmpty(itemCount) ? itemCount : "";
    }

    @Bindable
    public String getServiceType() {
        String type = orderDetail.getServiceType();
        if (type.equals("Wash") || type.equals("غسيل")) {
            return context.getResources().getString(R.string.cat_wash);
        } else if (type.equals("Ironing") || type.equals("كى")) {
            return context.getResources().getString(R.string.cat_ironing);
        } else {
            return context.getResources().getString(R.string.cat_wash_ironing);
        }
    }

    @Bindable
    public String getItemPrice() {
        return !TextUtils.isEmpty(orderDetail.getServicePrice()) ? orderDetail.getServicePrice() + context.getResources().getString(R.string.coin) : "";
    }

    public OrderObject getOrderDetail() {
        return orderDetail;
    }

}
