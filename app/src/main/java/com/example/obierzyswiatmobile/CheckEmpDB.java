package com.example.obierzyswiatmobile;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckEmpDB extends AsyncTask<Void, Void, String> {
    private String id;
    private String result = "";

    public CheckEmpDB(String id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL connection = new URL("http://10.0.2.2:8080/checkIdEmp" + "?id=" + id);
            HttpURLConnection urlConnection = (HttpURLConnection) connection.openConnection();
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.openStream()));
            String stringBuffer;
            String string = "";
            while ((stringBuffer = bufferedReader.readLine()) != null) {
                string = String.format("%s%s", string, stringBuffer);
            }
            result = string;
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

