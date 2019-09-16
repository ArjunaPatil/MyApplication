package com.example.vin.myapplication;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TwoFragment extends Fragment implements OnMapReadyCallback {

    private Realm mRealm;
    List<POJO_LatLon_Map> POJO;

    public TwoFragment() {
        //Activity
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_two, container, false);
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        //////////////////////////////////////////////////////
        /////////////////////////////////////////////////////

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();

        } else {
            // Google Play Services are available
            // Getting reference to the SupportMapFragment of activity_main.xml

            //SupportMapFragment fm = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
            //fm.getMapAsync(this);

            SupportMapFragment fm = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map));
            fm.getMapAsync(this);

        }



        /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                locationCount++;

                // Drawing marker on the map
                drawMarker(point);

                *//** Opening the editor object to write data to sharedPreferences *//*
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Storing the latitude for the i-th location
                editor.putString("lat" + Integer.toString((locationCount - 1)), Double.toString(point.latitude));

                // Storing the longitude for the i-th location
                editor.putString("lng" + Integer.toString((locationCount - 1)), Double.toString(point.longitude));

                // Storing the count of locations or marker count
                editor.putInt("locationCount", locationCount);

                *//** Storing the zoom level to the shared preferences *//*
                editor.putString("zoom", Float.toString(googleMap.getCameraPosition().zoom));

                *//** Saving the values stored in the shared preferences *//*
                editor.commit();

                Toast.makeText(getContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                // Removing the marker and circle from the Google Map
                googleMap.clear();

                // Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Clearing the editor
                editor.clear();

                // Committing the changes
                editor.commit();

                // Setting locationCount to zero
                locationCount = 0;
            }
        });*/
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    ////////////////////////////////////
    ///////////////////////////////////

    GoogleMap map;//googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;


    @Override
    public void onMapReady(GoogleMap map) {
        try {
            //DO WHATEVER YOU WANT WITH GOOGLEMAP
            this.map = map;
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
            map.setMyLocationEnabled(true);
            map.setTrafficEnabled(true);
            map.setIndoorEnabled(true);
            map.setBuildingsEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);

            mRealm = Realm.getDefaultInstance();
            final RealmResults<POJO_LatLon_Map> result_query1 = mRealm.where(POJO_LatLon_Map.class).findAll();//.sort("modified", Sort.DESCENDING);
            List<POJO_LatLon_Map> POJO = result_query1;
            Double Latitude = 0.00;
            Double Longitude = 0.00;
            for (int i = 0; i < POJO.size(); i++) {
                Latitude = Double.parseDouble(POJO.get(i).getlatitude().toString());
                Longitude = Double.parseDouble(POJO.get(i).getLongitude().toString());
                String name = POJO.get(i).getSubject().toString();
                String time = POJO.get(i).getTime_call().toString();
                if (Latitude > 0 && Longitude > 0) {
                    /*LatLng point = new LatLng(Latitude, Longitude);
                    drawMarker(point);*/
                    map.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude))
                            .title(name + " " + time).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 8));

                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void drawMarker(LatLng point) {
        try {
            // Creating an instance of MarkerOptions
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting latitude and longitude for the marker
            markerOptions.position(point);

            // Adding marker on the Google Map
            map.addMarker(markerOptions);//googleMap
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


    /*// Getting GoogleMap object from the fragment
    googleMap = fm.getMap();
    // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

    // Opening the sharedPreferences object
    sharedPreferences = getSharedPreferences("location", 0);

    // Getting number of locations already stored
    locationCount = sharedPreferences.getInt("locationCount", 0);

    // Getting stored zoom level if exists else return 0
    String zoom = sharedPreferences.getString("zoom", "0");

    // If locations are already saved
            if (locationCount != 0) {

        String lat = "";
        String lng = "";

        // Iterating through all the locations stored
        for (int i = 0; i < locationCount; i++) {

            // Getting the latitude of the i-th location
            lat = sharedPreferences.getString("lat" + i, "0");

            // Getting the longitude of the i-th location
            lng = sharedPreferences.getString("lng" + i, "0");

            // Drawing marker on the map
            drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
        }

        // Moving CameraPosition to last clicked position
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

        // Setting the zoom level in the map on last position is clicked
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
    }*/

}