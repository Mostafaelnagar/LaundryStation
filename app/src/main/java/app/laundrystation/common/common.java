package app.laundrystation.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.laundrystation.R;
import app.laundrystation.SplashActivity;
import app.laundrystation.models.cities.Cities;
import app.laundrystation.models.laundries.Laundry_Details;
import app.laundrystation.models.laundries.SectionModel;
import app.laundrystation.models.laundries.Service;
import app.laundrystation.models.register.Address;
import app.laundrystation.ui.HomeActivity;
import app.laundrystation.ui.ValidationActivity;
import app.laundrystation.ui.fragments.FragmentLaundryDetails;

public class common {
    public static final Integer REQUEST_CODE = 321;
    public static String token = null;
    public static String device_Id = null;
    public static String code;
    public static Laundry_Details LAUNDRY_DETAILS = null;
    public static String phone_Number;
    public static final String register_msg = "تم التسجيل وإرسال الكود بنجاح";
    public static final String userUpdatePassword = "تم تحديث كلمه المرور بنجاح";
    public static final String chanePasswordMsg = "تم تغير كلمه المرور بنجاح";
    public static final String confirmation_Login_msg = "الحساب غير مفعل";
    public static final String Login__Succuss_msg = "تم تسجيل الدخول بنجاح";
    public static Double lat = 0.0;
    public static Double lng = 0.0;
    public static String address;
    public static Address selectedAddress = new Address();
    public static List<Cities> citiesList = new ArrayList<>();
    List<SectionModel> sectionModels = new ArrayList<>();

    public common() {
        sectionModels.clear();
        sectionModels.add(new SectionModel(1, MyApplication.getInstance().getResources().getString(R.string.man_section), MyApplication.getInstance().getResources().getDrawable(R.drawable.clothes_men)));
        sectionModels.add(new SectionModel(2, MyApplication.getInstance().getResources().getString(R.string.woman_Section), MyApplication.getInstance().getResources().getDrawable(R.drawable.clothes_women)));
        sectionModels.add(new SectionModel(3, MyApplication.getInstance().getResources().getString(R.string.carpet_Section), MyApplication.getInstance().getResources().getDrawable(R.drawable.clothes_carpet)));
        sectionModels.add(new SectionModel(4, MyApplication.getInstance().getResources().getString(R.string.blanket_Section), MyApplication.getInstance().getResources().getDrawable(R.drawable.clothes_blanket)));

    }

    //parsing sections of laundries
    public List<SectionModel> getType(List<Integer> sections) {
        List<SectionModel> models = new ArrayList<>();
        for (SectionModel model : sectionModels) {
            if (sections.contains(model.section_id))
                models.add(model);
        }
        return models;
    }

    public static List<Service> getServices(int section_Id) {
        List<Service> serviceList = new ArrayList<>();
        for (Service model : common.LAUNDRY_DETAILS.getServices()) {
            if (section_Id == model.getSectionId()) {
                serviceList.add(model);
            }
        }
        return serviceList;
    }

    //take three paramter context and type of confirmation (activation or forget password) and phone
    public static void validation_Page(Context context, String code) {
        Intent intent = new Intent(context, ValidationActivity.class);
        intent.putExtra("code", code);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
        ((Activity) context).finish();

    }

    public static void direct_To_Home(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("type", "");
        intent.putExtra("order_id", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
        ((Activity) context).finish();
    }

    //take three paramter context and type of confirmation (activation or forget password) and phone
    public static void confirmation_Page(Context context, int layout_Id, Fragment fragment) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(layout_Id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void removeFragment(Fragment fragment, Context context) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }

    //take three paramter context and type of confirmation (activation or forget password) and phone
    public static void replaceFragment(Context context, int layout_Id, Fragment fragment) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(layout_Id, fragment);
        fragmentTransaction.commit();
    }

    public List<SectionModel> getSectionModels() {
        return sectionModels;
    }

    public static void waring_Message(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.waring_item);
        Button negative_Button = dialog.findViewById(R.id.negative_Button);
        Button postive_Button = dialog.findViewById(R.id.postive_Button);
        negative_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        postive_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.removeFragment(new FragmentLaundryDetails(), context);
                SharedPrefManager.getInstance(context).saveTotal((float) 0.0);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void setSectionModels(List<SectionModel> sectionModels) {
        this.sectionModels = sectionModels;
    }

    public static void restart(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    public static int fragmentCount(Context context) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }
}
