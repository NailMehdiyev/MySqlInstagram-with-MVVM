package com.nailmehdiyev.mymysqlinstagram.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nailmehdiyev.mymysqlinstagram.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}