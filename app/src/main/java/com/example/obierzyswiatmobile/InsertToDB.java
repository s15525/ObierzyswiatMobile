package com.example.obierzyswiatmobile;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertToDB extends AsyncTask<Void, Void, Void> {
    private String id;
    private String message;

    public InsertToDB(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL connection = new URL("http://10.0.2.2:8080/insert" + "?id=" + id + "&message=" + message);
            HttpURLConnection urlConnection = (HttpURLConnection) connection.openConnection();
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                string = String.format("%s%s", string, stringBuffer);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
