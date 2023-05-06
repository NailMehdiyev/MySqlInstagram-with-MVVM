package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentProfilBinding;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.Profiladapterpost;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Profilviewmodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfilFragment extends Fragment {

    private Profilviewmodel profilviewmodel;

    private FragmentProfilBinding fragmentProfilBinding;
    private int userid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profilviewmodel = new ViewModelProvider(this).get(Profilviewmodel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfilBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false);

        fragmentProfilBinding.setProfileFragment(this);


        ProfilFragmentArgs profilFragmentArgs = ProfilFragmentArgs.fromBundle(getArguments());//  sharedpreferences funcktion get id data after send here

        //userid = Session.MyActiveUser.getUserid();

        if (profilFragmentArgs.getUserid() == 0) {

            userid=Session.MyActiveUser.getUserid();
            Toast.makeText(getContext(), String.valueOf(userid), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "correct way", Toast.LENGTH_SHORT).show();
            userid = profilFragmentArgs.getUserid();
        }


        fragmentProfilBinding.frgProfileSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                profilviewmodel.getPostsfilterbyuserid(userid);
                profilviewmodel.getuserdetailsbyuserid(userid);

                fragmentProfilBinding.frgProfileSwipeRefresh.setRefreshing(false);
            }
        });


        profilviewmodel.getpostviewmodel().observe(getViewLifecycleOwner(), posts -> {

            fragmentProfilBinding.setPostCount(posts.size());

            Profiladapterpost profiladapterpost = new Profiladapterpost(getContext(),posts);

            fragmentProfilBinding.setPostAdapter(profiladapterpost);

            fragmentProfilBinding.frgProfileRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        });


        profilviewmodel.getuserviewmodel().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                fragmentProfilBinding.setUser(user);

                if (user.getUserphoto().equals("default.png")) {
                    fragmentProfilBinding.frgProfileImgUserPhoto.setImageResource(R.drawable.ic_image);
                } else {
                    Picasso.get().load(ApiUtils.Baseurl + getContext().getResources().getString(R.string.dir_profile_photos) + user.getUserphoto()).into(fragmentProfilBinding.frgProfileImgUserPhoto);

                }

                if (user.getUserprofileprivate() == 1 && user.getUserid() != Session.MyActiveUser.getUserid()) {

                    fragmentProfilBinding.frgProfileRecyclerView.setVisibility(View.GONE);
                    fragmentProfilBinding.frgProfileLblMsgPrivate.setVisibility(View.VISIBLE);
                } else {
                    fragmentProfilBinding.frgProfileRecyclerView.setVisibility(View.VISIBLE);
                    fragmentProfilBinding.frgProfileLblMsgPrivate.setVisibility(View.GONE);


                }
            }
        });

        profilviewmodel.getPostsfilterbyuserid(userid);
        profilviewmodel.getuserdetailsbyuserid(userid);


        return fragmentProfilBinding.getRoot();
    }


    public void showProfilePrivateError() {
        Toast.makeText(requireContext(), getString(R.string.fragment_profile_msg_private), Toast.LENGTH_SHORT).show();
    }


//    public void follow(User user) {
//
//        profilviewmodel.follow(user.getUserid());
//        profilviewmodel.getuserdetailsbyuserid(user.getUserid());
//    }

//    public void unfollow(User user) {
//
//        profilviewmodel.unfollow(user.getUserid());
//        profilviewmodel.getuserdetailsbyuserid(user.getUserid());
//    }




    public void follow(User user) {
        profilviewmodel.follow(user.getUserid());

        // update ui
        Session.MyActiveUser.getFollowing().add(user);
        profilviewmodel.getuserdetailsbyuserid(user.getUserid());
    }




    public void unfollow(int user_id) {
        profilviewmodel.unfollow(user_id);

        // update ui
        Iterator<User> iter = Session.MyActiveUser.getFollowing().iterator();
        while (iter.hasNext()) {
            User u = iter.next();

            if (u.getUserid() == user_id) {
                iter.remove();
            }
        }
        profilviewmodel.getuserdetailsbyuserid(user_id);
    }




    public void navToFollow(View view, List<User> userlist) {

        //naviqationla followfragmente toxunanda followerlisti ozuyle aparir followerfragmente  setusers ederek .qarsi  terefdende args ise onu alir

        ProfilFragmentDirections.ProfileToFollow profileToFollow = ProfilFragmentDirections.profileToFollow(userlist.toArray(new User[0]));


        Navigation.findNavController(view).navigate(profileToFollow);
    }


}