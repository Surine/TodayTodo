package com.surine.todaytodo;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.surine.todaytodo.Fragment.AboutFragment;
import com.surine.todaytodo.Fragment.SettingFragment;

/**
 * Created by surine on 2017/6/24.
 */

public class SettingActivity extends AppCompatActivity{
    SettingFragment prefFragment = new SettingFragment();
    AboutFragment mAboutFragment = AboutFragment.getInstance("关于");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("设置");

        boolean b = getIntent().getBooleanExtra("about",false);

        //preventing repeated loading (fragment)
        if (savedInstanceState == null) {
            if (b) {
                replaceFragment(mAboutFragment);
            } else {
                replaceFragment(prefFragment);
            }
        }
    }


    //overload
    private void replaceFragment(SettingFragment prefFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.share_setting_fragment, prefFragment);
        transaction.commit();
    }

    //overload
    private void replaceFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction tran = fm.beginTransaction();
        tran.replace(R.id.share_setting_fragment, fragment);
        tran.commit();
    }



    //set the back button listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
