package app.laundrystation.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.adapters.NotificationsAdapter;
import app.laundrystation.models.notifications.Notifications;
import app.laundrystation.models.notifications.NotificationsRequest;
import app.laundrystation.services.ServiceGenerator;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModels extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    private List<Notifications> notificationDetails;
    private NotificationsAdapter notificationAdapter;
    public MutableLiveData<NotificationsRequest> notificationsRequest;

    public NotificationsViewModels() {
        contextMutableLiveData = new MutableLiveData<>();
        notificationDetails = new ArrayList<>();
        notificationAdapter = new NotificationsAdapter();
        notificationsRequest = new MutableLiveData<>();
    }

    @BindingAdapter({"app:notificationAdapter", "app:notificationDetails"})
    public static void bind(RecyclerView recyclerView, NotificationsAdapter notificationAdapter, List<Notifications> notificationDetails) {
        recyclerView.setAdapter(notificationAdapter);
        notificationAdapter.updateData(notificationDetails);
    }

    public MutableLiveData<NotificationsRequest> getNotifications() {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getNotifications().enqueue(new Callback<NotificationsRequest>() {
            @Override
            public void onResponse(Call<NotificationsRequest> call, Response<NotificationsRequest> response) {
                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    notificationsRequest.setValue(response.body());
                    notificationDetails = notificationsRequest.getValue().getData();
                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsRequest> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return notificationsRequest;
    }

    @Bindable
    public List<Notifications> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(List<Notifications> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    @Bindable
    public NotificationsAdapter getNotificationAdapter() {
        return notificationAdapter;
    }

    public void setNotificationAdapter(NotificationsAdapter notificationAdapter) {
        this.notificationAdapter = notificationAdapter;
    }
}
