package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_HeadquarterWise_Stockiest_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_HeadquarterWise_Stockiest_List extends Fragment implements headquarter_list_hierarchy_FragmentDialog.EditHeadquarterListHirarchyDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //// add for load doctors
    private Realm mRealm;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    int sleep_wait = 0;

    public boolean bool_full_update = true;
    public POJO_Stockiest last_POJO;
    /////

    private OnFragmentInteractionListener mListener;
    ListView listView;
    public EditText edit_search;
    public String designation = "", employeename = "ALL", HQ_ID_List_String = "";

    public RealmResults<POJO_Territory_S> POJO_Territory_Obj;
    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_stockiest_list customAdapter;
    Bundle bundle;

    LinearLayout select_headquarter;
    LinearLayout select_headquarter1;
    TextView select_headquarter2;
    TextView name_user_of_headquarter;
    ImageButton select_headquarter3;
    View vw_headquarter;
    private long mLastClickTime = 0;

    public fragment_HeadquarterWise_Stockiest_List() {
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
    public static fragment_HeadquarterWise_Stockiest_List newInstance(String param1, String param2) {
        fragment_HeadquarterWise_Stockiest_List fragment = new fragment_HeadquarterWise_Stockiest_List();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        try {

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));

            ((DashBord_main) getActivity()).setActionBarTitle("STOCKIEST LIST");

            init_control();
            loadevents();

            pDialog = new ProgressDialog(getContext());

            select_headquarter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_headquarter();
                }
            });
            select_headquarter1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_headquarter();
                }
            });
            select_headquarter2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_headquarter();
                }
            });
            select_headquarter3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_headquarter();
                }
            });

            /*CHECK_ABM_RBM_ZBM_SM();
            bind_spinners();*/


