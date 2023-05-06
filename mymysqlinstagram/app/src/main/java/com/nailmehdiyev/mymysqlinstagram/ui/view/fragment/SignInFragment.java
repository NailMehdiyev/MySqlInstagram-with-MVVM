package com.nailmehdiyev.mymysqlinstagram.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nailmehdiyev.mymysqlinstagram.databinding.FragmentSignInBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.activity.MainActivity;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Signinviewmodel;

import com.nailmehdiyev.mymysqlinstagram.R;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SignInFragment extends Fragment {

    private FragmentSignInBinding fragmentSignInBinding;

    private Signinviewmodel signinviewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signinviewmodel= new ViewModelProvider(this).get(Signinviewmodel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         fragmentSignInBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false);

         fragmentSignInBinding.setSigninfragment(this);




         signinviewmodel.getmesaggeviewmodel().observe(getViewLifecycleOwner(), new Observer<String>() {
             @Override
             public void onChanged(String s) {

                 if (!s.isEmpty()){
                     Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                 }


             }
         });


         signinviewmodel.getstatusviewmodel().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
             @Override
             public void onChanged(Boolean aBoolean) {

                 if (aBoolean){

                     Intent intent=new Intent(getContext(), MainActivity.class);


                     startActivity(intent);

                     requireActivity().finish();
                 }

             }
         });

         signinviewmodel.getLastSessionUser();


        return  fragmentSignInBinding.getRoot();
    }

    public void signIn(String username,String userpassword){

        if (username.isEmpty() || userpassword.trim().isEmpty()) Toast.makeText(requireContext(), getString(R.string.msg_fill_the_blanks), Toast.LENGTH_SHORT).show();
        else {
            signinviewmodel.signin(username,userpassword);
        }


    }


    public void navToSignUp() {
        Navigation.findNavController(fragmentSignInBinding.frgSignInLblNavToSignUp).navigate(R.id.signInToSignUp);
    }

}

