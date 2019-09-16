package com.example.vin.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.example.vin.myapplication.MoviesAdapter.context;

public class test_show_location_FragmentDialog extends android.support.v4.app.DialogFragment implements LocationListener {

    RestService restService;

    Double lat = 0.0, lon = 0.0;
    LocationManager locationManager;

    Bundle bundle;
    String latitude = "", longitude = "";

    // Empty constructor required for DialogFragment
    public test_show_location_FragmentDialog() {
    }

    public static test_show_location_FragmentDialog newInstance(String title) {
        test_show_location_FragmentDialog frag = new test_show_location_FragmentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.test_show_location_fragment_dialog, container);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                try {
                    mapp = map;
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(16.6656089, 74.2127056))
                            .title("Location"));
                    bindDATA();//.position(new LatLng(16.6656089, 74.2127056))

                    mapp.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng point) {
                            mapp.clear();
                            //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

                            //The code below demonstrate how to convert between LatLng and Location

                            //Convert LatLng to Location
                            /*Location location = new Location("Test");
                            //location.setLatitude(point.latitude);
                            //location.setLongitude(point.longitude);

                            location.setLatitude(16.6656089);
                            location.setLongitude(74.2127056);

                            //Convert Location to LatLng
                            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(newLatLng)
                                    .title("New Location");

                            mapp.addMarker(markerOptions);

                            mapp.getUiSettings().setMyLocationButtonEnabled(true);*/
                            getLocation();
                            if (Build.VERSION.SDK_INT >= 23 &&
                                    ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                //Toast.makeText(getContext(), "mm", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                //Toast.makeText(getContext(), "nn", Toast.LENGTH_SHORT).show();
                            }
                            mapp.setMyLocationEnabled(true);

                        }
                    });

                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
        txt_test.setWidth(width);
    }

    @Override
    public void onStart() {
        try {
            bundle = this.getArguments();
            if (bundle != null) {
                //latitude = bundle.get("latitude").toString();
                //longitude = bundle.get("longitude").toString();

                //lat = Double.valueOf(latitude.toString());
                //lon = Double.valueOf(longitude.toString());

                //LatLng latLng = new LatLng(lat, lon);
                //moveToCurrentLocation(latLng);


                ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
                btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            getLocation();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ImageButton btn_close_data = (ImageButton) getView().findViewById(R.id.btn_close_data);
                btn_close_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //dismiss();
                            sendBackLocationResult("ttt");
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                super.onStart();
            } else {
                Toast.makeText(getContext(), "Please Select ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface EditLocationDialogListener {
        void onFinishLocationDialog(String ttt);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackLocationResult(String ttt) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            EditLocationDialogListener listener = (EditLocationDialogListener) getTargetFragment();
            listener.onFinishLocationDialog("ttt");
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*----------Trace Mobile Location Co-ordinate ----------------*/

    public void bindDATA() {
        try {
            Double lat = Double.valueOf(0.0);
            Double lon = Double.valueOf(0.0);
            //LatLng latLng = new LatLng(16.6656089, 74.2127056);.position(new LatLng(lat, lon))  ;new LatLng(lat, lon), 14);
            /*mapp.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title("Current Location"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 17);
            mapp.animateCamera(cameraUpdate);*/

            LatLng latLng = new LatLng(lat, lon);
            moveToCurrentLocation(latLng);
            //old_lat.setText(lat.toString());
            //old_lon.setText(lon.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
// locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        lat = Double.valueOf(location.getLatitude());
        lon = Double.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            /*locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));*/
            //Toast.makeText(getContext(), "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();

            LatLng latLng = new LatLng(lat, lon);
            moveToCurrentLocation(latLng);

        } catch (Exception e) {

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        //gps_enable_flag = 1;
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //String ss = provider + status;

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    /*--------------------------*/
    MapView mapView;
    GoogleMap mapp;


    private void moveToCurrentLocation(LatLng latLng) {
        try {
            mapp.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
            mapp.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mapp.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            mapp.animateCamera(CameraUpdateFactory.zoomIn());
            mapp.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}