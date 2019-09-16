package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static io.realm.Case.INSENSITIVE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_chemist_call#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chemist_call extends Fragment implements user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener, DatePickerFragment.DateDialogListener {
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
    Context context;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    ProgressDialog pDialog;

    public POJO_User_S last_POJO;
    public adapter_chemist_calls adapter;
    public String designation = "", employeename = "ALL", User_ID_List_String = "", my_id = "";
    private OnFragmentInteractionListener mListener;
    EditText edit_search;

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

    View vw_employee;
    private long mLastClickTime = 0;

    LocationManager locationManager;
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;

    public fragment_chemist_call() {
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
    public static fragment_chemist_call newInstance(String param1, String param2) {
        fragment_chemist_call fragment = new fragment_chemist_call();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chemist_call, container, false);

        return view;

        //  return inflater.inflate(R.layout.fragment_fragment__dash_board, container, false);
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
            mRealm.delete(POJO_chemist_Calls_S.class);
            mRealm.commitTransaction();
            mRealm.close();


            if (id == "ALL") {
                name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
                name_objective_of_employee.setText(my_id);

                select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
                select_employee2.setText("MY DR. CALL");
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
            mRealm.delete(POJO_chemist_Calls_S.class);
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

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("CHEMIST CALL");
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));
            my_id = (app_preferences.getString("name", "default"));
            init_control();
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
            name_objective_of_employee.setText(my_id);

            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            select_employee2.setText("MY CHEM. CALL");
            if (select_date2.getText().equals("dd/mm/yyyy")) {
                loaddate();
            }
            loadevents();
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
       /* mRealm = Realm.getDefaultInstance();

        RealmResults<POJO_Doctor_Calls_S> result_query1 = mRealm.where(POJO_Doctor_Calls_S.class).findAll().sort("doctor_name", Sort.ASCENDING);;//.sort("modified", Sort.DESCENDING);
        if (result_query1.size() > 0) {
            Bind_data_listview();
            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            //txt_loading.setVisibility(View.GONE);
            //txt_loading.setText("Refreshing Data..");
        } else {
            //TextView txt_loading = (TextView) getView().findViewById(txt_loading);
            //txt_loading.setVisibility(View.VISIBLE);
            //txt_loading.setText("Refreshing Data..");
         //   bool_full_update = false;
         //   full_update_calc();
            limitstart = 0;
            datafull = false;
            if (!select_date2.getText().equals("dd/mm/yyyy")) {
                //Load_Objective(select_date2.getText().toString());
                Check_class_user_null_or_not();
            } else {
                Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
            }
        }*/
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_chemist_Calls_S.class);
            mRealm.commitTransaction();
            mRealm.close();


            limitstart = 0;
            Check_class_user_null_or_not();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Check_class_user_null_or_not() {
        try {
            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).findAll();
            ;
            if (POJO_User_Obj.size() > 0) {
                /*for (POJO_User_Hierarchy contact : POJO_User_Obj) {
                    if (!contact.getName().equals("ALL")) {
                        User_ID_List_String = User_ID_List_String + "," + contact.getName();
                    }
                }
                User_ID_List_String = User_ID_List_String.substring(1);*/
                loadchemist(select_date2.getText().toString());
            } else {
                User_ID_List_String = " ";
                /*Dialog Box Code For Class HQ Null*/
                alert_box();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void init_control() {
        try {
            edit_search = (EditText) getView().findViewById(R.id.edit_search);
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


            //date spinner
            /*select_date = (LinearLayout) getView().findViewById(R.id.select_date);*/
            select_date1 = (LinearLayout) getView().findViewById(R.id.select_date1);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            name_date_of_objective = (TextView) getView().findViewById(R.id.name_date_of_objective);

/*            *//*select_date.setVisibility(View.GONE);*//*
            select_date1.setVisibility(View.GONE);
            select_date3.setVisibility(View.GONE);
            select_date2.setVisibility(View.GONE);
            name_date_of_objective.setVisibility(View.GONE);

            //view (line)
            vw_employee = getView().findViewById(R.id.vw_employee);
            vw_employee.setVisibility(View.GONE);*/


            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_objective_of_employee.setVisibility(View.VISIBLE);

                /*select_date.setVisibility(View.VISIBLE);*/
                select_date1.setVisibility(View.VISIBLE);
                select_date3.setVisibility(View.VISIBLE);
                select_date2.setVisibility(View.VISIBLE);
                name_date_of_objective.setVisibility(View.VISIBLE);

               /* vw_employee.setVisibility(View.VISIBLE);*/
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadevents() {
        try {
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("");

            CustomImageButton btn_chem_call_new = (CustomImageButton) getView().findViewById(R.id.btn_chem_call_new);
            btn_chem_call_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        GPSTracker gps = new GPSTracker(getContext(), fragment_chemist_call.this, false);
                        if (gps.canGetLocation()) {

                            Fragment frag = new fragment_chemist_call_new();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("fragment_chemist_call_new_from_list");

                            ft.commit();
                        } else {
                            gps.showSettingsAlert();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            edit_search = (EditText) getView().findViewById(R.id.edit_search);
            edit_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    Searching_data_listview(s.toString());
                    //refreshScreen(s.toString());
                }
            });
            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        restService = new RestService();
                        edit_search.setText("");
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_chemist_Calls_S.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        datafull = false;
                        limitstart = 0;

                        // select_employee2.setText("MY ");

                        if (!select_date2.getText().equals("dd/mm/yyyy")) {
                            //Load_Objective(select_date2.getText().toString());
                            Check_class_user_null_or_not();
                        } else {
                            Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_chemist_Calls_S clickedObj = (POJO_chemist_Calls_S) parent.getItemAtPosition(position);
                                                  // sendBackResult(clickedObj.getName(),clickedObj.getFull_name());

                                                  Bundle bundle = new Bundle();
                                                  bundle.putString("name", clickedObj.getName());
                                                  bundle.putString("user", clickedObj.getUser_id());
                                                  Fragment frag = new fragment_chemist_call_new();
                                                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                  ft.replace(R.id.content_frame, frag);
                                                  frag.setArguments(bundle);
                                                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                  ft.addToBackStack("fragment_chemist_call_new_edit_from_list");

                                                  ft.commit();
                                              } catch (Exception ex) {
                                                  Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }


            );
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Searching_data_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_chemist_Calls_S> result_query1 = mRealm.where(POJO_chemist_Calls_S.class).contains("chemist_name", search, INSENSITIVE).findAll().sort("chemist_name", Sort.ASCENDING);
            ;//.sort("modified", Sort.DESCENDING);
            List<POJO_chemist_Calls_S> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("CHEMIST CALL LIST (" + POJO.size() + ")");

            adapter = new adapter_chemist_calls(getContext(), R.layout.adapter_common_list_three_line, POJO);

            lv.setAdapter(adapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                //Toast.makeText(getContext(), "NO DOCTOR CALL FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddate() {
        try {
            TextView select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            select_date2.setText(sdf.format(date));
            /*select_date2.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //  showDialog(999);
                    Toast.makeText(getContext(), "ca",
                            Toast.LENGTH_SHORT)
                            .show();

                    return false;
                }
            });*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadchemist(final String dd) {
        try {


            // TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("chemist_name");
            jsonArray.put("user_id");
            jsonArray.put("hq_name");
            jsonArray.put("jwf_with");
            jsonArray.put("jwf_with2");
            jsonArray.put("creation");
            jsonArray.put("tamt_1");
            jsonArray.put("tamt_2");
            jsonArray.put("tamt_3");
            jsonArray.put("tamt_4");
            jsonArray.put("tamt_5");
            jsonArray.put("tamt_6");
            jsonArray.put("tamt_7");
            jsonArray.put("tamt_8");
            jsonArray.put("tamt_9");
            jsonArray.put("tamt_10");
            jsonArray.put("tamt_11");
            jsonArray.put("tamt_12");
            jsonArray.put("tamt_13");
            jsonArray.put("tamt_14");
            jsonArray.put("tamt_15");
            jsonArray.put("tamt_16");
            jsonArray.put("tamt_17");
            jsonArray.put("tamt_18");


            jsonArray.put("tqty_1");
            jsonArray.put("tqty_2");
            jsonArray.put("tqty_3");
            jsonArray.put("tqty_4");
            jsonArray.put("tqty_5");
            jsonArray.put("tqty_6");
            jsonArray.put("tqty_7");
            jsonArray.put("tqty_8");
            jsonArray.put("tqty_9");
            jsonArray.put("tqty_10");
            jsonArray.put("tqty_11");
            jsonArray.put("tqty_12");
            jsonArray.put("tqty_13");
            jsonArray.put("tqty_14");
            jsonArray.put("tqty_15");
            jsonArray.put("tqty_16");
            jsonArray.put("tqty_17");
            jsonArray.put("tqty_18");


            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            if (!select_date2.getText().equals("dd/mm/yyyy")) {
                Filter1.put("Chemist Call");
                Filter1.put("date");
                Filter1.put("=");
                Filter1.put(dd);

                //if (select_employee2.getText().equals("MY DR. CALL") ) {
                Filter2.put("Chemist Call");
                Filter2.put("call_by_user_id");
                Filter2.put("=");
                Filter2.put(name_objective_of_employee.getText().toString());
                Filters.put(Filter1);
                Filters.put(Filter2);

            }

            pDialog.show();
            restService.getService().getChemist_Call(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_chemist_Calls_S>>() {
                        }.getType();
                        List<POJO_chemist_Calls_S> POJO = gson.fromJson(j2, type);


                        if (POJO.size() == 0) {
                            datafull = true;

                            Bind_data_listview();


                        } else {
                            limitstart = limitstart + pagesize;

                        }


                        mRealm = Realm.getDefaultInstance();

                        mRealm.beginTransaction();

                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();


                        mRealm.close();


                        if (datafull == false) {
                            loadchemist(dd);

                        } else {
                            Bind_data_listview();
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
            });
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Bind_data_listview() {
        try {
            mRealm = Realm.getDefaultInstance();
            ListView listView = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_chemist_Calls_S> result_query1 = mRealm.where(POJO_chemist_Calls_S.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_chemist_Calls_S> mList = result_query1;

            adapter = new adapter_chemist_calls(getContext(), R.layout.adapter_common_list_three_line, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO CHEMIST CALL FOUND FOR SELECTED FILTER..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_employee() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "OBJ");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_chemist_call.this, 300);
                dialog.show(getFragmentManager(), "fdf");
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
                dialog.setTargetFragment(fragment_chemist_call.this, 300);
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

                    Fragment frag = new fragment_Hierarchy_Users_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("drcall_list");
                    ft.commit();

                    dialog.dismiss();
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

    private void alert_box_gps() {
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
