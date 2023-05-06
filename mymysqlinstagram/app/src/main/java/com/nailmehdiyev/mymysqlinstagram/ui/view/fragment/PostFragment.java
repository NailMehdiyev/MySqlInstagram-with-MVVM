package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import static com.nailmehdiyev.mymysqlinstagram.Realutil.getDataColumn;
import static com.nailmehdiyev.mymysqlinstagram.Realutil.isDownloadsDocument;
import static com.nailmehdiyev.mymysqlinstagram.Realutil.isExternalStorageDocument;
import static com.nailmehdiyev.mymysqlinstagram.Realutil.isGooglePhotosUri;
import static com.nailmehdiyev.mymysqlinstagram.Realutil.isMediaDocument;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Realutil;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentPostBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Postviewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostFragment extends Fragment {

    private ActivityResultLauncher<Intent> imageresultlauncher;

    private ActivityResultLauncher<String[]> permissionlancher;
    private FragmentPostBinding fragmentPostBinding;

    private Postviewmodel postviewmodel;

    private File file;
    private Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        postviewmodel = new ViewModelProvider(this).get(Postviewmodel.class);

//        ActivityCompat.requestPermissions(PostFragment.this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                REQUEST_CODE);





        imageresultlauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {


                    Uri uri=result.getData().getData();

                    fragmentPostBinding.frgNewPostImgSelectPhoto.setImageURI(uri);

                  String path=getRealPathFromURI_API19(getContext(),uri);;


                     file = new File(path);


                }

            }
        });


//        permissionlancher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
//                    @Override
//                    public void onActivityResult(Map<String, Boolean> result) {
//
//                        if (Boolean.TRUE.equals(result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) && Boolean.TRUE.equals(result.get(Manifest.permission.READ_EXTERNAL_STORAGE))) {
//                            Toast.makeText(requireContext(), getString(R.string.msg_permission_granted), Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//
//                            Toast.makeText(requireContext(), getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//
//                }


        // permission
        permissionlancher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (Boolean.TRUE.equals(result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) && Boolean.TRUE.equals(result.get(Manifest.permission.READ_EXTERNAL_STORAGE))) {
                Toast.makeText(requireContext(), getString(R.string.msg_permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPostBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);


        fragmentPostBinding.setNewPostFragment(this);


        // error messages

        postviewmodel.getmessageviewmodel().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


        // nav to feed after post shared
        postviewmodel.getstatusviewmodel().observe(getViewLifecycleOwner(), status -> {
            if (status) {
                Navigation.findNavController(fragmentPostBinding.frgNewPostBtnShare).navigate(R.id.PostToHome);
            }
        });


        return fragmentPostBinding.getRoot();

    }

    public void sharePost(String postDescription) {
        if (!postDescription.isEmpty()) {
            if (file != null) {
                postviewmodel.addpost(file, postDescription);
            } else
                Toast.makeText(requireContext(), getString(R.string.fragment_new_post_msg_select_image), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(requireContext(), getString(R.string.fragment_new_post_msg_description_can_not_be_empty), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("IntentReset")
    public void selectImage() {
        if (check()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");

            imageresultlauncher.launch(intent);
        } else {
            permissionlancher.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }

    private boolean check() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        return true;


    }



    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >=23;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)) {
                    if (split.length > 1) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    } else {
                        return Environment.getExternalStorageDirectory() + "/";
                    }
                    // This is for checking SD Card
                } else {
                    return "storage" + "/" + docId.replace(":", "/");
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                String fileName = getFilePath(context, uri);
                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                }

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }



    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
