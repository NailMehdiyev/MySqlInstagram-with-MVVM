package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSettingsBinding;
 import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Settingsviewmodel;

import com.squareup.picasso.Picasso;

import java.io.File;

import javax.xml.transform.Result;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;

    private Settingsviewmodel settingsviewmodel;

    private File file;
    private ActivityResultLauncher<Intent> selectImageResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        settingsviewmodel = new ViewModelProvider(this).get(Settingsviewmodel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        fragmentSettingsBinding.setSettingsFragment(this);


        Picasso.get().load(ApiUtils.Baseurl + getString(R.string.dir_profile_photos) + Session.MyActiveUser.getUserid()).into(fragmentSettingsBinding.frgSettingsImgProfilePhoto);


        selectImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == Activity.RESULT_OK) {

                    if (result.getData() != null) {

                        Uri uri = result.getData().getData();

                        fragmentSettingsBinding.frgSettingsImgProfilePhoto.setImageURI(uri);

                        String path = uri.getPath().substring(5);

                        file = new File(path);


                    }

                }

            }
        });


        settingsviewmodel.getmesaggeviewvmodel().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.isEmpty()) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });


        settingsviewmodel.getStatusviewvmodel().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean != null) {

                    if (aBoolean) {


                        Picasso.get().load(ApiUtils.Baseurl + getString(R.string.dir_profile_photos) + getString(R.string.default_photo)).into(fragmentSettingsBinding.frgSettingsImgProfilePhoto);
                        fragmentSettingsBinding.frgSettingsLblRemovePhoto.setVisibility(View.GONE);

                    } else
                        fragmentSettingsBinding.frgSettingsLblRemovePhoto.setVisibility(View.VISIBLE);
                }

            }
        });

        return fragmentSettingsBinding.getRoot();
    }


    public void changeProfilePhoto() {

        if (file != null) {


            settingsviewmodel.userchangeprofilphoto(file);

        } else {

            Toast.makeText(requireContext(), getString(R.string.fragment_new_post_msg_select_image), Toast.LENGTH_SHORT).show();
        }

    }

    public void updateUser(String user_email, String user_password, String user_fullname, String user_bio, int profile_private) {
        if (user_email.isEmpty())
            Toast.makeText(requireContext(), getString(R.string.fragment_settings_msg_email_can_not_be_empty), Toast.LENGTH_SHORT).show();

        User user = Session.MyActiveUser;
        user.setUseremail(user_email);
        user.setUserpassword(user_password);
        user.setUserfullname(user_fullname);
        user.setUserbio(user_bio);
        user.setUserprofileprivate(profile_private);

        settingsviewmodel.userupdate(user);
    }


    public void removeProfilePhoto(View view) {

        Snackbar.make(view, getString(R.string.fragment_settings_msg_remove_photo), Toast.LENGTH_SHORT).setAction(getString(R.string.btn_yes), v -> settingsviewmodel.removeoldphoto()).show();

    }


    @SuppressLint("IntentReset")

    public void selectimage() {

        if (check()) {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            intent.setType("image/*");

            selectImageResultLauncher.launch(intent);

        }

    }

    private boolean check() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
        return true;

    }
}