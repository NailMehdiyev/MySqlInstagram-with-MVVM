package com.nailmehdiyev.mymysqlinstagram.ui.view.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.databinding.CardPostBinding;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;

import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.fragment.SavedPostFragmentDirections;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.SavedPostviewmodel;

import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;

public class PostAdapterSaved extends RecyclerView.Adapter<PostAdapterSaved.PostViewHolder> {
    private Context context;
    private MainActivity activity;
    private List<Post> posts;
    private SavedPostviewmodel viewModel;

    public PostAdapterSaved(Context context, MainActivity activity, List<Post> posts, SavedPostviewmodel viewModel) {
        this.context = context;
        this.activity = activity;
        this.posts = posts;
        this.viewModel = viewModel;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public CardPostBinding binding;

        public PostViewHolder(@NonNull CardPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void navToPostOwnersProfile(View view, int user_id) {
        SavedPostFragmentDirections.SavedPostsToProfile savedPostsToProfile = SavedPostFragmentDirections.savedPostsToProfile();
        savedPostsToProfile.setUserid(user_id);

        Navigation.findNavController(view).navigate(savedPostsToProfile);
    }

    public void navToPostDetails(View view, int post_id) {
        SavedPostFragmentDirections.SavedPostsToPostDetails savedPostsToPostDetails = SavedPostFragmentDirections.savedPostsToPostDetails();
        savedPostsToPostDetails.setPostid(post_id);

        Navigation.findNavController(view).navigate(savedPostsToPostDetails);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void likePost(int post_id) {
        viewModel.like(post_id);

        // update ui
        viewModel.getSavedPosts();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unlikePost(int post_id) {
        viewModel.unlike(post_id);

        // update ui
        viewModel.getSavedPosts();
    }

    public boolean isPostLiked(Post post) {
        Iterator<User> iter = post.getLikers().iterator();
        while (iter.hasNext()) {
            User u = iter.next();

            if (u.getUserid()==(Session.MyActiveUser.getUserid())) {
                return true;
            }
        }

        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unsavePost(int post_id) {
        viewModel.unsavepost(post_id);

        // update ui
        viewModel.getSavedPosts();
    }

    @SuppressLint("NonConstantResourceId")
    public void openPopup(View view, Post post) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popub_post, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.actionEditPost:
                    showDialog(post, true);
                    break;

                case R.id.actionDeletePost:
                    showDialog(post, false);
                    break;

                default:
                    return false;
            }

            return true;
        });

        popupMenu.show();
    }

    private void showDialog(Post post, boolean open_custom_dialog) { // true = edit, false = delete
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));

        if (open_custom_dialog) { // edit
            View view = activity.getLayoutInflater().inflate(R.layout.custom_edittextdialoq, null);
            EditText txtContent = view.findViewById(R.id.dialogEditTextContent);
            txtContent.setText(post.getPostdescription());

            builder.setView(view);
            builder.setNegativeButton(context.getResources().getString(R.string.btn_cancel), null);
            builder.setPositiveButton(context.getResources().getString(R.string.btn_edit), (dialog, which) -> {
                String newText = txtContent.getText().toString().trim();
                if (newText.isEmpty()) Toast.makeText(context, context.getResources().getString(R.string.dialog_edit_text_msg_text_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    post.setPostdescription(newText);
                    viewModel.updatePost(post);
                    viewModel.getSavedPosts();
                }
            });
        } else { // delete
            builder.setMessage(context.getResources().getString(R.string.card_post_msg_delete));
            builder.setNegativeButton(context.getResources().getString(R.string.btn_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
          //  builder.setNegativeButton(context.getResources().getString(R.string.btn_no), null);

            builder.setPositiveButton(context.getResources().getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewModel.deletePost(post.getPostid());
                    viewModel.getSavedPosts();
                }
            });

        }

        builder.create().show();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_post, parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.binding.setPostAdapterSaved(this);
        holder.binding.setPost(post);

        // load photo
        Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.dir_profile_photos) + post.getPostowner().getUserphoto()).into(holder.binding.cardPostImgUserPhoto);
        Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.uploading) + post.getPostphoto()).into(holder.binding.cardPostImgPostImage);

        // make bold the username in description
        SpannableString ss = new SpannableString(post.getPostowner().getUsername() + " " + post.getPostdescription());
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, post.getPostowner().getUsername().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.binding.cardPostLblPostDescription.setText(ss);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
