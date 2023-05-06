package com.nailmehdiyev.mymysqlinstagram.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.nailmehdiyev.mymysqlinstagram.R;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.databinding.ActivityMainBinding;
import com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel.Mainviewmodel;

import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Context context;

    private ActivityMainBinding activityMainBinding;

    private Mainviewmodel mainviewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        context = MainActivity.this;

        mainviewmodel = new ViewModelProvider(this).get(Mainviewmodel.class);

        initMenu();

        if (Session.MyActiveUser.getUserid() == 0) {

            Intent intent = new Intent(MainActivity.this, SignInActivity.class);

            startActivity(intent);
            finish();
        }


        mainviewmodel.getuserviewmodel().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                ImageView view=activityMainBinding.navigationView.getHeaderView(0).findViewById(R.id.navHeaderImgUserPhoto);

                if(user.getUserphoto().equals("default.png")){
                    view.setImageResource(R.drawable.ic_image);

                }else{
                    Picasso.get().load(ApiUtils.Baseurl + getString(R.string.dir_profile_photos) + user.getUserphoto()).into(view);

                }



            }
        });

        // get notifications
        mainviewmodel.getunseennotfymesaggeviewmodel().observe(this, unseenNotificationCount -> {
            if (unseenNotificationCount > 0) activityMainBinding.bottomNavigationView.getMenu().getItem(3).setIcon(R.drawable.ic_notifications_active);
            else activityMainBinding.bottomNavigationView.getMenu().getItem(3).setIcon(R.drawable.ic_notifications_empty);
        });

        mainviewmodel.getallnotification();

    }


    private void initMenu() {


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);


        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navHostFragment.getNavController());


        activityMainBinding.navigationView.setNavigationItemSelectedListener(this);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawerLayout, activityMainBinding.toolbar, 0, 0);
        activityMainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // load profile picture & username
        View navigationHeader = activityMainBinding.navigationView.inflateHeaderView(R.layout.naviqation_header);
        Picasso.get().load(ApiUtils.Baseurl + getString(R.string.dir_profile_photos) + Session.MyActiveUser.getUserphoto()).into((ImageView) navigationHeader.findViewById(R.id.navHeaderImgUserPhoto));
        ((TextView) navigationHeader.findViewById(R.id.navHeaderLblUserName)).setText(Session.MyActiveUser.getUsername());





        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navHostFragment.getNavController());

        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();


                if (id == R.id.HomeFragment) navHostFragment.getNavController().navigate(R.id.HomeFragment);
                else if (id == R.id.searchFragment) navHostFragment.getNavController().navigate(R.id.searchFragment);
                else if (id == R.id.PostFragment) navHostFragment.getNavController().navigate(R.id.PostFragment);
                else if (id == R.id.notificationsFragment) navHostFragment.getNavController().navigate(R.id.notificationsFragment);
                else if (id == R.id.profileFragment) navHostFragment.getNavController().navigate(R.id.profileFragment);
                else return false;

                return true;

            }

        });



    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);

        int id=item.getItemId();

        if (id == R.id.actionProfile) navHostFragment.getNavController().navigate(R.id.profileFragment);
        else if (id == R.id.actionSavedPosts) navHostFragment.getNavController().navigate(R.id.savedPostsFragment);
        else if (id == R.id.actionSettings) navHostFragment.getNavController().navigate(R.id.settingsFragment);
        else if (id == R.id.actionLogOut) {
            Session.MyActiveUser = null;
           mainviewmodel.removelastSharedprefereces();

            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else return false;


        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
}