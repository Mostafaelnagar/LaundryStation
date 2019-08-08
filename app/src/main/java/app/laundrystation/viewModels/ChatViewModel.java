package app.laundrystation.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.BR;
import app.laundrystation.R;
import app.laundrystation.adapters.ChatAdapter;
import app.laundrystation.common.common;
import app.laundrystation.models.chat.ChatRequest;
import app.laundrystation.models.chat.Message;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.ChatFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public MutableLiveData<ChatRequest> chatRequest;
    public List<Message> chatDetails;
    public ChatAdapter chatAdapter;
    OrderDetail orderDetail;
    public String message, messageHint;

    public ChatViewModel(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        contextMutableLiveData = new MutableLiveData<>();
        chatDetails = new ArrayList<>();
        if (orderDetail.getChat() != null)
            setChatDetails(orderDetail.getChat().getMessages());
        chatAdapter = new ChatAdapter(orderDetail);
        chatRequest = new MutableLiveData<>();
    }

    @BindingAdapter({"app:chatAdapter", "app:chatDetails"})
    public static void bind(RecyclerView recyclerView, ChatAdapter chatAdapter, List<Message> chatDetails) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.updateData(chatDetails);
        if(chatDetails!=null) {
            recyclerView.scrollToPosition(chatDetails.size() - 1);
        }
    }

    public MutableLiveData<ChatRequest> sendMessageRequest() {
        ServiceGenerator.getRequestApi().sendMessage(orderDetail.getId(), orderDetail.getUser().getId(), orderDetail.getDelegateId(), getMessage()).enqueue(new Callback<ChatRequest>() {
            @Override
            public void onResponse(Call<ChatRequest> call, Response<ChatRequest> response) {
                if (response.body().getStatus() == 200) {
                    setMessage("");
                    chatRequest.setValue(response.body());
                    chatDetails = chatRequest.getValue().getData().getMessages();
                     notifyChange();
                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatRequest> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return chatRequest;
    }

    public void back() {
        common.removeFragment(new ChatFragment(), contextMutableLiveData.getValue());
    }

    public void sendMessage() {
        if (!message.equals(""))
            sendMessageRequest();
    }

    @Bindable
    public List<Message> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<Message> chatDetails) {
        this.chatDetails = chatDetails;
    }

    public ChatAdapter getChatAdapter() {
        return chatAdapter;
    }

    public void setChatAdapter(ChatAdapter chatAdapter) {
        this.chatAdapter = chatAdapter;
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getMessageHint() {
        return contextMutableLiveData.getValue().getResources().getString(R.string.chatHint);
    }

    public void setMessageHint(String messageHint) {
        this.messageHint = messageHint;
    }
}
