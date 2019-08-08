package app.laundrystation.ui.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import app.laundrystation.R;
import app.laundrystation.common.ConnectivityReceiver;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.RealTimeReceiver;
import app.laundrystation.databinding.FragmentChatBinding;
import app.laundrystation.models.chat.Message;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.viewModels.ChatViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements RealTimeReceiver.MessageReceiverListener {
    FragmentChatBinding chatBinding;
    ChatViewModel chatViewModel;
    OrderDetail orderDetail;

    public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        chatViewModel = new ChatViewModel(orderDetail);
        chatViewModel.contextMutableLiveData.setValue(getActivity());
        chatBinding.setChatViewModel(chatViewModel);
        return chatBinding.getRoot();
    }


    @Override
    public void onMessageChanged(String msg) {
         chatViewModel.chatDetails.add(new Message(msg, "delegate"));
        chatViewModel.notifyChange();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setMessageReceiverListener(this);
    }
}
