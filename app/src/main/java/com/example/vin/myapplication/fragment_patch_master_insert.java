package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_patch_master_insert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_patch_master_insert extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Realm mRealm;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RestService restService;
    EditText txt_patch_name;
    EditText txt_hq_name;
    TextView txt_patch_id;
    String User = "", user_name = "";
    Button btn_save_patch;

    private ProgressDialog pDialog;


    public fragment_patch_master_insert() {
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
        return inflater.inflate(R.layout.fragment_patch_master_insert, container, false);
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

            //Insert And Update Only Designation-TBM Employee
            CHECK_ABM_RBM_ZBM_SM();
            btn_save_patch.setText("SAVE");
            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                ((DashBord_main) getActivity()).setActionBarTitle("UPDATE PATCH");
                btn_save_patch.setText("UPDATE");
                edit_fill();

            } else {
                ((DashBord_main) getActivity()).setActionBarTitle("ADD PATCH");
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                User = app_preferences.getString("name", "default");
                user_name = app_preferences.getString("first_name", "") + " " + app_preferences.getString("middle_name", "") + " " + app_preferences.getString("last_name", "");
            }

            btn_save_patch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        lock_check();
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
            txt_patch_name = (EditText) getView().findViewById(R.id.txt_patch_name);
            txt_hq_name = (EditText) getView().findViewById(R.id.txt_hq_name);
            btn_save_patch = (Button) getView().findViewById(R.id.btn_save_patch);
            txt_patch_id = (TextView) getView().findViewById(R.id.txt_patch_id);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_fill() {
        try {
            Bundle bundle = this.getArguments();

            txt_patch_id.setText(bundle.getString("name"));
            txt_patch_name.setText(bundle.getString("patch_name"));

            txt_hq_name.setText(bundle.getString("headquarter"));
            btn_save_patch.setText("UPDATE");

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insert_patch(POJO_Patch_master POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().Patch_master_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_patch_master_class(jsonElement);
                        pDialog.hide();

                        Toast.makeText(getContext(), "PATCH ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Patch_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        //frag.setArguments(bundle);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
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
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            //onsession_failure();

                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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

    public void update_patch(POJO_Patch_master POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = txt_patch_id.getText().toString();

            restService.getService().Patch_master_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_patch_master_class(jsonElement);
                        pDialog.hide();
                        Toast.makeText(getContext(), "PATCH UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Patch_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        //frag.setArguments(bundle);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }


                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "PLEASE ENTER VALID DATA", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
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

    public void update_patch_master_class(JsonElement jsonElement) {
        try {
            JsonObject j1 = jsonElement.getAsJsonObject();
            JsonElement j2 = j1.getAsJsonObject("data");
            Gson gson = new Gson();
            Type type = new TypeToken<POJO_Patch_master>() {
            }.getType();
            POJO_Patch_master POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Check Form Lock Or Not*/
    public void lock_check() {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = app_preferences.getString("name", "default");

            restService.getService().getMasterFormLockOrNot(sid, "'" + name + "'", "patch", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("0")) {
                            save_patch();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "0") {
                            save_patch();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Patch...", Toast.LENGTH_SHORT).show();
                        }*/

                    } catch (Exception ex) {
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

    private void save_patch() {
        try {

            /*This code Cut From button Click Event*/

            final Bundle bundle = this.getArguments();

            POJO_Patch_master POJO = new POJO_Patch_master();

            POJO.setPatch_name(txt_patch_name.getText().toString());

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String hq = (app_preferences.getString("headquarter", "default"));
            POJO.setHeadquarter(hq);

            pDialog.show();
            if (Validation(POJO) == true) {
                if (bundle != null) {
                    update_patch(POJO);
                } else {
                    POJO.setUser(User);
                    POJO.setUser_name(user_name);
                    insert_patch(POJO);
                }
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean Validation(POJO_Patch_master POJO) {
        try {
            if (POJO.getPatch_name().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "PATCH NAME IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 3", Toast.LENGTH_SHORT).show();
                return false;
            } /*else if (POJO.getPatch_type().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "SELECT ONE PATCH TYPE ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getKm() == null) {
                Toast.makeText(getContext(), "PLEASE ENTER PATCH KM ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE WITH THIS PATCH ..YOU ARE NOT ASSIGNED EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getHeadquarter().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "Enter HQ Name Must Be greather than 3 words ", Toast.LENGTH_SHORT).show();
                return false;
            } */ else
                return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void CHECK_ABM_RBM_ZBM_SM() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));
            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM")) {
                btn_save_patch.setVisibility(View.INVISIBLE);
                /*btn_add_patch.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
            if (designation.equals("TBM")) {
                btn_save_patch.setVisibility(View.VISIBLE);
                /*btn_add_patch.setVisibility(View.VISIBLE);
                btn_delete_patch.setVisibility(View.VISIBLE);*/
            } else {
                btn_save_patch.setVisibility(View.INVISIBLE);
                /*btn_add_patch.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
