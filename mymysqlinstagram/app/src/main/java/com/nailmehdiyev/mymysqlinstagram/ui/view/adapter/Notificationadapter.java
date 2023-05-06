package com.nailmehdiyev.mymysqlinstagram.ui.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nailmehdiyev.mymysqlinstagram.data.model.Notification;
import com.nailmehdiyev.mymysqlinstagram.databinding.CardNotificationBinding;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;


import com.nailmehdiyev.mymysqlinstagram.ui.view.fragment.NotificationFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.NotificationViewHolder> {
    private Context context;
    private List<Notification> notifications;

    public Notificationadapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardNotificationBinding binding;

        public NotificationViewHolder(@NonNull CardNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void navToResource(View view, Notification notification) {
        if (notification.getUser() != null) { // nav to user profile
            NotificationFragmentDirections.NotificationsToProfile notificationsToProfile = NotificationFragmentDirections.notificationsToProfile();
            notificationsToProfile.setUserid(notification.getUser().getUserid());

            Navigation.findNavController(view).navigate(notificationsToProfile);
        } else { // nav to post details
            NotificationFragmentDirections.NotificationsToPostDetails notificationsToPostDetails = NotificationFragmentDirections.notificationsToPostDetails();
            notificationsToPostDetails.setPostid(notification.getPost().getPostid());

            Navigation.findNavController(view).navigate(notificationsToPostDetails);
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardNotificationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_notification, parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.binding.setNotificationAdapter(this);
        holder.binding.setNotification(notification);

        // load photo
        if (notification.getUser() != null) { // profile photo
            Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.dir_profile_photos) + notification.getUser().getUserphoto()).into(holder.binding.cardNotificationImgNotificationPhoto);
        } else { // post photo
            Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.uploading) + notification.getPost().getPostphoto()).into(holder.binding.cardNotificationImgNotificationPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}