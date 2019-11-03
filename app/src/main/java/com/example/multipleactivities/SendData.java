package com.example.multipleactivities;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendData extends AsyncTask<Void,Void,Void> {

    private int[][] result;
    static Bitmap bitmap;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket("192.168.43.101",5854);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            for(int i=0;i<3;i++){
                objectOutputStream.writeObject(requiredMatrix(i));
            }
            String text = "vishwanath bondugula";
            objectOutputStream.writeObject(text);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int[][]) objectInputStream.readObject();
            bitmap = null;
            getTheImage(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int[][] requiredMatrix(int i){
        switch (i){
            case 0:return Data.CAPITAL_LETTERS;
            case 1:return Data.SMALL_LETTERS;
            case 2:return Data.NUMBERS;
        }
        return Data.CAPITAL_LETTERS;
    }

    private void getTheImage(Bitmap bitmap) {
        if(this.result != null) {
            for (int i = 0; i < this.result.length; i++) {
                for (int j = 0; j < this.result[0].length; j++) {
                    bitmap.setPixel(j, i, this.result[i][j]);
                }
            }
        }
    }

}
