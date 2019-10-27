package com.example.tendertest1.addActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tendertest1.R;
import com.example.tendertest1.model.Models;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private final static int GALLERY_REQUEST_CODE = 1;
    private EditText companyEditText, adresEditText, numberEditText, categoryEditText, costEditText, contentEditText, tittleEditText, relizeEditText, deadlineEditText;

    private AddAdapter adapter = new AddAdapter();
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private Toolbar add_toolbar;

    private List<String> imagePathList;
    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem_activity);
        add_toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(add_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initObjects(savedInstanceState);

    }

    private void initObjects(Bundle savedInstanceState) {
        companyEditText = findViewById(R.id.name_companyEditText);
        adresEditText = findViewById(R.id.adresEditText);
        numberEditText = findViewById(R.id.numberphoneEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        costEditText = findViewById(R.id.costEditText);
        contentEditText = findViewById(R.id.contentEditText);
        tittleEditText = findViewById(R.id.titleEditText);
        relizeEditText = findViewById(R.id.relizedateEditText);
        deadlineEditText = findViewById(R.id.finishDateEditText);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("results");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        recyclerView = findViewById(R.id.add_recyclerView);
        recyclerView.setAdapter(adapter);


        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        // Нужен для накладывание элементов 2 в ряд Размер может варироват
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            adapter.setNewList(getAllShownImagesPath());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            }
        }

        if (savedInstanceState != null) {

        }
        //при нажатии на невидимую кнопку который лежит поверх recyclerview будет открыта openfileChooser
    }


    //Методы для извлечения фотографий из системной галареи
    public void openFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select photo"), GALLERY_REQUEST_CODE);
    }

    //Обработка полученной фотографии
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            imagePathList = new ArrayList<>();

            if (data.getClipData() != null) {

                int count = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    count = data.getClipData().getItemCount();
                }
                for (int i = 0; i < count; i++) {

                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    getImageFilePath(imageUri);
                }
            } else if (data.getData() != null) {

                Uri imgUri = data.getData();
                getImageFilePath(imgUri);
            }
        }
    }

    public void getImageFilePath(Uri uri) {
        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePathList.add(imagePath);
            cursor.close();
        }
    }

    //Метод на клик кнопки Опубликовать
    public void addModel(View view) {
        Toast.makeText(this, "Ваше оъявление будет показана!", Toast.LENGTH_SHORT).show();

        String companyName = companyEditText.getText().toString();
        String adres = adresEditText.getText().toString();
        String numberphone = numberEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String cost = costEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String tittle = tittleEditText.getText().toString();
        String relize = relizeEditText.getText().toString();
        String deadline = deadlineEditText.getText().toString();

        String logoLink = "empty";

        //фотографии
        List<String> images = imagePathList;

        String id = databaseReference.push().getKey();

        Models newModels = new Models(companyName, relize, adres, logoLink, category, tittle, content,
                deadline, cost, numberphone, images);

        Map<String, Object> modelValues = newModels.toMap();

        Map<String, Object> model = new HashMap<>();
        model.put(id, modelValues);

        databaseReference.updateChildren(model);

        Toast toast = Toast.makeText(this,
                "Объявление будет опубликовано", Toast.LENGTH_LONG);
        toast.show();

    }

    //получения картинок из галареи

    private ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(0, absolutePathOfImage);
        }
        return listOfAllImages;
    }
    // запрос на разрешение исп галареи

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapter.setNewList(getAllShownImagesPath());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    //Обротка кнопки назад
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
