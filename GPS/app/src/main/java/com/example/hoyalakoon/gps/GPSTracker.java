package com.example.hoyalakoon.gps;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;  // LocationListener 은 locationmanager의 알림을 수신함.
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
/**
 * Created by Hoyalakoon on 2015-09-14.
 */


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext; //LOCATIONMANAGER 객체 생성시에 필요.
    boolean isGPSEnabled = false; //gps상태확인
    boolean isNetworkEnabled = false; //네트워크 상태확인
    boolean canGetLocation = false;  //location참조 가능한지 확인

    Location location; // Location은 클래스의 해당 정보를 키/ 값의 bundle로 리턴함.
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //위치 업데이트 최소 변화거리
    private static final long MIN_TIME_BW_UPDATES = 1000; // 위치 업데이트 최소 시간 변화 (1초)

    protected LocationManager locationManager; // location manager 선언

    public GPSTracker(Context context){ //GPSTRACKER 생성자.

        this.mContext = context;
        getLocation(); // 인스턴스가 생성될 때 getLocation을 실행.
    }

    public Location getLocation() {
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
             // CONTEXT 여기서 쓰이는거 맞지?

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled&&!isNetworkEnabled){

            }
            else{
                this.canGetLocation = true;

                if(isGPSEnabled){ //gps가 가능하면
                    if(location == null){ //location이 비어있으면
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                this); //location updates 요쳥
                        Log.d("gps enabled","gps enabled");
                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            // locationManager가 null이 아니라는 의미를 알아야함.

                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                //location이 null 이 아니라면 받아서 위도 경도에 넣어준다.
                            }
                        }


                    }
                }


                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this);

                            Log.d("NETWORK","NETWORK");

                            if(locationManager != null){
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                if(location != null){
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                }
                            }
                }


            }


        }
        catch(Exception e){
            e.printStackTrace(); // 에러 출력 함수. 보안상 문제가 잇을 수 있다함.
        }

        return location;
    }

    public boolean canGetLocation(){

        return this.canGetLocation;

    }

    public void shoeSettingAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            // 여기는 다이얼로그 문법 찾아보면 될 듯.
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS 안켜졌다 임마 킬거야?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent); // 세팅에 있는 로케이션 세팅 창 띄우는 코딩임.
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

       alertDialog.show();

    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }


    public void onLocationChanged(Location location) {

           this.longitude = location.getLongitude();
           this.latitude = location.getLatitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
