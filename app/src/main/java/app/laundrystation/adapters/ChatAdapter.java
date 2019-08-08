package app.laundrystation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.databinding.SendItemBinding;
import app.laundrystation.models.chat.Message;
import app.laundrystation.models.orders.OrderDetail;
import app.laundrystation.viewModels.ChatItemViewModels;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<Message> messages;
    Context context;
    View view;
    OrderDetail orderDetail;

    public ChatAdapter(OrderDetail orderDetail) {
        messages = new ArrayList<>();
        this.orderDetail = orderDetail;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item, parent, false);
        context = parent.getContext();
        this.view = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Message details = messages.get(position);
        final ChatItemViewModels itemViewModel = new ChatItemViewModels(details);

        if (messages.get(position).getSentFrom().equals("user")) {
            holder.itemBinding.sendContainer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            itemViewModel.setUserImage(orderDetail.getUser().getImg());
        } else {
            holder.itemBinding.sendContainer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            itemViewModel.setUserImage(orderDetail.getDelegate().getImg());

        }
        holder.setViewModel(itemViewModel);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        this.orderDetail = orderDetail;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SendItemBinding itemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (itemBinding == null) {
                itemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (itemBinding != null) {
                itemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(ChatItemViewModels viewModel) {
            if (itemBinding != null) {
                itemBinding.setChatItemViewModel(viewModel);
            }
        }
    }
}
