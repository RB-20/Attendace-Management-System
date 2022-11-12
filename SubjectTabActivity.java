package com.example.ams.tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.ams.R;
import com.example.ams.main.ui.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;

public class SubjectTabActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    public static String action = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_tab);

        Intent intent = getIntent();
        action = intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPage);

        viewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        String data[] = {"Lecture","Practical","Seminar"};

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                return new Lecture(action);
            }
            if(position == 1){
                return new Practical(action);
            }/*
            if(position == 2){
                return new Seminar();
            }*/
            return null;
        }

        @Override
        public int getCount() {
            // return data.length;
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data[position];
        }
    }

}
