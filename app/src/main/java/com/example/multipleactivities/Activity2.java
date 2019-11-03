package com.example.multipleactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class Activity2 extends AppCompatActivity {

    TextView message;
    Button chooseFile;
    final static int READ_REQUEST_CODE = 42;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        message = findViewById(R.id.textView);
        chooseFile = findViewById(R.id.chooseFile);

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,READ_REQUEST_CODE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if(resultCode == RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e) {
                System.out.println("Couldn't load the image into bitmap ");
            }

            assert bitmap != null;
            int[][] imageData = new int[bitmap.getHeight()][bitmap.getWidth()];
            for(int i=0;i<imageData.length;i++){
                for(int j=0;j<imageData[0].length;j++){
                    imageData[i][j] = bitmap.getPixel(j,i);
                }
            }

            System.out.println(count);

            if(count == 0){
                Data.CAPITAL_LETTERS = imageData;
                System.out.println(Data.CAPITAL_LETTERS[0][9]);
                message.setText("Now Choose the small letters");
            }
            if(count == 1){
                Data.SMALL_LETTERS = imageData;
                System.out.println(Data.SMALL_LETTERS[0][9]);
                message.setText("Now choose the numbers file");
            }
            if(count == 2){
                Data.NUMBERS = imageData;
                System.out.println(Data.NUMBERS[0][9]);
                message.setText("Okay hold on");
                SendData sendData = new SendData();
                sendData.execute();
                openActivity3();
            }
            count++;
        }
    }

    void openActivity3(){
        Intent intent = new Intent(this.getApplicationContext(),Activity3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
