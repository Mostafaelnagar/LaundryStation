package app.laundrystation.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentLaundryDetailsBinding;
import app.laundrystation.ui.LaundryLocationActivity;
import app.laundrystation.viewModels.LaundryDetailsViewModel;

public class FragmentLaundryDetails extends Fragment {
    private FragmentLaundryDetailsBinding laundryDetailsBinding;
    private LaundryDetailsViewModel detailsViewModel;

    public FragmentLaundryDetails() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        laundryDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_laundry_details, container, false);
        detailsViewModel = new LaundryDetailsViewModel(getContext());
        laundryDetailsBinding.setLaundryDetailsViewModel(detailsViewModel);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_Laundry_Details, new LaundrySectionsFragment(), "LaundrySectionsFragment");
        fragmentTransaction.commit();
        detailsViewModel.getBackMutableLiveData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    checkFragmentOnBackPressed(getActivity());
                } else if (integer == 2) {
                    Intent intent = new Intent(getActivity(), LaundryLocationActivity.class);
                    intent.putExtra("LaundryName", common.LAUNDRY_DETAILS.getName());
                    intent.putExtra("LaundryLat", common.LAUNDRY_DETAILS.getLat());
                    intent.putExtra("LaundryLng", common.LAUNDRY_DETAILS.getLng());
                    Log.i("onChanged", "onChanged: " + common.LAUNDRY_DETAILS.getLat());
                    startActivity(intent);
                }
            }
        });
        detailsViewModel.getRateMutableLiveData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });
        laundryDetailsBinding.laundryRate.setNumStars(5);
        laundryDetailsBinding.laundryRate.setRating(common.LAUNDRY_DETAILS.getRate());
        return laundryDetailsBinding.getRoot();
    }

    public void ratingDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.waring_item);
        final RatingBar ratingBar = dialog.findViewById(R.id.laundryRate);
        Button negative_Button = dialog.findViewById(R.id.negativeRating);
        Button postive_Button = dialog.findViewById(R.id.postiveRating);
        negative_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        postive_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double rate = ratingBar.getRating();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void checkFragmentOnBackPressed(final Context context) {
        FragmentActivity activity = (FragmentActivity) context;
        String fragmentLaundryDetails = activity.getSupportFragmentManager().findFragmentById(R.id.home_Main_Container).getClass().getSimpleName();
        String fragmentInside = activity.getSupportFragmentManager().findFragmentById(R.id.frame_Laundry_Details).getClass().getSimpleName();
        if (fragmentLaundryDetails.equals("FragmentLaundryDetails") && fragmentInside.equals("LaundrySectionsFragment")
                && SharedPrefManager.getInstance(context).getCart() != null) {
            common.waring_Message(context);
        } else {
            common.removeFragment(new FragmentLaundryDetails(), context);
        }
    }
}
