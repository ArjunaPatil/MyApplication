package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_map_location_example1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_map_location_example1 extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText editText;

    MapView mapView;
    GoogleMap mapp;
    Bundle bundle;
    Context context;
    Button btn_save;
    Button btn_clear;
    TextView address;
    TextView old_lat;
    TextView old_lon;
    TextView new_lat;
    TextView new_lon;
    GPSTracker gps;
    //key generation example link--------------
    //https://mobiforge.com/design-development/using-google-maps-android ---https://www.youtube.com/watch?v=0dToEEuPL9Y
//For Calender https://inducesmile.com/android/how-to-create-android-custom-calendar-view-with-events/
    LocationManager locationManager;

    RestService restService;
    ProgressDialog pDialog;
    Double lat = 0.0, lon = 0.0;

    public fragment_map_location_example1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Doctor_List.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_map_location_example1 newInstance(String param1, String param2) {
        fragment_map_location_example1 fragment = new fragment_map_location_example1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            pDialog = new ProgressDialog(getContext());
            gps = new GPSTracker(getContext(), fragment_map_location_example1.this, false);
            if (gps.canGetLocation()) {

                lat = gps.getLatitude();
                lon = gps.getLongitude();
                init_controls();

            } else {
                gps.showSettingsAlert();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_insert_doctor_master_map, container, false);

        View v = inflater.inflate(R.layout.fragment_map_location_example, container, false);


        btn_save = (Button) v.findViewById(R.id.btn_save);
        btn_clear = (Button) v.findViewById(R.id.btn_clear);
        new_lat = (TextView) v.findViewById(R.id.new_lat);
        new_lon = (TextView) v.findViewById(R.id.new_lon);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            getLocation();
        }

        /*if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 101);

        }*/


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindDATA();

                // \n is for new line


            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapp.clear();
            }
        });

        try {
            // Gets the MapView from the XML layout and creates it
            mapView = (MapView) v.findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                try {
                    mapp = map;
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(0.0, 0.0))
                            .title("Marker"));
                    bindDATA();//.position(new LatLng(16.6656089, 74.2127056))

                    mapp.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng point) {
                            mapp.clear();
                            //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

                            //The code below demonstrate how to convert between LatLng and Location

                            //Convert LatLng to Location
                            Location location = new Location("Test");
                            location.setLatitude(point.latitude);
                            location.setLongitude(point.longitude);
                            //  location.setTime(new Date().getTime()); //Set time as current Date
                            //  txtinfo.setText(location.toString());

                            //Convert Location to LatLng
                            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(newLatLng)
                                    .title("New location of DR");

                            mapp.addMarker(markerOptions);

                            mapp.getUiSettings().setMyLocationButtonEnabled(true);
                            if (Build.VERSION.SDK_INT >= 23 &&
                                    ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(getContext(), "mm", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(getContext(), "nn", Toast.LENGTH_SHORT).show();
                            }
                            mapp.setMyLocationEnabled(true);

                        }
                    });

                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button search_button = (Button) v.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     EditText locationSearch = (EditText) v.findViewById(R.id.editText);
                String location = editText.getText().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    moveToCurrentLocation(latLng);
                }
            }
        });

        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


    public void init_controls() {
        try {
            editText = (EditText) getView().findViewById(R.id.editText);
            btn_save = (Button) getView().findViewById(R.id.btn_save);
            address = (TextView) getView().findViewById(R.id.address);
            /*old_lat = (TextView) getView().findViewById(R.id.old_lat);
            old_lon = (TextView) getView().findViewById(R.id.old_lon);*/
            new_lat = (TextView) getView().findViewById(R.id.new_lat);
            new_lon = (TextView) getView().findViewById(R.id.new_lon);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    int limitstart = 0;
    int pagesize = 10;
    public boolean bool_full_update = true;
    boolean datafull = false;
    int call_size = 0, cnt = 0;

    private void get_lat_lon(final String current_date, final String user_email_id) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().getLat_Lon_of_Emp(sid, current_date, user_email_id, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");
                        if (j2 != null) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_LatLon_Map>>() {
                            }.getType();
                            List<POJO_LatLon_Map> POJO = gson.fromJson(j2, type);
                            if (POJO.size() == 0) {
                                datafull = true;
                            } else {
                                if (call_size < 1) {
                                    call_size = POJO.size();
                                }
                                if (call_size == POJO.size()) {
                                    cnt++;
                                    if (cnt > 0) {
                                        datafull = true;
                                        call_size = 0;
                                        cnt = 0;
                                    }
                                }
                                limitstart = limitstart + pagesize;
                            }


                            if (datafull == false) {
                                get_lat_lon(current_date, user_email_id);

                            } else {
                                pDialog.hide();
                            }
                        }
                        pDialog.hide();

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        String msg = "";

                        pDialog.hide();

                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        //String msg = error.getMessage();

                        if (msg.contains("403 FORBIDDEN")) {
                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();

                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);

                            Toast.makeText(getContext(), "PLEASE WAIT..REFRESHING..", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {

                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }


                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }


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

    public void bindDATA() {
        try {
            mapp.clear();
            gps = new GPSTracker(getContext(), this, false);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                new_lat.setText("" + latitude);
                new_lon.setText("" + longitude);

                // Toast.makeText(getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                LatLng latLng = new LatLng(latitude, longitude);
                moveToCurrentLocation(latLng);

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                //    gps.showSettingsAlert();
            }
            Double lat = Double.valueOf(new_lat.getText().toString());
            Double lon = Double.valueOf(new_lon.getText().toString());
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    ////////////////////

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
       /* // locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        new_lat.setText("" + location.getLatitude());
        new_lon.setText("" + location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            *//*locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));*//*
            //Toast.makeText(getContext(), addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
            address.setText(addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
            *//*--------*//*
            Double lat = Double.valueOf(new_lat.getText().toString());
            Double lon = Double.valueOf(new_lon.getText().toString());
            LatLng latLng = new LatLng(lat, lon);
            moveToCurrentLocation(latLng);
            *//*--------*//*
        } catch (Exception e) {

        }*/

    }

    @Override
    public void onProviderDisabled(String provider) {
        // Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void alert_box() {
        try {
            context = getActivity();
            if (context != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
                builder.setMessage("Please Enable GPS and Internet");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*

    ArrayList<MarkerData> markersArray = new ArrayList<MarkerData>();

for(int i = 0 ; i < markersArray.size() ; i++ ) {

    createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
}

...

protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

    return googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(latitude, longitude))
            .anchor(0.5f, 0.5f)
            .title(title)
            .snippet(snippet);
            .icon(BitmapDescriptorFactory.fromResource(iconResID)));
}

     */


}

