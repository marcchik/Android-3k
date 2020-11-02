package com.smuzdev.lab_05.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.MyAdapter;
import com.smuzdev.lab_05.models.Dish;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Dish> dishList;
    Dish mDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView  = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        dishList = new ArrayList<>();

//        mDish = new Dish("Итальянская лазанья", "Рецепт лазаньи довольно прост: берем готовое тесто для лазаньи, делаем сочный фарш и добавляем побольше вкусного сыра.", "50 мин.", R.drawable.image_1);
//        dishList.add(mDish);
//
//        mDish = new Dish("Мусака с сыром", "Мусака из баклажанов с твердым сыром — аппетитное летнее блюдо, которое обязательно понравится вам и вашим близким.", "50 мин.", R.drawable.image_2);
//        dishList.add(mDish);
//
//        mDish = new Dish("Суп с помидорами", "Томатный суп — идеальное летнее первое блюдо. Приготовьте этот суп с помидорами для своих близких, и благодарность не заставит себя долго ждать.", "30 мин.", R.drawable.image_3);
//        dishList.add(mDish);
//
//        mDish = new Dish("Салат Цезарь", " Салат Цезарь с курицей — классический салат с куриным филе, который легко приготовить дома. При относительной простоте приготовления, он получается сытным и необыкновенно вкусным.", "30 мин.", R.drawable.image_4);
//        dishList.add(mDish);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, dishList);
        mRecyclerView.setAdapter(myAdapter);
    }

    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this, UploadActivity.class));

    }

}