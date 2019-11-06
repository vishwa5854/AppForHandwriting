package com.example.multipleactivities;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Activity3 extends AppCompatActivity{


    ImageView imageView;
    int[][] result;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        new SendData().execute();
    }

    private void getTheImage(Bitmap bitmap) {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                bitmap.setPixel(j, i,result[i][j]);
            }
        }
    }



    @SuppressLint("StaticFieldLeak")
    class SendData extends AsyncTask<Void,Void,Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket socket = new Socket("192.168.43.101",5854);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                for(int i=0;i<3;i++){
                    objectOutputStream.writeObject(requiredMatrix(i));
                }
                objectOutputStream.writeObject(Data.INPUT_TEXT + " ");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                result = (int[][]) objectInputStream.readObject();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bitmap = Bitmap.createBitmap(1080,1920 , Config.RGB_565);
            getTheImage(bitmap);
            imageView = findViewById(R.id.showOp);
            imageView.setImageBitmap(bitmap);
        }

        private int[][] requiredMatrix(int i){
            switch (i){
                case 0:return Data.CAPITAL_LETTERS;
                case 1:return Data.SMALL_LETTERS;
                case 2:return Data.NUMBERS;
            }
            return Data.CAPITAL_LETTERS;
        }


    }


}



