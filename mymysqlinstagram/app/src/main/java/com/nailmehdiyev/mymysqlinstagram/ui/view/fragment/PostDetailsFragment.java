package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.nailmehdiyev.mymysqlinstagram.data.model.Comment;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;

import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentPostDetailsBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.CommentAdapter;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.PostDetailsviewmodel;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;

import com.squareup.picasso.Picasso;


import java.util.List;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.Iterator;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostDetailsFragment extends Fragment {
    private FragmentPostDetailsBinding binding;
    private PostDetailsviewmodel viewModel;

    private List<Post> postsSaved;

    private String editText;
    private int  post_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PostDetailsviewmodel.class);



      }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, container, false);
        binding.setPostDetailsFragment(this);



        PostDetailsFragmentArgs bundle = PostDetailsFragmentArgs.fromBundle(getArguments());
        post_id = bundle.getPostid();

        viewModel.getpostsavelist().observe(getViewLifecycleOwner(), posts -> {
            postsSaved = posts;

            // load post details after saved posts
            // viewModel.getPostDetailsById(post_id);
        });

        viewModel.getpost().observe(getViewLifecycleOwner(), post -> {
            if (post.getPostid() == null) {
                Toast.makeText(requireContext(), getString(R.string.msg_something_went_wrong), Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            } else {
                binding.setPost(post);
                Toast.makeText(requireContext(), "alles gut", Toast.LENGTH_SHORT).show();

                // load photos
                Picasso.get().load(ApiUtils.Baseurl + getString(R.string.dir_profile_photos) + post.getPostowner().getUserphoto()).into(binding.frgPostDetailsImgUserPhoto);
                Picasso.get().load(ApiUtils.Baseurl +  post.getPostphoto()).into(binding.frgPostDetailsImgPostImage);

                // make bold the username in description
                SpannableString ss = new SpannableString(post.getPostowner().getUsername() + " " + post.getPostdescription());
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, post.getPostowner().getUsername().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                binding.frgPostDetailsLblPostDescription.setText(ss);

                // set like & save icons
                if (isPostLiked(post)) binding.frgPostDetailsImgLike.setImageResource(R.drawable.ic_liked);
                else binding.frgPostDetailsImgLike.setImageResource(R.drawable.ic_like);

                if (isPostSaved(post)) binding.frgPostDetailsImgSave.setImageResource(R.drawable.ic_saved);
                else binding.frgPostDetailsImgSave.setImageResource(R.drawable.ic_save);


                // System.out.println(post.getComments());

                if (post.getComments().size()>0){

                    for(Comment a:post.getComments()){

                        Log.wtf("Nail_",String.valueOf(a.getCommentpost()));
                    }

                }else
                    Log.wtf("Nail_", "olmur");

                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);


                binding.frgPostDetailsRecyclerView.setLayoutManager(linearLayoutManager);

                // load comments
                CommentAdapter commentAdapter = new CommentAdapter(requireContext(), (MainActivity) requireActivity(), post.getComments(), viewModel);

                binding.frgPostDetailsRecyclerView.setAdapter(commentAdapter);

            }
        });
        viewModel.getPostDetailsById(post_id);
        viewModel.getSavedPosts();



        binding.frgPostDetailsFabAddComment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               editText=binding.frgPostDetailsTxtComment.getText().toString();
               shareComment(editText,post_id);
           }
       });





        System.out.println(String.valueOf(post_id));


//        binding.frgPostDetailsImgLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(isPostLiked(post_id))
//
//            }
//        });
//

        binding.frgPostDetailsSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.getPostDetailsById(post_id);
            binding.frgPostDetailsSwipeRefresh.setRefreshing(false);
        });

        // post & comment crud messages
        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // return to back after post deleted
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status) {
                requireActivity().onBackPressed();
            }
        });



        return binding.getRoot();
    }

    public void navToPostOwnersProfile(View view, int user_id) {
        PostDetailsFragmentDirections.PostDetailsToProfile postDetailsToProfile = PostDetailsFragmentDirections.postDetailsToProfile();
        postDetailsToProfile.setUserid(user_id);

        Navigation.findNavController(view).navigate(postDetailsToProfile);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void likePost(int post_id) {
        viewModel.like(post_id);

//        if (isPostLiked(post_id)){
//
//        }

        // update ui
        viewModel.getPostDetailsById(post_id);
    }








    @SuppressLint("UseCompatLoadingForDrawables")
    public void unlikePost(int post_id) {
        viewModel.unlike(post_id);

        // update ui
        viewModel.getPostDetailsById(post_id);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void savePost(Post post) {
        viewModel.save(post.getPostid());

        // update ui
        viewModel.getSavedPosts();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
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
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.app_name));

        if (open_custom_dialog) { // edit
            View view = requireActivity().getLayoutInflater().inflate(R.layout.custom_edittextdialoq, null);
            EditText txtContent = view.findViewById(R.id.dialogEditTextContent);
            txtContent.setText(post.getPostdescription());

            builder.setView(view);
            builder.setNegativeButton(getString(R.string.btn_cancel), null);
            builder.setPositiveButton(getString(R.string.btn_edit), (dialog, which) -> {
                String newText = txtContent.getText().toString().trim();
                if (newText.isEmpty()) Toast.makeText(requireContext(), getString(R.string.dialog_edit_text_msg_text_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    post.setPostdescription(newText);
                    viewModel.updatePost(post);
                    viewModel.getPostDetailsById(post.getPostid());
                }
            });
        } else { // delete
            builder.setMessage(getString(R.string.card_post_msg_delete));
            builder.setNegativeButton(getString(R.string.btn_no), null);
            builder.setPositiveButton(getString(R.string.btn_yes), (dialog, which) -> viewModel.deletePost(post.getPostid()));
        }

        builder.create().show();
    }

    public void shareComment( String comment_text, int post_id) {

        if (comment_text.equals("")){
            Toast.makeText(getContext(), getString(R.string.fragment_post_details_msg_comment_cannot_be_empty), Toast.LENGTH_SHORT).show();
        }else {
            viewModel.addComment(comment_text, post_id);
            binding.frgPostDetailsTxtComment.setText("");
        }
    }
}