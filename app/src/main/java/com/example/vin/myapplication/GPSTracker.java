package com.example.vin.myapplication;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by VGIMPACT202 on 01/04/2017.
 */

public class GPSTracker extends Service implements LocationListener,
        popup_enable_gps.GpsListener {
    //extends Services 06/04/2017
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;
    boolean GPSFlag = false;//arjun

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    public Fragment fgg;

    RestService restService;
    public GPSTracker(Context context, Fragment fg, boolean first_time) {
        this.mContext = context;
        this.fgg = fg;
        getLocation(first_time);

    }



    public Location getLocation(boolean first_time) {
        try {



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }



            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled == true && isNetworkEnabled == true) {
                //showSettingsAlert();
                // no network provider is enabled
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            String ss = String.valueOf(location.getLatitude());
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }

            } else {
               // if(first_time==false) {
                    showSettingsAlert();
                //}

            }
           // Toast.makeText(mContext,"lat"+String.valueOf(location.getLatitude())+"lon"+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
            return location;
        } catch (Exception ex) {
            ex.printStackTrace();
            //Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();07112017
            latitude = 0.0;
            longitude = 0.0;
            return location;
        }
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    /*public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }*/

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     */
    public void showSettingsAlert() {
        try {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//
//            // Setting Dialog Title
//            alertDialog.setTitle("Location is settings");
//
//            // Setting Dialog Message
//            alertDialog
//                    .setMessage("Location  is not enabled.Enable it with Mode High accuracy. Do you want to go to settings menu?");
//
//            // On pressing Settings button
//            alertDialog.setPositiveButton("Settings",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(
//                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            mContext.startActivity(intent);
//                            setGPSFlag(true);//arjun
//                        /*if(getGPSFlag()==true) {
//                            Fragment currentFragment = getFragmentManager().findFragmentByTag("fragment_doctor_call_insert_map");
//                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                            fragmentTransaction.detach(currentFragment);
//                            fragmentTransaction.attach(currentFragment);
//                            fragmentTransaction.commit();
//                        }*/
//                        }
//                    });
//
//            // on pressing cancel button
//            alertDialog.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                            setGPSFlag(false);//arjun
//                        }
//                    });
//
//            // Showing Alert Message
//            alertDialog.show();


            try {


                popup_enable_gps dialog = popup_enable_gps.newInstance("Alert..Please enable location");

                int width = mContext.getResources().getDisplayMetrics().widthPixels;

                dialog.setTargetFragment(fgg, width - 30);

                dialog.show(fgg.getFragmentManager(), "");


            } catch (Exception ex) {
                Toast.makeText(mContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            //Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();07112017
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getLatitude();
        getLongitude();
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


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
           // locationManager.removeUpdates(GPSTracker.this);
        }
    }

    ////////////////////
    ///////////////////
    /*//GoogleMap mGoogleMap;
    //SupportMapFragment mapFrag;
    //LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    //Location mLastLocation;
    //Marker mCurrLocationMarker;
    //LocationManager locationManager;

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                //showGPSDisabledAlertToUser();
            }

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {

                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            //showGPSDisabledAlertToUser();
                        }

                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        //mGoogleMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/


    /////////////
    ////////////


    public boolean getGPSFlag() {
        return GPSFlag;
    }

    public void setGPSFlag(boolean GPSFlag) {
        this.GPSFlag = GPSFlag;

    }

    @Override
    public void onFinishGps(String flag) {
        try {
            if (flag.equals("Y")) {

            } else {

            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insert_location_temp()
    {
        restService = new RestService();
        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sid = app_preferences.getString("sid", "default");
    }

    /*public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                showGPSDisabledAlertToUser();
            }

            return true;
        }
    }*/

}

//http://stackoverflow.com/questions/34305377/get-current-location-0-in-marshmallow-where-below-23-api-its-give-exact-current