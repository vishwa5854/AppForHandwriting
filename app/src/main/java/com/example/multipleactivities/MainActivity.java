package com.example.multipleactivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputLayout = findViewById(R.id.input_text);
        Button button = findViewById(R.id.press);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void openActivity2(){
        Data.INPUT_TEXT = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}
