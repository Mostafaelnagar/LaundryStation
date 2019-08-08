package app.laundrystation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.laundrystation.R;
import app.laundrystation.databinding.FragmentNotificationsBinding;
import app.laundrystation.models.notifications.NotificationsRequest;
import app.laundrystation.viewModels.NotificationsViewModels;

public class FragmentNotifications extends Fragment {
    FragmentNotificationsBinding notificationsBinding;
    NotificationsViewModels viewModels;
    RecyclerView recyclerViewNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        notificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        viewModels = new NotificationsViewModels();
        notificationsBinding.setNotificationsViewModels(viewModels);
        initViews();
        viewModels.contextMutableLiveData.setValue(getActivity());
        viewModels.getNotifications();
        viewModels.notificationsRequest.observe(getActivity(), new Observer<NotificationsRequest>() {
            @Override
            public void onChanged(NotificationsRequest notificationsRequest) {
                viewModels.notifyChange();
            }
        });
        return notificationsBinding.getRoot();
    }

    private void initViews() {

        recyclerViewNotifications = notificationsBinding.recNotifications;
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNotifications.setHasFixedSize(true);
    }

}
