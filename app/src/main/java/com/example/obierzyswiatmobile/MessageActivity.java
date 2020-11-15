package com.example.obierzyswiatmobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        final TextView textView = findViewById(R.id.textView);
        Button buttonWyslij = findViewById(R.id.wyslij);
        Button buttonAnuluj = findViewById(R.id.anuluj);
        final String id = getIntent().getStringExtra("idEmp");

        buttonAnuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonWyslij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertToDB(id,textView.getText().toString()).execute();
                finish();
            }
        });

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 500){
                    Toast.makeText(getApplicationContext(),"Wiadomosc moze zawierac maksymalnie 500 znakow!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
