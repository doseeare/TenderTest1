package com.example.tendertest1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tendertest1.addActivity.AddActivity;
import com.example.tendertest1.fragmentAdapter.FragmentAdapter;
import com.example.tendertest1.fragments.favorite.FavoriteFragment;
import com.example.tendertest1.fragments.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Тендеры");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        viewPager = findViewById(R.id.viewPager);
        setupViewPager();
        setupNavigationView();
    }

    private void setupNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        getSupportActionBar().setTitle("Тендеры");

                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.favorite_menu:

                        getSupportActionBar().setTitle("Избранные");
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
    }

    private void setupViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new FavoriteFragment());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.home_menu);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.favorite_menu);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Нажмите \"Назад\" еще раз для выхода", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void ActionButton (View view){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }
//Добавление SearchView в Tool
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
