package com.example.covidnews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.covidnews.fragments.HomeFragment;
import com.example.covidnews.fragments.StatisticsFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;

import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BottomNavigationViewEx navigationView;
    ViewPager2 viewPager2;
    final int REQUEST_INTERNET_CODE = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();
        setBottomNavigationParams();
        permissionClaim();
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    private void setViewPager(){
        viewPager2 = findViewById(R.id.mainviewpager);
        mainViewPagerAdapter adapter = new mainViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("DBG", Integer.toString(position));
                if(position == 0){
                    navigationView.setSelectedItemId(R.id.bottombar_home);
                }
                else if(position == 1){
                    navigationView.setSelectedItemId(R.id.bottombar_statistics);
                }
                else if(position == 2) {
                    navigationView.setSelectedItemId(R.id.bottombar_news);
                }
                else
                    navigationView.setSelectedItemId(R.id.bottombar_tracing);
            }
        });


    }
    private void setBottomNavigationParams(){
        navigationView = findViewById(R.id.bottombar);
        navigationView.enableAnimation(false);
        navigationView.enableShiftingMode(false);
        navigationView.enableItemShiftingMode(false);
        navigationView.setItemHeight(80);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.bottombar_home: {
                         viewPager2.setCurrentItem(0);
                         break;
                     }
                     case R.id.bottombar_statistics: {
                         viewPager2.setCurrentItem(1);
                         break;
                     }
                     case R.id.bottombar_news: {
                         viewPager2.setCurrentItem(2);
                         break;
                     }
                     case R.id.bottombar_tracing: {
                         viewPager2.setCurrentItem(3);
                         break;
                     }
                 };
                 return true;
            };
        });
    }

    public class mainViewPagerAdapter extends FragmentStateAdapter{


        public mainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public mainViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public mainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("DBG", "Position: " + Integer.toString(position));
            if(position == 0) {

                return new HomeFragment();
            }
            else if(position == 1){


                return new StatisticsFragments();
            }
            else return new StatisticsFragments();
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    void permissionClaim(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            return;
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){

        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_INTERNET_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Oh noooo you denied my request :((", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Great!! Thank kiu for your support!!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}