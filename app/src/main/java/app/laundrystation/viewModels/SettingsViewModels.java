package app.laundrystation.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
 import app.laundrystation.common.common;
import app.laundrystation.models.settings.SettingsResponse;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.AboutFragment;
import app.laundrystation.ui.fragments.TermsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModels extends BaseObservable {
     public MutableLiveData<Context> contextMutableLiveData;
    public String about, terms;

    public SettingsViewModels() {
         contextMutableLiveData = new MutableLiveData<>();
     }

    private void getSettings() {
//        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
//        spotsDialog.show();
        ServiceGenerator.getRequestApi().getSettings().enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.body().getStatus() == 200) {
//                    spotsDialog.dismiss();
                     setAbout(response.body().getData().getAbout());
                    setTerms(response.body().getData().getTerms());
                    Log.i("onResponse", "onResponse: " + getAbout());

                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                notifyChange();

            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
//                spotsDialog.dismiss();
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    public void back() {
        common.removeFragment(new AboutFragment(), contextMutableLiveData.getValue());
    }

    public void backTerms() {
        common.removeFragment(new TermsFragment(), contextMutableLiveData.getValue());
    }

    @Bindable
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;

    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
