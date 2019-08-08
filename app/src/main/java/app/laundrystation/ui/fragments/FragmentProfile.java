package app.laundrystation.ui.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.laundrystation.R;
import app.laundrystation.common.common;
import app.laundrystation.databinding.CancelOrderItemBinding;
import app.laundrystation.databinding.FragmentProfileBinding;
import app.laundrystation.databinding.UpdateUserAddressItemBinding;
import app.laundrystation.filesutils.FileOperations;
import app.laundrystation.models.orders.CreateOrder;
import app.laundrystation.services.ServiceGenerator;
import app.laundrystation.ui.HomeActivity;
import app.laundrystation.ui.SelectLocationActivity;
import app.laundrystation.viewModels.UserProfileViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    FragmentProfileBinding profileBinding;
    UserProfileViewModel profileViewModel;
    private final int SELECT_PHOTO = 1;
    String address;
    Dialog dialog;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment__profile, container, false);
        profileViewModel = new UserProfileViewModel(getActivity());
        profileBinding.setUserProfileViewModel(profileViewModel);
        profileViewModel.addressResult.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                showAddresDialog();
            }
        });
        profileViewModel.ImgeResult.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        // start picker to get image for cropping and then use the image in cropping activity
                        bringImagePicker();
                    }
                } else {
                    bringImagePicker();

                }

            }
        });
        return profileBinding.getRoot();
    }

    private void showAddresDialog() {
        dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final UpdateUserAddressItemBinding typeItemBinding = (UpdateUserAddressItemBinding) DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.update_user_address_item, null, false);
        dialog.setContentView(typeItemBinding.getRoot());

        typeItemBinding.pickUpLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = typeItemBinding.ProfileAddress.getText().toString();
                if (!address.equals("")) {
                    Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                    startActivityForResult(intent, common.REQUEST_CODE);
                    profileViewModel.notifyChange();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    void bringImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == common.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                double lat = data.getDoubleExtra("lat", 0.0);
                double lng = data.getDoubleExtra("lang", 0.0);
                profileViewModel.addNewAddress(address, lat, lng);
                profileViewModel.notifyChange();
                address = "";
                dialog.dismiss();
            }

        }
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                // Set the image in ImageView
                String img = FileOperations.getVolleyFileObject(getActivity(), data, "IMAGENAME", FileOperations.FILE_TYPE_IMAGE).getFilePath();
                profileViewModel.imgUri.setValue(Uri.parse(img));
                profileViewModel.imgeResp = img;
                profileViewModel.notifyChange();

            }

        }
    }


}

