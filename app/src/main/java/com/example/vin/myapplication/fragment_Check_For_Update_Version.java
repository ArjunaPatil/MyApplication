package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_patch_master_insert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Check_For_Update_Version extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;
    RestService restService;

    String User = "", user_name = "";

    EditText txt_current_version;
    Button btn_check_version;

    private ProgressDialog pDialog;


    public fragment_Check_For_Update_Version() {
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
        return inflater.inflate(R.layout.fragment_check_for_update, container, false);
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

            pDialog = new ProgressDialog(getContext());

            init_controls();
            String versionName = BuildConfig.VERSION_NAME;
            txt_current_version.setText(versionName);

            ((DashBord_main) getActivity()).setActionBarTitle("UPDATE VERSION");

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            User = app_preferences.getString("name", "default");
            user_name = app_preferences.getString("first_name", "") + " " + app_preferences.getString("middle_name", "") + " " + app_preferences.getString("last_name", "");

            btn_check_version.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        Get_today_date_and_check_app_version();

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {
            txt_current_version = (EditText) getView().findViewById(R.id.txt_current_version);
            btn_check_version = (Button) getView().findViewById(R.id.btn_check_version);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public boolean temp_flag = false;

    public boolean Get_today_date_and_check_app_version() {

        String versionName = BuildConfig.VERSION_NAME;
        temp_flag = false;

        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = app_preferences.getString("name", "default");
            final String designation = app_preferences.getString("designation", "default");


            restService.getService().getToday_server_and_app_version_Method(sid, "", "'" +versionName+ "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message"); //JsonArray j2 = j1.getAsJsonArray("message");
                        JsonElement jj;

                        temp_flag = true;
                        String app_support = j2.get("app_ver_count").getAsString();
                        if (app_support == "0") {
                            App_old_redirect_to_play_store();
                            temp_flag = false;
                        } else if (app_support == "1") {
                            NoUpdateAvailable();
                            temp_flag = false;
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
                        temp_flag = true;

                        pDialog.hide();

                        // Toast.makeText(getContext(), error.getKind().toString()+">>"+error.getCause().getMessage(), Toast.LENGTH_LONG).show();

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


                            // Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void App_old_redirect_to_play_store() {
        try {
            Toast.makeText(getContext(), "PLEASE UPDATE APP..THIS VERSION IS NOT SUPPORTED FROM NOW", Toast.LENGTH_LONG).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    System.exit(0);
                }
            }, 3000);
            //get.finish();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void NoUpdateAvailable() {
        try {
            /*Dialog Box Code For Class HQ Null*/
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>MESSAGE</font>"));
            builder.setMessage("No Available Any Update For That Version.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

}
