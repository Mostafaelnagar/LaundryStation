package app.laundrystation.services;

import java.io.IOException;

import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.models.register.UserData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL = "http://laundrystation.my-staff.net/";
    public static String jwt = "";

    public static RequestApi getRequestApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        UserData userData = SharedPrefManager.getInstance(MyApplication.getInstance().getApplicationContext()).getUserData();
        if (userData != null) {
            jwt = userData.getJwt();
        }

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("jwt", jwt)
                        .addHeader("lang", SharedPrefManager.getInstance(MyApplication.getInstance().getApplicationContext()).getCurrentLanguage(MyApplication.getInstance().getApplicationContext())).build();
                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(httpClient.build()).build();

        RequestApi requestApi = retrofit.create(RequestApi.class);
        return requestApi;
    }
}
