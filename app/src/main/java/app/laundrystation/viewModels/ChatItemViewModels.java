package app.laundrystation.viewModels;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import app.laundrystation.R;
import app.laundrystation.models.chat.Message;
import app.laundrystation.models.notifications.Notifications;

public class ChatItemViewModels extends BaseObservable {
    Message messages;
    public String userImage;

    public ChatItemViewModels(Message messages) {
        this.messages = messages;
    }

    @Bindable
    public String getMessage() {
        return !TextUtils.isEmpty(messages.getContent()) ? messages.getContent() : "";
    }


    @BindingAdapter({"chatUserImage"})
    public static void loadImage(ImageView view, String profileImage) {
        if (!profileImage.equals("")) {
            Picasso.get().load(profileImage).placeholder(R.drawable.profile_holder).into(view);
        }
    }

    @Bindable
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
