package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_patch_master_insert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_test extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private ProgressDialog pDialog;
    Button btn_check_version;
    GPSTracker gps;
    MapView mapView;
    GoogleMap mapp;

    public fragment_test() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_insert_patch_master_1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_patch_master_insert newInstance(String param1, String param2) {
        fragment_patch_master_insert fragment = new fragment_patch_master_insert();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_check_for_loc, container, false);
        try {
            // Gets the MapView from the XML layout and creates it
            mapView = (MapView) v.findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                try {
                    mapp = map;
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(0.0, 0.0))
                            .title("Marker"));


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
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;


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

    public void onStart() {
        try {
            super.onStart();

            init_controls();
            // Gets to GoogleMap from the MapView and does initialization stuff

            btn_check_version.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
//                        mapp.clear();
//                        gps = new GPSTracker(getContext());
//
//                        // check if GPS enabled
//                        if (gps.canGetLocation()) {
//
//                            double latitude = gps.getLatitude();
//                            double longitude = gps.getLongitude();
//
//                            // \n is for new line
//                            Toast.makeText(getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//
//                            LatLng latLng = new LatLng(latitude, longitude);
//                            moveToCurrentLocation(latLng);
//
//                        } else {
//                            // can't get location
//                            // GPS or Network is not enabled
//                            // Ask user to enable GPS/network in settings
//                            //   gps.showSettingsAlert();
//                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            try {
                MapsInitializer.initialize(this.getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ((DashBord_main) getActivity()).setActionBarTitle("UPDATE VERSION");


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {

            btn_check_version = (Button) getView().findViewById(R.id.btn_check_version);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public boolean temp_flag = false;

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
