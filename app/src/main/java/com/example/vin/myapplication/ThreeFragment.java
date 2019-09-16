package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

public class ThreeFragment extends Fragment {

    //https://blog.fossasia.org/how-to-add-markers-in-map-fragment-of-open-event-android-app/

    MapView mapView;
    GoogleMap mapp;
    private ProgressDialog pDialog;

    private LocationManager locManager;
    //GPSTracker tracker;
    GPSTracker gps;
    Double lat, lon;//latt, lngg,
    double latt, lngg;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/


    @Override
    public void onStart() {
        try {

            super.onStart();
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            if (gps.canGetLocation()) {
                latt = gps.getLatitude();
                lngg = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String emp_name = app_preferences.getString("employee", "default");
            //Get_LatLon_List();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {

            View v = inflater.inflate(R.layout.fragment_three, container, false);
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            if (gps.canGetLocation()) {
                latt = gps.getLatitude();
                lngg = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }

            pDialog = new ProgressDialog(getContext());
            //Get_LatLon_List(savedInstanceState);
            mapView = (MapView) v.findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);
            pDialog.show();
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    mapp = map;
                    pDialog.hide();
                    /*if (mapp != null) {
                        new MarkerTask().execute();
                    }*/
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(latt, lngg))
                            .title("Your Location"));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latt, lngg), 12.5f);//16
                    map.animateCamera(cameraUpdate);
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);//MAP_TYPE_HYBRID
                }
            });

            try {
                MapsInitializer.initialize(this.getActivity());
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT);
            }
            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT);
            return null;
        }
    }


    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        try {
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            if (gps.canGetLocation()) {
                latt = gps.getLatitude();
                lngg = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }

            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT);
        }
    }

    RestService restService;

    private void Get_LatLon_List(final Bundle ss) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            pDialog.show();
            restService.getService().getLat_Lon_of_Emp(sid, "2018-05-31", "kasimmevekari@gmail.com", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_LatLon_Map>>() {
                        }.getType();
                        List<POJO_LatLon_Map> POJO = gson.fromJson(j2, type);

                        /*--------*/
                        if (POJO == null || POJO.size() == 0) {
                            pDialog.hide();
                        } else {
                            show_list(POJO, ss);
                            pDialog.hide();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "ACCESS FORBIDDEN", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void show_list(final List<POJO_LatLon_Map> ll, final Bundle ss) {
        try {
            // Latitude & Longitude
            mapView = (MapView) getView().findViewById(R.id.mapview);
            mapView.onCreate(ss);
            pDialog.show();

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    Double Latitude = 0.00;
                    Double Longitude = 0.00;
                    for (int i = 0; i < ll.size(); i++) {
                        Latitude = Double.parseDouble(ll.get(i).getlatitude().toString());
                        Longitude = Double.parseDouble(ll.get(i).getLongitude().toString());
                        String name = ll.get(i).getSubject().toString();
                        if (Latitude > 0 && Longitude > 0) {
                            MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
                            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                            map.addMarker(marker);
                            map.setMapType(map.MAP_TYPE_NORMAL);
                            map.animateCamera(CameraUpdateFactory.zoomTo(12.5f));
                            //map.setBuildingsEnabled(true);
                        }
                    }


                /*for (int j = 0; j < latLngs.size() - 1; j++) {
                    PolylineOptions line = new PolylineOptions().add((latLngs.get(j)), latLngs.get(j + 1)).width(3).color(Color.RED);
                    map.addPolyline(line);
                }*/
                }
            });

            try {
                MapsInitializer.initialize(this.getActivity());
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT);
        }
    }

    /*@Override
    public void onMapReady(GoogleMap map) {
        double latitude;
        double longitude;
        latLngs = new ArrayList<LatLng>();
        for (int i = 0; i < Choose.latitudes.size(); i++) {
            latitude = Double.valueOf(Choose.latitudes.get(i));
            longitude = Double.valueOf(Choose.longitudes.get(i));
            LatLng myLocation = new LatLng(latitude, longitude);
            latLngs.add(myLocatoin);
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            map.animateCamera(CameraUpdateFactory.zoomTo(12.5f));
            map.setBuildingsEnabled(true);
        }
        for (int j = 0; j < latLngs.size() - 1; j++) {
            PolylineOptions line = new PolylineOptions().add((latLngs.get(j)), latLngs.get(j + 1)).width(3).color(Color.RED);
            map.addPolyline(line);
        }
    }*/

    /*private void setUpMapIfNeeded() {
        if (mapp == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapview);
            mapp = mapFragment.getMap();
            if (map != null) {
                //setUpMap();
                new MarkerTask().execute();
            }
        }
    }*/


    private class MarkerTask extends AsyncTask<Void, Void, String> {

        private static final String LOG_TAG = "ExampleApp";

        private static final String SERVICE_URL = "http://139.59.63.181/api/method/team.team.doctype.test_gps_map.test_gps_map.get_lat_long_details/?date=2018-05-31&emp=kasimmevekari@gmail.com";

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(Void... args) {

            HttpURLConnection conn = null;
            final StringBuilder json = new StringBuilder();
            try {
                // Connect to the web service
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String sid = app_preferences.getString("sid", "default");

                URL url = new URL(SERVICE_URL);//new URL(SERVICE_URL)
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Cookie", sid);
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Read the JSON data into the StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    json.append(buff, 0, read);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error connecting to service", e);
                //throw new IOException("Error connecting to service", e); //uncaught
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return json.toString();
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String json) {

            try {
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    LatLng latLng = new LatLng(jsonObj.getJSONArray("latitude").getDouble(0),
                            jsonObj.getJSONArray("longitude").getDouble(1));

                    //move CameraPosition on first result
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng).zoom(13).build();

                        mapp.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                    // Create a marker for each city in the JSON data.
                    mapp.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(jsonObj.getString("subject"))
                            .snippet(Integer.toString(jsonObj.getInt("time_call")))
                            .position(latLng));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    }

}