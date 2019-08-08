package app.laundrystation.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrystation.BR;
import app.laundrystation.adapters.LaundryAdapter;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.cities.Cities;
import app.laundrystation.models.cities.CityRequest;
import app.laundrystation.models.laundries.Laundry_Details;
import app.laundrystation.models.laundries.Request_Details;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.AuthActivity;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Laundries_ViewModels extends BaseObservable {
    private Context context;
    public MutableLiveData<Request_Details> request_details = new MutableLiveData<>();
    public LaundryAdapter laundryAdapter;
    List<Laundry_Details> laundryDetails;
    private int emptyLaundries;


    public Laundries_ViewModels(Context context) {
        this.context = context;
        laundryDetails = new ArrayList<>();
        laundryAdapter = new LaundryAdapter(laundryDetails, context);
    }

    @BindingAdapter({"app:laundryAdapter", "app:laundryDetails"})
    public static void bind(RecyclerView recyclerView, LaundryAdapter laundryAdapter, List<Laundry_Details> laundryDetails) {
        recyclerView.setAdapter(laundryAdapter);
        laundryAdapter.updateData(laundryDetails);
    }

    public MutableLiveData<Request_Details> sendLaundriesRequest(Integer offer, Double lat, Double lng, final int page) {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getLaundries(offer, lat, lng, page).enqueue(new Callback<Request_Details>() {
            @Override
            public void onResponse(Call<Request_Details> call, Response<Request_Details> response) {
                if (response.body().getStatus() == 200) {
                    request_details.setValue(response.body());
                    spotsDialog.dismiss();
                    setEmptyLaundries(request_details.getValue().getData().getData().size());
                } else {
                    if (response.body().getMsg().equals("please enter a valid jwt")) {
                        SharedPrefManager.getInstance(context).loggout();
                        context.startActivity(new Intent(context, AuthActivity.class));
                        ((Activity) context).finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Request_Details> call, Throwable t) {
                spotsDialog.dismiss();

            }
        });
        return request_details;
    }


    public void getLaundries() {
        request_details.observe((LifecycleOwner) context, new Observer<Request_Details>() {
            @Override
            public void onChanged(Request_Details request_details) {
                laundryDetails = request_details.getData().data;
                notifyPropertyChanged(BR.laundryDetails);
            }
        });

    }

    public void search(CharSequence s, int start, int before, int count) {
        laundryAdapter.getFilter().filter(s);
    }

    @Bindable
    public LaundryAdapter getLaundryAdapter() {
        return laundryAdapter;
    }

    @Bindable
    public List<Laundry_Details> getLaundryDetails() {
        return laundryDetails;
    }

    public void setLaundryAdapter(LaundryAdapter laundryAdapter) {
        this.laundryAdapter = laundryAdapter;
    }

    public void setLaundryDetails(List<Laundry_Details> laundryDetails) {
        this.laundryDetails = laundryDetails;
    }

    @Bindable
    public int getEmptyLaundries() {
        return emptyLaundries == 0 ? View.VISIBLE : View.GONE;
    }

    public void setEmptyLaundries(int emptyLaundries) {
        this.emptyLaundries = emptyLaundries;
        notifyPropertyChanged(BR.emptyLaundries);

    }

    public void getCities() {
        ServiceGenerator.getRequestApi().getCities().enqueue(new Callback<CityRequest>() {
            @Override
            public void onResponse(Call<CityRequest> call, Response<CityRequest> response) {
                if (response.body().getStatus() == 200) {
                    common.citiesList = response.body().getData();

                } else {
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityRequest> call, Throwable t) {

            }
        });
    }
}
