package com.nailmehdiyev.mymysqlinstagram.ui.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.nailmehdiyev.mymysqlinstagram.data.model.Comment;
import com.nailmehdiyev.mymysqlinstagram.databinding.CommentItemBinding;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.fragment.PostDetailsFragmentDirections;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.PostDetailsviewmodel;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;

import com.squareup.picasso.Picasso;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private MainActivity activity;
    private List<Comment> comments;
    private PostDetailsviewmodel viewModel;

    public CommentAdapter(Context context, MainActivity activity, List<Comment> comments, PostDetailsviewmodel viewModel) {
        this.context = context;
        this.activity = activity;
        this.comments = comments;
        this.viewModel = viewModel;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;

        public CommentViewHolder(@NonNull CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public void navToCommentOwnersProfile(View view, int user_id) {
        PostDetailsFragmentDirections.PostDetailsToProfile postDetailsToProfile = PostDetailsFragmentDirections.postDetailsToProfile();
        postDetailsToProfile.setUserid(user_id);

        Navigation.findNavController(view).navigate(postDetailsToProfile);
    }

    @SuppressLint("NonConstantResourceId")
    public void openPopup(View view, Comment comment) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_poppub_comment, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.actionEditComment:
                    showDialog(comment, true);
                    break;

                case R.id.actionDeleteComment:
                    showDialog(comment, false);
                    break;

                default:
                    return false;
            }

            return true;
        });

        popupMenu.show();
    }

    private void showDialog(Comment comment, boolean open_custom_dialog) { // true = edit, false = delete
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));

        if (open_custom_dialog) { // edit
            View view = activity.getLayoutInflater().inflate(R.layout.custom_edittextdialoq, null);
            EditText txtContent = view.findViewById(R.id.dialogEditTextContent);
            txtContent.setText(comment.getCommenttext());

            builder.setView(view);
            builder.setNegativeButton(context.getResources().getString(R.string.btn_cancel), null);
            builder.setPositiveButton(context.getResources().getString(R.string.btn_edit), (dialog, which) -> {
                String newText = txtContent.getText().toString().trim();
                if (newText.isEmpty())
                    Toast.makeText(context, context.getResources().getString(R.string.dialog_edit_text_msg_text_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    comment.setCommenttext(newText);
                    viewModel.updateComment(comment);
                }
            });
        } else { // delete
            builder.setMessage(context.getResources().getString(R.string.card_comment_msg_delete));
            builder.setNegativeButton(context.getResources().getString(R.string.btn_no), null);
            builder.setPositiveButton(context.getResources().getString(R.string.btn_yes), (dialog, which) -> viewModel.deleteComment(comment));
        }

        builder.create().show();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.binding.setCommentAdapter(this);
        holder.binding.setComment(comment);

        // load photo
        Picasso.get().load(ApiUtils.Baseurl + context.getResources().getString(R.string.dir_profile_photos) + comment.getCommentowner().getUserphoto()).into(holder.binding.cardCommentImgUserPhoto);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}