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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;

/*import static com.example.vin.myapplication.DashBord_main.task2;
import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;
import static com.example.vin.myapplication.DashBord_main.task_Employee;
import static com.example.vin.myapplication.DashBord_main.task_Patch_Master;*/

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_chemist_master_insert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chemist_master_insert extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Realm mRealm;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tv_user;

    private OnFragmentInteractionListener mListener;

    RestService restService;
    Button btn_save;

    TextView tv_name;
    TextView tv_headquarter;
    EditText txt_chemist_name;
    EditText txt_address;
    EditText txt_city;
    EditText txt_pin_code;
    EditText txt_contact_no;
    EditText txt_contact_person;


    //Spinner spinner_headquarter;

    Bundle bundle;


    private ProgressDialog pDialog;


    public fragment_chemist_master_insert() {
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
    public static fragment_chemist_master_insert newInstance(String param1, String param2) {
        fragment_chemist_master_insert fragment = new fragment_chemist_master_insert();
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
        return inflater.inflate(R.layout.fragment_chemist_master_insert, container, false);
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
            CHECK_ABM_RBM_ZBM_SM();
            bundle = this.getArguments();
            if (bundle != null) {
                ((DashBord_main) getActivity()).setActionBarTitle("UPDATE CHEMIST");
                btn_save.setText("UPDATE");
                edit_fill();
            } else {
                ((DashBord_main) getActivity()).setActionBarTitle("ADD CHEMIST");
                btn_save.setText("SAVE");
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                tv_user.setText(app_preferences.getString("name", "default"));
                tv_headquarter.setText(app_preferences.getString("headquarter_name", "default"));
            }

            btn_save.setOnClickListener(new View.OnClickListener() {
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
            //   linear_layout_doc = (LinearLayout) getView().findViewById(R.id.linear_layout_doc);
            txt_chemist_name = (EditText) getView().findViewById(R.id.txt_chemist_name);
            txt_chemist_name.setSingleLine(true);

            tv_headquarter = (TextView) getView().findViewById(R.id.tv_headquarter);

            txt_address = (EditText) getView().findViewById(R.id.txt_address);
            txt_address.setSingleLine(true);

            txt_city = (EditText) getView().findViewById(R.id.txt_city);
            txt_city.setSingleLine(true);

            txt_pin_code = (EditText) getView().findViewById(R.id.txt_pin_code);
            txt_pin_code.setSingleLine(true);

            txt_contact_no = (EditText) getView().findViewById(R.id.txt_contact_no);
            txt_contact_no.setSingleLine(true);

            txt_contact_person = (EditText) getView().findViewById(R.id.txt_contact_person);
            txt_contact_person.setSingleLine(true);

            btn_save = (Button) getView().findViewById(R.id.btn_save);

            tv_name = (TextView) getView().findViewById(R.id.tv_name);
            tv_user = (TextView) getView().findViewById(R.id.tv_user);
            ///txt_emp_name = (TextView) getView().findViewById(R.id.txt_emp_name);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void edit_fill() {
        try {

            Bundle bundle = this.getArguments();
            String name = bundle.getString("name");
            tv_name.setText(bundle.getString("name"));

            pDialog.show();
            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("city");
            jsonArray.put("modified_by");
            jsonArray.put("name");
            jsonArray.put("chemist_name");
            jsonArray.put("creation");
            jsonArray.put("modified");
            jsonArray.put("headquarter");
            jsonArray.put("headquarter_name");
            jsonArray.put("contact_no");
            jsonArray.put("idx");
            //jsonArray.put("doctype");
            jsonArray.put("user");
            jsonArray.put("address");
            jsonArray.put("owner");
            jsonArray.put("docstatus");
            jsonArray.put("contact_person");
            jsonArray.put("pin_code");
            jsonArray.put("user_name");

            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("Chemist Master");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(tv_name.getText());

            Filters.put(Filter1);


            restService.getService().getChemist_Update_data(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Chemist>>() {
                        }.getType();
                        List<POJO_Chemist> POJO = gson.fromJson(j2, type);

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();

                        if (tv_name.getText().toString().length() > 0) {
                            Bind__data(tv_name.getText().toString());
                        }
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

                        //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        //SharedPreferences.Editor editor = app_preferences.edit();
                        //editor.putString("status", "0");
                        //editor.commit();

                        //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                        //getContext().startActivity(k);
                        Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                    }

                    if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind__data(String name) {
        try {
            // String Patch_type = "";
            Realm mRealm = Realm.getDefaultInstance();

            final POJO_Chemist POJO = mRealm.where(POJO_Chemist.class).equalTo("name", name).findFirst();

            tv_name.setText(POJO.getName());

            tv_name.setText(POJO.getName());
            txt_chemist_name.setText(POJO.getChemist_name());
            tv_headquarter.setText(POJO.getHeadquarter_name());//+ "(" + POJO.getHeadquarter() + ")"
            txt_address.setText(POJO.getAddress());
            txt_city.setText(POJO.getCity());
            txt_pin_code.setText(POJO.getPin_code());
            txt_contact_no.setText(POJO.getContact_no());
            txt_contact_person.setText(POJO.getContact_person());

            tv_user.setText(POJO.getUser());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_chemist(POJO_Chemist POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = bundle.getString("name").toString();

            restService.getService().chemist_master_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_chemist_master_class(jsonElement);
                        pDialog.hide();
                        Toast.makeText(getContext(), "CHEMIST UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Chemist_List();
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
                    pDialog.hide();
                    //Toast.makeText(getContext(), "PLEASE ENTER VALID DATA", Toast.LENGTH_SHORT).show();
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
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insert_chemist(POJO_Chemist POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().chemist_master_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_chemist_master_class(jsonElement);
                        pDialog.hide();

                        Toast.makeText(getContext(), "CHEMIST ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Chemist_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);

                        ft.commit();
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

    public void update_chemist_master_class(JsonElement jsonElement) {
        try {
            JsonObject j1 = jsonElement.getAsJsonObject();
            //   JsonArray j2 = j1.getAsJsonArray("data");
            JsonElement j2 = j1.getAsJsonObject("data");
            Gson gson = new Gson();
            Type type = new TypeToken<POJO_Chemist>() {
            }.getType();
            POJO_Chemist POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean Validation(POJO_Chemist POJO) {
        try {
            if (POJO.getChemist_name().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "CHEMIST NAME IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getAddress().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "ADDRESS IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5 ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getCity().toString().trim().length() < 2) {
                Toast.makeText(getContext(), "CITY IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPin_code().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "PINCODE IS NOT EMPTY  ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getContact_no().toString().trim().length() < 9) {
                Toast.makeText(getContext(), "CONTACT NUMBER IS NOT EMPTY  AND LENGHT MUST BE GRATER THAN 9 ", Toast.LENGTH_SHORT).show();
                return false;

            } /*else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE WITH THIS PATCH ..YOU ARE NOT ASSIGNED EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            }*/ else
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
                btn_save.setVisibility(View.INVISIBLE);
                /*btn_add_doc.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
            if (designation.equals("TBM")) {
                btn_save.setVisibility(View.VISIBLE);
                /*btn_add_doc.setVisibility(View.VISIBLE);
                btn_delete_patch.setVisibility(View.VISIBLE);*/
            } else {
                btn_save.setVisibility(View.INVISIBLE);
                /*btn_add_doc.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
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

            restService.getService().getMasterFormLockOrNot(sid, "'" + name + "'", "chemist", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("0")) {
                            save_chemist();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "0") {
                            save_chemist();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Chemist...", Toast.LENGTH_SHORT).show();
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

    private void save_chemist() {
        try {

            /*This code Cut From button Click Event*/

            POJO_Chemist POJO = new POJO_Chemist();
            // POJO.setName(txt_patch_id.getText().toString());
            POJO.setChemist_name(txt_chemist_name.getText().toString());

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            POJO.setHeadquarter(app_preferences.getString("headquarter", "default"));
            POJO.setHeadquarter_name(app_preferences.getString("headquarter_name", "default"));
            POJO.setAddress(txt_address.getText().toString());
            POJO.setCity(txt_city.getText().toString());
            POJO.setPin_code(txt_pin_code.getText().toString());
            POJO.setContact_no(txt_contact_no.getText().toString());
            POJO.setContact_person(txt_contact_person.getText().toString());

            POJO.setUser(tv_user.getText().toString());
            POJO.setUser_name(app_preferences.getString("first_name", "default") + " " + app_preferences.getString("middle_name", "default") + " " + app_preferences.getString("last_name", "default"));
            //POJO.setName((tv_name.getText().toString()==null)?"":tv_name.getText().toString());


            //  pDialog.show();
            if (Validation(POJO) == true) {
                if (bundle != null) {
                    POJO.setName((tv_name.getText().toString() == null) ? "" : tv_name.getText().toString());
                    update_chemist(POJO);
                } else {
                    POJO.setName("");
                    insert_chemist(POJO);
                }
            } else {
                //  pDialog.hide();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}