//            if (designation.equals("TBM") || designation.equals("KBM")) {
//
//
//            }
//            else {
//                data_fetch();
//            }


            data_fetch();
            super.onStart();

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headquarterwise_stockiest__list, container, false);
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

    public void init_control() {
        try {
            select_headquarter = (LinearLayout) getView().findViewById(R.id.select_headquarter);
            select_headquarter1 = (LinearLayout) getView().findViewById(R.id.select_headquarter1);
            select_headquarter3 = (ImageButton) getView().findViewById(R.id.select_headquarter3);
            select_headquarter2 = (TextView) getView().findViewById(R.id.select_headquarter2);
            name_user_of_headquarter = (TextView) getView().findViewById(R.id.name_user_of_headquarter);

            vw_headquarter = getView().findViewById(R.id.vw_headquarter);

            select_headquarter.setVisibility(View.GONE);
            select_headquarter1.setVisibility(View.GONE);
            select_headquarter3.setVisibility(View.GONE);
            select_headquarter2.setVisibility(View.GONE);
            name_user_of_headquarter.setVisibility(View.GONE);
            vw_headquarter.setVisibility(View.GONE);


            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                select_headquarter.setVisibility(View.VISIBLE);
                select_headquarter1.setVisibility(View.VISIBLE);
                select_headquarter3.setVisibility(View.VISIBLE);
                select_headquarter2.setVisibility(View.VISIBLE);
                name_user_of_headquarter.setVisibility(View.VISIBLE);
                vw_headquarter.setVisibility(View.VISIBLE);
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFinishHeadquarterListHirarchyDialog(String id, String headquartername) {
        TextView select_headquarter2 = (TextView) getView().findViewById(R.id.select_headquarter2);
        select_headquarter2.setText(headquartername.toString());

        TextView name_user_of_headquarter = (TextView) getView().findViewById(R.id.name_user_of_headquarter);
        name_user_of_headquarter.setText(id.toString());

        bind_stockiest_headquarter_wise_listview(id.toString());
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

    public void loadevents() {
        try {

            edit_search = (EditText) getView().findViewById(R.id.edit_search);

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        select_headquarter2.setText("Select HeadQuarter");
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_Stockiest.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        //txt_loading.setVisibility(View.VISIBLE);
                        //txt_loading.setText("Refreshing Data..");
                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;
                        if (designation.equals("TBM") || designation.equals("KBM")) {
                            Load_stockiest_List();
                        } else {
                            //pDialog.show();
                            //Load_stockiest_List();
                            Check_class_hq_null_or_not();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            /*ImageButton btn_add_data = (ImageButton) getView().findViewById(R.id.btn_add_data);
            btn_add_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (designation.equals("Stockiest")) {
                        Fragment frag = new fragment_stockiest_users_master_insert();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("stockiest_new_insert");

                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "Only Stockiest can Add/Update Doctor", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

            edit_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    try {
                        Searching_data_listview(s.toString());
                        //refreshScreen(s.toString());
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            /*ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              POJO_Stockiest clickedObj = (POJO_Stockiest) parent.getItemAtPosition(position);

                                              final Bundle bundle = new Bundle();
                                              bundle.putString("name", clickedObj.getName());
                                              Fragment frag = new fragment_stockiest_users_master_insert();

                                              FragmentTransaction ft = getFragmentManager().beginTransaction();
                                              ft.replace(R.id.content_frame, frag);
                                              frag.setArguments(bundle);
                                              ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                              ft.addToBackStack("stockiest_update");
                                              ft.commit();
                                          }
                                      }
            );*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                // if (mRealm.where(POJO_Stockiest.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("modified", Sort.DESCENDING);
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

    private void Load_stockiest_List() {
        try {

            ////TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            //Thread.sleep(sleep_wait);

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("customer_name");
            jsonArray.put("customer_short_name");
            jsonArray.put("customer_type");
            jsonArray.put("customer_group");
            jsonArray.put("territory");
            jsonArray.put("food_license_no");
            jsonArray.put("creation");
            jsonArray.put("disabled");
            jsonArray.put("owner");
            jsonArray.put("phone1");
            jsonArray.put("phone2");
            jsonArray.put("email");
            jsonArray.put("address");
            jsonArray.put("city");
            jsonArray.put("taluka");
            jsonArray.put("district");
            jsonArray.put("state");
            jsonArray.put("pincode");
            jsonArray.put("vat_no");
            jsonArray.put("cst_no");
            jsonArray.put("dlno20b");
            jsonArray.put("dlno21b");
            jsonArray.put("docstatus");
            jsonArray.put("excise_no");
            jsonArray.put("modified");
            jsonArray.put("modified_by");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            if (Designation.equals("TBM") || Designation.equals("KBM")) {

                String headquarter = app_preferences.getString("headquarter", "default");
                Filter1.put("Customer");
                Filter1.put("territory");
                Filter1.put("=");
                Filter1.put(headquarter);

                Filter2.put("Customer");
                Filter2.put("customer_group");
                Filter2.put("=");
                Filter2.put("Stockist");
                Filters.put(Filter1);
                Filters.put(Filter2);

            } else if ((Designation.equals("ABM")) || (Designation.equals("RBM")) || (Designation.equals("CRM")) || (Designation.equals("ZBM")) || (Designation.equals("SM"))) {

                Filter1.put("Customer");
                Filter1.put("territory");
                Filter1.put("in");
                Filter1.put(HQ_ID_List_String);

                Filter2.put("Customer");
                Filter2.put("customer_group");
                Filter2.put("=");
                Filter2.put("Stockist");

                Filters.put(Filter1);
                Filters.put(Filter2);

            } else if ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager"))) {

                Filter1.put("Customer");
                Filter1.put("docstatus");
                Filter1.put("=");
                Filter1.put(0);

                Filter2.put("Customer");
                Filter2.put("customer_group");
                Filter2.put("=");
                Filter2.put("Stockist");

                Filters.put(Filter1);
                Filters.put(Filter2);
            }

            pDialog.show();

            restService.getService().getStockiest(sid, limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Stockiest>>() {
                        }.getType();
                        List<POJO_Stockiest> POJO = gson.fromJson(j2, type);


                        if (POJO.size() == 0) {
                            datafull = true;
                            Bind_data_listview();
                            pDialog.hide();

                        } else {
                            limitstart = limitstart + pagesize;

                        }

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();

                        if (bool_full_update == false) {
                            for (POJO_Stockiest pp : POJO) {

                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;
                                    Bind_data_listview();
                                    pDialog.hide();
                                }
                            }
                        }


                        if (datafull == false) {
                            Load_stockiest_List();

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

                            //Comment code Direct Goes To Login Fragment
                            //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            //SharedPreferences.Editor editor = app_preferences.edit();
                            //editor.putString("status", "0");
                            //editor.commit();

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);

                            Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();
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

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("modified", Sort.DESCENDING);
            if (result_query1.size() > 0) {
                Bind_data_listview();
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.GONE);
                //txt_loading.setText("Refreshing Data..");
            } else {
                //TextView txt_loading = (TextView) getView().findViewById(txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("Refreshing Data..");
                bool_full_update = false;
                full_update_calc();
                limitstart = 0;
                datafull = false;
                //pDialog.show();

                if (designation.equals("TBM") || designation.equals("KBM")) {
                    Load_stockiest_List();
                } else {
                    //pDialog.show();
                    //Load_stockiest_List();
                    Check_class_hq_null_or_not();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Check_class_hq_null_or_not() {
        try {
            mRealm = Realm.getDefaultInstance();
            POJO_Territory_Obj = mRealm.where(POJO_Territory_S.class).findAll();//.sort("modified", Sort.DESCENDING)
            if (POJO_Territory_Obj.size() > 0) {
                for (POJO_Territory_S contact : POJO_Territory_Obj) {
                    if (!contact.getHeadquarter_id().equals("ALL")) {
                        HQ_ID_List_String = HQ_ID_List_String + "," + contact.getHeadquarter_id();
                    }
                }
                HQ_ID_List_String = HQ_ID_List_String.substring(1);
                Load_stockiest_List();
            } else {
                HQ_ID_List_String = " ";

                /*Dialog Box Code For Class HQ Null*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(Html.fromHtml("<font color='#FF7F27'>REQUIRED</font>"));
                builder.setMessage("Not Load Fully HQ. Do You Want Load HQ ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        show_dialog_for_select_headquarter();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        Toast.makeText(getContext(), "You Not See Your Stockiest...Please Load HQ...", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Bind_data_listview() {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).equalTo("customer_group", "Stockist").findAll().sort("modified", Sort.DESCENDING);
            List<POJO_Stockiest> POJO = result_query1;
            ((DashBord_main) getActivity()).setActionBarTitle("STOCKIEST LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_stockiest_list(getContext(), R.layout.adapter_headquarterwise_stockiest_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO STOCKIEST FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Searching_data_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Stockiest> result_query1 = mRealm.where(POJO_Stockiest.class).contains("customer_name", search, Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            List<POJO_Stockiest> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("STOCKIEST LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_stockiest_list(getContext(), R.layout.adapter_headquarterwise_stockiest_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO STOCKIEST FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void bind_stockiest_headquarter_wise_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Stockiest> result_query1;
            if (search.equals("ALL")) {
                result_query1 = mRealm.where(POJO_Stockiest.class).findAll().sort("modified", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_Stockiest.class).contains("territory", search, Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            }
            List<POJO_Stockiest> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("STOCKIEST LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_stockiest_list(getContext(), R.layout.adapter_headquarterwise_stockiest_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO STOCKIEST FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_headquarter() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                //After Whitelist Method Create Then implements
                headquarter_list_hierarchy_FragmentDialog dialog = headquarter_list_hierarchy_FragmentDialog.newInstance("Hello world");
                //
                dialog.setTargetFragment(fragment_HeadquarterWise_Stockiest_List.this, 300);
                dialog.show(getFragmentManager(), "fdf");

                //final Bundle bundle = new Bundle();
                //bundle.putString("stcokiest", "Y");
                //dialog.setArguments(bundle);
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

}
