package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

//import static com.example.vin.myapplication.R.layout.fragment_daily_plan_insert_temp;
//import static com.example.vin.myapplication.R.layout.fragment_daily_plan_insert_temp1;
//import static com.example.vin.myapplication.R.layout.fragment_daily_plan_insert_2;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_daily_plan_insert_temp1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_daily_plan_insert_temp1 extends Fragment
        implements DatePickerFragment.DateDialogListener, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int i = 0;

    private OnFragmentInteractionListener mListener;
    RestService restService;
    ProgressDialog pDialog;
    Bundle bundle = null;

    Context context;
    TextView dateDisplay1;
    CheckBox chkBoxDCR;
    CheckBox chkBoxcme;
    CheckBox chkBoxcamp;
    CheckBox chkBoxmeeting;
    CheckBox chkBoxleave;
    Button btn_submit1, btn_submit2;
    String DCR = "";
    String CME;
    String CAMP;
    String MEETING;
    int flag_user = 0;
    String select_user = "", user_id = "";

    TextView txt_plan_of_date;
    ImageButton select_date3;

    Double lat = 0.0, lon = 0.0;
    int gps_enable_flag = 0;
    LocationManager locationManager;

    public fragment_daily_plan_insert_temp1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_daily_plan_insert_temp1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_daily_plan_insert_temp1 newInstance(String param1, String param2) {
        fragment_daily_plan_insert_temp1 fragment = new fragment_daily_plan_insert_temp1();
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
        return inflater.inflate(R.layout.fragment_daily_plan_insert_temp, container, false);
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
    public void onStop() {
        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("plan_date", txt_plan_of_date.getText().toString());
        editor.commit();
        super.onStop();
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
    public void onStart() {
        super.onStart();
        try {
            pDialog = new ProgressDialog(getContext());

            ((DashBord_main) getActivity()).setActionBarTitle("PLAN");
            init_controls();

            GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp1.this, false);
            if (gps.canGetLocation()) {

                lat = gps.getLatitude();
                lon = gps.getLongitude();

            } else {
                gps.showSettingsAlert();
            }


            bundle = this.getArguments();
            if (bundle != null) {
                edit_fill();
                flag_user = 1;
                select_user = bundle.getString("select_user");
                get_todays_objective(bundle.getString("select_date"));
                //get_todays_objective(txt_plan_of_date.getText().toString());

            } else {
                loaddate();
            }

            /*New Code Date:09/12/2017*/
            txt_plan_of_date.setOnClickListener(new View.OnClickListener() {
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
            /*select_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });*/
            /*New Code Date:09/12/2017*/

            btn_submit1 = (Button) getView().findViewById(R.id.btn_submit1);
            btn_submit2 = (Button) getView().findViewById(R.id.btn_submit2);
            btn_submit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp1.this, false);
                        if (gps.canGetLocation()) {

                            lat = gps.getLatitude();
                            lon = gps.getLongitude();

                            After_submit();
                        } else {
                            gps.showSettingsAlert();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_submit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp1.this, false);
                        if (gps.canGetLocation()) {

                            lat = gps.getLatitude();
                            lon = gps.getLongitude();

                            After_submit();
                        } else {
                            gps.showSettingsAlert();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void init_controls() {
        try {
            dateDisplay1 = (TextView) getView().findViewById(R.id.date_display1);
            chkBoxDCR = (CheckBox) getView().findViewById(R.id.chkBoxDCR);
            chkBoxcme = (CheckBox) getView().findViewById(R.id.chkBoxcme);
            chkBoxcamp = (CheckBox) getView().findViewById(R.id.chkBoxcamp);
            chkBoxmeeting = (CheckBox) getView().findViewById(R.id.chkBoxmeeting);
            chkBoxleave = (CheckBox) getView().findViewById(R.id.chkBoxleave);
            btn_submit1 = (Button) getView().findViewById(R.id.btn_submit1);
            btn_submit2 = (Button) getView().findViewById(R.id.btn_submit2);


            txt_plan_of_date = (TextView) getView().findViewById(R.id.txt_plan_of_date);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);/**/

            chkBoxDCR.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chkBoxcme.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chkBoxcamp.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chkBoxmeeting.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chkBoxleave.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();
            try {
                if (isChecked) {
                    if (buttonView == chkBoxDCR) {
                        Remove_leave();
                        //showTextNotification("chkBoxDCR");
                    }

                    if (buttonView == chkBoxcme) {
                        Remove_leave();
                        //showTextNotification("chkBoxcme");
                    }

                    if (buttonView == chkBoxcamp) {
                        Remove_leave();
                        //  showTextNotification("chkBoxcamp");
                    }

                    if (buttonView == chkBoxmeeting) {
                        Remove_leave();
                        // showTextNotification("chkBoxmeeting");
                    }

                    if (buttonView == chkBoxleave) {
                        Remove_work();
                        //  showTextNotification("chkBoxleave");
                    }
                }
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Remove_leave() {
        try {
            chkBoxleave.setChecked(false);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Remove_work() {
        try {
            chkBoxDCR.setChecked(false);
            chkBoxcme.setChecked(false);
            chkBoxcamp.setChecked(false);
            chkBoxmeeting.setChecked(false);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_fill() {
        try {
            chkBoxDCR.setChecked(bundle.getString("dcr").equals("1"));
            //chkBoxcme.setChecked(bundle.getString("cme").equals("1"));
            chkBoxcamp.setChecked(bundle.getString("camp").equals("1"));
            chkBoxmeeting.setChecked(bundle.getString("meeting").equals("1"));
            chkBoxleave.setChecked(bundle.getString("lve").equals("1"));

            txt_plan_of_date.setText(bundle.getString("select_date"));
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void After_submit() {
        try {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putString("name", "0");
                bundle.putString("pl_date", txt_plan_of_date.getText().toString());
            }
            if (bundle.get("name").equals("0")) {
                bundle.putString("pl_date", txt_plan_of_date.getText().toString());
            }
            bundle.putString("dcr", chkBoxDCR.isChecked() == true ? "1" : "0");
            //bundle.putString("cme", chkBoxcme.isChecked() == true ? "1" : "0");
            bundle.putString("cme", "0");
            bundle.putString("camp", chkBoxcamp.isChecked() == true ? "1" : "0");
            bundle.putString("meeting", chkBoxmeeting.isChecked() == true ? "1" : "0");
            bundle.putString("lve", chkBoxleave.isChecked() == true ? "1" : "0");

            if (chkBoxDCR.isChecked() == true || chkBoxcamp.isChecked() == true || chkBoxmeeting.isChecked() == true || chkBoxleave.isChecked() == true) {
                Fragment frag = new fragment_daily_plan_insert_temp2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, frag);
                frag.setArguments(bundle);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("fragment_daily_plan_edit_2");

                ft.commit();
            } else {
                Toast.makeText(getContext(), "PLEASE SELECT ANY ONE PLAN...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loaddate() {
        try {
            txt_plan_of_date = (TextView) getView().findViewById(R.id.txt_plan_of_date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            txt_plan_of_date.setText(sdf.format(date));
            get_todays_objective(txt_plan_of_date.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void get_todays_objective(String dd) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            if (flag_user == 1) {
                user_id = select_user;
            } else {
                user_id = app_preferences.getString("name", "default");
            }
            final String sss = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");
            jsonArray.put("user");
            jsonArray.put("user_name");
            jsonArray.put("creation");
            jsonArray.put("modified");
            jsonArray.put("modified_by");
            jsonArray.put("owner");
            jsonArray.put("docstatus");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_flag");
            jsonArray.put("leave_flag");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            //name,select_date,user,user_name,select_patch_name,select_patch,dcr_jfw_with1,dcr_jfw_with1_name,dcr_jfw_with2,
            // dcr_jfw_with2_name,
            //call_agenda,doctor_flag,meeting_with,place,meeting_agenda,meeting_flag,doctor,doctor_name,camp_jfw_with1,
            // camp_jfw_with_name1,
            //camp_jfw_with2,camp_jfw_with_name2,camp_agenda,camp_flag,leave_type1,leave_type2,leave_type3,reason,
            // leave_flag,leave_approval,
            //leave_approved_by,plan_approve,plan_approved_by

            Filter1.put("Objective");
            Filter1.put("user");
            Filter1.put("=");
            Filter1.put(user_id);
            //Filter1.put("GOKULBORADE11@GMAIL.COM");


            Filter2.put("Objective");
            Filter2.put("select_date");
            Filter2.put("=");
            Filter2.put(dd);


            Filters.put(Filter1);
            Filters.put(Filter2);

            /*Tour Plan Exclude Filter
            JSONArray Filter3 = new JSONArray();
            Filter3.put("Objective");
            Filter3.put("tp_flag");
            Filter3.put("=");
            Filter3.put(0);
            Filters.put(Filter3);*/

            pDialog.show();
            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if (j22.size() <= 0) {
                            bundle = new Bundle();
                            bundle.putString("name", "0");

                            chkBoxDCR.setChecked(false);
                            //chkBoxcme.setChecked(bundle.getString("cme").equals("1"));
                            chkBoxcamp.setChecked(false);
                            chkBoxmeeting.setChecked(false);
                            chkBoxleave.setChecked(false);
                        }

                        if (j22.size() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);


                            if (POJO.size() > 0) {

                                POJO_objective firstA = POJO.get(0);
                                bundle = new Bundle();
                                bundle.putString("name", firstA.getName());
                                bundle.putString("btn_enable_flag", sss.equals(firstA.getUser()) == true ? "0" : "1");

                                chkBoxDCR.setChecked(firstA.getDoctor_flag().equals(1));
                                //chkBoxcme.setChecked(bundle.getString("cme").equals("1"));
                                chkBoxcamp.setChecked(firstA.getCamp_flag().equals(1));
                                chkBoxmeeting.setChecked(firstA.getMeeting_flag().equals(1));
                                chkBoxleave.setChecked(firstA.getLeave_flag().equals(1));
                               /* Object_id = firstA.getName();
                                tv_date.setText(firstA.getSelect_date());

                                txt_objective.setVisibility(View.VISIBLE);
                                txt_objective.setText(firstA.getObjective());

                                btn_submit.setText("SUBMITTED");*/

                            }
                        } else {
                        }
                    } catch (Exception ex) {
                        pDialog.hide();
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
                            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            //txt_loading.setText("ERROR..");
                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();

                        } else if (msg.contains("139.59.63.181")) {

                            //TextView txt_loading = (TextView) getView().findViewById(txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            //txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        pDialog.hide();
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            pDialog.hide();
            //TextView txt_loading = (TextView) getView().findViewById(txt_loading);
            //txt_loading.setVisibility(View.VISIBLE);
            //txt_loading.setText("Check Internet Connection and Try Again..");
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_date() {

        try {

            DatePickerFragment dialog = new DatePickerFragment();
            dialog.setTargetFragment(fragment_daily_plan_insert_temp1.this, 300);
            dialog.show(getFragmentManager(), "fragment_daily_plan_insert_temp1");

            //DatePickerFragment dialog = new DatePickerFragment();
            //dialog.show(getSupportFragmentManager(), DIALOG_DATE);

           /*user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");
            final Bundle bundle = new Bundle();
            bundle.putString("stcokiest", "N");
            dialog.setArguments(bundle);
            dialog.setTargetFragment(fragment_objective_list.this, 300);
            dialog.show(getFragmentManager(), "fdf");*/
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

            txt_plan_of_date.setText(hireDate);
            get_todays_objective(txt_plan_of_date.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }


    /*----------Trace Mobile Location Co-ordinate Start----------------*/

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
        } catch (Exception e) {

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        //gps_enable_flag = 1;
        //Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        //alert_box();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //String ss = provider + status;

    }

    @Override
    public void onProviderEnabled(String provider) {
        //gps_enable_flag = 0;
        //getLocation();
    }

    /*------------Co-ordinate End--------------*/

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

}
