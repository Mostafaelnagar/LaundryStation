package app.laundrystation.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.laundrystation.models.OrderObject;
import app.laundrystation.models.register.UserData;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_NAMES = "LuandrySharedPref";
    private static final String SHARED_PREF_NAME = "myshared";

    private SharedPrefManager(Context context) {
        mCtx = MyApplication.getInstance();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(UserData userData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        editor.putString("MyObject", json);
        editor.apply();

    }

    public String addUserData() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("MyObject", null);

    }

    public UserData getUserData() {
        Gson gson = new Gson();
        String json = addUserData();

        UserData obj = gson.fromJson(json, UserData.class);
        return obj;
    }

    public boolean loggout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    //add to cart
    public void addTocart(OrderObject orderObject) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<OrderObject> orderObjects = getCart();
        if (orderObjects == null) orderObjects = new ArrayList<>();

        boolean found = false;
        for (int i = 0; i < orderObjects.size(); i++) {

            if (orderObject.getServiceId() == orderObjects.get(i).getServiceId()) {
                orderObjects.get(i).setServiceCount(orderObjects.get(i).getServiceCount() + 1);
                found = true;
                break;
            }
        }
        if (!found)
            orderObjects.add(orderObject);
        String json = gson.toJson(orderObjects);
        editor.putString("OrderObject", json);
        editor.apply();
    }

    public void removeFromCart(int serviceId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<OrderObject> orderObjects = getCart();

        if (orderObjects == null) orderObjects = new ArrayList<>();

        boolean found = false;
        for (int i = 0; i < orderObjects.size(); i++) {

            if (serviceId == orderObjects.get(i).getServiceId()) {
                if (orderObjects.get(i).getServiceCount() > 1) {
                    orderObjects.get(i).setServiceCount(orderObjects.get(i).getServiceCount() - 1);
                } else {
                    orderObjects.remove(i);
                }
                break;
            }
        }


        String json = gson.toJson(orderObjects);
        editor.putString("OrderObject", json);
        editor.apply();

    }

    public String addOrderObject() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("OrderObject", null);
    }

    public List<OrderObject> getCart() {
        Gson gson = new Gson();
        String json = addOrderObject();
        Type type = new TypeToken<List<OrderObject>>() {
        }.getType();
        List<OrderObject> obj = gson.fromJson(json, type);
        return obj;
    }

    public boolean removeCart() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("OrderObject");
        editor.apply();

        return true;
    }

    public void saveToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", token);
        editor.apply();

    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("TOKEN", null);

    }

    public void saveTotal(Float total) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("TOTAL", total);
        editor.apply();

    }

    public float getTotal() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("TOTAL", 0);

    }


    public void setLanguage(Context context, String language) {
        SharedPreferences userDetails = context.getSharedPreferences("languageData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("language", language);
        editor.putBoolean("haveLanguage", true);
        editor.apply();
    }

    public String getCurrentLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("languageData", Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("haveLanguage", false)) return "en";
        return sharedPreferences.getString("language", "en");
    }
}
