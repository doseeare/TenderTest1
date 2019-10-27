package com.example.tendertest1.fragments.home.openedHomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tendertest1.R;
import com.example.tendertest1.model.Models;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenedHomeActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<String> imageUrls;
    private ViewPager viewPager;
    private TextView nameOfCompany, adress, numberPhone, category, cost, tittle, content, relizeDate, finishDate;
    private CirclePageIndicator viewPagerIndicator;
    private static int currentPage = 0;
    private ViewPagerAdapter adapter;
    private Models models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("favourite");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opened_toobar_menu, menu);
        return true;
    }

    public void initFromPrevieosActivity() {
        Intent intent = getIntent();
        models = intent.getParcelableExtra("Start");
        this.nameOfCompany.setText(models.getCompany());
        this.adress.setText(models.getAdress());
        this.category.setText(models.getCategory());
        this.numberPhone.setText(models.getNumberPhone());
        this.cost.setText(models.getCost());
        this.tittle.setText(models.getTittle());
        this.content.setText(models.getContent());
        this.relizeDate.setText(models.getRelizeDate());
        this.finishDate.setText(models.getDeadLine());
        imageUrls = models.getImages();

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
        if (item.getItemId() == R.id.ffavorite_menu) {
           addToFavourite(models);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToFavourite (Models models){

        String companyName = models.getCompany();
        String relize = models.getRelizeDate();
        String adress = models.getAdress();
        String logoLink = models.getLogoLink();
        String category = models.getCategory();
        String tittle = models.getTittle();
        String content= models.getContent();
        String deadline= models.getDeadLine();
        String cost= models.getCost();
        String numberphone = models.getNumberPhone();
        List images = models.getImages();

        String key = reference.push().getKey();

        Models newModels = new Models(companyName, relize, adress, logoLink, category, tittle, content,
                deadline, cost, numberphone, images);

        Map<String, Object> modelValues = newModels.toMap();

        Map<String, Object> model = new HashMap<>();
        model.put(key, modelValues);

        reference.updateChildren(model);

        Toast toast = Toast.makeText(this,
                "Добавлено в избранные", Toast.LENGTH_LONG);
        toast.show();

    }


}