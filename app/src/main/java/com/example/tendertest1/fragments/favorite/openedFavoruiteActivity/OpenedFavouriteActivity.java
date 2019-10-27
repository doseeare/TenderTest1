package com.example.tendertest1.fragments.favorite.openedFavoruiteActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tendertest1.R;
import com.example.tendertest1.fragments.home.openedHomeActivity.ViewPagerAdapter;
import com.example.tendertest1.model.Models;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

public class OpenedFavouriteActivity extends AppCompatActivity {


    private List<String> imageUrls;
    private ViewPager viewPager;
    private TextView nameOfCompany, adress, numberPhone, category, cost, tittle, content, relizeDate, finishDate;
    private CirclePageIndicator viewPagerIndicator;
    private static int currentPage = 0;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Тендер");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.opened_home_activity);
        initObjects();
        initFromPrevieosActivity();
    }

    private void initObjects() {
        nameOfCompany = findViewById(R.id.name_company);
        adress = findViewById(R.id.adress);
        category = findViewById(R.id.category);
        numberPhone = findViewById(R.id.numberphone);
        cost = findViewById(R.id.cost);
        tittle = findViewById(R.id.tittle);
        content = findViewById(R.id.contentEditText);
        relizeDate = findViewById(R.id.relizedate);
        finishDate = findViewById(R.id.finishdate);
        viewPager = findViewById(R.id.imageViewPager);
        viewPagerIndicator = findViewById(R.id.indicator);
    }

    public void initFromPrevieosActivity() {
        Intent intent = getIntent();
        Models results = intent.getParcelableExtra("Start");

        this.nameOfCompany.setText(results.getCompany());
        this.adress.setText(results.getAdress());
        this.category.setText(results.getCategory());
        this.numberPhone.setText(results.getNumberPhone());
        this.cost.setText(results.getCost());
        this.tittle.setText(results.getTittle());
        this.content.setText(results.getContent());
        this.relizeDate.setText(results.getRelizeDate());
        this.finishDate.setText(results.getDeadLine());
        imageUrls = results.getImages();

        adapter = new ViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);


        //set indicator to ViewPager
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        // Auto start of viewpager
        final Runnable Update = new Runnable() {
            public void run() {
                if (adapter != null && currentPage == adapter.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    //Обротка кнопки назад
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}
