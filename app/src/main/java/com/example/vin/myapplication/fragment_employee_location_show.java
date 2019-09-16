package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_employee_location_show#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_employee_location_show extends Fragment implements
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener, DatePickerFragment.DateDialogListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    private Realm mRealm;
    RestService restService;

    public String designation = "", employeename = "ALL", User_ID_List_String = "", my_id = "";
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    ProgressDialog pDialog;

    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;

    public POJO_User_S last_POJO;
    private OnFragmentInteractionListener mListener;


    //TextView select_date2;
    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_objective_of_employee;
    ImageButton select_employee3;

    LinearLayout select_date;
    LinearLayout select_date1;
    TextView select_date2;
    TextView name_date_of_objective;
    ImageButton select_date3;
    private long mLastClickTime = 0;
    View vw_employee;
    user_list_hierarchy_me_FragmentDialog dialog1;

    public fragment_employee_location_show() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_DashBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_employee_location_show newInstance(String param1, String param2) {
        fragment_employee_location_show fragment = new fragment_employee_location_show();
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
        View view = inflater.inflate(R.layout.fragment_employee_location_show, container, false);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();
        } else {
            /////
            SupportMapFragment fm = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map));
            fm.getMapAsync(this);
            /////


        }


        return view;
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

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String ss) {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_LatLon_Map.class);
            mRealm.commitTransaction();
            mRealm.close();


            if (id == "ALL") {
                name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
                name_objective_of_employee.setText(my_id);

                select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
                select_employee2.setText("MY LOCATION");
            } else {
                select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
                select_employee2.setText(fullname.toString());

                name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
                name_objective_of_employee.setText(id.toString());
            }


            //bind_objective_employee_wise_listview(id.toString());
            limitstart = 0;
            Check_class_user_null_or_not();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishDialog(Date date) {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = sdf.format(date);

            select_date2.setText(hireDate);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_LatLon_Map.class);
            mRealm.commitTransaction();
            mRealm.close();

            limitstart = 0;
            Check_class_user_null_or_not();
            //Toast.makeText(getContext(),"Selected Date :"+ hireDate, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        try {

            super.onStart();
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("MAP LOCATION");
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));
            my_id = (app_preferences.getString("name", "default"));
            init_control();
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
            name_objective_of_employee.setText(my_id);

            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            select_employee2.setText("MY LOCATION");
            if (select_date2.getText().equals("dd/mm/yyyy")) {
                loaddate();
            }

            pDialog = new ProgressDialog(getContext());
            select_employee1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });
            select_employee2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });
            select_employee3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });

            select_date1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });
            select_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });
            select_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });
            data_fetch();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_LatLon_Map.class);
            mRealm.commitTransaction();
            mRealm.close();

            limitstart = 0;
            Check_class_user_null_or_not();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void init_control() {
        try {
            //employee spinner
            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);

            // select_employee.setVisibility(View.GONE);
            select_employee1.setVisibility(View.GONE);
            select_employee3.setVisibility(View.GONE);
            select_employee2.setVisibility(View.GONE);
            name_objective_of_employee.setVisibility(View.GONE);

            select_date1 = (LinearLayout) getView().findViewById(R.id.select_date1);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            name_date_of_objective = (TextView) getView().findViewById(R.id.name_date_of_objective);

            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_objective_of_employee.setVisibility(View.VISIBLE);

                select_date1.setVisibility(View.VISIBLE);
                select_date3.setVisibility(View.VISIBLE);
                select_date2.setVisibility(View.VISIBLE);
                name_date_of_objective.setVisibility(View.VISIBLE);
            } else {

            }

            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setMovementMethod(new ScrollingMovementMethod());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddate() {
        try {
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            select_date2.setText(sdf.format(date));
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Check_class_user_null_or_not() {
        try {
            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).findAll();
            if (POJO_User_Obj.size() > 0) {
                loadMap_LatLon(select_date2.getText().toString());
            } else {
                User_ID_List_String = " ";
                /*Dialog Box Code For Class HQ Null*/
                alert_box();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadMap_LatLon(String dd) {
        try {

            //String ss = dd;
            //String ee = name_objective_of_employee.getText().toString();

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = "";
            if (name_objective_of_employee.getText().toString().contains("MY Location")) {
                emp = app_preferences.getString("name", "default");
            } else {
                emp = name_objective_of_employee.getText().toString();
            }
            pDialog.show();
            //dd,name_objective_of_employee.getText().toString()"2018-05-31","kasimmevekari@gmail.com"
            restService.getService().getLat_Lon_of_Emp(sid, dd, emp, new Callback<JsonElement>() {
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
                            Toast.makeText(getContext(), "No Location Found...", Toast.LENGTH_LONG).show();
                        } else {
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();
                            pDialog.hide();
                            displayData();
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

    private void displayData() {
        try {

            map.clear();
            mRealm = Realm.getDefaultInstance();
            final RealmResults<POJO_LatLon_Map> result_query1 = mRealm.where(POJO_LatLon_Map.class).findAll();//.sort("modified", Sort.DESCENDING);
            List<POJO_LatLon_Map> POJO = result_query1;
            Double Latitude = 0.00;
            Double Longitude = 0.00;

            /*for (int i = 0; i < POJO.size(); i++) {
                Latitude = Double.parseDouble(POJO.get(i).getlatitude().toString());
                Longitude = Double.parseDouble(POJO.get(i).getLongitude().toString());
                String name = POJO.get(i).getSubject().toString();
                String time = POJO.get(i).getTime_call().toString();
                String ss = "";
                if (Latitude > 0 && Longitude > 0) {
                    *//*LatLng point = new LatLng(Latitude, Longitude);
                    drawMarker(point);*//*
                    map.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude))
                            .title(name + " " + time).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 8));
                    int j = 0;
                    j = i + 1;
                    ss += String.valueOf(j) + ". " + "[" + POJO.get(i).getTime_call().toString() + "]: " + POJO.get(i).getSubject().toString() + "<br />";
                }
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText(Html.fromHtml(ss));
                //txt_presenty.setText(Html.fromHtml(ss));
            }*/
            String ss = "";
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
                    int j = 0;
                    j = i + 1;
                    ss += String.valueOf(j) + ". " + "[" + POJO.get(i).getTime_call().toString() + "]: " + POJO.get(i).getSubject().toString() + "<br />";
                }
                //txt_presenty.setText(Html.fromHtml(ss));
            }
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.VISIBLE);
            txt_loading.setText(Html.fromHtml(ss));

            if (POJO.size() < 1) {
                Toast.makeText(getContext(), "No Location Found...", Toast.LENGTH_LONG).show();
            }

            SupportMapFragment fm = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map));
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            ViewGroup.LayoutParams params = fm.getView().getLayoutParams();
            params.height = (int) (dpHeight * 75 / 100);
            fm.getView().setLayoutParams(params);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


        /*Marker marker;

        // Removes all markers, overlays, and polylines from the map.
        googleMap.clear();
        markerList.clear();

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL), 2000, null);

        // Add marker of user's position
        MarkerOptions userIndicator = new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("You are here")
                .snippet("lat:" + lat + ", lng:" + lng);
        marker = googleMap.addMarker(userIndicator);
//        Log.e(TAG, "Marker id '" + marker.getId() + "' added to list.");
        markerList.add(marker);

        // Add marker of venue if there is any
        if (venueList != null) {
            for (Venue venue : venueList) {
                String guys = venue.getMaleCount();
                String girls = venue.getFemaleCount();
                String checkinStatus = venue.getCan_checkin();
                if (checkinStatus.equalsIgnoreCase("true"))
                    checkinStatus = "Checked In - ";
                else
                    checkinStatus = "";

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(venue.getLatitude()), Double.parseDouble(venue.getLongitude())))
                        .title(venue.getName())
                        .snippet(checkinStatus + "Guys:" + guys + " and Girls:" + girls)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_orange_pin));

                marker = googleMap.addMarker(markerOptions);
//                Log.e(TAG, "Marker id '" + marker.getId() + "' added to list.");
                markerList.add(marker);
            }
        }

        // Move the camera instantly to where lat and lng shows.
        if (lat != 0 && lng != 0)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), ZOOM_LEVEL));*/

    }

    private void show_dialog_for_select_employee() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                // if(dialog1.isVisible()==false) {
                dialog1 = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "OBJ");
                //dialog.setView(layout);
                dialog1.setArguments(bundle);
                dialog1.setTargetFragment(fragment_employee_location_show.this, 300);
                dialog1.show(getFragmentManager(), "fdf");
                // }
            }
            mLastClickTime = SystemClock.elapsedRealtime();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_date() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_employee_location_show.this, 300);
                dialog.show(getFragmentManager(), "fdf");

                //DatePickerFragment dialog = new DatePickerFragment();
                //dialog.show(getSupportFragmentManager(), DIALOG_DATE);

           /*user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");
            final Bundle bundle = new Bundle();
            bundle.putString("stcokiest", "N");
            dialog.setArguments(bundle);
            dialog.setTargetFragment(fragment_objective_list.this, 300);
            dialog.show(getFragmentManager(), "fdf");*/
            }
            mLastClickTime = SystemClock.elapsedRealtime();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void alert_box() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");

            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Fragment frag = new fragment_Hierarchy_Users_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("drcall_list");
                        ft.commit();

                        dialog.dismiss();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        return false;
    }


    GoogleMap map;//googleMap;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    List<POJO_LatLon_Map> POJO;

    @Override
    public void onMapReady(GoogleMap map) {
        try {
            //DO WHATEVER YOU WANT WITH GOOGLEMAP
            this.map = map;
            map.clear();
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
            if (POJO.size() < 1) {
                Toast.makeText(getContext(), "No Location Found...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }



    /*mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_LatLon_Map.class);
            mRealm.commitTransaction();
            mRealm.close();*/


    /*double latval = Double.parseDouble(jsonobject.getString("lat"));
    double longval = Double.parseDouble(jsonobject.getString("lon"));

mMap.addMarker(new MarkerOptions()
               .position(new LatLng( latval,    longval))
            .title(jsonobject.getString("country"))
            .snippet("4 E. 28TH Street From $15 /per night")
               .rotation((float) -15.0)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            );

if (i == 0) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latval, longval), 7));
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(latval,longval))
                .radius(5000)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));
    }*/

    private void loaddoctor1(final String dd) {
        try {

            TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("doctor_name");
            jsonArray.put("patch_name");
            jsonArray.put("jwf_with");
            jsonArray.put("jwf_with2");
            jsonArray.put("user_id");
            jsonArray.put("creation");


            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            if (!select_date2.getText().equals("dd/mm/yyyy")) {
                Filter1.put("Doctor Calls");
                Filter1.put("date");
                Filter1.put("=");
                Filter1.put(dd);

                //if (select_employee2.getText().equals("MY DR. CALL") ) {
                Filter2.put("Doctor Calls");
                Filter2.put("dr_call_by_user_id");
                Filter2.put("=");
                Filter2.put(name_objective_of_employee.getText().toString());
                Filters.put(Filter1);
                Filters.put(Filter2);

            }

            pDialog.show();
            /*restService.getService().getDoctor_Calls(sid, "doctor_name asc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Doctor_Calls_S>>() {
                        }.getType();
                        List<POJO_Doctor_Calls_S> POJO = gson.fromJson(j2, type);

                        if (POJO.size() == 0) {
                            datafull = true;
                            //Bind_data_listview();

                        } else {
                            limitstart = limitstart + pagesize;
                        }

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();

                        if (datafull == false) {
                            loadMap_LatLon(dd);
                        } else {
                            // Bind_data_listview();
                            pDialog.hide();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();
                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);

                            Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
