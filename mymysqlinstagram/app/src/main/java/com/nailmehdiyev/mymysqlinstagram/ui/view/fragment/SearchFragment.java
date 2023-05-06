package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSearchBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.adapter.UserAdapterSearch;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Searchviewmodel;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SearchFragment extends Fragment {

    private Searchviewmodel searchviewmodel;

    private FragmentSearchBinding fragmentSearchBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchviewmodel = new ViewModelProvider(this).get(Searchviewmodel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);


        fragmentSearchBinding.setSearchfragment(this);

        searchviewmodel.userlistviewmodel().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                UserAdapterSearch userAdapterSearch = new UserAdapterSearch(requireContext(), users);

                fragmentSearchBinding.setUserAdapter(userAdapterSearch);


            }
        });


        searchviewmodel.usernamefilterbyname(fragmentSearchBinding.frgSearchTxtUserName.getText().toString().trim());


        return fragmentSearchBinding.getRoot();

    }



    public void onSearchTextChanged(String text) {

        searchviewmodel.usernamefilterbyname(text);

    }
}