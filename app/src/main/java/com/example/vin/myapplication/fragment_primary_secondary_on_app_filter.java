package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_primary_secondary_on_app_filter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_primary_secondary_on_app_filter extends Fragment implements
        DatePickerFragment.DateDialogListener, user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        stockist_list_to_top_hierarchy_FragmentDialog.EditStockistListHirarchyDialogListener,
        branch_list_for_primary_secondary_on_app_FragmentDialog.EditBranchListHirarchyDialogListener {
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

    Button btn_show_primary;

    LinearLayout txt_from_date;
    LinearLayout txt_from_date1;
    TextView txt_from_date2;
    ImageButton txt_from_date3;

    LinearLayout txt_to_date;
    LinearLayout txt_to_date1;
    TextView txt_to_date2;
    ImageButton txt_to_date3;

    TextView txt_lbl_EMPLOYEE;

    LinearLayout select_employee;
    LinearLayout select_employee_1;
    TextView select_employee_2;
    TextView name_select_employee;
    ImageButton select_employee_3;
    View view_employee;

    LinearLayout ll_stockist;

    LinearLayout select_stockist;
    LinearLayout select_stockist_1;
    TextView select_stockist_2;
    TextView name_select_stockist;
    ImageButton select_stockist_3;
    View view_stockist;

    LinearLayout ll_branch;
    LinearLayout select_branch;
    LinearLayout select_branch_1;
    TextView select_branch_2;
    TextView name_select_branch;
    ImageButton select_branch_3;
    View view_branch;

    ToggleButton btn_togg_user_stockist;

    LinearLayout ll_toggle_btn;
    View view_toggle_btn;
    /////////////////////////////////////


    CheckBox actirab_tab;
    CheckBox actirab_d_cap;
    CheckBox actirab_dv_cap;
    CheckBox actirab_l_cap;
    CheckBox empower_od_tab;
    CheckBox stand_sp_tab;
    CheckBox star_t_tab;
    CheckBox glucolyst_g1_tab;
    CheckBox lycorest_tab;
    CheckBox lycort_1ml_inj;
    CheckBox regain_xt_tab;
    CheckBox lycorest_60ml_susp;
    CheckBox lycolic_10ml_drops;
    CheckBox stand_mf_60ml_susp;
    CheckBox ten_n_30ml_syrup;
    CheckBox wego_gel_20_mg;
    CheckBox wego_gel_30_mg;
    CheckBox trygesic_tab;
    CheckBox itezone_200_cap;
    CheckBox nextvit_tab;
    /*new branch product 28/04/2018*/
    CheckBox trygesic_ptab;
    CheckBox cipgrow_syrup;
    CheckBox clavyten_625;
    CheckBox levocast_m;
    CheckBox altipan_dsr;
    CheckBox sangria_tonic;
    CheckBox onederm_cream;
    CheckBox actirest_ls;
    CheckBox actirest_dx;
    CheckBox korby_soap;
    /*End New*/
    BranchWiseProductList return_branch_wise_product;
    Bundle bundle;

    private ProgressDialog pDialog;
    private long mLastClickTime = 0;
    String date_flag = "", toggle_flag = "", branch = "";

    public fragment_primary_secondary_on_app_filter() {
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
    public static fragment_primary_secondary_on_app_filter newInstance(String param1, String param2) {
        fragment_primary_secondary_on_app_filter fragment = new fragment_primary_secondary_on_app_filter();
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
        return inflater.inflate(R.layout.fragment_primary_secondary_on_app_filter, container, false);
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

            ((DashBord_main) getActivity()).setActionBarTitle("FILTER'S FOR PRIMARY");

            init_controls();
            CHECK_ABM_RBM_ZBM_SM();
            todate_assign_to_from_to_date_text_view();


            ///////////////////////////////////////////
            txt_from_date.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     date_flag = "f";
                                                     show_dialog_for_select_date();
                                                 }
                                             }
            );
            txt_from_date1.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      date_flag = "f";
                                                      show_dialog_for_select_date();
                                                  }
                                              }
            );
            txt_from_date2.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      date_flag = "f";
                                                      show_dialog_for_select_date();
                                                  }
                                              }
            );
            txt_from_date3.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      date_flag = "f";
                                                      show_dialog_for_select_date();
                                                  }
                                              }
            );


            ///////////////////////////////////////////
            txt_to_date.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   date_flag = "t";
                                                   show_dialog_for_select_date();
                                               }
                                           }
            );
            txt_to_date1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    date_flag = "t";
                                                    show_dialog_for_select_date();
                                                }
                                            }
            );
            txt_to_date2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    date_flag = "t";
                                                    show_dialog_for_select_date();
                                                }
                                            }
            );
            txt_to_date3.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    date_flag = "t";
                                                    show_dialog_for_select_date();
                                                }
                                            }
            );


            ///////////////////////////////////////////
            select_employee.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       select_employee_2.setText(" Select");
                                                       show_dialog_for_employee_list_tbm();
                                                   }
                                               }
            );
            select_employee_1.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_employee_2.setText(" Select");
                                                         show_dialog_for_employee_list_tbm();
                                                     }
                                                 }
            );
            select_employee_2.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_employee_2.setText(" Select");
                                                         show_dialog_for_employee_list_tbm();
                                                     }
                                                 }
            );
            name_select_employee.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            select_employee_2.setText(" Select");
                                                            show_dialog_for_employee_list_tbm();
                                                        }
                                                    }
            );
            select_employee_3.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_employee_2.setText(" Select");
                                                         show_dialog_for_employee_list_tbm();
                                                     }
                                                 }
            );


            //////////////////////////////////////////////////////////
            select_stockist.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       select_stockist_2.setText(" Select");
                                                       show_dialog_for_stockist_list_tbm();
                                                   }
                                               }
            );
            select_stockist_1.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_stockist_2.setText(" Select");
                                                         show_dialog_for_stockist_list_tbm();
                                                     }
                                                 }
            );
            select_stockist_2.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_stockist_2.setText(" Select");
                                                         show_dialog_for_stockist_list_tbm();
                                                     }
                                                 }
            );
            name_select_stockist.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            select_stockist_2.setText(" Select");
                                                            show_dialog_for_stockist_list_tbm();
                                                        }
                                                    }
            );
            select_stockist_3.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         select_stockist_2.setText(" Select");
                                                         show_dialog_for_stockist_list_tbm();
                                                     }
                                                 }
            );

            ////////////////////
            select_branch.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     select_branch_2.setText(" Select");
                                                     show_dialog_for_branch_list();
                                                 }
                                             }
            );
            select_branch_1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       select_branch_2.setText(" Select");
                                                       show_dialog_for_branch_list();
                                                   }
                                               }
            );
            select_branch_2.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       select_branch_2.setText(" Select");
                                                       show_dialog_for_branch_list();
                                                   }
                                               }
            );
            name_select_branch.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          select_branch_2.setText(" Select");
                                                          show_dialog_for_branch_list();
                                                      }
                                                  }
            );
            select_branch_3.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       select_branch_2.setText(" Select");
                                                       show_dialog_for_branch_list();
                                                   }
                                               }
            );

            ///////////////////////////////////////////////////////
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final String designation = app_preferences.getString("designation", "default");
            final String user = app_preferences.getString("name", "default");
            branch = (app_preferences.getString("branch", ""));
            products_with_comma = (app_preferences.getString("branch_product", ""));
            /*if (designation.equals("TBM") == true) {
                if (!products_with_comma.equals("") || !products_with_comma.equals(null)) {
                    visibility_of_product();
                } else {
                    if (branch.equals("") || branch.equals(null)) {
                        Toast.makeText(getContext(), "Your Branch Is Empty; Inform To Office For Fill Up... ", Toast.LENGTH_SHORT).show();
                    } else {
                        get_product_list_of_branch(branch);
                    }
                }
            }*/

            if (!designation.equals("TBM")) {
                if (btn_togg_user_stockist.isChecked()) {
                    check_btn_toggle_condtion(true);
                } else {
                    check_btn_toggle_condtion(false);
                }
            }

            btn_togg_user_stockist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    check_btn_toggle_condtion(isChecked);
                    /*if (isChecked) {

                        //STOCKIST
                        toggle_flag = "S";
                        select_employee_2.setText(" Select");

                        txt_lbl_EMPLOYEE.setVisibility(View.GONE);

                        select_employee.setVisibility(View.GONE);
                        select_employee_1.setVisibility(View.GONE);
                        select_employee_2.setVisibility(View.GONE);
                        name_select_employee.setVisibility(View.GONE);
                        select_employee_3.setVisibility(View.GONE);
                        view_employee.setVisibility(View.GONE);

                        ll_stockist.setVisibility(View.VISIBLE);

                        select_stockist.setVisibility(View.VISIBLE);
                        select_stockist_1.setVisibility(View.VISIBLE);
                        select_stockist_2.setVisibility(View.VISIBLE);
                        name_select_stockist.setVisibility(View.VISIBLE);
                        select_stockist_3.setVisibility(View.VISIBLE);
                        view_stockist.setVisibility(View.VISIBLE);

                        if (branch.equals("ALL Branch") == true) {
                            ll_branch.setVisibility(View.VISIBLE);
                            select_branch.setVisibility(View.VISIBLE);
                            select_branch_1.setVisibility(View.VISIBLE);
                            select_branch_2.setVisibility(View.VISIBLE);
                            name_select_branch.setVisibility(View.VISIBLE);
                            select_branch_3.setVisibility(View.VISIBLE);
                            view_branch.setVisibility(View.VISIBLE);
                        } else {
                            ll_branch.setVisibility(View.GONE);
                            select_branch.setVisibility(View.GONE);
                            select_branch_1.setVisibility(View.GONE);
                            select_branch_2.setVisibility(View.GONE);
                            name_select_branch.setVisibility(View.GONE);
                            select_branch_3.setVisibility(View.GONE);
                            view_branch.setVisibility(View.GONE);
                        }

                    } else {

                        //EMPLOYEE
                        toggle_flag = "E";
                        select_stockist_2.setText(" Select");

                        txt_lbl_EMPLOYEE.setVisibility(View.VISIBLE);
                        select_employee.setVisibility(View.VISIBLE);
                        select_employee_1.setVisibility(View.VISIBLE);
                        select_employee_2.setVisibility(View.VISIBLE);
                        name_select_employee.setVisibility(View.VISIBLE);
                        select_employee_3.setVisibility(View.VISIBLE);
                        view_employee.setVisibility(View.VISIBLE);

                        ll_stockist.setVisibility(View.GONE);
                        select_stockist.setVisibility(View.GONE);
                        select_stockist_1.setVisibility(View.GONE);
                        select_stockist_2.setVisibility(View.GONE);
                        name_select_stockist.setVisibility(View.GONE);
                        select_stockist_3.setVisibility(View.GONE);
                        view_stockist.setVisibility(View.GONE);

                        ll_branch.setVisibility(View.GONE);
                        select_branch.setVisibility(View.GONE);
                        select_branch_1.setVisibility(View.GONE);
                        select_branch_2.setVisibility(View.GONE);
                        name_select_branch.setVisibility(View.GONE);
                        select_branch_3.setVisibility(View.GONE);
                        view_branch.setVisibility(View.GONE);

                    }*/
                }
            });

            btn_show_primary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (CheckDates(txt_from_date2.getText().toString(), txt_to_date2.getText().toString()) == true) {
                            //Date date1= dd/MM/yyyy
                            if (calculateDays(new SimpleDateFormat("yyyy-MM-dd").parse(txt_from_date2.getText().toString()), new SimpleDateFormat("yyyy-MM-dd").parse(txt_to_date2.getText().toString())) <= 31) {
                                if (designation.equals("TBM") == true) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString("user", user);
                                    bundle.putString("stockist", "");
                                    bundle.putString("fromdate", txt_from_date2.getText().toString());
                                    bundle.putString("todate", txt_to_date2.getText().toString());
                                    bundle.putString("products", "");
                                    bundle.putString("branch", "NO");
                                    bundle.putString("flag", "E");
                                    call_fragment(bundle);

                                } else {
                                    if (toggle_flag == "E") {
                                        if (select_employee_2.getText().toString().equals(" Select") == true) {
                                            Toast.makeText(getContext(), "Select Employee", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("user", name_select_employee.getText().toString());
                                            bundle.putString("stockist", "");//select_stockist_2.getText().toString()
                                            bundle.putString("fromdate", txt_from_date2.getText().toString());
                                            bundle.putString("todate", txt_to_date2.getText().toString());
                                            bundle.putString("products", "");
                                            bundle.putString("branch", "NO");
                                            bundle.putString("flag", "E");
                                            call_fragment(bundle);
                                        }
                                    } else if (toggle_flag == "S") {
                                        if (select_stockist_2.getText().toString().equals(" Select") == true) {
                                            Toast.makeText(getContext(), "Select Stockist", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (branch.equals("ALL Branch") == true) {
                                                if (select_branch_2.getText().toString().equals(" Select") == true) {
                                                    Toast.makeText(getContext(), "Select Branch", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("user", user);
                                                    bundle.putString("stockist", name_select_stockist.getText().toString());
                                                    bundle.putString("fromdate", txt_from_date2.getText().toString());
                                                    bundle.putString("todate", txt_to_date2.getText().toString());
                                                    bundle.putString("products", "");
                                                    bundle.putString("branch", name_select_branch.getText().toString());
                                                    bundle.putString("flag", "S");
                                                    call_fragment(bundle);
                                                }
                                            } else {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("user", user);
                                                bundle.putString("stockist", name_select_stockist.getText().toString());
                                                bundle.putString("fromdate", txt_from_date2.getText().toString());
                                                bundle.putString("todate", txt_to_date2.getText().toString());
                                                bundle.putString("products", "");
                                                bundle.putString("branch", "NO");
                                                bundle.putString("flag", "S");
                                                call_fragment(bundle);
                                            }
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(getContext(), "DATE FROM-TO DIFFERENCE MUST BE LESS THAN 31 DAYS", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Choose From Date Less Than To Date", Toast.LENGTH_SHORT).show();
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

    public void check_btn_toggle_condtion(boolean isChecked) {
        if (isChecked) {

            //STOCKIST
            toggle_flag = "S";
            select_employee_2.setText(" Select");

            txt_lbl_EMPLOYEE.setVisibility(View.GONE);

            select_employee.setVisibility(View.GONE);
            select_employee_1.setVisibility(View.GONE);
            select_employee_2.setVisibility(View.GONE);
            name_select_employee.setVisibility(View.GONE);
            select_employee_3.setVisibility(View.GONE);
            view_employee.setVisibility(View.GONE);

            ll_stockist.setVisibility(View.VISIBLE);

            select_stockist.setVisibility(View.VISIBLE);
            select_stockist_1.setVisibility(View.VISIBLE);
            select_stockist_2.setVisibility(View.VISIBLE);
            name_select_stockist.setVisibility(View.VISIBLE);
            select_stockist_3.setVisibility(View.VISIBLE);
            view_stockist.setVisibility(View.VISIBLE);

            if (branch.equals("ALL Branch") == true) {
                ll_branch.setVisibility(View.VISIBLE);
                select_branch.setVisibility(View.VISIBLE);
                select_branch_1.setVisibility(View.VISIBLE);
                select_branch_2.setVisibility(View.VISIBLE);
                name_select_branch.setVisibility(View.VISIBLE);
                select_branch_3.setVisibility(View.VISIBLE);
                view_branch.setVisibility(View.VISIBLE);
            } else {
                ll_branch.setVisibility(View.GONE);
                select_branch.setVisibility(View.GONE);
                select_branch_1.setVisibility(View.GONE);
                select_branch_2.setVisibility(View.GONE);
                name_select_branch.setVisibility(View.GONE);
                select_branch_3.setVisibility(View.GONE);
                view_branch.setVisibility(View.GONE);
            }

        } else {

            //EMPLOYEE
            toggle_flag = "E";
            select_stockist_2.setText(" Select");

            txt_lbl_EMPLOYEE.setVisibility(View.VISIBLE);
            select_employee.setVisibility(View.VISIBLE);
            select_employee_1.setVisibility(View.VISIBLE);
            select_employee_2.setVisibility(View.VISIBLE);
            name_select_employee.setVisibility(View.VISIBLE);
            select_employee_3.setVisibility(View.VISIBLE);
            view_employee.setVisibility(View.VISIBLE);

            ll_stockist.setVisibility(View.GONE);
            select_stockist.setVisibility(View.GONE);
            select_stockist_1.setVisibility(View.GONE);
            select_stockist_2.setVisibility(View.GONE);
            name_select_stockist.setVisibility(View.GONE);
            select_stockist_3.setVisibility(View.GONE);
            view_stockist.setVisibility(View.GONE);

            ll_branch.setVisibility(View.GONE);
            select_branch.setVisibility(View.GONE);
            select_branch_1.setVisibility(View.GONE);
            select_branch_2.setVisibility(View.GONE);
            name_select_branch.setVisibility(View.GONE);
            select_branch_3.setVisibility(View.GONE);
            view_branch.setVisibility(View.GONE);

        }
    }

    @Override
    public void onResume() {
        try {
            if (toggle_flag == "E") {

                txt_lbl_EMPLOYEE.setVisibility(View.VISIBLE);
                select_employee.setVisibility(View.VISIBLE);
                select_employee_1.setVisibility(View.VISIBLE);
                select_employee_2.setVisibility(View.VISIBLE);
                name_select_employee.setVisibility(View.VISIBLE);
                select_employee_3.setVisibility(View.VISIBLE);
                view_employee.setVisibility(View.VISIBLE);

                ll_stockist.setVisibility(View.GONE);
                select_stockist.setVisibility(View.GONE);
                select_stockist_1.setVisibility(View.GONE);
                select_stockist_2.setVisibility(View.GONE);
                name_select_stockist.setVisibility(View.GONE);
                select_stockist_3.setVisibility(View.GONE);
                view_stockist.setVisibility(View.GONE);

                ll_branch.setVisibility(View.GONE);
                select_branch.setVisibility(View.GONE);
                select_branch_1.setVisibility(View.GONE);
                select_branch_2.setVisibility(View.GONE);
                name_select_branch.setVisibility(View.GONE);
                select_branch_3.setVisibility(View.GONE);
                view_branch.setVisibility(View.GONE);

            } else if (toggle_flag == "S") {

                txt_lbl_EMPLOYEE.setVisibility(View.GONE);
                select_employee.setVisibility(View.GONE);
                select_employee_1.setVisibility(View.GONE);
                select_employee_2.setVisibility(View.GONE);
                name_select_employee.setVisibility(View.GONE);
                select_employee_3.setVisibility(View.GONE);
                view_employee.setVisibility(View.GONE);

                ll_stockist.setVisibility(View.VISIBLE);
                select_stockist.setVisibility(View.VISIBLE);
                select_stockist_1.setVisibility(View.VISIBLE);
                select_stockist_2.setVisibility(View.VISIBLE);
                name_select_stockist.setVisibility(View.VISIBLE);
                select_stockist_3.setVisibility(View.VISIBLE);
                view_stockist.setVisibility(View.VISIBLE);

                if (branch.equals("ALL Branch") == true) {
                    ll_branch.setVisibility(View.VISIBLE);
                    select_branch.setVisibility(View.VISIBLE);
                    select_branch_1.setVisibility(View.VISIBLE);
                    select_branch_2.setVisibility(View.VISIBLE);
                    name_select_branch.setVisibility(View.VISIBLE);
                    select_branch_3.setVisibility(View.VISIBLE);
                    view_branch.setVisibility(View.VISIBLE);
                } else {
                    ll_branch.setVisibility(View.GONE);
                    select_branch.setVisibility(View.GONE);
                    select_branch_1.setVisibility(View.GONE);
                    select_branch_2.setVisibility(View.GONE);
                    name_select_branch.setVisibility(View.GONE);
                    select_branch_3.setVisibility(View.GONE);
                    view_branch.setVisibility(View.GONE);
                }

            }

            super.onResume();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void call_fragment(Bundle bundle) {
        try {

            Fragment frag = new fragment_primary_secondary_on_app();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            frag.setArguments(bundle);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack("fragment_primary_secondary_on_app_filter");
            ft.commit();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public void init_controls() {
        try {

            btn_show_primary = (Button) getView().findViewById(R.id.btn_show_primary);

            btn_togg_user_stockist = (ToggleButton) getView().findViewById(R.id.btn_togg_user_stockist);

            txt_from_date = (LinearLayout) getView().findViewById(R.id.txt_from_date);
            txt_from_date1 = (LinearLayout) getView().findViewById(R.id.txt_from_date1);
            txt_from_date2 = (TextView) getView().findViewById(R.id.txt_from_date2);
            txt_from_date3 = (ImageButton) getView().findViewById(R.id.txt_from_date3);

            txt_to_date = (LinearLayout) getView().findViewById(R.id.txt_to_date);
            txt_to_date1 = (LinearLayout) getView().findViewById(R.id.txt_to_date1);
            txt_to_date2 = (TextView) getView().findViewById(R.id.txt_to_date2);
            txt_to_date3 = (ImageButton) getView().findViewById(R.id.txt_to_date3);

            txt_lbl_EMPLOYEE = (TextView) getView().findViewById(R.id.txt_lbl_EMPLOYEE);

            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee_1 = (LinearLayout) getView().findViewById(R.id.select_employee_1);
            select_employee_2 = (TextView) getView().findViewById(R.id.select_employee_2);
            name_select_employee = (TextView) getView().findViewById(R.id.name_select_employee);
            select_employee_3 = (ImageButton) getView().findViewById(R.id.select_employee_3);
            view_employee = (View) getView().findViewById(R.id.view_employee);


            ll_stockist = (LinearLayout) getView().findViewById(R.id.ll_stockist);
            select_stockist = (LinearLayout) getView().findViewById(R.id.select_stockist);
            select_stockist_1 = (LinearLayout) getView().findViewById(R.id.select_stockist_1);
            select_stockist_2 = (TextView) getView().findViewById(R.id.select_stockist_2);
            name_select_stockist = (TextView) getView().findViewById(R.id.name_select_stockist);
            select_stockist_3 = (ImageButton) getView().findViewById(R.id.select_stockist_3);
            view_stockist = (View) getView().findViewById(R.id.view_stockist);

            ll_branch = (LinearLayout) getView().findViewById(R.id.ll_branch);
            select_branch = (LinearLayout) getView().findViewById(R.id.select_branch);
            select_branch_1 = (LinearLayout) getView().findViewById(R.id.select_branch_1);
            select_branch_2 = (TextView) getView().findViewById(R.id.select_branch_2);
            name_select_branch = (TextView) getView().findViewById(R.id.name_select_branch);
            select_branch_3 = (ImageButton) getView().findViewById(R.id.select_branch_3);
            view_branch = (View) getView().findViewById(R.id.view_branch);

            ll_toggle_btn = (LinearLayout) getView().findViewById(R.id.ll_toggle_btn);
            view_toggle_btn = (View) getView().findViewById(R.id.view_toggle_btn);


            actirab_tab = (CheckBox) getView().findViewById(R.id.actirab_tab);
            actirab_d_cap = (CheckBox) getView().findViewById(R.id.actirab_d_cap);
            actirab_dv_cap = (CheckBox) getView().findViewById(R.id.actirab_dv_cap);
            actirab_l_cap = (CheckBox) getView().findViewById(R.id.actirab_l_cap);
            empower_od_tab = (CheckBox) getView().findViewById(R.id.empower_od_tab);
            stand_sp_tab = (CheckBox) getView().findViewById(R.id.stand_sp_tab);
            star_t_tab = (CheckBox) getView().findViewById(R.id.star_t_tab);
            glucolyst_g1_tab = (CheckBox) getView().findViewById(R.id.glucolyst_g1_tab);
            lycorest_tab = (CheckBox) getView().findViewById(R.id.lycorest_tab);
            lycort_1ml_inj = (CheckBox) getView().findViewById(R.id.lycort_1ml_inj);
            regain_xt_tab = (CheckBox) getView().findViewById(R.id.regain_xt_tab);
            lycorest_60ml_susp = (CheckBox) getView().findViewById(R.id.lycorest_60ml_susp);
            lycolic_10ml_drops = (CheckBox) getView().findViewById(R.id.lycolic_10ml_drops);
            stand_mf_60ml_susp = (CheckBox) getView().findViewById(R.id.stand_mf_60ml_susp);
            ten_n_30ml_syrup = (CheckBox) getView().findViewById(R.id.ten_n_30ml_syrup);
            wego_gel_20_mg = (CheckBox) getView().findViewById(R.id.wego_gel_20_mg);
            wego_gel_30_mg = (CheckBox) getView().findViewById(R.id.wego_gel_30_mg);
            trygesic_tab = (CheckBox) getView().findViewById(R.id.trygesic_tab);
            /*--------------------------------------------*/
            itezone_200_cap = (CheckBox) getView().findViewById(R.id.itezone_200_cap);
            nextvit_tab = (CheckBox) getView().findViewById(R.id.nextvit_tab);

            /*new branch product 28/04/2018*/
            trygesic_ptab = (CheckBox) getView().findViewById(R.id.trygesic_ptab);
            cipgrow_syrup = (CheckBox) getView().findViewById(R.id.cipgrow_syrup);
            clavyten_625 = (CheckBox) getView().findViewById(R.id.clavyten_625);
            levocast_m = (CheckBox) getView().findViewById(R.id.levocast_m);
            altipan_dsr = (CheckBox) getView().findViewById(R.id.altipan_dsr);
            sangria_tonic = (CheckBox) getView().findViewById(R.id.sangria_tonic);
            onederm_cream = (CheckBox) getView().findViewById(R.id.onederm_cream);
            actirest_ls = (CheckBox) getView().findViewById(R.id.actirest_ls);
            actirest_dx = (CheckBox) getView().findViewById(R.id.actirest_dx);
            korby_soap = (CheckBox) getView().findViewById(R.id.korby_soap);
            /*End New*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void todate_assign_to_from_to_date_text_view() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            txt_from_date2.setText(sdf.format(date));
            txt_to_date2.setText(sdf.format(date));
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void CHECK_ABM_RBM_ZBM_SM() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));

            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || designation.equals("NBM") || designation.equals("HR Manager") || designation.equals("Head of Marketing and Sales")) {
                toggle_flag = "E";

                txt_lbl_EMPLOYEE.setVisibility(View.VISIBLE);

                select_employee.setVisibility(View.VISIBLE);
                select_employee_1.setVisibility(View.VISIBLE);
                select_employee_2.setVisibility(View.VISIBLE);
                name_select_employee.setVisibility(View.VISIBLE);
                select_employee_3.setVisibility(View.VISIBLE);
                view_employee.setVisibility(View.VISIBLE);

                /*ll_stockist.setVisibility(View.VISIBLE);
                select_stockist.setVisibility(View.VISIBLE);
                select_stockist_1.setVisibility(View.VISIBLE);
                select_stockist_2.setVisibility(View.VISIBLE);
                name_select_stockist.setVisibility(View.VISIBLE);
                select_stockist_3.setVisibility(View.VISIBLE);
                view_stockist.setVisibility(View.VISIBLE);*/

                ll_toggle_btn.setVisibility(View.VISIBLE);
                view_toggle_btn.setVisibility(View.VISIBLE);

            } else if (designation.equals("TBM")) {
                toggle_flag = "";
                txt_lbl_EMPLOYEE.setVisibility(View.GONE);
                select_employee.setVisibility(View.GONE);
                select_employee_1.setVisibility(View.GONE);
                select_employee_2.setVisibility(View.GONE);
                name_select_employee.setVisibility(View.GONE);
                select_employee_3.setVisibility(View.GONE);
                view_employee.setVisibility(View.GONE);

                ll_stockist.setVisibility(View.GONE);
                select_stockist.setVisibility(View.GONE);
                select_stockist_1.setVisibility(View.GONE);
                select_stockist_2.setVisibility(View.GONE);
                name_select_stockist.setVisibility(View.GONE);
                select_stockist_3.setVisibility(View.GONE);
                view_stockist.setVisibility(View.GONE);/**/

                ll_toggle_btn.setVisibility(View.GONE);
                view_toggle_btn.setVisibility(View.GONE);
            } else {

                txt_lbl_EMPLOYEE.setVisibility(View.GONE);
                select_employee.setVisibility(View.GONE);
                select_employee_1.setVisibility(View.GONE);
                select_employee_2.setVisibility(View.GONE);
                name_select_employee.setVisibility(View.GONE);
                select_employee_3.setVisibility(View.GONE);
                view_employee.setVisibility(View.GONE);

                ll_stockist.setVisibility(View.GONE);
                select_stockist.setVisibility(View.GONE);
                select_stockist_1.setVisibility(View.GONE);
                select_stockist_2.setVisibility(View.GONE);
                name_select_stockist.setVisibility(View.GONE);
                select_stockist_3.setVisibility(View.GONE);
                view_stockist.setVisibility(View.GONE);/**/

                ll_toggle_btn.setVisibility(View.GONE);
                view_toggle_btn.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String ss) {
        try {

            select_employee_2 = (TextView) getView().findViewById(R.id.select_employee_2);
            select_employee_2.setText(fullname.toString());

            name_select_employee = (TextView) getView().findViewById(R.id.name_select_employee);
            name_select_employee.setText(id.toString());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishStockistListHirarchyDialog(String id, String fullname) {
        try {

            select_stockist_2 = (TextView) getView().findViewById(R.id.select_stockist_2);
            select_stockist_2.setText(fullname.toString());

            name_select_stockist = (TextView) getView().findViewById(R.id.name_select_stockist);
            name_select_stockist.setText(id.toString());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishBranchListHirarchyDialog(String id, String branch) {
        try {

            select_branch_2 = (TextView) getView().findViewById(R.id.select_branch_2);
            select_branch_2.setText(branch.toString());

            name_select_branch = (TextView) getView().findViewById(R.id.name_select_branch);
            name_select_branch.setText(id.toString());

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

            if (date_flag.equals("f")) {
                txt_from_date2.setText(sdf.format(date));
            } else if (date_flag.equals("t")) {
                txt_to_date2.setText(sdf.format(date));
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

    public boolean CheckDates(String from_date, String to_date) {
        boolean b = false;
        //String d1 = "2012-07-12";
        //String d2 = "2012-06-12";
        try {
            if (dfDate.parse(from_date).before(dfDate.parse(to_date))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(from_date).equals(dfDate.parse(to_date))) {
                b = false;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static long calculateDays(Date dateEarly, Date dateLater) {
        return (dateLater.getTime() - dateEarly.getTime()) / (24 * 60 * 60 * 1000);
    }

    ////////////////////////////////////////////////////////////
    private void show_dialog_for_employee_list_tbm() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "TBM_ONLY");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_primary_secondary_on_app_filter.this, 300);
                dialog.show(getFragmentManager(), "fragment_primary_secondary_on_app_filter");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_stockist_list_tbm() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                stockist_list_to_top_hierarchy_FragmentDialog dialog = stockist_list_to_top_hierarchy_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stockist", "stockist");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_primary_secondary_on_app_filter.this, 300);
                dialog.show(getFragmentManager(), "fragment_primary_secondary_on_app_filter");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_date() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_primary_secondary_on_app_filter.this, 300);
                dialog.show(getFragmentManager(), "fragment_primary_secondary_on_app_filter");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_branch_list() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                branch_list_for_primary_secondary_on_app_FragmentDialog dialog = branch_list_for_primary_secondary_on_app_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("branch", "branch");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_primary_secondary_on_app_filter.this, 300);
                dialog.show(getFragmentManager(), "fragment_primary_secondary_on_app_filter");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void get_branch_of_user(String user) {
        try {

            pDialog.show();
            restService = new RestService();
            //final CountDownLatch latch = new CountDownLatch(1);
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String branch1 = app_preferences.getString("branch", "-");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("branch");
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user);
            Filters.put(Filter1);

            //"modified desc", 0, 1,
            restService.getService().getUser(sid, 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User>>() {
                        }.getType();
                        List<POJO_User> POJO = gson.fromJson(j2, type);

                        String branch2 = "";
                        if (POJO.size() <= 1) {
                            for (POJO_User pp : POJO) {
                                if (pp.getBranch() != null)
                                    branch2 = pp.getBranch();
                            }
                        }
                        if (!branch2.equals("")) {
                            if (branch1.equals(branch2)) {
                                //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                products_with_comma = app_preferences.getString("branch_product", "");
                                if (products_with_comma == "" || products_with_comma == null) {
                                    get_product_list_of_branch(branch2);
                                } else {
                                    visibility_of_product();
                                }
                            } else {
                                get_product_list_of_branch(branch2);
                            }
                        } else {
                            Toast.makeText(getContext(), "NOT ASSIGN BRANCH FOR SELECTED USER", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
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
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    String products_with_comma = "";

    public void get_product_list_of_branch(String branch) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().getproduct_list(sid, "'" + branch + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        //String products_with_comma = j2.get("msg").getAsString();
                        products_with_comma = j2.get("msg").getAsString();
                        //call_hide_show_function(products_with_comma);

                        /*SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("branch_product", products_with_comma);
                        editor.commit();*/

                        //////////////////////
                        visibility_of_product();
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
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void visibility_of_product() {
        try {

            //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String prodcut_comma = products_with_comma;//app_preferences.getString("branch_product", "");

            if (!prodcut_comma.equals("") && !prodcut_comma.equals(null)) {
                if (prodcut_comma.contains("ACTIRAB TAB") == true) {
                    actirab_tab.setVisibility(View.VISIBLE);
                } else {
                    actirab_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB - D CAP") == true) {
                    actirab_d_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_d_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB -DV Cap") == true) {
                    actirab_dv_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_dv_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB - L CAP") == true) {
                    actirab_l_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_l_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("EMPOWER - OD TAB") == true) {
                    empower_od_tab.setVisibility(View.VISIBLE);
                } else {
                    empower_od_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAND -SP TAB") == true) {
                    stand_sp_tab.setVisibility(View.VISIBLE);
                } else {
                    stand_sp_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAR T TAB") == true) {
                    star_t_tab.setVisibility(View.VISIBLE);
                } else {
                    star_t_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("GLUCOLYST -G1 TAB") == true) {
                    glucolyst_g1_tab.setVisibility(View.VISIBLE);
                } else {
                    glucolyst_g1_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOREST TAB") == true) {
                    lycorest_tab.setVisibility(View.VISIBLE);
                } else {
                    lycorest_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCORT 1 ml INJ") == true) {
                    lycort_1ml_inj.setVisibility(View.VISIBLE);
                } else {
                    lycort_1ml_inj.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("REGAIN - XT TAB") == true) {
                    regain_xt_tab.setVisibility(View.VISIBLE);
                } else {
                    regain_xt_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOREST 60ml SUSP") == true) {
                    lycorest_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    lycorest_60ml_susp.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOLIC 10ml DROP") == true) {
                    lycolic_10ml_drops.setVisibility(View.VISIBLE);
                } else {
                    lycolic_10ml_drops.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAND - MF 60ml SUSP") == true) {
                    stand_mf_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    stand_mf_60ml_susp.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("TEN-ON 30 ml SYRUP") == true) {
                    ten_n_30ml_syrup.setVisibility(View.VISIBLE);
                } else {
                    ten_n_30ml_syrup.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("WEGO GEL 20mg") == true) {
                    wego_gel_20_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_20_mg.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("WEGO GEL 30mg") == true) {
                    wego_gel_30_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_30_mg.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("TRYGESIC TAB") == true) {
                    trygesic_tab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_tab.setVisibility(View.GONE);
                }
                ////////////////////////
                if (prodcut_comma.contains("TRYGESIC-P") == true) {
                    trygesic_ptab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_ptab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("CIPGROW SYRUP") == true) {
                    cipgrow_syrup.setVisibility(View.VISIBLE);
                } else {
                    cipgrow_syrup.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("CLAVYTEN 625") == true) {
                    clavyten_625.setVisibility(View.VISIBLE);
                } else {
                    clavyten_625.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LEVOCAST-M") == true) {
                    levocast_m.setVisibility(View.VISIBLE);
                } else {
                    levocast_m.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ALTIPAN DSR") == true) {
                    altipan_dsr.setVisibility(View.VISIBLE);
                } else {
                    altipan_dsr.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("SANGRIA TONIC") == true) {
                    sangria_tonic.setVisibility(View.VISIBLE);
                } else {
                    sangria_tonic.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ONEDERM CREAM") == true) {
                    onederm_cream.setVisibility(View.VISIBLE);
                } else {
                    onederm_cream.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIREST-LS") == true) {
                    actirest_ls.setVisibility(View.VISIBLE);
                } else {
                    actirest_ls.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIREST-DX") == true) {
                    actirest_dx.setVisibility(View.VISIBLE);
                } else {
                    actirest_dx.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("KORBY SOAP") == true) {
                    korby_soap.setVisibility(View.VISIBLE);
                } else {
                    korby_soap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ITEZONE 200 CAP") == true) {
                    itezone_200_cap.setVisibility(View.VISIBLE);
                } else {
                    itezone_200_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("NEXTVIT TAB") == true) {
                    nextvit_tab.setVisibility(View.VISIBLE);
                } else {
                    nextvit_tab.setVisibility(View.GONE);
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

