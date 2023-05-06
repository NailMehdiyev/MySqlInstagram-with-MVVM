package com.nailmehdiyev.mymysqlinstagram.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.databinding.CardProfilpostBinding;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;


import com.nailmehdiyev.mymysqlinstagram.ui.view.fragment.ProfilFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Profiladapterpost extends RecyclerView.Adapter<Profiladapterpost.PostThumbnailViewHolder> {
    private Context context;
    private List<Post> posts;

    public Profiladapterpost(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    public class PostThumbnailViewHolder extends RecyclerView.ViewHolder {
        public CardProfilpostBinding binding;

        public PostThumbnailViewHolder(@NonNull CardProfilpostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void navToPostDetails(View view, int post_id) {
        ProfilFragmentDirections.ProfileToPostDetails profileToPostDetails = ProfilFragmentDirections.profileToPostDetails();
        profileToPostDetails.setPostid(post_id);

        Navigation.findNavController(view).navigate(profileToPostDetails);
    }

    @NonNull
    @Override
    public PostThumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardProfilpostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_profilpost, parent, false);
        return new PostThumbnailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostThumbnailViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.binding.setPostAdapterProfile(this);
        holder.binding.setPost(post);

        // load photo
        Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.uploading) + post.getPostphoto()).into(holder.binding.cardPostThumbnailImgPostPhoto);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}