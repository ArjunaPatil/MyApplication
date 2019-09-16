package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_add_current_location#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_add_current_location extends Fragment implements LocationListener {
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

    Button btn_save;
    TextView address;
    TextView new_lat;
    TextView new_lon;
    RestService restService;
    private ProgressDialog pDialog;
    private int i = 0;
    private long mLastClickTime = 0;

    //key generation example link--------------
    //https://mobiforge.com/design-development/using-google-maps-android ---https://www.youtube.com/watch?v=0dToEEuPL9Y
//For Calender https://inducesmile.com/android/how-to-create-android-custom-calendar-view-with-events/
    LocationManager locationManager;

    public fragment_add_current_location() {
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
    public static fragment_add_current_location newInstance(String param1, String param2) {
        fragment_add_current_location fragment = new fragment_add_current_location();
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
            init_controls();
            getLocation();
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

        View v = inflater.inflate(R.layout.fragment_add_current_location, container, false);


        btn_save = (Button) v.findViewById(R.id.btn_save);
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
            //getLocation();
        }

        /*if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 101);

        }*/

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

                    } else {
                        i = 1;
                        getLocation();
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }

    public void init_controls() {
        try {
            editText = (EditText) getView().findViewById(R.id.editText);
            btn_save = (Button) getView().findViewById(R.id.btn_save);
            address = (TextView) getView().findViewById(R.id.address);
            new_lat = (TextView) getView().findViewById(R.id.new_lat);
            new_lon = (TextView) getView().findViewById(R.id.new_lon);
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

   /* @Override
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
    }*/

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
        // locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        try {
            new_lat.setText("" + location.getLatitude());
            new_lon.setText("" + location.getLongitude());
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            /*locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));*/
            //Toast.makeText(getContext(), addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
            address.setText(addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
            /*--------*/
            if (i == 1) {
                save_emp_location(Double.valueOf(new_lat.getText().toString()), Double.valueOf(new_lon.getText().toString()));
            }
            /*--------*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void save_emp_location(double lat, double lon) {
        try {

            /*This code Cut From button Click Event*/
            POJO_Employee_Location POJO = new POJO_Employee_Location();

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            POJO.setLatitude(lat);
            POJO.setLongitude(lon);
            //POJO.setTime("");
            //POJO.setLatlon_date("");

            POJO.setUser(app_preferences.getString("name", "-"));
            POJO.setUser_name(app_preferences.getString("first_name", "default") + " " + app_preferences.getString("middle_name", "default") + " " + app_preferences.getString("last_name", "default"));
            POJO.setName("");
            insert_employee_location(POJO);
        } catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insert_employee_location(POJO_Employee_Location POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().employee_location_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();

                        Toast.makeText(getContext(), "LOCATION ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        i = 0;
                        /*Fragment frag = new fragment_DashBoard();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();*/

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.hide();

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }
                    if (msg.equals("403 FORBIDDEN")) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        //onsession_failure();
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

