package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.MoviesAdapter.context;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_user_lock#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_user_lock extends Fragment
        implements fragment_dialog_for_user_lock_master.ParamUserMasterLockListener,
        fragment_dialog_for_user_lock_set_time.ParamUserLockSetTimeListener,
        fragment_dialog_for_user_lock_set_from_to_date.ParamUserLockSetDateFromToListener,
        fragment_dialog_for_user_activation_deactivation.ParamUserStatusListener {
    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    ProgressDialog pDialog;

    public POJO_User_S last_POJO;
    public adapter_chemist_calls adapter;
    public String designation = "", employeename = "ALL", User_ID_List_String = "", my_id = "";
    private OnFragmentInteractionListener mListener;

    private long mLastClickTime = 0;

    LocationManager locationManager;
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;

    Bundle bundle;
    TextView name_flag_of_user;
    LinearLayout ll_master_form;
    ImageButton img_lock_mast_prof;
    ImageButton img_open_mast_prof;
    ImageButton img_lock_mast_patch;
    ImageButton img_open_mast_patch;
    ImageButton img_lock_mast_doc;
    ImageButton img_open_mast_doc;
    ImageButton img_lock_mast_chem;
    ImageButton img_open_mast_chem;
    //no imp only txt colour
    TextView txt_mast_prof;
    TextView txt_mast_patch;
    TextView txt_mast_doc;
    TextView txt_mast_chem;
    LinearLayout ll_today_form;
    TextView txt_increment_today_time;

    //txt colour red/green

    LinearLayout ll_from_to_form;
    TextView txt_from_to; //txt colour red/green

    LinearLayout ll_reset_all_form;
    TextView txt_reset_all;

    LinearLayout ll_active_user_form;
    TextView txt_active_user;

    Integer mast_prof = 0;
    Integer mast_patch = 0;
    Integer mast_doc = 0;
    Integer mast_chem = 0;
    Integer trans_obj = 0;
    Integer trans_doc = 0;
    Integer trans_chem = 0;
    Integer active_flag = 0;

    /*reset all users parameter*/
    Integer profile_master = 0;
    Integer patch_master = 0;
    Integer doctor_master = 0;
    Integer chemist_master = 0;
    String objective_lock_time = "", doctor_start_time = "", chemist_start_time = "";

    public Integer allow_user_for_user_form = 0;

    public fragment_user_lock() {
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
    public static fragment_user_lock newInstance(String param1, String param2) {
        fragment_user_lock fragment = new fragment_user_lock();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFinishParamUserMasterLock(String flag, String name, String select_user, String select_date, String dcr, String camp, String meeting, String lve) {
        try {
            if (flag.equals("Y")) {
                load_user_data(bundle.getString("user"));
                pDialog.hide();
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishParamUserLockSetTime(String flag) {
        try {
            if (flag.equals("Y")) {
                load_user_data(bundle.getString("user"));
                pDialog.hide();
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishParamUserLockSetDateFromTo(String flag) {
        try {
            if (flag.equals("Y")) {
                load_user_data(bundle.getString("user"));
                pDialog.hide();
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishParamUserStatus(String flag) {
        try {
            if (flag.equals("Y")) {
                load_user_data(bundle.getString("user"));
                pDialog.hide();
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_lock, container, false);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            bundle = this.getArguments();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            allow_user_for_user_form = Integer.valueOf(app_preferences.getString("allow_user_for_user_form", "0"));

            if (allow_user_for_user_form == 1) {

                if (bundle != null) {
                    ((DashBord_main) getActivity()).setActionBarTitle("LOCK MASTER");
                    pDialog = new ProgressDialog(getContext());
                    load_user_data(bundle.getString("user"));

                } else {
                    /*Toast.makeText(getContext(), "RESTRICTED ROUT", Toast.LENGTH_LONG).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }*/
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    if (manager.getBackStackEntryCount() > 0) {
                        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1);
                        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            } else {
                Toast.makeText(getContext(), "RESTRICTED ROUT", Toast.LENGTH_LONG).show();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                if (manager.getBackStackEntryCount() > 0) {
                    FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - (manager.getBackStackEntryCount() - 1));
                    manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void load_user_data(final String name) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            //final String name = "sunilkumbhar721@gmail.com";//app_preferences.getString("name", "default");

            restService.getService().getUserLockData(sid, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        mast_prof = Integer.valueOf(j2.get("lock_prof").getAsString());
                        mast_patch = Integer.valueOf(j2.get("lock_patch").getAsString());
                        mast_doc = Integer.valueOf(j2.get("lock_doc").getAsString());
                        mast_chem = Integer.valueOf(j2.get("lock_chem").getAsString());
                        trans_obj = Integer.valueOf(j2.get("trans_obj").getAsString());
                        trans_doc = Integer.valueOf(j2.get("trans_doc").getAsString());
                        trans_chem = Integer.valueOf(j2.get("trans_chem").getAsString());
                        active_flag = Integer.valueOf(j2.get("active_flag").getAsString());

                        init_control();
                        loadevents(name);

                        pDialog.hide();

                       /* if (lock_flag.equals("1")) {
                            save_doctor_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }*/

                    } catch (Exception ex) {
                        pDialog.hide();
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "PLEASE TRY AGAIN...", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            //onsession_failure();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_control() {
        try {

            //no imp only txt colour
            name_flag_of_user = (TextView) getView().findViewById(R.id.name_flag_of_user);
            ll_master_form = (LinearLayout) getView().findViewById(R.id.ll_master_form);
            img_lock_mast_prof = (ImageButton) getView().findViewById(R.id.img_lock_mast_prof);
            img_open_mast_prof = (ImageButton) getView().findViewById(R.id.img_open_mast_prof);
            img_lock_mast_patch = (ImageButton) getView().findViewById(R.id.img_lock_mast_patch);
            img_open_mast_patch = (ImageButton) getView().findViewById(R.id.img_open_mast_patch);
            img_lock_mast_doc = (ImageButton) getView().findViewById(R.id.img_lock_mast_doc);
            img_open_mast_doc = (ImageButton) getView().findViewById(R.id.img_open_mast_doc);
            img_lock_mast_chem = (ImageButton) getView().findViewById(R.id.img_lock_mast_chem);
            img_open_mast_chem = (ImageButton) getView().findViewById(R.id.img_open_mast_chem);

            if (active_flag == 1) {
                if (mast_prof == 1) {
                    img_lock_mast_prof.setVisibility(View.VISIBLE);
                    img_open_mast_prof.setVisibility(View.GONE);
                } else {
                    img_lock_mast_prof.setVisibility(View.GONE);
                    img_open_mast_prof.setVisibility(View.VISIBLE);
                }

                if (mast_patch == 1) {
                    img_lock_mast_patch.setVisibility(View.VISIBLE);
                    img_open_mast_patch.setVisibility(View.GONE);
                } else {
                    img_lock_mast_patch.setVisibility(View.GONE);
                    img_open_mast_patch.setVisibility(View.VISIBLE);
                }

                if (mast_doc == 1) {
                    img_lock_mast_doc.setVisibility(View.VISIBLE);
                    img_open_mast_doc.setVisibility(View.GONE);
                } else {
                    img_lock_mast_doc.setVisibility(View.GONE);
                    img_open_mast_doc.setVisibility(View.VISIBLE);
                }

                if (mast_chem == 1) {
                    img_lock_mast_chem.setVisibility(View.VISIBLE);
                    img_open_mast_chem.setVisibility(View.GONE);
                } else {
                    img_lock_mast_chem.setVisibility(View.GONE);
                    img_open_mast_chem.setVisibility(View.VISIBLE);
                }
            } else {
                img_lock_mast_prof.setVisibility(View.VISIBLE);
                img_open_mast_prof.setVisibility(View.GONE);
                img_lock_mast_patch.setVisibility(View.VISIBLE);
                img_open_mast_patch.setVisibility(View.GONE);
                img_lock_mast_doc.setVisibility(View.VISIBLE);
                img_open_mast_doc.setVisibility(View.GONE);
                img_lock_mast_chem.setVisibility(View.VISIBLE);
                img_open_mast_chem.setVisibility(View.GONE);
            }


            //no imp only txt colour
            txt_mast_prof = (TextView) getView().findViewById(R.id.txt_mast_prof);
            txt_mast_patch = (TextView) getView().findViewById(R.id.txt_mast_patch);
            txt_mast_doc = (TextView) getView().findViewById(R.id.txt_mast_doc);
            txt_mast_chem = (TextView) getView().findViewById(R.id.txt_mast_chem);


            ll_today_form = (LinearLayout) getView().findViewById(R.id.ll_today_form);
            txt_increment_today_time = (TextView) getView().findViewById(R.id.txt_increment_today_time);

            if (trans_obj == 1 && trans_doc == 1 && trans_chem == 1) {
                txt_increment_today_time.setTextColor(Color.parseColor("#f44336"));
            } else {
                txt_increment_today_time.setTextColor(Color.parseColor("#00c853"));
            }

            //txt colour red/green
            ll_from_to_form = (LinearLayout) getView().findViewById(R.id.ll_from_to_form);
            txt_from_to = (TextView) getView().findViewById(R.id.txt_from_to);

            ll_reset_all_form = (LinearLayout) getView().findViewById(R.id.ll_reset_all_form);
            txt_reset_all = (TextView) getView().findViewById(R.id.txt_reset_all);

            ll_active_user_form = (LinearLayout) getView().findViewById(R.id.ll_active_user_form);
            txt_active_user = (TextView) getView().findViewById(R.id.txt_active_user);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadevents(final String user) {
        try {
            name_flag_of_user.setText(bundle.getString("user_name"));
            ll_master_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (active_flag == 1) {
                        //Toast.makeText(getContext(), "Clicked On Master Form's...", Toast.LENGTH_SHORT).show();
                        //Bundle bundle = new Bundle();
                        bundle.putString("user", user);
                        show_dialog_for_master_form_lock(bundle);
                    } else {
                        Toast.makeText(getContext(), "This User Deactivated...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ll_today_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (active_flag == 1) {
                        //Toast.makeText(getContext(), "Clicked On Time Increment...", Toast.LENGTH_SHORT).show();
                        // Bundle bundle = new Bundle();
                        bundle.putString("user", user);
                        bundle.putString("user_name", bundle.getString("user_name"));
                        show_dialog_for_transaction_time_lock(bundle);
                    } else {
                        Toast.makeText(getContext(), "This User Deactivated...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ll_from_to_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (active_flag == 1) {
                        //Toast.makeText(getContext(), "Clicked On Form To Date...", Toast.LENGTH_SHORT).show();
                        //Bundle bundle = new Bundle();
                        bundle.putString("user", user);
                        bundle.putString("user_name", bundle.getString("user_name"));
                        show_dialog_for_transaction_from_to_date(bundle);
                    } else {
                        Toast.makeText(getContext(), "This User Deactivated...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ll_reset_all_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (active_flag == 1) {*/
                    //Toast.makeText(getContext(), "Reset All User", Toast.LENGTH_SHORT).show();
                    alert_box();
                    /*} else {
                        Toast.makeText(getContext(), "This User Deactivated...", Toast.LENGTH_SHORT).show();
                    }*/
                }
            });

            ll_active_user_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if (active_flag == 1) {
                    //Toast.makeText(getContext(), "ACTIVE/DEACTIVE USER", Toast.LENGTH_SHORT).show();
                    //Bundle bundle = new Bundle();
                    bundle.putString("user", user);
                    String ss = bundle.getString("user_name");
                    bundle.putString("user_name", bundle.getString("user_name"));
                    show_dialog_for_user_activation_deactivation(bundle);
                    /*} else {
                        Toast.makeText(getContext(), "This User Deactivated...", Toast.LENGTH_SHORT).show();
                    }*/
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    private void show_dialog_for_master_form_lock(Bundle bundle) {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_master dialog = fragment_dialog_for_user_lock_master.newInstance("Hello world");
                int width = getContext().getResources().getDisplayMetrics().widthPixels;
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_user_lock.this, width - 30);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_transaction_time_lock(Bundle bundle) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_set_time dialog = fragment_dialog_for_user_lock_set_time.newInstance("Hello world");
                int width = getContext().getResources().getDisplayMetrics().widthPixels;
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_user_lock.this, width - 30);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_transaction_from_to_date(Bundle bundle) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_set_from_to_date dialog = fragment_dialog_for_user_lock_set_from_to_date.newInstance("Hello world");
                int width = getContext().getResources().getDisplayMetrics().widthPixels;
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_user_lock.this, width - 30);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_user_activation_deactivation(Bundle bundle) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_activation_deactivation dialog = fragment_dialog_for_user_activation_deactivation.newInstance("Hello world");
                int width = getContext().getResources().getDisplayMetrics().widthPixels;
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_user_lock.this, width - 30);
                dialog.show(getFragmentManager(), "fdf");
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
            builder.setMessage("DO YOU WANT TO SET DEFAULT SETTING FOR ALL USER'S");

            //AGAIN TO DEAFULT TIME AND RESET FROM-TO DATES FOR ALL USER'S
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    reset_all_user_time_date();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reset_all_user_time_date() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("profile_master");
            jsonArray.put("patch_master");
            jsonArray.put("doctor_master");
            jsonArray.put("chemist_master");
            jsonArray.put("objective_lock_time");
            jsonArray.put("doctor_start_time");
            jsonArray.put("chemist_start_time");

            /*
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user_id);

            Filters.put(Filter1);
            */

            pDialog.show();
            restService.getService().getStd_Lock_time_date(sid, 0, 1, jsonArray, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Standard_Lock_Time>>() {
                        }.getType();
                        List<POJO_Standard_Lock_Time> POJO = gson.fromJson(j2, type);

                        for (POJO_Standard_Lock_Time pp : POJO) {
                            profile_master = pp.getProfile_master();
                            patch_master = pp.getPatch_master();
                            doctor_master = pp.getDoctor_master();
                            chemist_master = pp.getChemist_master();
                            objective_lock_time = pp.getObjective_lock_time();
                            doctor_start_time = pp.getDoctor_start_time();
                            chemist_start_time = pp.getChemist_start_time();
                        }

                        update_user_lock_time_date();
                        // pDialog.hide();
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

                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();

                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_user_lock_time_date() {
        try {
            // pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            //String ss = "update `tabUser` set m_pro=" + profile_master + ",m_pat=" + patch_master + ",m_doc=" + doctor_master + ",m_che=" + chemist_master + ",t_obj_time=" + objective_lock_time + ",t_drc_s_time=" + doctor_start_time + ",t_chc_s_time=" + chemist_start_time + ",t_drc1=" + "" + ",t_drc2=" + "" + ",t_obj1=" + "" + ",t_obj2=" + "" + ",t_chc1=" + "" + ",t_chc2=" + "" + " where enabled=1 and designation in ('TBM','ABM','RBM','SM','NBM')";

            restService.getService().putUser_lock_time_date(sid, profile_master, patch_master, doctor_master, chemist_master, "'" + objective_lock_time + "'", "'" + doctor_start_time + "'", "'" + chemist_start_time + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        Integer flag = Integer.valueOf(j2.get("flag").getAsString());
                        Toast.makeText(getContext(), "RESET ALL USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            // onsession_failure();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

/*

name_flag_of_user,
img_lock_mast_prof,
img_open_mast_prof,
img_lock_mast_patch,
img_open_mast_patch,
img_lock_mast_doc,
img_open_mast_doc,
img_lock_mast_chem,
img_open_mast_chem,
//no imp only txt colour
txt_mast_prof,
txt_mast_patch,
txt_mast_doc,
txt_mast_chem,

ll_today_form,
txt_increment_today_time //txt colour red/green

ll_from_to_form,
txt_from_to //txt colour red/green

*/
