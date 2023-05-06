package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.nailmehdiyev.mymysqlinstagram.R;

import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentFollowBinding;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSearchBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.FollowAdapter;


import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FollowFragment extends Fragment {

    private FragmentFollowBinding fragmentFollowBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFollowBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_follow ,container,false);

        fragmentFollowBinding.setFollowfragment(this);

        FollowFragmentArgs followFragmentArgs = FollowFragmentArgs.fromBundle(getArguments());

        followFragmentArgs.getUsers();
        List<User> userList = Arrays.asList(followFragmentArgs.getUsers());

        FollowAdapter followAdapter = new FollowAdapter(getContext(), userList);

        fragmentFollowBinding.setFollowadapter(followAdapter);

        if (userList.size() == 0)
            fragmentFollowBinding.frgFollowLblMsgNothingInHere.setVisibility(View.VISIBLE);



        return fragmentFollowBinding.getRoot();
    }
}