package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.HomePostAdapter;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Homeviewmodel;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentHomeBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint

public class HomeFragment extends Fragment {

    public List<Post> postsSaved;
    public FragmentHomeBinding fragmentHomeBinding;
    public Homeviewmodel homeviewmodel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        homeviewmodel = new ViewModelProvider(this).get(Homeviewmodel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        fragmentHomeBinding.fragmentHomeRecyclerViewSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                homeviewmodel.getPostsfilterbyfollowingid();

                fragmentHomeBinding.fragmentHomeRecyclerViewSwipeRefresh.setRefreshing(false);

            }
        });


        homeviewmodel.getPostlistviewmodel().observe(getViewLifecycleOwner(), posts -> {

            HomePostAdapter postAdapter=new HomePostAdapter(requireContext(),(MainActivity)requireActivity(),posts,postsSaved,homeviewmodel);

            fragmentHomeBinding.setPostAdapter(postAdapter);


            if (posts.size() == 0) {
                fragmentHomeBinding.fragmenthomemesaggenopost.setVisibility(View.VISIBLE);
            } else fragmentHomeBinding.fragmenthomemesaggenopost.setVisibility(View.GONE);

        });


        homeviewmodel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


        homeviewmodel.getPostsaveviewmodel().observe(getViewLifecycleOwner(), savedPosts -> {


            postsSaved = savedPosts;

            homeviewmodel.getPostsfilterbyfollowingid();


        });

        homeviewmodel.getSavedPosts();


        return fragmentHomeBinding.getRoot();


    }
}
