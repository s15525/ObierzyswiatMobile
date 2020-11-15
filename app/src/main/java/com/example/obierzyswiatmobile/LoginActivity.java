package com.example.obierzyswiatmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        final Button loginButton = findViewById(R.id.loginButton);
        editText = findViewById(R.id.editText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                CheckEmpDB checkEmpDB = (CheckEmpDB) new CheckEmpDB(editText.getText().toString()).execute();
                try {
                    result = checkEmpDB.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                if (result.equals("true")) {
                    loginInActivity(result);
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Twoj numer pracowniczy jest nieprawidlowy", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loginInActivity(String idEmp) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idEmp", idEmp);
        startActivity(intent);
    }
}
