package app.laundrystation.viewModels;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import app.laundrystation.models.notifications.Notifications;

public class NotificationsItemViewModels extends BaseObservable {
    Notifications notifications;

    public NotificationsItemViewModels(Notifications notifications) {
        this.notifications = notifications;
    }

    @Bindable
    public String getDate() {
        return !TextUtils.isEmpty(notifications.getCreatedAt()) ? notifications.getCreatedAt() : "";
    }

    @Bindable
    public String getTitle() {
        return !TextUtils.isEmpty(notifications.getTitle()) ? notifications.getTitle() : "";
    }

    @Bindable
    public String getBody() {
        return !TextUtils.isEmpty(notifications.getBody()) ? notifications.getBody() : "";
    }



}
