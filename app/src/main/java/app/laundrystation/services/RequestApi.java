package app.laundrystation.services;


import app.laundrystation.models.chat.ChatRequest;
import app.laundrystation.models.cities.CityRequest;
import app.laundrystation.models.laundries.Request_Details;
import app.laundrystation.models.notifications.NotificationsRequest;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.models.orders.OrderRequest;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.models.settings.SettingsResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RequestApi {


    @FormUrlEncoded
    @POST("api/auth/register")
    Call<ReqDetailsModel> addNewUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("notification_token") String notification_token,
            @Field("device_id") String device_id,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ReqDetailsModel> signIn(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/auth/code-confirmation")
    Call<ReqDetailsModel> confirmation_Code(
            @Field("phone") String phone,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("api/auth/reset-password")
    Call<ReqDetailsModel> send_Code(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("api/auth/change-password")
    Call<ReqDetailsModel> change_Password(
            @Field("phone") String phone,
            @Field("new_password") String password,
            @Field("new_password_confirmation") String new_password_confirmation
    );


    @GET("api/laundries")
    Call<Request_Details> getLaundries(
            @Query("has_offer") Integer has_offer,
            @Query("lat") Double lat,
            @Query("lng") Double lng,
            @Query("per_page") int per_page
    );

    @Multipart
    @POST("api/profile/update-info")
    Call<ReqDetailsModel> updateUserInfo(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part img
    );

    @POST("api/profile/update-password")
    Call<ReqDetailsModel> updateUserPassword(
            @Query("old_password") String old_password,
            @Query("new_password") String new_password,
            @Query("new_password_confirmation") String password
    );

    @POST("api/profile/add-address")
    Call<ReqDetailsModel> addNewAddress(
            @Query("lat") Double lat,
            @Query("lng") Double lng,
            @Query("address") String address
    );

    @POST("api/profile/update-notification-token")
    Call<CreateOrder> updateToken(
            @Query("notification_token") String token
    );

    @POST("api/orders")
    Call<CreateOrder> createOrder(
            @Query("services_id") String services_id,
            @Query("services_name") String services_name,
            @Query("services_type") String services_type,
            @Query("services_count") String services_count,
            @Query("services_price") String services_price,
            @Query("city") String city,
            @Query("delivery") int delivery,
            @Query("address") String address,
            @Query("laundry_id") int laundry_id,
            @Query("lat") String lat,
            @Query("lng") String lng
    );

    @GET("api/orders")
    Call<OrderRequest> getMyOrders();

    @GET("api/profile/get-notification")
    Call<NotificationsRequest> getNotifications();

    //contact Forum
    @POST("api/suggestions/add")
    Call<CreateOrder> AddContactForum(
            @Query("title") String title,
            @Query("details") String details

    );

    //get Cities
    @GET("api/cities")
    Call<CityRequest> getCities();

    //Cancel Orders
    @POST("api/orders/cancel")
    Call<CreateOrder> cancelOrders(
            @Query("order_id") int order_id,
            @Query("reason") String reason
    );

    @POST("api/chat/send")
    Call<ChatRequest> sendMessage(
            @Query("order_id") int order_id,
            @Query("sender_id") int sender_id,
            @Query("target_id") int target_id,
            @Query("message") String message
    );
    @GET("api/settings")
    Call<SettingsResponse> getSettings();
}
