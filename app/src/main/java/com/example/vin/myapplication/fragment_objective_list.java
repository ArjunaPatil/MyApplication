package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
 * Use the {@link fragment_objective_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_objective_list extends Fragment implements
        user_list_hierarchy_FragmentDialog.EditUserListHirarchyDialogListener,
        DatePickerFragment.DateDialogListener,
        daily_plan_details_FragmentDialog.ParamDailyPlanListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ///********
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    ///********

    private Realm mRealm;
    RestService restService;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    int limitstart = 0;
    int pagesize = 10;
    public boolean bool_full_update = true;
    boolean datafull = false;
    ProgressDialog pDialog;

    public POJO_objective_S last_POJO;
    public adapter_objective_list adapter;

    private OnFragmentInteractionListener mListener;
    EditText edit_search;
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

    View vw_employee;

    private long mLastClickTime = 0;
    Bundle bundle;

    public fragment_objective_list() {
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
    public static fragment_objective_list newInstance(String param1, String param2) {
        fragment_objective_list fragment = new fragment_objective_list();
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

        View view = inflater.inflate(R.layout.fragment_objective_list, container, false);

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


    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname) {
        try {
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            select_employee2.setText(fullname.toString());

            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);
            name_objective_of_employee.setText(id.toString());

            data_fetch();
            //bind_objective_employee_wise_listview(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishParamDailyPlan(String flag, String name, String select_user, String select_date, String dcr, String camp, String meeting, String lve) {
        try {
            if (flag.equals("Y")) {
                /*if (bundle != null) {*/
                //(String flag,String name,String select_user,String select_date,String dcr,String camp,String meeting,String lve)
                final Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("select_user", select_user);
                bundle.putString("select_date", select_date);
                bundle.putString("dcr", dcr);
                bundle.putString("camp", camp);
                bundle.putString("meeting", meeting);
                bundle.putString("lve", lve);

                call_for_edit_fragment(bundle);

                /*}*/
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void call_for_edit_fragment(Bundle bundle) {

        try {
            Fragment frag = new fragment_daily_plan_insert_temp1();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            frag.setArguments(bundle);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack("obj_update");
            ft.commit();
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
            //Toast.makeText(this, "Selected Date :"+ formatDate(date), Toast.LENGTH_SHORT).show();
            select_date2.setText(hireDate);
            //Load_Objective(select_date2.getText().toString());

            restService = new RestService();
            edit_search.setText("");

            mRealm = Realm.getDefaultInstance();
            RealmResults<POJO_objective_S> result_query1;

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                result_query1 = mRealm.where(POJO_objective_S.class).contains("user", name_objective_of_employee.getText().toString().trim(), INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
            }

                       /* mRealm.beginTransaction();
                        result_query1.deleteAllFromRealm();
                        mRealm.commitTransaction();
                        mRealm.close();*/

            mRealm = Realm.getDefaultInstance();

                        /*RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("user",name_doctor_of_employee.toString().trim()).findAll().sort("modified", Sort.DESCENDING);*/
            RealmResults<POJO_objective_S> result_query2 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
            mRealm.beginTransaction();
            result_query2.deleteAllFromRealm();
            mRealm.commitTransaction();
            mRealm.close();

            bool_full_update = false;
            datafull = false;
            limitstart = 0;

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                    balnk_listview();
                    Load_Objective(select_date2.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                }
            } else {
                if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                    Check_class_user_null_or_not();
                } else {
                    Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                }

            }
            //Toast.makeText(getContext(),"Selected Date :"+ hireDate, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
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

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));

            ((DashBord_main) getActivity()).setActionBarTitle("OBJECTIVE LIST");
            init_control();
            if (select_date2.getText().equals("dd/mm/yyyy")) {
                loaddate();
            }

            loadevents();

            String plandate = app_preferences.getString("plan_date", "");
            if (!plandate.isEmpty()) {
                select_date2.setText(plandate);
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString("plan_date", "");
                editor.commit();
            }
            pDialog = new ProgressDialog(getContext());

            /////employee
            /*select_employee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_employee();
                }
            });*/
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

            /////date
            /*select_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });*/
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

            ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_objective_S clickedObj = (POJO_objective_S) parent.getItemAtPosition(position);
                                                  if (clickedObj.getDoctor_flag() == 1 || clickedObj.getCamp_flag() == 1 || clickedObj.getMeeting_flag() == 1 || clickedObj.getLeave_flag() == 1) {
                                                      final Bundle bundle = new Bundle();
                                                      bundle.putString("name", clickedObj.getName());
                                                      bundle.putString("select_user", clickedObj.getUser());
                                                      bundle.putString("select_date", select_date2.getText().toString());
                                                      bundle.putString("dcr", clickedObj.getDoctor_flag().toString());
                                                      bundle.putString("camp", clickedObj.getCamp_flag().toString());
                                                      bundle.putString("meeting", clickedObj.getMeeting_flag().toString());
                                                      bundle.putString("lve", clickedObj.getLeave_flag().toString());

                                                      show_dialog_for_plan_details(bundle);

                                                  /*Fragment frag = new fragment_daily_plan_insert_temp1();
                                                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                  ft.replace(R.id.content_frame, frag);
                                                  frag.setArguments(bundle);
                                                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                  ft.addToBackStack("obj_update");
                                                  ft.commit();*/
                                                  } else {
                                                      Toast.makeText(getContext(), "PLAN NOT CREATED...", Toast.LENGTH_SHORT).show();
                                                  }

                                              } catch (Exception ex) {
                                                  Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }
            );

            data_fetch();
            super.onStart();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_objective_S.class);
            mRealm.commitTransaction();
            mRealm.close();


            limitstart = 0;
            Check_class_user_null_or_not();


           /* mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_objective_S> result_query1;
            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                result_query1 = mRealm.where(POJO_objective_S.class).contains("user", name_objective_of_employee.getText().toString().trim(), INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
            }
            if (result_query1.size() > 0) {
                Bind_data_listview();
            } else {
                bool_full_update = false;

                limitstart = 0;
                datafull = false;
                if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                    if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                        balnk_listview();
                        Load_Objective(select_date2.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                        Check_class_user_null_or_not();

                    } else {
                        Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                    }
                }
            }*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void balnk_listview() {

        final RealmResults<POJO_objective_S> result_query1;

        result_query1 = mRealm.where(POJO_objective_S.class).equalTo("user", "tivtiv").findAll().sort("creation", Sort.DESCENDING);


        List<POJO_objective_S> mList = result_query1;


        adapter = new adapter_objective_list(getContext(), R.layout.adapter_objective_list, mList);
        ListView lv = (ListView) getView().findViewById(R.id.listView);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                // if (mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                RealmResults<POJO_objective_S> result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    last_POJO = result_query1.first();
                } else {
                    bool_full_update = true;
                }
            }

        } catch (Exception ex) {
            String exx = ex.getMessage();
            exx = ex.getMessage();
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

            select_employee.setVisibility(View.GONE);
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

            /*select_date.setVisibility(View.GONE);*/
            select_date1.setVisibility(View.GONE);
            select_date3.setVisibility(View.GONE);
            select_date2.setVisibility(View.GONE);
            name_date_of_objective.setVisibility(View.GONE);

            //view (line)
            vw_employee = getView().findViewById(R.id.vw_employee);
            vw_employee.setVisibility(View.GONE);


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

                vw_employee.setVisibility(View.VISIBLE);
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
                        /*restService = new RestService();
                        edit_search.setText("");

                        select_employee2.setText("SELECT EMPLOYEE");
                        name_objective_of_employee.setText("");

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_objective_S.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;

                        select_employee2.setText("SELECT EMPLOYEE");

                        if (!select_date2.getText().equals("dd/mm/yyyy")) {
                            Check_class_user_null_or_not();
                        } else {
                            Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                        }*/


                        restService = new RestService();
                        edit_search.setText("");

                        mRealm = Realm.getDefaultInstance();
                        RealmResults<POJO_objective_S> result_query1;

                        if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                            result_query1 = mRealm.where(POJO_objective_S.class).contains("user", name_objective_of_employee.getText().toString().trim(), INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
                        } else {
                            result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
                        }

                       /* mRealm.beginTransaction();
                        result_query1.deleteAllFromRealm();
                        mRealm.commitTransaction();
                        mRealm.close();*/

                        mRealm = Realm.getDefaultInstance();

                        /*RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("user",name_doctor_of_employee.toString().trim()).findAll().sort("modified", Sort.DESCENDING);*/
                        RealmResults<POJO_objective_S> result_query2 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
                        mRealm.beginTransaction();
                        result_query2.deleteAllFromRealm();
                        mRealm.commitTransaction();
                        mRealm.close();

                        bool_full_update = false;
                        datafull = false;
                        limitstart = 0;

                        if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                            if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                                balnk_listview();
                                Load_Objective(select_date2.getText().toString());
                            } else {
                                Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                                Check_class_user_null_or_not();
                            } else {
                                Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                            }

                        }

                        /*if (!select_date2.getText().toString().trim().contains("dd/mm/yyyy")) {
                            Check_class_user_null_or_not();
                        } else {
                            Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                        }*/


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void loaddate() {
        try {
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
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
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Check_class_user_null_or_not() {
        try {
            User_ID_List_String = "";
            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).contains("flag", "B", INSENSITIVE).findAll();
            if (POJO_User_Obj.size() > 0) {
                for (POJO_User_Hierarchy contact : POJO_User_Obj) {
                    if (!contact.getName().equals("ALL")) {
                        User_ID_List_String += contact.getName() + ",";
                    }
                }
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String name = app_preferences.getString("name", "default");
                User_ID_List_String += name;
                //User_ID_List_String = User_ID_List_String.substring(1);
                balnk_listview();
                Load_Objective(select_date2.getText().toString());
            } else {
                User_ID_List_String = " ";
                /*Dialog Box Code For Class HQ Null*/
                alert_box();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Load_Objective(final String dd) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");
            //String EmployeeHierarchyId = app_preferences.getString("EmployeeUnderHierarchy", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");
            jsonArray.put("user");
            jsonArray.put("user_name");
            jsonArray.put("creation");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_flag");
            jsonArray.put("leave_flag");

            jsonArray.put("call_agenda");
            jsonArray.put("meeting_agenda");
            jsonArray.put("camp_agenda");
            jsonArray.put("reason");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();


            Filter1.put("Objective");
            Filter1.put("select_date");
            Filter1.put("=");
            Filter1.put(dd);

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                Filter2.put("Objective");
                Filter2.put("user");
                Filter2.put("=");
                Filter2.put(name_objective_of_employee.getText().toString());
            } else {
                Filter2.put("Objective");
                Filter2.put("user");
                Filter2.put("in");
                Filter2.put(User_ID_List_String);
            }

            /*Tour Plan Exclude Filter
            JSONArray Filter3 = new JSONArray();
            Filter3.put("Objective");
            Filter3.put("tp_flag");
            Filter3.put("=");
            Filter3.put(0);
            Filters.put(Filter3);*/

            Filters.put(Filter1);
            Filters.put(Filter2);


            /*if (!select_date2.getText().equals("dd/mm/yyyy")) {
                Filter1.put("Objective");
                Filter1.put("select_date");
                Filter1.put("=");
                Filter1.put(dd);

                if (!select_employee2.getText().equals("SELECT EMPLOYEE") && !select_employee2.getText().equals("ALL")) {
                    Filter2.put("Objective");
                    Filter2.put("user");
                    Filter2.put("=");
                    Filter2.put(name_objective_of_employee.getText().toString());
                    Filters.put(Filter1);
                    Filters.put(Filter2);
                } else if (!select_employee2.getText().equals("SELECT EMPLOYEE") && select_employee2.getText().equals("ALL")) {
                    Filter2.put("Objective");
                    Filter2.put("user");
                    Filter2.put("in");
                    Filter2.put(User_ID_List_String);
                    Filters.put(Filter1);
                    Filters.put(Filter2);
                } else {
                    Filter2.put("Objective");
                    Filter2.put("user");
                    Filter2.put("in");
                    Filter2.put(User_ID_List_String);
                    Filters.put(Filter1);
                    Filters.put(Filter2);
                }
            }*/


            pDialog.show();
            restService.getService().getObjectiveList(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_objective_S>>() {
                        }.getType();
                        List<POJO_objective_S> POJO = gson.fromJson(j2, type);

                        if (POJO.size() == 0) {
                            datafull = true;
                            //Bind_data_listview();
                            //pDialog.hide();
                        } else {
                            limitstart = limitstart + pagesize;
                        }

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();

                       /* if (bool_full_update == false) {
                            for (POJO_objective_S pp : POJO) {

                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName()))) {
                                    //&& (pp.getModified().equals(last_POJO.getModified()))
                                    datafull = true;
                                    Bind_data_listview();
                                    pDialog.hide();
                                }
                            }
                        }*/

                        if (datafull == false) {
                            Load_Objective(dd);

                        } else {
                            Bind_data_listview();
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


                            //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            //SharedPreferences.Editor editor = app_preferences.edit();
                            //editor.putString("status", "0");
                            //editor.commit();

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);


                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
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
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Bind_data_listview() {
        try {

            final RealmResults<POJO_objective_S> result_query1;

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                result_query1 = mRealm.where(POJO_objective_S.class).equalTo("user", name_objective_of_employee.getText().toString().trim()).findAll().sort("creation", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("creation", Sort.DESCENDING);
            }

            List<POJO_objective_S> mList = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("OBJECTIVE LIST (" + mList.size() + ")");

            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO OBJECTIVE FOUND FOR SELECTED FILTER..");
            } else {
                adapter = new adapter_objective_list(getContext(), R.layout.adapter_objective_list, mList);
                ListView lv = (ListView) getView().findViewById(R.id.listView);
                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                ///

                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Searching_data_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_objective_S> result_query1 = mRealm.where(POJO_objective_S.class).contains("user", search, INSENSITIVE).findAll();//.sort("modified", Sort.DESCENDING);
            List<POJO_objective_S> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("OBJECTIVE LIST (" + POJO.size() + ")");

            adapter = new adapter_objective_list(getContext(), R.layout.adapter_objective_list, POJO);

            lv.setAdapter(adapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO OBJECTIVE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_employee() {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "OBJ");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_objective_list.this, 300);
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
                dialog.setTargetFragment(fragment_objective_list.this, 300);
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

    private void show_dialog_for_plan_details(Bundle bundle) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                daily_plan_details_FragmentDialog dialog = daily_plan_details_FragmentDialog.newInstance("Hello world");
                int width = getContext().getResources().getDisplayMetrics().widthPixels;
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_objective_list.this, width - 30);
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
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");

            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Fragment frag = new fragment_Hierarchy_Users_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("objective_list");
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

    public void bind_objective_employee_wise_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_objective_S> result_query1;
            if (search.equals("ALL")) {
                result_query1 = mRealm.where(POJO_objective_S.class).findAll();
            } else {
                result_query1 = mRealm.where(POJO_objective_S.class).contains("user", search, INSENSITIVE).findAll();
            }
            //final RealmResults<POJO_objective_S> result_query1 = mRealm.where(POJO_objective_S.class).contains("user", search, INSENSITIVE).findAll();//.sort("modified", Sort.DESCENDING);
            List<POJO_objective_S> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("OBJECTIVE LIST (" + POJO.size() + ")");
            adapter = new adapter_objective_list(getContext(), R.layout.adapter_objective_list, POJO);

            lv.setAdapter(adapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO OBJECTIVE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE); NetworkInfo netInfo = conMgr.getActiveNetworkInfo(); if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){ Toast.makeText(getContext(), "No Internet connection!",
                Toast.LENGTH_LONG).show();
            return false; }
        return true;
    }*/

}
