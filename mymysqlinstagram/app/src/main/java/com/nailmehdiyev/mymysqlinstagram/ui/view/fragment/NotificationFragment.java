package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentNotificationBinding;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentPostBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.Notificationadapter;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Notificationviewmodel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding fragmentNotificationBinding;

    private Notificationviewmodel notificationviewmodel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        notificationviewmodel = new ViewModelProvider(this).get(Notificationviewmodel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);


        fragmentNotificationBinding.setNotificationsFragment(this);


        notificationviewmodel.getnotificationviewmodel().observe(getViewLifecycleOwner(), notifications -> {

            Notificationadapter notificationadapter = new Notificationadapter(getContext(),notifications);

            fragmentNotificationBinding.setNotificationAdapter(notificationadapter);


            notificationviewmodel.markAllNotificationsAsSeen();


            notificationviewmodel.getUnseenNotificationCount().setValue(0);

            if (notifications.size() == 0) {

                fragmentNotificationBinding.frgNotificationsLblMsgNothingInHere.setVisibility(View.VISIBLE);

            }

        });


        notificationviewmodel.getallnotif();


        return fragmentNotificationBinding.getRoot();

    }


    public void fabDeleteOnClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.fragment_notifications_msg_delete));
        builder.setNegativeButton(getString(R.string.btn_no), null);
        builder.setPositiveButton(getString(R.string.btn_yes), (dialog, which) -> notificationviewmodel.deletenotif());
        builder.show();
    }
}