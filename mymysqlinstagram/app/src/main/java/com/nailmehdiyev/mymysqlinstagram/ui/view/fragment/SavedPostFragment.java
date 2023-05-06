package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;


import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.PostAdapterSaved;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.SavedPostviewmodel;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSavedPostBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SavedPostFragment extends Fragment {
    private FragmentSavedPostBinding binding;
    private SavedPostviewmodel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SavedPostviewmodel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_post, container, false);

        binding.frgSavedPostsRecyclerViewSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.getSavedPosts();
            binding.frgSavedPostsRecyclerViewSwipeRefresh.setRefreshing(false);
        });

        // post crud messages
        viewModel.getmesaageviewmodel().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getPostsaveviewmodel().observe(getViewLifecycleOwner(), savedPosts -> {
            PostAdapterSaved postAdapter = new PostAdapterSaved(requireContext(), (MainActivity)requireActivity() ,savedPosts,viewModel);
            binding.setPostAdapter(postAdapter);

            if (savedPosts.size() == 0) binding.frgSavedPostsLblMsgNoPost.setVisibility(View.VISIBLE);
            else binding.frgSavedPostsLblMsgNoPost.setVisibility(View.GONE);
        });

        viewModel.getSavedPosts();

        return binding.getRoot();
    }
}