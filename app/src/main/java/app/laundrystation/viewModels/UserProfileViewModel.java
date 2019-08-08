package app.laundrystation.viewModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;

import app.laundrystation.R;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.common.common;
import app.laundrystation.models.register.ReqDetailsModel;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.fragments.FragmentProfile;
import app.laundrystation.ui.fragments.FragmentResetPassword;
import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileViewModel extends BaseObservable {
    public String userName;
    public String userEmail;
    public String userPhone;
    private Context context;
    public MutableLiveData<Integer> addressResult = new MutableLiveData<>();
    public MutableLiveData<Integer> ImgeResult = new MutableLiveData<>();
    public MutableLiveData<Uri> imgUri = new MutableLiveData<Uri>();
    public String ProfileImage, imgeResp;

    public UserProfileViewModel(Context context) {
        this.context = context;
        this.userName = SharedPrefManager.getInstance(context).getUserData().getName();
        this.userEmail = SharedPrefManager.getInstance(context).getUserData().getEmail();
        this.userPhone = SharedPrefManager.getInstance(context).getUserData().getPhone();
        this.ProfileImage = SharedPrefManager.getInstance(context).getUserData().getImg();
        notifyChange();
    }

    //add new address for user
    public void addNewAddress(String address, double lat, double lng) {
        ServiceGenerator.getRequestApi().addNewAddress(lat, lng, address).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                SharedPrefManager.getInstance(context).userLogin(response.body().getUserData());
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userChangePassword() {
        common.confirmation_Page(context, R.id.home_Main_Container, new FragmentResetPassword());
    }

    public void saveChanges() {
        notifyChange();
        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userName)
                && !TextUtils.isEmpty(userEmail)) {
            updateUserInfo();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }

    }


    private void updateUserInfo() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        File file = null;
        MultipartBody.Part fileReqBody = null;
        if (imgeResp != null) {
            file = new File(imgeResp);
            // Create a request body with file and image media type
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            fileReqBody = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }
        //Create request body with text description and text media type
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), getUserName());
        RequestBody userEmail = RequestBody.create(MediaType.parse("text/plain"), getUserEmail());

        RequestBody userPhone = RequestBody.create(MediaType.parse("text/plain"), getUserPhone());
        ServiceGenerator.getRequestApi().updateUserInfo(username, userEmail, userPhone
                , fileReqBody).enqueue(new Callback<ReqDetailsModel>() {
            @Override
            public void onResponse(Call<ReqDetailsModel> call, Response<ReqDetailsModel> response) {
                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    SharedPrefManager.getInstance(context).userLogin(response.body().getUserData());
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    spotsDialog.dismiss();
                    Toast.makeText(context, "" + response.body().msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReqDetailsModel> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("Repos", "" + t.getMessage());
            }
        });
    }

    public void back() {
        common.removeFragment(new FragmentProfile(), context);
    }

    public void userChangeAddress() {
        addressResult.setValue(common.REQUEST_CODE);
    }


    public String getProfileImage() {
        if (imgUri.getValue() == null) {
            return String.valueOf(!TextUtils.isEmpty(ProfileImage) ? ProfileImage : context.getResources().getDrawable(R.drawable.profile_holder));
        } else {
            return imgUri.getValue().toString();
        }
    }

    @BindingAdapter({"profileImage"})
    public static void loadImage(ImageView view, String profileImage) {
        if (imagePathValid(profileImage)) {
            Picasso.get()
                    .load(profileImage)
                    .placeholder(R.drawable.profile_holder)
                    .into(view);
        } else {
            File imgFile = new File(profileImage);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                view.setImageBitmap(myBitmap);

            }
        }
    }


    public void addImage() {
        ImgeResult.setValue(1);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    @Bindable

    public String getUserEmail() {
        return userEmail;
    }


    @Bindable

    public String getUserPhone() {
        return userPhone;
    }


    public void setUserName(String userName) {
        this.userName = userName;
        notifyChange();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyChange();

    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        notifyChange();

    }
    public static boolean imagePathValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
