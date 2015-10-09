package com.example.hoyalakoon.gps;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class AndroidGPSTrackingActivity extends ActionBarActivity implements LocationListener{

    private GoogleMap map;
    LatLng prev;
    LatLng current;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_gpstracking);


        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hello World",Toast.LENGTH_LONG).show();
            }
        });

        TextView text1 = (TextView) findViewById(R.id.text1);


    }

    @Override
    public void onLocationChanged(Location location) {

        /*
        LatLng now = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        mp.title("my position");

        map.addMarker(mp);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(now, 16));
        Polyline line = map.addPolyline(new PolylineOptions().add(now).width(5).color(Color.RED));
*/

        current = new LatLng(location.getLatitude(), location.getLongitude());

        if(flag==0)  //when the first update comes, we have no previous points,hence this
        {
            prev=current;
            flag=1;
        }
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(current, 16);
        map.animateCamera(update);
        map.addPolyline((new PolylineOptions())
                .add(prev, current).width(6).color(Color.BLUE)
                .visible(true));
        prev=current;
        current = null;

    }



    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_gpstracking, menu);
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
}
