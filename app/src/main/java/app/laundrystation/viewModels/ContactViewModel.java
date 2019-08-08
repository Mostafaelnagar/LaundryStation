package app.laundrystation.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrystation.common.common;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.ContactFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public String ContactForum, ContactForumTitle;

    public ContactViewModel() {
        contextMutableLiveData = new MutableLiveData<>();
    }


    public void sendForum() {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().AddContactForum(getContactForumTitle(), getContactForum()).enqueue(new Callback<CreateOrder>() {
            @Override
            public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    spotsDialog.dismiss();
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrder> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    public void back(){
    common.removeFragment(new ContactFragment(),contextMutableLiveData.getValue());
}
    @Bindable
    public String getContactForum() {
        return ContactForum;
    }

    @Bindable
    public String getContactForumTitle() {
        return ContactForumTitle;
    }

    public void setContactForumTitle(String contactForumTitle) {
        ContactForumTitle = contactForumTitle;
        notifyChange();
    }

    public void setContactForum(String contactForum) {
        ContactForum = contactForum;
        notifyChange();
    }
}
