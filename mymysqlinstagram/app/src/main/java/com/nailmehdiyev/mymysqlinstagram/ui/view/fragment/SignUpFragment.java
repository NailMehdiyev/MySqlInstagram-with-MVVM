package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSignUpBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Signupviewmodel;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding fragmentSignUpBinding;

    private Signupviewmodel signupviewmodel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signupviewmodel = new ViewModelProvider(this).get(Signupviewmodel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        fragmentSignUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        fragmentSignUpBinding.setSignUpFragment(this);

        signupviewmodel.getmesaggeviewmodel().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (!s.isEmpty()) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                    System.out.println(s);
                }

            }
        });


        signupviewmodel.getstatusviewmodel().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        });


        return fragmentSignUpBinding.getRoot();
    }


    public void signUp(String useremail, String username, String userpassword) {
        if (useremail.trim().isEmpty() || username.trim().isEmpty() || userpassword.trim().isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.msg_fill_the_blanks), Toast.LENGTH_SHORT).show();
        } else {
            signupviewmodel.signup(useremail, username, userpassword);

        }


    }

    public void navToSignIn() {
        requireActivity().onBackPressed();
    }
}