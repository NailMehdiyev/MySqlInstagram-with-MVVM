package com.nailmehdiyev.mymysqlinstagram.ui.view.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.nailmehdiyev.mymysqlinstagram.ui.view.fragment.HomeFragmentDirections;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Homeviewmodel;

import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;


import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;


public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.PostViewHolder> {
    private Context context;
    private MainActivity activity;
    private List<Post> posts, postsSaved;
    private Homeviewmodel viewModel;

    public HomePostAdapter(Context context, MainActivity activity, List<Post> posts, List<Post> postsSaved, Homeviewmodel viewModel) {
        this.context = context;
        this.activity = activity;
        this.posts = posts;
        this.postsSaved = postsSaved;
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
        HomeFragmentDirections.Hometoprofile feedToProfile = HomeFragmentDirections.hometoprofile();
        feedToProfile.setUserid(user_id);

        Navigation.findNavController(view).navigate(feedToProfile);
    }

    public void navToPostDetails(View view, int post_id) {
        HomeFragmentDirections.HomeToPostDetails feedToPostDetails = HomeFragmentDirections.homeToPostDetails();
        feedToPostDetails.setPostid(post_id);

        Log.wtf("Insaf",String.valueOf(post_id));

        Navigation.findNavController(view).navigate(feedToPostDetails);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void likePost(int post_id) {
        viewModel.like(post_id);

        // update ui
        viewModel.getPostsfilterbyfollowingid();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unlikePost(int post_id) {
        viewModel.unlike(post_id);

        // update ui
        viewModel.getPostsfilterbyfollowingid();
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
    public void savePost(int post_id) {
        viewModel.save(post_id);

        // update ui
        viewModel.getSavedPosts();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unsavePost(int post_id) {
        viewModel.unsave(post_id);

        // update ui
        viewModel.getSavedPosts();
    }

    public boolean isPostSaved(Post post) {
        Iterator<Post> iter = postsSaved.iterator();
        while (iter.hasNext()) {
            Post p = iter.next();

            if (p.getPostid().equals(post.getPostid())) {
                return true;
            }
        }

        return false;
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


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_post, parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.binding.setPostAdapterFeed(this);
        holder.binding.setPost(post);

        // load photo

//        if(post.getPostowner().getUserphoto().equals("default.png") && post.getPostphoto()==null){
//
//            holder.binding.cardPostImgUserPhoto.setImageResource(R.drawable.ic_user);
//            holder.binding.cardPostImgPostImage.setImageResource(R.drawable.ic_image);

        //}else{
        Picasso.get().load(ApiUtils.Baseurl +  post.getPostowner().getUserphoto()).into(holder.binding.cardPostImgUserPhoto);
        Picasso.get().load(ApiUtils.Baseurl + post.getPostphoto()).into(holder.binding.cardPostImgPostImage);
      //  }



        Log.i("Serhatim",ApiUtils.Baseurl + post.getPostphoto());


    //    holder.binding..cardPostImgPostImage



        // make bold the username in description
        SpannableString ss = new SpannableString(post.getPostowner().getUsername() + " " + post.getPostdescription());
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, post.getPostowner().getUsername().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.binding.cardPostLblPostDescription.setText(ss);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    private void showDialog(Post post, boolean open_custom_dialog) { // true = edit, false = delete
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));

        if (open_custom_dialog) { // edit
            View view = activity.getLayoutInflater().inflate(R.layout.custom_edittextdialoq, null);
            EditText txtContent = view.findViewById(R.id.dialogEditTextContent);
            txtContent.setText(post.getPostdescription());

            builder.setView(view);
            builder.setNegativeButton("CANCEL", null);
            builder.setPositiveButton("EDIT", (dialog, which) -> {
                String newText = txtContent.getText().toString().trim();
                if (newText.isEmpty()) Toast.makeText(context, context.getResources().getString(R.string.dialog_edit_text_msg_text_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    post.setPostdescription(newText);
                    viewModel.updatepost(post);
                    viewModel.getPostsfilterbyfollowingid();
                }
            });


            AlertDialog alert = builder.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            //Set negative button background
            nbutton.setBackgroundColor(Color.MAGENTA);
            //Set negative button text color
            nbutton.setTextColor(Color.YELLOW);
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            //Set positive button background
            pbutton.setBackgroundColor(Color.YELLOW);
            //Set positive button text color
            pbutton.setTextColor(Color.MAGENTA);
        } else { // delete
            builder.setMessage(context.getResources().getString(R.string.card_post_msg_delete));
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.deletepost(post.getPostid());
                            viewModel.getPostsfilterbyfollowingid();
                        }
                    });


            builder.setNegativeButton( "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });




            AlertDialog alert = builder.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            //Set negative button background
            nbutton.setBackgroundColor(Color.MAGENTA);
            //Set negative button text color
            nbutton.setTextColor(Color.YELLOW);
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            //Set positive button background
            pbutton.setBackgroundColor(Color.YELLOW);
            //Set positive button text color
            pbutton.setTextColor(Color.MAGENTA);





//            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onShow(DialogInterface dialog) {
//                  //  alertDialog.getWindow().setBackgroundDrawableResource(R.color.md_red_A200);
//
//                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context.getApplicationContext(), R.color.md_blue_300));
//                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context.getApplicationContext(), R.color.black));
//
//                }
//            });
//
//
//            alertDialog.show();



        }


        //builder.show();
    }

}