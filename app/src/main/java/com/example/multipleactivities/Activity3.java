package com.example.multipleactivities;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Activity3 extends AppCompatActivity{


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        imageView = findViewById(R.id.showOp);
        imageView.setImageBitmap(SendData.bitmap);
    }

}



