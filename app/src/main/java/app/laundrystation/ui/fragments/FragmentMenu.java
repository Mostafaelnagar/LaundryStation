package app.laundrystation.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import app.laundrystation.R;
import app.laundrystation.SplashActivity;
import app.laundrystation.common.MovementManager;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.databinding.FragmentMenuBinding;
import app.laundrystation.ui.AuthActivity;
import app.laundrystation.viewModels.MenuViewModel;

public class FragmentMenu extends Fragment {
    FragmentMenuBinding menuBinding;
    MenuViewModel menuViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        menuBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        menuViewModel = new MenuViewModel(getActivity());
        menuBinding.setMenuViewModel(menuViewModel);
        menuViewModel.getLogoutMutableLiveData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    waring_Message();
                } else if (integer == 2) {
                    languageChange();
                } else {
                    showGrandDialog();
                }
            }
        });
        return menuBinding.getRoot();
    }

    public void waring_Message() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.log_out_item);
        TextView negative_Button = dialog.findViewById(R.id.reject_LogOut);
        TextView postive_Button = dialog.findViewById(R.id.accept_LogOut);
        negative_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        postive_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
                SharedPrefManager.getInstance(getActivity()).loggout();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void languageChange() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_item);
        Button arButton = dialog.findViewById(R.id.arBtn);
        Button enButton = dialog.findViewById(R.id.enbtn);
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLan("ar");
                dialog.dismiss();
            }
        });
        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLan("en");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeLan(String lang) {
        Resources res = MyApplication.getInstance().getResources();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        SharedPrefManager.getInstance(MyApplication.getInstance()).setLanguage(MyApplication.getInstance(), lang);
        ((Activity) getActivity()).finish();
        common.restart(getActivity());

    }

    private void showGrandDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.grand_dialog);
        ImageView dialogClose = dialog.findViewById(R.id.dialogClose);
        TextView whatsAppClick = dialog.findViewById(R.id.whatsApp);
        final TextView grandPhone = dialog.findViewById(R.id.grandPhone);
        final TextView grandCall = dialog.findViewById(R.id.grandCall);
        final TextView grandSite = dialog.findViewById(R.id.grandSite);
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        grandCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startCall(getActivity(), grandPhone.getText().toString());
            }
        });
        whatsAppClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startWhatsApp(getActivity(), grandPhone.getText().toString());
            }
        });
        grandSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startWebPage(getActivity(), grandCall.getText().toString());
            }
        });

        dialog.show();
    }
}
