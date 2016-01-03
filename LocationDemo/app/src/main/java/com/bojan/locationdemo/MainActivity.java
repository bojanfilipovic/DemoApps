package com.bojan.locationdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//implements LocList allows our class to use all vars and methods from the LocList
public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;        //allows us to acces the users location information
    String provider;                    //store the name of the provider

    public void getLocation(View view){
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //how to get the users location, gps or wifi location
        provider = locationManager.getBestProvider(new Criteria(), false);        //if we want to specify special criteria

        Location location = locationManager.getLastKnownLocation(provider);      //gets last known user loc
        if (location != null) {
            Log.i("location", "loc achieved");
        } else {
            Log.i("location", "no loc :(");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);        //400 standard, less frequently = more battery, 1 = no.of meters
    }

    @Override
    protected void onPause() {
        super.onPause();


        locationManager.removeUpdates(this);        //stop tracking when the app is put in background
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        //gives us the new location as soon as it has been updated
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Toast.makeText(getApplicationContext(), "Lat = " + lat + "Lng = " + lng, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //to know when the gps loc is available
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
