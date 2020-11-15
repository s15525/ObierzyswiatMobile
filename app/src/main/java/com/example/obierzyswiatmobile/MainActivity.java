package com.example.obierzyswiatmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

public class MainActivity extends AppCompatActivity implements LocationListener {
    protected Geocoder geocoder;
    private String city;
    private String mPhoneNumber;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startTripButton = findViewById(R.id.startTripButton);
        final Button messageButton = findViewById(R.id.messageButton);
        geocoder = new Geocoder(this, Locale.getDefault());
        id = getIntent().getStringExtra("idEmp");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_SMS, READ_PHONE_STATE, READ_PHONE_NUMBERS, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, INTERNET}, 100);
        } else {
            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneNumber = tMgr.getLine1Number();
            LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        startTripButton.setOnClickListener(new View.OnClickListener() {
            boolean toggled = false;

            @Override
            public void onClick(View v) {
                if (!toggled) {
                    startTripButton.setText("Trasa rozpoczeta");
                    startTripButton.setBackground(getResources().getDrawable(R.drawable.custom_button_clicked));
                    new InsertToDB(id, "Kierowca o numerze telefonu: " + mPhoneNumber + " rozpoczal trase w miejscowosci " + city).execute();
                    toggled = true;
                } else {
                    startTripButton.setText("Rozpocznij trase");
                    startTripButton.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    new InsertToDB(id, "Kierowca o numerze telefonu: " + mPhoneNumber + " zakończyl trase w miejscowosci " + city).execute();
                    toggled = false;
                }
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityMessage();
            }
        });

    }

    private void startActivityMessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(id,"idEmp");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            List<Address> adrress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            city = adrress.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

